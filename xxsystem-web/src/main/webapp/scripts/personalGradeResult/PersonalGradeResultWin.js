/**
 * 个人评分窗口
 * @date 20150919
 * @author wujl
 */

var row1 = {  
        layout:'column',    //从左往右布局  
        border : false,
        items:[{  
            columnWidth:.3, //该列有整行中所占百分比  
            layout:'form',  //从上往下布局  
            border : false,
            items:[{  
                xtype:'displayfield',
                name:'gradeUser',
                fieldLabel:'姓    名'
            }]  
        },{  
            columnWidth:.3,  
            layout:'form',  
            border : false,
            items:[{  
                xtype:'displayfield',  
                name:'gender',
                fieldLabel:'性    别'
            }]  
        },{  
            columnWidth:.3,  
            layout:'form', 
            border : false,
            items:[{  
                xtype:'displayfield',  
                name:'birthDay',
                fieldLabel:'出生年月'
            }]  
        }]  
    }; 

var row2 = {  
        layout:'column',    //从左往右布局  
        border : false,
        items:[{  
            columnWidth:.3, //该列有整行中所占百分比  
            layout:'form',  //从上往下布局  
            border : false,
            items:[{  
                xtype:'displayfield',  
                name:'politicsStatus',
                fieldLabel:'政治面貌'
            }]  
        },{  
            columnWidth:.3,  
            layout:'form',  
            border : false,
            items:[{  
                xtype:'displayfield',  
                name:'educationBackground',
                fieldLabel:'最高学历'
            }]  
        },{  
            columnWidth:.3,  
            layout:'form', 
            border : false,
            items:[{  
                xtype:'displayfield',  
                name:'educationBackground',
                fieldLabel:'参加工作时间'
            }]  
        }]  
    }; 

//行3  
var row3 = {  
	layout:'column',    //从左往右布局  
	border : false,
	items:[{  
	    columnWidth:.5, //该列有整行中所占百分比  
	    layout:'form',  //从上往下布局  
	    border : false,
	    items:[{  
	        xtype:'displayfield',
	        name:'responsibilities',
	        fieldLabel:'现任岗位' 
	    }]  
		},{  
		    columnWidth:.5,  
		    layout:'form',  
		    border : false,
		    items:[{  
		        xtype:'displayfield',  
		        name:'responsibilities',
		        fieldLabel:'任现岗位时间'
		    }]  
		}]  
};

var row4 = {  
        layout:'form',  
        border : false,
        items:[{  
            xtype:'displayfield',  
            name:'problem',
            fieldLabel:'存在的问题' 
        }]  
    }; 

var row5 = {  
        layout:'form', 
        border : false,
        items:[{  
            xtype:'displayfield',  
            name:'workPlan',
            fieldLabel:'明年工作计划'
        }]  
    }; 

var row6 = {  
        layout:'form', 
        border : false,
        items:[{  
            xtype:'textarea',  
            name:'evaluation',
            id:'evaluation',
        	regex : new RegExp('^([^<^>])*$'),
            regexText : '不能包含特殊字符！',
    		maxLength : 500,
            fieldLabel:'评价'
        }]  
    }; 

var row7 = {  
        layout:'form', 
        border : false,
        items:[{  
            xtype:'numberfield',  
            name:'score',
            id:'score',
            maxValue: 100,  
            minValue: 0,
            fieldLabel:'得分'
        }]  
    }; 

grade.personalGradeResult.PersonalGradeResultForm = Ext.create("Ext.form.Panel", {  
    //renderTo:Ext.getBody(),  
	layout : 'form',
	region: "north",
    width:780,  
    autoHeight:true,  
    frame:true,  
    border : false,
    labelWidth:65,  
    labelAlign:'right',  
    style:'padding:10px',  
    items:[
           {
		   		id:'id',
				name : 'id',
				xtype:'textfield',
				hidden : true
				},
		   {
				id:'personalGradeId',
				name : 'personalGradeId',
				xtype:'textfield',
				hidden : true
			},
			 {
			   	id:'gradeUserType',
				name : 'gradeUserType',
				xtype:'textfield',
				hidden : true
			 },
           row1,row2,row3,row4,row5,row6,row7]
});

/**
 * 定义个人评分基础信息form
 */
grade.personalGradeResult.PersonalGradeResultWin = Ext.create("Ext.window.Window", {
	height : 500,
	width : 800,
	layout: 'border',
	items : [grade.personalGradeResult.PersonalGradeResultForm,grade.personalDutyResult.PersonalDutyResultGrid],
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
    		var gradeUserType = Ext.getCmp('gradeUserType').getValue();
    		if (gradeUserType == 0) {
    			Ext.getCmp('evaluation').hide();
			}else{
				Ext.getCmp('evaluation').show();
			}
    		grade.personalDutyResult.PersonalDutyResultStore.load({
    			params:
    				{
    					personalGradeId : personalGradeId
    				}
    			});
    	}
	}
});



