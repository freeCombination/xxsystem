/**
 * hedj
 * 合并单元格 适用于EXT3.3 和EXT4.2。
 * @param {} grid  要合并单元格的grid对象
 * @param {} cols  要合并哪几列 [2,3] 如果有选择框列和序号列时，第一列的序号应该为2.
 * @param {} parentMode  父单元格式模式。合并单元格式模式 不传时为 before
 										  before 前一个。
 										  first  使用cols[0], 可以配合第二个参数cols使用，调整父单元格到cols[0]
 										  none 不用父单元格
 * Ext.getCmp('grid').getStore().on('load',function(){  
 *	    mergeCells(Ext.getCmp('grid'),[2,3,4,5,6],'before');
 *  });  
 */
var mergeCells = function(grid,cols,parentMode){
	var arrayTr=document.getElementById(grid.getId()+"-body").firstChild.firstChild.getElementsByTagName('tbody')[0].getElementsByTagName('tr');	
	var trCount = arrayTr.length;
	var arrayTd;
	var td;
	var merge = function(rowspanObj,removeObjs){ //定义合并函数
		if(rowspanObj.rowspan != 1){
			arrayTd =arrayTr[rowspanObj.tr].getElementsByTagName("td"); //合并行
			td=arrayTd[rowspanObj.td];
			td.rowSpan=rowspanObj.rowspan;
			td.vAlign="middle";				
			Ext.each(removeObjs,function(obj){ //隐身被合并的单元格
				arrayTd =arrayTr[obj.tr].getElementsByTagName("td");
				arrayTd[obj.td].style.display='none';							
			});
		}
	};
	var rowspanObj = {}; //要进行跨列操作的td对象{tr:1,td:2,rowspan:5}	
	var removeObjs = []; //要进行删除的td对象[{tr:2,td:2},{tr:3,td:2}]
	Ext.each(cols,function(colIndex){ //逐列去操作tr
		var rowspan = 1;
		var divHtml = null;//单元格内的数值
		for(var i=0;i<trCount;i++){  //i=0表示表头等没用的行
			arrayTd = arrayTr[i].getElementsByTagName("td");
			if(!divHtml){
				divHtml = arrayTd[colIndex].innerHTML;
				rowspanObj = {tr:i,td:colIndex,rowspan:rowspan}
			}else{
				var cellText = arrayTd[colIndex].innerHTML;
				var addf=function(){ 
					rowspanObj["rowspan"] = rowspanObj["rowspan"]+1;
					removeObjs.push({tr:i,td:colIndex});
					if(i==trCount-1)
						merge(rowspanObj,removeObjs);//执行合并函数
				};
				var mergef=function(){
					merge(rowspanObj,removeObjs);//执行合并函数
					divHtml = cellText;
					rowspanObj = {tr:i,td:colIndex,rowspan:rowspan}
					removeObjs = [];
				};
				if(cellText == divHtml){
					if(colIndex!=cols[0]){
						var parentCol = parentMode=='first'? cols[0] : colIndex-1; //默认前一个
						if(parentMode=='none'){
							addf();
						}else{
							var leftDisplay=arrayTd[parentCol].style.display;//判断左边单元格值是否已display
							leftDisplay=='none'? addf() : mergef();	
						}							
					}else
						addf();
				}else
					mergef();
			}
		}
	});	
};