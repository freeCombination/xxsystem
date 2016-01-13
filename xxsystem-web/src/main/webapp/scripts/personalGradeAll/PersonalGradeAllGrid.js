/**
 * 个人评分列表
 * @date 20150919
 * @author wujl
 */

/**
 * 定义Model
 */
Ext.define("grade.personalGrade.PersonalGradeModel",{
					extend:"Ext.data.Model",
					fields:[
						{name:'id'},
						{name:'gradeYear'},
						{name:'problem'},
						{name:'workPlan'},
						{name:'compositeScores'},
						{name:'status'},
						{name:'responsibilities'},
						{name:'userName'},
						{name:'orgName'},
						{name:'totalPersonCount'},
						{name:'commitPersonCount'},
						{name:'isScoreChange'},
						{name:'title'}
					]
	});

/**
 * 定义Store
 */
grade.personalGrade.PersonalGradeStore = Ext.create('Ext.data.Store', {
	model : 'grade.personalGrade.PersonalGradeModel',
	proxy : {
		type : "format",
		url : basePath + "/personalGrade/getAllPersonalGradeList.action"
	}
});

grade.personalGrade.PersonalGradeStore.getProxy().setExtraParam("status","1,2");


grade.personalGrade.PersonalGradeStore.addListener('beforeload', function(st, rds, opts) {
	grade.personalGrade.PersonalGradeGrid.getSelectionModel().clearSelections();
	Ext.getCmp('export-button').setDisabled(true);
	Ext.getCmp('query-button').setDisabled(true);
	Ext.getCmp('delete-button').setDisabled(true);
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
			header : "标题",
			dataIndex : "title"
		},
		{
			header : "年份",
			dataIndex : "gradeYear"
		},
		{
			header : "部门",
			dataIndex : "orgName"
		},
		{
			header : "职工姓名",
			dataIndex : "userName"
		},
		{
			header : "现任岗位",
			dataIndex : "responsibilities"
		},
		{
			header : "已提交人数/总人数",
			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				var totalPersonCount = record.get('totalPersonCount');
				var commitPersonCount = record.get('commitPersonCount');
				var status = record.get('status');
				var personalGradeId = record.get('id');
				if (status == '0') {
					return "" ;
				} else {
					return "<span><a style='ext-decoration:underline;color:#5555FF;cursor:pointer' title='查看未评分人员列表' onclick='getResultPersonList("+personalGradeId+");'>"+commitPersonCount + "/" + totalPersonCount+"</a></span>";
				}
			}
		},
		{
			header : "状态",
			dataIndex : "status",
			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				if(value == '0'){
					cellmeta.attr = 'ext:qtip="' + '草稿' + '<br/>"';
					return '草稿';
				}else if(value == '1'){
					cellmeta.attr = 'ext:qtip="' + '已提交' + '<br/>"';
					return '已提交';
				}else if(value == '2'){
					cellmeta.attr = 'ext:qtip="' + '已归档' + '<br/>"';
					return '已归档';
				}
			}
		},
		{
			header : "综合得分",
			dataIndex : "compositeScores"
		},
    	{
			header: "操作",dataIndex: "",width:50,
    		renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					var status = record.get('status');
					var id = record.get('id');
					//已归档的数据可以刷新得分，得分在两种情况下会出现变化，1.部门总得分发生变化 ； 2.对于已经提交的人员，管理员对用户进行删除或者禁用操作。
					if(status == 2){
						return '<img title="刷新个人得分" src="'+basePath+'/images/icons/refresh.gif" style="cursor: pointer" onclick="grade.personalGrade.refreshScore('+id+')"/>';
					}else{
						return '';
					}
				},
				align:'center'
    	}
      ];

/**
 * 刷新得分
 */
