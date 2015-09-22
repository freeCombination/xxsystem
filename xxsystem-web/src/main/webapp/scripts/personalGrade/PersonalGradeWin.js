/**
 * 个人评分窗口
 * @date 20150919
 * @author wujl
 */

/**
 * 定义个人评分基础信息form
 */
grade.personalGrade.PersonalGradeForm = Ext.create("Ext.form.Panel", {
	layout : 'form',
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


grade.personalGrade.PersonalGradeWin = Ext.create("Ext.window.Window", {
	height : 400,
	width : 600,
	items : [ grade.personalGrade.PersonalGradeForm ],
	buttons : [ {
		text : '确定',
		handler : function() {
			if (grade.personalGrade.PersonalGradeForm.form.isValid()) {
				grade.personalGrade.PersonalGradeForm.form.submit({
					success : function(form, action) {
						Ext.Msg.showTip(action.result.msg);
						grade.personalGrade.PersonalGradeStore.loadPage(1);
						grade.personalGrade.PersonalGradeWin.hide();
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
			grade.personalGrade.PersonalGradeWin.hide();
		}
	} ]
});



