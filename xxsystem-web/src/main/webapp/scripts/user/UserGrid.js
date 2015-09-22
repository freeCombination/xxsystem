/**
 * 用户管理的列表功能，包含功能有 列表、查看功能。 
 * @date 20150706
 * @author wujl
 */

/**
 * 定义用户Model
 */
Ext.define("sshframe.user.roleManageModel",{
					extend:"Ext.data.Model",
					fields:[
						{name:'roleName'},
						{name:'roleCode'},
						{name:'description'},
						{name:'roleId',type:'int'}
					]
				});

Ext.define("sshframe.user.permissionManageModel",{
					extend:"Ext.data.Model",
					fields:[
						{name:'resourceName'},
						{name:'code'},
						{name:'resourceType'},
						{name:'remarks'},
						{name:'resourceId',type:'int'}
					]
				});

Ext.define("sshframe.user.userModel",{
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
		
		{name:"respName"},
		{name:"respId"},
		{name: "nationality"},
		{name: "partyDate"},
		{name: "jobStartDate"},
		{name: "officeHoldingDate"},
		{name: "educationBackground"},
		{name: "technicaTitles"},
		{name: "comeDate"},
		{name: "skill"},
		{name: "performance"},
		{name: "employmentInfo"},
		{name: "postWage"},
		{name: "trainInfo"}
	]
});

/**
 * 定义Store
 */
sshframe.user.userStore=Ext.create("Ext.data.Store", {
    pageSize: SystemConstant.commonSize,
    model:"sshframe.user.userModel",
    remoteSort:true,
    
	proxy: {
        type:"ajax",
        actionMethods: {
                read: 'POST'
            },
	    url: basePath+"/user/getUserList.action",
	    reader: {
		     totalProperty: "totalSize",
		     root: "list"
	    },
    simpleSortMode :true
    },
    sorters:[{
        property:"userId",
        direction:"ASC"
    }]
});

var cm=[
			{xtype: "rownumberer",text:"序号",width:60,align:"center"},
        	{header: "ID",align:'left',dataIndex: "userId",hidden: true},
        	{header: "部门",align:'left',dataIndex: "orgName",width:90},
        	{header: "登录名",align:'left',dataIndex: "username",width:90},
        	{header: "姓名",align:'left',dataIndex: "realname",width:90},
        	{header: "ERPID",align:'left',dataIndex: "erpId",width:90},
        	{header: "性别",align:'left',dataIndex: "gender",width:90},
        	{header: "岗位",align:'left',dataIndex: "respName",width:90},
        	{header: "电话",align:'left',dataIndex: "mobileNo",width:90},
        	{header: "办公电话",dataIndex: "phoneNo",width:90},
        	{header: "禁用",dataIndex: "",width:50,
        		renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
						var isLockup = record.get('enable');
						if(isLockup == null || typeof(isLockup) == "undefined"){
							return ;
						}
						var userId = record.get('userId');
						if(isLockup == 0){
							return '<img title="点击锁定用户" src="'+basePath+'/images/icons/unlock.gif" style="cursor: pointer" onclick="sshframe.user.lockupUser('+userId+','+isLockup+')"/>';
						}else if(isLockup == 1){
							return '<img title="点击解锁用户" src="'+basePath+'/images/icons/lock.gif" style="cursor: pointer" onclick="sshframe.user.lockupUser('+userId+','+isLockup+')"/>';
						}
					},
					align:'center'
        	}
        ];
/**
 * 定义Grid
 */