grade.personalGrade.refreshScore = function(id){
	Ext.Ajax.request({
    	url: basePath+'/personalGrade/refreshScore.action',
 		params: {
 	   		id:id
 	   	},
 		success: function(response, opts) {
 	      	var result = Ext.decode(response.responseText);
 	      	var flag = result.success;
 	      	if(flag){
 	      		grade.personalGrade.PersonalGradeStore.load();
 	      		Ext.Msg.showTip(result.msg);
 	      	}else{
 	      		Ext.Msg.showInfo(result.msg);
 	      	}
 	   	}
	});
};

/**
 * 查看未评分人员列表
 */
var getResultPersonList = function(personalGradeId){
	var proxy = grade.personalUser.PersonalUserStore.getProxy();
	proxy.setExtraParam("personalGradeId", personalGradeId);
	//proxy.setExtraParam("state", 0);
	grade.personalUser.PersonalUserStore.load();
	grade.personalUser.PersonalUserWin.show();
};

/**
 * 树形模型
 */
Ext.define('treeModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'nodeId',type: 'int'}, 
        {name: 'parentId',type: 'int'}, 
        {name: 'id',type: 'int',mapping:'nodeId'},
        {name: 'text',type: 'string'} 
    ]
});

/**
 * 部门
 */
var canpDeptQuery = Ext.create("Ext.ux.TreePicker", {
    allowBlank: false,
    value:'0',
    id:'canpDeptQuery',
    displayField: 'text',
    rootVisible: true,
    valueField: 'id',
    minPickerHeight: 200, //最小高度，不设置的话有时候下拉会出问题
    editable: false, //启用编辑，主要是为了清空当前的选择项
    enableKeyEvents: true, //激活键盘事件
    store: Ext.create("Ext.data.TreeStore", {
        model: 'treeModel',
        nodeParam:'parentId',
        autoLoad:false,
        clearOnLoad :true,
        proxy: {
            type: 'ajax',
            reader:{
                type: 'json'
            },
            folderSort: true,
            sorters: [{
                 property: 'nodeId',
                 direction: 'DESC'
            }],
            url :basePath+'/org/getUnitTreeListNotCheck.action'
        },
        root: {
            expanded: true,
            id:"0",
            text:'全部'
        }
    })
});

/**
 * 定义Grid
 */
