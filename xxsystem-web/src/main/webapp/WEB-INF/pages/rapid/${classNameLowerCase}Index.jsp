<#assign className = table.className>
<#assign classNameLower = className?lower_case>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/doc_type.jsp"%>
<html>
<head>
<%@include file="../common/meta.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/css.jsp"%>
<%@include file="../common/ext.jsp"%>
<title>${table.tableAlias}管理</title>
</head>
<body>

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
		Ext.define("${className}",{
			extend:"Ext.data.Model",
			fields:[
				{name: "${classNameLower}Id",mapping:"${classNameLower}Id"}, 
				{name: "${classNameLower}Name",mapping:"${classNameLower}Name"}, 
				{name: "${classNameLower}Order",mapping:"${classNameLower}Order"},
				{name: "${classNameLower}Value",mapping:"${classNameLower}Value"},
				{name: "${classNameLower}Status",mapping:"${classNameLower}Status"}
			]
		});
		
		//建立数据Store
		var ${classNameLower}Store=Ext.create("Ext.data.Store", {
	        pageSize: SystemConstant.commonSize,
	        model:"${className}",
	        remoteSort:true,
			proxy: {
	            type:"ajax",
	            actionMethods: {
                	read: 'POST'
           		},
			    url: "<#noparse>${ctx}</#noparse>/${classNameLower}/get${className}ListVo.action",
			    reader: {
				     totalProperty: "totalSize",
				     root: "list"
			    },
	        simpleSortMode :true
	        }
		});
		
		//行选择模型
		var sm=Ext.create("Ext.selection.CheckboxModel",{
			injectCheckbox:1,
	    	listeners: {
		      selectionchange: function(){
		        	var c = ${classNameLower}Grid.getSelectionModel().getSelection();
						 	if(c.length > 0){
								Ext.getCmp('del${className}Btn').setDisabled(false);
						 	}else{
							 	Ext.getCmp('del${className}Btn').setDisabled(true);
						 	}
						 	if(c.length == 1){
							 	Ext.getCmp('update${className}Btn').setDisabled(false);
						 	}else{
							 	Ext.getCmp('update${className}Btn').setDisabled(true);
						 	}
		      }
			}
	    });
		
		var cm=[
				{header:"序号",xtype: "rownumberer",width:60,align:"center",menuDisabled: true,sortable :false},
	            {header: "ID",width: 70,dataIndex: "id",menuDisabled: true,sortable :false},
	            {header: "名称",width: 200,dataIndex: "${classNameLower}Name",menuDisabled: true,sortable :false},
	            {header: "值",width: 200,dataIndex: "${classNameLower}Value",menuDisabled: true,sortable :false},
	            {header: "排序",width: 200,dataIndex: "${classNameLower}Order",menuDisabled: true,sortable :false}
	         ];
		
		
		//grid组件
		var ${classNameLower}Grid =  Ext.create("Ext.grid.Panel",{
			title:'管理',
			border:false,
			columnLines: true,
			layout:"fit",
			region: "center",
			width: "100%",
			height: document.body.clientHeight,
			id: "${classNameLower}Grid",
			bbar:  Ext.create("Ext.PagingToolbar", {
				store: ${classNameLower}Store,
				displayInfo: true,
				displayMsg: SystemConstant.displayMsg,
				emptyMsg: SystemConstant.emptyMsg
			}),
			columns:cm,
	        selModel:sm,
	     	forceFit : true,
			store: ${classNameLower}Store,
			autoScroll: true,
			stripeRows: true,
			tbar: ['名称：',
			{
				id: '${classNameLower}Name',
				width: 150,   
				labelWidth: 70,   
				xtype: 'textfield'
			},'&nbsp;&nbsp;',
			{
				id:'search${className}Btn',
				xtype:'button',
				disabled:false,
				text:'查询',
				iconCls:'search-button',
				handler:function(){
					var proxy = ${classNameLower}Store.getProxy();
					proxy.setExtraParam("${classNameLower}Name",Ext.getCmp("${classNameLower}Name").getValue());
					${classNameLower}Store.loadPage(1);
				}
			},'->',
			{
				id:'add${className}Btn',
				xtype:'button',
				disabled:false,
				text:'添加',
				iconCls:'add-button',
				handler:function(){
					createAdd${className}();
				}
			},
			{
				id:'update${className}Btn',
				xtype:'button',
				text:'修改',
				disabled:true,
				iconCls:'edit-button',
				handler:function(){
					createUpdate${className}();
				}
			},
			{
				id:'del${className}Btn',
				xtype:'button',
				text:'删除',
				disabled:true,
				iconCls:'delete-button',
				handler:function(){
					var ck = Ext.getCmp('${classNameLower}Grid').getSelectionModel().getSelection();
					var itemsArray = new Array();
					for(var i=0;i<ck.length;i++){
						itemsArray.push(ck[i].data.${classNameLower}Id);
					}
					var idss = itemsArray.toString();
					Ext.Msg.confirm(SystemConstant.alertTitle,"确认删除所选数据吗？",function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url : '<#noparse>${ctx}</#noparse>/${classNameLower}/del${className}.action',
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
											icon: Ext.MessageBox.ERROR
										});
									}
									${classNameLower}Store.loadPage(1);
								}
							});
						}
					});
				}
			}] 
		
		});
		${classNameLower}Store.load({params:{start:0,limit:SystemConstant.commonSize}});
		
		Ext.create("Ext.container.Viewport", {
		    layout: "border",
			renderTo: Ext.getBody(),
			items: [${classNameLower}Grid]
		});
		
		//添加信息窗口哦
		var createAdd${className} = function(){
			var ${classNameLower}Form = Ext.create("Ext.form.Panel", {
				layout: 'form',
				bodyStyle :'padding:15px 10px 0 0',
				border: false,
				labelAlign: 'right',
				fieldDefaults: {
		            labelWidth: 100,
		        	labelAlign: 'right'
		        },
		        defaults: {
			        anchor: '60%'
			    },
			    defaultType: 'textfield',
			    items: [{
			    	id: 'dicName',
			        fieldLabel: '名称',
			        name: '${classNameLower}.${classNameLower}Name',
			        //beforeLabelTextTpl: required,
			        regex : new RegExp('^([\u4E00-\u9FFF]*-*\\w*)*$'),
					regexText : '不能包含特殊字符',
					maxLength:25,
			        width: 100,
			        allowBlank: false
			    },{
			        fieldLabel: '值',
			        name: '${classNameLower}.${classNameLower}Value',
			        //beforeLabelTextTpl: required,
			        regex : new RegExp('^([\u4E00-\u9FFF]*-*\\w*)*$'),
					regexText : '不能包含特殊字符',
			        maxLength:50,
			        minValue:1,
			        width: 100,
			        allowBlank: false
			    },{
			        fieldLabel: '排序',
			        name: '${classNameLower}.${classNameLower}Order',
			        //beforeLabelTextTpl: required,
			        maxLength:8,
			        regex : /^[0-9]*[1-9][0-9]*$/,
					regexText : '请输入正整数',
			        width: 100,
			        allowBlank: false
			    }]
			});
			
			var ${classNameLower}Win=Ext.create("Ext.window.Window",{
				title: '添加',
				resizable: false,
				buttonAlign:"center",
			  	height: 250,
			    width: 350,
			    modal:true,
			    layout: 'fit',
			    modal : true,
			    items: [${classNameLower}Form],
			    buttons:[{
					    text: SystemConstant.saveBtnText,
				    	handler: function(){
				    		if(${classNameLower}Form.form.isValid()){
				    			
				    				Ext.MessageBox.wait("", "添加数据", 
											{
												text:"请稍后..."
											}
										);
				    			${classNameLower}Form.form.submit({
				    				url:"<#noparse>${ctx}</#noparse>/${classNameLower}/add${className}.action",
			    				    success : function(form, action) {
			    					    new Ext.ux.TipsWindow(
											{
												title: SystemConstant.alertTitle,
												autoHide: 3,
												html:action.result.msg
											}).show();
			    					   
			    					  ${classNameLower}Store.loadPage(1); 
			    					  ${classNameLower}Win.close();
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
			    					   ${classNameLower}Store.loadPage(1);
			    					   ${classNameLower}Win.close();
			    					   Ext.MessageBox.hide();
			    				   }
				    			});
			 				}
		                }
					},{
					    text: '关闭',
		                handler: function(){
		                	${classNameLower}Win.close();
		                }
				}]
			 }).show();
			
		};
		
		//修改信息
		var createUpdate${className} = function(){
			var row = ${classNameLower}Grid.getSelectionModel().getSelection();
			var dname = row[0].get('${classNameLower}Name');
			var ${classNameLower}Form = Ext.create("Ext.form.Panel", {
				layout: 'form',
				bodyStyle :'padding:15px 10px 0 0',
				border: false,
				labelAlign: 'right',
				fieldDefaults: {
		            labelWidth: 100,
		        	labelAlign: 'right'
		        },
		        defaults: {
			        anchor: '80%'
			    },
			    defaultType: 'textfield',
			    items: [
			    {
			        name: '${classNameLower}.${classNameLower}Id',
			        value:row[0].get('${classNameLower}Id'),
			        hidden:true,
			        width: 100,
			        allowBlank: true
			    }, 
			    {
			        fieldLabel: '名称',
			        name: '${classNameLower}.${classNameLower}Name',
			        value:row[0].get('${classNameLower}Name'),
			        regex : new RegExp('^([\u4E00-\u9FFF]*-*\\w*)*$'),
					regexText : '不能包含特殊字符',
			        //beforeLabelTextTpl: required,
			        width: 100,
			        maxLength:25,
			        allowBlank: false
			    },
			    {
			        fieldLabel: '值',
			        name: '${classNameLower}.${classNameLower}Value',
			        value:row[0].get('${classNameLower}Value'),
			        regex :  new RegExp('^([\u4E00-\u9FFF]*-*\\w*)*$'),
					regexText : '不能包含特殊字符！',
			        //beforeLabelTextTpl: required,
			        width: 100,
			        maxLength:50,
			        minValue:1,
			        allowBlank: false
			    },
			    {
			        fieldLabel: '排序',
			        name: '${classNameLower}.${classNameLower}Order',
			        value:row[0].get('${classNameLower}Order'),
			        regex : /^[0-9]*[1-9][0-9]*$/,
					regexText : '请输入正整数',
			        //beforeLabelTextTpl: required,
			        width: 100,
			        maxLength:8,
			        allowBlank: false
			    }]
			 });
			
			var ${classNameLower}Win=Ext.create("Ext.window.Window",{
				title: '修改',
				resizable: false,
				buttonAlign:"center",
			  	height: 250,
			    width: 350,
			    modal:true,
			    layout: 'fit',
			    items: [${classNameLower}Form],
			    buttons:[
			    	{
					    text: SystemConstant.saveBtnText,
				    	handler: function(){
				    		if(${classNameLower}Form.form.isValid()){
				    			Ext.MessageBox.wait("", "修改数据", 
										{
											text:"请稍后..."
										}
									);
									${classNameLower}Form.form.submit({
										url:"<#noparse>${ctx}</#noparse>/${classNameLower}/update${className}.action",
										success : function(form, action) {  
				    					   Ext.MessageBox.hide();
				    					   //清除选中行;
				    					   ${classNameLower}Grid.getSelectionModel().deselectAll();
				    					   //Ext.getCmp("${classNameLower}Grid").getSelectionModel().clearSelections();
				    					   new Ext.ux.TipsWindow(
												{
													title: SystemConstant.alertTitle,
													autoHide: 3,
													html:action.result.msg
												}
											).show();
				    					   ${classNameLower}Win.close();
				    					   ${classNameLower}Store.loadPage(1);
				    				   },
										failure:function(form, action){
										 	Ext.getCmp("${classNameLower}Grid").getSelectionModel().clearSelections();
										 	Ext.MessageBox.show({
												title: SystemConstant.alertTitle,
												msg: action.result.msg,
												buttons: Ext.MessageBox.OK,
												icon: Ext.MessageBox.ERROR
											});
											Ext.MessageBox.hide();
											${classNameLower}Win.close();
											${classNameLower}Store.loadPage(1);
										 	return false;
										}
								});
		 					}
	          			}
					},
					{
				    text: '关闭',
		                handler: function(){
		                	${classNameLower}Win.close();
		                }
					}
				]
			 }).show();
			
		};
		
	});
	</script>
</body>
</html>