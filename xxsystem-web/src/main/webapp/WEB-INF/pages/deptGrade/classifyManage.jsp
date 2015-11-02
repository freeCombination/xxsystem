<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/doc_type.jsp"%>
<html>
<head>
<%@include file="../common/meta.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/css.jsp"%>
<%@include file="../common/ext.jsp"%>
<title>指标分类管理</title>
<link href="" rel="SHORTCUT ICON" />
<style type="text/css">
  .x-form-layout-table{
	table-layout: fixed;
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
                {name: "scoreTypeId"},
                {name: "scoreTypeName"},
                {name: "participation", type: 'boolean'}
			]
		});
		
		Ext.define('Duty', {
	        extend: 'Ext.data.Model',
	        fields: [
	            {name: 'dutyId', type: 'int'},
	            {name: 'number', type: 'string'},
                {name: 'dutyContent', type: 'string'},
                {name: 'dutyType', type: 'string'},
                {name: 'respName', type: 'string'},
                {name: 'respId', type: 'int'}
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
		
		//建立数据Store
		var classifyStore=Ext.create("Ext.data.Store", {
	        pageSize: SystemConstant.commonSize,
	        model:"Classify",
	        remoteSort:true,
			proxy: {
	            type:"ajax",
	            actionMethods: {
                	read: 'POST'
           		},
			    url: "${ctx}/deptgrade/getClassifyList.action",
			    reader: {
				     totalProperty: "totalSize",
				     root: "list"
			    }
	        }
		});
		
		//行选择模型
		var sm=Ext.create("Ext.selection.CheckboxModel",{
			injectCheckbox:1,
	    	listeners: {
		        selectionchange: function(){
		        	if (Ext.getCmp("electYearQuery").getValue() == Ext.Date.format(new Date(),"Y")) {
		        		var c = classifyGrid.getSelectionModel().getSelection();
	                    if(c.length > 0){
	                        var enable = 1;
	                        for (var i = 0; i < c.length; i++) {
	                            if (c[i].get('enable') != 1) {
	                                enable = 0;
	                                break;
	                            }
	                        }
	                        
	                        if (enable == 1) {
	                            Ext.getCmp('delClassifyBtn').setDisabled(false);
	                        }
	                        else {
	                            Ext.getCmp('delClassifyBtn').setDisabled(true);
	                        }
	                    }else{
	                        Ext.getCmp('delClassifyBtn').setDisabled(true);
	                    }
	                    if(c.length == 1){
	                        var enable = c[0].get('enable');
	                        if (enable == 1) {
	                            Ext.getCmp('updateClassifyBtn').setDisabled(false);
	                        }
	                        else {
	                            Ext.getCmp('updateClassifyBtn').setDisabled(true);
	                        }
	                    }else{
	                        Ext.getCmp('updateClassifyBtn').setDisabled(true);
	                    }
		        	}
		        	else {
		        		Ext.getCmp('updateClassifyBtn').setDisabled(true);
		        		Ext.getCmp('delClassifyBtn').setDisabled(true);
		        	}
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
				{header: "汇总得分分类",width: 200,dataIndex: "scoreTypeName",menuDisabled: true,sortable :false,
                    renderer : function(value, cellmeta, record, rowIndex,
                            columnIndex, store) {
                    	var hasSub = '<span style="color:red;">否</span>';
                    	if (1 == value) {
                    		hasSub = '<span style="color:green;">是</span>';
                    	}
                    	
                        cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return value;
                    }
				},
				{header: "是否参与评分",width: 200,dataIndex: "participation",menuDisabled: true,sortable :false,
                    renderer : function(value, cellmeta, record, rowIndex,
                            columnIndex, store) {
                        var participation = '<span style="color:red;">否</span>';
                        if (value) {
                        	participation = '<span style="color:green;">是</span>';
                        }
                        
                        //cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return participation;
                    }
                },
				{header: "状态",width: 200,dataIndex: "enable",
		            renderer: function(value, cellmeta, record, rowIndex, columnIndex, store){
		                //cellmeta.tdAttr = 'data-qtip="' + orgTypeArr[i].name + '"';
		                var classify = record.get('classifyId');
		                if(value == 1){
		                    return '<img title="点击锁定指标分类" src="'+basePath+'/images/icons/unlock.gif" style="cursor: pointer;padding:0;margin:0;" onclick="lockUnLock(\''+classify+'\',\''+value+'\')"/>';
		                }else{
		                    return '<img title="点击解锁指标分类" src="'+basePath+'/images/icons/lock.gif" style="cursor: pointer;padding:0;margin:0;" onclick="lockUnLock(\''+classify+'\',\''+value+'\')"/>';
		                }
		            }
		        }
	         ];
		
		//grid组件
		var classifyGrid =  Ext.create("Ext.grid.Panel",{
			title:'指标分类管理',
			border:false,
			columnLines: true,
			layout:"fit",
			region: "center",
			width: "100%",
			height: document.body.clientHeight,
			id: "classifyGrid",
			bbar:  Ext.create("Ext.PagingToolbar", {
				store: classifyStore,
				displayInfo: true,
				displayMsg: SystemConstant.displayMsg,
				emptyMsg: SystemConstant.emptyMsg
			}),
			columns:cm,
	        selModel:sm,
	     	forceFit : true,
			store: classifyStore,
			autoScroll: true,
			stripeRows: true,
			tbar: ['指标分类编号',
			{
				id: 'classifyNoQuery',
				width: 100,   
				labelWidth: 70,
				xtype: 'textfield'
			},'&nbsp;指标分类名称',
			{
				id: 'classifyNameQuery',
                width: 100,   
                labelWidth: 70,
                xtype: 'textfield'
			},'&nbsp;参评年份',
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
                                    if ($dp.cal.getP('y') == Ext.Date.format(new Date(),"Y")) {
                                        Ext.getCmp("addClassifyBtn").setDisabled(false);
                                        Ext.getCmp('copyPreBtn').setDisabled(false);
                                    }
                                    else {
                                        Ext.getCmp("addClassifyBtn").setDisabled(true);
                                        Ext.getCmp('copyPreBtn').setDisabled(true);
                                    }
                                }
                            });
                            //,onpicked:function(){$dp.$('electYearQuery-inputEl').focus();}
                        });
                    }
                }
            },
            '&nbsp;',
			{
				id:'searchClassifyBtn',
				xtype:'button',
				disabled:false,
				text:'查询',
				iconCls:'search-button',
				handler:function(){
					var proxy = classifyStore.getProxy();
					proxy.setExtraParam("classifyVo.number",Ext.getCmp("classifyNoQuery").getValue());
					proxy.setExtraParam("classifyVo.name",Ext.getCmp("classifyNameQuery").getValue());
					proxy.setExtraParam("classifyVo.electYear",Ext.getCmp("electYearQuery").getValue());
					classifyStore.loadPage(1);
				}
			},'->',
			{
				id:'addClassifyBtn',
				xtype:'button',
				disabled:false,
				text:'添加',
				//hidden:true,
				iconCls:'add-button',
				handler:function(){
					addClassify(null);
				}
			},
			{
				id:'updateClassifyBtn',
				xtype:'button',
				text:'修改',
				//hidden:true,
				disabled:true,
				iconCls:'edit-button',
				handler:function(){
					var row = classifyGrid.getSelectionModel().getSelection()[0];
					addClassify(row);
				}
			},
			{
				id:'delClassifyBtn',
				xtype:'button',
				text:'删除',
				//hidden:true,
				disabled:true,
				iconCls:'delete-button',
				handler:function(){
					var ck = classifyGrid.getSelectionModel().getSelection();
					var itemsArray = new Array();
					for(var i=0;i<ck.length;i++){
						itemsArray.push(ck[i].data.classifyId);
					}
					var idss = itemsArray.toString();
					
					Ext.Msg.confirm(SystemConstant.alertTitle,"确认删除所选指标分类数据吗？",function(btn) {
                        if (btn == 'yes') {
                            Ext.Ajax.request({
                                url : '${ctx}/deptgrade/delClassifies.action',
                                params : {ids: idss},
                                success : function(res, options) {
                                    var result = Ext.decode(res.responseText);
                                    if(result.success == 'true'){
                                        new Ext.ux.TipsWindow(
                                                {
                                                    title: SystemConstant.alertTitle,
                                                    autoHide: 3,
                                                    html:result.msg
                                                }
                                        ).show();
                                    }else{
                                        Ext.MessageBox.show({
                                            title: SystemConstant.alertTitle,
                                            msg: result.msg,
                                            buttons: Ext.MessageBox.OK,
                                            icon: Ext.MessageBox.INFO
                                        });
                                    }
                                    //classifyStore.loadPage(1);
                                    
                                    classifyStore.load({
                                        params:{
                                            start:0,
                                            limit:SystemConstant.commonSize,
                                            'classifyVo.electYear':Ext.getCmp('electYearQuery').getValue()
                                        }
                                    });
                                }
                            });
                        }
                    });
				}
			},
            {
                id:'copyPreBtn',
                xtype:'button',
                disabled:false,
                text:'复制评分基础数据',
                //hidden:true,
                iconCls:'reset-button',
                handler:function(){
                	Ext.MessageBox.wait("", "正在生成当前年份基础数据", {
                        text:"请稍后..."
                    });
                	
                	Ext.Ajax.request({
                        url : '${ctx}/deptgrade/copyBaseData.action',
                        success : function(res, options) {
                            Ext.MessageBox.hide();
                            
                            var data = Ext.decode(res.responseText);
                            if(data.success == 'true'){
                                new Ext.ux.TipsWindow({
                                    title : SystemConstant.alertTitle,
                                    autoHide : 3,
                                    html : data.msg
                                }).show();
                            }else{
                                Ext.MessageBox.show({
                                    title: SystemConstant.alertTitle,
                                    msg: data.msg,
                                    buttons: Ext.MessageBox.OK,
                                    icon: Ext.MessageBox.INFO
                                });
                            }
                            
                            classifyStore.load({
                                params:{
                                    start:0,
                                    limit:SystemConstant.commonSize,
                                    'classifyVo.electYear':Ext.getCmp('electYearQuery').getValue()
                                }
                            });
                        }
                    });
                }
            }]/* , 
			listeners:{
				'render': function() {
					for(var i = 0;i < userPermissionArr.length;i++){
						if("resp_add_btn" == userPermissionArr[i].name){
							Ext.getCmp('addClassifyBtn').setVisible(true);
						}
						if("resp_update_btn" == userPermissionArr[i].name){
							Ext.getCmp('updateClassifyBtn').setVisible(true);
						}
						if("resp_delete_btn" == userPermissionArr[i].name){
							Ext.getCmp('delClassifyBtn').setVisible(true);
						}
					}
				}
			} */
		});
		
		classifyStore.load({
            params:{
                start:0,
                limit:SystemConstant.commonSize,
                'classifyVo.electYear':Ext.getCmp('electYearQuery').getValue()
            }
        });
		
		Ext.create("Ext.container.Viewport", {
		    layout: "border",
			renderTo: Ext.getBody(),
			items: [classifyGrid]
		});
		
		function addClassify(row){
			var oldNumber = '';
			if (row) {
				oldNumber = row.get('number');
			}
			
			Ext.define('scoreTypeModel', {
			    extend: 'Ext.data.Model',
			    fields: [
			        {name: 'dictionaryId',type:"int"},
			        {name: 'dictionaryName'},
			        {name: 'dictionaryValue'}
			    ]
			}); 
			
			var scoreTypeStore = Ext.create('Ext.data.Store', {
			    model: 'scoreTypeModel',
			    proxy: {
			       type: 'ajax',
			       url: basePath + '/user/getSelectionsByType.action',
			       extraParams:{dictTypeCode:"SCORETYPE"},
			       reader: {
			          type: 'json',
			          root: 'list'
			       }
			    },
			    autoLoad: false
			});
			
			var classifyForm = Ext.create("Ext.form.Panel", {
                layout: 'form',
                bodyStyle :'padding:2px 30px 2px 0',
                border: false,
                items: [{
                    layout : 'column',
                    border : false,
                    items:[
					{
					    layout: 'form',
					    columnWidth: .5,
					    labelWidth:60,
		                defaultType:'textfield',
		                defaults:{
		                    labelAlign:'right'
		                },
					    border: false,
					    items: [
							{
							    id:'classifyVoId',
							    name: 'classifyVo.classifyId',
							    hidden:true
							},
							{
			                    id:'classifyVoNumber',
			                    fieldLabel: '指标分类编号',
			                    name: 'classifyVo.number',
			                    maxLength:25,
			                    regex : new RegExp('^([^<^>])*$'),
			                    regexText : '不能包含特殊字符！',
			                    allowBlank: false,
			                    validator: function(value){
			                        var returnObj = null;
			                        if(value == oldNumber){
			                            return true;
			                        }else{
			                            $.ajax({
			                                url : '${ctx}/deptgrade/checkNumber.action',
			                                data:{value:value},
			                                cache : false,
			                                async : false,
			                                type : "POST",
			                                dataType : 'json',
			                                success : function (result){
			                                    if(!result.valid){
			                                        returnObj = result.reason;
			                                    }else{
			                                        returnObj = true;
			                                    }
			                                }
			                            });
			                            return returnObj;
			                        }
			                    }
			                },
			                {
			                	fieldLabel:'组织部门',
                                id:'orgNames',
                                name:'classifyVo.orgNames',
                                labelAlign:'right',
                                xtype:'textfield',
                                readOnly:true,
                                minWidth: 250,
                                allowBlank:false,
                                blankText : '组织部门不能为空',
                                listeners:{
                                    'focus':function(){
                                        /* var userId = Ext.getCmp('userId').getValue();
                                        if(userId && userId != 0){
                                            chooseOrganization('orgNames','addOrgIds',userId);
                                        }else{ */
                                            chooseOrganization('orgNames','addOrgIds');
                                        //}
                                    }
                                }
			                },
			                {id:'addOrgIds', name: 'classifyVo.orgIds',xtype:'hidden'},
			                {
			                	xtype: 'combobox',
                                fieldLabel: '汇总得分分类',
                                id:'scoreTypeId',
                                name: 'classifyVo.scoreTypeId',
                                store: scoreTypeStore,
                                valueField: 'dictionaryId',
                                displayField: 'dictionaryName',
                                typeAhead:false,
                                allowBlank:false,
                                editable:false,
                                queryMode: 'remote'
			                }
					    ]
					},
					{
                        layout: 'form',
                        columnWidth: .5,
                        labelWidth:60,
                        defaultType:'textfield',
                        defaults:{
                            labelAlign:'right'
                        },
                        border: false,
                        items: [
							{
							    id:'classifyVoName',
							    fieldLabel: '指标分类名称',
							    name: 'classifyVo.name',
							    regex : new RegExp('^([^<^>])*$'),
			                    regexText : '不能包含特殊字符！',
							    maxLength:50,
							    allowBlank: false
							},
							{
			                    id: 'classifyVoElectYear',
			                    fieldLabel: '参评年份',
			                    name: 'classifyVo.electYear',
			                    regex : new RegExp('^([^<^>])*$'),
			                    regexText : '不能包含特殊字符！',
			                    maxLength:50,
			                    allowBlank: false,
			                    listeners :{
			                        'render' : function(p){
			                            p.getEl().on('click',function(){
			                                WdatePicker({readOnly:true,dateFmt:'yyyy',maxDate:Ext.Date.format(new Date(),"Y")});
			                                //,onpicked:function(){$dp.$('electYearQuery-inputEl').focus();}
			                            });
			                        }
			                    }
			                },
			                {
			                	xtype: 'checkboxfield',
			                	fieldLabel : '是否参与评分',
			                    name : 'classifyVo.participation',
			                    id : 'isParticipation',
			                    inputValue:'true',
			                    checked : true
			                }
                        ]
					}
					]}
                ]
            });
            
			var winTitle = '添加指标分类';
			var formUrl = '${ctx}/deptgrade/addClassify.action';
			if (row) {
				winTitle = '修改指标分类';
	            formUrl = '${ctx}/deptgrade/updateClassify.action';
			}
			
            var classifyWin=Ext.create("Ext.window.Window",{
                title: winTitle,
                resizable: false,
                buttonAlign:"center",
                closeAction : 'destroy',
                height: 360,
                width: 600,
                modal:true,
                items: [classifyForm],
                buttons:[{
                    text: SystemConstant.saveBtnText,
                    handler: function(){
                        if(classifyForm.form.isValid()){
                            Ext.MessageBox.wait("", "添加岗位数据", 
                                {
                                    text:"请稍后..."
                                }
                            );
                            //alert(Ext.getCmp('isParticipation').getValue());
                            classifyForm.form.submit({
                                url : formUrl,
                                //params : {'classifyVo.participation' : true},
                                success : function(form, action) {
                                    new Ext.ux.TipsWindow({
                                        title: SystemConstant.alertTitle,
                                        autoHide: 3,
                                        html:action.result.msg
                                    }).show();
                                    
                                    classifyStore.load({
                                        params:{
                                            'classifyVo.electYear':Ext.getCmp('electYearQuery').getValue()
                                        }
                                    });
                                    classifyWin.close();
                                    Ext.MessageBox.hide();
                                },
                                failure : function(form,action){
                                    Ext.MessageBox.show({
                                        title:'提示信息',
                                        msg:action.result.msg,
                                        buttons: Ext.Msg.YES,
                                        modal : true,
                                        icon: Ext.Msg.ERROR
                                    });
                                    classifyStore.load({
                                        params:{
                                            'classifyVo.electYear':Ext.getCmp('electYearQuery').getValue()
                                        }
                                    });
                                    classifyWin.close();
                                    Ext.MessageBox.hide();
                                 }
                            });
                        }
                    }
                },{
                    text: '关闭',
                    handler: function(){
                        classifyWin.close();
                    }
                }],
                listeners: {
                	afterrender: function(){
                		if (row) {
                            Ext.getCmp('classifyVoId').setValue(row.get('classifyId'));
                            Ext.getCmp('classifyVoNumber').setValue(row.get('number'));
                            Ext.getCmp('orgNames').setValue(row.get('orgNames'));
                            Ext.getCmp('addOrgIds').setRawValue(row.get('orgIds'));
                            Ext.getCmp('classifyVoElectYear').setValue(row.get('electYear'));
                            Ext.getCmp('classifyVoName').setValue(row.get('name'));
                            
                            scoreTypeStore.load(function() {
                            	Ext.getCmp('scoreTypeId').setValue(row.get('scoreTypeId'));
                            });
                            Ext.getCmp('isParticipation').setValue(row.get('participation'));
                        }
                		else{
                			scoreTypeStore.load(function(records){
                                if(records.length>0){
                                    Ext.getCmp("scoreTypeId").setValue(records[0].get("dictionaryId"));
                                }
                            });
                		}
                	}
                }
             }).show();
		}
		
		/**
		 * 选择组织
		 */
		function chooseOrganization(objName,objId,userId){
		    var params = {};
		    if(userId){
		        params.userId = userId;
		    }
		    var orgTreePanel=Ext.create('Ext.tree.Panel', {
		        autoScroll: true,
		        border:false,
		        rootVisible: false,
		        store: Ext.create('Ext.data.TreeStore', {
		                model: 'treeModel',
		                nodeParam:'parentId',
		                autoLoad:false,
		                clearOnLoad :true,
		                proxy: {
		                    type: 'ajax',
		                    extraParams:params,
		                    reader:{
		                             type: 'json'
		                          },
		                     folderSort: true,
		                     sorters: [{
		                                property: 'orgId',
		                                direction: 'DESC'
		                     }],
		                    url :'${ctx}/org/getUnitTreeListForModifyUser.action'
		                },
		                root: {
		                      expanded: true,
		                      id:"0"
		                      }
		                })
		    });
		    
		    var win = new Ext.Window({
		            title: '选择组织',
		            closable:true,
		            width:300,
		            height:300,
		            modal:true,
		            plain:true,
		            layout:"fit",
		            resizable:true,
		            items: [
		                 orgTreePanel
		            ],
		            buttonAlign:'center', 
		            buttons:[{
		                text:SystemConstant.yesBtnText,
		                handler:function(){
		                  var choosenNodes = [];
		                  orgTreePanel.getRootNode().cascadeBy(function(child){
		                        if(child.data.id != 0 && child.get('checked')){
		                            choosenNodes.push(child);
		                        }
		                  });
		                  if(choosenNodes.length<1){
		                      Ext.Msg.showInfo("至少选择一个组织部门");
		                      return;
		                  }
		                  var ids=[];
		                  var names=[];
		                  for(var i=0;i<choosenNodes.length;i++){
		                      var node=choosenNodes[i];
		                      ids.push(node.get('nodeId'));
		                      names.push(node.get('text'));
		                  }
		                  Ext.getCmp(objName).setValue(names.join(","));
		                  Ext.getCmp(objId).setValue(ids.join(","));
		                  win.close();
		                }
		            },{
		                text:SystemConstant.closeBtnText,
		                handler:function(){
		                    win.close();
		                }
		            }]
		        });
		    win.show();
		}
		
		lockUnLock = function(classify, enable){
			var title = '确认锁定所选指标分类数据吗？';
			if (enable == 0) {
				title = '确认解锁所选指标分类数据吗？';
			}
			
			Ext.Msg.confirm(SystemConstant.alertTitle, title, function(btn) {
                if (btn == 'yes') {
                	Ext.Ajax.request({
                        url: '${ctx}/deptgrade/lockUnLock.action',
                        async:false,
                        params: {
                        	classify: classify,
                            enable: enable
                        },
                        success : function(res, options) {
                            var result = Ext.decode(res.responseText);
                            if(result.success == 'true'){
                                new Ext.ux.TipsWindow(
                                        {
                                            title: SystemConstant.alertTitle,
                                            autoHide: 3,
                                            html:result.msg
                                        }
                                ).show();
                            }else{
                                Ext.MessageBox.show({
                                    title: SystemConstant.alertTitle,
                                    msg: result.msg,
                                    buttons: Ext.MessageBox.OK,
                                    icon: Ext.MessageBox.INFO
                                });
                            }
                            //classifyStore.loadPage(1);
                            classifyStore.load({
                                params:{
                                    start:0,
                                    limit:SystemConstant.commonSize,
                                    'classifyVo.electYear':Ext.getCmp('electYearQuery').getValue()
                                }
                            });
                        }
                    });
                }
            });
		};
		
	});
	</script>
</body>
</html>