/**
 * 个人评分职责列表
 * 
 * @date 20150923
 * @author wujl
 */

/**
 * 定义Model
 */
Ext.define("grade.personalDuty.PersonalDutyModel",{
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
grade.personalDuty.PersonalDutyViewStore = Ext.create('Ext.data.Store', {
	model : 'grade.personalDuty.PersonalDutyModel',
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
    		    return '<div style="white-space:normal;line-height:16px;">' + value + '</div>';
    		}
		},
		{
			header : "完成情况",
			dataIndex : "completion",
        	renderer : function(value, p, record) {
    		    return '<div style="white-space:normal;line-height:16px;">' + value + '</div>';
    		}
		}
          ];

/**
 * 定义Grid
 */
grade.personalDuty.PersonalDutyViewGrid = Ext.create("Ext.grid.Panel", {
	region : "center",
	title:'岗位职责',
	store : grade.personalDuty.PersonalDutyViewStore,
	columns : cm
});



