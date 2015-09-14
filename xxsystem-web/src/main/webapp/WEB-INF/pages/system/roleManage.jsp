<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>角色管理</title>
	   <link rel="stylesheet" type="text/css" href="${ctx}/js/ext-3.4/resources/css/ext-all.css" />
	    <link rel="stylesheet" type="text/css" href="${ctx}/css/icons.css" />
 	<script type="text/javascript" src="${ctx}/js/ext-3.4/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="${ctx}/js/ext-3.4/ext-all.js"></script>
	<script type="text/javascript" src="${ctx}/js/ext-3.4/locale/ext-lang-zh_CN.js"></script>

	<script type="text/javascript" src="${ctx}/js/Ext.ux.plugins.js"></script>
	<script type="text/javascript" src="${ctx}/js/ext-3.4/ProgressBarPager.js"></script>
	 <script type="text/javascript" src="${ctx}/js/ext-3.4/TreeFilter.js"></script>
	  <script type="text/javascript" src="${ctx}/js/ext-3.4/TreeFilter.js"></script>
    <script type="text/javascript" src="${ctx}/js/ext-3.4/TreeCombo.js"></script>
    <script type="text/javascript" src="${ctx}/js/ext-3.4/tooltip.js"></script>
    <script type="text/javascript" src="${ctx}/js/ext-3.4/PanelResizer.js"></script>
    <script type="text/javascript" src="${ctx}/js/ext-3.4/PagingMemoryProxy.js"></script>
    <script type="text/javascript" src="${ctx}/js/tool/Ext.ux.plugins.js"></script>
</head>

<body>
	
</body>
</html>

