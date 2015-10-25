<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/doc_type.jsp"%>
<html>
<head>
<%@include file="../common/meta.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/css.jsp"%>
<%@include file="../common/ext.jsp"%>
<title>部门最终得分</title>
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
                {name: 'gradeUsrDept', type: 'string'},
                {name: 'percentage', type: 'string'},
                {name: 'sumScore', type: 'string'},
                {name: 'buildScore', type: 'string'},
                {name: 'finalScore', type: 'string'}
	        ]
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
			    url: "${ctx}/deptgrade/queryDeptGradeSummarizing.action",
			    reader: {
				     totalProperty: "totalSize",
				     root: "list"
			    }
	        },
            listeners:{
                load:function(store, records){
                    if (records.length > 0) {
                        mergeCells(recordGrid, [1]);
                    }
                }
            }
		});
		
		var cm=[
				{header:"序号",xtype: "rownumberer",width:60,align:"center",menuDisabled: true,sortable :false},
	            {header: "ID",width: 70,dataIndex: "gradeDetailId",hidden: true,menuDisabled: true,sortable :false},
	            {header: "指标分类",width: 200,dataIndex: "classifyName",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}},
	            {header: "参评部门",width: 200,dataIndex: "canpDept",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}
				},
                {header: "得分",width: 200,dataIndex: "score",menuDisabled: true,sortable :false,
                    renderer : function(value, cellmeta, record, rowIndex,
                            columnIndex, store) {
                        cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return value;
                    }
                }
	         ];
		
		//grid组件
		recordGrid =  Ext.create("Ext.grid.Panel",{
			title:'部门最终得分',
			border:false,
			columnLines: true,
			layout:"fit",
			region: "center",
			width: "100%",
			height: document.body.clientHeight,
			id: "recordGrid",
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
                            WdatePicker({readOnly:true,dateFmt:'yyyy',maxDate:Ext.Date.format(new Date(),"Y")});
                            //,onpicked:function(){$dp.$('electYearQuery-inputEl').focus();}
                        });
                    }
                }
            },
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
					recordStore.loadPage(1);
				}
			}]
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