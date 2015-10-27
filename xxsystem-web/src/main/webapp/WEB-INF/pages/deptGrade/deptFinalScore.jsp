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
		
		var cfPer = 0;
		var bdPer = 0;
		// 获取总分计算所需权重
		$.ajax({
            type : "POST",
            url : "${ctx}/user/getSelectionsByType.action",
            data : {
            	dictTypeCode:"SCORETYPE"
            },
            cache : false,
            async : false,
            dataType : 'json',
            success : function(records) {
            	if (records && records.list && records.list.length > 0) {
	                for(var j=0;j<records.list.length;j++){
	                    if ('INXSCORE' == records.list[j].dictCode) {
	                    	cfPer = records.list[j].dictionaryValue;
	                    }
	                    else if ('BUILDSCORE' == records.list[j].dictCode) {
                            bdPer = records.list[j].dictionaryValue;
                        }
	                }
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
			    url: "${ctx}/deptgrade/queryDeptFinalScore.action"
	        },
            listeners:{
                load:function(store, records){
                    /* if (records.length > 0) {
                        mergeCells(recordGrid, [1]);
                    } */
                }
            }
		});
		
		var cm=[
				{header:"序号",xtype: "rownumberer",width:60,align:"center",menuDisabled: true,sortable :false},
	            {header: "ID",width: 70,dataIndex: "gradeDetailId",hidden: true,menuDisabled: true,sortable :false},
	            {header: "部门",width: 100,dataIndex: "canpDept",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}
	            },
                {header: "部门指标年度得分（" + (cfPer * 100) + "%权重）" ,width: 600,menuDisabled: true,sortable :false,
					columns:[
						{header: "指标名称",width: 240,dataIndex: "classifyName",menuDisabled: true,sortable :false,
						    renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
						        cellmeta.tdAttr = 'data-qtip="' + value + '"';
						        return value;
						    }
						},
						{header: "得分（可编辑）",width: 120,dataIndex: "score",menuDisabled: true,sortable :false,
		                    renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
		                        cellmeta.tdAttr = 'data-qtip="' + value + '"';
		                        return value;
		                    },
		                    field: {
		                        xtype:'textfield',
		                        maxLength:10,
		                        regex : new RegExp('^[0-9]+(.[0-9]{1,2})?$'),
		                        regexText : '保留两位小数！',
		                        allowBlank: false
		                    }
		                },
		                {header: "权重（可编辑）",width: 120,dataIndex: "percentage",menuDisabled: true,sortable :false,
		                    renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
		                        cellmeta.tdAttr = 'data-qtip="' + value + '"';
		                        return value;
		                    },
		                    field: {
		                        xtype:'textfield',
		                        maxLength:10,
		                        regex : new RegExp('^[0-9]+(.[0-9]{1,2})?$'),
		                        regexText : '保留两位小数！',
		                        allowBlank: false
		                    }
		                },
		                {header: "得分",width: 120,dataIndex: "sumScore",menuDisabled: true,sortable :false,
		                    renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
		                        cellmeta.tdAttr = 'data-qtip="' + value + '"';
		                        return value;
		                    }
		                }
					]
                },
                {header: "部门 建设得分（" + (bdPer * 100) + "%权重）",width: 200,menuDisabled: true,sortable :false,
                	columns:[
						{header: "评价得分",width: 200,dataIndex: "buildScore",menuDisabled: true,sortable :false,
						    renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
						        cellmeta.tdAttr = 'data-qtip="' + value + '"';
						        return value;
						    }
						}
                	]
                },
                {header: "总分",width: 120,dataIndex: "finalScore",menuDisabled: true,sortable :false,
                    renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
                        cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return value;
                    }
                }
	         ];
		
		var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
            clicksToEdit: 1
        });
		
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
			plugins: [cellEditing],
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