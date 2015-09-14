/**
 * 倒班规则管理
 */
var basePath=(function(){
	var href=window.location.href;
	var host=window.location.host;
	var index = href.indexOf(host)+host.length+1; //host结束位置的索引（包含/）
	return href.substring(0,href.indexOf('/',index));
})(window);

//根据规则ID获得规则的详细信息;
function getPlanDetial(ruleId,cycleDays,ruleName, org_id){
	$.ajax({
		type : "POST",
		url : basePath+"/workturnsrule/getWorkRoundAll.action",
		data : {
			ruleId:ruleId
		},
		success : function(data) {
			if(data==null){
				Ext.tooltip.msg("no","您还没有添加班次信息，或者班次信息已经删除！");
				return ;
			}
			var dataMap=eval("("+data+")");
			showPlanDetial(ruleId,cycleDays,dataMap,ruleName,org_id);
		},
		failure:function(data){
			Ext.tooltip.msg("no",data.msg);
		}
	});
}



function showPlanDetial(ruleId,cycleDays,dataMap,ruleName,org_id){
	//班次ids
	var roundIds=new Array();
	
	var gridRoundId = 0;
	
	var sm = Ext.create("Ext.selection.CheckboxModel",{
		injectCheckbox:0,
    	listeners: {
	        selectionchange: function(){
	        	/*var c = ruleTeamRoundMapGrid.getSelectionModel().getSelection();
				if (c.length > 0) {
					Ext.getCmp('delRuleDetailBtn').setDisabled(false);
				} else {
					Ext.getCmp('delRuleDetailBtn').setDisabled(true);
				}*/
	        }
		}
    });
	
	var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
		clicksToEdit: 1 ,
		 listeners : {
			 beforeedit:function(editor, e, eOpts ){
				 gridRoundId = e.column.dataIndex;
				 workTeamStore.load();
			 }
		 }
	}); 
	
	//建立下拉列表的Store
	Ext.define('workTeamCombo', {
	    extend: 'Ext.data.Model',
	    fields: [
			{name: 'workTeamId', type: 'string'},
			{name: 'workTeamName',  type: 'string'}
	    ]
	});
	
	var workTeamStore = Ext.create("Ext.data.Store", {
        pageSize: SystemConstant.commonSize,
        model:"workTeamCombo",
		proxy: {
            type:"ajax",
            extraParams:{ruleId:ruleId},
            actionMethods: {
            	read: 'POST'
       		},
       		url: basePath + "/workturnsrule/getAllWorkTeam.action",
		    reader:{
				type:'json'
			}
        }
	});
	
	var columns = new Array();
	var headers=new Array();
	//columns.push(sm);
	columns.push({header:'计划时间',width: 25, dataIndex:'date'});
	headers.push({name:'date',type:'string'});
	
	for(var j=0;j<dataMap.length;j++){
		var roundId=dataMap[j].id;
		headers.push({name:roundId,type:'string'});//实际的数据列
		headers.push({name:"id&&"+roundId,type:'string'});
		headers.push({name:"teamId&&"+roundId,type:'string'});
	}
	
	Ext.define('turnsRuleDetail', {
	    extend: 'Ext.data.Model',
	    fields: headers
	});
	
	for(var i=0;i<dataMap.length;i++){
		roundIds.push(dataMap[i].id + '');//保存班次ID
		  columns.push({header:dataMap[i].roundName+"("+dataMap[i].startTime+"-"+dataMap[i].endTime+")",width: 25, dataIndex:dataMap[i].id + '',
			field:{
				store: workTeamStore,
			    queryMode: 'local',
			    displayField: 'workTeamName',
			    valueField: 'workTeamName',
			    editable : false,
			    xtype:'combo',
			    listeners : {
					'select' : function(combo,re) {
						var row = ruleTeamRoundMapGrid.getSelectionModel().getSelection()[0];
						//row.set('teamId&&'+gridRoundId,re[0].get('workTeamId'));
						var id=row.get("id&&"+gridRoundId);
						var workTeamId=re[0].get('workTeamId');
						var planDate=row.get('date');
						updateWorkRulePlan(id,workTeamId,gridRoundId,planDate,ruleId);
					}/*,
					'change' : function(combo,newVaue,oldValue,eOpts){
						var row = ruleTeamRoundMapGrid.getSelectionModel().getSelection()[0];
						var id=row.get("id&&"+roundId);
						var workTeamId=row.get("teamId&&"+roundId);
						var planDate=row.get('date');
						updateWorkRulePlan(id,workTeamId,roundId,planDate,ruleId);
					}*/
		        }
			},
			renderer:function(value,metadata,re){
				if(value){
					var index = workTeamStore.find('workTeamId',value);
					if(index != -1){
						var record = workTeamStore.getAt(index);
						return record.data.workTeamName;
					}else{
						return value;
					}
				}else{
					return "";
				}
			}
		});//显示的数据列;
	}
	//建立grid的Store
	var ruleTeamRoundMapStore = Ext.create("Ext.data.Store", {
        pageSize: SystemConstant.commonSize,
        model:"turnsRuleDetail",
        //remoteSort:true,
		proxy: {
            type:"ajax",
            actionMethods: {
            	read: 'POST'
       		},
       		extraParams:{ruleId:ruleId,orgId:org_id},
       		url: basePath + "/workturnsrule/getWorkRulePlans.action",
       		reader: {
			     totalProperty: "totalSize",
			     root: "list"
		    }
            //simpleSortMode :true
        }
        /*sorters:[{
            property:"id",
            direction:"ASC"
        }],*/
        //autoLoad: true
	});
	
	//grid组件
	var ruleTeamRoundMapGrid =  Ext.create("Ext.grid.Panel",{
		id: 'ruleTeamRoundMapGrid',
		border:false,
		columnLines: true,
		layout:"fit",
		region: "center",
		width: "100%",
		height: document.body.clientHeight,
		columns:columns,
        selModel:sm,
        plugins: [cellEditing],
     	forceFit : true, 
		store: ruleTeamRoundMapStore,
		autoScroll: true,
		stripeRows: true,
		bbar:Ext.create("Ext.PagingToolbar",{
	    	  style:'border-right:0px;border-bottom:0px;border-left:0px',
	    	  store: ruleTeamRoundMapStore,
	    	  displayInfo: true,
			  displayMsg: SystemConstant.displayMsg,
			  emptyMsg: SystemConstant.emptyMsg
	    }),
	    tbar:['计划时间从：',{
	    	id:'startTime',
	    	name:'start_date',
	    	xtype:'datefield',
	    	format: 'Y-m-d',
	    	editable:false,
			invalidText:'格式无效，格式必须为例如:2012-01-01',
	    	width:110
	    },'&nbsp;到:',{
	    	id:'endTime',
	    	name:'end_date',
	    	xtype:'datefield',
	    	editable:false,
			invalidText:'格式无效，格式必须为例如:2012-01-01',
	    	format: 'Y-m-d',
	    	width:110
	    },' ',{
	    	id:'searchBtn',
			xtype:'button',
	    	text :'查询', 
	    	iconCls:'search-button',
	    	handler:function(){
	    		var startDate = Ext.getCmp('startTime').getValue();
   	    		var endDate = Ext.getCmp('endTime').getValue();
   	    		var proxy = ruleTeamRoundMapStore.getProxy();
				proxy.setExtraParam("workRulePlanStartDate",Ext.util.Format.date(startDate,'Y-m-d'));
				proxy.setExtraParam("workRulePlanEndDate",Ext.util.Format.date(endDate,'Y-m-d'));
				ruleTeamRoundMapStore.loadPage(1);
	    	}
	    }]
	});
	
	var ruleTeamRoundMapWin = Ext.create("Ext.window.Window",{
		id:'ruleTeamRoundMapWin',
		title: '倒班计划',
		resizable: false,
		buttonAlign:"center",
	  	height: 450,
	    width: 800,
	    modal:true,
	    layout: 'fit',
	    modal : true,
	    items: [ruleTeamRoundMapGrid],
	    buttons:[{
			    text: '关闭',
                handler: function(){
                	ruleTeamRoundMapWin.close();
                }
		}]
	 }).show();
	ruleTeamRoundMapStore.loadPage(1);
}

function updateWorkRulePlan(id,teamId,roundId,planDate,ruleId){
	//保存到数据库;
	 Ext.Ajax.request({
			url : basePath+'/workturnsrule/updateRulePlan.do',
			params : {
				'id' : id,
				'teamId':teamId,
				'roundId':roundId,
				'planDate':planDate,
				'ruleId':ruleId
			},
			success : function(response, opts) {
				var data=Ext.decode(response.responseText);
				if(data.success){
					Ext.getCmp("ruleTeamRoundMapGrid").getStore().reload();
				}
			},
			failure : function(response, opts) {
			}
		});
}
