<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/doc_type.jsp"%>
<html>
<head>
<%@include file="../common/meta.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/css.jsp"%>
<%@include file="../common/ext.jsp"%>
<title>部门评分</title>
<link href="" rel="SHORTCUT ICON" />
<style type="text/css">
  .x-form-layout-table{
	table-layout: fixed;
  }
  
  .x-grid-td {
    vertical-align: middle !important;
  }
  
  .custom-grid-row{
    height:45px;
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
                {name: "hasSubmit"},
                {name: "hasSaved"}
            ]
        });
		
		var classifyStore=Ext.create("Ext.data.Store", {
            pageSize: SystemConstant.commonSize,
            model:"Classify",
            remoteSort:true,
            proxy: {
                type:"ajax",
                actionMethods: {
                    read: 'POST'
                },
                url: "${ctx}/deptgrade/getClassifyListForGrade.action",
                reader: {
                	type: 'json'
                }
            }
        });
		
        var cm=[
                {header:"序号",xtype: "rownumberer",width:60,align:"center",menuDisabled: true,sortable :false},
                {header: "ID",width: 70,dataIndex: "classifyId",hidden: true,menuDisabled: true,sortable :false},
                {header: "指标分类编号",width: 200,dataIndex: "number",menuDisabled: true,sortable :false,
                    renderer : function(value, cellmeta, record, rowIndex,
                            columnIndex, store) {
                        cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return value;
                    }},
                {header: "指标分类名称",width: 200,dataIndex: "name",menuDisabled: true,sortable :false,
                    renderer : function(value, cellmeta, record, rowIndex,
                            columnIndex, store) {
                        cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return value;
                    }},
                {header: "参评部门",width: 200,dataIndex: "orgNames",menuDisabled: true,sortable :false,
                    renderer : function(value, cellmeta, record, rowIndex,
                            columnIndex, store) {
                        cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return value;
                    }},
                {header: "参评年份",width: 200,dataIndex: "electYear",menuDisabled: true,sortable :false,
                    renderer : function(value, cellmeta, record, rowIndex,
                            columnIndex, store) {
                        cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return value;
                    }},
                {header: "是否提交评分",width: 200,dataIndex: "hasSubmit",menuDisabled: true,sortable :false,
                    renderer : function(value, cellmeta, record, rowIndex,
                            columnIndex, store) {
                        var hasSub = '<span style="color:red;">否</span>';
                        if (1 == value) {
                            hasSub = '<span style="color:green;">是</span>';
                        }
                        
                        //cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return hasSub;
                    }
                },
                {header: "是否已评分",width: 200,dataIndex: "hasSaved",menuDisabled: true,sortable :false,
                    renderer : function(value, cellmeta, record, rowIndex,
                            columnIndex, store) {
                        var hasSub = '<span style="color:red;">否</span>';
                        if (1 == value) {
                            hasSub = '<span style="color:green;">是</span>';
                        }
                        
                        //cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return hasSub;
                    }
                },
                {header: "操作",width: 200,dataIndex: "hasSubmit",
                    renderer: function(value, cellmeta, record, rowIndex, columnIndex, store){
                        //cellmeta.tdAttr = 'data-qtip="' + orgTypeArr[i].name + '"';
                        //if(value == 0){
                            return '<img title="进行部门评分" src="${ctx}/images/icons/table_edit.png" style="cursor: pointer;padding:0;margin:0;heigth:16px;" onclick="deptGradeFun()"/>';
                        //}
                    }
                }
             ];
		
		//grid组件
		grageGrid = Ext.create("Ext.grid.Panel",{
			title:'部门评分',
			border:false,
			columnLines: true,
			layout:"fit",
			region: "center",
			width: "100%",
			height: document.body.clientHeight,
			id: "grageGrid",
			columns:cm,
	     	forceFit : true,
			store: classifyStore,
			autoScroll: true,
			stripeRows: true,
			tbar: ['参评年份',
            {
                id: 'electYearQuery',
                width: 160,
                labelWidth: 70,
                xtype: 'textfield',
                regex : new RegExp('^([^<^>])*$'),
                regexText : '不能包含特殊字符！',
                value: Ext.Date.format(new Date(),"Y"),
                allowBlank: false,
                listeners :{
                    'render' : function(p){
                        p.getEl().on('click',function(){
                            WdatePicker({readOnly:true,dateFmt:'yyyy',maxDate:Ext.Date.format(new Date(),"Y"),
                            	onpicked:function(){
                                    if ($dp.cal.getP('y') == Ext.Date.format(new Date(),"Y")) {
                                        Ext.getCmp("submitPercentageBtn").setDisabled(false);
                                    }
                                    else {
                                        Ext.getCmp("submitPercentageBtn").setDisabled(true);
                                    }
                                }
                            });
                            //,onpicked:function(){$dp.$('electYearQuery-inputEl').focus();}
                        });
                    }
                }
            },'&nbsp;',
            {
                id:'searchClassifyBtn',
                xtype:'button',
                disabled:false,
                text:'查询',
                iconCls:'search-button',
                handler:function(){
                    var proxy = classifyStore.getProxy();
                    proxy.setExtraParam("classifyVo.electYear",Ext.getCmp("electYearQuery").getValue());
                    classifyStore.load();
                }
            },
            '->',
			{
				id:'submitPercentageBtn',
				xtype:'button',
				text:'提交',
				iconCls:'save-button',
				handler:function(){
					Ext.Msg.confirm(SystemConstant.alertTitle,"提交后不能修改评分，确定提交最后评分吗？",function(btn) {
                        if (btn == 'yes') {
	                        var c = classifyStore.getCount();
	                        if (c > 0) {
	                        	Ext.MessageBox.wait("", "提交中", 
	                                {
	                                    text:"请稍后..."
	                                }
	                            );
	                        	
	                        	var cfIds = '';
	                            for(var i=0; i<c; i++){
	                                var re = classifyStore.getAt(i);
	                                cfIds += ',' + re.get('classifyId');
	                            }
	                            
	                            $.ajax({
	                                url : '${ctx}/deptgrade/submitDeptGrade.action',
	                                data: {cfIds : cfIds.substring(1), electYear : Ext.getCmp('electYearQuery').getValue()},
	                                cache : false,
	                                async : false,
	                                type : "POST",
	                                dataType : 'json',
	                                success : function (responseText){
	                                	var result = responseText; // Ext.decode(responseText);
	                                    if(result.success == "true"){
	                                        new Ext.ux.TipsWindow({
	                                            title:SystemConstant.alertTitle,
	                                            html: result.msg
	                                        }).show();
	                                        Ext.MessageBox.hide();
	                                        classifyStore.reload();
	                                    }else{
	                                        Ext.MessageBox.hide();
	                                        Ext.MessageBox.show({
	                                            title: SystemConstant.alertTitle,
	                                            msg: result.msg,
	                                            buttons: Ext.MessageBox.OK,
	                                            icon: Ext.MessageBox.INFO
	                                        });
	                                    }
	                                }
	                            });
	                        }
                        }
                    });
				}
			}]
		});
		
		classifyStore.load({
            params:{
                'classifyVo.electYear':Ext.getCmp('electYearQuery').getValue()
            }
        });
		
		deptGradeFun = function() {
			var record = grageGrid.getSelectionModel().getSelection()[0];
			
			var headers=new Array();// 数据模型
	        var columns = new Array();// 表头
	        
	        headers.push({name:'name',type:'string'});
	        headers.push({name:'gradeIndex2Name',type:'string'});
	        headers.push({name:'indexId'});
            headers.push({name:'number',type:'string'});
            headers.push({name:'classifyName',type:'string'});
            headers.push({name:'classifyId'});
            headers.push({name:'grade',type:'string'});
            headers.push({name:'grade2',type:'string'});
            headers.push({name:'remark',type:'string'});
            headers.push({name:'remark2',type:'string'});
            headers.push({name:'electYear',type:'string'});
            headers.push({name:'gradeIndex1Id'});
            headers.push({name:'gradeRecs'});
	        
            columns.push({header:"序号",xtype: "rownumberer",width:60,height:36,align:"center",menuDisabled: true,sortable :false});
            columns.push({header: "ID",width: 30,dataIndex: "indexId",hidden: true,menuDisabled: true,sortable :false});
	        columns.push({header:'一级指标',width: 150, dataIndex:'name',menuDisabled: true,sortable :false,
	        	renderer : function(value, p, record) {
        		    return '<div style="white-space:normal;font-size:14px;line-height:18px;">' + value + '</div>';
        		}
	        });
	        columns.push({header:'说明',width: 80, dataIndex:'remark',menuDisabled: true,sortable :false,
	        	renderer : function(value, p, record) {
                    return '<div style="white-space:normal;font-size:14px;line-height:18px;">' + value + '</div>';
                }
	        });
	        columns.push({header:'分值',width: 40, dataIndex:'grade',menuDisabled: true,sortable :false});
	        columns.push({header:'二级指标',width: 150, dataIndex:'gradeIndex2Name',menuDisabled: true,sortable :false,
	        	renderer : function(value, p, record) {
                    return '<div style="white-space:normal;font-size:14px;line-height:18px;">' + value + '</div>';
                }
	        });
	        columns.push({header:'说明',width: 80, dataIndex:'remark2',menuDisabled: true,sortable :false,
	        	renderer : function(value, p, record) {
                    return '<div style="white-space:normal;font-size:14px;line-height:18px;">' + value + '</div>';
                }
	        });
	        columns.push({header:'分值',width: 40, dataIndex:'grade2',menuDisabled: true,sortable :false});
	        
	        // 参评部门：用户查询显示已评分列表
	        var cpbm = null;
	        
	        $.ajax({
	            type : "POST",
	            url : "${ctx}/deptgrade/getOrgListForGrade.action",
	            data : {
	            	'classifyVo.classifyId' : record.get('classifyId'),
	                'classifyVo.electYear' : record.get('electYear')
	            },
	            cache : false,
	            async : false,
	            dataType : 'json',
	            success : function(records) {
	            	cpbm = records;
	                for(var j=0;j<records.length;j++){
	                    var orgId=records[j].orgId;
	                    headers.push({name:"orgId_"+orgId,type:'string'});
	                    
	                    columns.push({header:records[j].orgName + "评分",width: 80, dataIndex:"orgId_"+orgId,menuDisabled: true,sortable :false,
	                        field:{
	                            xtype:'combo',
	                            maxLength:10,
	                            regex : new RegExp('^([^<^>])*$'),
	                            regexText : '不能包含特殊字符！',
	                            allowBlank: false,
	                            //editable: true,
	                            displayField: 'score',
	                            valueField: 'score',
	                            store: Ext.create('Ext.data.Store', {
	                                fields: ['score'],
	                                data : [
	                                    {"score":"0"}
	                                ]
	                            })
	                        }
	                    });
	                }
	            }
	        });
	        
	        Ext.define("DeptGrade",{
	            extend:"Ext.data.Model",
	            fields: headers
	        });
	        
	        //建立数据Store
	        var deptGrageStore=Ext.create("Ext.data.Store", {
	            pageSize: SystemConstant.commonSize,
	            model:"DeptGrade",
	            remoteSort:true,
	            proxy: {
	                type:"ajax",
	                actionMethods: {
	                    read: 'POST'
	                },
	                url: "${ctx}/deptgrade/getIndexListForGrade.action",
	                reader: {
	                    type: 'json'
	                }
	            },
	            listeners:{
	                load:function(store, records){
	                	if (records.length > 0) {
	                		var has2 = false;
	                        for(var i = 0; i < records.length; i++){
	                            var grades = records[i].get('gradeRecs').split('|');
	                            for(var j = 0; j < cpbm.length; j++){
	                                // 解析后台返回的各部门分数字符串
	                                var grade = '';
	                                for(var k = 0; k < grades.length; k++){
	                                    if (parseInt(grades[k].split(':')[0]) == cpbm[j].orgId) {
	                                        grade = grades[k].split(':')[1];
	                                        break;
	                                    }
	                                }
	                                records[i].set('orgId_' + cpbm[j].orgId, grade);
	                            }
	                            
	                            if (records[i].get('gradeIndex2Name')) {
	                            	has2 = true;
	                            }
	                        }
	                        
	                        if (!has2) {
	                        	deptGrageGrid.columns[5].setVisible(false);
	                        	deptGrageGrid.columns[6].setVisible(false);
	                        	deptGrageGrid.columns[7].setVisible(false);
	                        }
	                        
	                        // 统计汇总
	                        var obj = "{indexId:-1, name:'合计'";
	                        for(var j = 0; j < cpbm.length; j++){
	                        	var scoreSum = 0;
	                        	for(var i = 0; i < records.length; i++){
	                        		if (records[i].get('orgId_' + cpbm[j].orgId)) {
		                        		scoreSum += parseFloat(records[i].get('orgId_' + cpbm[j].orgId));
	                        		}
	                        	}
	                        	obj += ", orgId_" + cpbm[j].orgId + ":" + scoreSum;
	                        }
	                        
	                        obj += "}";
	                        store.add(Ext.decode(obj));
	                        
	                        mergeCells(deptGrageGrid, [1, 2, 3]);
	                    }
	                }
	            }
	        });
	        
	        var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
	            clicksToEdit: 1,
	            listeners : {
	                beforeedit:function(editor, e, eOpts ){
	                	var scoreStore = e.column.field.store;
	                	
	                	if (e.record.data.indexId == -1) {
	                		scoreStore.removeAll();
	                		//e.column.field.editable = false;
	                		//e.column.field.readOnly = true;
	                		//e.column.field = null;
	                	}else{
	                		var maxScore = e.record.data.grade;
	                        if (e.record.data.grade2) {
	                            maxScore = e.record.data.grade2;
	                        }
	                        
	                        var scoreData = [];
	                        for (var i = maxScore; i >= 0; i--) {
	                            scoreData.push({'score' : i});
	                        }
	                        
	                        scoreStore.removeAll();
	                        scoreStore.add(scoreData);
	                	}
	                },
	                edit:function(editor, e, eOpts ){
	                	for(var j = 0; j < cpbm.length; j++){
                            var scoreSum = 0;
                            for(var i=0; i<deptGrageStore.getCount() - 1; i++){
                            	var re = deptGrageStore.getAt(i);
                            	
                                if (re.get('orgId_' + cpbm[j].orgId)) {
                                    scoreSum += parseFloat(re.get('orgId_' + cpbm[j].orgId));
                                }
                            }
                            
                            deptGrageStore.getAt(deptGrageStore.getCount() - 1).set('orgId_' + cpbm[j].orgId, scoreSum);
                        }
	                }
	            }
	        });
	        
	        deptGrageGrid = Ext.create("Ext.grid.Panel",{
	            //title:'部门评分',
	            border:false,
	            columnLines: true,
	            layout:"fit",
	            region: "center",
	            width: "100%",
	            height: document.body.clientHeight,
	            id: "deptGrageGrid",
	            columns:columns,
	            plugins: [cellEditing],
	            forceFit : true,
	            store: deptGrageStore,
	            autoScroll: true,
	            stripeRows: true,
	            viewConfig:{
                    getRowClass:function(){
                        // 在这里添加自定样式 改变这个表格的行高
                        return 'x-grid-row custom-grid-row';
                    }
                },
	            tbar: ['指标分类：',
	            {
	            	xtype: 'label',
	                text: record.get('name')
	            },'&nbsp;&nbsp;参评年份：',
	            {
	            	xtype: 'label',
                    text: record.get('electYear')
	            }]
	        });
	        
	        var deptGrageWin = Ext.create("Ext.window.Window",{
	            id:'deptGrageWin',
	            title: '部门评分--' + record.get('name'),
	            resizable: false,
	            buttonAlign:"center",
	            height: 540,
	            width: 1200,
	            modal:true,
	            closeAction : 'destroy',
	            layout: 'fit',
	            items: [deptGrageGrid],
	            buttons:[{
                    text: '保存',
                    id: 'saveGradeBtn',
                    handler: function(){
                        var scores = '';
                        var c = deptGrageStore.getCount();
                        if (c > 0) {
                        	Ext.MessageBox.wait("", "保存评分数据", 
                                {
                                    text:"请稍后..."
                                }
                            );
                        	
                            for(var i=0; i<deptGrageStore.getCount() - 1; i++){
                                var re = deptGrageStore.getAt(i);
                                var index = re.get('indexId');
                                var index1 = re.get('gradeIndex1Id');
                                
                                scores += ', {classifyId:' + record.get('classifyId')
                                	+ ', indexId:' + index
                                	+ ', gradeIndex1Id:' + index1;
                                
                                var df = '';
                                for(var j = 0; j < cpbm.length; j++){
                                	var grade = re.get('orgId_' + cpbm[j].orgId);
                                	if (!grade) {
                                        Ext.MessageBox.show({
                                            title:'提示信息',
                                            msg:"得分不能为空",
                                            buttons: Ext.Msg.YES,
                                            modal : true,
                                            icon: Ext.Msg.INFO
                                        });
                                        return false;
                                    }
                                	
                                	df += '|' + cpbm[j].orgId + ':' + grade;
                                }
                                
                                scores += ', gradeRecs:\'' + df.substring(1) + '\'}';
                            }
                            
                            scores = '[' + scores.substring(1) + ']';
                            
                            // 保存数据
                            Ext.Ajax.request({
                                url : '${ctx}/deptgrade/saveDeptGrade.action',
                                params : {defen: scores},
                                success : function(res, options) {
                                    Ext.MessageBox.hide();
                                    
                                    var result = Ext.decode(res.responseText);
                                    if(result.success == 'true'){
                                        new Ext.ux.TipsWindow(
                                                {
                                                    title: SystemConstant.alertTitle,
                                                    autoHide: 3,
                                                    html:result.msg
                                                }
                                        ).show();
                                        
                                        deptGrageWin.close();
                                        deptGrageStore.load();
                                    }else{
                                        Ext.MessageBox.show({
                                            title: SystemConstant.alertTitle,
                                            msg: result.msg,
                                            buttons: Ext.MessageBox.OK,
                                            icon: Ext.MessageBox.INFO
                                        });
                                    }
                                }
                            });
                        }
                    }
                },{
                    text: '关闭',
                    handler: function(){
                    	deptGrageWin.close();
                    }
	            }]
	         }).show();
	        
	        
	        if (Ext.getCmp("electYearQuery").getValue() == Ext.Date.format(new Date(),"Y")) {
	        	var hasSubmit = record.get('hasSubmit');
	            if (1 == hasSubmit) {
	                Ext.getCmp('saveGradeBtn').setVisible(false);
	            }
	        }
	        else {
	        	Ext.getCmp('saveGradeBtn').setVisible(false);
	        }
	        
	         
	         deptGrageStore.load({
	             params:{
	            	 cfId:record.get('classifyId')
	             },
                 callback: function(records, operation, success) {
                     /* if (records.length > 0) {
                    	for(var i = 0; i < records.length; i++){
                    		var grades = records[i].get('gradeRecs').split('|');
                    		for(var j = 0; j < cpbm.length; j++){
                    			// 解析后台返回的各部门分数字符串
                    			var grade = '';
                    			for(var k = 0; k < grades.length; k++){
                    				if (parseInt(grades[k].split(':')[0]) == cpbm[j].orgId) {
                    					grade = grades[k].split(':')[1];
                    					break;
                    				}
                    			}
                    			records[i].set('orgId_' + cpbm[j].orgId, grade);
                    		}
                    	}
                     } */
                 }
	         });
		};
		
		Ext.create("Ext.container.Viewport", {
		    layout: "border",
			renderTo: Ext.getBody(),
			items: [grageGrid]
		});
		
	});
	</script>
</body>
</html>