<script type="text/javascript">
	Ext.BLANK_IMAGE_URL = '${ctx}/images/s.gif';
	Ext.QuickTips.init();
	Ext.namespace('role'); 
	
	role.manage = function() { 
	var grid;
	var permissionStore;
	var rolePermissionPanel;
	
	//得到grid中选择记录的id,例如1,2,3
	function getSelectRecordIds(){
	    var records = role.manage.grid.getSelectionModel().getSelections();
	  	if(records.join('')=='') {
	    	return false;
	    }
	    
	    var ids = [];
	    for(var i=0; i<records.length; i+=1){
	    	var id = records[i].get('id');  
	        ids.push(id);                //向数组后添加元素
	    }
	    return ids.toString();
	}

	//清空查询条件，更新角色列表
	function refreshRoleGrid(){
		Ext.getCmp('roleName').setValue("");
		var store = role.manage.grid.getStore();
		store.setBaseParam("roleName",'');
		store.load({params:{start:0,limit:20}});
	}

	//修改角色权限，刷新角色权限列表
	function refreshPermission(roleId,cursor){
		Ext.Ajax.request({ 
 	 		url:'${ctx}/system/getRolePermissionList.do',
 	 		params:{id:roleId},
 	        success : function(res ,options) {
 	 			var objs= Ext.decode(res.responseText);
 	 			rolePermissionPanel.getStore().proxy = new Ext.data.PagingMemoryProxy(objs); 
 	 			rolePermissionPanel.getStore().load({params:{start:cursor,limit:12}});
 			}, 
 	        failure : function(response) { 
 	         	Ext.tooltip.msg('no', '系统繁忙，请稍后再试！');
 	        }
 		}); 
	}
	
    // 公共空间 
    return { 
        // 公共的属性,如,要转换的字符串 
        // 公共方法 
        setGrid : function (grid){
			this.grid = grid;
        },
        //添加角色
        addRole : function (){
            //添加角色窗口布局
        	var addRoleForm = new Ext.form.FormPanel({
        	    bodyStyle :'padding:5px 5px 0 0',
        		border :false,
        	    layout : 'form', 
        	    labelAlign:'right',
        	    labelWidth:80,
        	    defaults:{
        	      	width:250,
        	      	allowBlank:false
        	    },
        	    items:[{
        		    xtype : 'textfield',
        	        fieldLabel:'角色名称',
        	        name:'role.roleName',
        	        blankText : '角色名称不能为空',
        	        maxLength:50,
        	        plugins : [Ext.ux.plugins.RemoteValidator], 
			        rvOptions : { 
			        	url : '${ctx}/system/validateAddRoleName.do?oldRoleId='+0
			    	},  
        	        msgTarget : 'side'
        	    },{
        	    	xtype : 'textarea',
        	        fieldLabel:'角色描述',
        	        height : 80,
        	        maxLength:300,
        	        name:'role.description',
        	        allowBlank:true
        	    }]
        	});

			//添加角色方法
        	var addRoleWin = new Ext.Window({
        		title:'添加角色',
                closable:true,
                width:410,
                height:220,
                modal:true,
                plain:true,
                layout :"fit",
                resizable:false,
                items: [
					addRoleForm
                ],
                buttonAlign:'center', 
            	buttons:[{
            		text:'保存',
            	    handler:function(){
                	    if(!addRoleForm.getForm().isValid()){
                	    	return;
                	    }
                	    addRoleForm.getForm().submit({
        	                url:'${ctx}/system/addRole.do',
        	                success:function(form,action){
        	                	Ext.tooltip.msg('yes', action.result.msg);
                	    		addRoleWin.close(); 
                	    		refreshRoleGrid();
                      	    },
        	              	failure:function(form,action){
                      	    	Ext.tooltip.msg('no', action.result.msg);
        	              	}
                        });
            	    }
            	    },{
            	       text:'重置',
            	       handler:function(){
            	    		addRoleForm.getForm().reset();
            	       }
            	    },{
            	       text:'关闭',
            	       handler:function(){
            	    		addRoleForm.getForm().reset();
            	    		addRoleWin.close();
            	       }
            	    }]
            });
            
        	addRoleWin.show();
       },
       //修改角色方法
       updateRole : function(){

           //判断修改角色是否只选择一行
    	   	var ids = getSelectRecordIds();
			if(ids==false){
				Ext.tooltip.msg('no', '请选择要操作的行！');
	          	return;
			}if(ids.indexOf(',')!=-1){
				Ext.tooltip.msg('no', '只能选择一条记录！');
	          	return;
			}

			//修改角色布局窗口
			var row = role.manage.grid.getSelectionModel().getSelections()[0];
    	   	var updateRoleForm = new Ext.form.FormPanel({
    		    bodyStyle :'padding:5px 5px 0 0',
    			border :false,
    		    layout : 'form', 
    		    labelAlign:'right',
    		    labelWidth:80,
    		    defaults:{
    		      	width:250,
    		      	allowBlank:false
    		    },
    		    items:[{
    		        xtype:'hidden',
    		        name:'role.id',
    		        value : row.get('id')
    		    },{
    			    xtype : 'textfield',
    		        fieldLabel:'角色名称',
    		        name:'role.roleName',
    		        value : row.get('roleName'),
        	        maxLength:50,
			    	plugins : [Ext.ux.plugins.RemoteValidator], 
			        rvOptions : { 
			        	url : '${ctx}/system/validateUpdateRoleName.do?oldRoleId='+row.get("id")
			    	},
    		        blankText : '角色名称不能为空',  
    		        msgTarget : 'side'
    		    },{
    		    	xtype : 'textarea',
    		        fieldLabel:'角色描述',
    		        value : row.get('description'),
    		        height : 80,
        	        maxLength:300,
    		        name:'role.description',
    		        allowBlank:true
    		    }]
    		});
    		
    		//修改角色方法
			var updateRoleWin = new Ext.Window({
				title:'修改角色',
	           	closable:true,
	            width:410,
	            height:220,
	            modal:true,
	            plain:true,
	            layout :"fit",
         	    resizable:false,
	            items: [
	   			 updateRoleForm
	            ],
	            buttonAlign:'center', 
	       		buttons:[{
	       		text:'保存',
	       	    handler:function(){
	           	    if(!updateRoleForm.getForm().isValid()){
	           	    	return;
	           	    }
	           	    updateRoleForm.getForm().submit({
	   	                url:'${ctx}/system/updateRole.do',
	   	                success:function(form,action){
	   	                	Ext.tooltip.msg('yes', action.result.msg);
	           	    		updateRoleWin.close(); 
	           	    		role.manage.grid.getStore().reload();
	                 	    },
	   	              	failure:function(form,action){
	                 		Ext.tooltip.msg('no', action.result.msg);
	   	              	}
	                   });
	       	    }
	       	    },{
	       	       text:'重置',
	       	       handler:function(){
	       	    		updateRoleForm.getForm().reset();
	       	       }
	       	    },{
	       	       text:'关闭',
	       	       handler:function(){
	       	    		updateRoleWin.close();
	       	       }
	       	    }]
	       	});

	        updateRoleWin.show();
        },
       //删除角色方法
        deleteRole : function(){
			var ids = getSelectRecordIds();//所选择删除行的ID
   	        if(ids==false){
   	        	Ext.tooltip.msg('no', '请选择要操作的行！');
   	       		return
   	        }
   	       	Ext.MessageBox.confirm('信息提示：', '确定要删除这'+role.manage.grid.getSelectionModel().getSelections().length+'条数据?', function(btn){
   	       		if(btn == 'yes'){
   	       	    	Ext.Ajax.request({
	   	       			url: '${ctx}/system/deleteRole.do',
	   	       	    	params: { ids: ids},
	   	       	    	success: function(response, opts) {
	   	       	    		var result = Ext.decode(response.responseText);
		   	       	    	var flag = result.success;
					 	 	if(flag){
		   	       	    		Ext.tooltip.msg('yes', result.msg);
		         	    		role.manage.grid.getStore().reload();
					 	     }else{
					 	      	Ext.tooltip.msg('no', result.msg);
						 	 }
	   	       	    	}
   	       	    	});
   	       	    }
   	       	});
       }, 
       //显示角色信息窗口
       viewRole : function(){
    	    var row = role.manage.grid.getSelectionModel().getSelections()[0];
       		var viewRoleForm = new Ext.form.FormPanel({
       			bodyStyle :'padding:20 0 0 0',
			    border :false,
			    layout : 'form', 
			    labelAlign:'right',
			    defaultType:'textfield',
			    items:[{
		        	fieldLabel:'角色名称',
	       	        xtype: 'box',
	       	        style:'padding-top:5px;',  
	       	        html: '<div style="width:200px;">'+row.get('roleName')+'</div>'    
			    },{
			    	fieldLabel:'角色描述',
	       	        xtype: 'box',
	       	        style:'padding-top:5px;',  
	       	        html: '<div style="width:200px;height:100px;overflow:auto;overflow-x:hidden;">'+row.get('description')+'</div>'    
			    }]
			});

        	var viewRoleWin = new Ext.Window({
				title: '查看角色',
		        closable:true,
		        width:340,
		        height:250,
		        modal:true,
		        plain:true,
		        layout :"fit",
		        resizable:false,
		        items: [
					viewRoleForm
		        ],
		        buttonAlign:'center', 
		    	buttons:[{
	    	       	text:'关闭',
	    	       	handler:function(){
		    		viewRoleWin.close();
	    	    	}
		    	}]
		    });
        	viewRoleWin.show();
			
       	},
       	//查看权限方法
        viewPermission : function(rowIndex){
       		var record = rolePermissionPanel.getStore().getAt(rowIndex);
    		var rolePermissionId = record.get('rolePermissionId');
    		var roleId = record.get('roleId');
       		var isView = record.get('isView');
    		var isAdd = record.get('isAdd');
    		var isUpdate = record.get('isUpdate');
    		var isDelete = record.get('isDelete');
    		var isAudit = record.get('isAudit');
    		var moduleCode = record.get('moduleCode');
	    	if(isView == 1){
	    		isView = 0;
	    	}else{
	    		isView = 1;
	    	}
	    	role.manage.updateRolePermissions(rolePermissionId,roleId,isView,isAdd,isUpdate,isDelete,isAudit,moduleCode);
        },
        //添加权限方法
        addPermission : function (rowIndex){
        	var record = rolePermissionPanel.getStore().getAt(rowIndex);
    		var rolePermissionId = record.get('rolePermissionId');
    		var roleId = record.get('roleId');
       		var isView = record.get('isView');
    		var isAdd = record.get('isAdd');
    		var isUpdate = record.get('isUpdate');
    		var isDelete = record.get('isDelete');
    		var isAudit = record.get('isAudit');
    		var moduleCode = record.get('moduleCode');
	    	if(isAdd == 1){
	    		isAdd = 0;
	    	}else{
	    		isAdd = 1;
	    	}
	    	role.manage.updateRolePermissions(rolePermissionId,roleId,isView,isAdd,isUpdate,isDelete,isAudit,moduleCode);
        },
        //修改权限方法
        updatePermission : function(rowIndex){
        	var record = rolePermissionPanel.getStore().getAt(rowIndex);
    		var rolePermissionId = record.get('rolePermissionId');
    		var roleId = record.get('roleId');
       		var isView = record.get('isView');
    		var isAdd = record.get('isAdd');
    		var isUpdate = record.get('isUpdate');
    		var isDelete = record.get('isDelete');
    		var isAudit = record.get('isAudit');
    		var moduleCode = record.get('moduleCode');
	    	if(isUpdate == 1){
	    		isUpdate = 0;
	    	}else{
	    		isUpdate = 1;
	    	}
	    	role.manage.updateRolePermissions(rolePermissionId,roleId,isView,isAdd,isUpdate,isDelete,isAudit,moduleCode);
        },
        //删除权限方法
        deletePermission : function(rowIndex){
        	var record = rolePermissionPanel.getStore().getAt(rowIndex);
    		var rolePermissionId = record.get('rolePermissionId');
    		var roleId = record.get('roleId');
       		var isView = record.get('isView');
    		var isAdd = record.get('isAdd');
    		var isUpdate = record.get('isUpdate');
    		var isDelete = record.get('isDelete');
    		var isAudit = record.get('isAudit');
    		var moduleCode = record.get('moduleCode');
	    	if(isDelete == 1){
	    		isDelete = 0;
	    	}else{
	    		isDelete = 1;
	    	}
	    	role.manage.updateRolePermissions(rolePermissionId,roleId,isView,isAdd,isUpdate,isDelete,isAudit,moduleCode);
        },
 
        //角色分配权限
        updateRolePermissions : function(rolePermissionId,roleId,isView,isAdd,isUpdate,isDelete,isAudit,moduleCode){
        	Ext.Ajax.request({
         		url: '${ctx}/system/updateRolePermission.do',
         	   	params: { 
         	   		rolePermissionId:rolePermissionId,
        			roleId: roleId,
        			isView:isView,
        			isAdd: isAdd,
        			isUpdate: isUpdate,
        			isDelete: isDelete,
        			isAudit: isAudit,
        			moduleCode:moduleCode
         	   	},
				success: function(response, opts) {
         	   		var result = Ext.decode(response.responseText);
      	    		var flag = result.success;
	 	      		if(flag){
      	    			Ext.tooltip.msg('yes', result.msg);
  	    				refreshPermission(roleId,rolePermissionPanel.getBottomToolbar().cursor);
	 	      		}else{
	 	      			Ext.tooltip.msg('no', result.msg);
		 	   	 	}
           	   	}
        	});
        },
        //角色分配权限显示窗口
        viewRolePermission : function(){
        	var row = rolePermissionPanel.getSelectionModel().getSelections()[0];
       		var viewRolePermissionForm = new Ext.form.FormPanel({
       			bodyStyle :'padding:20 0 0 0',
			    border :false,
			    layout : 'form', 
			    labelAlign:'right',
			    defaultType:'textfield',
			    items:[{
		        	fieldLabel:'模块名称',
	       	        xtype: 'box',
	       	        style:'padding-top:5px;',  
	       	        html: '<div style="width:200px;">'+row.get('moduleName')+'</div>'    
			    },{
			    	fieldLabel:'权限说明',
	       	        xtype: 'box',
	       	        style:'padding-top:5px;',  
	       	        html: '<div style="width:200px;height:100px;overflow:auto;overflow-x:hidden;">'+row.get('moduleDescribe')+'</div>'    
			    }]
			});

        	var rolePermissionWin = new Ext.Window({
				title: '查看权限说明',
		        closable:true,
		        width:360,
		        height:250,
		        modal:true,
		        plain:true,
		        layout :"fit",
		        items: [
					viewRolePermissionForm
		        ],
		        buttonAlign:'center', 
		    	buttons:[{
	    	       	text:'关闭',
	    	       	handler:function(){
		    		rolePermissionWin.close();
	    	    	}
		    	}]
		    });
        	rolePermissionWin.show();
        },
        //进入角色分配权限窗口
		updateRolePermission : function(){

    		var row = role.manage.grid.getSelectionModel().getSelections()[0];
    	    var rolePermRecord = Ext.data.Record.create([
		    {name:'rolePermissionId',type:'int'},
		    {name:'roleId',type:'int'},
			{name:'moduleCode',type:'string'},
			{name:'moduleName',type:'string'},
			{name:'moduleDescribe',type:'string'},
			{name:'view',type:'int'},
			{name:'add',type:'int'},
			{name:'update',type:'int'},
			{name:'delete',type:'int'},
			{name:'isView',type:'int'},
			{name:'isAdd',type:'int'},
			{name:'isUpdate',type:'int'},
			{name:'isDelete',type:'int'},
			{name:'isAudit',type:'int'}
			]);

    		var content = function(value, cellmeta, record, rowIndex, columnIndex, store){
	         	var enter = record.get('moduleName');
				return '<div ext:qtip='+enter+' />'+enter+'</div>';
 			}	
    		
    		permissionStore = new Ext.data.Store({
	     	 	reader:new Ext.data.JsonReader({},rolePermRecord)
	     	 });

   			Ext.Ajax.request({ 
     	 		url:'${ctx}/system/getRolePermissionList.do',
     	 		params:{id:row.get("id")},
     	        success : function(res ,options) {
     	 			var objs= Ext.decode(res.responseText);
     	 			rolePermissionPanel.getStore().proxy = new Ext.data.PagingMemoryProxy(objs); 
     	 			rolePermissionPanel.getStore().load({params:{start:0,limit:12}});
     			}, 
     	        failure : function(response) { 
     				Ext.tooltip.msg('no', '系统繁忙，请稍后再试！');
     	        }
     		}); 

   			//角色权限，查看方法
			var viewPermissionRenderer = function(value, cellmeta, record, rowIndex, columnIndex, store){
   				var view = record.get('view');
        		var isView = record.get('isView');
        		
        		if(view == 1){
        			if(isView == 1){
        				return '<img title="可查看" src="${ctx}/images/icons/yes.png"  style="cursor: pointer" onclick="javascript:view_Permission('+rowIndex+');"/>';
        			}else{
        				return '<img title="不可查看" src="${ctx}/images/icons/no.png" style="cursor: pointer" onclick="javascript:view_Permission('+rowIndex+');"/>';
        			}
        		}
        		return '';
        	}

   			//角色权限，添加方法
			var addPermissionRenderer = function(value, cellmeta, record, rowIndex, columnIndex, store){
        		var add = record.get('add');
        		var isAdd = record.get('isAdd');
        		if(add == 1){
        			if(isAdd == 1){
        				return '<img title="可添加" src="${ctx}/images/icons/yes.png" style="cursor: pointer" onclick="javascript:add_Permission('+rowIndex+');"/>';
        			}else{
        				return '<img title="不可添加" src="${ctx}/images/icons/no.png"  style="cursor: pointer" onclick="javascript:add_Permission('+rowIndex+');"/>';
        			}
        		}
        		return '';
        	}

   			//角色权限，修改方法
			var updatePermissionRenderer = function(value, cellmeta, record, rowIndex, columnIndex, store){
        		var update = record.get('update');
        		var isUpdate = record.get('isUpdate');
        		if(update == 1){
        			if(isUpdate == 1){
        				return '<img title="可修改" src="${ctx}/images/icons/yes.png" style="cursor: pointer" onclick="javascript:update_Permission('+rowIndex+');"/>';
        			}else{
        				return '<img title="不可修改" src="${ctx}/images/icons/no.png" style="cursor: pointer" onclick="javascript:update_Permission('+rowIndex+');"/>';
        			}
        		}
        		return '';
        	}

        	//角色权限，删除方法
			var deletePermissionRenderer = function(value, cellmeta, record, rowIndex, columnIndex, store){
        		var del = record.get('delete');
        		var isDelete = record.get('isDelete');
        		if(del == 1){
        			if(isDelete == 1){
        				return '<img title="可删除" src="${ctx}/images/icons/yes.png"  style="cursor: pointer" onclick="javascript:delete_Permission('+rowIndex+');"/>';
        			}else{
        				return '<img title="不可删除" src="${ctx}/images/icons/no.png"  style="cursor: pointer" onclick="javascript:delete_Permission('+rowIndex+');"/>';
        			}
        		}
        		return '';
        	}

   			var p_rm = new Ext.grid.RowNumberer();
          	
          	var p_cm = new Ext.grid.ColumnModel([
          	 	p_rm,
          		{header:'模块名称',dataIndex:'moduleName',width:25,sortable:true},
          		{header:'查看',dataIndex:'',renderer:viewPermissionRenderer,width:25,align:'center'},
          		{header:'添加',dataIndex:'',renderer:addPermissionRenderer,width:25,align:'center'},
          		{header:'修改',dataIndex:'',renderer:updatePermissionRenderer,width:25,align:'center'},
          		{header:'删除',dataIndex:'',renderer:deletePermissionRenderer,width:25,align:'center'},
          		{header:'权限说明',dataIndex:'moduleDescribe',sortable:true}
          	]);

          	//点击角色，弹出的该角色对应的权限列表
          	rolePermissionPanel = new Ext.grid.GridPanel({
          		region:'center',
          		height:700,
          		stripeRows: true,
          		loadMask:true,
          		columnLines: true,
          		store:permissionStore,
          		cm:p_cm,
          		sm: new Ext.grid.RowSelectionModel({singleSelect : true}),
          		viewConfig:{
          			columnsText:'显示的列',
          		    scrollOffset:-1,//滚动条宽度,默认20
          		    sortAscText:'升序',
          		    sortDescText:'降序',
          		    forceFit:true//表格会自动延展每列的长度,使内容填满整个表格
          		},  
          		listeners:{
          			rowdblclick: function(gridThis,rowIndex,e){
		   			    var model = rolePermissionPanel.getSelectionModel();
		   			 model.selectRow(rowIndex);
		   			    role.manage.viewRolePermission();
		   			}
        		},
          	   	bbar:new Ext.PagingToolbar({
          	    	pageSize: 12,
          	        store: permissionStore,
          	        displayInfo: true,
          	        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
          	        emptyMsg: "无数据",
          	       	plugins: new Ext.ux.ProgressBarPager()
          	    }),
          	    plugins:new Ext.ux.PanelResizer({
          	        minHeight: 100
          	    })
          	});
                  	
			var rolePermissionWin = new Ext.Window({
         		title:'角色管理-角色授权',
         	    closable:true,
         	    modal:true,
		        plain:true,
		        border:false,
         	    width:750,
         	    height:410,
         	    layout :"fit",
         	    items: [
					rolePermissionPanel
         	    ],
         	    buttonAlign:'center', 
         	    buttons:[{
         	    	text:'关闭',
         	    	handler:function(){
         	    		rolePermissionWin.close();
         	    	}
         	 	}]
         	});
			rolePermissionWin.show();
       }
    }; 
}();