sshframe.user.userGrid =  Ext.create("Ext.grid.Panel",{
	title:'用户管理',
	border:false,
	width: "100%",
	maxHeight:900,
	id: "userGrid",
	region: "center",
	bbar:  Ext.create("Ext.PagingToolbar", {
		store: sshframe.user.userStore
	}),
    selModel:Ext.create("Ext.selection.CheckboxModel"),
	store: sshframe.user.userStore,
	columns:cm,
	listeners:{
		'itemdblclick' : function(userGrid, record, item, index, e, eOpts ){
			
		},
		'render': function(g) {
			 for(var i = 0;i < userPermissionArr.length;i++){
					if("usermanage_add_btn" == userPermissionArr[i].name){
						Ext.getCmp('addUser').setVisible(true);
					}
					if("usermanage_delete_btn" == userPermissionArr[i].name){
						Ext.getCmp('deleteUser').setVisible(true);
					}
					if("usermanage_update_btn" == userPermissionArr[i].name){
						Ext.getCmp('updateUser').setVisible(true);
					}
					if("usermanage_excel_btn" == userPermissionArr[i].name){
						Ext.getCmp('excelGroupBtn').setVisible(true);
					}
					if("usermanage_userResetPwd_btn" == userPermissionArr[i].name){
						Ext.getCmp('userResetPwd').setVisible(true);
					}
					if("usermanage_excel_userImp_btn" == userPermissionArr[i].name){
						Ext.getCmp('userImport').setVisible(true);
					}
					if("usermanage_excel_userExp_btn" == userPermissionArr[i].name){
						Ext.getCmp('userExport').setVisible(true);
					}
					if("usermanage_excel_userRoleImp_btn" == userPermissionArr[i].name){
					}
					if("usermanage_excel_userImptemplate_btn" == userPermissionArr[i].name){
						Ext.getCmp('userImortTemplateDownload').setVisible(true);
					}
					if("usermanage_excel_userRoleImptemplate_btn" == userPermissionArr[i].name){
					}
			 }    
			 sshframe.user.userStore.loadPage(1);
         }  
	},
	tbar: [
	"姓名",
					{	
    						xtype:'textfield',
    		    			id:'inputSearchName'
    		    		
    		    	},
    		    	{
    	    	    	text :   "查询", 
    	    	    	iconCls: "search-button", 
    	    	    	handler:function(){
    	    	    		var proxy = sshframe.user.userStore.getProxy();
    	   					proxy.setExtraParam("userName",Ext.getCmp('inputSearchName').getValue());
    	   					sshframe.user.userStore.loadPage(1);
    	    			} 
    	    	   	},
		"->",
		{
			text:SystemConstant.resetPwd,
			id:"userResetPwd",
			iconCls: "resetPwd",
			hidden:false,
			disabled:true,
			disabledExpr : "$selectedRows != 1 && $enable == 0",
			handler: function(){
				sshframe.user.resetPwd();
			}
		},
		{
			text:'Excel',
			id:'excelGroupBtn',
    	   	iconCls:'excel-button',
    	   	xtype:'splitbutton',
    	   	hidden:true,
    	   	menu:[
    	   		{
					text:SystemConstant.importUserInfoBtnText,
					id:"userImport",
					iconCls: "import-button",
					hidden:true,
					handler: function(){
						importUserWin.show();
					}
				},
				{
					text:SystemConstant.exportUserInfoBtnText,
					id:"userExport",
					iconCls: "export-button",
					hidden:true,
					handler: function(){
						Ext.MessageBox.wait("", "导出数据", 
								{
									text:"请稍后..."
								}
							);
						$('#exportUsers').submit();
						Ext.MessageBox.hide();
						
					}
				},
    			{
    	   			text:SystemConstant.downloadUserInfoImportTemplateBtnText,
    	   			id:'userImortTemplateDownload',
    	   			iconCls:'excel-button',
    	   			hidden:true,
    	   			handler:function(){
    	   				$('#downloadType').val('user');
    	   				$('#downloadExcel').submit();
    	   			}
    			}
    	   	]
    	},
		{
			text:SystemConstant.addBtnText,
			id:"addUser",
			iconCls: "add-button",
			hidden:true,
			handler: function(){
				sshframe.user.addUser();
			}
		},
		{
			text:SystemConstant.modifyBtnText,
			id:"updateUser",
			disabled: true,
			disabledExpr : "$selectedRows != 1 && $enable == 0",
			iconCls: "edit-button",
			hidden:true,
			handler:function(){
				var rows = sshframe.user.userGrid.getSelectionModel().getSelection();
				cUserIsExist(rows[0].get('userId'));
			}
		},
		{
			text:SystemConstant.deleteBtnText,
			id:"deleteUser",
			disabled:true,
			disabledExpr : "$selectedRows == 0 && $enable == 0",
			hidden:true,
			iconCls: "delete-button",
			handler:function(){
				sshframe.user.deleteUser();
			}
		}
	]
});
 	
