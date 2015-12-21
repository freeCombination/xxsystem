/**
 * 个人自评查看窗口
 * @date 20150919
 * @author wujl
 */



/**
 * 定义个人评分基础信息form
 */
grade.personalGrade.PersonalGradeViewForm = Ext.create("Ext.form.Panel", {
	layout : 'form',
	region: "south",
	title:'工作计划',
	height : 200,
	autoScroll:true,
	border : false,
	labelAlign : 'right',
	frame: true,
	fieldDefaults : {
		labelWidth : 60,
		labelAlign : 'right'
	},
	defaults : {
		anchor : '70%',
		width : 100
	},
    items: [
            {
            	id:'personalGradeViewId',
        		name : 'id',
        		height:0,
        		xtype : 'textfield',
        		hidden : true
        	},
        	{
        		fieldLabel : '存在问题',
        		name : 'problem',
        		xtype : 'displayfield'
        	},
        	{
        		fieldLabel : '工作计划',
        		name : 'workPlan',
        		xtype : 'displayfield'
        	}
            ]
});




grade.personalGrade.PersonalGradeViewWin = Ext.create("Ext.window.Window", {
	height : 500,
	width : 800,
	layout: 'border',
	title:'详情',
	items : [grade.personalDuty.PersonalDutyViewGrid, grade.personalGrade.PersonalGradeViewForm],
	buttons : [ {
		text : '关闭',
		handler : function() {
			grade.personalGrade.PersonalGradeViewWin.close();
		}
	} ],
	listeners: {
		show: function(){
    		var personalGradeId = Ext.getCmp('personalGradeViewId').getValue();
    		grade.personalDuty.PersonalDutyViewStore.load({
    			params:
    				{
    					personalGradeId : personalGradeId
    				}
    			});
    	}
	}
});



