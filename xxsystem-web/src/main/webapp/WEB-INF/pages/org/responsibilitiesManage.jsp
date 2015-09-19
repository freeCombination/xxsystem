<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/doc_type.jsp"%>
<html>
<head>
<%@include file="../common/meta.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/css.jsp"%>
<%@include file="../common/ext.jsp"%>
<title>岗位管理</title>
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
		Ext.define("Dict",{
			extend:"Ext.data.Model",
			fields:[
				{name: "pkDictionaryId",mapping:"pkDictionaryId"}, 
				{name: "dictionaryName",mapping:"dictionaryName"}, 
				{name: "typeName",mapping:"dictionaryTypeName"}, 
				{name: "typeId",mapping:"dictionaryTypeId"},
				{name: "levelOrder",mapping:"levelOrder"},
				{name: "dictionaryValue",mapping:"dictionaryValue"},
				{name: "dictionaryCode",mapping:"dictionaryCode"}
			]
		});
		
		//建立数据Store
		var dictStore=Ext.create("Ext.data.Store", {
	        pageSize: SystemConstant.commonSize,
	        model:"Dict",
	        remoteSort:true,
			proxy: {
	            type:"ajax",
	            actionMethods: {
                	read: 'POST'
           		},
			    url: "${ctx}/dict/getDicts.action",
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
		
		//行选择模型
		var sm=Ext.create("Ext.selection.CheckboxModel",{
			injectCheckbox:1,
	    	listeners: {
		      selectionchange: function(){
		        	var c = dictGrid.getSelectionModel().getSelection();
						 	if(c.length > 0){
								Ext.getCmp('delDicBtn').setDisabled(false);
						 	}else{
							 	Ext.getCmp('delDicBtn').setDisabled(true);
						 	}
						 	if(c.length == 1){
							 	Ext.getCmp('updateDicBtn').setDisabled(false);
						 	}else{
							 	Ext.getCmp('updateDicBtn').setDisabled(true);
						 	}
		      }
			}
	    });
		
		var cm=[
				{header:"序号",xtype: "rownumberer",width:60,align:"center",menuDisabled: true,sortable :false},
	            {header: "ID",width: 70,dataIndex: "pkDictionaryId",hidden: true,menuDisabled: true,sortable :false},
	            {header: "岗位编号",width: 200,dataIndex: "dictionaryName",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}},
	            {header: "名称",width: 200,dataIndex: "typeName",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}},
	            {header: "所属部门",width: 200,dataIndex: "dictionaryValue",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}},
	            {header: "岗位级别",width: 200,dataIndex: "dictionaryCode",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}},
				{header: "状态",width: 200,dataIndex: "enable",
		            renderer: function(value, cellmeta, record, rowIndex, columnIndex, store){debugger;
		                //cellmeta.tdAttr = 'data-qtip="' + orgTypeArr[i].name + '"';
		                var orgId = record.get('orgId');
		                if(value == 1){
		                    return '<img title="点击锁定部门" src="'+basePath+'/images/icons/unlock.gif" style="cursor: pointer" onclick="lockupOrg('+orgId+','+value+')"/>';
		                }else{
		                    return '<img title="点击解锁部门" src="'+basePath+'/images/icons/lock.gif" style="cursor: pointer" onclick="lockupOrg('+orgId+','+value+')"/>';
		                }
		            }
		        }
	         ];
		
		//grid组件
		var dictGrid =  Ext.create("Ext.grid.Panel",{
			title:'岗位管理',
			border:false,
			columnLines: true,
			layout:"fit",
			region: "center",
			width: "100%",
			height: document.body.clientHeight,
			id: "dictGrid",
			bbar:  Ext.create("Ext.PagingToolbar", {
				store: dictStore,
				displayInfo: true,
				displayMsg: SystemConstant.displayMsg,
				emptyMsg: SystemConstant.emptyMsg
			}),
			columns:cm,
	        selModel:sm,
	     	forceFit : true,
			store: dictStore,
			autoScroll: true,
			stripeRows: true,
			tbar: ['岗位编号',
			{
				id: 'respNo',
				width: 150,   
					labelWidth: 70,
				xtype: 'textfield'
			},'&nbsp;岗位名称',
			{
				id: 'respName',
                width: 150,   
                    labelWidth: 70,
                xtype: 'textfield'
			}
			,'&nbsp;所属部门',
            {
                id: 'respOrg',
                width: 150,   
                    labelWidth: 70,
                xtype: 'textfield'
            },'&nbsp;',
			{
				id:'searchDicBtn',
				xtype:'button',
				disabled:false,
				text:'查询',
				iconCls:'search-button',
				handler:function(){
					var proxy = dictStore.getProxy();
					proxy.setExtraParam("dictName",Ext.getCmp("dictName").getValue());
					var dictType = Ext.getCmp("typeCombox").getValue();
					if(dictType=='全部') {
						dictType = '';
					}
					
					proxy.setExtraParam("dictType",dictType);
					dictStore.loadPage(1);
				}
			},'->',
			{
				id:'addDicBtn',
				xtype:'button',
				disabled:false,
				text:'添加',
				hidden:true,
				iconCls:'add-button',
				handler:function(){
					createAddDictInfo();
				}
			},
			{
				id:'updateDicBtn',
				xtype:'button',
				text:'修改',
				hidden:true,
				disabled:true,
				iconCls:'edit-button',
				handler:function(){
					var rowId = dictGrid.getSelectionModel().getSelection()[0].get("id");
					Ext.Ajax.request({
						url : '${ctx}/dict/checkDictIsExist.action',
						params : {ids: rowId},
						success : function(res, options) {
							var data = Ext.decode(res.responseText);
							if(data.success){
								createUpdateDictInfo();
							}else{
								dictStore.loadPage(1);
							 	Ext.MessageBox.show({
									title: SystemConstant.alertTitle,
									msg: data.msg,
									buttons: Ext.MessageBox.OK,
									icon: Ext.MessageBox.INFO
								});
							 	return false;
							}
						}
					});
					
				}
			},
			{
				id:'delDicBtn',
				xtype:'button',
				text:'删除',
				hidden:true,
				disabled:true,
				iconCls:'delete-button',
				handler:function(){
					var ck = Ext.getCmp('dictGrid').getSelectionModel().getSelection();
					var itemsArray = new Array();
					for(var i=0;i<ck.length;i++){
						itemsArray.push(ck[i].data.pkDictionaryId);
					}
					var idss = itemsArray.toString();
					Ext.Ajax.request({
						url : '${ctx}/dict/checkDictIsExist.action',
						params : {ids: idss},
						success : function(res, options) {
							 var data = Ext.decode(res.responseText);
							 if(data.success){
							 	Ext.Msg.confirm(SystemConstant.alertTitle,"确认删除所选字典数据吗？",function(btn) {
									if (btn == 'yes') {
										Ext.Ajax.request({
											url : '${ctx}/dict/delDictInfo.action',
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
												dictStore.loadPage(1);
												typeNameStore.loadPage(1);
											}
										});
									}
								});
							 }else{
							 	dictStore.loadPage(1);
							 	Ext.MessageBox.show({
									title: SystemConstant.alertTitle,
									msg: data.msg,
									buttons: Ext.MessageBox.OK,
									icon: Ext.MessageBox.INFO
								});
							 	return false;
							 }
						}
					});
				}
			}] , 
			listeners:{
					'render': function() {    
						for(var i = 0;i < userPermissionArr.length;i++){
							if("dict_add_btn" == userPermissionArr[i].name){
								Ext.getCmp('addDicBtn').setVisible(true);
							}
							if("dict_update_btn" == userPermissionArr[i].name){
								Ext.getCmp('updateDicBtn').setVisible(true);
							}
							if("dict_delete_btn" == userPermissionArr[i].name){
								Ext.getCmp('delDicBtn').setVisible(true);
							}
						}
            		}
			}
		});
		dictStore.load({params:{start:0,limit:SystemConstant.commonSize}});
		
		Ext.create("Ext.container.Viewport", {
		    layout: "border",
			renderTo: Ext.getBody(),
			items: [dictGrid]
		});
	});
	</script>
</body>
</html>