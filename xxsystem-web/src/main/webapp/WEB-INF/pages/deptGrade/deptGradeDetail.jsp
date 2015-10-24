<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/doc_type.jsp"%>
<html>
<head>
<%@include file="../common/meta.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/css.jsp"%>
<%@include file="../common/ext.jsp"%>
<title>部门评分明细</title>
<link href="" rel="SHORTCUT ICON" />
<style type="text/css">
  .x-form-layout-table{
	table-layout: fixed;
  }
  
  .x-grid-td {
    vertical-align: middle !important;
  }
</style>
</head>
<body>
	<script type="text/javascript">
		var userPermissionArr = new Array();
		<c:forEach items="${userPermission}" var="v">
			var obj=new Object();
			obj.value='${v.resourceId }';
			obj.name='${v.code }';
			userPermissionArr.push(obj);
		</c:forEach>
	</script>

	<script type="text/javascript">
	var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
	
	Ext.onReady(function() {
		Ext.QuickTips.init();
		//自动引入其他需要的js
		Ext.require(["Ext.container.*",
		             "Ext.grid.*", 
		             "Ext.toolbar.Paging", 
		             "Ext.form.*",
					 "Ext.data.*" ]);
		//建立Model模型对象
		Ext.define("Classify",{
			extend:"Ext.data.Model",
			fields:[
				{name: "classifyId"}, 
				{name: "number"}, 
				{name: "name"}, 
				{name: "orgNames"},
				{name: "orgIds"},
				{name: "electYear"},
				{name: "enable"},
				{name: "isDelete"},
                {name: "hasSubmit"}
			]
		});
		
		Ext.define('GradeRecord', {
	        extend: 'Ext.data.Model',
	        fields: [
	            {name: 'gradeDetailId', type: 'int'},
	            {name: 'classifyName', type: 'string'},
                {name: 'name', type: 'string'},
                {name: 'gradeIndex2Name', type: 'string'},
                {name: 'canpDept', type: 'string'},
                {name: 'score', type: 'string'},
                {name: 'gradeUsr', type: 'string'},
                {name: 'gradeUsrDept', type: 'string'}
	        ]
	    });
		
		Ext.define('treeModel', {
		    extend: 'Ext.data.Model',
		    fields: [
		        {name: 'nodeId',type: 'int'}, 
		        {name: 'parentId',type: 'int'}, 
		        {name: 'id',type: 'int',mapping:'nodeId'},
		        {name: 'text',type: 'string'} 
		    ]
		});
		
		var cfStore = Ext.create('Ext.data.Store', {
            model: 'Classify',
            proxy: {
               type: 'ajax',
               url: '${ctx}/deptgrade/getAllClassifies.action',
               extraParams:{
                   electYear:Ext.getCmp('electYearQuery') ? Ext.getCmp('electYearQuery').getValue() : Ext.Date.format(new Date(),"Y"),
                   participation: 'true'
               },
               reader: {
                  type: 'json'
               }
            },
            autoLoad: true, 
            listeners:{
                load:function(store, records){
                    var obj = {classifyId:'0', name:'全部'};
                    store.insert(0, obj);
                }
            }
        });
		
		//建立数据Store
		var recordStore=Ext.create("Ext.data.Store", {
	        pageSize: SystemConstant.commonSize,
	        model:"GradeRecord",
	        remoteSort:true,
			proxy: {
	            type:"ajax",
	            actionMethods: {
                	read: 'POST'
           		},
			    url: "${ctx}/deptgrade/queryDeptGradeDetail.action",
			    reader: {
				     totalProperty: "totalSize",
				     root: "list"
			    }
	        },
	        listeners:{
                load:function(store, records){
                    if (records.length > 0) {
                        mergeCells(recordGrid, [1, 2, 3]);
                        mergeCells(recordGrid, [6, 7]);
                    }
                }
            }
		});
		
		var cm=[
				{header:"序号",xtype: "rownumberer",width:60,align:"center",menuDisabled: true,sortable :false},
	            {header: "ID",width: 70,dataIndex: "gradeDetailId",hidden: true,menuDisabled: true,sortable :false},
	            {header: "指标分类",width: 100,dataIndex: "classifyName",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}},
	            {header: "一级指标",width: 200,dataIndex: "name",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}},
	            {header: "二级指标",width: 200,dataIndex: "gradeIndex2Name",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}},
	            {header: "参评部门",width: 100,dataIndex: "canpDept",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}
				},
                {header: "得分",width: 100,dataIndex: "score",menuDisabled: true,sortable :false,
                    renderer : function(value, cellmeta, record, rowIndex,
                            columnIndex, store) {
                        cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return value;
                    }
                },
                {header: "评分人",width: 100,dataIndex: "gradeUsr",menuDisabled: true,sortable :false,
                    renderer : function(value, cellmeta, record, rowIndex,
                            columnIndex, store) {
                        cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return value;
                    }
                },
                {header: "评分人部门",width: 100,dataIndex: "gradeUsrDept",menuDisabled: true,sortable :false,
                    renderer : function(value, cellmeta, record, rowIndex,
                            columnIndex, store) {
                        cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return value;
                    }
                }
	         ];
		
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
                    url :'${ctx}/org/getUnitTreeListNotCheck.action'
                },
                root: {
                    expanded: true,
                    id:"0",
                    text:'全部'
                }
            })
        });
		
		var gradeUsrDeptQuery = Ext.create("Ext.ux.TreePicker", {
            allowBlank: false,
            value:'0',
            id:'gradeUsrDeptQuery',
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
                    url :'${ctx}/org/getUnitTreeListNotCheck.action'
                },
                root: {
                    expanded: true,
                    id:"0",
                    text:'全部'
                }
            })
        });
		
		//grid组件
		recordGrid =  Ext.create("Ext.grid.Panel",{
			title:'部门评分明细',
			border:false,
			columnLines: true,
			layout:"fit",
			region: "center",
			width: "100%",
			height: document.body.clientHeight,
			id: "recordGrid",
			bbar:  Ext.create("Ext.PagingToolbar", {
				store: recordStore,
				displayInfo: true,
				displayMsg: SystemConstant.displayMsg,
				emptyMsg: SystemConstant.emptyMsg
			}),
			columns:cm,
	     	forceFit : true,
			store: recordStore,
			autoScroll: true,
			stripeRows: true,
			tbar: ['参评年份',
            {
                id: 'electYearQuery',
                width: 100,   
                labelWidth: 70,
                value: Ext.Date.format(new Date(),"Y"),
                xtype: 'textfield',
                listeners :{
                    'render' : function(p){
                        p.getEl().on('click',function(){
                            WdatePicker({readOnly:true,dateFmt:'yyyy',maxDate:Ext.Date.format(new Date(),"Y"),
                            	onpicked:function(){
                                    Ext.getCmp("classifyIdQuery").reset();
                                    cfStore.load({
                                        params:{
                                            'electYear':$dp.cal.getP('y')
                                        },
                                        callback:function(records){
                                            Ext.getCmp("classifyIdQuery").setValue('0');
                                        }
                                    });
                                }
                            });
                            //,onpicked:function(){$dp.$('electYearQuery-inputEl').focus();}
                        });
                    }
                }
            },'&nbsp;指标分类',
            {
                xtype: 'combobox',
                id:'classifyIdQuery',
                store: cfStore,
                valueField: 'classifyId',
                displayField: 'name',
                typeAhead:false,
                allowBlank:false,
                editable:false,
                queryMode: 'remote'
            },'&nbsp;参评部门',canpDeptQuery,
            '&nbsp;评分人部门',gradeUsrDeptQuery,
            '&nbsp;',
			{
				id:'searchRecordBtn',
				xtype:'button',
				disabled:false,
				text:'查询',
				iconCls:'search-button',
				handler:function(){
					var proxy = recordStore.getProxy();
					proxy.setExtraParam("electYear",Ext.getCmp("electYearQuery").getValue());
					proxy.setExtraParam("canpDeptId",Ext.getCmp("canpDeptQuery").getValue());
					proxy.setExtraParam("gradeUsrDeptId",Ext.getCmp("gradeUsrDeptQuery").getValue());
					proxy.setExtraParam("cfId",Ext.getCmp("classifyIdQuery").getValue());
					recordStore.loadPage(1);
				}
			}]
		});
		
		cfStore.load(function(){
			Ext.getCmp("classifyIdQuery").setValue('0');
		});
		
		recordStore.load({
            params:{
                start:0,
                limit:SystemConstant.commonSize,
                'electYear':Ext.getCmp('electYearQuery').getValue()
            }
        });
		
		Ext.create("Ext.container.Viewport", {
		    layout: "border",
			renderTo: Ext.getBody(),
			items: [recordGrid]
		});
	});
	</script>
</body>
</html>