importUserRoleWin = new Ext.Window({
   	title: SystemConstant.resource_user_role+SystemConstant.importBtnText,
	closable:true,
	width:400,
	closeAction:'hide',
	height:150,
    modal:true,
	resizable:false,
	layout :"fit",
	buttonAlign:'center',
	html:'<form id="importUserRoleForm" style="padding: 25 0 0 50" action="'+basePath+'/user/uploadExcelToBacthImportUserRole.action" method="post" enctype="multipart/form-data">'+
		 'EXCEL文件：<input type="file" name="uploadAttach" onchange="javascript:$(\'#importUserRoleForm\')[0].filename.value=this.value;"></input><br />'+
		'<span id="uploadTips" style="color: red;"></span>'+
		'<input type="hidden" name="filename" id="filename"></input>'+
		'</form>',
	    buttons:[{
	       text:SystemConstant.uploadBtnText,
	       handler:uploadExcelToImportUserRole
	       },{
	        text:SystemConstant.closeBtnText,
	        handler:function(){
	        importUserRoleWin.hide();
	       }
	      }]
   	    });	

importUserWin = new Ext.Window({
   			title: '用户导入',
			closable:true,
			width:400,
			closeAction:'hide',
			height:150,
			modal:true,
			resizable:false,
			layout :"fit",
			buttonAlign:'center',
			html:'<form id="importUserFormDom" style="padding: 25 0 0 50" action="'+basePath+'/user/uploadExcelToBacthImportUser.action" method="post" enctype="multipart/form-data">'+
			     'EXCEL文件：<input type="file" name="uploadAttach" id="uploadAttach" onchange="javascript:$(\'#filename\').val(this.value);"></input><br />'+
				 '<span id="uploadTip" style="color: red;"></span>'+
				 '<input type="hidden" name="filename" id="filename"></input>'+
				 '</form>',
	             buttons:[{
	            	text:SystemConstant.uploadBtnText,
	                handler:importUserInfo
	                },{
	            	   text:SystemConstant.closeBtnText,
	            	   handler:function(){
	            	   	 importUserWin.hide();
	            	   }
	            	}]
   				});




function importUserRoleInfo(){
            	    	if($('#uploadAttach').val()=="" || null==$('#uploadAttach').val()){
            	    		$('#uploadTip').text('选择Excel文件先！');
            	    		setTimeout("$('#uploadTip').text('')",1500);
            	    		return;
            	    	}
            	    	var fileURL = $('#uploadAttach').val();
	    				var type = fileURL.substring(fileURL.lastIndexOf(".")+1).toLowerCase();
	    				if (type != 'xls'&&type != 'xlsx') {
	    					Ext.Msg.showInfo('文件格式错误,支持[.xls]和[.xlsx]结尾的excel格式！');
	    					$('#uploadAttach').val('');
	    					return;
	    				};
	    				Ext.Ajax.request({
	    					url:'uploadExcelToBacthImportUser.action',   
					        isUpload:true,   
					        form:'importUserRoleForm',   
					        success:function(response, opts){   
			         	      	var result = Ext.decode(response.responseText);
			         	      	var flag = result.msg;
			         	      	if(flag=="importSuccess"){
			         	      		Ext.Msg.show({
		    	    					title:SystemConstant.alertTitle,
		    	    				   	msg: '导入数据成功！',
		    	    				   	icon:Ext.MessageBox.INFO,
		    	    				   	buttons: Ext.Msg.OK,
		    	    				   	fn : function(){
		    	    				   		importUserWin.hide();
		    	    				   		userManageStore.loadPage(1);
		    	    				   	}
		    	    				});
			         	      	}else{
			         	      		Ext.Msg.showError(flag);
			         	      	}
					        } 
	    				});
};

//锁定/解锁用户
sshframe.user.lockupUser = function (userId,enable){
	var currentUserId = "<s:property value='#session.CurrentUser.userId' />" ;
    if(userId == currentUserId ){
        Ext.Msg.showInfo("不允许禁用/解禁用户本身！");
        return false;
    }
     	Ext.Ajax.request({
        	url: basePath+'/user/updateUserEnable.action',
     		params: {
     	   		userId: userId
     	   	},
     		success: function(response, opts) {
     	      	var result = Ext.decode(response.responseText);
     	      	var flag = result.success;
     	      	if(flag){
     	      		refreshUserGrid();
     	      		Ext.Msg.showTip(result.msg);
     	      	}else{
     	      		Ext.Msg.showInfo(result.msg);
     	      	}
     	   	}
    	});
	};

