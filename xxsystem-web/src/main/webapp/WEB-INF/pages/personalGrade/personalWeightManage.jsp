<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/doc_type.jsp"%>
<html>
<head>
<%@include file="../common/meta.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/css.jsp"%>
<%@include file="../common/ext.jsp"%>
<title>个人评分权重管理</title>
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
		Ext.define('DictModel', {
            extend: 'Ext.data.Model',
            fields: [
                {name: 'dictionaryId',type:"int"},
                {name: 'dictionaryName'},
                {name: 'dictionaryValue'}
            ]
        }); 
		
		Ext.define("Weight",{
			extend:"Ext.data.Model",
			fields:[
				{name: "id"}, 
				{name: "classificationId"}, 
				{name: "classificationName"}, 
				{name: "indexTypeId"},
				{name: "indexTypeName"},
				{name: "percentage"},
				{name: "isGrade"},
				{name: "remark"},
				{name: "rwList"}
			]
		});
		
		//建立数据Store
		var usrTypeStore = Ext.create('Ext.data.Store', {
            model: 'DictModel',
            proxy: {
               type: 'ajax',
               url: basePath + '/user/getSelectionsByType.action',
               extraParams:{dictTypeCode:"GRADE_QZFL"},
               reader: {
                  type: 'json',
                  root: 'list'
               }
            },
            autoLoad: false,
            listeners:{
                load:function(store, records){
                    var obj = {dictionaryId:0, dictionaryName:'全部'};
                    store.insert(0, obj);
                }
            }
        });
		
		var idxStore = Ext.create('Ext.data.Store', {
            model: 'DictModel',
            proxy: {
               type: 'ajax',
               url: basePath + '/user/getSelectionsByType.action',
               extraParams:{dictTypeCode:"GRADE_ZBFL"},
               reader: {
                  type: 'json',
                  root: 'list'
               }
            },
            autoLoad: false,
            listeners:{
                load:function(store, records){
                    var obj = {dictionaryId:0, dictionaryName:'全部'};
                    store.insert(0, obj);
                }
            }
        });
		
		var weightStore=Ext.create("Ext.data.Store", {
	        pageSize: SystemConstant.commonSize,
	        model:"Weight",
	        remoteSort:true,
			proxy: {
	            type:"ajax",
	            actionMethods: {
                	read: 'POST'
           		},
			    url: "${ctx}/personalWeight/getPersonalWeightList.action",
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
		    		var c = weightGrid.getSelectionModel().getSelection();
                    if(c.length > 0){
                        Ext.getCmp('delWeightBtn').setDisabled(false);
                    }else{
                        Ext.getCmp('delWeightBtn').setDisabled(true);
                    }
                    if(c.length == 1){
                        Ext.getCmp('updateWeightBtn').setDisabled(false);
                    }else{
                        Ext.getCmp('updateWeightBtn').setDisabled(true);
                    }
				}
			}
	    });
		
		var cm=[
				{header:"序号",xtype: "rownumberer",width:60,align:"center",menuDisabled: true,sortable :false},
	            {header: "ID",width: 70,dataIndex: "id",hidden: true,menuDisabled: true,sortable :false},
	            {header: "参评人员分类",width: 100,dataIndex: "classificationName",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}},
	            {header: "考核指标",width: 100,dataIndex: "indexTypeName",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}},
	            {header: "权重",width: 100,dataIndex: "percentage",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}},
	            {header: "备注",width: 60,dataIndex: "remark",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}
				}
	         ];
		
		//grid组件
		var weightGrid =  Ext.create("Ext.grid.Panel",{
			title:'个人评分权重管理',
			border:false,
			columnLines: true,
			layout:"fit",
			region: "center",
			width: "100%",
			height: document.body.clientHeight,
			id: "weightGrid",
			bbar:  Ext.create("Ext.PagingToolbar", {
				store: weightStore,
				displayInfo: true,
				displayMsg: SystemConstant.displayMsg,
				emptyMsg: SystemConstant.emptyMsg
			}),
			columns:cm,
	        selModel:sm,
	     	forceFit : true,
			store: weightStore,
			autoScroll: true,
			stripeRows: true,
			tbar: ['参评人员分类',
			{
                xtype: 'combobox',
                id:'usrTypeCombo',
                store: usrTypeStore,
                valueField: 'dictionaryId',
                displayField: 'dictionaryName',
                typeAhead:false,
                editable:false,
                queryMode: 'remote'
            },'&nbsp;考核指标',
            {
                xtype: 'combobox',
                id:'idxCombo',
                store: idxStore,
                valueField: 'dictionaryId',
                displayField: 'dictionaryName',
                typeAhead:false,
                editable:false,
                queryMode: 'remote'
            },
            '&nbsp;',
			{
				id:'searchRespBtn',
				xtype:'button',
				disabled:false,
				text:'查询',
				iconCls:'search-button',
				handler:function(){
					var proxy = weightStore.getProxy();
					proxy.setExtraParam("usrTypeId",Ext.getCmp("usrTypeCombo").getValue());
					proxy.setExtraParam("idxId",Ext.getCmp("idxCombo").getValue());
					weightStore.loadPage(1);
				}
			},'->',
			{
				id:'addWeightBtn',
				xtype:'button',
				disabled:false,
				text:'添加',
				//hidden:true,
				iconCls:'add-button',
				handler:function(){
					addWeight(null);
				}
			},
			{
				id:'updateWeightBtn',
				xtype:'button',
				text:'修改',
				//hidden:true,
				disabled:true,
				iconCls:'edit-button',
				handler:function(){
					var row = weightGrid.getSelectionModel().getSelection()[0];
					addWeight(row);
				}
			},
			{
				id:'delWeightBtn',
				xtype:'button',
				text:'删除',
				//hidden:true,
				disabled:true,
				iconCls:'delete-button',
				handler:function(){
					var ck = weightGrid.getSelectionModel().getSelection();
					var itemsArray = new Array();
					for(var i=0;i<ck.length;i++){
						itemsArray.push(ck[i].data.id);
					}
					var idss = itemsArray.toString();
					
					Ext.Msg.confirm(SystemConstant.alertTitle,"确认删除所选指标数据吗？",function(btn) {
                        if (btn == 'yes') {
                            Ext.Ajax.request({
                                url : '${ctx}/personalWeight/deletePersonalWeigh.action',
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
                                    weightStore.loadPage(1);
                                }
                            });
                        }
                    });
				}
			}]
		});
		usrTypeStore.load(function(){
            Ext.getCmp("usrTypeCombo").setValue(0);
        });
		idxStore.load(function(){
            Ext.getCmp("idxCombo").setValue(0);
        });
		weightStore.load();
		
		Ext.create("Ext.container.Viewport", {
		    layout: "border",
			renderTo: Ext.getBody(),
			items: [weightGrid]
		});
		
		function addWeight(row){
			var count = 0;
			
			var usrTypeFormStore = Ext.create('Ext.data.Store', {
	            model: 'DictModel',
	            proxy: {
	               type: 'ajax',
	               url: basePath + '/user/getSelectionsByType.action',
	               extraParams:{dictTypeCode:"GRADE_QZFL"},
	               reader: {
	                  type: 'json',
	                  root: 'list'
	               }
	            },
	            autoLoad: false
	        });
	        
	        var idxFormStore = Ext.create('Ext.data.Store', {
	            model: 'DictModel',
	            proxy: {
	               type: 'ajax',
	               url: basePath + '/user/getSelectionsByType.action',
	               extraParams:{dictTypeCode:"GRADE_ZBFL"},
	               reader: {
	                  type: 'json',
	                  root: 'list'
	               }
	            },
	            autoLoad: false
	        });
			
			var weightForm = Ext.create("Ext.form.Panel", {
				region: "north",
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
							    id:'personalWeightId',
							    name: 'personalWeightVo.id',
							    hidden:true
							},
							{
				                xtype: 'combobox',
				                fieldLabel: '参评人员分类',
                                allowBlank: false,
				                id:'usrTypeFormCombo',
				                name:'personalWeightVo.classificationId',
				                store: usrTypeFormStore,
				                valueField: 'dictionaryId',
				                displayField: 'dictionaryName',
				                typeAhead:false,
				                editable:false,
				                queryMode: 'remote'
				            },
				            {
                                id: 'weightTextfield',
                                fieldLabel: '权重',
                                name: 'personalWeightVo.percentage',
                                regex : new RegExp('^[0-9]+(.[0-9]{1,2})?$'),
                                regexText : '保留两位小数！',
                                maxLength:50,
                                allowBlank: false
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
                                xtype: 'combobox',
                                fieldLabel: '考核指标',
                                allowBlank: false,
                                id:'idxFormCombo',
                                name:'personalWeightVo.indexTypeId',
                                store: idxFormStore,
                                valueField: 'dictionaryId',
                                displayField: 'dictionaryName',
                                typeAhead:false,
                                editable:false,
                                queryMode: 'remote'
                            },
                            {
                                xtype: 'checkboxfield',
                                fieldLabel : '是否参与评分',
                                name : 'personalWeightVo.isGrade',
                                id : 'isGrade',
                                inputValue:'1',
                                checked : true
                            }
                        ]
					},
					{
                        layout: 'form',
                        columnWidth: 1,
                        labelWidth:60,
                        defaultType:'textfield',
                        defaults:{
                            labelAlign:'right'
                        },
                        border: false,
                        items: [
                            {
                                id:'weightRemark',
                                xtype:'textarea',
                                rows:3,
                                fieldLabel: '备注',
                                name: 'personalWeightVo.remark',
                                //regex : new RegExp('^([^<^>])*$'),
                                //regexText : '不能包含特殊字符！',
                                maxLength:1000
                            }
                        ]
                    }
					]}
                ]
            });
			
			Ext.define("RoleWeight",{
	            extend:"Ext.data.Model",
	            fields:[
	                {name: "id"}, 
	                {name: "roleId"}, 
	                {name: "roleName"}, 
	                {name: "percentage"}
	            ]
	        });
			
			Ext.define("Role",{
	            extend:"Ext.data.Model",
	            fields:[
	                {name: "roleId"}, 
	                {name: "roleName"}
	            ]
	        });
			
			var weightRoleStore = Ext.create("Ext.data.Store", {
		        model:"RoleWeight"
		    });
			
			if (row) {
				weightRoleStore.add(row.get('rwList'));
			}
			
			var roleStore = Ext.create('Ext.data.Store', {
	            model: 'Role',
	            proxy: {
	               type: 'ajax',
	               url: '${ctx}/deptgrade/getAllRole.action',
	               reader: {
	                  type: 'json'
	               }
	            },
	            autoLoad: true
	        });
			
			var weightRoleCm=[
                {header:"序号",xtype: "rownumberer",width:60,align:"center",menuDisabled: true,sortable :false},
                {header: "ID",width: 50,dataIndex: "id",hidden: true,menuDisabled: true,sortable :false},
                {header: "角色",width: 100,dataIndex: "roleName",menuDisabled: true,sortable :false,
                    renderer : function(value, cellmeta, record, rowIndex,
                            columnIndex, store) {
                        cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return value;
                    },
                    field: {
                        xtype:'combo',
                        allowBlank: false,
                        editable: false,
                        store: roleStore,
                        queryMode: 'remote',
                        displayField: 'roleName',
                        valueField: 'roleName'
                    }
                },
                {header: "权重",width: 100,dataIndex: "percentage",menuDisabled: true,sortable :false,
                	renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
                        var showStr = Math.round(value * 100) + "%";
                        cellmeta.tdAttr = 'data-qtip="' + showStr + '"';
                        return showStr;
                    },
                    field: {
                        xtype:'textfield',
                        maxLength:10,
                        regex : new RegExp('^[0-9]+(.[0-9]{1,2})?$'),
                        regexText : '保留两位小数！',
                        allowBlank: false
                    }
                }
            ];
			
			var weightRoleSm = Ext.create("Ext.selection.CheckboxModel",{
		        injectCheckbox:0,
		        listeners: {
		            selectionchange: function(){
		                var c = weightRoleGrid.getSelectionModel().getSelection();
		                if (c.length > 0) {
		                    Ext.getCmp('weightRoleBtn').setDisabled(false);
		                } else {
		                    Ext.getCmp('weightRoleBtn').setDisabled(true);
		                }
		            }
		        }
		    });
			
			var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
		        clicksToEdit: 1
		    });
			
			var weightRoleGrid =  Ext.create("Ext.grid.Panel",{
		        border:false,
		        columnLines: true,
		        layout:"fit",
		        region: "center",
		        height: 120,
		        id: "weightRoleGrid",
		        columns:weightRoleCm,
		        selModel:weightRoleSm,
		        plugins: [cellEditing],
		        forceFit : true,
		        store: weightRoleStore,
		        autoScroll: true,
		        stripeRows: true,
		        tbar:["  岗位职责",
		              "->",
		            {
		                xtype:'button',
		                disabled:false,
		                text:'添加',
		                iconCls:'add-button',
		                handler:function(){
		                	count++;
		                    //拼接一个数据格式;
		                    var data= "{ id:" + count
		                            + ", roleId:''"
                                    + ", roleName:''"
                                    + ", percentage:''}";

		                    weightRoleStore.add(eval("("+data+")"));
		                }
		            },
		            {
		                id : 'weightRoleBtn',
		                xtype : 'button',
		                disabled : true,
		                text : '删除',
		                iconCls : 'delete-button',
		                handler : function() {
		                    var rows = weightRoleGrid.getSelectionModel().getSelection();
		                    Ext.Msg.confirm('系统提示','确定要删除这'+rows.length+'条记录吗?',function(btn){
		                        if(btn=='yes'){
		                            for(var i=0; i<rows.length;i++){
		                            	weightRoleStore.remove(rows[i]);
		                            }
		                            weightRoleGrid.getView().refresh();//刷新行号
		                        }
		                    });
		                }
		            }
		        ]
		    });
			//weightRoleStore.load();
            
			var winTitle = '添加个人评分权重';
			var formUrl = '${ctx}/personalWeight/addPersonalWeigh.action';
			if (row) {
				winTitle = '修改个人评分权重';
	            formUrl = '${ctx}/personalWeight/updatePersonalWeigh.action';
			}
			
            var weightRoleWin=Ext.create("Ext.window.Window",{
                title: winTitle,
                resizable: false,
                buttonAlign:"center",
                closeAction : 'destroy',
                height: 400,
                width: 600,
                modal:true,
                layout: 'border',
                items: [weightForm, weightRoleGrid],
                buttons:[{
                    text: SystemConstant.saveBtnText,
                    handler: function(){
                        if(weightForm.form.isValid()){
                            Ext.MessageBox.wait("", "添加个人评分权重数据", 
                                {
                                    text:"请稍后..."
                                }
                            );
                            
                            var weightRoleLst = '';
                            var c = weightRoleStore.getCount();
                            if (c > 0) {
                            	for(var i=0; i<weightRoleStore.getCount(); i++){
                                    var re = weightRoleStore.getAt(i);
                                    var roleName = re.get('roleName');
                                    var roleId = roleStore.findRecord('roleName', roleName).get('roleId');
                                    var percentage = re.get('percentage');
                                    
                                    if (!roleName || !percentage) {
                                        Ext.MessageBox.show({
                                            title:'提示信息',
                                            msg:"角色、权重不能为空！",
                                            buttons: Ext.Msg.YES,
                                            modal : true,
                                            icon: Ext.Msg.INFO
                                        });
                                        return false;
                                    }
                                    
                                    weightRoleLst += '&personalWeightVo.rwList[' + i + '].roleId=' + roleId
                                                + '&personalWeightVo.rwList[' + i + '].percentage=' + percentage;
                                }
                            	
                            	weightRoleLst = weightRoleLst.substring(1)
                            }
                            
                            weightForm.form.submit({
                                url : formUrl,
                                params : weightRoleLst,
                                success : function(form, action) {
                                    new Ext.ux.TipsWindow({
                                        title: SystemConstant.alertTitle,
                                        autoHide: 3,
                                        html:action.result.msg
                                    }).show();
                                    
                                    weightStore.load();
                                    weightRoleWin.close();
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
                                    weightStore.load();
                                    weightRoleWin.close();
                                    Ext.MessageBox.hide();
                                 }
                            });
                        }
                    }
                },{
                    text: '关闭',
                    handler: function(){
                        weightRoleWin.close();
                    }
                }],
                listeners: {
                	afterrender: function(){
                		if (row) {
                            Ext.getCmp('personalWeightId').setValue(row.get('id'));
                            usrTypeFormStore.load(function(){
	                            Ext.getCmp('usrTypeFormCombo').setValue(row.get('classificationId'));
                            });
                            
                            idxFormStore.load(function(){
                            	Ext.getCmp('idxFormCombo').setValue(row.get('indexTypeId'));
                            });
                            
                            Ext.getCmp('weightTextfield').setValue(row.get('percentage'));
                            Ext.getCmp('isGrade').setValue(row.get('isGrade'));
                            Ext.getCmp('weightRemark').setValue(row.get('remark'));
                        }
                	}
                }
             }).show();
		}
	});
	</script>
</body>
</html>