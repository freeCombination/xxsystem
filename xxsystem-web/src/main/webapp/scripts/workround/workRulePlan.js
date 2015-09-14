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
function getRuleDetial(ruleId,cycleDays,ruleName,org_id){
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
			showRuleDetial(ruleId,cycleDays,dataMap,ruleName,org_id);
		},
		failure:function(data){
			Ext.tooltip.msg("no",data.msg);
		}
	});
}

function showRuleDetial(ruleId,cycleDays,dataMap,ruleName,org_id){
	//班次ids
	var roundIds=new Array();
	//定义一个删除的ID数组集合;
	var delRuleIds=new Array();
	
	var gridRoundId = 0;
	
	var sm = Ext.create("Ext.selection.CheckboxModel",{
		injectCheckbox:0,
    	listeners: {
	        selectionchange: function(){
	        	var c = ruleTeamRoundMapGrid.getSelectionModel().getSelection();
				if (c.length > 0) {
					Ext.getCmp('delRuleDetailBtn').setDisabled(false);
				} else {
					Ext.getCmp('delRuleDetailBtn').setDisabled(true);
				}
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
	columns.push({header:'轮次',width: 10, dataIndex:'turnsOrder'});
	headers.push({name:'turnsOrder',type:'string'});
	
	for(var j=0;j<dataMap.length;j++){
		var roundId=dataMap[j].id;
		headers.push({name:roundId,type:'string'});//实际的数据列
		headers.push({name:"teamId&&"+roundId,type:'string'});
		headers.push({name:"id&&"+roundId,type:'string'});
	}
	
	Ext.define('turnsRuleDetail', {
	    extend: 'Ext.data.Model',
	    fields: headers
	});
	
	for(var i=0;i<dataMap.length;i++){
		roundIds.push(dataMap[i].id + '');//保存班次ID
		//columns.push({header:dataMap[i].roundName+"("+dataMap[i].startTime+"-"+dataMap[i].endTime+")",width: 25, dataIndex:dataMap[i].id, editor:workTeamEditor});//显示的数据列;
		columns.push({header:dataMap[i].roundName+"("+dataMap[i].startTime+"-"+dataMap[i].endTime+")",width: 25, dataIndex:dataMap[i].id + '',
			field:{
				store: workTeamStore,
			    queryMode: 'local',
			    displayField: 'workTeamName',
			    valueField: 'workTeamName',
			    editable : false,
			    xtype:'combo',
			    listeners : {
					"select" : function(combo,re) {
						var row = ruleTeamRoundMapGrid.getSelectionModel().getSelection()[0];
						row.set('teamId&&'+gridRoundId,re[0].get('workTeamId'));
					},
					'expand':function(combo){
					    
		            }
		        }
			},
			renderer:function(value,metadata,re){
				if(value){
					//var indexByName = workTeamStore.find('workTeamName',value);
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
        remoteSort:true,
		proxy: {
            type:"ajax",
            actionMethods: {
            	read: 'POST'
       		},
       		extraParams:{rule_id:ruleId},
		    url: basePath+"/workturnsrule/getRuleWorkTeamMapsByRuleId.action",
		    reader:{
				type:'json'
			},
			simpleSortMode :true
        }
	});
	
	//grid组件
	var ruleTeamRoundMapGrid =  Ext.create("Ext.grid.Panel",{
		border:false,
		columnLines: true,
		layout:"fit",
		//region: "center",
		//width: "100%",
		//height: document.body.clientHeight,
		id: "ruleTeamRoundMapGrid",
		columns:columns,
        selModel:sm,
        plugins: [cellEditing],
     	forceFit : true, 
		store: ruleTeamRoundMapStore,
		autoScroll: true,
		stripeRows: true,
		tbar:[
		      "->",
			{
				xtype:'button',
				disabled:false,
				text:'添加',
				iconCls:'add-button',
				handler:function(){
					var count = ruleTeamRoundMapStore.getCount();
		        	var turnsOrder = count + 1;
		        	//拼接一个数据格式;
		        	var data="{turnsOrder:"+turnsOrder+",";
		        	for(var i=0;i<roundIds.length;i++){
		        		data+="'teamId&&"+roundIds[i]+
		        		  "':'','id&&"+roundIds[i]+"':'',"+
		        		  roundIds[i]+":'',";
		        	}
		        	data=data.substring(0, data.length-1);
		        	data+="}";
		        	
		        	ruleTeamRoundMapStore.add(eval("("+data+")"));
				}
			},
			{
				id : 'delRuleDetailBtn',
				xtype : 'button',
				disabled : true,
				text : '删除',
				iconCls : 'delete-button',
				handler : function() {
					var rows = Ext.getCmp('ruleTeamRoundMapGrid').getSelectionModel().getSelection();
		    		Ext.Msg.confirm('系统提示','确定要删除这'+rows.length+'条记录吗?',function(btn){
		    			if(btn=='yes'){
	    					for(var i=0; i<rows.length;i++){
	    						//保存已存在的规则ids；
	    						for(var j=0;j<roundIds.length;j++){
	    							var delId=rows[i].get("id&&"+roundIds[j]);
	    							if(delId&&delId>0){
	    								delRuleIds.push(delId);
	    							}
	    						}
	    						ruleTeamRoundMapStore.remove(rows[i]);
	    					}
	    					//重新设置轮次；
	    					var store =Ext.getCmp('ruleTeamRoundMapGrid').getStore();
	    					var count = store.getCount();
	    					for(var i=0;i<count;i++){
	    						var re=store.getAt(i);
	    						re.set("turnsOrder",i+1);//重新排轮次
	    					}
	    					Ext.getCmp('ruleTeamRoundMapGrid').getView().refresh();//刷新行号
		    			}
		    		});
				}
			}
		]
	});
	
	
	var originalCount = 0;
	var operation = "close";
	var ruleTeamRoundMapWin = Ext.create("Ext.window.Window",{
		title: ruleName+'(周期：'+cycleDays+')',
		resizable: false,
		buttonAlign:"center",
	  	height: 400,
	    width: 600,
	    modal:true,
	    layout: 'fit',
	    modal : true,
	    items: [ruleTeamRoundMapGrid],
	    listeners:{
	    	beforeclose:function(){
	    		if(operation == "close"){
	    			var count = ruleTeamRoundMapStore.getCount();
	    			if(count!=cycleDays){
		        		Ext.MessageBox.show({
						    title:'提示信息',
						    msg:'周期与轮次不匹配！',
						    buttons: Ext.Msg.YES,
						    modal : true,
						    icon: Ext.Msg.ERROR
					    });
		        		return false;
		        	}
	    			
	    			for(var i=0;i<ruleTeamRoundMapStore.getCount();i++){
		    			var re=ruleTeamRoundMapStore.getAt(i);
		    			for(var j=0;j<roundIds.length;j++){
		    				var roundId=roundIds[j];
		    				var workTeamId=re.get('teamId&&'+roundId);
		    				if(!workTeamId){
		    					//判断数据的完整性
	    						Ext.MessageBox.show({
	    						    title:'提示信息',
	    						    msg:'请填写完整的倒班规则！',
	    						    buttons: Ext.Msg.YES,
	    						    modal : true,
	    						    icon: Ext.Msg.ERROR
	    					    });
	    						return false;
	    					}
		    			}
		    		}
	    			
	    			if(originalCount != ruleTeamRoundMapStore.getCount()){
	    				Ext.MessageBox.show({
						    title:'提示信息',
						    msg:'数据未保存，不能关闭！',
						    buttons: Ext.Msg.YES,
						    modal : true,
						    icon: Ext.Msg.ERROR
					    });
						return false;
	    			}
	    		}
	    	}
	    },
	    buttons:[{
			    text: SystemConstant.saveBtnText,
		    	handler: function(){
		    		operation = "sure";
		    		
		    		var count = ruleTeamRoundMapStore.getCount();
	    			if(count!=cycleDays){
		        		Ext.MessageBox.show({
						    title:'提示信息',
						    msg:'周期与轮次不匹配！',
						    buttons: Ext.Msg.YES,
						    modal : true,
						    icon: Ext.Msg.ERROR
					    });
		        		operation = "close";
		        		return;
		        	}
		    		var ruleWorkTeamMap=new Array();
		    		for(var i=0;i<ruleTeamRoundMapStore.getCount();i++){
		    			var re=ruleTeamRoundMapStore.getAt(i);
		    			var turnsOrder=re.get('turnsOrder');
		    			for(var j=0;j<roundIds.length;j++){
		    				var rule="";
		    				var roundId=roundIds[j];
		    				var workTeamId=re.get('teamId&&'+roundId);
		    				if(workTeamId){
		    				    var id=re.get('id&&'+roundId);
	    						if(!id){//如果没有定义ID，则初始化一个值‘0’;
	    							id="0";
	    						}
	    						rule+="{";
	    						//更新时，加入ID字段
	    						rule+="id:"+id+",";
	    						//设置倒班规则ID
	    						rule+="ruleId:"+ruleId+",";
	    						//设置班组ID
	    						rule+="workTeamId:"+workTeamId+",";
	    						rule+="orderId:"+turnsOrder+",";//周期号
	    						rule+="roundId:"+roundId;//班次ID
	    						rule+="}";
	    						ruleWorkTeamMap.push(rule);
	    					}else{
	    						//判断数据的完整性
	    						Ext.MessageBox.show({
	    						    title:'提示信息',
	    						    msg:'请填写完整的倒班规则！',
	    						    buttons: Ext.Msg.YES,
	    						    modal : true,
	    						    icon: Ext.Msg.ERROR
	    					    });
	    						operation = "close";
	    						return ;
	    					}
		    			}
		    		}
		    		
		    		//保存到数据库;
		    		Ext.Ajax.request({
		    			url : basePath+'/workturnsrule/saveOrUpdateRuleWorkTeam.action',
		    			params : {
	    					'cycleDays':cycleDays,
	    					'ruleId':ruleId,
	    					'ruleMap' : ruleWorkTeamMap,
	    					'deleteRuleMapIds':delRuleIds.join(',')
	    				},
	    				success : function(response, opts) {
	    					var res = Ext.decode(response.responseText);
	    					if (res.success) {
	    						ruleTeamRoundMapWin.close();
	    						new Ext.ux.TipsWindow(
									{
										title: SystemConstant.alertTitle,
										autoHide: 3,
										html:res.msg
									}
								).show();
	    					}else{
	    						Ext.MessageBox.show({
	    						    title:'提示信息',
	    						    msg:res.msg,
	    						    buttons: Ext.Msg.YES,
	    						    modal : true,
	    						    icon: Ext.Msg.ERROR
	    					    });
	    					}
	    				},
	    				failure : function(response, opts) {
	    					//Ext.getCmp("save").setDisabled(false);
	    	       	    	//Ext.getCmp("next").setDisabled(false);
	    	       	    	//Ext.getCmp("close").setDisabled(false);
	    					var res = Ext.decode(response.responseText);
	    					Ext.MessageBox.show({
    						    title:'提示信息',
    						    msg:res.msg,
    						    buttons: Ext.Msg.YES,
    						    modal : true,
    						    icon: Ext.Msg.ERROR
    					    });
		    			}
		    		});
		    		
		    		
		    		
                }
			},{
				text:'下一步',
				handler:function(){
					operation = "next";
					
					var count = ruleTeamRoundMapStore.getCount();
	    			if(count!=cycleDays){
		        		Ext.MessageBox.show({
						    title:'提示信息',
						    msg:'周期与轮次不匹配！',
						    buttons: Ext.Msg.YES,
						    modal : true,
						    icon: Ext.Msg.ERROR
					    });
		        		operation = "close";
		        		return;
		        	}
		    		var ruleWorkTeamMap=new Array();
		    		for(var i=0;i<ruleTeamRoundMapStore.getCount();i++){
		    			var re=ruleTeamRoundMapStore.getAt(i);
		    			var turnsOrder=re.get('turnsOrder');
		    			for(var j=0;j<roundIds.length;j++){
		    				var rule="";
		    				var roundId=roundIds[j];
		    				var workTeamId=re.get('teamId&&'+roundId);
		    				if(workTeamId){
		    				    var id=re.get('id&&'+roundId);
	    						if(!id){//如果没有定义ID，则初始化一个值‘0’;
	    							id="0";
	    						}
	    						rule+="{";
	    						//更新时，加入ID字段
	    						rule+="id:"+id+",";
	    						//设置倒班规则ID
	    						rule+="ruleId:"+ruleId+",";
	    						//设置班组ID
	    						rule+="workTeamId:"+workTeamId+",";
	    						rule+="orderId:"+turnsOrder+",";//周期号
	    						rule+="roundId:"+roundId;//班次ID
	    						rule+="}";
	    						ruleWorkTeamMap.push(rule);
	    					}else{
	    						//判断数据的完整性
	    						Ext.MessageBox.show({
	    						    title:'提示信息',
	    						    msg:'请填写完整的倒班规则！',
	    						    buttons: Ext.Msg.YES,
	    						    modal : true,
	    						    icon: Ext.Msg.ERROR
	    					    });
	    						operation = "close";
	    						return ;
	    					}
		    			}
		    		}
					//保存到数据库;
		    		Ext.Ajax.request({
		    			url : basePath+'/workturnsrule/saveOrUpdateRuleWorkTeam.action',
		    			params : {
	    					'cycleDays':cycleDays,
	    					'ruleId':ruleId,
	    					'ruleMap' : ruleWorkTeamMap,
	    					'deleteRuleMapIds':delRuleIds.join(',')
	    				},
	    				success : function(response, opts) {
	    					var res = Ext.decode(response.responseText);
	    					if (res.success) {
	    						ruleTeamRoundMapWin.close();
	    						getRuleInfomation(ruleId,cycleDays,ruleName,org_id);
	    					}else{
	    						Ext.MessageBox.show({
	    						    title:'提示信息',
	    						    msg:res.msg,
	    						    buttons: Ext.Msg.YES,
	    						    modal : true,
	    						    icon: Ext.Msg.ERROR
	    					    });
	    					}
	    				},
	    				failure : function(response, opts) {
	    					//Ext.getCmp("save").setDisabled(false);
	    	       	    	//Ext.getCmp("next").setDisabled(false);
	    	       	    	//Ext.getCmp("close").setDisabled(false);
	    					var res = Ext.decode(response.responseText);
	    					Ext.MessageBox.show({
    						    title:'提示信息',
    						    msg:res.msg,
    						    buttons: Ext.Msg.YES,
    						    modal : true,
    						    icon: Ext.Msg.ERROR
    					    });
		    			}
		    		});
				}
			},{
			    text: '关闭',
                handler: function(){
                	operation = "close";
                	ruleTeamRoundMapWin.close();
                }
		}]
	 }).show();
	
	//ruleTeamRoundMapStore.load();
	ruleTeamRoundMapStore.load(function(){
		originalCount = ruleTeamRoundMapStore.getCount();
	});
}