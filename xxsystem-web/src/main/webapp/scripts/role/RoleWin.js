sshframe.role.RoleForm = Ext.create("Ext.form.Panel", {
	layout : 'form',
	bodyStyle : 'padding:15px 10px 0 0',
	border : false,
	labelAlign : 'right',
	fieldDefaults : {
		labelWidth : 60,
		labelAlign : 'right'
	},
	defaults : {
		anchor : '70%',
		width : 100
	},
	defaultType : 'textfield',
	items : [ {
		name : 'roleId',
		hidden : true
	}, {
		fieldLabel : '角色类型',
		name : 'roleTypeId',
		xtype : 'combobox',
		allowBlank : false,
		queryMode : 'remote',
		valueField : 'dictionaryId',
		displayField : 'dictionaryName',
		store : Ext.create('Ext.data.Store', {
			model : Ext.define('userTypeModel', {
				extend : 'Ext.data.Model',
				fields : [ {
					name : 'dictionaryId'
				}, {
					name : 'dictionaryName'
				}, {
					name : 'dictionaryValue'
				} ]
			}),
			proxy : {
				type : 'format',
				url : basePath + '/user/getSelectionsByType.action',
				extraParams : {
					dictTypeCode : "ROLETYPE"
				}
			},
			autoLoad : true
		})
	},{
		fieldLabel : '角色名称',
		name : 'roleName',
		vtype:'filterHtml',
		maxLength : 20,
		allowBlank : false
	}, {
		fieldLabel : '角色编码',
		name : 'roleCode',
		allowBlank : false,
		maxLength : 10,
		vtype:'filterHtml'
	}, {
		fieldLabel : '角色描述',
		name : 'description',
		vtype:'filterHtml',
		maxLength : 500,
		xtype : 'textareafield'
	} ]
});

sshframe.role.RoleWin = Ext.create("Ext.window.Window", {
	height : 250,
	width : 380,
	items : [ sshframe.role.RoleForm ],
	buttons : [ {
		text : '确定',
		handler : function() {
			if (sshframe.role.RoleForm.form.isValid()) {
				sshframe.role.RoleForm.form.submit({
					success : function(form, action) {
						Ext.Msg.showTip(action.result.msg);
						sshframe.role.RoleStore.loadPage(1);
						sshframe.role.RoleWin.close();
					},
					failure : function(form, action) {
						Ext.Msg.showError(action.result.msg);
					}
				});
			}
		}
	}, {
		text : '关闭',
		handler : function() {
			sshframe.role.RoleWin.close();
		}
	} ]
});
