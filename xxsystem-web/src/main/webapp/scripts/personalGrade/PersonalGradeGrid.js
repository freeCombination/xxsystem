/**
 * 个人评分列表
 * @date 20150919
 * @author wujl
 */

/**
 * 定义Model
 */
Ext.define("grade.personalGrade.PersonalGradeModel",{
					extend:"Ext.data.Model",
					fields:[
						{name:'id'},
						{name:'gradeYear'},
						{name:'problem'},
						{name:'workPlan'},
						{name:'compositeScores'},
						{name:'status'},
						{name:'title'}
					]
	});

/**
 * 定义Store
 */
grade.personalGrade.PersonalGradeStore = Ext.create('Ext.data.Store', {
	model : 'grade.personalGrade.PersonalGradeModel',
	proxy : {
		type : "format",
		url : basePath + "/personalGrade/getPersonalGradeForUserSelfList.action"
	}
});

grade.personalGrade.PersonalGradeStore.addListener('load', function(st, rds, opts) {
	if (rds.length > 0) {
		Ext.getCmp('generate_btn').hide();
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
		}, {
			header : "标题",
			dataIndex : "title"
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
			header : "综合得分",
			dataIndex : "compositeScores"
		},
		{
			header : "状态",
			dataIndex : "status",
			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				if(value == '0'){
					return '草稿';
				}else if(value == '1'){
					return '已提交';
				}else if(value == '2'){
					return '已归档';
				}
			}
		}
          ]

/**
 * 定义Grid
 */
grade.personalGrade.PersonalGradeGrid = Ext.create("Ext.grid.Panel", {
	title : '个人评分',
	region : "center",
	bbar : Ext.create("Ext.PagingToolbar", {
		store : grade.personalGrade.PersonalGradeStore
	}),
	selModel : Ext.create("Ext.selection.CheckboxModel"),
	store : grade.personalGrade.PersonalGradeStore,
	columns : cm,
	tbar : [ '标题', {
		xtype : 'textfield',
		stripCharsRe : /^\s+|\s+$/g, // 禁止输入空格
		id : 'inputTitle'
	}, {
		text : "查询",
		iconCls : "search-button",
		handler : function(button) {
			grade.personalGrade.PersonalGradeStore.getProxy().setExtraParam("inputTitle", button.prev().getValue());
			grade.personalGrade.PersonalGradeStore.loadPage(1);
		}
	}, '->', {
		xtype : 'button',
		id:'generate_btn',
		text : '生成个人评分',
		iconCls : 'edit-button',
		handler : function() {
			grade.personalGrade.generatePersonalGrade();
		}
		},
	{
		xtype : 'button',
		text : '编辑',
		disabledExpr : "$selectedRows != 1 || $status!='0'",// $selected 表示选中的记录数不等于1
		disabled : true,
		iconCls : 'edit-button',
		handler : function() {
			grade.personalGrade.EditPersonalGrade();
		}
	},{
		xtype : 'button',
		text : '提交',
		disabledExpr : "$selectedRows == 0 || $status=='1'",// $selected 表示选中的记录数不等于1
		disabled : true,
		iconCls : 'edit-button',
		handler : function() {
			grade.personalGrade.SubmitPersonalGrade();
		}
	} ]
});

/**
 * 编辑个人评分
 */
grade.personalGrade.EditPersonalGrade = function() {
	grade.personalGrade.PersonalGradeWin.setTitle('编辑');
	var row = grade.personalGrade.PersonalGradeGrid.getSelectionModel().getSelection()
	var id = row[0].data.id;
	var basicForm = grade.personalGrade.PersonalGradeWin.down('form').getForm();
	basicForm.reset();
	basicForm.url = basePath + '/personalGrade/editPersonalGrade.action';
	basicForm.findField('id').setValue(id);
	basicForm.load({
		url : basePath + '/personalGrade/getPersonalGradeById.action',
		params : {
			id : id
		}
	});
	grade.personalGrade.PersonalGradeWin.show();
}

/**
 * 提交个人评分 预留可选多个提交
 */
grade.personalGrade.SubmitPersonalGrade = function() {
	
	var rows = grade.personalGrade.PersonalGradeGrid.getSelectionModel().getSelection();
	var ids = "";
	for (var i = 0; i < rows.length; i++) {
		ids += (rows[i].data.id + ",");
	}
	ids = ids.substring(0, ids.length - 1);
	Ext.Msg.confirm(SystemConstant.alertTitle, "确认提交这" + rows.length + "条数据吗?提交后不可修改!", function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url : basePath + '/personalGrade/submitPersonalGrade.action',
				params : {
					ids : ids
				},
				success : function(res, options) {
					var data = Ext.decode(res.responseText);
					if (data.success) {
						Ext.Msg.showTip(data.msg);
						grade.personalGrade.PersonalGradeStore.loadPage(1);
					} else {
						Ext.Msg.showError(data.msg);
					}
				},
				failure : sshframe.FailureProcess.Ajax
			});
		}
	});
	
}



