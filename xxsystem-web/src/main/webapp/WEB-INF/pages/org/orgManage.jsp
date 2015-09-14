<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/doc_type.jsp"%>
<html>
<head>
<%@include file="../common/meta.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/css.jsp"%>
<%@include file="../common/ext.jsp"%>
<title>组织管理</title>
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
</script>
	<script type="text/javascript">
		var currentOrgId = 0;
		var selectNode=null;
		var currentIndex = 0;
		var orgNameForAdd ='新组织';
		Ext.onReady(function() {
			Ext.QuickTips.init();
			//自动引入其他需要的js
			Ext.require(["Ext.container.*",
			             "Ext.grid.*", 
			             "Ext.toolbar.Paging", 
			             "Ext.form.*",
						 "Ext.data.*" ]);
			//建立Model模型对象
			Ext.define("orgModel",{
				extend:"Ext.data.Model",
				fields:[
						{name: "orgId",type:"int"}, 
						{name: "orgName"},
						{name: "orgType"},
						{name: "orgTypeId"},
						{name: "orgCode"}
					]
			});
			Ext.define('orgTypeModel', {
     			extend: 'Ext.data.Model',
     			fields: [
         			{name: 'dictionaryId'},
         			{name: 'dictionaryName'},
         			{name: 'dictionaryValue'}
     			]
 			});
			orgTypeStore = Ext.create('Ext.data.Store', {
     			model: 'orgTypeModel',
     			proxy: {
      	   		type: 'ajax',
      	   		url: '${ctx}/user/getSelectionsByType.action',
      	   		extraParams:{dictTypeCode:"ORGTYPE"},
       	   		reader: {
       	      		type: 'json',
       	      		root: 'list'
      	   		}
     			},
     			autoLoad: true
 			});
			//建立数据Store
			var orgStore=Ext.create("Ext.data.Store", {
		        pageSize: SystemConstant.commonSize,
		        model:"orgModel",
		        remoteSort:true,
		        actionMethods: {
	                read: 'POST'
	            },
				proxy: {
		            type:"ajax",
				    url: "${ctx}/org/getOrgById.action",
				    reader: {
				    },
		        simpleSortMode :true
		        },
		        sorters:[{
		            property:"orgId",
		            direction:"ASC"
		        }]
			});
			
			var cm=[
					{xtype: "rownumberer",width:60,text:'序号',align:"center",menuDisabled: true},
		            {header: "orgId",width: 70,dataIndex: "orgId",hidden: true,menuDisabled: true},
		            //{header: "上级",width: 200,dataIndex: "parentOrgName",menuDisabled: true},
		            {header: "名称",width: 200,dataIndex: "orgName",menuDisabled: true,
		            	editor: {
                    		allowBlank: false
                		}
		            },
		            {header: "编码",width: 200,dataIndex: "orgCode",menuDisabled: true,
		            	editor: {
                    		allowBlank: false
                		},
                		renderer: function(value, cellmeta, record, rowIndex, columnIndex, store){
                			var orgCode = record.get("orgCode");
                			if(orgCode != null && orgCode != "\"null\"" && typeof(orgCode) != 'undefined'){
                				return orgCode;
                			}else{
                				record.set("orgCode","");
                				return "";
                			}
                		}
		            },
		            {header: "orgTypeId",width: 70,dataIndex: "orgTypeId",hidden: true,id:"orgTypeId",menuDisabled: true},
		            {header: "类型",width: 200,dataIndex: "orgType",menuDisabled: true,id:"orgType",
		            	editor: new Ext.form.ComboBox({ 
		            			store:orgTypeStore,
		            			valueField: 'dictionaryId',
		            			displayField: 'dictionaryName',
		            			typeAhead:false,
		            			editable:false,
		            			queryMode: 'remote'
		            	}),
		            	listeners:{
             				'select':function( combo, records, eOpts ){
             					//Ext.getCmp('orgType').setValue(Number(records[0].data.dictionaryId));
             				}
             			}
		            	/*
                		renderer: function(value, cellmeta, record, rowIndex, columnIndex, store){
                			var orgType = record.get("orgType");
                			if(orgType != null && orgType != "\"null\"" && typeof(orgType) != 'undefined'){
                				return orgType;
                			}else{
                				record.set("orgType","");
                				return "";
                			}
                		}*/
		            }
		           ];
			
			//grid组件
			var orgGrid =  Ext.create("Ext.grid.Panel",{
				title:'组织管理',
				border:false,
				columnLines: true,
				width: "100%",
				region: "center",
				height: document.body.clientHeight,
				id: "orgGrid",
				plugins: [Ext.create('Ext.grid.plugin.CellEditing', {clicksToEdit: 1})],
				bbar:  Ext.create("Ext.PagingToolbar", {
					store: orgStore,
					displayInfo: true,
					displayMsg: SystemConstant.displayMsg,
					emptyMsg: SystemConstant.emptyMsg
				}),
		     	forceFit : true,
				store: orgStore,
				autoScroll: true,
				stripeRows: true,
				selModel: {
                selType: 'cellmodel'
           		},
				listeners:{
						'render': function(g) {   
											for(var i = 0;i < userPermissionArr.length;i++){
													if("organization_addOrg_btn" == userPermissionArr[i].name){
														Ext.getCmp('addOrg').setVisible(true);
													}
													if("organization_deleteOrg_btn" == userPermissionArr[i].name){
														Ext.getCmp('deleteOrg').setVisible(true);
													}
													if("organization_synch_btn" == userPermissionArr[i].name){
														Ext.getCmp('synchInfo').setVisible(true);
													}
									 		}  
                 			g.on("itemmouseenter", function(view,record,mode,index,e) {
                 	 			var view = g.getView(); 
                     			orgGrid.tip = Ext.create('Ext.tip.ToolTip', {  
                        		target: view.el,
                        		delegate: view.getCellSelector(),
                        		trackMouse: true,
                        		renderTo: Ext.getBody(),
                        		listeners: {   
                            		beforeshow: function updateTipBody(tip) {
                						tip.update(tip.triggerElement.innerHTML);
                            		}  
                        		}  
                    		});  
  
                 		});    
             		}  
				},
				columns:cm
			});
			orgGrid.on('edit', function(editor, e) {
				var field = e.field;
    			var newValue = e.value;
    			var param = {};
    			if(newValue == null || newValue == "" || currentOrgId == 0){
    				return;
    			}
    			if(field == "orgName"){
    				param = {orgId: currentOrgId,orgName:newValue}
    			}else if(field == "orgCode"){
    				param = {orgId: currentOrgId,orgCode:newValue}
    			}else if(field == "orgType"){
    				param = {orgId: currentOrgId,orgType:e.value}
    			}
    			
    			Ext.Ajax.request({
						url : '${ctx}/org/updateOrg.action',
						params : param,
						success : function(res, options) {
							var data = Ext.decode(res.responseText);
							orgGrid.getStore().load({params: {orgId:currentOrgId}});
							treePanel.getStore().load();
							new Ext.ux.TipsWindow(
								{
									title: SystemConstant.alertTitle,
									autoHide: 3,
									html:data.msg
								}
							).show();
						}
				});
			});
			
			//右键菜单
			var materialMenu =  Ext.create("Ext.menu.Menu", {
				floating:true,
				items : [
						{
							text : "查看详情",
							id : 'viewOrgInfo',
							handler : function() {
								var orgId = selectNode.raw.nodeId;
								var store = orgGrid.getStore();
								var proxy = store.getProxy();
								proxy.setExtraParam("orgId",orgId);
		        	            store.load(proxy);
							}
						},
						{
							text : "添加",
							id : 'addOrg',
							hidden:true,
							handler : function() {
								var orgType = 2;
								if(selectNode.getDepth() == 0){
									pOrgId = 0;
									orgType = 1;
								}else{
									pOrgId = selectNode.raw.nodeId;
								}
								Ext.Ajax.request({
											url : '${ctx}/org/addOrg.action',
											params : {pOrgId:pOrgId ,orgName:orgNameForAdd,orgType:orgType},
											success : function(res, options) {
												var data = Ext.JSON.decode(res.responseText);
												new Ext.ux.TipsWindow(
														{
																title: SystemConstant.alertTitle,
																autoHide: 3,
																html:data.msg
														}
												).show();
												if(data.success == 'true'){
							          				selectNode.appendChild({nodeId:data.orgId,text:orgNameForAdd});
													orgGrid.getStore().load({params: {orgId: data.orgId}});
												}
											treePanel.getStore().load();
											}
											
								});
							}
						},
						{
							text : "删除",
							id : 'deleteOrg',
							hidden:true,
							handler : function() {
								Ext.Msg.confirm(SystemConstant.alertTitle,"确认删除所选组织及其子组织吗？",function(btn) {
									if (btn == 'yes') {
										Ext.Ajax.request({
											url : '${ctx}/org/delOrg.action',
											params : {orgId: selectNode.raw.nodeId},
											success : function(res, options) {
												 if(res.status == '200' && res.responseText.charAt(1) == 'Y'){
												 	new Ext.ux.TipsWindow(
													{
														title: SystemConstant.alertTitle,
														autoHide: 3,
														html: res.responseText.substring(2,res.responseText.length - 1)
													}).show();
													
												 	var parentNode=selectNode.parentNode;
												 	selectNode.remove();
												 	orgGrid.getStore().load({params: {orgId: parentNode.raw.nodeId}});
												 	parentNode.expand();
												 }else{
												 	Ext.Msg.alert(SystemConstant.alertTitle, res.responseText.substring(2,res.responseText.length - 1));
												 	return false;	
												 }
											}
										});
									}
								});
							}
						} ]
				});

			

			 var treeStore = Ext.create("Ext.data.TreeStore", {
			        proxy: {
			            type: "ajax",
			            actionMethods: {
			                read: 'POST'
			            },
			            url: "${ctx}/org/getOrgTreeList.action"
			        },
			        root: {
			        	text:"组织树形展示",
			        	nodeId:"0"
			        },
			        listeners: {
                        beforeload: function (ds, opration, opt) {
                            opration.params.parentId = opration.node.raw.nodeId;
                        }
                    }
			    });
			 
			 
			 var treePanel=Ext.create("Ext.tree.Panel", {
			 		title:'组织信息',
			       	store: treeStore,
			     	id:"typeTree",
			        height: document.body.clientHeight,
			        width: 200,
			        useArrows: true,
			        rootVisible : false,
					region: "west",
					border: false,
					collapsible: true,
            		split: true,
					collapseMode:"mini",
					draggable:false,
/* 			        viewConfig: {
	                	plugins: {
	                    	ptype: 'treeviewdragdrop',
	                   		containerScroll: true
	                	}
	            	}, */
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
							text : "同步",
							id : 'synchInfo',
							iconCls: "refresh-button",
							handler : function() {
								Ext.Msg.confirm(SystemConstant.alertTitle,"同步将会删除现有数据，确定要同步吗？",function(btn) {
					  				if (btn == 'yes') {
					  						Ext.MessageBox.wait("", "同步组织数据", 
														{
			                						text:"请稍后..."
			            					}
			            			);
			            			return false;
												Ext.Ajax.request({
												timeout:60000000,
												url : '${ctx}/org/synchOrgData.action',
												success : function(res, options) {
													var data = Ext.decode(res.responseText);
													treePanel.getStore().load();
													Ext.MessageBox.hide();
													if(data.success == 'true'){
															new Ext.ux.TipsWindow(
															{
																title: SystemConstant.alertTitle,
																autoHide: 3,
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
					}]
			        }],
			        listeners:{
			       	 	"itemcontextmenu":function(tree, record, item, index, e, eOpts){
			        		selectNode=record;
			        		e.preventDefault();
			        		if(record.raw.nodeId == "0"){
			        			Ext.getCmp("deleteOrg").setVisible(false);
			        		}
			        		materialMenu.showAt(e.getPoint());
			        	},
			        	'itemmousedown': function( treePanel, record, item, index, e, eOpts ){
			        		currentIndex = record.parentNode.indexOf(record)
			        		srcOrgId = record.raw.nodeId;
			        	},
	            		'itemmove':function( treePanel, oldParent, newParent, index, eOpts ){
	            			var targetOrgId = 0;
	            			position = "";
							if(index == 0){
									if(newParent.hasChildNodes()){
										var i = 0;
										newParent.eachChild(function(){
											i++;
										});
									}
	        	    				if(i = 1){
		        	    				targetOrgId = newParent.raw.nodeId;
		        	    				position = "INNER";
	        	    				}else{
	        	    					position = "PREV";
	        	    					targetOrgId = newParent.getChildAt(index).raw.nodeId;
	        	    				}
	    	        		}else if(index > 0){
		            				position = "NEXT";
		            				targetOrgId = newParent.getChildAt(index - 1).raw.nodeId;
		            		}
		            		$.ajax({
					   			type: "POST",
					   			dateType:'json',
					   			url: "${ctx}/org/updateOrgDisOrder.action",
					   			data: {
									   'srcOrgId':srcOrgId,
									   'targetOrgId':targetOrgId,
									   'position':position
					   			}
								}); 
	            		}
			        
			        }
			    });
			 treePanel.on("itemclick",function(view,record,item,index,e,opts){  
			     //获取当前点击的节点  
			      var treeNode=record.raw; 
			      var text=treeNode.text;
			      var id = treeNode.nodeId;
			      currentOrgId = id;
			      orgStore.on('beforeload',function(store,options){
			    	  var new_params = {"orgId":id};
			    	  Ext.apply(store.proxy.extraParams,new_params);
			      });
			      orgStore.load({params:{start:0,limit:SystemConstant.commonSize}});
		 	});
			 
			 Ext.create("Ext.container.Viewport", {
				layout: "border",
				renderTo: Ext.getBody(),
				items: [treePanel,orgGrid]
			});
		});
	</script>
</body>
</html>