/**
 * 个人评分窗口
 * @date 20150919
 * @author wujl
 */

/**
 * 定义个人评分基础信息form
 */
grade.personalGradeResult.PersonalGradeResultForm = Ext.create("Ext.form.Panel", {
	//title:'基本信息',
	layout : 'form',
	region: "north",
	bodyStyle : 'padding:15px 10px 0 0',
	border : false,
	labelAlign : 'right',
	fieldDefaults : {
		labelWidth : 60,
		labelAlign : 'right'
	},
	defaults : {
		anchor : '70%',
		width : 100
	},
	defaultType : 'textfield',
    items: [
            {
            	id:'id',
        		name : 'id',
        		hidden : true
        	},
            {
            	id:'personalGradeId',
        		name : 'personalGradeId',
        		hidden : true
        	},
        	{
        		fieldLabel : '得分',
        		name : 'score',
        		id:'score',
        		xtype : 'numberfield'
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




grade.personalGradeResult.PersonalGradeResultWin = Ext.create("Ext.window.Window", {
	height : 400,
	width : 600,
	layout: 'border',
	items : [ grade.personalGradeResult.PersonalGradeResultForm , grade.personalDutyResult.PersonalDutyResultGrid],
	buttons : [ {
		text : '确定',
		id:'result_submit',
		handler : function() {
			if (grade.personalGradeResult.PersonalGradeResultForm.form.isValid()) {
				grade.personalGradeResult.PersonalGradeResultForm.form.submit({
					success : function(form, action) {
						Ext.Msg.showTip(action.result.msg);
						grade.personalGradeResult.PersonalGradeResultStore.loadPage(1);
						grade.personalGradeResult.PersonalGradeResultWin.hide();
					},
					failure : function(form, action) {
						Ext.Msg.showError(action.result.msg);
					}
				});
			}
		}
	}, {
		text : '关闭',
		handler : function() {
			grade.personalGradeResult.PersonalGradeResultWin.close();
		}
	} ],
	listeners: {
    	afterrender: function(){
    		var personalGradeId = Ext.getCmp('personalGradeId').getValue();
    		grade.personalDutyResult.PersonalDutyResultStore.load({
    			params:
    				{
    					personalGradeId : personalGradeId
    				}
    			});
    	}
	}
});