Ext.onReady(function() {
	
	//--------------------------------------------角色表格开始---------------------------------------------------
	var roleRecord = Ext.data.Record.create([
		{name:'id',type:'int'},
		{name:'roleName',type:'string'},
		{name:'description',type:'string'}
	]);

	var roleStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({url: '${ctx}/system/getRoleList.do'}),
		reader:new Ext.data.JsonReader({
		   totalProperty:'totalSize',
		   root:'list'
		},roleRecord)
	});
	
	var rm = new Ext.grid.RowNumberer();
	var sm = new Ext.grid.CheckboxSelectionModel({
		listeners : {
			"selectionchange" : function(win) {
				var length=role.manage.grid.getSelectionModel().getSelections().length;
					if (length == 1) {
							Ext.getCmp('btnDeleteUser').setDisabled(false);
							Ext.getCmp('btnUpdateUser').setDisabled(false);
					} else if(length==0){
						Ext.getCmp('btnDeleteUser').setDisabled(true);
						Ext.getCmp('btnUpdateUser').setDisabled(true);
					}else{
						Ext.getCmp('btnDeleteUser').setDisabled(false);
						Ext.getCmp('btnUpdateUser').setDisabled(true);
					}
			}
		}
	});
	
	var cm = new Ext.grid.ColumnModel([
	 	rm,sm,
		{header:'角色名称',dataIndex:'roleName',sortable:true,width: 40},
		{header:'角色描述',dataIndex:'description',sortable:true},
		{header:'权限',
			dataIndex:'',
			sortable:true,
			width: 20,
			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				return '<img title="点击编辑权限" src="${ctx}/images/icons/permission.gif" style="cursor: pointer" onclick="updateRolePermission();"/>';
			},
			align:'center'
		}
	]);
	cm.defaultSortable = true;
	
	var roleGridPanel = new Ext.grid.GridPanel({
		bodyStyle :'margin: 0px;border-right:0px;border-left:0px',
		stripeRows: true,
		loadMask:true,
		columnLines: true,
		store:roleStore,
		cm:cm,
		sm: sm,
		viewConfig:{
			columnsText:'显示的列',
			scrollOffset:-1,//滚动条宽度,默认20
		    sortAscText:'升序',
		    sortDescText:'降序',
		    forceFit:true//表格会自动延展每列的长度,使内容填满整个表格
		},
		listeners:{
			rowdblclick: function(gridThis,rowIndex,e){
   			    var model =role.manage.grid.getSelectionModel();
   			 model.selectRow(rowIndex);
   			    role.manage.viewRole();
   			}
		},
		tbar :new Ext.Toolbar({
		    height: 40,
		    style:'padding:10px 10px 0px 10px;border-top:0px;border-right:0px;border-left:0px',
			items:[
	    	'角色名称：',
	    	' ',
	    	new Ext.form.TextField({
	    		width:'135',
	    		id:'roleName'
	    	}),
	    	"&nbsp;",
	    	{
    	    	text :   "查询", 
    	    	iconCls: "search-button", 
    	    	handler:function(){
		    		roleStore.setBaseParam("roleName",Ext.getCmp('roleName').getValue());
    	    		roleStore.load({params:{start:0,limit:20}});
    			} 
    	   	},
		    '-',
	    	{
    	    	text :   "重置", 
    	    	iconCls: "reset-button", 
    	    	handler:function(){
    	   			Ext.getCmp('roleName').setValue("");
    			} 
    	   	},
		   	'->',{
				text : "添加",
    	    	iconCls: "add-button",
    	    	handler:role.manage.addRole
    	   	},
    	   	'-',{
    	    	text : "修改",
    	    	disabled:true,
    	    	iconCls: "update-button",
    	    	id:'btnUpdateUser',
    	    	handler:role.manage.updateRole
    	   	},
    	   	'-',
    	   	{
    	    	text : "删除",
    	    	iconCls: "delete-button",
    	    	id:'btnDeleteUser',
    	    	disabled:true,
    	    	handler:role.manage.deleteRole
           	}/* ,
			'-',
   	    	{
    	    	iconCls: "help-button",
    	    	tooltip: '帮助',
    	    	handler: function() {
    	    		if(Ext.getCmp("helpWin")) {
    	    			Ext.getCmp("helpWin").close();
    	    		}
    	    		openHelpWin("5.9.2");
       	   		}
       	   	} */
		   ]
   	   	}),  
	   	bbar:new Ext.PagingToolbar({
	   		style:'border-right:0px;border-bottom:0px;border-left:0px',
	        pageSize: 20,
	        store: roleStore,
	        displayInfo: true,
	        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
	        emptyMsg: "无数据",
	        plugins: new Ext.ux.ProgressBarPager()
	    }),
	    plugins:new Ext.ux.PanelResizer({
	        minHeight: 100
	    })
	});

	roleStore.load({params:{start:0,limit:20}});

	
	role.manage.setGrid(roleGridPanel);
	new Ext.Viewport({
	    layout: 'fit',
	    bodyStyle :'margin: 0px;border:0px',
	    items: [
          roleGridPanel
	    ]
	});
	//------------------------------角色表格结束---------------------------------------------------	
});

	//点击进入到角色分配权限页面
	function updateRolePermission(){
		role.manage.updateRolePermission();
	}
	//角色分配查看方法
	function view_Permission(rowIndex){
		role.manage.viewPermission(rowIndex);
	}
	//角色分配添加方法
	function add_Permission(rowIndex){
		role.manage.addPermission(rowIndex);
	}
	//角色修改权限方法
	function update_Permission(rowIndex){
		role.manage.updatePermission(rowIndex);
	}
	//角色删除权限方法
	function delete_Permission(rowIndex){
		role.manage.deletePermission(rowIndex);
	}

</script>
