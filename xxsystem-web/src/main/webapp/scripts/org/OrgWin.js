var orgForm=Ext.create("Ext.form.Panel", {
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
	    	name : 'org.orgId',
	    	hidden:true
	    },{
	    	name: 'hiddenOrgCode',
	    	hidden:true
	    },{
	    	name : 'org.organization.orgId',
	    	hidden:true
	    },{
	    	fieldLabel: '上级',
	        name: 'org.organization.orgName',
	        disabled:true,
	        width: 100
	    },{
	    	xtype: 'combobox',
	        fieldLabel: '类型',
	        id:'addOrgSelectionId',
	        name: 'org.orgType.pkDictionaryId',
	        store: orgTypeStore,
	        valueField: 'dictionaryId',
	        displayField: 'dictionaryName',
	        editable:false,
	        queryMode: 'remote',
	        //beforeLabelTextTpl: required,
	        width: 100,
	        allowBlank: false
	    },{
	    	fieldLabel: '名称',
	        name: 'org.orgName',
	        vtype:'filterHtml',
	        width: 100,
	        maxLength:20,
	        allowBlank: false
	       /* validator: function(value){
					var returnObj = null;
					$.ajax({
						url : basePath+'/org/validateOrgProperties.action',
						data:{
							key:'0',
							value:value,
							parentOrgId:selectNode.raw.nodeId
						},
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
				}*/

	    },{
	    	 fieldLabel: '编码',
	    	 vtype:'filterHtml',
		     name: 'org.orgCode',
		        width: 100,
		        maxLength:30,
		        allowBlank: false
		        /*validator: function(value){
						var returnObj = null;
						$.ajax({
							url : '${ctx}/org/validateOrgProperties.action',
							data:{key:'1',value:value,parentOrgId:selectNode.raw.nodeId},
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
					}*/
	    }, {
	    	name : 'org.deptHead.userId',
	    	id : 'deptHeadId',
	    	hidden:true
	    }, {
	    	fieldLabel: '部门领导',
	        name: 'org.deptHead.realname',
	        id : 'deptHeadName',
	        //allowBlank: false,
	        width: 100,
	        listeners:{
				'focus':function(){
					var userId = Ext.getCmp('deptHeadId').getValue();
					if(userId && userId != 0){
						createAddUserInfo('deptHeadName','deptHeadId',userId);
					}else{
						createAddUserInfo('deptHeadName','deptHeadId');
					}
				}
			}
	    }, {
	    	name : 'org.branchedLeader.userId',
	    	id : 'branchedLeaderId',
	    	hidden:true
	    }, {
	    	fieldLabel: '分管领导',
	        name: 'org.branchedLeader.realname',
	        id : 'branchedLeaderName',
	        //allowBlank: false,
	        width: 100,
	        listeners:{
				'focus':function(){
					var userId = Ext.getCmp('branchedLeaderId').getValue();
					if(userId && userId != 0){
						createAddUserInfo('branchedLeaderName','branchedLeaderId',userId);
					}else{
						createAddUserInfo('branchedLeaderName','branchedLeaderId');
					}
				}
			}
	    }, {
	    	name : 'org.otherSup.userId',
	    	id : 'otherSupId',
	    	hidden:true
	    }, {
	    	fieldLabel: '其他所领导',
	        name: 'org.otherSup.realname',
	        id : 'otherSupName',
	        //allowBlank: false,
	        width: 100,
	        listeners:{
				'focus':function(){
					var userId = Ext.getCmp('otherSupId').getValue();
					if(userId && userId != 0){
						createAddUserInfo('otherSupName','otherSupId',userId);
					}else{
						createAddUserInfo('otherSupName','otherSupId');
					}
				}
			}
	    }, {
	    	name : 'org.superintendent.userId',
	    	id : 'superintendentId',
	    	hidden:true
	    }, {
	    	fieldLabel: '所长',
	        name: 'org.superintendent.realname',
	        //allowBlank: false,
	        id : 'superintendentName',
	        width: 100,
	        listeners:{
				'focus':function(){
					var userId = Ext.getCmp('superintendentId').getValue();
					if(userId && userId != 0){
						createAddUserInfo('superintendentName','superintendentId',userId);
					}else{
						createAddUserInfo('superintendentName','superintendentId');
					}
				}
			}
	    }, {
	        fieldLabel: '排序',
	        name: 'org.disOrder',
	        width: 100,
	        xtype: 'numberfield',
	        minValue:0,
	        maxValue:999
	    }]
	 });
