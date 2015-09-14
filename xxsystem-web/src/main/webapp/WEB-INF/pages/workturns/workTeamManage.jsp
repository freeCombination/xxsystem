<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/doc_type.jsp"%>
<html>
<head> 
<%@include file="../common/meta.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/css.jsp"%>
<%@include file="../common/ext.jsp"%>
<script type="text/javascript" src="${ctx}/scripts/extjs/ux/TreePicker.js"></script>
<title>班组管理</title>
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
		var selectNode=null;
		//组织id
		var orgId;
		//组织名称
		var orgName;
		//班组id
		var workTeamId;
		var nodeId;
		
		Ext.onReady(function() {
			Ext.QuickTips.init();
			//自动引入其他需要的js
			Ext.require(["Ext.container.*",
			             "Ext.grid.*", 
			             "Ext.toolbar.Paging", 
			             "Ext.form.*",
						 "Ext.data.*"]);
			
		 	var treeStore = Ext.create("Ext.data.TreeStore", {
		        proxy: {
		            type: "ajax",
		            actionMethods: {
		                read: 'POST'
		            },
		            url: "${ctx}/org/getOrgTreeList.action"
		        },
		        root: {
		        	text:"组织树形展示",
		        	nodeId:"0"
		        },
		        listeners: {
                    beforeload: function (ds, opration, opt) {
                        opration.params.parentId = opration.node.raw.nodeId;
                        opration.params.permissionFlag = 'true';
                    }
                }
		    });
			
			var treePanel=Ext.create("Ext.tree.Panel", {
		 		title:'组织信息',
		       	store: treeStore,
		     	id:"typeTree",
		        height: document.body.clientHeight,
		        width: 200,
		        useArrows: true,
		        rootVisible : false,
				region: "west",
				border: false,
				collapsible: true,
        		split: true,
				collapseMode:"mini",
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
		        		treePanel.expandPath(path);
		        	}
		        }
		    });
			
			treeStore.on("load",function(store, node, record){
				if(node != null && node.raw.nodeId == "0" && node.firstChild){
					treePanel.getSelectionModel().select(node.firstChild,true);
					treePanel.fireEvent("itemclick",treePanel.getView(),node.firstChild);
				}
			});
	
			treePanel.on("itemclick",function(view,record,item,index,e,opts){  
			     //获取当前点击的节点  
			      selectNode=record;
			      var treeNode=record.raw;  
			      var text=treeNode.text;
			      var nodeId = treeNode.nodeId;
			      orgId = nodeId;
			      orgName=text;
			      var proxy = workTeamStore.getProxy();
			      proxy.setExtraParam("orgId",nodeId);
			      workTeamStore.loadPage(1);
		 	}); 
			
			//treePanel.getRootNode().expand(true);
		 	
		 	Ext.define("WorkTeam",{
				extend:"Ext.data.Model",
				fields:[
					{name: "id",mapping:"workTeamId"}, 
					{name: "workTeamName",mapping:"workTeamName"}, 
					{name: "orgName",mapping:"orgName"}, 
					{name: "orgId",mapping:"orgId",type:'int'}, 
					{name: "createDate",mapping:"createDate"}, 
					{name: "remark",mapping:"remark"},
					{name: "monitorId",mapping:"monitorName"}
				]
			});
			
			var teamColumns=[
					{header:"序号",xtype: "rownumberer",width:60,align:"center",menuDisabled: true,sortable :false},
					{header: "ID",width: 70,dataIndex: "id",hidden: true,menuDisabled: true,sortable :false},
					{header: "组织编号",width: 70,dataIndex: "orgId",hidden: true,menuDisabled: true,sortable :false},
					{header: "班组名称",width: 200,dataIndex: "workTeamName",menuDisabled: true,sortable :false},
		            {header: "组织名称",width: 200,dataIndex: "orgName",menuDisabled: true,sortable :false},
		            {header: "创建时间",width: 200,dataIndex: "createDate",menuDisabled: true,sortable :false},
		            {header: "备注",width: 200,dataIndex: "remark",menuDisabled: true,sortable :false},
		            {header: "班组成员管理",
		            	sortable : false,
		            	menuDisabled: true,
		                xtype : 'actioncolumn',
		                width:160,
		                align : 'center',
		                items : [{
		                    icon : '${ctx}/images/icons/collapse-all.gif',
		                    tooltip : '班组成员管理',
		                    handler : function(grid, rowIndex, colIndex){
		                    	var teamId = workTeamStore.getAt(rowIndex).get("id");
		                    	var orgId = workTeamStore.getAt(rowIndex).get("orgId");
		                    	workTeamMenberManage(teamId,orgId);
		                    }
		                }]
		            }
		         ];
			
			//建立数据Store
			var workTeamStore=Ext.create("Ext.data.Store", {
		        pageSize: SystemConstant.commonSize,
		        model:"WorkTeam",
		        remoteSort:true,
				proxy: {
		            type:"ajax",
		            actionMethods: {
	                	read: 'post'
	           		},
				    url: "${ctx}/workteam/getAllWorkTeam.action",
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
			        	var c = workTeamGrid.getSelectionModel().getSelection();
						 	if(c.length > 0){
								Ext.getCmp('delWorkTeamBtn').setDisabled(false);
						 	}else{
							 	Ext.getCmp('delWorkTeamBtn').setDisabled(true);
						 	}
						 	if(c.length > 0 && c.length < 2){
							 	Ext.getCmp('updateWorkTeamBtn').setDisabled(false);
						 	}else{
							 	Ext.getCmp('updateWorkTeamBtn').setDisabled(true);
						 	}
			      	}
				}
		    });
			
			//grid组件
			var workTeamGrid =  Ext.create("Ext.grid.Panel",{
				title:'班组管理',
				border:false,
				columnLines: true,
				layout:"fit",
				region: "center",
				width: "100%",
				height: document.body.clientHeight,
				id: "workTeamGrid",
				bbar:  Ext.create("Ext.PagingToolbar", {
					store: workTeamStore,
					displayInfo: true,
					displayMsg: SystemConstant.displayMsg,
					emptyMsg: SystemConstant.emptyMsg
				}),
				columns:teamColumns,
		        selModel:sm,
		     	forceFit : true,
				store: workTeamStore,
				autoScroll: true,
				stripeRows: true,
				 tbar: ['名称：',
				{
					id: 'workTeamName',
					width: 150,   
 					labelWidth: 70,   
					xtype: 'textfield'
				},'&nbsp;',{
					id:'searchDicBtn',
					xtype:'button',
					disabled:false,
					text:'查询',
					iconCls:'search-button',
					handler:function(){
						var proxy = workTeamStore.getProxy();
						proxy.setExtraParam("workTeamName",Ext.getCmp("workTeamName").getValue());
						workTeamStore.loadPage(1);
					}
				},'->',
				{
					id:'addWorkTeamBtn',
					xtype:'button',
					disabled:false,
					text:'添加',
					hidden:true,
					iconCls:'add-button',
					handler:function(){
						addWorkTeamInfo();
					}
				},
				{
					id:'updateWorkTeamBtn',
					xtype:'button',
					text:'修改',
					disabled:true,
					hidden:true,
					iconCls:'edit-button',
					handler:function(){
						updateWorkTeamInfo();
					}
				},
				{
					id:'delWorkTeamBtn',
					xtype:'button',
					text:'删除',
					disabled:true,
					hidden:true,
					iconCls:'delete-button',
					handler:function(){
						delWorkTeamInfo();
					}
				}],
				listeners:{
					'render':function(){
						for(var i = 0;i < userPermissionArr.length;i++){
							if("workteam_add_btn" == userPermissionArr[i].name){
								Ext.getCmp('addWorkTeamBtn').setVisible(true);
							}
							if("workteam_update_btn" == userPermissionArr[i].name){
								Ext.getCmp('updateWorkTeamBtn').setVisible(true);
							}
							if("workteam_delete_btn" == userPermissionArr[i].name){
								Ext.getCmp('delWorkTeamBtn').setVisible(true);
							}
						}
					}
				}
			});		
			
			//添加
			addWorkTeamInfo=function(){
				var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
				var workTeamForm=Ext.create("Ext.form.Panel",{
					layout: 'form',
					bodyStyle :'padding:15px 20px',
					border: false,
					labelAlign: 'right',
					fieldDefaults: {
			            labelWidth: 80,
			        	labelAlign: 'right'
			        },
			        defaults: {
				        anchor: '60%'
				    },
				    defaultType: 'textfield',
				    items:[{
				    	fieldLabel:'班组名称',
	                    id:'workTeamNameText',
	                    name:'workTeam.workTeamName',
	                    maxLength: 20,
	                    maxLenghtText:'用户名超出长度限制20',
	                    blankText:'班组名称不能为空',
	                    regex : /^[\w.\-\u4e00-\u9fa5]*$/,
	                    regexText : "输入内容不能包含空格和特殊字符！",
	                    validationDelay:500,
	                    allowBlank:false,
	                    validator : function(value){
	                    	var returnFlag = true;
	                    	var returnStr = "";
	                    	if(value!=""){
	                    		$.ajax({
		                            data:{value:value,orgId:orgId},
		                            url : '${ctx}/workteam/validateAddWorkTeamName.action',
		                            cache : false,
		                            async : false,
		                            type : "post",
		                            dataType : 'json',
		                            success : function (result){
		                                if(result.success){
		                                    returnStr = result.msg;
		                                }else{
		                                    returnFlag = false;
		                                }
		                            }
		                        });
	                    	}
	                    	
	                    	if(returnFlag){
	                    		return returnStr;
	                    	}else{
	                    		return true;
	                    	}
	                    }
	                    
				    },{
	                    fieldLabel:'组织名称',
	                    xtype:'treepicker',
	                    id:'workTeamOrgNameText',
	                    name:'workTeam.orgId',
	    				displayField:'text',
	    				//blankText:'组织名称不能为空',
	    				forceSelection:true,
	    				//allowBlank:false,
	    				listeners:{
	    					select:function(){
	    						//orgId = Ext.getCmp('workTeamOrgNameText').getValue();
    	                    	Ext.getCmp("workTeamNameText").validate();
    	                    },
    	                    render:function(){
    				    		Ext.getCmp('workTeamOrgNameText').getStore().getRootNode().expand(true,function(){
    				    			Ext.getCmp('workTeamOrgNameText').setValue(orgId + '');
    				    		});
    				    	}

	    				},
	    				store:Ext.create('Ext.data.TreeStore',{
	    					root:{
	    						text:'',
	    						nodeId:'0'
	    					},
	    					proxy:{
	    						type:'ajax',
	    						url:'${ctx}/org/getOrgTreeList.action',
	    						reader:{
	    							type:'json'
	    						}
	    					},
	    			        listeners: {
	    	                    beforeload: function (ds, opration, opt) {
	    	                        opration.params.parentId = opration.node.raw.nodeId;
	    	                        opration.params.permissionFlag = 'true';
	    	                    }
	    	                }
	    				})
	                    
				    },{
				    	fieldLabel:'备注',
	                    id:'workTeamRemarkText',
	                    name:'workTeam.remark',
	                    maxLength: 500,
	                    maxLenghtText:'备注超出长度限制500',
// 	                    regex : /^[\w.\-\u4e00-\u9fa5]*$/,
// 	                    regexText : "输入内容不能包含空格和特殊字符！",
						regex : /^[\w.\- , ，。.\u4e00-\u9fa5]*$/,
	                    regexText : "输入内容不能包含特殊字符！",
	                    xtype : 'textareafield',
	                    allowBlank:true
				    }],
				    listeners:function(){
				    	
				    }
				});
				
				var workTeamWin = Ext.create("Ext.window.Window",{
					title: '添加班组',
					resizable: false,
					buttonAlign:"center",
				  	height: 200,
				    width: 450,
				    modal:true,
				    layout: 'fit',
				    items: [workTeamForm],
				    buttons:[{
				    	text: SystemConstant.saveBtnText,
				    	handler: function(){
				    		if(workTeamForm.form.isValid()){
				    			workTeamForm.form.submit({
				    				url:"${ctx}/workteam/addWorkTeamInfo.action",
			    				   success : function(form, action) {
			    					   Ext.getCmp("workTeamGrid").getSelectionModel().clearSelections();
			    					   new Ext.ux.TipsWindow(
											{
												title: SystemConstant.alertTitle,
												autoHide: 3,
												html:action.result.msg
											}
									).show();
			    					  workTeamStore.loadPage(1); 
			    					  workTeamWin.close();
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
			    					   workTeamStore.loadPage(1);
			    					   workTeamWin.close();
			    					   Ext.MessageBox.hide();
			    				   }
				    			});
				    		}
				    	}
				    },{
				    	text: '关闭',
		                handler: function(){
		                	workTeamWin.close();
		                }
				    }]
				}).show();
			};
		
			//修改
			updateWorkTeamInfo = function(){
				var row = Ext.getCmp('workTeamGrid').getSelectionModel().getSelection();
				var workTeamName = row[0].get("workTeamName");
				var teamId = row[0].get("id");
				nodeId = row[0].get("orgId");
				var teamForm=Ext.create("Ext.form.Panel",{
					layout: 'form',
					bodyStyle :'padding:15px 20px',
					border: false,
					labelAlign: 'right',
					fieldDefaults: {
			            labelWidth: 80,
			        	labelAlign: 'right'
			        },
			        defaults: {
				        anchor: '60%'
				    },
				    defaultType: 'textfield',
				    items:[{
				    	xtype:'hidden',
	                    id:'id',
	                    name:'workTeam.workTeamId',
	                    value:row[0].data.id
				    },{
				    	fieldLabel:'班组名称',
	                    id:'workTeamNameText',
	                    name:'workTeam.workTeamName',
	                    maxLength: 20,
	                    maxLenghtText:'用户名超出长度限制20',
	                    blankText:'班组名称不能为空',
	                    regex : /^[\w.\-\u4e00-\u9fa5]*$/,
	                    regexText : "输入内容不能包含空格和特殊字符！",
	                    validationDelay:500,
	                    allowBlank:false,
	                    value:row[0].get("workTeamName"),
	                    validator : function(value){
	                    	var returnFlag = true;
	                    	var returnStr = "";
	                    	if(orgId==nodeId && orgName==value){
	                    		return true;
	                    	}
	                    	if(value!=""){
	                    		$.ajax({
		                            data:{value:value,orgId:orgId,teamId:teamId},
		                            url : '${ctx}/workteam/validateAddWorkTeamName.action',
		                            cache : false,
		                            async : false,
		                            type : "POST",
		                            dataType : 'json',
		                            success : function (result){
		                                if(result.success){
		                                    returnStr = result.msg;
		                                }else{
		                                    returnFlag = false;
		                                }
		                            }
		                        });
	                    	}
	                    	
	                    	if(returnFlag){
	                    		return returnStr;
	                    	}else{
	                    		return true;
	                    	}
	                    }
				    },{
	                    
	                    fieldLabel:'组织名称',
	                    xtype:'treepicker',
	                    id:'workTeamOrgNameText',
	                    name:'workTeam.orgId',
	    				displayField:'text',
	    				forceSelection:true,
	    				listeners:{
	    					select:function(){
	    						//orgId = Ext.getCmp('workTeamOrgNameText').getValue();
    	                    	Ext.getCmp("workTeamNameText").validate();
    	                    },
    	                    afterrender:function(){
					    		Ext.getCmp('workTeamOrgNameText').getStore().getRootNode().expand(true,function(){
					    			Ext.getCmp('workTeamOrgNameText').setValue(row[0].get("orgId") + '');
					    		});
					    	}
	    				},
	    				store:Ext.create('Ext.data.TreeStore',{
	    					root:{
	    						text:'',
	    						nodeId:'0'
	    					},
	    					proxy:{
	    						type:'ajax',
	    						url:'${ctx}/org/getOrgTreeList.action',
	    						reader:{
	    							type:'json'
	    						}
	    					},
	    			        listeners: {
	    	                    beforeload: function (ds, opration, opt) {
	    	                        opration.params.parentId = opration.node.raw.nodeId;
	    	                        opration.params.permissionFlag = 'true';
	    	                    }
	    	                }
	    				})
	                    
				    },{
				    	fieldLabel:'备注',
	                    id:'workTeamRemarkText',
	                    name:'workTeam.remark',
	                    maxLength: 500,
	                    maxLenghtText:'备注超出长度限制500',
// 	                    regex : /^[\w.\-\u4e00-\u9fa5]*$/,
// 	                    regexText : "输入内容不能包含空格和特殊字符！",
						regex : /^[\w.\- , ，。.\u4e00-\u9fa5]*$/,
	                    regexText : "输入内容不能包含特殊字符！",
	                    allowBlank:true,
	                    xtype : 'textareafield',
	                    value:row[0].get("remark")
				    }]
				});
				
				var teamWin = Ext.create("Ext.window.Window",{
					title: '修改班组',
					resizable: false,
					buttonAlign:"center",
				  	height: 200,
				    width: 450,
				    modal:true,
				    layout: 'fit',
				    items: [teamForm],
				    buttons:[{
				    	text: SystemConstant.saveBtnText,
				    	handler: function(){
				    		
				    		if(teamForm.form.isValid()){
				    			teamForm.form.submit({
				    				params:{nodeId:nodeId,teamId:teamId},
				    				url:"${ctx}/workteam/updateWorkTeamInfo.action",
			    				   success : function(form, action) {
			    					   workTeamGrid.getSelectionModel().deselectAll();
			    					   Ext.getCmp("workTeamGrid").getSelectionModel().clearSelections();
			    					   new Ext.ux.TipsWindow(
											{
												title: SystemConstant.alertTitle,
												autoHide: 3,
												html:action.result.msg
											}
									).show();
			    					  workTeamStore.loadPage(1); 
			    					  teamWin.close();
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
			    					   workTeamStore.loadPage(1);
			    					   teamWin.close();
			    					   Ext.MessageBox.hide();
			    				   }
				    			});
				    		}
				    		
				    	}
				    },{
				    	text: '关闭',
		                handler: function(){
		                	teamWin.close();
		                }
				    }]
				}).show();
			};
			
			//删除
			delWorkTeamInfo = function(){
				var ids = Ext.getCmp('workTeamGrid').getSelectionModel().getSelection();
				var array = new Array();
				for(var i=0; i<ids.length; i++){
					array.push(ids[i].data.id);
				}
				var id = array.toString();
				
				Ext:Ext.Msg.confirm(SystemConstant.alertTitle,"确认删除数据吗？",function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							url : '${ctx}/workteam/delWorkTeam.action',
							params : {ids: id},
							success : function(res, options) {
								workTeamGrid.getSelectionModel().deselectAll();
								Ext.getCmp("workTeamGrid").getSelectionModel().clearSelections();
								var result = Ext.decode(res.responseText);
								if(result.success){
									new Ext.ux.TipsWindow({
                            		    title: SystemConstant.alertTitle,
                            		    autoHide: 3,
                            		    html:result.msg
                            	    }).show();
								}else{
									Ext.MessageBox.show({
										title: SystemConstant.alertTitle,
										msg: result.msg,
										buttons: Ext.MessageBox.OK,
										icon: Ext.MessageBox.ERROR
									});
								}
								workTeamStore.loadPage(1);
							}
						});
					}
				});
			};
			
			Ext.define("UserVo",{
				extend:"Ext.data.Model",
				fields:[
					{name: "userId",mapping:"userId"}, 
					{name: "realname",mapping:"realname"}, 
					{name: "erpId",mapping:"erpId"}, 
					{name: "mobileNo1",mapping:"mobileNo1"}
				]
			});
			
			//班组管理
			workTeamMenberManage = function(teamId,orgId){
				
				var teamColumns=[
					{header:'userId', dataIndex:'userId' , hidden:true, sortable:false},
	            	{header:'姓名', dataIndex:'realname' ,menuDisabled: true, sortable:false, width:40},
	            	{header:'ERP编码', dataIndex:'erpId' ,menuDisabled: true,sortable:false,width:40},
	            	{header:'电话号码', dataIndex:'mobileNo1',menuDisabled: true,sortable:false,width: 40}
		         ];
				
				//行选择模型
				var sm=Ext.create("Ext.selection.CheckboxModel",{
					showHeaderCheckbox :true,
					injectCheckbox:1,
			    	listeners: {
				      	selectionchange: function(){
				        	var c = workTeamMenberGrid.getSelectionModel().getSelection();
							 	if(c.length > 0){
									Ext.getCmp('delworkTeamMenberBtn').setDisabled(false);
							 	}else{
								 	Ext.getCmp('delworkTeamMenberBtn').setDisabled(true);
							 	}
				      	}
					}
			    });
				
				//建立数据Store
				workTeamMenberStore=Ext.create("Ext.data.Store", {
			        pageSize: SystemConstant.commonSize,
			        model:"UserVo",
			        remoteSort:true,
					proxy: {
			            type:"ajax",
			            extraParams:{teamId:teamId},
					    url: "${ctx}/workteam/getWorkTeamMember.action",
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
				
				//grid组件
				workTeamMenberGrid =  Ext.create("Ext.grid.Panel",{
					border:false,
					columnLines: true,
					id:'workTeamMenberGrid',
					columns:teamColumns,
					selModel:sm,
					forceFit : true,
					store: workTeamMenberStore,
					autoScroll: true,
					stripeRows: true,
					bbar:  Ext.create("Ext.PagingToolbar", {
						store: workTeamMenberStore,
						displayInfo: true,
						displayMsg: SystemConstant.displayMsg,
						emptyMsg: SystemConstant.emptyMsg
					}),
					tbar:['->',{
						id:'addworkTeamMenberBtn',
						xtype:'button',
						disabled:false,
						text:'添加',
						iconCls:'add-button',
						handler:function(){
							addWorkTeamMenber(teamId,orgId);
						}
					},
					{
						id:'delworkTeamMenberBtn',
						xtype:'button',
						text:'删除',
						disabled:true,
						iconCls:'delete-button',
						handler:function(){
							delWorkTeamMenber(teamId,orgId);
						}
					}]
				});
							
				var teamWin = Ext.create("Ext.window.Window",{
					title: '班组成员',
					id:'workTeamMemberWin',
					resizable: false,
				  	height: 400,
				    width: 600,
				    layout: 'fit',
				    modal:true,
					buttonAlign:"center",
				    items:[workTeamMenberGrid],
				    buttons:[{
				    	text: '关闭',
		                handler: function(){
		                	teamWin.close();
		                }
				    }]
				}).show();
				
				workTeamMenberStore.loadPage(1);
			};
			
			//删除工作组
			delWorkTeamMenber = function(teamId,orgId){
				var rows = workTeamMenberGrid.getSelectionModel().getSelection();
				var ids = '';
				for(var i = 0; i < rows.length; i++){
	    			if(i < rows.length-1){
	    				ids+=rows[i].get("userId")+",";
	    			}else {
	    				ids+=rows[i].get("userId");
	    			}
	    		}
	    		
	    		if(ids != ""){
	    			Ext.Msg.confirm('系统提示','你确定要删除这'+rows.length+'条数据',function(btn){
	    				if(btn=='yes'){
	    					Ext.Ajax.request({
	    						url:'${ctx}/workteam/delTeamMember.action',
	    						method:'post',
                                params: {teamId:teamId,ids:ids},
	    						success:function(res){
	    							Ext.getCmp("workTeamMenberGrid").getSelectionModel().clearSelections();
	    							var data = Ext.decode(res.responseText);
	    							new Ext.ux.TipsWindow({
	                            		  title: SystemConstant.alertTitle,
	                            		  autoHide: 3,
	                            		  html:data.msg
	                            	  }).show();
	    							
	    							var proxy = workTeamMenberStore.getProxy();
	    							proxy.setExtraParam("teamId",teamId);
	    							workTeamMenberStore.loadPage(1);
	    						},
	    						
	    						failure: function(){
	    							var data = Ext.decode(res.responseText);
	    							Ext.MessageBox.show({
										title:'提示信息',
										msg:data.msg,
										buttons: Ext.Msg.YES,
										modal : true,
										icon: Ext.Msg.ERROR
			    					});
                                }
	    						
	    					});
	    				}
	    				
	    			});
	    		}    		    		
			};
			
			//添加工作组
			addWorkTeamMenber = function(teamId,orgId){	
				
				var teamColumns=[
						{header:'id', dataIndex:'userId' , hidden:true, sortable:false},
		            	{header:'姓名', dataIndex:'realname' ,menuDisabled: true, sortable:false, width:40},
		            	{header:'ERP编码', dataIndex:'erpId' ,menuDisabled: true,sortable:false,width:40},
		            	{header:'电话号码', dataIndex:'mobileNo1',menuDisabled: true,sortable:false,width: 40}
		         ];
					
				workTeamUserStore = Ext.create("Ext.data.Store", {
			        pageSize: SystemConstant.commonSize,
			        model:"UserVo",
			        remoteSort:true,
					proxy: {
			            type:"ajax",
			            extraParams:{teamId:teamId,orgId:orgId},
					    url: "${ctx}/workteam/getDeptMember.action",
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
			    			var rows = workTeamUserGrid.getSelectionModel().getSelection();
			    			if(rows.length > 0){
								Ext.getCmp('addTeamUser').setDisabled(false);
						 	}else{
					    		Ext.getCmp('addTeamUser').setDisabled(true);
						 	}
				      	}
					}
			    });
				
				var workTeamUserGrid = Ext.create("Ext.grid.Panel",{
					border:false,
					columnLines: true,
					layout:'fit',
					region:'center',
					width:'100%',
					id:'workTeamUserGrid',
					columns:teamColumns,
					selModel:sm,
					forceFit : true,
					store: workTeamUserStore,
					autoScroll: true,
					stripeRows: true,
					bbar:  Ext.create("Ext.PagingToolbar", {
						store: workTeamUserStore,
						displayInfo: true,
						displayMsg: SystemConstant.displayMsg,
						emptyMsg: SystemConstant.emptyMsg
					})
				});
								
				var teamWin = Ext.create("Ext.window.Window",{
					title: '添加班组成员',
					resizable: false,
					buttonAlign:"center",
				  	height: 400,
				    width: 500,
				    modal:true,
				    items:[workTeamUserGrid],
				    layout: 'fit',
				    buttons:[{
				    	text:'确定',
				    	id:'addTeamUser',
				    	disabled:true,
				    	handler:function(){
				    		var rows = workTeamUserGrid.getSelectionModel().getSelection();
				    		var ids = '';
				    		for(var i = 0; i < rows.length; i++){
				    			if(i < rows.length-1){
				    				ids+=rows[i].get("userId")+",";
				    			}else {
				    				ids+=rows[i].get("userId");
				    			}
				    		}
				    		
				    		if(ids != ""){
				    			Ext.Ajax.request({
		                              url: '${ctx}/workteam/addDeptMember.action',
		                              method:'post',
		                              params: {teamId:teamId,ids:ids,orgId:orgId},
		                              success: function(res){
		                            	  Ext.getCmp("workTeamMenberGrid").getSelectionModel().clearSelections();
		                            	  var data = Ext.decode(res.responseText);
		                            	  new Ext.ux.TipsWindow({
		                            		  title: SystemConstant.alertTitle,
		                            		  autoHide: 3,
		                            		  html:data.msg
		                            	  }).show();
		                            	  teamWin.close();
		                            	  var proxy = workTeamMenberStore.getProxy();
			    						  proxy.setExtraParam("teamId",teamId);
			    						  workTeamMenberStore.loadPage(1);
		                            	  
									},failure : function(res){
										var data = Ext.decode(res.responseText);
										Ext.MessageBox.show({
											title:'提示信息',
											msg:data.msg,
											buttons: Ext.Msg.YES,
											modal : true,
											icon: Ext.Msg.ERROR
				    					});
										teamWin.close();
										var proxy = workTeamMenberStore.getProxy();
		    							proxy.setExtraParam("teamId",teamId);
		    							workTeamMenberStore.loadPage(1);
									}
				    			});
				    		}				    					    		
				    	}
				    },{
				    	text: '关闭',
		                handler: function(){
		                	teamWin.close();
		                }
				    }]
				}).show();
				
				var proxy = workTeamUserStore.getProxy();
				proxy.setExtraParam("orgId",orgId);
				proxy.setExtraParam("teamId",teamId);
				workTeamUserStore.loadPage(1);
			};
			
			 Ext.create("Ext.container.Viewport", {
				layout: "border",
				border: true,
				renderTo: Ext.getBody(),
				items: [treePanel,workTeamGrid]
			});
		});
		
	</script>

</body>
</html>