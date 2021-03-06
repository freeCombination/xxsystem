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
	region: "south",
	bodyStyle : 'padding:0px 5px 0 0',
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
	defaultType : 'textfield',
    items: [
            {
            	id:'personalGradeId',
        		name : 'id',
        		hidden : true
        	},
        	{
        		fieldLabel : '存在问题',
        		allowBlank:false, 
        		name : 'problem',
        		//vtype:'filterHtml',
            	regex : new RegExp('^([^<^>])*$'),
                regexText : '不能包含特殊字符！',
        		//maxLength : 500,
        		xtype : 'textareafield',
        		height:50
        	},
        	{
        		fieldLabel : '工作计划',
        		allowBlank:false, 
        		name : 'workPlan',
            	regex : new RegExp('^([^<^>])*$'),
                regexText : '不能包含特殊字符！',
        		xtype : 'textareafield',
        		height:50
        	},
        	{
        		fieldLabel : '思想政治',
        		allowBlank:false, 
        		name : 'politicalThought',
            	regex : new RegExp('^([^<^>])*$'),
                regexText : '不能包含特殊字符！',
        		xtype : 'textareafield',
        		height:50
        	},
        	{
        		fieldLabel : '岗位能力',
        		allowBlank:false, 
        		name : 'postAbility',
            	regex : new RegExp('^([^<^>])*$'),
                regexText : '不能包含特殊字符！',
        		xtype : 'textareafield',
        		height:50
        	}
            ]
});




grade.personalGrade.PersonalGradeWin = Ext.create("Ext.window.Window", {
	height : 560,
	width : 800,
	layout: 'border',
	items : [grade.personalDuty.PersonalDutyGrid, grade.personalGrade.PersonalGradeForm],
	buttons : [ {
		text : '确定',
		handler : function() {
			if (grade.personalGrade.PersonalGradeForm.form.isValid()) {
				grade.personalGrade.PersonalGradeForm.form.submit({
					success : function(form, action) {
						Ext.Msg.showTip(action.result.msg);
						grade.personalGrade.PersonalGradeStore.loadPage(1);
						grade.personalGrade.PersonalGradeWin.close();
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
			grade.personalGrade.PersonalGradeWin.close();
		}
	} ],
	listeners: {
    	afterrender: function(){
    		var personalGradeId = Ext.getCmp('personalGradeId').getValue();
    		grade.personalDuty.PersonalDutyStore.load({
    			params:
    				{
    					personalGradeId : personalGradeId
    				}
    			});
    	}
	}
});



