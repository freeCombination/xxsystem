<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/doc_type.jsp"%>
<html>
<head>
<%@include file="../common/meta.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/css.jsp"%>
<%@include file="../common/ext.jsp"%>
<title>日志管理</title>
</head>
<body>
	<script type="text/javascript">
		Ext.onReady(function() {
			Ext.QuickTips.init();
			//自动引入其他需要的js
			Ext.require(["Ext.container.*",
			             "Ext.grid.*", 
			             "Ext.toolbar.Paging", 
			             "Ext.form.*",
						 "Ext.data.*" ]);
			//建立Model模型对象
			Ext.define("logModel",{
				extend:"Ext.data.Model",
				fields:[
					{name: "id",mapping:"pkLogId"}, 
					{name: "typeId",mapping:"logTypeId"}, 
					{name: "typeName",mapping:"logTypeText"}, 
					{name: "userId",mapping:"userId"}, 
					{name: "userName",mapping:"userName"}, 
					{name: "opDate",mapping:"opDate"}, 
					{name: "ipUrl",mapping:"ipUrl"},
					{name: "opContent",mapping:"opContent"}
				]
			});
			//建立数据Store
			var logStore=Ext.create("Ext.data.Store", {
		        pageSize: SystemConstant.commonSize,
		        model:"logModel",
		        remoteSort:true,
				proxy: {
		            type:"ajax",
		            actionMethods: {
	                	read: 'POST'
	           		},
				    url: "${ctx}/log/getLog.action",
				    reader: {
					     totalProperty: "totalSize",
					     root: "list"
				    },
		        simpleSortMode :true
		        },
		        sorters:[{
		            property:"id",
		            direction:"ASC"
		        }]
			});
			var cm=[
					{xtype: "rownumberer",width:60,text:"序号",align:"center",menuDisabled: true,sortable :false},
		            {header: "ID",width: 70,dataIndex: "id",hidden: true,menuDisabled: true,sortable :false},
		            {header: "typeId",width: 200,dataIndex: "typeId",hidden: true,menuDisabled: true,sortable :false},
		            {header: "日志类型",width: 70,dataIndex: "typeName",menuDisabled: true,sortable :false},
		            {header: "发生日期",width: 100,dataIndex: "opDate",menuDisabled: true,sortable :false,
		            	renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
		            		var opDate = record.get("opDate");
		            		if(opDate != null && typeof(opDate) != 'undefined' && opDate != ""){
		            			return opDate.substring(0,opDate.length - 2);
		            		}else{
		            			return "";
		            		}
		            	}
		            },
		            {header: "操作用户ID",width: 100,dataIndex: "userId",hidden: true,menuDisabled: true,sortable :false},
		            {header: "操作用户",width: 100,dataIndex: "userName",menuDisabled: true,sortable :false},
		            {header: "链接信息",width: 150,dataIndex: "ipUrl",menuDisabled: true,sortable :false},
		            {header: "日志内容",width: 400,dataIndex: "opContent",menuDisabled: true,sortable :false}
		           ];
			
			//grid组件
			var logGrid =  Ext.create("Ext.grid.Panel",{
				title:'日志管理',
				columnLines: true,
				region: "center",
				id: "logGrid",
				forceFit : true,
				store: logStore,
				columns:cm,
				listeners:{
						'render': function(g) {    
                 			g.on("itemmouseenter", function(view,record,mode,index,e) {
                 	 			var view = g.getView(); 
                     			logGrid.tip = Ext.create('Ext.tip.ToolTip', {  
                        		target: view.el,
                        		delegate: view.getCellSelector(),
                        		trackMouse: true,
                        		renderTo: Ext.getBody(),
                        		listeners: {
                            		beforeshow: function updateTipBody(tip) {
                						tip.update(tip.triggerElement.innerHTML);
                            		}  
                        		}  
                    		});  
                 		});    
             		}  
				},
				bbar:  Ext.create("Ext.PagingToolbar", {
					store: logStore,
					displayInfo: true,
					displayMsg: SystemConstant.displayMsg,
					emptyMsg: SystemConstant.emptyMsg
				}),
				tbar: [
					'开始时间',
					{
						id:'startDate',
						name:'startDate',
						width:80,
						xtype:"textfield",
							listeners :{
								"afterrender":function(com, eOpts ){
		               				var startTime = Ext.getDom('startDate-inputEl');
		               				startTime.initcfg={dateFmt:'yyyy-MM-dd',disabledDates:[]};
		               				startTime.onclick=function(){
										 WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate-inputEl\')}',onpicked:function(){$dp.$('startDate-inputEl').focus();}});
		                            }
								}
							}
					},' ',
					'结束时间',
					{
						id:'endDate',
						name:'endDate',
						width:80,
						xtype:"textfield",
						listeners :{
							"afterrender":function(com, eOpts ){
	               				var endTime = Ext.getDom('endDate-inputEl');
								 endTime.initcfg={dateFmt:'yyyy-MM-dd',disabledDates:[]};
								 endTime.onclick=function(){
									 WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate-inputEl\')}',onpicked:function(){$dp.$('endDate-inputEl').focus();}});
								 }
							}
						}
					},
					' ',
					"日志内容",
					{
						xtype:"textfield",
						id:"logContent"
					},
					{ 
						text: '查询',
					  	iconCls:'search-button',
					  	handler:function(){
					  		var proxy = logStore.getProxy();
	    	   				proxy.setExtraParam("startDate",Ext.getCmp('startDate').getValue());
	    	   				proxy.setExtraParam("endDate",Ext.getCmp('endDate').getValue());
	    	   				proxy.setExtraParam("logContent",Ext.getCmp('logContent').getValue());
	    	   				logStore.loadPage(1);
	    	   				logStore.load(proxy);
					  	}
					}
				]
			});
			 	
			logGrid.on("itemdblclick",function(gridView, record, item, index, e, eOpts ){
				Ext.create("Ext.window.Window", {
					title:"日志详细",
					height:400,
					width:700,
					modal: true,
					bodyStyle:"background-color:#FFFFFF",
					html:"<table bgcolor='#FFFFFF'  cellspacing='5' cellpadding='5' >"+
						"<tr><td width='100'>日志类型：</td><td>"+record.get('typeName')+"</td><td width='100'>发生日期：</td><td>"+record.get('opDate').substr(0,19)+"</td></tr>"+
						"<tr><td width='100'>操作用户：</td><td>"+record.get('userName')+"</td><td width='100'>链接信息：</td><td>"+record.get('ipUrl')+"</td></tr>"+
						"<tr><td width='100'>日志内容：</td><td colspan='3'>"+record.get('opContent')+"</td></tr>"+
						"</table>",
					buttonAlign:'center',
					buttons:[{text:"关闭",handler:function(){
						this.up('window').close();
					}}
					]
				}).show();
			});

			 //treepanel
			 var treeStore = Ext.create("Ext.data.TreeStore", {
			        proxy: {
			            type: "ajax",
			            actionMethods: {
			                read: 'POST'
			            },
			            url: "${ctx}/log/getLogType.action"
			        },
			        root: {
			        	text:"日志类型",
			        	nodeId:"0"
			        },
			        listeners: {
                        beforeload: function (ds, opration, opt) {
                            opration.params.code = "LOGTYPE";
                        }
                    }
			    });
			 treeStore.on("load",function(store, node, records){
				 if(node != null && node.raw.nodeId == "0" && node.firstChild){
					 treePanel.getSelectionModel().select(treePanel.getRootNode().firstChild,true);
					 treePanel.fireEvent("itemclick",treePanel.getView(),node.firstChild);
				 }
			 });
						 
			 var treePanel=Ext.create("Ext.tree.Panel", {
			 		title:'日志类型',
			 		layout:'fit',
					width: 200,
					region: "west",
					border: false,
					collapsible: true,
            		split: true,
					collapseMode:"mini",
			       	store: treeStore,
			     	id:"typeTree",
			        useArrows: true,
			        rootVisible : true,
			        dockedItems: [{
			        	xtype: 'toolbar',
						style:"border-top:0px;border-left:0px",
						items:[{
					        iconCls: "icon-expand-all",
					        text:'展开',
							tooltip: "展开所有",
					        handler: function(){ treePanel.expandAll(); },
					        scope: this
					    },{
					        iconCls: "icon-collapse-all",
					        text:'折叠',
					        tooltip: "折叠所有",
					        handler: function(){ treePanel.collapseAll(); },
					        scope: this
					    }]
			        }],
			        listeners:{
			        	"afterrender":function( treePanel, eOpts ){
			        		var path = treePanel.getRootNode().getPath();
			        		treePanel.expandPath(path)
			        	}
			        }
			    });
			 treePanel.on("itemclick",function(view,record,item,index,e,opts){  
			     //获取当前点击的节点  
			      var treeNode=record.raw;  
			      var text=treeNode.text;
			      var id = treeNode.nodeId;
			      if(treeNode.id!="root"){
				      logStore.on('beforeload',function(store,options){
				    	 var new_params = {"type":id};
				    	  Ext.apply(store.proxy.extraParams,new_params);
				      });
  	   				var proxy = logStore.getProxy();
				      proxy.setExtraParam("startDate",Ext.Date.format(Ext.getCmp('startDate').value,'Y-m-d'));
  	   				proxy.setExtraParam("endDate",Ext.Date.format(Ext.getCmp('endDate').value,'Y-m-d'));
				      logStore.loadPage(1);
				      logStore.load(proxy);
			 	}
		 	});
			 
			 Ext.create("Ext.container.Viewport", {
				layout: "border",
				items: [ treePanel,logGrid]
			});
		});
	</script>
</body>
</html>