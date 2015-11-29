/**
 * 个人评分 评分人员列表
 * 
 * @date 20150923
 * @author wujl
 */

/**
 * 定义Model
 */
Ext.define("grade.personalUser.PersonalUserModel",{
					extend:"Ext.data.Model",
					fields:[
						{name:'id'},
						{name:'userName'},
						{name:'state'}
					]
	});

/**
 * 定义Store
 */
grade.personalUser.PersonalUserStore = Ext.create('Ext.data.Store', {
	model : 'grade.personalUser.PersonalUserModel',
	proxy : {
		type : "format",
		url : basePath + "/personalGrade/getPersonalGradeResultDetailsCountsList.action"
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
			header : "评分人",
			dataIndex : "userName"
		}, {
			header : "状态",
			dataIndex : "state",
			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				if(value == 0){
					return '未处理';
				}else if(value == 1){
					return '已提交';
				}
			}
		},
    	{
			header: "操作",dataIndex: "",width:50,
    		renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					var state = record.get('state');
					var id = record.get('id');
					if(state == 0){
						
					}else if(state == 1){
						return '<img title="撤回评分" src="'+basePath+'/images/icons/revokeBack.gif" style="cursor: pointer" onclick="grade.personalUser.backCommit('+id+')"/>';
					}
				},
				align:'center'
    	}
          ];

grade.personalUser.backCommit = function (id){
     	Ext.Ajax.request({
        	url: basePath+'/personalGrade/backCommit.action',
     		params: {
     	   		id:id
     	   	},
     		success: function(response, opts) {
     	      	var result = Ext.decode(response.responseText);
     	      	var flag = result.success;
     	      	if(flag){
     	      		grade.personalUser.PersonalUserStore.load();
     	      		grade.personalGrade.PersonalGradeStore.load();
     	      		Ext.Msg.showTip(result.msg);
     	      	}else{
     	      		Ext.Msg.showInfo(result.msg);
     	      	}
     	   	}
    	});
	};

/**
 * 定义Grid
 */
grade.personalUser.PersonalUserGrid = Ext.create("Ext.grid.Panel", {
	region : "center",
	store : grade.personalUser.PersonalUserStore,
	columns : cm
});



