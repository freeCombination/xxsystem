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
						{name:'gradeUserType'},
						{name:'respChangeDate'},
						{name:'state'},
						{name:'jobStartDate'}
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

grade.personalGradeResult.PersonalGradeResultStore.addListener('beforeload', function(st, rds, opts) {
	grade.personalGradeResult.PersonalGradeResultGrid.getSelectionModel().clearSelections();
	Ext.getCmp('edit-button').setDisabled(true);
	Ext.getCmp('submit-button').setDisabled(true);
	Ext.getCmp('query-button').setDisabled(true);
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
			header : "员工部门",
			dataIndex : "gradeOrg"
		},
		{
			header : "员工姓名",
			dataIndex : "gradeUser"
		},
		/*{
			header : "得分",
			dataIndex : "score"
		},*/
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
		id:'edit-button',
		text : '评分',
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
		//hidden:true,
		id:'query-button',
		iconCls : 'query-button',
		handler : function() {
			grade.personalGradeResult.ViewPersonalGradeResult();
		}
	}
	,{
		xtype : 'button',
		id:'submit-button',
		text : '提交',
		disabledExpr : "$selectedRows == 0 || $state==1",// $selected 表示选中的记录数不等于1
		disabled : true,
		iconCls : 'icon-sendReview',
		handler : function() {
			grade.personalGradeResult.SubmitPersonalGradeResult();
		}
	} ]
});

/**
 * 编辑个人评分
 */
grade.personalGradeResult.EditPersonalGradeResult = function() {
	grade.personalGradeResult.PersonalGradeResultWin.setTitle('评分');
	var row = grade.personalGradeResult.PersonalGradeResultGrid.getSelectionModel().getSelection()
	var id = row[0].data.id;
	var personalGradeId = row[0].data.personalGradeId;
	var gradeUserType = row[0].data.gradeUserType;
	var basicForm = grade.personalGradeResult.PersonalGradeResultForm.getForm();
	basicForm.reset();
	Ext.getCmp('evaluation').setReadOnly(false);
	Ext.getCmp('evaluation1').setReadOnly(false);
	Ext.getCmp('evaluation2').setReadOnly(false);
	Ext.getCmp('evaluation3').setReadOnly(false);
	basicForm.load({
		url : basePath + '/personalGrade/getPersonalGradeResultById.action',
		params : {
			id : id
		}
	});
	var basicFormProblem = grade.personalGradeResult.PersonalGradeResultFormProblem.getForm();
	basicFormProblem.reset();
	basicFormProblem.load({
		url : basePath + '/personalGrade/getPersonalGradeResultById.action',
		params : {
			id : id
		}
	});
	
	Ext.getCmp('result_submit').show();
	
	var basicForm1 = grade.personalGradeResult.PersonalGradeResultForm1.getForm();
	basicForm1.reset();
	basicForm1.findField('id').setValue(id);
	basicForm1.findField('personalGradeId').setValue(personalGradeId);
	basicForm1.findField('gradeUserType').setValue(gradeUserType);
	basicForm1.url = basePath + '/personalGrade/editPersonalGradeResult.action';
	basicForm1.load({
		url : basePath + '/personalGrade/getPersonalGradeResultById.action',
		params : {
			id : id
		}
	});
	
/*	var scoreColumns = grade.personalDutyResultDetails.PersonalDutyResultDetailsGrid.columns;
	var c = scoreColumns[4];
	c.hidden = false ;
	
	var c1 = scoreColumns[5];
	c1.hidden = true ;*/
	
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
	var gradeUserType = row[0].data.gradeUserType;
	var basicForm = grade.personalGradeResult.PersonalGradeResultWin.down('form').getForm();
	basicForm.reset();
	basicForm.url = basePath + '/personalGrade/editPersonalGradeResult.action';
	Ext.getCmp('evaluation').setReadOnly(true);
	Ext.getCmp('evaluation1').setReadOnly(true);
	Ext.getCmp('evaluation2').setReadOnly(true);
	Ext.getCmp('evaluation3').setReadOnly(true);
	basicForm.load({
		url : basePath + '/personalGrade/getPersonalGradeResultById.action',
		params : {
			id : id
		}
	});
	
	Ext.getCmp('result_submit').hide();
	
	var basicForm1 = grade.personalGradeResult.PersonalGradeResultForm1.getForm();
	basicForm1.reset();
	basicForm1.findField('id').setValue(id);
	basicForm1.findField('personalGradeId').setValue(personalGradeId);
	basicForm1.findField('gradeUserType').setValue(gradeUserType);
	basicForm1.url = basePath + '/personalGrade/editPersonalGradeResult.action';
	basicForm1.load({
		url : basePath + '/personalGrade/getPersonalGradeResultById.action',
		params : {
			id : id
		}
	});
	
	//设置分数列
	//isCellEditable
/*	var scoreColumns = grade.personalDutyResultDetails.PersonalDutyResultDetailsGrid.columns;
	var c = scoreColumns[4];
	c.hidden = true ;
	
	var c1 = scoreColumns[5];
	c1.hidden = false ;*/
	
	/**/
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
						Ext.Msg.showInfo(data.msg);
					}
				},
				failure : sshframe.FailureProcess.Ajax
			});
		}
	});
	
}



