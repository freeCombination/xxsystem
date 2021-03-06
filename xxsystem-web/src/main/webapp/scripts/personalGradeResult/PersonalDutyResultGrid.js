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
			dataIndex : "workDuty",
        	renderer : function(value, p, record) {
    		    return '<div style="white-space:normal;line-height:16px;">' + value.replace(/\n/g,'<br />') + '</div>';
    		}
		},
		{
			header : "完成情况",
			dataIndex : "completion",
        	renderer : function(value, p, record) {
    		    return '<div style="white-space:normal;line-height:16px;">' + value.replace(/\n/g,'<br />') + '</div>';
    		}
		}
          ];

/**
 * 定义Grid
 */
grade.personalDutyResult.PersonalDutyResultGrid = Ext.create("Ext.grid.Panel", {
	title:'一、岗位职责完成情况',
	height : 360,
	width : 1160,
	boder:false,
	store : grade.personalDutyResult.PersonalDutyResultStore,
	columns : cm
});



