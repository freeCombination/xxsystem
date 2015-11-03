/**
 * 个人评分明细列表
 * @date 20150919
 * @author wujl
 */




/**
 * 定义Model
 */
Ext.define("grade.personalGradeResult.PersonalGradeResultDetailsModel",{
					extend:"Ext.data.Model",
					fields:[
					    {name:'id'},
						{name:'detailsId'},
						{name:'personalGradeId'},
						{name:'gradeUser'},
						{name:'gradeOrg'},
						{name:'userName'},
						{name:'orgName'},
						{name:'score'},
						{name:'gradeDate'},
						{name:'gradeYear'},
						{name:'title'},
						{name:'problem'},
						{name:'workPlan'},
						{name:'responsibilities'},
						{name:'birthDay'},
						{name:'gender'},
						{name:'educationBackground'},
						{name:'politicsStatus'},
						{name:'roleName'},
						{name:'indexTypeName'},
						{name:'state'}
					]
	});

/**
 * 定义Store
 */
grade.personalGradeResult.PersonalGradeResultDetailsStore = Ext.create('Ext.data.Store', {
	model : 'grade.personalGradeResult.PersonalGradeResultDetailsModel',
	proxy : {
		type : "format",
		url : basePath + "/personalGrade/getPersonalGradeResultDetailsList.action"
	}
});

//合并行
grade.personalGradeResult.PersonalGradeResultDetailsStore.addListener('load', function(store,records) {
	mergeCells(grade.personalGradeResult.PersonalGradeResultGrid, [1,2,3,4,5,6]);
});

grade.personalGradeResult.PersonalGradeResultDetailsStore.getProxy().setExtraParam("state", 1);

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
 * 列表字段
 */
var cm = [
		{
			xtype : "rownumberer",
			text : '序号',
			width : 60,
			align : "center"
		}, {
			header : "标题",
			dataIndex : "title",
        	renderer : function(value, p, record) {
    		    return '<div style="white-space:normal;">' + value + '</div>';
    		}
		},
		{
			header : "年份",
			dataIndex : "gradeYear"
		},
		{
			header : "职工姓名",
			dataIndex : "gradeUser"
		},
		{
			header : "部门",
			dataIndex : "gradeOrg"
		},
		{
			header : "现任岗位",
			dataIndex : "responsibilities"
		},
		{
			header : "评分指标",
			dataIndex : "indexTypeName"
		},
		{
			header : "评分职员",
			dataIndex : "userName"
		},
		{
			header : "评分职员角色",
			dataIndex : "roleName"
		},
		{
			header : "得分",
			dataIndex : "score"
		}
          ];

/**
 * 定义Grid
 */
grade.personalGradeResult.PersonalGradeResultGrid = Ext.create("Ext.grid.Panel", {
	title : '个人评分明细',
	region : "center",
	bbar : Ext.create("Ext.PagingToolbar", {
		store : grade.personalGradeResult.PersonalGradeResultDetailsStore
	}),
	store : grade.personalGradeResult.PersonalGradeResultDetailsStore,
	columns : cm,
	tbar : [
	'&nbsp;部门',canpDeptQuery,
	'职工姓名', {
		xtype : 'textfield',
		stripCharsRe : /^\s+|\s+$/g, // 禁止输入空格
		id : 'inputGradeUser'
	},
	'评分职工', {
		xtype : 'textfield',
		stripCharsRe : /^\s+|\s+$/g, // 禁止输入空格
		id : 'inputUserName'
	},
	{
		text : "查询",
		iconCls : "search-button",
		handler : function(button) {
			var proxy = grade.personalGradeResult.PersonalGradeResultDetailsStore.getProxy();
			proxy.setExtraParam("inputGradeUser", Ext.getCmp('inputGradeUser').getValue());
			proxy.setExtraParam("inputUserName", Ext.getCmp('inputUserName').getValue());
			proxy.setExtraParam("canpDeptQuery", Ext.getCmp('canpDeptQuery').getValue());
			proxy.setExtraParam("state", 1);
			grade.personalGradeResult.PersonalGradeResultDetailsStore.loadPage(1);
		}
	}
	  ]
});



