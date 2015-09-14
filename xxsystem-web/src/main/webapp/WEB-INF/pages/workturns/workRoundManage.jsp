<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/doc_type.jsp"%>
<html>
<head> 
<%@include file="../common/meta.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/css.jsp"%>
<%@include file="../common/ext.jsp"%>
<title>班次管理</title>
<link href="" rel="SHORTCUT ICON" />
<style type="text/css">
  .x-form-layout-table{
	table-layout: fixed;
  }
</style>
</head>
<body>
	<script type="text/javascript">
		var userPermissionArr = new Array();
		<c:forEach items="${userPermission}" var="v">
			var obj=new Object();
			obj.value='${v.resourceId }';
			obj.name='${v.code }';
			userPermissionArr.push(obj);
		</c:forEach>
	</script>

	<script type="text/javascript">
		Ext.onReady(function() {
			Ext.QuickTips.init();
			//自动引入其他需要的js
			Ext.require(["Ext.container.*",
			             "Ext.grid.*", 
			             "Ext.toolbar.Paging", 
			             "Ext.form.*",
						 "Ext.data.*"]);
			//建立Model模型对象
			Ext.define("Round",{
				extend:"Ext.data.Model",
				fields:[
					{name: "id",mapping:"id"}, 
					{name: "rName",mapping:"roundName"}, 
					{name: "startTime",mapping:"startTime"}, 
					{name: "endTime",mapping:"endTime"}
				]
			});
			
			//建立数据Store
			var roundStore=Ext.create("Ext.data.Store", {
		        pageSize: SystemConstant.commonSize,
		        model:"Round",
		        remoteSort:true,
				proxy: {
		            type:"ajax",
		            actionMethods: {
	                	read: 'POST'
	           		},
				    url: "${ctx}/workturns/getWorkRoundList.action",
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
			
			//行选择模型
			var sm=Ext.create("Ext.selection.CheckboxModel",{
				showHeaderCheckbox :true,
				injectCheckbox:1,
		    	listeners: {
			      selectionchange: function(){
			        	var c = roundGrid.getSelectionModel().getSelection();
							 	if(c.length > 0){
									Ext.getCmp('delDicBtn').setDisabled(false);
							 	}else{
								 	Ext.getCmp('delDicBtn').setDisabled(true);
							 	}
							 	if(c.length > 0 && c.length < 2){
								 	Ext.getCmp('updateDicBtn').setDisabled(false);
							 	}else{
								 	Ext.getCmp('updateDicBtn').setDisabled(true);
							 	}
			      }
				}
		    });
			var cm=[
					{header:"序号",xtype: "rownumberer",width:60,align:"center",menuDisabled: true,sortable :false},
		            {header: "ID",width: 70,dataIndex: "id",hidden: true,menuDisabled: true,sortable :false},
		            {header: "班次名称",width: 200,dataIndex: "rName",menuDisabled: true,sortable :false},
		            {header: "开始时间",width: 200,dataIndex: "startTime",menuDisabled: true,sortable :false},
		            {header: "结束时间",width: 200,dataIndex: "endTime",menuDisabled: true,sortable :false}
		         ];
			
			//grid组件
			var roundGrid =  Ext.create("Ext.grid.Panel",{
				title:'班次管理',
				border:false,
				columnLines: true,
				layout:"fit",
				region: "center",
				width: "100%",
				height: document.body.clientHeight,
				id: "roundGrid",
				bbar:  Ext.create("Ext.PagingToolbar", {
					store: roundStore,
					displayInfo: true,
					displayMsg: SystemConstant.displayMsg,
					emptyMsg: SystemConstant.emptyMsg
				}),
				columns:cm,
		        selModel:sm,
		     	forceFit : true,
				store: roundStore,
				autoScroll: true,
				stripeRows: true,
				 tbar: ['&nbsp;&nbsp;班次名称：',
				{
					id: 'roundName',
					width: 170,   
					xtype: 'textfield',
 					labelWidth: 70
 					/* value:'输入名称...',
					listeners: {   
						focus: function(){
							Ext.getCmp("roundName").setValue('');
                    	}
					} */
				},
				{
					id:'searchDicBtn',
					xtype:'button',
					disabled:false,
					text:'查询',
					iconCls:'search-button',
					handler:function(){
						var proxy = roundStore.getProxy();
						proxy.setExtraParam("name",Ext.getCmp("roundName").getValue());
						roundStore.loadPage(1);
					}
				},/* {
					id:'',
					xtype:'button',
					disabled:false,
					text:'重置',
					iconCls:'reset-button',
					handler:function(){
						Ext.getCmp("roundName").setValue() == '';
					}
					
				}, */'->',
				{
					id:'addDicBtn',
					xtype:'button',
					disabled:false,
					text:'添加',
					hidden:true,
					iconCls:'add-button',
					handler:function(){
						createAddDictInfo();
					}
				},
				{
					id:'updateDicBtn',
					xtype:'button',
					text:'修改',
					disabled:true,
					hidden:true,
					iconCls:'edit-button',
					handler:function(){
						var rowId = roundGrid.getSelectionModel().getSelection()[0].get("id");
						Ext.Ajax.request({
							url : '${ctx}/workturns/checkRounIsExist.action',
							params : {ids: rowId},
							success : function(res, options) {
								var data = Ext.decode(res.responseText);
								if(data.success){
									createUpdateDictInfo();
								}else{
									roundStore.loadPage(1);
								 	Ext.MessageBox.show({
										title: SystemConstant.alertTitle,
										msg: data.msg,
										buttons: Ext.MessageBox.OK,
										icon: Ext.MessageBox.ERROR
									});
								 	return false;
								}
							}
						});
						
					}
				},
				{
					id:'delDicBtn',
					xtype:'button',
					text:'删除',
					disabled:true,
					hidden:true,
					iconCls:'delete-button',
					handler:function(){
						var ck = Ext.getCmp('roundGrid').getSelectionModel().getSelection();
						var itemsArray = new Array();
						for(var i=0;i<ck.length;i++){
							itemsArray.push(ck[i].data.id);
						}
						var idss = itemsArray.toString();
						Ext.Ajax.request({
							url : '${ctx}/workturns/checkRounIsExist.action',
							params : {ids: idss},
							success : function(res, options) {
								 var data = Ext.decode(res.responseText);
								 if(data.success){
								 	Ext.Msg.confirm(SystemConstant.alertTitle,"确认删除所选班次数据吗？",function(btn) {
										if (btn == 'yes') {
											Ext.Ajax.request({
												url : '${ctx}/workturns/deleteWorkRound.action',
												params : {ids: idss},
												success : function(res, options) {
													var result = Ext.decode(res.responseText);
													if(result.success){
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
													roundStore.loadPage(1);
												}
											});
										}
									});
								 }else{
								 	roundStore.loadPage(1);
								 	Ext.MessageBox.show({
										title: SystemConstant.alertTitle,
										msg: data.msg,
										buttons: Ext.MessageBox.OK,
										icon: Ext.MessageBox.ERROR
									});
								 	return false;
								 }
							}
						});
					}
				}],
				listeners:{
						'render': function(g) {    
                 			/* g.on("itemmouseenter", function(view,record,mode,index,e) {
                 	 			var view = g.getView(); 
                     			g.tip = Ext.create('Ext.tip.ToolTip', {  
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
                 		    });   */
                 			
                 			for(var i = 0;i < userPermissionArr.length;i++){
                 				if("workround_add_btn" == userPermissionArr[i].name){
                 					Ext.getCmp('addDicBtn').setVisible(true);
                 				}
                 				if("workround_update_btn" == userPermissionArr[i].name){
                 					Ext.getCmp('updateDicBtn').setVisible(true);
                 				}
                 				if("workround_delete_btn" == userPermissionArr[i].name){
                 					Ext.getCmp('delDicBtn').setVisible(true);
                 				}
                 			}
             			}  
				}
			});
			roundStore.load({params:{start:0,limit:SystemConstant.commonSize}});
			
			
			//添加班次信息窗口
			createAddDictInfo = function(){
				var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
				var tail = '<span>点</span>';
				var roundForm=Ext.create("Ext.form.Panel", {
					url:"${ctx}/workturns/addWorkRound.action",
					layout: 'form',
					bodyStyle :'padding:15px 10px 0 0',
					border: false,
					labelAlign: 'center',
					fieldDefaults: {
			            labelWidth: 100,
			        	labelAlign: 'right'
			        },
			        defaults: {
				        anchor: '60%'
				    },
				    defaultType: 'textfield',
				    items: [{
				        fieldLabel: 'ID',
				        name: 'workRound.id',
				        value:null,
				        hidden:true,
				        width: 100,
				        allowBlank: true
				    }, 
				    {
				    	id:'name',
				        fieldLabel: '班次名称',
				        name: 'workRound.roundName',
				        readOnly:false,
				        disabled:false,
				        hidden:false,
				        regex : new RegExp('^([\u4E00-\u9FFF]*-*\\w*)*$'),
						regexText : '不能包含特殊字符',
				        maxLength:25,
				        width:100,
				        allowBlank: false,
				        validator: function(value){
							var returnObj = null;
							$.ajax({
								url : '${ctx}/workturns/validateRoundName.action',
								data:{value:value},
								cache : false,
								async : false,
								type : "POST",
								dataType : 'json',
								success : function (result){
									if(!result.valid){
										returnObj = result.reason;
									}else{
										returnObj = true;
									}
								}
							});
							return returnObj;
						} 
				    },
				    {
				    	id:'startTime',
				    	fieldLabel: '开始时间',
				    	name: 'workRound.startTime',
				    	xtype:'numberfield',
						maxLength : 10,
						//minValue : 0,
						maxValue: 24,
						hidden : false,
						width : 100,
						allowBlank : false,
						regex:new RegExp('^[0-9]*[0-9][0-9]*$'),
						regexText : '只能输入非负整数！',
						beforeLabelTextTpl : required
// 						afterLabelTpl : tail
// 						listeners: {
// 					        render: function(obj) {
// 						        var font=document.createElement("font");
// 								font.setAttribute("color","black");
// 								var redStar=document.createTextNode('点');
// 								font.appendChild(redStar);    
// 								obj.el.dom.parentNode.appendChild(font);
// 					      	}
// 					    }
				    }, 
				    {
				    	id:'endTime',
				    	fieldLabel: '结束时间',
				    	xtype:'numberfield',
				    	name: 'workRound.endTime',
				    	regex:new RegExp('^[0-9]*[0-9][0-9]*$'),
				    	regexText : '只能输入非负整数！',
				    	maxLength:10,
				    	//minValue:0,
				    	maxValue: 24,
				    	width: 100,
				    	allowBlank: false
				    }]
				 });
				
				var roundWin=Ext.create("Ext.window.Window",{
					title: '添加班次',
					resizable: false,
					buttonAlign:"center",
				  	height: 250,
				    width: 350,
				    modal:true,
				    layout: 'fit',
				    modal : true,
				    items: [roundForm],
				    buttons:[{
						    text: SystemConstant.saveBtnText,
					    	handler: function(){
					    		var start = Ext.getCmp('startTime').getValue();
					    		var end = Ext.getCmp('endTime').getValue();
					    		/* if(start > 24){
					    			Ext.MessageBox.show({
			    						   title:'提示信息',
			    						   msg:"开始时间不能大于24小时！",
			    						   buttons: Ext.Msg.OK,
			    						   modal : true,
			    						   icon: Ext.Msg.INFO
			    					});
					    			return;
					    		}
					    		if(end > 24){
					    			Ext.MessageBox.show({
			    						   title:'提示信息',
			    						   msg:"结束时间不能大于24小时！",
			    						   buttons: Ext.Msg.OK,
			    						   modal : true,
			    						   icon: Ext.Msg.INFO
			    					});
					    			return;
					    		} */
					    		
					    		if(end < start || end == start){
					    			Ext.MessageBox.show({
			    						   title:'提示信息',
			    						   msg:"结束时间不能小于或等于开始时间！",
			    						   buttons: Ext.Msg.OK,
			    						   modal : true,
			    						   icon: Ext.Msg.INFO
			    					});
					    			return;
					    		}
					    		if(roundForm.form.isValid()){
					    				Ext.MessageBox.wait("", "添加字典数据", 
												{
													text:"请稍后..."
												}
											);
					    			roundForm.form.submit({
				    				   success : function(form, action) {
				    					   new Ext.ux.TipsWindow(
															{
																	title: SystemConstant.alertTitle,
																	autoHide: 3,
																	html:action.result.msg
															}
													).show();
				    					   
				    					  roundStore.loadPage(1); 
				    					  roundWin.close();
				    					  Ext.MessageBox.hide();
				    				   },
				    				   failure : function(form,action){
				    					   Ext.MessageBox.show({
				    						   title:'提示信息',
				    						   msg:action.result.msg,
				    						   buttons: Ext.Msg.OK,
				    						   modal : true,
				    						   icon: Ext.Msg.ERROR
				    					   });
				    					   return;
				    					   roundStore.loadPage(1);
				    					   roundWin.close();
				    					   //Ext.MessageBox.hide();
				    				   }
					    			});
				 				}
			                }
						},{
						    text: '关闭',
			                handler: function(){
			                	roundWin.close();
			                }
					}]
				 });
				roundWin.show();
			}
		//修改字典信息
			createUpdateDictInfo = function(){
				var row = roundGrid.getSelectionModel().getSelection();
				var dname = row[0].get('rName');
				var startTime = row[0].get('startTime');
				var endTime = row[0].get('endTime');
				var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
				var roundForm=Ext.create("Ext.form.Panel", {
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
				    items: [{
				        fieldLabel: 'ID',
				        name: 'workRound.id',
				        value:row[0].get('id'),
				        hidden:true,
				        width: 100,
				        allowBlank: true
				    }, 
				    {
				    	id:'name',
				        fieldLabel: '班次名称',
				        name: 'workRound.roundName',
				        value: row[0].get('rName'),
				        readOnly:false,
				        disabled:false,
				        hidden:false,
				        regex : new RegExp('^([\u4E00-\u9FFF]*-*\\w*)*$'),
				    	regexText : '不能包含特殊字符',
				        maxLength:25,
				        width:100,
				        allowBlank: false,
				        validator: function(value){
				        	var returnObj = null;
							if(value == dname){
								return true;
							}else{
								$.ajax({
									url : '${ctx}/workturns/validateRoundName.action',
									data:{value:value},
									cache : false,
									async : false,
									type : "POST",
									dataType : 'json',
									success : function (result){
										if(!result.valid){
											returnObj = result.reason;
										}else{
											returnObj = true;
										}
									}
								});
							}
							return returnObj;
				        }
				    },
				    {
				    	id:'startTime',
				    	fieldLabel: '开始时间',
				    	xtype:'numberfield',
				    	name: 'workRound.startTime',
				    	value: row[0].get('startTime'),
				    	maxLength:10,
				    	//minValue:0,
				    	maxValue: 24,
				    	regex:new RegExp('^[0-9]*[0-9][0-9]*$'),
				    	regexText : '只能输入非负整数！',
				    	width: 100,
				    	allowBlank: false
				    }, 
				    {
				    	id:'endTime',
				    	fieldLabel: '结束时间',
				    	xtype:'numberfield',
				    	name: 'workRound.endTime',
				    	value: row[0].get('endTime'),
				    	maxLength:10,
				    	//minValue:0,
				    	maxValue: 24,
				    	width: 100,
				    	regex:new RegExp('^[0-9]*[0-9][0-9]*$'),
				    	regexText : '只能输入非负整数！',
				    	allowBlank: false
				    	
				    }]
				 });
				var roundWin=Ext.create("Ext.window.Window",{
					title: '修改字典',
					resizable: false,
					buttonAlign:"center",
				  	height: 250,
				    width: 350,
				    modal:true,
				    layout: 'fit',
				    items: [roundForm],
				    buttons:[
				    	{
						    text: SystemConstant.saveBtnText,
					    	handler: function(){
					    		var start = Ext.getCmp('startTime').getValue();
					    		var end = Ext.getCmp('endTime').getValue();
					    		if(end < start || end == start){
					    			Ext.MessageBox.show({
			    						   title:'提示信息',
			    						   msg:"结束时间不能小于或等于开始时间！",
			    						   buttons: Ext.Msg.OK,
			    						   modal : true,
			    						   icon: Ext.Msg.INFO
			    					});
					    			return;
					    		}
					    		if(roundForm.form.isValid()){
					    			var rowId = roundGrid.getSelectionModel().getSelection()[0].get("id");
					    			Ext.MessageBox.wait("", "修改字典数据", 
											{
												text:"请稍后..."
											}
										);
										roundForm.form.submit({
											url:"${ctx}/workturns/updateWorkRound.action",
											success : function(form, action) {  
					    					   Ext.MessageBox.hide();
					    					   //清除选中行;
					    					   roundGrid.getSelectionModel().deselectAll();
					    					   //Ext.getCmp("roundGrid").getSelectionModel().clearSelections();
					    					   new Ext.ux.TipsWindow(
													{
														title: SystemConstant.alertTitle,
														autoHide: 3,
														html:action.result.msg
													}
												).show();
					    					   roundWin.close();
					    					   roundStore.loadPage(1);
					    				   },
											failure:function(form, action){
											 	Ext.MessageBox.show({
													title: SystemConstant.alertTitle,
													msg: action.result.msg,
													buttons: Ext.MessageBox.OK,
													icon: Ext.MessageBox.ERROR
												});
											 	return;
											 	Ext.getCmp("roundGrid").getSelectionModel().clearSelections();
												//Ext.MessageBox.hide();
												roundWin.close();
												roundStore.loadPage(1);
											 	return false;
											}
									});
			 					}
		          			}
						},
						{
					    text: '关闭',
			                handler: function(){
			                	roundWin.close();
			                }
						}
					]
				 });
				 roundWin.show();
			}
			 Ext.create("Ext.container.Viewport", {
				layout: "border",
				border: true,
				renderTo: Ext.getBody(),
				items: [roundGrid]
			});
		});
	</script>
</body>
</html>