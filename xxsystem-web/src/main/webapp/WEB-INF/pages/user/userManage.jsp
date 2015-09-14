<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/doc_type.jsp"%>
<html>
<head>
<%@include file="../common/meta.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/css.jsp"%>
<%@include file="../common/ext.jsp"%>
<title>用户管理</title>
<script type="text/javascript" src="${ctx}/scripts/user/UserGrid.js"></script>
<script type="text/javascript" src="${ctx}/scripts/user/UserWin.js"></script>
<link href="" rel="SHORTCUT ICON" />
</head>
<body><div id="userManagerPanel"></div>
<div id="roleManagerWindow"></div>
<div id="permissionManagerWindow"></div>
<div id="permissionGrids"></div>
<div id="roleGrids"></div>
<div style="display: none;">
	<form id="downloadExcel" action="${ctx}/user/downloadUserExcelTemplate.action" method="post">
		<input type="text" name="downloadType" id="downloadType" />
	</form>
	<form id="exportUsers" action="${ctx}/user/exportAllUsers.action" method="post"></form>
</div>
</body>
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
var expandFlag = false;
var isExist;
var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
Ext.onReady(function(){
	Ext.QuickTips.init(); 
	//自动引入其他需要的js
	 Ext.require([
        "Ext.grid.*",
        "Ext.toolbar.Paging",
        "Ext.form.*",
        "Ext.data.*"
	]);
	var checkedResIdsArray = [];
	var treePanel;
	
	Ext.define('treeModel', {
	    extend: 'Ext.data.Model',
	    fields: [
	             {name: 'nodeId',type: 'int'}, 
	             {name: 'parentId',type: 'int'}, 
	             {name: 'id',type: 'int',mapping:'nodeId'},
	             {name: 'text',type: 'string'} 
	            ]
   });

//主页面左侧
var orgTreeStore2=Ext.create('Ext.data.TreeStore', {
    model: 'treeModel',
    nodeParam:'parentId',
    autoLoad:false,
    clearOnLoad :true,
    proxy: {
        type: 'ajax',
        reader:{
                 type: 'json'
              },
         folderSort: true,
         sorters: [{
                    property: 'nodeId',
                    direction: 'DESC'
         }],
        url :'${ctx}/org/getUnitTreeList.action'
    },
    root: {
          expanded: true,
          id:"0"
          }
    });


	orgTreeStore2.on("load",function(store, node, records){
		treePanel.getSelectionModel().select(treePanel.getRootNode().firstChild,true);
	});
                    
	 treePanel=Ext.create("Ext.tree.Panel", {
	 		title:'组织信息',
	       	store: orgTreeStore2,
	     	id:"orgTreeLeft",
	        height: document.body.clientHeight,
	        width: 200,
	        useArrows: true,
	        rootVisible : false,
			region: "west",
			border: false,
			collapsible: true,
            split: true,
			collapseMode:"mini",
	        listeners:{
	        	'checkchange':	function(node,checked,eOpts){
						if(!node.leaf){
							if(checked){		//勾选
								node.expand(true);
								node.cascadeBy(function(child){	//子节点全勾选
									child.set('checked', checked);
								});
								while(node.parentNode.data.id != 0){	//父节点全勾选
									node.parentNode.set('checked', checked);
									node = node.parentNode;
								}
							}else{				//取消勾选
								node.cascadeBy(function(child){	//子节点全取消勾选
									child.set('checked', checked);
								});

								//如果父节点的所有子节点都没被选中，则父节点也取消勾选
								while(node.parentNode.data.id != 0){
									var checkFlag = false;
									node.parentNode.cascadeBy(function(child){
										if(child.data.id != node.parentNode.data.id && child.get('checked')){
											checkFlag = true;
										}
									});

									if(!checkFlag){
										node.parentNode.set('checked', checked);
									}
									node = node.parentNode;
								}
							}
						}
						var checkedResIds = "";
						treePanel.getRootNode().cascadeBy(function(child){
							if(child.data.id != 0 && child.get('checked')){
								checkedResIds += child.data.id + ",";
							}
						});
						checkedResIds = checkedResIds.substring(0,checkedResIds.length - 1);
	     				var store = sshframe.user.userGrid.getStore();
	     				var proxy = store.getProxy();
	     				proxy.setExtraParam("orgId",checkedResIds);
						proxy.setExtraParam("userName",Ext.getCmp('inputSearchName').getValue());
	     				store.loadPage(1);
 					},
				"afteritemexpand" : function(node){
					if(node.parentNode.get('checked') || node.get('checked')){
						node.set('checked', true);
						node.cascadeBy(function(child){	//子节点全勾选
							child.set('checked', true);
						});

						var checkedResIds = "";
						treePanel.getRootNode().cascadeBy(function(child){
							if(child.data.id != 0 && child.get('checked')){
								checkedResIds += child.data.id + ",";
							}
						});
						checkedResIds = checkedResIds.substring(0,checkedResIds.length - 1);
						var store = sshframe.user.userGrid.getStore();
						var proxy = store.getProxy();
						proxy.setExtraParam("orgId",checkedResIds);
						proxy.setExtraParam("userName",Ext.getCmp('inputSearchName').getValue());
						store.removeAll(true);
						store.loadPage(1);
					}
				}
	        },
			tbar:new Ext.Toolbar({
				style:"border-top:0px;border-left:0px",
				items:[
				{
		            iconCls: "icon-expand-all",
		            text:"展开",
					tooltip: "展开所有",
		            handler: function(){ treePanel.expandAll(); },
		            scope: this
		        },{
		            iconCls: "icon-collapse-all",
		            text:"折叠",
		            tooltip: "折叠所有",
		            handler: function(){ treePanel.collapseAll(); },
		            scope: this
		        }]
	        })
	    });
	 
	//视图
	Ext.create("Ext.container.Viewport", {
		layout : "border",
		items : [ treePanel, sshframe.user.userGrid ]
	});
	
});

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
};

</script>
</html>
