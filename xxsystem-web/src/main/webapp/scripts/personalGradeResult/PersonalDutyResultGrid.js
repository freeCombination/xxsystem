/**
 * 个人评分职责列表
 * 
 * @date 20150923
 * @author wujl
 */

/**
 * 定义Model
 */
Ext.define("grade.personalDutyResult.PersonalDutyResultModel",{
					extend:"Ext.data.Model",
					fields:[
						{name:'id'},
						{name:'workDuty'},
						{name:'completion'}
					]
	});

/**
 * 定义Store
 */
grade.personalDutyResult.PersonalDutyResultStore = Ext.create('Ext.data.Store', {
	model : 'grade.personalDutyResult.PersonalDutyResultModel',
	proxy : {
		type : "format",
		url : basePath + "/personalGrade/getPersonalDutyList.action"
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
			header : "工作职责",
			dataIndex : "workDuty"
		},
		{
			header : "完成情况",
			dataIndex : "completion"
		}
          ];

/**
 * 定义Grid
 */
grade.personalDutyResult.PersonalDutyResultGrid = Ext.create("Ext.grid.Panel", {
	title:'岗位职责完成情况',
	height : 160,
	width : 790,
	boder:false,
	//region : "center",
	store : grade.personalDutyResult.PersonalDutyResultStore,
	columns : cm
});



