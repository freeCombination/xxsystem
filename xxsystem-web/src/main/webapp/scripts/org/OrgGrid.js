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
			{name: "superiorLeader"},
			{name: "deptLeader"},
			{name: "otherSup"},
			{name: "superintendent"},
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
	    {header: "上级部门",width: 200,dataIndex: "parentOrgName"},
	    {header: "部门名称",width: 200,dataIndex: "orgName"},
	    {header: "部门类型",width: 200,dataIndex: "orgTypeName",sortable :false,menuDisabled: true},
        {header: "部门编码",width: 200,dataIndex: "orgCode"},
        {header: "部门领导",width: 200,dataIndex: "deptLeader"},
	    {header: "分管领导",width: 200,dataIndex: "superiorLeader"},
	    {header: "所领导",width: 200,dataIndex: "otherSup"},
	    {header: "所长",width: 200,dataIndex: "superintendent"},
	    {header: "状态",width: 200,dataIndex: "enable",
	    	renderer: function(value, cellmeta, record, rowIndex, columnIndex, store){
	    		//cellmeta.tdAttr = 'data-qtip="' + orgTypeArr[i].name + '"';
	    		var orgId = record.get('orgId');
				if(value == 1){
					return '<img title="点击锁定部门" src="'+basePath+'/images/icons/unlock.gif" style="cursor: pointer" onclick="lockupOrg('+orgId+','+value+')"/>';
				}else{
					return '<img title="点击解锁部门" src="'+basePath+'/images/icons/lock.gif" style="cursor: pointer" onclick="lockupOrg('+orgId+','+value+')"/>';
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
		disabledExpr : "$selectedRows != 1 || $enable == 0",// $selected 表示选中的记录数不等于1
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
		disabledExpr : "$selectedRows == 0 || $enable == 0",
		iconCls : 'delete-button',
		handler : function() {
			deleteOrg();
		}
	} ]
});
var addOrg = function() {
	orgWin.setTitle(SystemConstant.addBtnText);
	//var row = orgGrid.getSelectionModel().getSelection()
	var basicForm = orgWin.down('form').getForm();
	basicForm.reset();
	basicForm.url = basePath + '/org/addOrg.action';
	orgTypeStore.load();
	/*if(row!='' && row[0].data.parentOrgId){
		basicForm.findField('org.organization.orgId').setValue(row[0].data.parentOrgId);
		basicForm.findField('org.organization.orgName').setValue(row[0].data.parentOrgName);
	}else{*/
		basicForm.findField('org.organization.orgId').setValue(selectNode.raw.nodeId);
		basicForm.findField('org.organization.orgName').setValue(selectNode.raw.text);
	//}
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
		url : basePath + '/org/getOrgById.action',
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

lockupOrg = function(orgId, enable){
	var title = '确认锁定所选组织数据吗？';
	if (enable == 0) {
		title = '确认解锁所选组织据吗？';
	}
	
	Ext.Msg.confirm(SystemConstant.alertTitle, title, function(btn) {
        if (btn == 'yes') {
        	Ext.Ajax.request({
                url: basePath + '/org/lockupOrg.action',
                async:false,
                params: {
                	orgId: orgId,
                    enable: enable
                },
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
                            icon: Ext.MessageBox.INFO
                        });
                    }
                    orgStore.load();
                }
            });
        }
    });
};