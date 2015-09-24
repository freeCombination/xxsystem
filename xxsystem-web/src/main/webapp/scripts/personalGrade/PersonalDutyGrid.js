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
grade.personalDuty.PersonalDutyStore = Ext.create('Ext.data.Store', {
	model : 'grade.personalDuty.PersonalDutyModel',
	proxy : {
		type : "format",
		url : basePath + "/personalGrade/getPersonalDutyList.action"
	}
});

var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    clicksToEdit: 1
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
			dataIndex : "completion",
			field: {
            	xtype:'textarea',
            	style: {
                    marginTop: '38px'
                },
            	height:60,
            	maxLength:1000,
            	regex : new RegExp('^([^<^>])*$'),
                regexText : '不能包含特殊字符！',
                listeners:{
                	'change':function(thiz, newValue,oldValue){
                		var row = grade.personalDuty.PersonalDutyGrid.getSelectionModel().getSelection();
        				var id = row[0].data.id;
        					Ext.Ajax.request({ 
        				 		url: basePath+'/personalGrade/updatePersonalDuty.action',
        					    method: "post",
        					    params:{
        					    			id:id,
        					    			completion:newValue
        					    		}, 
        					    success: function(response, config){ 
        					    	
        					    }, 
        					    failure: function(){ 
        					       
        					    }
        					});
                	}
                }
            }
		}
          ]

/**
 * 定义Grid
 */
grade.personalDuty.PersonalDutyGrid = Ext.create("Ext.grid.Panel", {
	region : "center",
	store : grade.personalDuty.PersonalDutyStore,
	columns : cm,
	plugins: [cellEditing],
	tbar : [
	        "  岗位职责",
            "->",
            {
                xtype:'button',
                disabled:false,
                text:'导出',
                iconCls:'add-button',
                handler:function(){
                	
                }
            }
	        ]
});


