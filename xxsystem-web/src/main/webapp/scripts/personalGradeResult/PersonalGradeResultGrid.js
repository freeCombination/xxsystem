/**
 * 个人评分列表
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
		url : basePath + "/personalGrade/getPersonalGradeResultList.action"
	}
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
			header : "姓名",
			dataIndex : "gradeUser"
		},
		{
			header : "性别",
			dataIndex : "gender"
		},
		{
			header : "出生年月",
			dataIndex : "birthDay"
		},
		{
			header : "政治面貌",
			dataIndex : "politicsStatus"
		},
		{
			header : "学历",
			dataIndex : "educationBackground"
		},
		{
			header : "现任岗位",
			dataIndex : "responsibilities"
		},
		{
			header : "年份",
			dataIndex : "gradeYear"
		},
		{
			header : "存在问题",
			dataIndex : "problem"
		},
		{
			header : "工作计划",
			dataIndex : "workPlan"
		},
		{
			header : "得分",
			dataIndex : "score"
		},
		{
			header : "状态",
			dataIndex : "state",
			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
			if(value == 0){
					return '未处理';
				}else if(value == 1){
					return '已提交';
				}
			}
		}
          ]

/**
 * 定义Grid
 */
grade.personalGradeResult.PersonalGradeResultGrid = Ext.create("Ext.grid.Panel", {
	title : '个人评分',
	region : "center",
	bbar : Ext.create("Ext.PagingToolbar", {
		store : grade.personalGradeResult.PersonalGradeResultStore
	}),
	selModel : Ext.create("Ext.selection.CheckboxModel"),
	store : grade.personalGradeResult.PersonalGradeResultStore,
	columns : cm,
	tbar : [ '标题', {
		xtype : 'textfield',
		stripCharsRe : /^\s+|\s+$/g, // 禁止输入空格
		id : 'inputTitle'
	}, {
		text : "查询",
		iconCls : "search-button",
		handler : function(button) {
			grade.personalGradeResult.PersonalGradeResultStore.getProxy().setExtraParam("inputTitle", button.prev().getValue());
			grade.personalGradeResult.PersonalGradeResultStore.loadPage(1);
		}
	}, '->', {
		xtype : 'button',
		text : '编辑',
		disabledExpr : "$selectedRows != 1 || $state!=0",// $selected 表示选中的记录数不等于1
		disabled : true,
		iconCls : 'edit-button',
		handler : function() {
			grade.personalGradeResult.EditPersonalGradeResult();
		}
	},
	 {
		xtype : 'button',
		text : '查看',
		disabledExpr : "$selectedRows != 1",// $selected 表示选中的记录数不等于1
		disabled : true,
		iconCls : 'edit-button',
		handler : function() {
			grade.personalGradeResult.ViewPersonalGradeResult();
		}
	}
	,{
		xtype : 'button',
		text : '提交',
		disabledExpr : "$selectedRows == 0 || $state==1",// $selected 表示选中的记录数不等于1
		disabled : true,
		iconCls : 'edit-button',
		handler : function() {
			grade.personalGradeResult.SubmitPersonalGradeResult();
		}
	} ]
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



