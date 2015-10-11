/**
 * 个人评分明细列表
 * @date 20150919
 * @author wujl
 */




/**
 * 定义Model
 */
Ext.define("grade.personalGradeResult.PersonalGradeResultModel",{
					extend:"Ext.data.Model",
					fields:[
						{name:'id'},
						{name:'personalGradeId'},
						{name:'gradeUser'},
						{name:'gradeOrg'},
						{name:'userName'},
						{name:'orgName'},
						{name:'score'},
						{name:'gradeDate'},
						{name:'gradeYear'},
						{name:'title'},
						{name:'problem'},
						{name:'workPlan'},
						{name:'responsibilities'},
						{name:'birthDay'},
						{name:'gender'},
						{name:'educationBackground'},
						{name:'politicsStatus'},
						{name:'state'}
					]
	});

/**
 * 定义Store
 */
grade.personalGradeResult.PersonalGradeResultStore = Ext.create('Ext.data.Store', {
	model : 'grade.personalGradeResult.PersonalGradeResultModel',
	proxy : {
		type : "format",
		url : basePath + "/personalGrade/getPersonalGradeResultDetailsList.action"
	}
});

grade.personalGradeResult.PersonalGradeResultStore.getProxy().setExtraParam("state", 1);

/**
 * 树形模型
 */
Ext.define('treeModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'nodeId',type: 'int'}, 
        {name: 'parentId',type: 'int'}, 
        {name: 'id',type: 'int',mapping:'nodeId'},
        {name: 'text',type: 'string'} 
    ]
});

/**
 * 部门
 */
var canpDeptQuery = Ext.create("Ext.ux.TreePicker", {
    allowBlank: false,
    value:'0',
    id:'canpDeptQuery',
    displayField: 'text',
    rootVisible: true,
    valueField: 'id',
    minPickerHeight: 200, //最小高度，不设置的话有时候下拉会出问题
    editable: false, //启用编辑，主要是为了清空当前的选择项
    enableKeyEvents: true, //激活键盘事件
    store: Ext.create("Ext.data.TreeStore", {
        model: 'treeModel',
        nodeParam:'parentId',
        autoLoad:false,
        clearOnLoad :true,
        proxy: {
            type: 'ajax',
            reader:{
                type: 'json'
            },
            folderSort: true,
            sorters: [{
                 property: 'nodeId',
                 direction: 'DESC'
            }],
            url :basePath+'/org/getUnitTreeListNotCheck.action'
        },
        root: {
            expanded: true,
            id:"0",
            text:'全部'
        }
    })
});

/**
 * 列表字段
 */
var cm = [
		{
			xtype : "rownumberer",
			text : '序号',
			width : 60,
			align : "center"
		}, {
			header : "id",
			dataIndex : "id",
			hidden : true
		},{
			header : "标题",
			dataIndex : "title"
		},
		{
			header : "年份",
			dataIndex : "gradeYear"
		},
		{
			header : "职工姓名",
			dataIndex : "gradeUser"
		},
		{
			header : "部门",
			dataIndex : "gradeOrg"
		},
		{
			header : "现任岗位",
			dataIndex : "responsibilities"
		},
		{
			header : "评分职员",
			dataIndex : "userName"
		},
		{
			header : "得分",
			dataIndex : "score"
		}
          ]

/**
 * 定义Grid
 */
