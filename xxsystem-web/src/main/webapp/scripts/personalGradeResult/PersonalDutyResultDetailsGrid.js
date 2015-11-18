/**
 * 个人评分结果明细列表
 * 
 * @date 20150923
 * @author wujl
 */

/**
 * 定义Model
 */
Ext.define("grade.personalDutyResultDetails.PersonalDutyResultDetailsModel",{
					extend:"Ext.data.Model",
					fields:[
						{name:'id'},
						{name:'score'},
						{name:'roleId'},
						{name:'roleName'},
						{name:'indexTypeId'},
						{name:'indexTypeName'}
					]
	});

Ext.define("grade.personalDutyResultDetails.ScoreStoreModel",{
	extend:"Ext.data.Model",
	fields:[
		{name:'score'}
	]
});

/**
 * 定义Store
 */
grade.personalDutyResultDetails.PersonalDutyResultDetailsStore = Ext.create('Ext.data.Store', {
	model : 'grade.personalDutyResultDetails.PersonalDutyResultDetailsModel',
	proxy : {
		type : "format",
		url : basePath + "/personalGrade/getPersonalResultDetailsList.action"
	}
});


grade.personalDutyResultDetails.ScoreStore = Ext.create('Ext.data.Store', {
    model: 'grade.personalDutyResultDetails.ScoreStoreModel',
    proxy: {
		type : "format",
		url : basePath + "/personalGrade/getScoreList.action"
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
			header : "评分指标",
			dataIndex : "indexTypeName"
		},
		{
			header : "评分人角色",
			dataIndex : "roleName"
		},
		{
			header : "得分",
			dataIndex : "score",
			itemId:"score",
            field:{
                xtype:'combo',
                maxLength:10,
                regex : new RegExp('^([^<^>])*$'),
                regexText : '不能包含特殊字符！',
                allowBlank: false,
                //editable: true,
                displayField: 'score',
                valueField: 'score',
                store:grade.personalDutyResultDetails.ScoreStore,
                listeners : {
                    'change' : function(combo,newValue,oldValue,eOpts){
                  		var row = grade.personalDutyResultDetails.PersonalDutyResultDetailsGrid.getSelectionModel().getSelection();
        				var id = row[0].data.id;
        					Ext.Ajax.request({ 
        				 		url: basePath+'/personalGrade/updatePersonalResultDetails.action',
        					    method: "post",
        					    params:{
        					    			id:id,
        					    			score:newValue
        					    		}, 
        					    success: function(response, config){ 
        					    	
        					    }, 
        					    failure: function(){ 
        					       
        					    }
        					});
                    },
                    'focus':function(combo, The, eOpts){
                    	//选择的时候重新加载得分列表
                  		var row = grade.personalDutyResultDetails.PersonalDutyResultDetailsGrid.getSelectionModel().getSelection();
        				var id = row[0].data.id;
                		grade.personalDutyResultDetails.ScoreStore.load({
                			params:
            				{
                				id : id
            				}
                		});
                    }
                }
            }
		}
          ];



/**
 * 定义Grid
 */
grade.personalDutyResultDetails.PersonalDutyResultDetailsGrid = Ext.create("Ext.grid.Panel", {
	title:'评分明细',
	//region : "center",
	height : 130,
	width : 1190,
	boder:false,
	store : grade.personalDutyResultDetails.PersonalDutyResultDetailsStore,
	plugins: [cellEditing],
	columns : cm
});




