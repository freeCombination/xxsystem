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
                {name: 'finalScore', type: 'string'},
                {name: 'isParticipation', type: 'int'},
                {name: 'classifyId', type: 'int'},
                {name: 'canpDeptId', type: 'int'},
                {name: 'jdScore', type: 'string'},
                {name: 'jdPercentage', type: 'string'},
                {name: 'jdSumScore', type: 'string'},
                {name: 'plusedScore', type: 'string'},
                {name: 'partyScore', type: 'string'},
                {name: 'zhzlScore', type: 'string'},
                {name: 'secScore', type: 'string'},
                {name: 'lhScore', type: 'string'}
            ]
        });
        
        var cfPer = 0;
        var bdPer = 0;
        var jdPer = 0;
        
        var partyPer = 0;
        var zhzlPer = 0;
        var secPer = 0;
        var lhPer = 0;
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
                        else if ('JDSCORE' == records.list[j].dictCode) {
                            jdPer = records.list[j].dictionaryValue;
                        }
                        else if ('PARTYSCORE' == records.list[j].dictCode) {
                        	partyPer = records.list[j].dictionaryValue;
                        }
                        else if ('ZHZLSCORE' == records.list[j].dictCode) {
                        	zhzlPer = records.list[j].dictionaryValue;
                        }
                        else if ('SECSCORE' == records.list[j].dictCode) {
                        	secPer = records.list[j].dictionaryValue;
                        }
                        else if ('LHSCORE' == records.list[j].dictCode) {
                        	lhPer = records.list[j].dictionaryValue;
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
                    if (records.length > 0) {
                        /* for (var i = 0; i < records.length; i++) {
                            if (!records[i].get('classifyName') && !records[i].get('finalScore') &&
                                    Ext.getCmp('electYearQuery').getValue() == Ext.Date.format(new Date(),"Y")) {
                                records[i].set('finalScore', records[i].get('buildScore'));
                                
                                $.ajax({
                                    type : "POST",
                                    url : "${ctx}/deptgrade/saveFinalScore.action",
                                    data : {
                                        orgId:records[i].get('canpDeptId'),
                                        sumScore:0,
                                        finalScore:records[i].get('buildScore'),
                                        electYear:Ext.getCmp('electYearQuery').getValue()
                                    },
                                    cache : false,
                                    //async : false,
                                    dataType : 'json',
                                    success : function(response) {
                                        
                                    }
                                });
                            }
                        } */
                        
                        mergeCells(recordGrid, [1, 2, 3]);
                        mergeCells(recordGrid, [7, 8, 9, 10, 11, 12, 13, 14]);
                    }
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
                {header: "季度得分（" + (jdPer * 100).toFixed(0) + "%权重）" ,width: 140,menuDisabled: true,sortable :false,
                    columns:[
                        {header: "季度得分（可编辑）",width: 140,dataIndex: "jdScore",menuDisabled: true,sortable :false,
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
                        }/* ,
                        {header: "权重（可编辑）",width: 110,dataIndex: "jdPercentage",menuDisabled: true,sortable :false,
                            renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
                                if (value) {
                                    var showStr = Math.round(value * 100) + "%";
                                    cellmeta.tdAttr = 'data-qtip="' + showStr + '"';
                                    return showStr;
                                }
                                else {
                                    return value;
                                }
                            },
                            field: {
                                xtype:'textfield',
                                maxLength:10,
                                regex : new RegExp('^[0-9]+(.[0-9]{1,2})?$'),
                                regexText : '保留两位小数！',
                                allowBlank: false
                            }
                        },
                        {header: "季度得分",width: 110,dataIndex: "jdSumScore",menuDisabled: true,sortable :false,
                            renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
                                cellmeta.tdAttr = 'data-qtip="' + value + '"';
                                return value;
                            }
                        } */
                    ]
                },
                {header: "部门指标年度得分（" + (cfPer * 100).toFixed(0) + "%权重）" ,width: 610,menuDisabled: true,sortable :false,
                    columns:[
						{header: "加减分项（可编辑）",width: 130,dataIndex: "plusedScore",menuDisabled: true,sortable :false,
						    renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
						        cellmeta.tdAttr = 'data-qtip="' + value + '"';
						        return value;
						    },
						    field: {
						        xtype:'textfield',
						        maxLength:10,
						        regex : new RegExp('^[-]?[0-9]+(.[0-9]{1,2})?$'),
						        regexText : '保留两位小数！',
						        allowBlank: false
						    }
						},
                        
                        {header: "指标名称",width: 180,dataIndex: "classifyName",menuDisabled: true,sortable :false,
                            renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
                                cellmeta.tdAttr = 'data-qtip="' + value + '"';
                                return value;
                            }
                        },
                        {header: "得分（可编辑）",width: 100,dataIndex: "score",menuDisabled: true,sortable :false,
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
                        {header: "权重（可编辑）",width: 100,dataIndex: "percentage",menuDisabled: true,sortable :false,
                            renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
                                if (value) {
                                    var showStr = Math.round(value * 100) + "%";
                                    cellmeta.tdAttr = 'data-qtip="' + showStr + '"';
                                    return showStr;
                                }
                                else {
                                    return value;
                                }
                            },
                            field: {
                                xtype:'textfield',
                                maxLength:10,
                                regex : new RegExp('^[0-9]+(.[0-9]{1,2})?$'),
                                regexText : '保留两位小数！',
                                allowBlank: false
                            }
                        },
                        {header: "idformerge",width: 0, maxWidth: 0, dataIndex: "canpDept",menuDisabled: true,sortable :false},
                        {header: "年度得分",width: 100,dataIndex: "sumScore",menuDisabled: true,sortable :false,
                            renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
                                cellmeta.tdAttr = 'data-qtip="' + value + '"';
                                return value;
                            }
                        }
                    ]
                },
                {header: "部门建设得分（" + (bdPer * 100).toFixed(0) + "%权重）",width: 160,menuDisabled: true,sortable :false,
                    columns:[
                        {header: "评价得分",width: 160,dataIndex: "buildScore",menuDisabled: true,sortable :false,
                            renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
                                cellmeta.tdAttr = 'data-qtip="' + value + '"';
                                return value;
                            }
                        }
                    ]
                },
                {header: "党建（" + (partyPer * 100).toFixed(0) + "%权重）",width: 120,menuDisabled: true,sortable :false,
                    columns:[
                        {header: "（可编辑）",width: 120,dataIndex: "partyScore",menuDisabled: true,sortable :false,
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
                        }
                    ]
                },
                {header: "综合治理（" + (zhzlPer * 100).toFixed(0) + "%权重）",width: 140,menuDisabled: true,sortable :false,
                    columns:[
                        {header: "（可编辑）",width: 140,dataIndex: "zhzlScore",menuDisabled: true,sortable :false,
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
                        }
                    ]
                },
                {header: "保密（" + (secPer * 100).toFixed(0) + "%权重）",width: 120,menuDisabled: true,sortable :false,
                    columns:[
                        {header: "（可编辑）",width: 120,dataIndex: "secScore",menuDisabled: true,sortable :false,
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
                        }
                    ]
                },
                {header: "例会（" + (lhPer * 100).toFixed(0) + "%权重）",width: 120,menuDisabled: true,sortable :false,
                    columns:[
                        {header: "（可编辑）",width: 120,dataIndex: "lhScore",menuDisabled: true,sortable :false,
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
                        }
                    ]
                },
                {header: "总分",width: 100,dataIndex: "finalScore",menuDisabled: true,sortable :false,
                    renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
                        cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return value;
                    }
                }
             ];
        
        var tempScore = '';
        var tempPerc = '';
        var tempJdScore = '';
        //var tempJdPerc = '';
        var tempPlScore = '';
        
        var tempPartyScore = '';
        var tempZhzlScore = '';
        var tempSecScore = '';
        var tempLhScore = '';
        var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
            clicksToEdit: 1,
            listeners : {
                beforeedit:function(editor, e, eOpts ){
                    tempScore = e.record.data.score;
                    tempPerc = e.record.data.percentage;
                    tempJdScore = e.record.data.jdScore;
                    //tempJdPerc = e.record.data.jdPercentage;
                    tempPlScore = e.record.data.plusedScore;
                    
                    tempPartyScore = e.record.data.partyScore;
                    tempZhzlScore = e.record.data.zhzlScore;
                    tempSecScore = e.record.data.secScore;
                    tempLhScore = e.record.data.lhScore;
                },
                edit:function(editor, e, eOpts ){
                	var editable = Ext.getCmp('editable').getValue();
                	
                    var col = e.column.dataIndex;
                    if ("score" == col) {
                        if (!e.record.data.classifyName || e.record.data.isParticipation == 1 ||
                        		!isCanEdit() || !editable) {
                        	//Ext.getCmp('electYearQuery').getValue() != Ext.Date.format(new Date(),"Y") || 
                            recordStore.getAt(e.rowIdx).set('score', tempScore);
                        }
                        else{
                        	recordStore.getAt(e.rowIdx).set('score', editor.context.value);
                            $.ajax({
                                type : "POST",
                                url : "${ctx}/deptgrade/saveEditScore.action",
                                data : {
                                    cfId:e.record.data.classifyId,
                                    orgId:e.record.data.canpDeptId,
                                    score:editor.context.value
                                },
                                cache : false,
                                async : false,
                                dataType : 'json',
                                success : function(response) {
                                	saveSumScore(e, 'idx');
                                }
                            });
                        }
                    }
                    
                    if ("percentage" == col) {
                        if (!e.record.data.classifyName || !isCanEdit() || !editable) {
                            recordStore.getAt(e.rowIdx).set('percentage', tempPerc);
                        }
                        else{
                        	recordStore.getAt(e.rowIdx).set('percentage', editor.context.value);
                            $.ajax({
                                type : "POST",
                                url : "${ctx}/deptgrade/saveEditScore.action",
                                data : {
                                    cfId:e.record.data.classifyId,
                                    orgId:e.record.data.canpDeptId,
                                    percentage:editor.context.value,
                                    flag:'percentage'
                                },
                                cache : false,
                                async : false,
                                dataType : 'json',
                                success : function(response) {
                                	saveSumScore(e, 'idx');
                                }
                            });
                        }
                    }
                    
                    if ("jdScore" == col) {
                        if (!isCanEdit() || !editable) {
                            recordStore.getAt(e.rowIdx).set('jdScore', tempJdScore);
                        }
                        else{
                        	for (var i = 0; i < recordStore.getCount(); i++) {
                                if (recordStore.getAt(i).get('canpDeptId') == e.record.data.canpDeptId) {
                                    recordStore.getAt(i).set('jdScore', editor.context.value);
                                }
                            }
                        	
                            $.ajax({
                                type : "POST",
                                url : "${ctx}/deptgrade/saveJdEditScore.action",
                                data : {
                                    orgId:e.record.data.canpDeptId,
                                    score:editor.context.value,
                                    electYear:Ext.getCmp('electYearQuery').getValue()
                                },
                                cache : false,
                                async : false,
                                dataType : 'json',
                                success : function(response) {
                                	saveSumScore(e, 'jd');
                                }
                            });
                        }
                    }
                    
                    /* if ("jdPercentage" == col) {
                        if (Ext.getCmp('electYearQuery').getValue() != Ext.Date.format(new Date(),"Y") || !editable) {
                            recordStore.getAt(e.rowIdx).set('jdPercentage', tempJdPerc);
                        }
                        else{
                            $.ajax({
                                type : "POST",
                                url : "${ctx}/deptgrade/saveJdEditScore.action",
                                data : {
                                    orgId:e.record.data.canpDeptId,
                                    percentage:editor.context.value,
                                    electYear:Ext.getCmp('electYearQuery').getValue(),
                                    flag:'percentage'
                                },
                                cache : false,
                                async : false,
                                dataType : 'json',
                                success : function(response) {
                                	saveSumScore(e, 'jd');
                                }
                            });
                        }
                    } */
                    
                    if ("plusedScore" == col) {
                        if (!isCanEdit() || !editable) {
                            recordStore.getAt(e.rowIdx).set('plusedScore', tempPlScore);
                        }
                        else{
                        	for (var i = 0; i < recordStore.getCount(); i++) {
                                if (recordStore.getAt(i).get('canpDeptId') == e.record.data.canpDeptId) {
                                	recordStore.getAt(i).set('plusedScore', editor.context.value);
                                }
                            }
                        	
                            $.ajax({
                                type : "POST",
                                url : "${ctx}/deptgrade/saveInputScore.action",
                                data : {
                                    orgId:e.record.data.canpDeptId,
                                    score:editor.context.value,
                                    flag:'plusedScore',
                                    electYear:Ext.getCmp('electYearQuery').getValue()
                                },
                                cache : false,
                                async : false,
                                dataType : 'json',
                                success : function(response) {
                                    saveSumScore(e, 'idx');
                                }
                            });
                        }
                    }
                    
                    if ("partyScore" == col) {
                    	if (!isCanEdit() || !editable) {
                            recordStore.getAt(e.rowIdx).set('partyScore', tempPartyScore);
                        }
                        else{
                        	for (var i = 0; i < recordStore.getCount(); i++) {
                                if (recordStore.getAt(i).get('canpDeptId') == e.record.data.canpDeptId) {
                                	recordStore.getAt(i).set('partyScore', editor.context.value);
                                }
                            }
                        	
                            $.ajax({
                                type : "POST",
                                url : "${ctx}/deptgrade/saveInputScore.action",
                                data : {
                                    orgId:e.record.data.canpDeptId,
                                    score:editor.context.value,
                                    flag:'partyScore',
                                    electYear:Ext.getCmp('electYearQuery').getValue()
                                },
                                cache : false,
                                async : false,
                                dataType : 'json',
                                success : function(response) {
                                    saveSumScore(e, 'jd');
                                }
                            });
                        }
                    }
                    
                    if ("zhzlScore" == col) {
                    	if (!isCanEdit() || !editable) {
                            recordStore.getAt(e.rowIdx).set('zhzlScore', tempZhzlScore);
                        }
                        else{
                        	for (var i = 0; i < recordStore.getCount(); i++) {
                                if (recordStore.getAt(i).get('canpDeptId') == e.record.data.canpDeptId) {
                                	recordStore.getAt(i).set('zhzlScore', editor.context.value);
                                }
                            }
                        	
                            $.ajax({
                                type : "POST",
                                url : "${ctx}/deptgrade/saveInputScore.action",
                                data : {
                                    orgId:e.record.data.canpDeptId,
                                    score:editor.context.value,
                                    flag:'zhzlScore',
                                    electYear:Ext.getCmp('electYearQuery').getValue()
                                },
                                cache : false,
                                async : false,
                                dataType : 'json',
                                success : function(response) {
                                    saveSumScore(e, 'jd');
                                }
                            });
                        }
                    }
                    
                    if ("secScore" == col) {
                    	if (!isCanEdit() || !editable) {
                            recordStore.getAt(e.rowIdx).set('secScore', tempSecScore);
                        }
                        else{
                        	for (var i = 0; i < recordStore.getCount(); i++) {
                                if (recordStore.getAt(i).get('canpDeptId') == e.record.data.canpDeptId) {
                                	recordStore.getAt(i).set('secScore', editor.context.value);
                                }
                            }
                        	
                            $.ajax({
                                type : "POST",
                                url : "${ctx}/deptgrade/saveInputScore.action",
                                data : {
                                    orgId:e.record.data.canpDeptId,
                                    score:editor.context.value,
                                    flag:'secScore',
                                    electYear:Ext.getCmp('electYearQuery').getValue()
                                },
                                cache : false,
                                async : false,
                                dataType : 'json',
                                success : function(response) {
                                    saveSumScore(e, 'jd');
                                }
                            });
                        }
                    }
                    
                    if ("lhScore" == col) {
                    	if (!isCanEdit() || !editable) {
                            recordStore.getAt(e.rowIdx).set('lhScore', tempLhScore);
                        }
                        else{
                        	for (var i = 0; i < recordStore.getCount(); i++) {
                                if (recordStore.getAt(i).get('canpDeptId') == e.record.data.canpDeptId) {
                                	recordStore.getAt(i).set('lhScore', editor.context.value);
                                }
                            }
                        	
                            $.ajax({
                                type : "POST",
                                url : "${ctx}/deptgrade/saveInputScore.action",
                                data : {
                                    orgId:e.record.data.canpDeptId,
                                    score:editor.context.value,
                                    flag:'lhScore',
                                    electYear:Ext.getCmp('electYearQuery').getValue()
                                },
                                cache : false,
                                async : false,
                                dataType : 'json',
                                success : function(response) {
                                    saveSumScore(e, 'jd');
                                }
                            });
                        }
                    }
                }
            }
        });
        
        //grid组件
        recordGrid =  Ext.create("Ext.grid.Panel",{
            title:'部门最终得分',
            border:false,
            //columnLines: true,
            //layout:"fit",
            //region: "center",
            //width: "100%",
            //height: document.body.clientHeight,
            id: "recordGrid",
            columns:cm,
            plugins: [cellEditing],
            forceFit : false,
            store: recordStore,
            autoScroll: true,
            stripeRows: true,
            //viewConfig : {forceFit : false},
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
            },
            '->',
            '编辑',
            {
            	xtype: 'checkboxfield',
                id : 'editable'
            },
            '&nbsp;&nbsp;&nbsp;&nbsp;',
            {
            	xtype:'button',
                text:'导出',
                iconCls:'export-button',
                handler:function(){
                    window.location.href = "${ctx}/deptgrade/exportDeptFinalScore.action?electYear=" + Ext.getCmp('electYearQuery').getValue();
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
        
        // 保存指标年度得分小计或者季度得分小计
        function saveSumScore(e, flag){
        	
    		var sumScore = 0;
    		var bdScore = '0';
    		var plScore = '0';
    		var allFlag = true;
    		for (var i = 0; i < recordStore.getCount(); i++) {
                if (recordStore.getAt(i).get('canpDeptId') == e.record.data.canpDeptId) {
                    var s = recordStore.getAt(i).get('score');
                    var p = recordStore.getAt(i).get('percentage');
                    bdScore = recordStore.getAt(i).get('buildScore');
	                
                    if (recordStore.getAt(i).get('plusedScore')) {
	                    plScore = recordStore.getAt(i).get('plusedScore');
	                }
                    
                    if (s && p) {
                        sumScore += parseFloat(s) * parseFloat(p);
                    }
                    else {
                        allFlag = false;
                        break;
                    }
                }
            }
    		
    		bdScore = (bdScore == null || bdScore == '' ? '0' : bdScore);
    		
    		var sc = e.record.data.jdScore;
            //var per = e.record.data.jdPercentage;
            //var jdSumScore = 0;
            var sc1 = e.record.data.partyScore;
            var sc2 = e.record.data.zhzlScore;
            var sc3 = e.record.data.secScore;
            var sc4 = e.record.data.lhScore;
            
            if (allFlag && sc && sc1 && sc2 && sc3 && sc4) {// && per
            	//jdSumScore = parseFloat(sc) * parseFloat(per);
                var finalScore = (sumScore + parseFloat(plScore)) * cfPer
                               + parseFloat(bdScore) * bdPer
                               + sc * jdPer
                               + sc1 * partyPer
                               + sc2 * zhzlPer
                               + sc3 * secPer
                               + sc4 * lhPer;
                $.ajax({
                    type : "POST",
                    url : "${ctx}/deptgrade/saveFinalScore.action",
                    data : {
                        orgId:e.record.data.canpDeptId,
                        finalScore:finalScore.toFixed(2),
                        electYear:Ext.getCmp('electYearQuery').getValue()
                    },
                    cache : false,
                    async : false,
                    dataType : 'json',
                    success : function(response) {
                        //recordStore.load();
                    }
                });
            }
            
    		if (allFlag && 'idx' == flag) {
    			var cfSum = sumScore + parseFloat(plScore);
    			
    			$.ajax({
                    type : "POST",
                    url : "${ctx}/deptgrade/saveSumScore.action",
                    data : {
                        orgId:e.record.data.canpDeptId,
                        sumScore:cfSum.toFixed(2),
                        electYear:Ext.getCmp('electYearQuery').getValue()
                    },
                    cache : false,
                    async : false,
                    dataType : 'json',
                    success : function(response) {
                    	recordStore.load({
                            params:{
                                start:0,
                                limit:SystemConstant.commonSize,
                                'electYear':Ext.getCmp('electYearQuery').getValue()
                            }
                        });
                    }
                });
    		}
    	
            if (sc && 'jd' == flag) {// && per
            	recordStore.load({
                    params:{
                        start:0,
                        limit:SystemConstant.commonSize,
                        'electYear':Ext.getCmp('electYearQuery').getValue()
                    }
                });
            	/* jdSumScore = parseFloat(sc) * parseFloat(per);
            	$.ajax({
                    type : "POST",
                    url : "${ctx}/deptgrade/saveSumScore.action",
                    data : {
                        orgId:e.record.data.canpDeptId,
                        sumScore:jdSumScore.toFixed(2),
                        flag:'jdSumScore',
                        electYear:Ext.getCmp('electYearQuery').getValue()
                    },
                    cache : false,
                    async : false,
                    dataType : 'json',
                    success : function(response) {
                        recordStore.load();
                    }
                }); */
            }
            
        }
        
        Ext.create("Ext.container.Viewport", {
            layout: "fit",
            renderTo: Ext.getBody(),
            items: [recordGrid]
        });
        
        function isCanEdit(){
        	var now = new Date();
            var year = now.getFullYear();
            var month = now.getMonth();
            var day = now.getDate();
            
            month += 1;
            if(month < 10){
                month = "0" + month;
            }
            
            var deadLine = parseInt((parseInt(Ext.getCmp('electYearQuery').getValue()) + 1) + "0331");
            var curr = parseInt(year + "" + month + "" + day);
            if (curr <= deadLine) {
            	return true;
            }
            return false;
        }
    });
    </script>
</body>
</html>