//刷新用户列表
function refreshUserGrid(){
	var store = sshframe.user.userGrid.getStore();
	store.load();
}

function uploadExcelToImportUserRole(){
	if($('#importUserRoleForm')[0].uploadAttach.value=="" || null==$('#importUserRoleForm')[0].uploadAttach.value){
	            	    		$('#uploadTips').text('选择Excel文件先！');
	            	    		setTimeout("$('#uploadTips').text('')",1500);
	            	    		return;
	            	    	}
	var fileURL = $('#importUserRoleForm')[0].uploadAttach.value;
		    				var type = fileURL.substring(fileURL.lastIndexOf(".")+1).toLowerCase();
		    				if (type != 'xls'&&type != 'xlsx') {
		    					Ext.Msg.showInfo('文件格式错误,支持[.xls]和[.xlsx]结尾的excel格式！');
		    					$('#importUserRoleForm')[0].uploadAttach.value = "";
		    					return;
		    				};
	Ext.Ajax.request({
		    					url:'uploadExcelToBacthImportUserRole.action',
						        isUpload:true,   
						        form:'importUserRoleForm',   
						        success:function(response, opts){   
				         	      	var result = Ext.decode(response.responseText);
				         	      	var flag = result.msg;
				         	      	if(flag=="importSuccess"){
				         	      		Ext.Msg.show({
			    	    					title:SystemConstant.alertTitle,
			    	    				   	msg: '导入数据成功！',
			    	    				   	icon:Ext.MessageBox.INFO,
			    	    				   	buttons: Ext.Msg.OK,
			    	    				   	fn : function(){
			    	    				   		importUserRoleWin.hide();
			    	    				   	}
			    	    				});
				         	      	}else{
				         	      		Ext.Msg.showError(flag);
				         	      	}
						        } 
		    				});
	
}

function importUserInfo(){
	if($('#uploadAttach').val()=="" || null==$('#uploadAttach').val()){
		$('#uploadTip').text('请先选择Excel文件');
		setTimeout("$('#uploadTip').text('')",1500);
		return;
	}
	var fileURL = $('#uploadAttach').val();
	var type = fileURL.substring(fileURL.lastIndexOf(".")+1).toLowerCase();
	if (type != 'xls'&&type != 'xlsx') {
		Ext.Msg.showInfo('文件格式错误,支持[.xls]结尾的excel格式！');
		$('#uploadAttach').val('');
		return;
	}
	Ext.MessageBox.wait("", "导入数据", 
			{
				text:"请稍后..."
			}
		);
	Ext.Ajax.request({
		url:basePath+'/user/uploadExcelToBacthImportUser.action',   
        isUpload:true,   
        form:'importUserFormDom',   
        success:function(response, opts){
 	      	var result = Ext.decode(response.responseText);
 	      	var flag = result.msg;
 	      	if(flag=="importSuccess"){
 	      		Ext.MessageBox.hide();
 	      		Ext.Msg.showTip('用户导入成功');
 	      		importUserWin.hide();
 	      		sshframe.user.userGrid.getStore().load();
 	      	}else{
 	      		Ext.Msg.showError(flag);
 	      	}
        } 
	});
	
}

//重置密码
sshframe.user.resetPwd =function(){
	var rows = sshframe.user.userGrid.getSelectionModel().getSelection();
	var currentUserId = "<s:property value='#session.CurrentUser.userId' />" ;
	
	if(rows.length > 0){
        Ext.Msg.confirm('系统提示','你确定要将这'+rows.length+'条数据密码重置为'+ SystemConstant.defaultPassword +'吗?',function(btn){
            if(btn=='yes'){
                var userIds = new Array();
                for(var i=0;i<rows.length;i++){
                    var row=rows[i];
                    userIds.push(row.get('userId'));
                }
                Ext.Ajax.request({
                    url: basePath+'/user/resetUserPassword.action',
                    params: { 
                        userIds: userIds
                    },
                    success: function(response, opts) {
                        var result = Ext.decode(response.responseText);
                        var flag = result.success;
                        if(flag){
                            refreshUserGrid();
                            Ext.Msg.showTip(result.msg);
                            if(rows[0].get('userId') == currentUserId){
                            	setTimeout(function(){
                            		Ext.Ajax.request({
                                        url : basePath+'/user/logout.action',
                                        success : function(res ,options) {
                                            var json = Ext.decode(res.responseText);
                                            if(json.success){
                                                parent.window.location.href=basePath+"/toLogin.action";
                                            }
                                        }
                                    });
                            	},2000);
                            }
                        }else{
                            Ext.Msg.showInfo(result.msg);
                        }
                    }
                });
            }
        });
	}
}


