/**
 * 个人评分列表
 * @date 20150919
 * @author wujl
 */

/**
 * 定义用户Model
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
		url : basePath + "/grade/getPersonalGradeList.action"
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
			dataIndex : "status"
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
		text : '编辑',
		disabledExpr : "$selectedRows != 1",// $selected 表示选中的记录数不等于1
		disabled : true,
		iconCls : 'edit-button',
		handler : function() {
			grade.personalGrade.EditPersonalGrade();
		}
	},{
		xtype : 'button',
		text : '提交',
		disabledExpr : "$selectedRows != 1",// $selected 表示选中的记录数不等于1
		disabled : true,
		iconCls : 'edit-button',
		handler : function() {
			grade.personalGrade.SubmitPersonalGrade();
		}
	} ]
});