grade.personalGrade.PersonalGradeGrid = Ext.create("Ext.grid.Panel", {
	title : '个人评分汇总',
	region : "center",
	bbar : Ext.create("Ext.PagingToolbar", {
		store : grade.personalGrade.PersonalGradeStore
	}),
	selModel : Ext.create("Ext.selection.CheckboxModel"),
	store : grade.personalGrade.PersonalGradeStore,
	columns : cm,
	tbar : [ 
	'&nbsp;部门',canpDeptQuery,
	'职工姓名', {
		xtype : 'textfield',
		stripCharsRe : /^\s+|\s+$/g, // 禁止输入空格
		id : 'inputGradeUser'
	},
	,'年份',
	{
		id:'gradeYear',
		name:'gradeYear',
		width:80,
		xtype:"textfield",
		value:Ext.Date.format(new Date(),"Y"),
		listeners :{
				'render' : function(p){
					p.getEl().on('click',function(){
						WdatePicker({readOnly:true,dateFmt:'yyyy',maxDate:Ext.Date.format(new Date(),"Y"),onpicked:function(){$dp.$('reportDate-inputEl').focus();}});
					});
				}
		}
	}, {
		text : "查询",
		iconCls : "search-button",
		handler : function(button) {
			var proxy = grade.personalGrade.PersonalGradeStore.getProxy();
			proxy.setExtraParam("gradeYear", button.prev().getValue());
			proxy.setExtraParam("inputGradeUser", Ext.getCmp('inputGradeUser').getValue());
			proxy.setExtraParam("canpDeptQuery", Ext.getCmp('canpDeptQuery').getValue());
			grade.personalGrade.PersonalGradeStore.loadPage(1);
		}
	},
	"->",
	 {
		xtype : 'button',
		text : '查看',
		disabledExpr : "$selectedRows != 1",// $selected 表示选中的记录数不等于1
		disabled : true,
		id:'query-button',
		iconCls : 'query-button',
		handler : function() {
			grade.personalGrade.ViewPersonalGradeAll();
		}
	},
	{
		xtype : 'button',
		text : '删除',
		id:'delete-button',
		disabledExpr : "$selectedRows == 0",
		disabled : true,
		//hidden:true,
		iconCls : 'delete-button',
		handler : function() {
			grade.personalGrade.deletePersonalGrade();
		}
	},
    {
        xtype:'button',
        disabled:false,
        text:'导出',
        disabledExpr : "$selectedRows != 1 || $status!='2' ",
        disabled : true,
        iconCls:'export-button',
        id:'export-button',
        handler:function(){
        	var row = grade.personalGrade.PersonalGradeGrid.getSelectionModel().getSelection();
        	var personalGradeId = row[0].data.id;
        	Ext.Ajax.request({ 
		 		url: basePath+'/personalGrade/exportPersonalGradeAll.action',
			    method: "post",
			    params:{
			    			personalGradeId:personalGradeId
			    		}, 
			    success: function(response, config){ 
			    	var result = Ext.decode(response.responseText);
			    	if(result.success==false){
                        Ext.Msg.alert('系统提示', "导出失败!");
                    }else{
                        var url = basePath+"/personalGrade/exportPersonalGradeAllFile.action";
                        window.location.href = url;
                    }
			    }, 
			    failure: function(){ 
			       
			    }
			});
        }
    },
    {
        xtype:'button',
        text:'导出Excel',
        iconCls:'excel-button',
        id:'excel-button',
        handler:function(){
        	Ext.Ajax.request({ 
		 		url: basePath+'/personalGrade/exportPersonalAllList.action',
			    method: "post",
			    params:{
					    	gradeYear:Ext.getCmp('gradeYear').getValue(),
					    	inputGradeUser:Ext.getCmp('inputGradeUser').getValue(),
					    	canpDeptQuery:Ext.getCmp('canpDeptQuery').getValue(),
					    	start:"0",
					    	limit:"999999999",
					    	status:"1,2"
			    		}, 
			    success: function(response, config){ 
			    	var result = Ext.decode(response.responseText);
			    	if(result.success==false){
                        Ext.Msg.alert('系统提示', "导出失败!");
                    }else{
                        var url = basePath+"/personalGrade/exportPersonalAllListFile.action";
                        window.location.href = url;
                    }
			    }, 
			    failure: function(){ 
			       
			    }
			});
        }
    }
	]
});

/**
 * 查看详情
 */
grade.personalGrade.ViewPersonalGradeAll = function(){
	var row = grade.personalGrade.PersonalGradeGrid.getSelectionModel().getSelection();
	var id = row[0].data.id;
	var basicForm = grade.personalGrade.PersonalGradeViewWin.down('form').getForm();
	basicForm.reset();
	basicForm.findField('id').setValue(id);
	basicForm.load({
		url : basePath + '/personalGrade/getPersonalGradeById.action',
		params : {
			id : id
		}
	});
	grade.personalGrade.PersonalGradeViewWin.show();
};


/**
 * 调用后台删除
 */
grade.personalGrade.deletePersonalGrade = function() {
	var rows = grade.personalGrade.PersonalGradeGrid.getSelectionModel().getSelection();
	var ids = "";
	for (var i = 0; i < rows.length; i++) {
		ids += (rows[i].data.id + ",");
	}
	ids = ids.substring(0, ids.length - 1);
	Ext.Msg.confirm(SystemConstant.alertTitle, "确认删除这" + rows.length + "条数据吗?删除个人评分后，员工需重新生成个人评分！", function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url : basePath + '/personalGrade/deletePersonalGrade.action',
				params : {
					ids : ids
				},
				success : function(res, options) {
					var data = Ext.decode(res.responseText);
					if (data.success) {
						Ext.Msg.showTip(data.msg);
						grade.personalGrade.PersonalGradeStore.loadPage(1);
					} else {
						Ext.Msg.showError(data.msg);
					}
				}
			});
		}
	});
};



