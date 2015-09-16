Ext.define("orgModel",{
	extend:"Ext.data.Model",
	fields:[
			{name: "orgId",type:"int"}, 
			{name: "orgName"},
			{name: "parentOrgId",type:"int"}, 
			{name: "parentOrgName"},
			{name: "orgType"},
			{name: "orgTypeValue"},
			{name: "orgTypeName"},
			{name: "orgCode"},
			{name: "superiorLeaderId",type:"int"}, 
			{name: "superiorLeader"},
			{name: "deptLeaderId",type:"int"}, 
			{name: "deptLeader"},
			{name: "enable", type:"int"}
		]
});
var orgStore=Ext.create("Ext.data.Store", {
    model:"orgModel",
	proxy: {
        type:"format",
	    url: basePath+"/org/getOrgList.action"
    }
});

var orgGrid = Ext.create("Ext.grid.Panel", {
	title : '组织管理',
	region : "center",
	bbar : Ext.create("Ext.PagingToolbar", {
		store : orgStore
	}),
	selModel : Ext.create("Ext.selection.CheckboxModel"),
	store : orgStore,
	columns : [
	    {xtype: "rownumberer",width:60,text:'序号',align:"center"},
	    {header: "orgId",dataIndex: "orgId",hidden: true},
	    {header: "parentOrgId",dataIndex: "parentOrgId",hidden: true},
	    {header: "deptLeaderId",dataIndex: "deptLeaderId",hidden: true},
	    {header: "superiorLeaderId",dataIndex: "superiorLeaderId",hidden: true},
	    {header: "上级部门",width: 200,dataIndex: "parentOrgName"},
	    {header: "部门名称",width: 200,dataIndex: "orgName"},
	    {header: "部门类型",width: 200,dataIndex: "orgTypeName",sortable :false,menuDisabled: true},
        {header: "部门编码",width: 200,dataIndex: "orgCode"},
        {header: "部门领导",width: 200,dataIndex: "deptLeader"},
	    {header: "分管领导",width: 200,dataIndex: "superiorLeader"},
	    {header: "操作",width: 200,dataIndex: "enable",
	    	renderer: function(value, cellmeta, record, rowIndex, columnIndex, store){
	    		//cellmeta.tdAttr = 'data-qtip="' + orgTypeArr[i].name + '"';
	    		if (value == 0) {
	    			return '<a>停用</a>';
	    		}
	    		else {
	    			return '<a>启用</a>';
	    		}
        	}
	    }
	],
	tbar : [ '->', {
		xtype : 'button',
		text : SystemConstant.addNextOrgBtnText,
		iconCls : 'add-button',
		id : 'addOrg',
		handler : function() {
			addOrg();
		}
	}, {
		xtype : 'button',
		id : 'updateOrg',
		text : SystemConstant.modifyBtnText,
		disabledExpr : "$selectedRows != 1",// $selected 表示选中的记录数不等于1
		disabled : true,
		iconCls : 'edit-button',
		handler : function() {
			updateOrg();
		}
	}, {
		xtype : 'button',
		text : SystemConstant.deleteBtnText,
		id:'deleteOrg',
		disabled : true,
		disabledExpr : "$selectedRows == 0",
		iconCls : 'delete-button',
		handler : function() {
			deleteOrg();
		}
	} ]
});
var addOrg = function() {
	orgWin.setTitle(SystemConstant.addBtnText);
	var row = orgGrid.getSelectionModel().getSelection()
	var basicForm = orgWin.down('form').getForm();
	basicForm.reset();
	basicForm.url = basePath + '/org/addOrg.action';
	orgTypeStore.load();
	if(row!=''){
		basicForm.findField('org.organization.orgId').setValue(row[0].data.parentOrgId);
		basicForm.findField('org.organization.orgName').setValue(row[0].data.parentOrgName);
	}else{
		basicForm.findField('org.organization.orgId').setValue(selectNode.raw.nodeId);
		basicForm.findField('org.organization.orgName').setValue(selectNode.raw.text);
	}
	basicForm.findField('org.orgCode').setDisabled(false);
	orgWin.show();
};
var updateOrg = function() {
	orgWin.setTitle(SystemConstant.modifyBtnText);
	var row = orgGrid.getSelectionModel().getSelection()
	var orgId = row[0].data.orgId;
	var basicForm = orgWin.down('form').getForm();
	basicForm.reset();
	basicForm.url = basePath + '/org/updateOrg.action';
	basicForm.findField('org.orgId').setValue(orgId);
	basicForm.load({
		url : basePath + '/org/getOById.action',
		params : {
			orgId : orgId
		}
	});
	basicForm.findField('org.orgCode').setDisabled(true);
	orgWin.show();
};
var deleteOrg = function(){
	var rows = orgGrid.getSelectionModel().getSelection();
	var ids = "";
	for (var i = 0; i < rows.length; i++) {
		ids += (rows[i].data.orgId + ",");
	}
	ids = ids.substring(0, ids.length - 1);
	Ext.Msg.confirm(SystemConstant.alertTitle, "确认删除所选组织及其子组织吗？", function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url : basePath + '/org/delOrg.action',
				params : {
					orgIds : ids
				},
				success : function(res, options) {
					var data = Ext.decode(res.responseText);
					if (data.success) {
						orgStore.loadPage(1);
						Ext.Msg.showTip(data.msg);
					} else {
						Ext.Msg.showError(data.msg);
					}
				},
				failure : sshframe.FailureProcess.Ajax
			});
		}
	});
};