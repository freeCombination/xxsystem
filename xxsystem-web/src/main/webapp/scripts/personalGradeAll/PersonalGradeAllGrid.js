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
						{name:'responsibilities'},
						{name:'userName'},
						{name:'orgName'},
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
		url : basePath + "/personalGrade/getAllPersonalGradeList.action"
	}
});

grade.personalGrade.PersonalGradeStore.getProxy().setExtraParam("status", 2);


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
			header : "部门",
			dataIndex : "orgName"
		},
		{
			header : "职工姓名",
			dataIndex : "userName"
		},
		{
			header : "现任岗位",
			dataIndex : "responsibilities"
		},
		{
			header : "综合得分",
			dataIndex : "compositeScores"
		}
      ]

/**
 * 定义Grid
 */
grade.personalGrade.PersonalGradeGrid = Ext.create("Ext.grid.Panel", {
	title : '个人评分汇总',
	region : "center",
	bbar : Ext.create("Ext.PagingToolbar", {
		store : grade.personalGrade.PersonalGradeStore
	}),
	selModel : Ext.create("Ext.selection.CheckboxModel"),
	store : grade.personalGrade.PersonalGradeStore,
	columns : cm,
	tbar : [ '&nbsp;年份',
	{
		id:'gradeYear',
		name:'gradeYear',
		width:80,
		xtype:"textfield",
		value:Ext.Date.format(new Date(),"Y"),
		listeners :{
				'render' : function(p){
					p.getEl().on('click',function(){
						WdatePicker({readOnly:true,dateFmt:'yyyy',maxDate:Ext.Date.format(new Date(),"Y"),onpicked:function(){$dp.$('reportDate-inputEl').focus();}});
					});
				}
		}
	}, {
		text : "查询",
		iconCls : "search-button",
		handler : function(button) {
			grade.personalGrade.PersonalGradeStore.getProxy().setExtraParam("gradeYear", button.prev().getValue());
			grade.personalGrade.PersonalGradeStore.loadPage(1);
		}
	} ]
});