//在点击修改按钮之前，判断当前用户是否存在
function cUserIsExist(userId){
	Ext.Ajax.request({
		url: basePath+'/user/userIsExist.action',
	 		params: { 
	 	   		userId: userId,
	 	   		code:'update'
	 	   	},
			success: function(response, opts) {
		      	var result = Ext.decode(response.responseText);
		      	var flag = result.success;
		      	if(!flag){
		      		Ext.Msg.showInfo(result.msg);
		      	}else{
		      		//打开修改窗口
		      		sshframe.user.updateUser(userId);
		      	}
		   	}
	});
}


/**
 * 调用后台删除用户
 */
sshframe.user.deleteUser = function() {
 	var rows = sshframe.user.userGrid.getSelectionModel().getSelection();
 	if(rows.length <= 0){
 		Ext.Msg.showInfo('请先选择要删除的数据');
		return;
 	}
 	
 	Ext.Msg.confirm(SystemConstant.alertTitle,SystemConstant.deleteMsgStart+rows.length+SystemConstant.deleteMsgEnd,function(btn){
 		if(btn=='yes'){
 			var currentUserId = "<s:property value='#session.CurrentUser.userId' />"; 
 			var userIds = "";
			if(rows.length==1){
				if(rows[0].get('userId') == currentUserId ){
					Ext.Msg.showInfo('不允许删除用户本身');
					return;
				}
				userIds = rows[0].get('userId');
			}else{
				for(var i=0;i<rows.length;i++){
					userIds += ',' + rows[i].get('userId');
					if(rows[i].get('userId') == currentUserId ){
						Ext.Msg.showInfo('不允许删除用户本身');
						return;
					}
				}
				userIds = userIds.substring(1);
			}
			
			Ext.Ajax.request({
				url: basePath+'/user/userIsExist.action',
			 		params: { 
			 	   		userId: userIds,
			 	   		code:'delete'
			 	   	},
					success: function(response, opts) {
				      	var result = Ext.decode(response.responseText);
				      	var flag = result.success;
				      	if(flag){
				      		sshframe.user.userGrid.getStore().loadPage(1);
				      		Ext.Msg.showInfo(result.msg);
				      	}else{
				      		$.post(
				      				basePath+'/user/deleteUser.action',
		    						{userIds : userIds	},
		    						function(data){
										Ext.Msg.showTip('删除用户成功');
										sshframe.user.userStore.loadPage(1);
		    						}
		    					);
				      	}
				   	}
			});
			
 		}
 	});
}

/**
 * 调用后台添加用户
 */
sshframe.user.addUser = function() {
	sshframe.user.UserWin.setTitle('添加');
	var basicForm = sshframe.user.UserWin.down('form').getForm();
	basicForm.reset();
	basicForm.url = basePath+'/user/addUser.action';
	basicForm.findField('user.password').setVisible(true);
	basicForm.findField('confirmPassword').setVisible(true);
	basicForm.findField('user.password').setDisabled(false);
	basicForm.findField('confirmPassword').setDisabled(false);
	basicForm.findField('user.username').setDisabled(false);
	sshframe.user.UserWin.show();
}


/**
 * 调用后台修改用户
 */
sshframe.user.updateUser = function(userId) {
	sshframe.user.UserWin.setTitle('修改');
	var basicForm = sshframe.user.UserWin.down('form').getForm();
	basicForm.reset();
	basicForm.url = basePath + '/user/editUser.action';
	basicForm.findField('user.userId').setValue(userId);
	basicForm.findField('user.password').setVisible(false);
	basicForm.findField('confirmPassword').setVisible(false);
	basicForm.findField('user.password').setDisabled(true);
	basicForm.findField('confirmPassword').setDisabled(true);
	basicForm.findField('user.username').setDisabled(true);
	basicForm.load({
		url : basePath + '/user/getUserByIdForUpdate.action',
		params : {
			id : userId
		}
	});
	sshframe.user.UserWin.show();
}