var orgWin = Ext.create("Ext.window.Window", {
	height : 350,
	width : 380,
	items : [orgForm],
	buttons : [ {
		text : SystemConstant.yesBtnText,
		handler : function() {
			if (orgForm.form.isValid()) {
				orgForm.form.submit({
					success : function(form, action) {
						//resourceStore.loadPage(1);
						
						if(selectNode.parentNode != null){
							var  idPath = selectNode.parentNode.firstChild.getPath("id");
						}else{
							var  idPath = treePanel.getRootNode().getPath("id");
						}
						treePanel.getStore().reload({  
	                        //node: treePanel.getRootNode(),  
	                        node:selectNode,
	                        callback: function () {  
	                            treePanel.selectPath(selectNode.getPath("id"), 'id'); 
	                            //treePanel.fireEvent("itemclick",treePanel.getSelectionModel().getSelection(),selectNode); 
	                        }  
	                    });
						orgStore.loadPage(1);
						
						orgWin.close();
						Ext.Msg.showTip(action.result.msg);
					},
					failure : function(form, action) {
						Ext.Msg.showError(action.result.msg);
					}
				});
			}
		}
	}, {
		text : SystemConstant.closeBtnText,
		handler : function() {
			orgWin.close();
		}
	} ]
});

function createAddUserInfo(nameId, idId, userId){
	//左侧组织树store
	var orgTreeStore1 = Ext.create('Ext.data.TreeStore', {
		  proxy: {
	            type: "ajax",
	            actionMethods: {
	                read: 'POST'
	            },
	            url: basePath +"/org/getOrgTreeList.action"
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
	    
	//左侧组织树panel
	orgTreePanelleft = Ext.create(Ext.tree.Panel,{
		title:'组织信息',
       	store: orgTreeStore1,
     	id:"orgTreePanelleft",
        height: document.body.clientHeight,
        width: 200,
        useArrows: true,
        rootVisible : false,
		region: "west",
		border: false,
		collapsible: true,
		split: true,
		collapseMode:"mini",
        dockedItems: [{
        	xtype: 'toolbar',
			style:"border-top:0px;border-left:0px",
			items:[{
		        iconCls: "icon-expand-all",
		        text:'展开',
				tooltip: "展开所有",
		        handler: function(){ orgTreePanelleft.expandAll(); },
		        scope: this
		    },{
		        iconCls: "icon-collapse-all",
		        text:'折叠',
		        tooltip: "折叠所有",
		        handler: function(){ orgTreePanelleft.collapseAll(); },
		        scope: this
		    }]
        }],
        listeners:{
        	"afterrender":function( treePanel, eOpts ){
        		var path = treePanel.getRootNode().getPath();
        		treePanel.expandPath(path);
        	}
        }

	});
	
	orgTreeStore1.on("load",function(store, node, record){
		if(node != null && node.raw.nodeId == "0" && node.firstChild){
			orgTreePanelleft.getSelectionModel().select(node.firstChild,true);
			orgTreePanelleft.fireEvent("itemclick",orgTreePanelleft.getView(),node.firstChild);
		}
	});

	orgTreePanelleft.on("itemclick",function(view,record,item,index,e,opts){  
	     //获取当前点击的节点  
	      var treeNode=record.raw;  
	      var nodeId = treeNode.nodeId;
	      var proxy = userRoleStore1.getProxy();
	      proxy.setExtraParam("orgId",nodeId);
	      userRoleStore1.loadPage(1);
 	});
	
	orgTreePanelleft.expandAll();
	
	//建立用户Model
	Ext.define("userModel",{
		extend:"Ext.data.Model",
		fields:[
			{name:"userId",mapping:"userId"},
			{name:"orgName"},
			{name:"username"},
			{name:"password"},
			{name:"realname"},
			{name:"gender"},
			{name:"mobileNo"},
			{name:"phoneNo"},
			{name:"shortNo"},
			{name:"idCard"},
			{name:"birthPlace"},
			{name:"erpId"},
			{name:"orgId"},
			{name:"status"},
			{name:"disOrder"},
			{name:"enable"},
			{name: "email"},
			{name: "isDeletable"},
			{name: "birthDay"},
			{name: "flag"}
		 ]
	});
	
	
	//行选择模型
	var userSm1=Ext.create("Ext.selection.CheckboxModel",{
		injectCheckbox:1,
		mode : 'SINGLE',
    	listeners: {
		    selectionchange: function(){
	    	    var rows = Ext.getCmp('userPanel1').getSelectionModel().getSelection();
	        	//var c = userPanel1.getSelectionModel().getSelection();
			 	if(rows.length > 0){
					Ext.getCmp('userOK').setDisabled(false);
			 	}else{
				 	Ext.getCmp('userOK').setDisabled(true);
			 	}
		    }
		}
	});
		  
	var userCm1=[
		{xtype: "rownumberer",text:"序号",width:60,align:"center"},
    	{header: "ID",width: 70,align:'center',dataIndex: "userId",hidden: true,menuDisabled: true,sortable:false},
    	{header: "姓名",width: 200,align:'center',dataIndex: "realname",width:90,menuDisabled: true,sortable:false},
    	{header: "部门",width: 200,align:'center',dataIndex: "orgName",width:90,menuDisabled: true,sortable:false}
    	
    ];

	userRoleStore1 = Ext.create('Ext.data.Store', {
		pageSize: SystemConstant.commonSize,
    	model: 'userModel',
		proxy: {
			type: 'ajax',
			//extraParams:{roleId:roleId,flag: 'QxUser',roleIdOrRoleMemberId:type},
			actionMethods: {
                read: 'POST'
            },
			url: basePath +'/user/getUserList.action',
			reader:{
				type: 'json',
      			root: 'list',
      			totalProperty:"totalSize"
			},
			autoLoad: true
		}
	});

	var userPanel1 = Ext.create(Ext.grid.Panel,{
		title:'用户信息',
   		id: "userPanel1",
		stripeRows: true,
		border:false,
		forceFit:true,
		columnLines: true,
		autoScroll: true,
		store : userRoleStore1,
		selModel:userSm1,
		columns:userCm1,
		tbar:new Ext.Toolbar({
			items:[
			"姓名",
	    	new Ext.form.TextField({
	    		width:'135',
	    		id:'inputUserName'
	    	}),
	    	{
    	    	text :   "查询", 
    	    	iconCls: "search-button", 
    	    	handler:function(){
    	    		var proxy = userRoleStore1.getProxy();
    	    		proxy.setExtraParam("userName",Ext.getCmp('inputUserName').getValue());
    	    		userRoleStore1.loadPage(1);
    			} 
    	  }
    	   	]
   	   	}),
		bbar:new Ext.PagingToolbar({
			pageSize: SystemConstant.commonSize,
	        store: userRoleStore1,
	        displayInfo: true,
	        displayMsg: SystemConstant.displayMsg,
	        emptyMsg: SystemConstant.emptyMsg
	    })
	});
		    		
	//用户分配角色窗口
	var userWin1 = Ext.create(Ext.window.Window,{
  		title:"选择用户",
  		width:650,
		height:450,
		modal:true,
		resizable:true,
		closeAction:'destroy',
		layout:'border',
		items:[orgTreePanelleft,{
				region:'center',
				layout:'fit',
				border:false,
				items:[userPanel1]
		}],
		buttonAlign : 'center',
		buttons:[{
			id:'userOK',
			text:'确定',
			disabled:true,
			handler:function(){
				var rows = Ext.getCmp('userPanel1').getSelectionModel().getSelection();
				var userId = rows[0].get("userId");
				var userName = rows[0].get("realname");
				
				Ext.getCmp(idId + '').setValue(userId);
				Ext.getCmp(nameId + '').setValue(userName);
				
				userWin1.close();
			}
		},{
			text:'取消',handler:function(){
			userWin1.close();
		}}
		]
	}).show();
}
