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
                id:'gradeUser',
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
                name:'jobStartDate',
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
		        name:'respChangeDate',
		        fieldLabel:'现任岗位时间'
		    }]  
		}]  
};

var row4 = {  
        layout:'form',  
        border : false,
        items:[{  
            xtype:'displayfield',  
            name:'problem',
            fieldLabel:'二、存在的问题' ,
    		renderer:function(value){
    			return value.replace(/\n/g,'<br />');
    		}
        }]  
    }; 

var row5 = {  
        layout:'form', 
        border : false,
        items:[{  
            xtype:'displayfield',  
            name:'workPlan',
            fieldLabel:'三、明年工作计划',
    		renderer:function(value){
    			return value.replace(/\n/g,'<br />');
    		}
        }]  
    }; 

var row6 = {  
        layout:'form', 
        border : false,
        items:[{  
            xtype:'textarea',  
            name:'evaluation',
            id:'evaluation',
            hidden:true,
        	regex : new RegExp('^([^<^>])*$'),
            regexText : '不能包含特殊字符！',
    		maxLength : 500,
            fieldLabel:'部门主任评价'
        }]  
    }; 

var row7 = {  
        layout:'form', 
        border : false,
        items:[{  
            xtype:'textarea',  
            name:'evaluation1',
            id:'evaluation1',
            hidden:true,
        	regex : new RegExp('^([^<^>])*$'),
            regexText : '不能包含特殊字符！',
    		maxLength : 500,
            fieldLabel:'分管领导评价'
        }]  
    }; 

var row8 = {  
        layout:'form', 
        border : false,
        items:[{  
            xtype:'textarea',  
            name:'evaluation2',
            id:'evaluation2',
            hidden:true,
        	regex : new RegExp('^([^<^>])*$'),
            regexText : '不能包含特殊字符！',
    		maxLength : 500,
            fieldLabel:'其他所领导评价'
        }]  
    }; 

var row9 = {  
        layout:'form', 
        border : false,
        items:[{  
            xtype:'textarea',  
            name:'evaluation3',
            id:'evaluation3',
            hidden:true,
        	regex : new RegExp('^([^<^>])*$'),
            regexText : '不能包含特殊字符！',
    		maxLength : 500,
            fieldLabel:'所领导评价'
        }]  
    }; 


/*var row7 = {  
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
    }; */

grade.personalGradeResult.PersonalGradeResultForm = Ext.create("Ext.form.Panel", {  
	layout : 'form',
	//region: "north",
    //width:780,  
    //height:120,
    frame:true,  
    border : false,
    labelWidth:65,  
    labelAlign:'right',  
    //style:'padding:10px',  
    items:[row1,row2,row3]
});

grade.personalGradeResult.PersonalGradeResultFormProblem = Ext.create("Ext.form.Panel", {  
	layout : 'form',
    frame:true,  
    border : false,
    labelWidth:65,  
    labelAlign:'right',  
    items:[row4,row5]
});

grade.personalGradeResult.PersonalGradeResultForm1 = Ext.create("Ext.form.Panel", {  
	layout : 'form',
	//region: "south",
    //width:780,  
    //height:180,
    frame:true,  
    border : false,
    labelWidth:65,  
    labelAlign:'right',  
    //style:'padding:10px',  
    items:[ {
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
	 },row6,row7,row8,row9]
});

var htmlDes = new Ext.Panel({
	id:"des",
	title:"评分说明",
	border:false,
	html:"<div>&nbsp;&nbsp;&nbsp;&nbsp;对员工评价打分的最高分值为120分，评价分值高于120分的为无效评价。110≤得分≤120绩效等级为A级，90≤得分<110绩效等级为B级，70≤得分<90绩效等级为C级，60≤得分<70绩效等级为D级，得分<60绩效等级为E级，各部门按15%以内的比例确定A级绩效员工。</div>"
});

/**
 * 定义个人评分基础信息form
 */
grade.personalGradeResult.PersonalGradeResultWin = Ext.create("Ext.window.Window", {
	height : 520,
	width : 1200,
	border:false,
	items : [{
		xtype:'panel',
		bodyStyle :"overflow-x:hidden;overflow-y:auto",
		border:false,
		items : [grade.personalGradeResult.PersonalGradeResultForm,grade.personalDutyResult.PersonalDutyResultGrid,grade.personalGradeResult.PersonalGradeResultFormProblem,htmlDes,grade.personalDutyResultDetails.PersonalDutyResultDetailsGrid,grade.personalGradeResult.PersonalGradeResultForm1]
	}],
	buttons : [ {
		text : '确定',
		id:'result_submit',
		handler : function() {
			if (grade.personalGradeResult.PersonalGradeResultForm1.form.isValid()) {
				grade.personalGradeResult.PersonalGradeResultForm1.form.submit({
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
		show: function(){
    		var personalGradeId = Ext.getCmp('personalGradeId').getValue();
    		var personalGradeResultId = Ext.getCmp('id').getValue();
    		var gradeUserType = Ext.getCmp('gradeUserType').getValue();
    		if (gradeUserType != '') {
    			var gradeUserTypeArr = gradeUserType.split(',');
    			if (gradeUserTypeArr[0]=='true') {
    				Ext.getCmp('evaluation').show();
				}else{
					Ext.getCmp('evaluation').hide();
				}
    			if (gradeUserTypeArr[1]=='true') {
    				Ext.getCmp('evaluation1').show();
				}else{
					Ext.getCmp('evaluation1').hide();
				}
    			if (gradeUserTypeArr[2]=='true') {
    				Ext.getCmp('evaluation2').show();
				}else{
					Ext.getCmp('evaluation2').hide();
				}
    			if (gradeUserTypeArr[3]=='true') {
    				Ext.getCmp('evaluation3').show();
				}else{
					Ext.getCmp('evaluation3').hide();
				}
			}
    		grade.personalDutyResult.PersonalDutyResultStore.load({
    			params:
    				{
    					personalGradeId : personalGradeId
    				}
    			});
    		grade.personalDutyResultDetails.PersonalDutyResultDetailsStore.load({
    			params:
				{
    				personalGradeResultId : personalGradeResultId
				}
			});
    	}
	}
});


