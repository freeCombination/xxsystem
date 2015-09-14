<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/doc_type.jsp"%>
<html>
<head>
<%@include file="../common/meta.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/css.jsp"%>
<%@include file="../common/ext.jsp"%>
<title>模板管理</title>
</head>
<body>

	<script type="text/javascript">
	var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
	
	Ext.onReady(function() {
		Ext.QuickTips.init();
		//自动引入其他需要的js
		Ext.require(["Ext.container.*",
		             "Ext.grid.*", 
		             "Ext.toolbar.Paging", 
		             "Ext.form.*",
					 "Ext.data.*" ]);
		//建立Model模型对象
		Ext.define("Rapid",{
			extend:"Ext.data.Model",
			fields:[
				{name:'rapidId',mapping:'rapidId'},
				{name:'rapidName',mapping:'rapidName'},
				{name:'rapidValue',mapping:'rapidValue'},
				{name:'rapidOrder',mapping:'rapidOrder'},
				{name:'rapidStatus',mapping:'rapidStatus'}
			]
		});
		
		//建立数据Store
		var rapidStore=Ext.create("Ext.data.Store", {
	        pageSize: SystemConstant.commonSize,
	        model:"Rapid",
	        remoteSort:true,
			proxy: {
	            type:"ajax",
	            actionMethods: {
                	read: 'POST'
           		},
			    url: "${ctx}/rapid/getRapidListVo.action",
			    reader: {
				     totalProperty: "totalSize",
				     root: "list"
			    },
	        simpleSortMode :true
	        }
		});
		
		//行选择模型
		var sm=Ext.create("Ext.selection.CheckboxModel",{
			injectCheckbox:1,
	    	listeners: {
		      selectionchange: function(){
		        	var c = rapidGrid.getSelectionModel().getSelection();
						 	if(c.length > 0){
								Ext.getCmp('delRapidBtn').setDisabled(false);
						 	}else{
							 	Ext.getCmp('delRapidBtn').setDisabled(true);
						 	}
						 	if(c.length == 1){
							 	Ext.getCmp('updateRapidBtn').setDisabled(false);
						 	}else{
							 	Ext.getCmp('updateRapidBtn').setDisabled(true);
						 	}
		      }
			}
	    });
		
		var cm=[
				{header:"序号",xtype: "rownumberer",width:60,align:"center",menuDisabled: true,sortable :false}
				,{header:'字典名称',width:100,sortable:false,dataIndex:'rapidName',menuDisabled: true}
				,{header:'字典值',width:100,sortable:false,dataIndex:'rapidValue',menuDisabled: true}
				,{header:'排序',width:100,sortable:false,dataIndex:'rapidOrder',menuDisabled: true}
				,{header:'rapidStatus',width:100,sortable:false,dataIndex:'rapidStatus',menuDisabled: true}
	         ];
		
		
		//grid组件
		var rapidGrid =  Ext.create("Ext.grid.Panel",{
			title:'管理',
			border:false,
			columnLines: true,
			layout:"fit",
			region: "center",
			width: "100%",
			height: document.body.clientHeight,
			id: "rapidGrid",
			bbar:  Ext.create("Ext.PagingToolbar", {
				store: rapidStore,
				displayInfo: true,
				displayMsg: SystemConstant.displayMsg,
				emptyMsg: SystemConstant.emptyMsg
			}),
			columns:cm,
	        selModel:sm,
	     	forceFit : true,
			store: rapidStore,
			autoScroll: true,
			stripeRows: true,
			tbar: [
			'&nbsp;'
			,{xtype:'textfield',fieldLabel:'字典名称',id:'s_rapidName',width:288}
			,{xtype:'textfield',fieldLabel:'字典值',id:'s_rapidValue',width:288}
			,{xtype:'textfield',fieldLabel:'排序',id:'s_rapidOrder',width:288}
			,{xtype:'textfield',fieldLabel:'rapidStatus',id:'s_rapidStatus',width:288}
			,{
				id:'searchRapidBtn',
				xtype:'button',
				disabled:false,
				text:'查询',
				iconCls:'search-button',
				handler:function(){
					var proxy = rapidStore.getProxy();
					proxy.setExtraParam("s_rapidName",Ext.getCmp("s_rapidName").getValue());
					proxy.setExtraParam("s_rapidValue",Ext.getCmp("s_rapidValue").getValue());
					proxy.setExtraParam("s_rapidOrder",Ext.getCmp("s_rapidOrder").getValue());
					proxy.setExtraParam("s_rapidStatus",Ext.getCmp("s_rapidStatus").getValue());
					rapidStore.loadPage(1);
				}
			},'->',
			{
				id:'addRapidBtn',
				xtype:'button',
				disabled:false,
				text:'添加',
				iconCls:'add-button',
				handler:function(){
					createAddRapid();
				}
			},
			{
				id:'updateRapidBtn',
				xtype:'button',
				text:'修改',
				disabled:true,
				iconCls:'edit-button',
				handler:function(){
					createUpdateRapid();
				}
			},
			{
				id:'delRapidBtn',
				xtype:'button',
				text:'删除',
				disabled:true,
				iconCls:'delete-button',
				handler:function(){
					var ck = Ext.getCmp('rapidGrid').getSelectionModel().getSelection();
					var itemsArray = new Array();
					for(var i=0;i<ck.length;i++){
						itemsArray.push(ck[i].data.rapidId);
					}
					var idss = itemsArray.toString();
					Ext.Msg.confirm(SystemConstant.alertTitle,"确认删除所选数据吗？",function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url : '${ctx}/rapid/delRapid.action',
								params : {ids: idss},
								success : function(res, options) {
									var result = Ext.decode(res.responseText);
									if(result.success == 'true'){
										new Ext.ux.TipsWindow(
												{
													title: SystemConstant.alertTitle,
													autoHide: 3,
													html:result.msg
												}
										).show();
									}else{
										Ext.MessageBox.show({
											title: SystemConstant.alertTitle,
											msg: result.msg,
											buttons: Ext.MessageBox.OK,
											icon: Ext.MessageBox.ERROR
										});
									}
									rapidStore.loadPage(1);
								}
							});
						}
					});
				}
			}] 
		
		});
		rapidStore.load({params:{start:0,limit:SystemConstant.commonSize}});
		
		Ext.create("Ext.container.Viewport", {
		    layout: "border",
			renderTo: Ext.getBody(),
			items: [rapidGrid]
		});
		
		//添加信息窗口
		var createAddRapid = function(){
			var rapidForm = Ext.create("Ext.form.Panel", {
				layout: 'form',
				bodyStyle :'padding:15px 10px 0 0',
				border: false,
				labelAlign: 'right',
				fieldDefaults: {
		            labelWidth: 100,
		        	labelAlign: 'right'
		        },
		        defaults: {
			        anchor: '60%'
			    },
			    defaultType: 'textfield',
			    items: [
					{
					    xtype:'panel',
					    layout:'column',
					    width:400,
					    border:false,
					    defaults:{border:false}
					}
					,{xtype:'hidden',fieldLabel:'主键',name:'rapid.rapidId',width:288}
					,{xtype:'textfield',fieldLabel:'字典名称',name:'rapid.rapidName',width:288}
					,{xtype:'textfield',fieldLabel:'字典值',name:'rapid.rapidValue',width:288}
					,{xtype:'textfield',fieldLabel:'排序',name:'rapid.rapidOrder',width:288}
					,{xtype:'textfield',fieldLabel:'rapidStatus',name:'rapid.rapidStatus',width:288}
			      ]
			});
			
			var rapidWin=Ext.create("Ext.window.Window",{
				title: '添加',
				resizable: false,
				buttonAlign:"center",
			  	height: 250,
			    width: 350,
			    modal:true,
			    layout: 'fit',
			    modal : true,
			    items: [rapidForm],
			    buttons:[{
					    text: SystemConstant.saveBtnText,
				    	handler: function(){
				    		if(rapidForm.form.isValid()){
				    			
				    				Ext.MessageBox.wait("", "添加数据", 
											{
												text:"请稍后..."
											}
										);
				    			rapidForm.form.submit({
				    				url:"${ctx}/rapid/addRapid.action",
			    				    success : function(form, action) {
			    					    new Ext.ux.TipsWindow(
											{
												title: SystemConstant.alertTitle,
												autoHide: 3,
												html:action.result.msg
											}).show();
			    					   
			    					  rapidStore.loadPage(1); 
			    					  rapidWin.close();
			    					  Ext.MessageBox.hide();
			    				   },
			    				   failure : function(form,action){
			    					   Ext.MessageBox.show({
			    						   title:'提示信息',
			    						   msg:action.result.msg,
			    						   buttons: Ext.Msg.YES,
			    						   modal : true,
			    						   icon: Ext.Msg.ERROR
			    					   });
			    					   rapidStore.loadPage(1);
			    					   rapidWin.close();
			    					   Ext.MessageBox.hide();
			    				   }
				    			});
			 				}
		                }
					},{
					    text: '关闭',
		                handler: function(){
		                	rapidWin.close();
		                }
				}]
			 }).show();
			
		};
		
		//修改信息
		var createUpdateRapid = function(){
			var row = rapidGrid.getSelectionModel().getSelection();
			var dname = row[0].get('rapidName');
			var rapidForm = Ext.create("Ext.form.Panel", {
				layout: 'form',
				bodyStyle :'padding:15px 10px 0 0',
				border: false,
				labelAlign: 'right',
				fieldDefaults: {
		            labelWidth: 100,
		        	labelAlign: 'right'
		        },
		        defaults: {
			        anchor: '80%'
			    },
			    defaultType: 'textfield',
			    items: [
				{
				    xtype:'panel',
				    layout:'column',
				    width:400,
				    border:false,
				    defaults:{border:false}
				}
				,{xtype:'hidden',fieldLabel:'主键',name:'rapid.rapidId',width:288}
				,{xtype:'textfield',fieldLabel:'字典名称',name:'rapid.rapidName',width:288}
				,{xtype:'textfield',fieldLabel:'字典值',name:'rapid.rapidValue',width:288}
				,{xtype:'textfield',fieldLabel:'排序',name:'rapid.rapidOrder',width:288}
				,{xtype:'textfield',fieldLabel:'rapidStatus',name:'rapid.rapidStatus',width:288}
			   ]
			 });
			
			var rapidWin=Ext.create("Ext.window.Window",{
				title: '修改',
				resizable: false,
				buttonAlign:"center",
			  	height: 250,
			    width: 350,
			    modal:true,
			    layout: 'fit',
			    items: [rapidForm],
			    buttons:[
			    	{
					    text: SystemConstant.saveBtnText,
				    	handler: function(){
				    		if(rapidForm.form.isValid()){
				    			Ext.MessageBox.wait("", "修改数据", 
										{
											text:"请稍后..."
										}
									);
									rapidForm.form.submit({
										url:"${ctx}/rapid/updateRapid.action",
										success : function(form, action) {  
				    					   Ext.MessageBox.hide();
				    					   //清除选中行;
				    					   rapidGrid.getSelectionModel().deselectAll();
				    					   //Ext.getCmp("rapidGrid").getSelectionModel().clearSelections();
				    					   new Ext.ux.TipsWindow(
												{
													title: SystemConstant.alertTitle,
													autoHide: 3,
													html:action.result.msg
												}
											).show();
				    					   rapidWin.close();
				    					   rapidStore.loadPage(1);
				    				   },
										failure:function(form, action){
										 	Ext.getCmp("rapidGrid").getSelectionModel().clearSelections();
										 	Ext.MessageBox.show({
												title: SystemConstant.alertTitle,
												msg: action.result.msg,
												buttons: Ext.MessageBox.OK,
												icon: Ext.MessageBox.ERROR
											});
											Ext.MessageBox.hide();
											rapidWin.close();
											rapidStore.loadPage(1);
										 	return false;
										}
								});
		 					}
	          			}
					},
					{
				    text: '关闭',
		                handler: function(){
		                	rapidWin.close();
		                }
					}
				]
			 }).show();
			
		};
		
	});
	</script>
</body>
</html>