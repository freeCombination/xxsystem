<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/doc_type.jsp"%>
<html>
<head>
<%@include file="../common/meta.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/css.jsp"%>
<%@include file="../common/ext.jsp"%>
<title>资源管理</title>
<link href="" rel="SHORTCUT ICON" />
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
	
	var resourceTypeArr = new Array();
	<c:forEach items="${resDictList}" var="v">
		var obj=new Object();
		obj.value='${v.pkDictionaryId }';
		obj.name='${v.dictionaryName }';
		resourceTypeArr.push(obj);
	</c:forEach>
</script>
	<script type="text/javascript">
		var selectNode=null;
		Ext.onReady(function() {
			Ext.QuickTips.init();
			//自动引入其他需要的js
			Ext.require(["Ext.container.*",
			             "Ext.grid.*", 
			             "Ext.toolbar.Paging", 
			             "Ext.form.*",
						 "Ext.data.*" ]);
			document.getElementById("tempId").value = "";
			document.getElementById("tempText").value = "";
			
			Ext.define('resourceTypeModel', {
		     extend: 'Ext.data.Model',
		     fields: [
		         {name: 'dictionaryId',type:"int"},
		         {name: 'dictionaryName'},
		         {name: 'dictionaryValue'}
		     ]
		 	});	
			
			//建立Model模型对象
			Ext.define("resourceModel",{
				extend:"Ext.data.Model",
				fields:[
					{name: "resourceId",type:"int"}, 
					{name: "resourceName"},
					{name: "code"},
					{name: "disOrder"},
					{name: "resourceTypeId",type:"int"},
					{name: "resourceTypeName"},
					{name: "resourceTypeValue"},
					{name: "urlpath"},
					{name: "createDate"},
					{name: "parentResourceId"},
					{name: "parentResourceName"},
					{name: "remarks"}
					]
			});
			//建立数据Store
			var resourceStore=Ext.create("Ext.data.Store", {
		        pageSize: SystemConstant.commonSize,
		        model:"resourceModel",
		        remoteSort:true,
		        actionMethods: {
	                read: 'POST'
	            },
				proxy: {
		            type:"ajax",
				    url: "${ctx}/resource/getResourceList.action",
				    reader: {
					     totalProperty: "totalSize",
					     root: "list"
				    },
		        simpleSortMode :true
		        },
		        sorters:[{
		            property:"id",
		            direction:"ASC"
		        }]
			});
			
			var cm=[
					{xtype: "rownumberer",width:60,text:'序号',align:"center",menuDisabled: true},
		            {header: "resourceId",width: 70,dataIndex: "resourceId",hidden: true,menuDisabled: true},
		            {header: "parentResId",width: 200,dataIndex: "parentResourceId",hidden: true,menuDisabled: true},
		            {header: "上级",width: 200,dataIndex: "parentResourceName",menuDisabled: true,sortable :false},
		            {header: "名称",width: 200,dataIndex: "resourceName",menuDisabled: true,sortable :false},
		            {header: "类别",width: 200,dataIndex: "resourceTypeId",menuDisabled: true,sortable :false,
		            	renderer: function(value, cellmeta, record, rowIndex, columnIndex, store){
		            		for(var i=0;i<resourceTypeArr.length;i++){
								if(value==resourceTypeArr[i].value){
									return resourceTypeArr[i].name;
								}
							}
		            	}
		            },
		            {header: "编码",width: 200,dataIndex: "code",menuDisabled: true,sortable :false},
		            {header: "URL",width: 200,dataIndex: "urlpath",menuDisabled: true,sortable :false},
		            {header: "描述",width: 200,dataIndex: "remarks",menuDisabled: true,sortable :false}
		           ];
			
			//grid组件
			var resourceGrid =  Ext.create("Ext.grid.Panel",{
				title:'资源管理',
				border:false,
				columnLines: true,
				width: "100%",
				height: document.body.clientHeight,
				id: "resourceGrid",
				
				bbar:  Ext.create("Ext.PagingToolbar", {
					store: resourceStore,
					displayInfo: true,
					displayMsg: SystemConstant.displayMsg,
					emptyMsg: SystemConstant.emptyMsg
				}),
		     	forceFit : true,
				store: resourceStore,
				autoScroll: true,
				stripeRows: true,
				listeners:{
					'render':function(){
						for(var i = 0;i < userPermissionArr.length;i++){
							if("resourcemanage_addpra_btn" == userPermissionArr[i].name){
								Ext.getCmp('addParentResource').setVisible(true);
							}
							if("resourcemanage_add_btn" == userPermissionArr[i].name){
								Ext.getCmp('addResource').setVisible(true);
							}
							if("resourcemanage_delete_btn" == userPermissionArr[i].name){
								Ext.getCmp('deleteResource').setVisible(true);
							}
							if("resourcemanage_update_btn" == userPermissionArr[i].name){
								Ext.getCmp('updateResource').setVisible(true);
							}
				 		 } 
					}
				},
				columns:cm/*,
				tbar: ['->',
				{
					id:'addResourceBtn',
					xtype:'button',
					text:'新增',
					iconCls:'add-button',
					handler:function(){
						var a1 = document.getElementById("tempId").value;
						var a2 = document.getElementById("tempText").value;
						if(a1 != "" && a1 != null && a2 != null && a2 != ""){
							createResourceAddInfo();
						}else{
							Ext.MessageBox.show({
								title: SystemConstant.alertTitle,
								msg: "请选择类型信息",
								buttons: Ext.MessageBox.OK,
								icon: Ext.MessageBox.ERROR
							});
							return;
						}
					}
				},'-',
				{
					id:'delResourceBtn',
					xtype:'button',
					text:'删除',
					disabled:true,
					iconCls:'delete-button',
					handler:function(){
						var ck = Ext.getCmp('resourceGrid').getSelectionModel().getSelection();
						var itemsArray = new Array();
						for(var i=0;i<ck.length;i++){
							itemsArray.push(ck[i].data.id);
						}
						var idss = itemsArray.toString();
						var nodeId = document.getElementById("tempId").value;
						Ext.Msg.confirm(SystemConstant.alertTitle,"确认删除所选资源及其子类资源吗？",function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : '${ctx}/resource/deleteResource.action',
									params : {resourceIds: idss},
									success : function(res, options) {
											var data = Ext.decode(res.responseText);
		        	                		if(data.success == 'true'){
												new Ext.ux.TipsWindow({
													title:SystemConstant.alertTitle,
													html: data.msg
												}).show();
											}else{
												Ext.MessageBox.show({
													title: SystemConstant.alertTitle,
													msg: data.msg,
													buttons: Ext.MessageBox.OK,
													icon: Ext.MessageBox.ERROR
											});
										}
										resourceStore.load({params:{start:0,limit:SystemConstant.commonSize,parentResId:nodeId}});
									}
							});
							}
						});
					}
				},'-',
				{
					id:'updateResourceBtn',
					xtype:'button',
					text:'修改',
					disabled:true,
					iconCls:'update-button',
					handler:function(){
						createUpdateResourceInfo();
					}
				}]
			*/});
			
			
			
			
			//右键菜单
			var materialMenu =  Ext.create("Ext.menu.Menu", {
				floating:true,
				items : [{
							text : SystemConstant.addParentBtnText,
							id : 'addParentResource',
							hidden:true,
							handler : function() {
								createParentResourceAddWin();
							}
						},
						{
							text : SystemConstant.addNextBtnText,
							id : 'addResource',
							hidden:true,
							handler : function() {
								createResourceAddWin();
							}
						},
						{
							text : SystemConstant.modifyBtnText,
							id : 'updateResource',
							hidden:true,
							handler : function() {
								var resId = selectNode.raw.nodeId;
								Ext.Ajax.request({
											url : '${ctx}/resource/getResourceById.action',
											params : {resourceId: resId},
											success : function(res, options) {
												 var data = Ext.decode(res.responseText);
												 createResourceUpdateWin(data.list[0]);
											}
									});
							}
						},
						{
							text : SystemConstant.deleteBtnText,
							id : 'deleteResource',
							hidden:true,
							handler : function() {
								Ext.Msg.confirm(SystemConstant.alertTitle,"确认删除所选资源及其子资源吗？",function(btn) {
									if (btn == 'yes') {
										Ext.Ajax.request({
											url : '${ctx}/resource/deleteResource.action',
											params : {resourceIds: selectNode.raw.nodeId},
											success : function(res, options) {
												 selectNode.remove();
												 treePanel.getStore().load({params: {parentResId: selectNode.raw.nodeId}});
												 var data = Ext.decode(res.responseText); 
												 if(data.success == 'true'){
													new Ext.ux.TipsWindow({
														title:SystemConstant.alertTitle,
														html: data.msg
													}).show();
											     }else{
													Ext.MessageBox.show({
														title: SystemConstant.alertTitle,
														msg: data.msg,
														buttons: Ext.MessageBox.OK,
														icon: Ext.MessageBox.ERROR
													});
												  }
											}
									});
									}
								});
							}
						} ]
				});

			
			//treepanel

				resourceTypeStore = Ext.create('Ext.data.Store', {
			     	model: 'resourceTypeModel',
			     	proxy: {
			      	   type: 'ajax',
			      	   url: '${ctx}/user/getSelectionsByType.action',
			      	   extraParams:{dictTypeCode:"RESOURCETYPE"},
			       	   reader: {
			       	      type: 'json',
			       	      root: 'list'
			      	   }
			     	},
			     	autoLoad: true
			 	});


			 var treeStore = Ext.create("Ext.data.TreeStore", {
			        proxy: {
			            type: "ajax",
			            actionMethods: {
			                read: 'POST'
			            },
			            url: "${ctx}/resource/getResourceByParentId.action"
			        },
			        root: {
			        	text:"资源",
			        	nodeId:"0"
			        },
			        listeners: {
                        beforeload: function (ds, opration, opt) {
                            opration.params.parentResId = opration.node.raw.nodeId;
                        }
                    }
			    });
			 
			 treeStore.on("load",function(store, node, records){
				treePanel.getSelectionModel().select(treePanel.getRootNode().firstChild,true);
				treePanel.fireEvent("itemclick",treePanel.getView(),node.firstChild);
			});
	
			 
			 var treePanel=Ext.create("Ext.tree.Panel", {
			 		title:'资源信息',
			 		layout:'fit',
					width: 200,
					region: "west",
					border:false,
					collapsible: true,
            		split: true,
					collapseMode:"mini",
			       	store: treeStore,
			     	id:"typeTree",
			        height: document.body.clientHeight,
			        useArrows: true,
			        rootVisible : true,
			        dockedItems: [{
			        	xtype: 'toolbar',
						style:"border-top:0px;border-left:0px",
						items:[{
					        iconCls: "icon-expand-all",
					        text:'展开',
							tooltip: "展开所有",
					        handler: function(){ treePanel.expandAll(); },
					        scope: this
					    },{
					        iconCls: "icon-collapse-all",
					        text:'折叠',
					        tooltip: "折叠所有",
					        handler: function(){ treePanel.collapseAll(); },
					        scope: this
					    },{
							text : SystemConstant.synchronizeBtnText,
							id : 'synchInfo',
							iconCls: "refresh-button",
							handler : function() {
								
								Ext.Msg.confirm(SystemConstant.alertTitle,"同步将会删除现有数据，确定要同步吗？",function(btn) {
					  				if (btn == 'yes') {
					  					Ext.MessageBox.wait("", "同步资源数据", 
												{
			                						text:"请稍后..."
			            			}
			            		);
											Ext.Ajax.request({
												timeout:60000000,
												url : '${ctx}/resource/synchronizeResourceInfo.action',
												success : function(res, options) {
													var data = Ext.decode(res.responseText);
													treePanel.getStore().load();
													Ext.MessageBox.hide();
													
													if(data.success){
														new Ext.ux.TipsWindow({
															title:SystemConstant.alertTitle,
															html: data.msg
														}).show();
													}else{
														Ext.MessageBox.show({
															title: SystemConstant.alertTitle,
															msg: data.msg,
															buttons: Ext.MessageBox.OK,
															icon: Ext.MessageBox.ERROR
														});
													}
												}
											});
					  				}
					  		});
								
							}
					}
					   ]
			        }],
			        listeners:{
			        	"itemcontextmenu":function(tree, record, item, index, e, eOpts){
			        		selectNode=record;
			        		e.preventDefault();
			        		if(record.raw.nodeId == "0"){
			        			Ext.getCmp("updateResource").setVisible(false);
			        			Ext.getCmp("deleteResource").setVisible(false);
			        		}
			        		materialMenu.showAt(e.getPoint());
			        	}
			        }
			    });
			 treePanel.on("itemclick",function(view,record,item,index,e,opts){  
			     //获取当前点击的节点  
			      var treeNode=record.raw;  
			      var text=treeNode.text;
			      var id = treeNode.nodeId;
			      document.getElementById("tempId").value = id;
			      document.getElementById("tempText").value = text;
			      resourceStore.on('beforeload',function(store,options){
			    	  var new_params = {"parentResId":id};
			    	  Ext.apply(store.proxy.extraParams,new_params);
			      });
			      resourceStore.loadPage(1);
			      //resourceStore.load({params:{start:0,limit:SystemConstant.commonSize}});
		 	});
			 //添加上级
			 createParentResourceAddWin = function(){
			 	var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
			 	var parentResourceAddForm=Ext.create("Ext.form.Panel", {
					url:"${ctx}/resource/addResource.action",
					layout: 'form',
					bodyStyle :'padding:15px 10px 0 0',
					border: false,
					labelAlign: 'right',
					fieldDefaults: {
			            labelWidth: 60,
			        	labelAlign: 'right'
			        },
			        defaults: {
				        anchor: '60%'
				    },
				    defaultType: 'textfield',
				    items: [
				    {
				     	xtype: 'combobox',
				        fieldLabel: '类型',
				        name: 'resource.resourceType.pkDictionaryId',
				        store: resourceTypeStore,
				        valueField: 'dictionaryId',
				        displayField: 'dictionaryName',
				        editable:false,
				        queryMode: 'remote',
				        //beforeLabelTextTpl: required,
				        width: 100,
				        allowBlank: false
				    },
				    {
				        fieldLabel: '名称',
				        name: 'resource.resourceName',
				        //beforeLabelTextTpl: required,
				        width: 100,
				        allowBlank: false,
				        validator:function(value){
				        	var returnObj = null;
				        	$.ajax({
									url : '${ctx}/resource/validateResourceProperties.action',
									data:{key:'0',value:value},
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
				    },
				    {
				        fieldLabel: 'URL',
				        name: 'resource.urlpath',
				        //beforeLabelTextTpl: required,
				        width: 100,
				        allowBlank: false,
				        validator: function(value){
								var returnObj = null;
								$.ajax({
									url : '${ctx}/resource/validateResourceProperties.action',
									data:{key:'2',value:value},
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
				    },
				    {
				        fieldLabel: '编码',
				        name: 'resource.code',
				        //beforeLabelTextTpl: required,
				        width: 100,
				        allowBlank: false,
				        validator: function(value){
								var returnObj = null;
								$.ajax({
									url : '${ctx}/resource/validateResourceProperties.action',
									data:{key:'1',value:value},
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
				    },
				    {
				        fieldLabel: '描述',
				        name: 'resource.remarks',
				        width: 100
				    },{
				        fieldLabel: '排序',
				        name: 'resource.disOrder',
				        value:responseData.disOrder,
				        width: 100,
				        regex : /^[0-9]+$/,regexText : '排序号码只能为数字。'
				    }]
				 });
				 parentResourceAddWin=Ext.create("Ext.window.Window",{
					title: '添加上级资源',
					resizable: false,
					buttonAlign:"center",
				  	height: 260,
				    width: 300,
				    modal : true,
				    layout: 'fit',
				    items: [parentResourceAddForm],
				    buttons:[{
						    text: SystemConstant.saveBtnText,
					    	handler: function(){
					    		if(parentResourceAddForm.form.isValid()){
					    			parentResourceAddForm.form.submit({
				    				   success : function(form, action) {
				    				   		var  idPath = selectNode.getPath("id");  
						                   treePanel.getStore().load({  
						                        node: treePanel.getRootNode(),  
						                        callback: function () {  
						                            treePanel.selectPath(idPath, 'id'); 
						                            treePanel.fireEvent("itemclick",treePanel.getSelectionModel().getSelection(),selectNode); 
						                        }  
						                    }); 
		        	                		var data = action.result; 
												 if(data.success == 'true'){
													new Ext.ux.TipsWindow({
														title:SystemConstant.alertTitle,
														html: data.msg
													}).show();
											     }else{
													Ext.MessageBox.show({
														title: SystemConstant.alertTitle,
														msg: data.msg,
														buttons: Ext.MessageBox.OK,
														icon: Ext.MessageBox.ERROR
													});
											}  
				    					    parentResourceAddWin.close();
				    				   }
					    			});
				 				}
			                }
						},{
						    text: '关闭',
			                handler: function(){
			                	parentResourceAddWin.close();
			                }
					}]
				 });
				 parentResourceAddWin.show();
			 }
			 
			 //添加上级资源资源
			 createResourceAddWin = function(){
				 var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
				 var resourceAddForm=Ext.create("Ext.form.Panel", {
					url:"${ctx}/resource/addResource.action",
					layout: 'form',
					bodyStyle :'padding:15px 10px 0 0',
					border: false,
					labelAlign: 'right',
					fieldDefaults: {
			            labelWidth: 60,
			        	labelAlign: 'right'
			        },
			        defaults: {
				        anchor: '60%'
				    },
				    defaultType: 'textfield',
				    items: [{
				        fieldLabel: '上级资源ID',
				        name: 'resource.resource.resourceId',
				        value:selectNode.raw.nodeId,
				        hidden:true,
				        //beforeLabelTextTpl: required,
				        width: 100,
				        allowBlank: false
				    }, {
				        fieldLabel: '上级',
				        name: 'resource.resource.resourceName',
				        value:selectNode.raw.text,
				        disabled:true,
				        //beforeLabelTextTpl: required,
				        width: 100,
				        allowBlank: false
				    },
				    {
				     	xtype: 'combobox',
				        fieldLabel: '类型',
				        name: 'resource.resourceType.pkDictionaryId',
				        store: resourceTypeStore,
				        valueField: 'dictionaryId',
				        displayField: 'dictionaryName',
				        editable:false,
				        queryMode: 'remote',
				        //beforeLabelTextTpl: required,
				        width: 100,
				        allowBlank: false
				    },
				    {
				        fieldLabel: '名称',
				        name: 'resource.resourceName',
				        //beforeLabelTextTpl: required,
				        width: 100,
				        allowBlank: false,
				        validator: function(value){
								var returnObj = null;
								$.ajax({
									url : '${ctx}/resource/validateResourceProperties.action',
									data:{key:'0',value:value},
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
				    },
				    {
				        fieldLabel: 'URL',
				        name: 'resource.urlpath',
				        //beforeLabelTextTpl: required,
				        width: 100,
				        allowBlank: false,
				        validator: function(value){
								var returnObj = null;
								$.ajax({
									url : '${ctx}/resource/validateResourceProperties.action',
									data:{key:'2',value:value},
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
				    },
				    {
				        fieldLabel: '编码',
				        name: 'resource.code',
				        //beforeLabelTextTpl: required,
				        width: 100,
				        allowBlank: false,
				        validator: function(value){
								var returnObj = null;
								$.ajax({
									url : '${ctx}/resource/validateResourceProperties.action',
									data:{key:'1',value:value},
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
				    },
				    {
				        fieldLabel: '描述',
				        name: 'resource.remarks',
				        width: 100
				    },{
				        fieldLabel: '排序',
				        name: 'resource.disOrder',
				        value:responseData.disOrder,
				        width: 100,
				        regex : /^[0-9]+$/,regexText : '排序号码只能为数字。'
				    }]
				 });
				 resourceAddWin=Ext.create("Ext.window.Window",{
					title: '添加资源',
					closable:true,
					resizable: false,
					buttonAlign:"center",
				  	height: 260,
				    width: 300,
				    modal : true,
				    layout: 'fit',
				    closeAction:'destroy',
				    items: [resourceAddForm],
				    buttons:[{
						    text: SystemConstant.saveBtnText,	
					    	handler: function(){
					    		if(resourceAddForm.form.isValid()){
					    			resourceAddForm.form.submit({
				    				   success : function(form, action) {  
				    					  	resourceAddWin.hide();
				    				  		var  idPath = selectNode.getPath("id");  
						                   treePanel.getStore().load({  
						                        node: treePanel.getRootNode(),  
						                        callback: function () {  
						                            treePanel.selectPath(idPath, 'id'); 
						                            treePanel.fireEvent("itemclick",treePanel.getSelectionModel().getSelection(),selectNode); 
						                        }  
						                    }); 
		        	                	   	var data = action.result; 
		        	                		if(data.success == 'true'){
												new Ext.ux.TipsWindow({
													title:SystemConstant.alertTitle,
													html: data.msg
												}).show();
											}else{
												Ext.MessageBox.show({
													title: SystemConstant.alertTitle,
													msg: data.msg,
													buttons: Ext.MessageBox.OK,
													icon: Ext.MessageBox.ERROR
												});
											}
											resourceAddWin.close();
				    				   }
					    			});
				 				}
			                }
						},{
						    text: '关闭',
			                handler: function(){
			                	resourceAddWin.close();
			                }
					}]
				 });
				 resourceAddWin.show();
			 }
			 
			//修改
			 createResourceUpdateWin = function(responseData){
			 	 var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
				 var resourceModifyForm=Ext.create("Ext.form.Panel", {
					url:"${ctx}/resource/editResource.action",
					layout: 'form',
					bodyStyle :'padding:15px 10px 0 0',
					border: false,
					labelAlign: 'right',
					fieldDefaults: {
			            labelWidth: 60,
			        	labelAlign: 'right'
			        },
			        defaults: {
				        anchor: '60%'
				    },
				    defaultType: 'textfield',
				    items: [{
				        fieldLabel: 'ID',
				        name: 'typeId',
				        value:selectNode.raw.nodeId,
				        hidden:true,
				        width: 100,
				        allowBlank: true
				    }, {
				        fieldLabel: '上级资源id',
				        id:'pResId',
				        name: 'resource.resource.resourceId',
				        value:responseData.parentResourceId,
				        hidden:true,
				        width: 100,
				        //beforeLabelTextTpl: required,
				        allowBlank: false
				    }, {
				        fieldLabel: '上级',
				        id:'pResName',
				        name: 'resource.resource.resourceName',
				        value:responseData.parentResourceName,
				        disabled:true,
				        //beforeLabelTextTpl: required,
				        width: 100,
				        hidden:false,
				        allowBlank: false
				    },{
				        fieldLabel: 'ID',
				        name: 'resource.resourceId',
				        //beforeLabelTextTpl: required,
				        value:responseData.resourceId,
				        width: 100,
				        hidden:true,
				        allowBlank: false
				    },{
				     	xtype: 'combobox',
				        fieldLabel: '类型',
				        id:'modifyResourceTypeId',
				        name: 'resource.resourceType.pkDictionaryId',
				        store: resourceTypeStore,
				        valueField: 'dictionaryId',
				        displayField: 'dictionaryName',
				        editable:false,
				        queryMode: 'remote',
				        value:responseData.resourceTypeId,
				        width: 100,
				        //beforeLabelTextTpl: required,
				        allowBlank: false
				    },{
				        fieldLabel: '名称',
				        name: 'resource.resourceName',
				        //beforeLabelTextTpl: required,
				        value:responseData.resourceName,
				        width: 100,
				        allowBlank: false,
				        validator: function(value){
								var returnObj = null;
								$.ajax({
									url : '${ctx}/resource/validateResourceProperties.action',
									data:{key:'0',value:value,validatorType:'update',resourceId:responseData.resourceId},
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
				    },{
				        fieldLabel: '编码',
				        name: 'resource.code',
				        //beforeLabelTextTpl: required,
				        value:responseData.code,
				        width: 100,
				        allowBlank: false,
				        validator: function(value){
								var returnObj = null;
								$.ajax({
									url : '${ctx}/resource/validateResourceProperties.action',
									data:{key:'1',value:value,validatorType:'update',resourceId:responseData.resourceId},
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
				    },{
				        fieldLabel: 'URL',
				        name: 'resource.urlpath',
				        value:responseData.urlpath,
				        width: 100,
				        allowBlank: false,
				        validator: function(value){
								var returnObj = null;
								$.ajax({
									url : '${ctx}/resource/validateResourceProperties.action',
									data:{key:'2',value:value,validatorType:'update',resourceId:responseData.resourceId},
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
				    },{
				        fieldLabel: '描述',
				        name: 'resource.remarks',
				        value:responseData.remarks,
				        width: 100
				    },{
				        fieldLabel: '排序',
				        name: 'resource.disOrder',
				        value:responseData.disOrder,
				        width: 100,
				        regex : /^[0-9]+$/,regexText : '排序号码只能为数字。'
				    }
				    ]
				 });
				 resourceModifyWin=Ext.create("Ext.window.Window",{
					title: '修改资源',
					closable : true,
					resizable: false,
					buttonAlign:"center",
				  	height: 280,
				    width: 250,
				    modal : true,
				    layout: 'fit',
				    closeAction:'hide',
				    items: [resourceModifyForm],
				    buttons:[{
						    text: SystemConstant.saveBtnText,
					    	handler: function(){
					    		if(resourceModifyForm.form.isValid()){
					    			resourceModifyForm.form.submit({
					    				 success:function(form,action){
					    				 	var  idPath = selectNode.getPath("id");  
						                    treePanel.getStore().load({  
						                        node: treePanel.getRootNode(),  
						                        callback: function () {  
						                            treePanel.selectPath(idPath, 'id'); 
						                            treePanel.fireEvent("itemclick",treePanel.getSelectionModel().getSelection(),selectNode); 
						                        }  
						                    }); 
		        	                		var data = action.result; 
		        	                		if(data.success){
												new Ext.ux.TipsWindow({
													title:SystemConstant.alertTitle,
													html: data.msg
												}).show();
											}else{
												Ext.MessageBox.show({
													title: SystemConstant.alertTitle,
													msg: data.msg,
													buttons: Ext.MessageBox.OK,
													icon: Ext.MessageBox.ERROR
												});
											}
		        	                		resourceModifyWin.close();
	                      	    		}
					    			});
				 				}
			                }
						},{
						    text: '关闭',
			                handler: function(){
			                	resourceModifyWin.close();
			                }
					}]
				 });
				 resourceModifyWin.show();
			 }
			 
			//新增资源信息窗口哦
			createResourceAddInfo = function(){
				resourceAddWin.show();
			}
		//修改资源信息
			createUpdateResourceInfo = function(){
				resourceModifyWin.show();
			}
			 Ext.create("Ext.container.Viewport", {
				layout: "border",
				border:true,
				renderTo: Ext.getBody(),
				items: [treePanel,
					{
						region:'center',
						layout:'fit',
						border:false,
						items: [resourceGrid]
					}
				]
			});
		});
	</script>
	<input id="tempId" type="hidden" />
	<input id="tempText" type="hidden"/>
</body>
</html>