grade.personalGradeResult.PersonalGradeResultGrid = Ext.create("Ext.grid.Panel", {
	title : '个人评分明细',
	region : "center",
	bbar : Ext.create("Ext.PagingToolbar", {
		store : grade.personalGradeResult.PersonalGradeResultStore
	}),
	selModel : Ext.create("Ext.selection.CheckboxModel"),
	store : grade.personalGradeResult.PersonalGradeResultStore,
	columns : cm,
	tbar : [
	'&nbsp;部门',canpDeptQuery,
	'职工姓名', {
		xtype : 'textfield',
		stripCharsRe : /^\s+|\s+$/g, // 禁止输入空格
		id : 'inputGradeUser'
	},
	'评分职工', {
		xtype : 'textfield',
		stripCharsRe : /^\s+|\s+$/g, // 禁止输入空格
		id : 'inputUserName'
	},
	{
		text : "查询",
		iconCls : "search-button",
		handler : function(button) {
			var proxy = grade.personalGradeResult.PersonalGradeResultStore.getProxy();
			proxy.setExtraParam("inputGradeUser", Ext.getCmp('inputGradeUser').getValue());
			proxy.setExtraParam("inputUserName", Ext.getCmp('inputUserName').getValue());
			proxy.setExtraParam("canpDeptQuery", Ext.getCmp('canpDeptQuery').getValue());
			proxy.setExtraParam("state", 1);
			grade.personalGradeResult.PersonalGradeResultStore.loadPage(1);
		}
	}
	  ]
});

/**
 * 编辑个人评分
 */
grade.personalGradeResult.EditPersonalGradeResult = function() {
	grade.personalGradeResult.PersonalGradeResultWin.setTitle('编辑');
	var row = grade.personalGradeResult.PersonalGradeResultGrid.getSelectionModel().getSelection()
	var id = row[0].data.id;
	var personalGradeId = row[0].data.personalGradeId;
	var basicForm = grade.personalGradeResult.PersonalGradeResultWin.down('form').getForm();
	basicForm.reset();
	basicForm.url = basePath + '/personalGrade/editPersonalGradeResult.action';
	basicForm.findField('id').setValue(id);
	basicForm.findField('personalGradeId').setValue(personalGradeId);
	basicForm.load({
		url : basePath + '/personalGrade/getPersonalGradeResultById.action',
		params : {
			id : id
		}
	});
	grade.personalGradeResult.PersonalGradeResultWin.show();
};

/**
 * 查看个人评分结果
 */
grade.personalGradeResult.ViewPersonalGradeResult = function() {
	grade.personalGradeResult.PersonalGradeResultWin.setTitle('详情');
	var row = grade.personalGradeResult.PersonalGradeResultGrid.getSelectionModel().getSelection()
	var id = row[0].data.id;
	var personalGradeId = row[0].data.personalGradeId;
	var basicForm = grade.personalGradeResult.PersonalGradeResultWin.down('form').getForm();
	basicForm.reset();
	basicForm.url = basePath + '/personalGrade/editPersonalGradeResult.action';
	basicForm.findField('id').setValue(id);
	basicForm.findField('personalGradeId').setValue(personalGradeId);
	Ext.getCmp('result_submit').hide();
	Ext.getCmp('score').hide();
	basicForm.load({
		url : basePath + '/personalGrade/getPersonalGradeResultById.action',
		params : {
			id : id
		}
	});
	grade.personalGradeResult.PersonalGradeResultWin.show();
};

/**
 * 提交个人评分 预留可选多个提交
 */
grade.personalGradeResult.SubmitPersonalGradeResult = function() {
	
	var rows = grade.personalGradeResult.PersonalGradeResultGrid.getSelectionModel().getSelection();
	var ids = "";
	for (var i = 0; i < rows.length; i++) {
		ids += (rows[i].data.id + ",");
	}
	ids = ids.substring(0, ids.length - 1);
	Ext.Msg.confirm(SystemConstant.alertTitle, "确认提交这" + rows.length + "条数据吗?提交后不可修改!", function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url : basePath + '/personalGrade/submitPersonalGradeResult.action',
				params : {
					ids : ids
				},
				success : function(res, options) {
					var data = Ext.decode(res.responseText);
					if (data.success) {
						Ext.Msg.showTip(data.msg);
						grade.personalGradeResult.PersonalGradeResultStore.loadPage(1);
					} else {
						Ext.Msg.showError(data.msg);
					}
				},
				failure : sshframe.FailureProcess.Ajax
			});
		}
	});
	
}



