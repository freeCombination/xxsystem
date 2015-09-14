<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/doc_type.jsp"%>
<html>
<head>
<%@include file="../common/meta.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/css.jsp"%>
<%@include file="../common/ext.jsp"%>
<meta http-equiv="Content-Type" content="UTF-8" />

<title></title>
<script type="text/javascript" src="${ctx}/scripts/workround/workRulePlan.js"></script>

<script type="text/javascript" src="${ctx}/scripts/workround/workRoundPlan.js"></script>
<script type="text/javascript" src="${ctx}/scripts/workround/workRulePlanDetail.js"></script>
<script type="text/javascript" src="${ctx}/scripts/extjs/ux/TreePicker.js"></script>
<style type="text/css">
  .x-form-layout-table{
	table-layout: fixed;
  }
</style>

<script type="text/javascript">
	var userPermissionArr = new Array();
	<c:forEach items="${userPermission}" var="v">
	var obj = new Object();
	obj.value = '${v.resourceId }';
	obj.name = '${v.code }';
	userPermissionArr.push(obj);
	</c:forEach>
</script>
<script type="text/javascript">
	var org_id;
	var org_id_validate;
	var ruleNameForValidate = "";
	var period = 0;
	
	Ext.onReady(function() {
	//主页面左侧
	var treeStore = Ext.create("Ext.data.TreeStore",{
		proxy : {
			type : "ajax",
			actionMethods : {
				read : 'POST'
			},
			url : "${ctx}/org/getOrgTreeList.action"
		},
		root : {
			text : "组织树形展示",
			nodeId : "0"
		},
		listeners : {
			beforeload : function(ds, opration, opt) {
				opration.params.parentId = opration.node.raw.nodeId;
				opration.params.permissionFlag = 'true';
			}
		}
	});
				

	var treePanel = Ext.create("Ext.tree.Panel", {
		title : '组织信息',
		store : treeStore,
		id : "typeTree",
		height : document.body.clientHeight,
		width : 200,
		useArrows : true,
		rootVisible : false,
		region : "west",
		border : false,
		collapsible : true,
		split : true,
		collapseMode : "mini",
		dockedItems : [ {
			xtype : 'toolbar',
			style : "border-top:0px;border-left:0px",
			items : [ {
				iconCls : "icon-expand-all",
				text : '展开',
				tooltip : "展开所有",
				handler : function() {
					treePanel.expandAll();
				},
				scope : this
			}, {
				iconCls : "icon-collapse-all",
				text : '折叠',
				tooltip : "折叠所有",
				handler : function() {
					treePanel.collapseAll();
				},
				scope : this
			} ]
		} ],
		listeners : {
			'itemclick' : function(treeObj, record, item, index) {
				roleGrid.getSelectionModel().deselectAll();
				var treeNode = record.raw;
				org_id = treeNode.nodeId;
				org_id_validate = treeNode.nodeId;
				
				var proxy = roleStore.getProxy();
				proxy.setExtraParam("orgId",org_id);
				roleStore.loadPage(1);
			},
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
	
// 	treePanel.getRootNode().expand(true);
	//以上都是树

	Ext.QuickTips.init();
	//自动引入其他需要的js
	Ext.require([ 
	    "Ext.container.*",
	    "Ext.grid.*",
		"Ext.toolbar.Paging",
		"Ext.form.*",
		"Ext.data.*" 
	]);
	
	//建立Model模型对象
	Ext.define("WorkTurnsRule", {
		extend : "Ext.data.Model",
		fields : [ {
			name : "id",
			mapping : "id"
		}, {
			name : "rolename",
			mapping : "name"
		}, {
			name : "roleuser",
			mapping : "user"
		}, {
			name : "rolemakeDate",
			mapping : "makeDate"
		}, {
			name : "rolecycleDays",
			mapping : "cycleDays"
		}, {
			name : "roleremarks",
			mapping : "remarks"
		} , {
			name : "orgName",
			mapping : "orgName"
		}]
	});

	//建立数据Store
	var roleStore = Ext.create("Ext.data.Store",{
		pageSize : SystemConstant.commonSize,
		model : "WorkTurnsRule",
		remoteSort : true,
		proxy : {
			type : "ajax",
			actionMethods:{
				read:'POST'
			},
			url : "${ctx}/workturnsrule/getWorkTurnsRuleList.action",
			reader : {
				totalProperty : "totalSize",
				root : "list"
			},
			simpleSortMode : true
		},
		sorters : [ {
			property : "id",
			direction : "ASC"
		}]
	});

	//行选择模型
	var sm = Ext.create("Ext.selection.CheckboxModel",{
		showHeaderCheckbox : true,
		injectCheckbox : 1,
		listeners : {
			selectionchange : function() {
				var c = roleGrid.getSelectionModel().getSelection();
				if (c.length > 0) {
					Ext.getCmp('delRuleBtn').setDisabled(false);
				} else {
					Ext.getCmp('delRuleBtn').setDisabled(true);
				}
				if (c.length == 1) {
					Ext.getCmp('updateRuleBtn').setDisabled(false);
				} else {
					Ext.getCmp('updateRuleBtn').setDisabled(true);
				}
			}
		}
	});
	var cm = [ {
		header : "序号",
		xtype : "rownumberer",
		width : 60,
		align : "center",
		menuDisabled : true,
		sortable : false
	}, {
		header : "ID",
		width : 70,
		dataIndex : "id",
		hidden : true,
		menuDisabled : true,
		sortable : false
	}, {
		header : "规则名称",
		width : 200,
		dataIndex : "rolename",
		menuDisabled : true,
		sortable : false
	}, {
		header : "组织",
		width : 200,
		dataIndex : "orgName",
		menuDisabled : true,
		sortable : false
	},{
		header : "制定人",
		width : 200,
		dataIndex : "roleuser",
		menuDisabled : true,
		sortable : false
	}, {
		header : "制定时间",
		width : 200,
		dataIndex : "rolemakeDate",
		menuDisabled : true,
		sortable : false
	}, {
		header : "周期",
		width : 200,
		dataIndex : "rolecycleDays",
		menuDisabled : true,
		sortable : false
	}, {
		header : "描述",
		width : 200,
		dataIndex : "roleremarks",
		menuDisabled : true,
		sortable : false
	}, {
		header : "倒班规则详细",
		sortable : false,
		width : 20,
		xtype : 'actioncolumn',
		width : 200,
		align : 'center',
		items : [ {
			icon : '${ctx}/images/icons/collapse-all.gif',
			tooltip : '倒班规则详细',
			handler : function(grid, rowIndex, colIndex) {
				var record=grid.getStore().getAt(rowIndex);
                var id=record.get("id");
                var name=record.get("rolename");
                var cycleDays=record.get("rolecycleDays");
				getRuleDetial(id,cycleDays,name,org_id);
			}
		} ]
	}, {
		header : "倒班计划详细",
		sortable : false,
		width : 20,
		xtype : 'actioncolumn',
		width : 200,
		align : 'center',
		items : [ {
			icon : '${ctx}/images/icons/collapse-all.gif',
			tooltip : '倒班计划详细',
			handler : function(grid, rowIndex, colIndex) {
				var record=grid.getStore().getAt(rowIndex);
                var id=record.get("id");
                var name=record.get("rolename");
                var cycleDays=record.get("rolecycleDays");
                getPlanDetial(id,cycleDays,name,org_id);
				
			}
		} ]
	}, {
		header : "生成倒班计划",
		sortable : false,
		width : 20,
		xtype : 'actioncolumn',
		width : 200,
		align : 'center',
		items : [ {
			icon : '${ctx}/images/icons/collapse-all.gif',
			tooltip : '生成倒班计划',
			handler : function(grid, rowIndex, colIndex) {
				var record=grid.getStore().getAt(rowIndex);
                var id=record.get("id");
                var cycleDays=record.get("rolecycleDays");
                var name = record.get("rolename");
				getRuleInfomation(id,cycleDays,name,org_id);
			}
		} ]
	} ];

	//grid组件
	var roleGrid = Ext.create("Ext.grid.Panel",{
		title : '倒班规则管理',
		border : false,
		columnLines : true,
		layout : "fit",
		region : "center",
		width : "100%",
		height : document.body.clientHeight,
		id : "roleGrid",
		bbar : Ext.create("Ext.PagingToolbar", {
			store : roleStore,
			displayInfo : true,
			displayMsg : SystemConstant.displayMsg,
			emptyMsg : SystemConstant.emptyMsg
		}),
		columns : cm,
		selModel : sm,
		forceFit : true,
		store : roleStore,
		autoScroll : true,
		stripeRows : true,
		tbar : [
			'规则名称：',
			{
				id : 'roelName',
				width : 150,
				labelWidth : 70,
				xtype : 'textfield'
			},
			'&nbsp;',
			{
				id : 'searchDicBtn',
				xtype : 'button',
				disabled : false,
				text : '查询',
				iconCls : 'search-button',
				handler : function() {
					var proxy = roleStore.getProxy();
					proxy.setExtraParam("name",Ext.getCmp("roelName").getValue());
					proxy.setExtraParam("orgId",org_id);
					roleStore.loadPage(1);
				}
			},
			'->',
			{
				id : 'addRuleBtn',
				xtype : 'button',
				disabled : false,
				text : '添加',
				hidden : true,
				iconCls : 'add-button',
				handler : function() {
					createAddRoleInfo();
				}
			},
			{
				id : 'updateRuleBtn',
				xtype : 'button',
				text : '修改',
				disabled : true,
				hidden : true,
				iconCls : 'edit-button',
				handler : function() {
					createUpdateRoleInfo();
				}
			},
			{
				id : 'delRuleBtn',
				xtype : 'button',
				text : '删除',
				disabled : true,
				hidden : true,
				iconCls : 'delete-button',
				handler : function() {
					var ck = Ext.getCmp('roleGrid').getSelectionModel().getSelection();
					var itemsArray = new Array();
					for (var i = 0; i < ck.length; i++) {
						itemsArray.push(ck[i].data.id);
					}
					var idss = itemsArray.toString();
					Ext.Ajax.request({
						url : '${ctx}/workturnsrule/checkRoleIsExist.action',
						params : {
							ids : idss
						},
						success : function(res,options) {
						var data = Ext.decode(res.responseText);
						if (data.success) {
							Ext.Msg.confirm(SystemConstant.alertTitle,"确认删除倒班规则吗？",
							function(btn) {
								if (btn == 'yes') {
									Ext.Ajax.request({
										url : '${ctx}/workturnsrule/deleteWorkTurnsRule.action',
										params : {
											ids : idss
										},
										success : function(res,options) {
											var result = Ext.decode(res.responseText);
											if (result.success == 'true') {
												new Ext.ux.TipsWindow({
													title : SystemConstant.alertTitle,
													autoHide : 3,
													html : result.msg
												}).show();
												//清除选中行;
						    					Ext.getCmp("roleGrid").getSelectionModel().clearSelections();
											} else {
												Ext.MessageBox.show({
													title : SystemConstant.alertTitle,
													msg : result.msg,
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.ERROR
												});
											}
											
											var proxy = roleStore.getProxy();
											proxy.setExtraParam("orgId",org_id);
											roleStore.loadPage(1);
										}
									});
								}
							});
						} else {
							var proxy = roleStore.getProxy();
							proxy.setExtraParam("orgId",org_id);
							roleStore.loadPage(1);
							
							Ext.MessageBox.show({
								title : SystemConstant.alertTitle,
								msg : data.msg,
								buttons : Ext.MessageBox.OK,
								icon : Ext.MessageBox.ERROR
							});
							return false;
						}
					}
				});
			}
		} ],
		listeners : {
			'render' : function(g) {
				/* g.on("itemmouseenter",function(view,record,mode,index, e) {
					var view = g.getView();
					g.tip = Ext.create('Ext.tip.ToolTip',{
						target : view.el,
						delegate : view.getCellSelector(),
						trackMouse : true,
						renderTo : Ext.getBody(),
						listeners : {
							beforeshow : function updateTipBody(tip) {
								tip.update(tip.triggerElement.innerHTML);
							}
						}
					});
				}); */
				for (var i = 0; i < userPermissionArr.length; i++) {
					if ("turnsrule_add_btn" == userPermissionArr[i].name) {
						Ext.getCmp('addRuleBtn').setVisible(true);
					}
					if ("turnsrule_update_btn" == userPermissionArr[i].name) {
						Ext.getCmp('updateRuleBtn').setVisible(true);
					}
					if ("turnsrule_delete_btn" == userPermissionArr[i].name) {
						Ext.getCmp('delRuleBtn').setVisible(true);
					}
				}
			}
		}
	});

	//这里是规则添加grid
	createAddRoleInfo = function() {
		var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
		//这里负责往form里放入什么样的格子
		var roleForm = Ext.create("Ext.form.Panel", {
			layout : 'form',
			bodyStyle : 'padding:15px 20px 0 0',
			border : false,
			labelAlign : 'right',
			fieldDefaults : {
				labelWidth : 100,
				labelAlign : 'right'
			},
			defaults : {
				anchor : '60%'
			},
			defaultType : 'textfield',
			items : [ {
				name : 'workTurnsRule.id',
				hidden : true
			},
			{
	            fieldLabel:'组织名称',
	            xtype:'treepicker',
	            id:'workTeamOrgNameText',
	            name:'workTurnsRule.org.orgId',
  				displayField:'text',
  				//blankText:'组织名称不能为空',
  				forceSelection:true,
  				//allowBlank:false,
  				listeners:{
					select:function(){
  						org_id_validate = Ext.getCmp('workTeamOrgNameText').getValue();
  						Ext.getCmp('rolename').validate();
	 	        	},afterrender:function(){
			    		Ext.getCmp('workTeamOrgNameText').getStore().getRootNode().expand(true,function(){
			    			Ext.getCmp('workTeamOrgNameText').setValue(org_id + '');
			    		});
			    		org_id_validate = org_id;
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
		    }, {
				id : 'rolename',
				fieldLabel : '规则名称',
				name : 'workTurnsRule.name',
				readOnly : false,
				disabled : false,
				hidden : false,
				beforeLabelTextTpl : required,
				maxLength : 50,
	            maxLenghtText:'规则名称最大长度为50！',
	            regex : new RegExp('^([\u4E00-\u9FFF]*-*\\w*)*$'),
		    	regexText : '不能包含特殊字符!',
				width : 100,
				allowBlank : false,
				validator : function(value){
                	var returnObj = null;
                		$.ajax({
                            data:{val:value,deptId:org_id_validate},
                            url : '${ctx}/workturnsrule/validateRuleName.action',
                            cache : false,
                            async : false,
                            type : "post",
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
			},{
				id : 'rolecycleDays',
				fieldLabel : '周期',
				name : 'workTurnsRule.cycleDays',
				xtype:'numberfield',
				maxLength : 5,
				//minValue : 1,
				hidden : false,
				width : 100,
				allowBlank : false,
				regex:new RegExp('^[0-9]*[1-9][0-9]*$'),
				regexText : '只能输入正整数！',
				beforeLabelTextTpl : required
			}, {
				id : 'roleremarks',
				fieldLabel : '描述',
				name : 'workTurnsRule.remarks',
// 				regex : new RegExp('^([\u4E00-\u9FFF]* *-*\\w*)*$'),
// 		    	regexText : '不能包含特殊字符!',
				regex : /^[\w.\- , ，。.\u4e00-\u9fa5]*$/,
	            regexText : "输入内容不能包含特殊字符！",
				xtype : 'textareafield',
				maxLength : 500
			} ]
		});
	
		//这里是规则添加pannel 负责把form装配到pannel上面
		roleWin = Ext.create(
			"Ext.window.Window",
			{
				title : '添加规则',
				resizable : false,
				buttonAlign : "center",
				height : 250,
				width : 350,
				modal : true,
				layout : 'fit',
				modal : true,
				items : [ roleForm ],
				buttons : [{
					text : SystemConstant.saveBtnText,
					handler : function() {
						if (roleForm.form.isValid()) {
							Ext.MessageBox.wait("","添加规则",{
								text : "请稍后..."
							});
							roleForm.form.submit({
								url : "${ctx}/workturnsrule/addorupdateWorkTurnsRule.action",
								success : function(form,action) {
									new Ext.ux.TipsWindow({
										title : SystemConstant.alertTitle,
										autoHide : 3,
										html : action.result.msg
									}).show();
									
									var proxy = roleStore.getProxy();
									proxy.setExtraParam("orgId",org_id);
									roleStore.loadPage(1);
									
									roleWin.close();
									Ext.MessageBox.hide();
								},
								failure : function(form,action) {
									Ext.MessageBox.show({
												title : '提示信息',
												msg : action.result.msg,
												buttons : Ext.Msg.YES,
												modal : true,
												icon : Ext.Msg.ERROR
											});
									
									var proxy = roleStore.getProxy();
									proxy.setExtraParam("orgId",org_id);
									roleStore.loadPage(1);
									
									roleWin.close();
									Ext.MessageBox.hide();
								}
							});
						}
					}
				}, {
					text : '下一步',
					handler : function() {
						if (roleForm.form.isValid()) {
							var cycledays = Ext.getCmp("rolecycleDays").getValue();
							var rulename = Ext.getCmp("rolename").getValue();
							Ext.MessageBox.wait("","添加字典数据",{
								text : "请稍后..."
							});
							roleForm.form.submit({
								url : "${ctx}/workturnsrule/addorupdateWorkTurnsRule.action",
								success : function(form,action) {
									getRuleDetial(action.result.id,cycledays,rulename,org_id);
									
									var proxy = roleStore.getProxy();
									proxy.setExtraParam("orgId",org_id);
									roleStore.loadPage(1);
									
									roleWin.close();
									Ext.MessageBox.hide();
								},
								failure : function(form,action) {
									Ext.MessageBox.show({
												title : '提示信息',
												msg : action.result.msg,
												buttons : Ext.Msg.YES,
												modal : true,
												icon : Ext.Msg.ERROR
											});
									
									var proxy = roleStore.getProxy();
									proxy.setExtraParam("orgId",org_id);
									roleStore.loadPage(1);
									
									roleWin.close();
									Ext.MessageBox.hide();
								}
							});
						}
					}
				},{
					text : '关闭',
					handler : function() {
						roleWin.close();
					}
				} ]
			});
		roleWin.show();
	};

	//这里是规则修改grid
	createUpdateRoleInfo = function() {
		var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
		var row = roleGrid.getSelectionModel().getSelection();
		var newOrgId = null;
		var oldOrgId = null;
		//这里负责往form里放入什么样的格子
		var roleForm = Ext.create("Ext.form.Panel", {
			layout : 'form',
			bodyStyle : 'padding:15px 50px 0 0',
			border : false,
			labelAlign : 'right',
			fieldDefaults : {
				labelWidth : 100,
				labelAlign : 'right'
			},
			defaults : {
				anchor : '60%'
			},
			defaultType : 'textfield',
			items : [ {
				name : 'workTurnsRule.id',
				value : row[0].get('id'),
				hidden : true
			}, {
                fieldLabel:'组织名称',
                xtype:'treepicker',
                id:'workTeamOrgNameText',
                name:'workTurnsRule.org.orgId',
   				displayField:'text',
   				//blankText:'组织名称不能为空',
   				forceSelection:true,
   				//allowBlank:false,
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
   				}),
   				listeners:{
   					select:function(){
   						newOrgId = Ext.getCmp('workTeamOrgNameText').getValue();
   						org_id_validate = Ext.getCmp('workTeamOrgNameText').getValue();
  						Ext.getCmp('rolename').validate();
  	                    }
   				}
		    },{
				id : 'rolename',
				fieldLabel : '规则名称',
				name : 'workTurnsRule.name',
				readOnly : false,
				disabled : false,
				hidden : false,
				beforeLabelTextTpl : required,
				maxLength : 50,
				width : 100,
				maxLenghtText:'规则名称的最大长度为50!',
				regex : new RegExp('^([\u4E00-\u9FFF]*-*\\w*)*$'),
		    	regexText : '不能包含特殊字符',
				allowBlank : false,
				validator : function(value){
                	var returnObj = null;
                	if(value && ruleNameForValidate && value != ruleNameForValidate){
                		$.ajax({
                            data:{val:value,deptId:org_id_validate},
                            url : '${ctx}/workturnsrule/validateRuleName.action',
                            cache : false,
                            async : false,
                            type : "post",
                            dataType : 'json',
                            success : function (result){
                                if(!result.valid){
                                	returnObj = result.reason;
                                }else{
                                	returnObj = true;
                                }
                            }
                        });
                	}else{
                		returnObj = true;
                	}
                	
                	return returnObj;
                }
			}, {
				id : 'rolecycleDays',
				fieldLabel : '周期',
				name : 'workTurnsRule.cycleDays',
				xtype:'numberfield',
				maxLength : 5,
				//minValue : 1,
				hidden : false,
				width : 100,
				allowBlank : false,
				regex:new RegExp('^[0-9]*[1-9][0-9]*$'),
				regexText : '只能输入正整数！',
				beforeLabelTextTpl : required,
				listeners:{
					change : function(obj, newValue, oldValue){
						if(newValue == null){
							Ext.getCmp('saveBtnAtAddWin').setDisabled(false);
						}else if(period == 0 || newValue == period){
							Ext.getCmp('saveBtnAtAddWin').setDisabled(false);
						}else{
							Ext.getCmp('saveBtnAtAddWin').setDisabled(true);
						}
					}
				}
			}, {
				id : 'roleremarks',
				fieldLabel : '描述',
				name : 'workTurnsRule.remarks',
// 				regex : new RegExp('^([\u4E00-\u9FFF]* *-*\\w*)*$'),
// 		    	regexText : '不能包含特殊字符!',
				regex : /^[\w.\- , ，。.\u4e00-\u9fa5]*$/,
	            regexText : "输入内容不能包含特殊字符！",
				xtype : 'textareafield',
				maxLength : 500
			} ]
		});

		//这里是规则修改pannel 负责把form装配到pannel上面
		roleWin = Ext.create("Ext.window.Window",{
			title : '修改规则',
			resizable : false,
			buttonAlign : "center",
			height : 250,
			width : 350,
			modal : true,
			layout : 'fit',
			modal : true,
			items : [ roleForm ],
			listeners:{
				afterrender:function(){
		    		Ext.Ajax.request({
						url : '${ctx}/workturnsrule/getWorkTurnsRuleById.action',
						params : {
							role_id : row[0].get('id')
						},
						success : function(res, options) {
							var data = Ext.decode(res.responseText);
							period = data.cycleDays;
							ruleNameForValidate = data.name;
							org_id_validate = data.orgId;
				    		oldOrgId=data.orgId;
							Ext.getCmp('rolename').setValue(data.name);
							Ext.getCmp('workTeamOrgNameText').getStore().getRootNode().expand(true,function(){
  				    			Ext.getCmp('workTeamOrgNameText').setValue(data.orgId + '');
  				    		});
							
							Ext.getCmp('rolecycleDays').setValue(data.cycleDays);
							Ext.getCmp('roleremarks').setValue(data.remarks); 
						}
					});
		    	}
			},
			buttons : [{
				text : SystemConstant.saveBtnText,
				id:'saveBtnAtAddWin',
				handler : function() {
					var com_func = function(){
						if (roleForm.form.isValid()) {
							Ext.MessageBox.wait("","修改字典数据",{
								text : "请稍后..."
							});
							roleForm.form.submit({
								url : "${ctx}/workturnsrule/addorupdateWorkTurnsRule.action",
								success : function(form,action) {
									new Ext.ux.TipsWindow(
										{
											title : SystemConstant.alertTitle,
											autoHide : 3,
											html : action.result.msg
										}).show();
									
									var proxy = roleStore.getProxy();
									proxy.setExtraParam("orgId",org_id);
									roleStore.loadPage(1);
									
									roleWin.close();
									Ext.MessageBox.hide();
									//清除选中行;
// 			    					Ext.getCmp("roleGrid").getSelectionModel().clearSelections();
			    					roleGrid.getSelectionModel().deselectAll();
								},
								failure : function(form,action) {
									Ext.MessageBox.show({
												title : '提示信息',
												msg : action.result.msg,
												buttons : Ext.Msg.YES,
												modal : true,
												icon : Ext.Msg.ERROR
											});
									
									var proxy = roleStore.getProxy();
									proxy.setExtraParam("orgId",org_id);
									roleStore.loadPage(1);
									
									roleWin.close();
									Ext.MessageBox.hide();
								}
							});
						}
					};
					if (newOrgId && oldOrgId!=newOrgId ) {
						Ext.Ajax.request({
  								url : '${ctx}/workturnsrule/checkOrgIsSame.action',
  								params : {
  									ruleId : row[0].get('id')
  								},
  								success : function(res, options) {
  									var result = Ext.decode(res.responseText);
								if (!result.success) {
									Ext.MessageBox.show({
										title : SystemConstant.alertTitle,
										msg : result.msg,
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
								}else{
									com_func();
								} 
  								}
  							});
					}else{
						com_func();
					}
				}
				}, {
					text : '下一步',
					handler : function() {
						var cycledays = Ext.getCmp("rolecycleDays").getValue();
						var rulename = Ext.getCmp("rolename").getValue();
						var com_func = function(){
							if (roleForm.form.isValid()) {
								Ext.MessageBox.wait("","修改字典数据",{
									text : "请稍后..."
								});
								roleForm.form.submit({
									url : "${ctx}/workturnsrule/addorupdateWorkTurnsRule.action",
									success : function(form,action) {
										getRuleDetial(action.result.id,cycledays,rulename,org_id);
										
										var proxy = roleStore.getProxy();
										proxy.setExtraParam("orgId",org_id);
										roleStore.loadPage(1);
										
										roleWin.close();
										Ext.MessageBox.hide();
										//清除选中行;
				    					Ext.getCmp("roleGrid").getSelectionModel().clearSelections();
									},
									failure : function(form,action) {
										Ext.MessageBox.show({
													title : '提示信息',
													msg : action.result.msg,
													buttons : Ext.Msg.YES,
													modal : true,
													icon : Ext.Msg.ERROR
												});
										
										var proxy = roleStore.getProxy();
										proxy.setExtraParam("orgId",org_id);
										roleStore.loadPage(1);
										
										roleWin.close();
										Ext.MessageBox.hide();
									}
								});
							}
						};
						if (newOrgId && oldOrgId!=newOrgId ) {
							Ext.Ajax.request({
   								url : '${ctx}/workturnsrule/checkOrgIsSame.action',
   								params : {
   									ruleId : row[0].get('id')
   								},
   								success : function(res, options) {
   									var result = Ext.decode(res.responseText);
									if (!result.success) {
										Ext.MessageBox.show({
											title : SystemConstant.alertTitle,
											msg : result.msg,
											buttons : Ext.MessageBox.OK,
											icon : Ext.MessageBox.ERROR
										});
									}else{
										com_func();
									} 
   								}
   							});
						}else{
							com_func();
						}
					}
					},{
						text : '关闭',
						handler : function() {
							roleWin.close();
						}
					} ]
				});
		roleWin.show();
	};
	
	Ext.create("Ext.container.Viewport", {
		layout : "border",
		border : true,
		renderTo : Ext.getBody(),
		items : [ roleGrid, treePanel ]
	});
});
</script>
</head>
<body style="overflow-y: hidden">
	<!-- 这里的样式为了解决IE下垂直滚动条处出现空白垂直边 -->
</body>
</html>

