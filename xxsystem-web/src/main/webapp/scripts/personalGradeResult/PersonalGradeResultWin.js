/**
 * 个人评分窗口
 * @date 20150919
 * @author wujl
 */

/**
 * 定义个人评分基础信息form
 */
grade.personalGradeResult.PersonalGradeResultForm = Ext.create("Ext.form.Panel", {
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
            	id:'personalGradeId',
        		name : 'id',
        		hidden : true
        	},
        	{
        		fieldLabel : '存在问题',
        		name : 'problem',
        		vtype:'filterHtml',
        		maxLength : 500,
        		xtype : 'textareafield'
        	},
        	{
        		fieldLabel : '工作计划',
        		name : 'workPlan',
        		vtype:'filterHtml',
        		maxLength : 500,
        		xtype : 'textareafield'
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
			grade.personalGradeResult.PersonalGradeResultWin.hide();
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



