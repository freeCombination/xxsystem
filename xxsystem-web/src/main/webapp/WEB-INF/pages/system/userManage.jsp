<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>用户管理</title>
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
<div id="divUser" style="width: 100%;height: 100%; overflow: hidden;margin: 0px;padding:0px;"></div>
</body>
</html>

<script type="text/javascript">
Ext.BLANK_IMAGE_URL = '${ctx}/images/s.gif';

Ext.QuickTips.init();
Ext.namespace('user'); 

user.manage = function() { 
	var grid;
	var tree;
	var rolePanel;
	var permissionStore;
	var userPermissionPanel;

	//得到grid中选择记录的id,例如1,2,3
	function getSelectRecordIds(){
	    var records = user.manage.grid.getSelectionModel().getSelections();
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

	//修改用户权限，刷新用户权限列表
	function refreshPermission(userId,cursor){
		Ext.Ajax.request({ 
 	 		url:'${ctx}/system/getUserPermissionList.do',
 	 		params:{id:userId},
 	        success : function(res ,options) {
 	 			var objs= Ext.decode(res.responseText);
 	 			userPermissionPanel.getStore().proxy = new Ext.data.PagingMemoryProxy(objs); 
 	 			userPermissionPanel.getStore().load({params:{start:cursor,limit:12}});
 			}, 
 	        failure : function(response) {
 	         	Ext.tooltip.msg('no', '系统繁忙，请稍后再试！');
 	        }
 		}); 
	}

	function getUnitTreeLoader(){
		var unitTreeLoader = new Ext.tree.TreeLoader({
			preloadChildren: true,
			clearOnLoad: false,
			url : '${ctx}/system/getUnitTreeList.do'
		});
		
		unitTreeLoader.on('beforeload', function(treeLoader, node) {
			treeLoader.baseParams = {
				nodeId : node.attributes.nodeId
			};
		}, this);
		
		return unitTreeLoader;
	}

    // 公共空间 
    return { 
        setTree : function (tree){
			this.tree = tree;
        },
        
        setGrid : function (grid){
			this.grid = grid;
        },
        
        addUnit : function (){
        	var sort = 0;
        	var curNode = this.tree.getSelectionModel().getSelectedNode();
        	if(curNode == null){
        		curNode = this.tree.getRootNode();
        	}
        	
			var child  = curNode.firstChild;
			while(child){
				sort++;
				child = child.nextSibling;
			}
			
			var addUnitForm = new Ext.form.FormPanel({
			    bodyStyle :'padding:20 0 0 0',
				border :false,
			    layout : 'form', 
			    labelAlign:'right',
			    labelWidth:80,
			    defaultType:'textfield',
			    defaults:{
			        width:200,
			      	allowBlank:false,
			      	msgTarget : 'side'//在该组件的下面显示错误提示信息   取值为'qtip','side','title','under'
			    },
			    items:[{
			        xtype:'hidden',
			        name:'unitVo.parentId',
			        value: curNode.attributes.nodeId
			    },{
			        xtype:'hidden',
			        name:'unitVo.sort',
			        value: sort
			    },{
			        xtype:'hidden',
			        name:'unitVo.number',
			        value: curNode.attributes.nodeId
			    }
			    ,{
			        fieldLabel:'单位名称',
			        name:'unitVo.unitName',
			        maxLength: 50,
			        blankText : '单位名称不能为空' 
			    },{
			        fieldLabel:'单位地址',
			        name:'unitVo.unitAddress',
			        blankText : '单位地址不能为空' ,
			        maxLength: 200,
			        allowBlank:true
			    },{
			        fieldLabel:'单位电话',
			        name:'unitVo.unitPhone',
			        
			       
			        maxLength: 20,
			        allowBlank:true
			    }]
			});

			var win = new Ext.Window({
				title: '添加单位',
		        closable:true,
		        width:340,
		        height:190,
		        modal:true,
		        plain:true,
		        layout:"fit",
		        resizable:false,
		        items: [
					addUnitForm
		        ],
		        buttonAlign:'center', 
		    	buttons:[{
		    		text:'保存',
		    	    handler:function(){
			    		if(!addUnitForm.getForm().isValid()){
		        	    	return;
		        	    }
			        	    
						addUnitForm.getForm().submit({
			                url:'addUnit.do',
			                success:function(form,action){
			                	Ext.tooltip.msg('yes', action.result.msg);
					 			curNode.reload();
		              	     	win.close();
		              	    },
			              	failure:function(form,action){
		              	    	Ext.tooltip.msg('no', action.result.msg);
			              	}
		                });
		    		}
	    	    },{
	    	       	text:'重置',
	    	       	handler : function(){
						addUnitForm.getForm().reset();
	    	    	}
	    	    },{
	    	       	text:'关闭',
	    	       	handler:function(){
	    	    		win.close();
	    	    	}
		    	}]
		    });
			win.show();
        },
        
        updateUnit : function(){
        	var curNode = this.tree.getSelectionModel().getSelectedNode();
        	var updateUnitForm = new Ext.form.FormPanel({
        		bodyStyle :'padding:20 0 0 0',
			    border :false,
			    layout : 'form', 
			    labelAlign:'right',
			    labelWidth:80,
			    defaultType:'textfield',
			    defaults:{
			        width:200,
			      	allowBlank:false,
			      	msgTarget : 'side'//在该组件的下面显示错误提示信息   取值为'qtip','side','title','under'
			    },
			    items:[{
			        xtype:'hidden',
			        name:'unitVo.id',
			        value: curNode.attributes.nodeId
			    },{
			        fieldLabel:'单位名称',
			        name:'unitVo.unitName',
			        value : curNode.attributes.text,
			        maxLength: 50,
			        
			       
			        blankText : '单位名称不能为空' 
			    },{
			        fieldLabel:'单位地址',
			        name:'unitVo.unitAddress',
			        value : curNode.attributes.unitAddress,
			        maxLength: 200,
			        
			       
			        blankText : '单位地址不能为空' ,
			        allowBlank:true
			    },{
			        fieldLabel:'单位电话',
			        name:'unitVo.unitPhone',
			        value : curNode.attributes.unitPhone,
			        
			       
			        maxLength: 20,
			        allowBlank:true
			    }]
			});

        	var win = new Ext.Window({
				title: '修改单位',
		        closable:true,
		        width:340,
		        height:190,
		        modal:true,
		        plain:true,
		        layout :"fit",
		        resizable:false,
		        items: [
					updateUnitForm
		        ],
		        buttonAlign:'center', 
		    	buttons:[{
		    		text:'保存',
		    	    handler:function(){
			    		if(!updateUnitForm.getForm().isValid()){
		        	    	return;
		        	    }
			        	    
			    		updateUnitForm.getForm().submit({
			                url:'updateUnit.do',
			                success:function(form,action){
			                	Ext.tooltip.msg('yes', action.result.msg);
			                	curNode.parentNode.reload();
		              	     	win.close();
		              	    },
			              	failure:function(form,action){
		              	    	Ext.tooltip.msg('no', action.result.msg);
			              	}
		                });
		    		}
	    	    },{
	    	       	text:'重置',
	    	       	handler : function(){
	    	    		updateUnitForm.getForm().reset();
	    	    	}
	    	    },{
	    	       	text:'关闭',
	    	       	handler:function(){
	    	    		win.close();
	    	    	}
		    	}]
		    });
			win.show();
        },
        
        viewUnit : function(){
        	var win = new Ext.Window({
				title: '查看单位',
		        closable:true,
		        width:340,
		        height:190,
		        modal:true,
		        plain:false,
		        autoScroll : true,
		        bodyStyle: 'background-color: white;',
		        layout :"fit",
		        resizable:false,
		        buttonAlign:'center', 
		    	buttons:[{
	    	       	text:'关闭',
	    	       	handler:function(){
	    	    		win.close();
	    	    	}
		    	}]
		    });

        	win.show();

        	var tpl = new Ext.XTemplate( 
       			'<table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;font-family: \'宋体\', \'sans-serif\'; border:0px;margin:0px;line-height:23px;">',
       			'<tr>',
       			'<td width="25%" style="border:1px dotted #999;text-align:right; padding-right:5px;padding-top:5px;padding-bottom:5px;">',
       		    '单位名称：', 
       			'</td>',
       			'<td width="75%" style="border-top: #999 1px dotted;border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;padding-top:5px;padding-bottom:5px;">',
       		    '{unitName}&nbsp;', 
       			'</td>',
       			'</tr>',
       			'<tr>',
       			'<td style="border-left: #999 1px dotted;border-right: #999 1px dotted;border-bottom: #999 1px dotted;text-align:right; padding-right:5px;padding-top:5px;padding-bottom:5px;">',
       		    '单位描述：', 
       		    '</td>',
       			'<td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;padding-top:5px;padding-bottom:5px;">',
       		    '{remarks}&nbsp;', 
       		    '</td>',
       			'</tr>',
       			'<table>' 
       		); 

        	var curNode = this.tree.getSelectionModel().getSelectedNode();
			var data = new Object();
			data.unitName = curNode.attributes.text;
			data.remarks = curNode.attributes.remarks;
			tpl.overwrite(win.body, data); 
        },
        
        deleteUnit : function (){
        	var curNode = this.tree.getSelectionModel().getSelectedNode();
        	Ext.MessageBox.confirm('信息提示：', '确定要删除'+curNode.attributes.text+'吗?', function(btn){
           	    if (btn == 'yes'){
           	    	Ext.Ajax.request({
           	    	   	url: 'deleteUnits.do',
           	    	   	params: { ids: curNode.attributes.nodeId },
           	    	 	success: function(response, opts) {
           	    	      	var result = Ext.decode(response.responseText);
           	    	      	var flag = result.success;
           	    	      	if(flag){
           	    	      		Ext.tooltip.msg('yes', result.msg);
               	    	     	curNode.parentNode.reload();
               	    	     	user.manage.grid.getStore().reload();
           	    	      	} else {
           	    	      		Ext.tooltip.msg('no', result.msg);
           	    	      	}
           	    	   	}
           	    	});
           	    }
           	});
        },

        addUser : function(){
			
        	var treeLoader = getUnitTreeLoader();
          	var unitTreeCombo =  new QM.ui.TreeCombo({
          		fieldLabel:'单位',
                lazyInit: true,
                height:180,
                ignoreFolder:false,
                editable: false, //如果树中结点不是一次全部加载请请此项设为false
                tree: {
        	  		autoScroll:true,
        	  		height:180,
        	  		lines: false,
        	  		useArrows: true,
                    loader : treeLoader,
                    root: {
            			nodeId : 0, 
            	        nodeType: 'async',
            	      	text: '单位树',
            	    	leaf:false,
            	    	draggable: false
            	    },
                    listeners:{
        		    	'click' : function(node, event) {
            	    		addUserForm.getForm().findField("user.unit.id").setValue(node.attributes.nodeId); 
        				},'expand':function(){
        					alert("nihao");
        				}
        			}
        		}
          	}); 

          	Ext.apply(Ext.form.VTypes,{
	    	    addValidatePassword:function(val,field){//val指这里的文本框值，field指这个文本框组件
	    	        return (val == addUserForm.getForm().findField("password").getValue());
	    	    }
	    	});	 
          	
        	var addUserForm = new Ext.form.FormPanel({
			    bodyStyle :'padding:10 0 0 0',
				border :false,
				autoScroll : true,
				layout : 'form', 
			    labelAlign:'right',
			    fileUpload : true,
			    items:[{
					layout : 'column',
					border : false,
					ctxCls : 'x-plain',
					enctype: 'multipart/form-data',   
					items : [{
						columnWidth : .5,
						layout : 'form',
						ctxCls : 'x-plain',
						border : false,
					    labelWidth:80,
						defaultType:'textfield',
						defaults:{
				    		width:200,
							msgTarget : 'side',
							allowBlank:false
						},
						items : [{
					    	fieldLabel:'账号',
					        name:'user.loginName',
					        maxLength: 50,
					      /*  vtype:'alphanum',
	    			        vtypeText:"只能输入字母和数字",*/
					        blankText : '账号不能为空',
					        plugins : [Ext.ux.plugins.RemoteValidator], 
	    			        rvOptions : { 
	    			        	url : 'validateAddLoginName.do'
	    			    	}  
						},{
					    	fieldLabel:'密码',
					    	inputType: 'password',
					        name:'password',
					        maxLength : 15,
					        value : '111111',
					        vtype:'alphanum',
					        
					       
	    			        vtypeText:"只能输入字母和数字",
					        blankText : '密码不能为空' 
						},{
					    	fieldLabel:'重复密码',
					    	inputType: 'password',
					        name:'password1',
					        maxLength : 15,
					        value : '111111',
					        vtype:'alphanum',
					        
	    			        vtypeText:"只能输入字母和数字",
					        blankText : '重复密码不能为空',
					        vtype:"addValidatePassword",//自定义的验证类型
	    		            vtypeText:"两次输入密码不一致！" 
						},{
					    	fieldLabel:'姓名',
					        name:'user.userName',
					        maxLength: 100,
					        blankText : '登录名不能为空' 
						}/*,{
					    	fieldLabel:'性别',
					        hiddenName:'user.gender',
					        xtype:'combo',
					        store:new Ext.data.SimpleStore({
					           fields:['value','text'],
					           data:[[0,'男'],[1,'女']]
					        }),
					        emptyText:'请选择',
					        mode:'local',
					        triggerAction:'all',
					        displayField:'text',
					        valueField:'value',
					        typeAhead : true,
					        value : 0,   
					        blankText : '性别不能为空',   
					        selectOnFocus : true,   
					        editable : false
					    }*/,{
					    	fieldLabel:'工号',
					        name:'user.jobNumber',
					        maxLength: 20,
					        allowBlank:true
					       // blankText : '工号不能为空' 
						},{
					    	fieldLabel:'职务',
					    	maxLength: 50,
					        name:'user.job',
					        blankText : '职务不能为空' 
						},
							unitTreeCombo
						,{
					        xtype:'hidden',
					        name:'user.unit.id'
					    },{
							xtype: 'datefield',
					    	fieldLabel:'出生日期',
					        name:'user.birth',
					        format: 'Y-m-d',
					        editable : false,
					        allowBlank:true
						},{
							fieldLabel : '照片预览',
							xtype : 'box',
							id : 'addImgPreview',
							allowBlank:true,
							width : 110,
							height : 130,
							autoEl : {
							    tag : 'img',
							    src : '${ctx}/images/photo.jpg', 
							    style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)'
							}
						},{
							xtype : 'textfield',
							id : 'txtAddUserImage',
							allowBlank:true,
							name : 'userImage',
							inputType : 'file',
							fieldLabel : '用户照片',
							listeners : {
								'render':function(){
									var userImageCmp = Ext.get('txtAddUserImage');
								    var divUserDom=Ext.get('divUser').dom; 
									userImageCmp.on('change',function(field,newValue,oldValue){
								        var url = '';
								        var txtUserImage = Ext.get('txtAddUserImage').dom;
										if (window.navigator.userAgent.indexOf("MSIE")>=1){
											if(navigator.appVersion.match(/9./i)){
												txtUserImage.select();
												txtUserImage.blur();
			        							url = document.selection.createRange().text; 
			        							if(url==''){
			        								txtUserImage.select();
			        								divUserDom.focus();
			        								url = document.selection.createRange().text; 
			        							}
			        							if(url==''){
			        								url=txtUserImage.value;
			        							}
			    							}
								     	}else if(window.navigator.userAgent.indexOf("Firefox")>=1){
								     		if(txtUserImage.files){
								     			url = txtUserImage.files.item(0).getAsDataURL();
								     		}
								     		url = txtUserImage.value;
								     	}

								     	if(url == ''){
											return;
									    }

										var AllowExt=".jpg|.gif|.bmp|.jpeg|.png" //允许上传的文件类型
										var FileExt= url.substring(url.lastIndexOf(".")+1).toLowerCase();
										if(AllowExt.indexOf(FileExt)==-1){ //判断文件类型是否允许上传
										    Ext.tooltip.msg('no', '仅支持jpg、bmp、gif、png、jpeg格式！');
										    return;
										}
								     	      
								        if(Ext.isIE){
						                     var image = Ext.get('addImgPreview').dom;  
									         image.src = Ext.BLANK_IMAGE_URL;
									         image.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = url; 
								        }else{
									         //支持FF
									         Ext.get('addImgPreview').dom.src =Ext.get('txtAddUserImage').dom.files.item(0).getAsDataURL();
								        }
									},this);
						    	}
							}
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						border : false,
						labelWidth:80,
						ctxCls : 'x-plain',
						defaultType:'textfield',
						defaults:{
				    		width:200,
							msgTarget : 'side'
						},
						items : [{
					    	fieldLabel:'身份证',
					        name:'user.identityCard',
					        maxLength: 30,
					        
					       
					        allowBlank:true
						},{
					    	fieldLabel:'手机',
					        name:'user.cellPhone',
					        maxLength: 20,
					        
					       
					        allowBlank:true
						},{
					    	fieldLabel:'家庭电话',
					    	maxLength: 20,
					        name:'user.homePhone',
					        
					       
					        allowBlank:true
						},{
					    	fieldLabel:'是否负责人',
					        hiddenName:'user.isPrincipal',
					        xtype:'combo',
					        store:new Ext.data.SimpleStore({
					           fields:['value','text'],
					           data:[[0,'否'],[1,'是']]
					        }),
					        emptyText:'请选择',
					        mode:'local',
					        triggerAction:'all',
					        displayField:'text',
					        valueField:'value',
					        value: 0,
					        typeAhead : true,   
					        blankText : '是否负责人不能为空', 
					        selectOnFocus : true,   
					        editable : false
						},{
					    	fieldLabel:'政治面貌',
					        name:'user.political',
					        maxLength: 10,
					        
					       
					        allowBlank:true
						},{
					    	fieldLabel:'家庭地址',
					    	maxLength: 100,
					        name:'user.address',
					        
					       
					        allowBlank:true
						},{
					    	fieldLabel:'所学专业',
					    	maxLength: 300,
					        name:'user.majorIn',
					        
					       
					        allowBlank:true
						},{
					    	fieldLabel:'办公电话',
					        name:'user.officePhone',
					        maxLength: 20,
					        
					       
					        allowBlank:true
						},{
					    	fieldLabel:'电子邮件',
					        name:'user.email',
					        maxLength: 100,
					        allowBlank:true,
					        
					        vtype : "email",
							vtypeText : "不是有效的邮箱地址"
						},{
							fieldLabel : '签字预览',
							xtype : 'box',
							id : 'addSignerImg',
							width : 110,
							height : 130,
							autoEl : {
							    tag : 'img',
							    src : '${ctx}/images/signer.jpg', 
							    style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)'
							}
						},{
							xtype : 'textfield',
							id : 'txtAddSignerImage',
							name : 'signerImage',
							inputType : 'file',
							fieldLabel : '用户签字',
							listeners : {
								'render':function(){
									var signerImageCmp = Ext.get('txtAddSignerImage');
								    var txtSignerImage = Ext.get('txtAddSignerImage').dom;
								    var divUserDom = Ext.get('divUser').dom;
									signerImageCmp.on('change',function(field,newValue,oldValue){
										  var url = '';
										if(navigator.appVersion.match(/9./i)){
											txtSignerImage.select();
											txtSignerImage.blur();
		        							url = document.selection.createRange().text; 
		        							if(url==''){
		        								txtSignerImage.select();
		        								divUserDom.focus();
		        								url = document.selection.createRange().text; 
		        							}
		        							if(url==''){
		        								url=txtSignerImage.value;
		        							}
		    							}else if(window.navigator.userAgent.indexOf("Firefox")>=1){
								     		if(txtSignerImage.files){
								     			url = txtSignerImage.files.item(0).getAsDataURL();
								     		}
								     		url = txtSignerImage.value;
								     	}

								     	if(url == ''){
											return;
									    }

										var AllowExt=".jpg|.gif|.bmp|.jpeg|.png" //允许上传的文件类型
										var FileExt= url.substring(url.lastIndexOf(".")+1).toLowerCase();
										if(AllowExt.indexOf(FileExt)==-1){ //判断文件类型是否允许上传
										    Ext.tooltip.msg('no', '仅支持jpg、bmp、gif、png、jpeg格式！');
										    return;
										}
								     	      
								        if(Ext.isIE){
						                     var image = Ext.get('addSignerImg').dom;  
									         image.src = Ext.BLANK_IMAGE_URL;
									         image.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = url; 
								        }else{
									         //支持FF
									         Ext.get('addSignerImg').dom.src =Ext.get('txtAddSignerImage').dom.files.item(0).getAsDataURL();
								        }
									},this);
						    	}
							}
						}]
					}]
				}]
			});

  		     
        	var win = new Ext.Window({
				title: '添加用户',
		        closable:true,
		        width:660,
		        height:360,
		        modal:true,
		        plain:true,
		        resizable:false,
		        layout :"fit",
		        buttonAlign:'center', 
		        items: [
					addUserForm
		        ],
		    	buttons:[{
		    		text:'保存',
		    	    handler:function(){
			    		if(!addUserForm.getForm().isValid()){
		        	    	return;
		        	    }

			    		//验证图片格式
			    		var url = '';
				        var txtUserImage = Ext.get('txtAddUserImage').dom;
				        var divUserDom = Ext.get('divUser').dom;
						if (window.navigator.userAgent.indexOf("MSIE")>=1){
							if(navigator.appVersion.match(/9./i)){
								txtUserImage.select();
								txtUserImage.blur();
    							url = document.selection.createRange().text; 
    							if(url==''){
    								txtUserImage.select();
    								divUserDom.focus();
    								url = document.selection.createRange().text; 
    							}
    							if(url==''){
    								url=txtUserImage.value;
    							}
							}
				     	}else if(window.navigator.userAgent.indexOf("Firefox")>=1){
				     		if(txtUserImage.files){
				     			url = txtUserImage.files.item(0).getAsDataURL();
				     		}
				     		url = txtUserImage.value;
				     	}

				     	if(url != ''){
							var AllowExt=".jpg|.gif|.bmp|.jpeg|.png" //允许上传的文件类型
							var FileExt= url.substring(url.lastIndexOf(".")+1).toLowerCase();
							if(AllowExt.indexOf(FileExt)==-1){ //判断文件类型是否允许上传
							    Ext.tooltip.msg('no', '仅支持jpg、bmp、gif、png、jpeg格式！');
							    return;
							}
					    }

				     	//验证签名图片格式
			    		var urlSignerImage = '';
				        var txtAddSignerImage = Ext.get('txtAddSignerImage').dom;
						if (window.navigator.userAgent.indexOf("MSIE")>=1){
							if(navigator.appVersion.match(/9./i)){
								txtAddSignerImage.select();
								txtAddSignerImage.blur();
								urlSignerImage = document.selection.createRange().text; 
    							if(urlSignerImage==''){
    								txtAddSignerImage.select();
    								divUserDom.focus();
    								urlSignerImage = document.selection.createRange().text; 
    							}
    							if(urlSignerImage==''){
    								urlSignerImage=txtAddSignerImage.value;
    							}
							}
				     	}else if(window.navigator.userAgent.indexOf("Firefox")>=1){
				     		if(txtAddSignerImage.files){
				     			urlSignerImage = txtAddSignerImage.files.item(0).getAsDataURL();
				     		}
				     		urlSignerImage = txtAddSignerImage.value;
				     	}

				     	if(urlSignerImage != ''){
							var AllowExtSignerImage=".jpg|.gif|.bmp|.jpeg|.png" //允许上传的文件类型
							var FileExtSignerImage= urlSignerImage.substring(urlSignerImage.lastIndexOf(".")+1).toLowerCase();
							if(AllowExtSignerImage.indexOf(FileExtSignerImage)==-1){ //判断文件类型是否允许上传
							    Ext.tooltip.msg('no', '仅支持jpg、bmp、gif、png、jpeg格式！');
							    return;
							}
					    }

				     	var unitId = addUserForm.getForm().findField("user.unit.id").getValue();

					    if(unitId == 0){
					    	Ext.tooltip.msg('no', '请选择单位');
					    	return;
						}
			        	    
			    		addUserForm.getForm().submit({
			                url:'addUser.do',
			                success:function(form,action){
			                	Ext.tooltip.msg('yes', action.result.msg);
			                	user.manage.grid.getStore().reload();
		              	     	win.close();
		              	    },
			              	failure:function(form,action){
		              	    	Ext.tooltip.msg('no', action.result.msg);
			              	}
		                });
		    		}
	    	    },{
	    	       	text:'重置',
	    	       	handler : function(){
	    	    		addUserForm.getForm().reset();
	    	    		addUserForm.getForm().findField("user.unit.id").setValue(0);
	    	    	}
	    	    },{
	    	       	text:'关闭',
	    	       	handler:function(){
	    	    		win.close();
	    	    	}
		    	}]
		    });
			win.show();
        },

        //修改用户
        updateUser : function (){
    	   	var ids = getSelectRecordIds();
			if(ids==false){
				Ext.tooltip.msg('no', '请选择要操作的行！');
	          	return;
			}if(ids.indexOf(',')!=-1){
				Ext.tooltip.msg('no', '只能选择一条记录！');
	          	return;
			}

			var row = user.manage.grid.getSelectionModel().getSelections()[0];

			//-------------------下拉单位树开始------------------------
          	var unitTreeCombo = new Ext.form.ComboBox({
				fieldLabel : '单位',
				value : row.get('unit'),
				store : new Ext.data.SimpleStore({
					fields : [],
					data : [[]]
				}),
				
				editable : false,
				mode : 'local',
				triggerAction : 'all',
				height : 200,
				tpl : '<div style="height:200px"><div id="divUpdateUnitTree" style="overflow: auto;"></div></div>',
				selectedClass : '',
				onSelect : Ext.emptyFn,
				emptyText : '请选择',
				listeners:{
    		    	'expand' : function(node, event) {
						unitTreepanel.render('divUpdateUnitTree');
						unitTreepanel.expandAll();
    				}
    			}
			});

          	var treeLoader = getUnitTreeLoader();

			var unitTreepanel = new Ext.tree.TreePanel({
				border : false,
				autoScroll : true,
				animate : true,
				animCollapse : true,
				rootVisible : false,
				lines : false,
				height : 200,
				useArrows : true,
				root : {
					nodeId : 0,
					nodeType : 'async',
					text : '根结点',
					leaf : false,
					singleClickExpand : false,
					draggable : false,
					cls : "folder"
				},
				loader : treeLoader,
				listeners : {
					'click' : function(node, event) {
						unitTreeCombo.setValue(node.text);
						updateUserForm.getForm().findField("user.unit.id").setValue(node.attributes.realId);
					}
				}
			});
			//-------------------下拉单位树结束------------------------
			var updateUserForm = new Ext.form.FormPanel({
			    bodyStyle :'padding:10 0 0 0',
				border :false,
				autoScroll : true,
				layout : 'form', 
			    labelAlign:'right',
			    fileUpload : true,
			    items:[{
					layout : 'column',
					border : false,
					ctxCls : 'x-plain',
					enctype: 'multipart/form-data',   
					items : [{
						columnWidth : .5,
						layout : 'form',
						ctxCls : 'x-plain',
						border : false,
					    labelWidth:80,
						defaultType:'textfield',
						defaults:{
				    		width:200,
							msgTarget : 'side',
							allowBlank:false
						},
						items : [{
		    		        xtype:'hidden',
		    		        name:'user.id',
		    		        value : row.get('id')
		    		    },{
					    	fieldLabel:'账号',
					        name:'user.loginName',
					        maxLength: 50,
					        value : row.get('loginName'),
					       
					        //vtype:'alphanum',
	    			        //vtypeText:"只能输入字母和数字",
					        blankText : '账号不能为空',
					        readOnly : true,
					        
					        plugins : [Ext.ux.plugins.RemoteValidator], 
	    			        rvOptions : { 
	    			        	url : 'validateUpdateLoginName.do?oldLoginName='+row.get("loginName")
	    			    	}  
						},{
					    	fieldLabel:'姓名',
					        name:'user.userName',
					        maxLength: 100,
					        value : row.get('userName'),
					       
					        
					        blankText : '登录名不能为空' 
						}/*,{
					    	fieldLabel:'性别',
					        hiddenName:'user.gender',
					        xtype:'combo',
					        store:new Ext.data.SimpleStore({
					           fields:['value','text'],
					           data:[[0,'男'],[1,'女']]
					        }),
					        value : row.get('gender'),
					        emptyText:'请选择',
					        mode:'local',
					        triggerAction:'all',
					        displayField:'text',
					        valueField:'value',
					        typeAhead : true,
					        blankText : '性别不能为空',   
					        selectOnFocus : true,   
					        editable : false,
					        readOnly : true
					    }*/,{
					    	fieldLabel:'政治面貌',
					    	maxLength: 10,
					        name:'user.political',
					       
					        
					        value : row.get('political'),
					        allowBlank:true
						},{
					    	fieldLabel:'工号',
					        name:'user.jobNumber',
					        value : row.get('jobNumber'),
					       
					        
					        maxLength: 20,
					        allowBlank:true
						},{
					    	fieldLabel:'职务',
					        name:'user.job',
					        maxLength: 50,
					       
					        
					        value : row.get('job'),
					        allowBlank:true
						},
							unitTreeCombo
						,{
					        xtype:'hidden',
					        value : row.get('unitId'),
					        name:'user.unit.id'
					    },{
							xtype: 'datefield',
					    	fieldLabel:'出生日期',
					        name:'user.birth',
					        value : row.get('birth'),
					        format: 'Y-m-d',
					        editable : false,
					        allowBlank:true
						},{
							fieldLabel : '照片预览',
							xtype : 'box',
							id : 'updateImgPreview',
							allowBlank:true,
							width : 110,
							height : 130,
							autoEl : {
							    tag : 'img',
							    src : '${ctx}/images/photo.jpg', 
							    style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)'
							}
						},{
							xtype : 'textfield',
							id : 'txtUpdateUserImage',
							name : 'userImage',
							inputType : 'file',
							allowBlank:true,
							fieldLabel : '用户照片',
							listeners : {
								'render':function(){
									var userImageCmp = Ext.get('txtUpdateUserImage');
									var pictureURL = row.get('pictureURL');
									if(pictureURL){
										Ext.Ajax.request({
						   	       			url: '${ctx}/system/isFileExit.do',
						   	       	    	params: { filePath : pictureURL},
						   	       	    	success: function(response, opts) {
						   	       	    		var result = Ext.decode(response.responseText);
							   	       	    	var flag = result.success;
										 	 	if(flag){
										 	 		pictureURL = '${ctx}/' + pictureURL;
													pictureURL = pictureURL.replace(/\\/g,"/");
										 	 		
										 	 		if(Ext.isIE){
									                     var image = Ext.get('updateImgPreview').dom;  
												         image.src = Ext.BLANK_IMAGE_URL;
												         image.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = pictureURL; 
											        }else{
												        Ext.get('updateImgPreview').dom.src = pictureURL;
											        }
											 	}
						   	       	    	}
					   	       	    	});
									}

									userImageCmp.on('change',function(field,newValue,oldValue){
								        var txtUserImage = Ext.get('txtUpdateUserImage').dom;
								        var divUserDom = Ext.get('divUser').dom;
								        var url = '';
										if (window.navigator.userAgent.indexOf("MSIE")>=1){
											if(navigator.appVersion.match(/9./i)){
												txtUserImage.select();
												txtUserImage.blur();
			        							url = document.selection.createRange().text; 
			        							if(url==''){
			        								txtUserImage.select();
			        								divUserDom.focus();
			        								url = document.selection.createRange().text; 
			        							}
			        							if(url==''){
			        								url=txtUserImage.value;
			        							}
			    							}
								     	}else if(window.navigator.userAgent.indexOf("Firefox")>=1){
								     		if(txtUserImage.files){
								     			url = txtUserImage.files.item(0).getAsDataURL();
								     		}
								     		url = txtUserImage.value;
								     	}

								     	if(url == ''){
											return;
									    }

										var AllowExt=".jpg|.gif|.bmp|.jpeg|.png" //允许上传的文件类型
										var FileExt= url.substring(url.lastIndexOf(".")+1).toLowerCase();
										if(AllowExt.indexOf(FileExt)==-1){ //判断文件类型是否允许上传
										    Ext.tooltip.msg('no', '仅支持jpg、bmp、gif、png、jpeg格式！');
										    return;
										}
										
								        if(Ext.isIE){
						                     var image = Ext.get('updateImgPreview').dom;  
									         image.src = Ext.BLANK_IMAGE_URL;
									         image.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = url; 
								        }else{
									         //支持FF
									         Ext.get('updateImgPreview').dom.src =Ext.get('txtUpdateUserImage').dom.files.item(0).getAsDataURL();
								        }
									},this);
						    	}
							}
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						border : false,
						labelWidth:80,
						ctxCls : 'x-plain',
						defaultType:'textfield',
						defaults:{
				    		width:200,
							msgTarget : 'side'
						},
						items : [{
					    	fieldLabel:'身份证',
					        name:'user.identityCard',
					        value : row.get('identityCard'),
					        maxLength: 30,
					       
					        
					        allowBlank:true
						},{
					    	fieldLabel:'手机',
					    	maxLength: 20,
					        name:'user.cellPhone',
					        value : row.get('cellPhone'),
					       
					        
					        allowBlank:true
						},{
					    	fieldLabel:'家庭电话',
					        name:'user.homePhone',
					        maxLength: 20,
					       
					        
					        value : row.get('homePhone'),
					        allowBlank:true
						},{
					    	fieldLabel:'是否负责人',
					        hiddenName:'user.isPrincipal',
					        xtype:'combo',
					        value : row.get('isPrincipal'),
					        store:new Ext.data.SimpleStore({
					           fields:['value','text'],
					           data:[[0,'否'],[1,'是']]
					        }),
					        emptyText:'请选择',
					        mode:'local',
					        triggerAction:'all',
					        displayField:'text',
					        valueField:'value',
					        typeAhead : true,   
					        blankText : '是否负责人不能为空', 
					        selectOnFocus : true,   
					        editable : false
						},{
					    	fieldLabel:'家庭地址',
					        name:'user.address',
					        maxLength: 100,
					       
					        
					        value : row.get('address'),
					        allowBlank:true
						},{
					    	fieldLabel:'所学专业',
					        name:'user.majorIn',
					       
					        
					        maxLength: 300,
					        value : row.get('majorIn'),
					        allowBlank:true
						},{
					    	fieldLabel:'办公电话',
					        name:'user.officePhone',
					       
					        
					        value : row.get('officePhone'),
					        maxLength: 20,
					        allowBlank:true
						},{
					    	fieldLabel:'电子邮件',
					        name:'user.email',
					        value : row.get('email'),
					        maxLength: 100,
					        allowBlank:true,
					        vtype : "email",
							vtypeText : "不是有效的邮箱地址"
						},{
							fieldLabel : '签字预览',
							xtype : 'box',
							id : 'updateSignerImg',
							width : 110,
							height : 130,
							autoEl : {
							    tag : 'img',
							    src : '${ctx}/images/signer.jpg', 
							    style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)'
							}
						},{
							xtype : 'textfield',
							id : 'txtUpdateSignerImage',
							name : 'signerImage',
							inputType : 'file',
							fieldLabel : '用户签字',
							listeners : {
								'render':function(){
									var signerImageCmp = Ext.get('txtUpdateSignerImage');
									var signerPictureURL = row.get('signerPictureURL');
									if(signerPictureURL){
										Ext.Ajax.request({
						   	       			url: '${ctx}/system/isFileExit.do',
						   	       	    	params: { filePath : signerPictureURL},
						   	       	    	success: function(response, opts) {
						   	       	    		var result = Ext.decode(response.responseText);
							   	       	    	var flag = result.success;
										 	 	if(flag){
										 	 		signerPictureURL = '${ctx}/' + signerPictureURL;
										 	 		signerPictureURL = signerPictureURL.replace(/\\/g,"/");
										 	 		
										 	 		if(Ext.isIE){
									                     var image = Ext.get('updateSignerImg').dom;  
												         image.src = Ext.BLANK_IMAGE_URL;
												         image.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = signerPictureURL; 
											        }else{
												        Ext.get('updateSignerImg').dom.src = pictureURL;
											        }
											 	}
						   	       	    	}
					   	       	    	});
									}

									signerImageCmp.on('change',function(field,newValue,oldValue){
								        var txtSignerImage = Ext.get('txtUpdateSignerImage').dom;
								        var divUserDom = Ext.get('divUser').dom;
								        var url = '';
										if (window.navigator.userAgent.indexOf("MSIE")>=1){
											if(navigator.appVersion.match(/9./i)){
												txtSignerImage.select();
												txtSignerImage.blur();
			        							url = document.selection.createRange().text; 
			        							if(url==''){
			        								txtSignerImage.select();
			        								divUserDom.focus();
			        								url = document.selection.createRange().text; 
			        							}
			        							if(url==''){
			        								url=txtSignerImage.value;
			        							}
			    							}
								     	}else if(window.navigator.userAgent.indexOf("Firefox")>=1){
								     		if(txtSignerImage.files){
								     			url = txtSignerImage.files.item(0).getAsDataURL();
								     		}
								     		url = txtSignerImage.value;
								     	}

								     	if(url == ''){
											return;
									    }

										var AllowExt=".jpg|.gif|.bmp|.jpeg|.png" //允许上传的文件类型
										var FileExt= url.substring(url.lastIndexOf(".")+1).toLowerCase();
										if(AllowExt.indexOf(FileExt)==-1){ //判断文件类型是否允许上传
										    Ext.tooltip.msg('no', '仅支持jpg、bmp、gif、png、jpeg格式！');
										    return;
										}
										
								        if(Ext.isIE){
						                     var image = Ext.get('updateSignerImg').dom;  
									         image.src = Ext.BLANK_IMAGE_URL;
									         image.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = url; 
								        }else{
									         //支持FF
									         Ext.get('updateSignerImg').dom.src =Ext.get('txtUpdateSignerImage').dom.files.item(0).getAsDataURL();
								        }
									},this);
						    	}
							}
						}]
					}]
				}]
			});

  		     
        	var win = new Ext.Window({
				title: '修改用户',
		        closable:true,
		        width:660,
		        height:360,
		        modal:true,
		        plain:true,
		        resizable:false,
		        layout :"fit",
		        buttonAlign:'center', 
		        items: [
					updateUserForm
		        ],
		    	buttons:[{
		    		text:'保存',
		    	    handler:function(){
			    		if(!updateUserForm.getForm().isValid()){
		        	    	return;
		        	    }

			    		//验证图片格式
			    		var url = '';
				        var txtUserImage = Ext.get('txtUpdateUserImage').dom;
				        var divUserDom=Ext.get('divUser').dom;
						if (window.navigator.userAgent.indexOf("MSIE")>=1){
							if(navigator.appVersion.match(/9./i)){
								txtUserImage.select();
								txtUserImage.blur();
    							url = document.selection.createRange().text; 
    							if(url==''){
    								txtUserImage.select();
    								divUserDom.focus();
    								url = document.selection.createRange().text; 
    							}
    							if(url==''){
    								url=txtUserImage.value;
    							}
							}
				     	}else if(window.navigator.userAgent.indexOf("Firefox")>=1){
				     		if(txtUserImage.files){
				     			url = txtUserImage.files.item(0).getAsDataURL();
				     		}
				     		url = txtUserImage.value;
				     	}

				     	if(url != ''){
							var AllowExt=".jpg|.gif|.bmp|.jpeg|.png" //允许上传的文件类型
							var FileExt= url.substring(url.lastIndexOf(".")+1).toLowerCase();
							if(AllowExt.indexOf(FileExt)==-1){ //判断文件类型是否允许上传
							    Ext.tooltip.msg('no', '仅支持jpg、bmp、gif、png、jpeg格式！');
							    return;
							}
					    }

				     	//验证签名图片格式
			    		var urlSignerImage = '';
				        var txtUpdateSignerImage = Ext.get('txtUpdateSignerImage').dom;
						if (window.navigator.userAgent.indexOf("MSIE")>=1){
							if(navigator.appVersion.match(/9./i)){
								txtUpdateSignerImage.select();
								txtUpdateSignerImage.blur();
								urlSignerImage = document.selection.createRange().text; 
    							if(urlSignerImage==''){
    								txtUpdateSignerImage.select();
    								divUserDom.focus();
    								urlSignerImage = document.selection.createRange().text; 
    							}
    							if(urlSignerImage==''){
    								urlSignerImage=txtUpdateSignerImage.value;
    							}
							}
				     	}else if(window.navigator.userAgent.indexOf("Firefox")>=1){
				     		if(txtUserImage.files){
				     			urlSignerImage = txtUpdateSignerImage.files.item(0).getAsDataURL();
				     		}
				     		urlSignerImage = txtUpdateSignerImage.value;
				     	}

				     	if(urlSignerImage != ''){
							var AllowExtSignerImage=".jpg|.gif|.bmp|.jpeg|.png" //允许上传的文件类型
							var FileExtSignerImage= urlSignerImage.substring(urlSignerImage.lastIndexOf(".")+1).toLowerCase();
							if(AllowExtSignerImage.indexOf(FileExtSignerImage)==-1){ //判断文件类型是否允许上传
							    Ext.tooltip.msg('no', '仅支持jpg、bmp、gif、png、jpeg格式！');
							    return;
							}
					    }
			        	    
				     	updateUserForm.getForm().submit({
			                url:'${ctx}/system/updateUser.do',
			                success:function(form,action){
			                	Ext.tooltip.msg('yes', action.result.msg);
			                	user.manage.grid.getStore().reload();
		              	     	win.close();
		              	    },
			              	failure:function(form,action){
		              	    	Ext.tooltip.msg('no', action.result.msg);
			              	}
		                });
		    		}
	    	    },{
	    	       	text:'重置',
	    	       	handler : function(){
	    	    		updateUserForm.getForm().reset();
	    	    	}
	    	    },{
	    	       	text:'关闭',
	    	       	handler:function(){
	    	    		win.close();
	    	    	}
		    	}]
		    });
			win.show();
        },

        //删除用户
		deleteUser : function (){
        	var ids = getSelectRecordIds();
        	if(ids==false){
   	        	Ext.tooltip.msg('no', '请选择要操作的行！');
   	       		return
   	        }
   	       	Ext.MessageBox.confirm('信息提示：', '确定要删除这'+user.manage.grid.getSelectionModel().getSelections().length+'条数据?', function(btn){
   	       		if(btn == 'yes'){
   	       	    	Ext.Ajax.request({
	   	       			url: 'deleteUser.do',
	   	       	    	params: { ids: ids},
	   	       	    	success: function(response, opts) {
	   	       	    		var result = Ext.decode(response.responseText);
		   	       	    	var flag = result.success;
					 	 	if(flag){
		   	       	    		Ext.tooltip.msg('yes', result.msg);
		   	       	    		user.manage.grid.getStore().reload();
					 	    }else{
					 	      	Ext.tooltip.msg('no', result.msg);
						 	}
	   	       	    	}
   	       	    	});
   	       	    }
   	       	});
        },
        
        //主数据单位同步
        synchroUnit : function () {
        	user.manage.waitUpload();
        	Ext.Ajax.request({
         	   	url: '${ctx}/system/synchroUnit.do',
         		success: function(response, opts) {
         	      	var result = Ext.decode(response.responseText);
         	      	var flag = result.success;
         	      	if(flag){
         	      		user.manage.synchroUser();
         	      	} else {
         	      		Ext.getCmp("waitUploadWin").close();
         	      		Ext.tooltip.msg('no', result.msg);
         	      	}
         	   	}
         	});
        },
        
      //主数据人员同步
        synchroUser : function () {
        	Ext.Ajax.request({
         	   	url: '${ctx}/system/synchroUser.do',
         		success: function(response, opts) {
         			Ext.getCmp("waitUploadWin").close();
         	      	var result = Ext.decode(response.responseText);
         	      	var flag = result.success;
         	      	if(flag){
         	      		Ext.tooltip.msg('yes', result.msg);
   	       	    		user.manage.grid.getStore().reload();
   	       	    		Ext.getCmp("treePanel").getRootNode().reload();
         	      	}else{
         	      		Ext.getCmp("waitUploadWin").close();
         	      		Ext.tooltip.msg('no', result.msg);
         	      	}
         	   	}
         	});
        },
        
		//等待上传
		waitUpload : function () {
			var waitUploadWin = new Ext.Window({
				id:'waitUploadWin',
		        closable:false,
		        width:260,
		        height:120,
		        border:false,
		        bodyBorder:false,
		        frame:true,
		        closeAction:'close',
		        modal:true,
		        plain:false,
		        hideBorders:true,
      		    resizable:false,
		        layout :"fit",
				bodyStyle: 'background-color: white;',
				html:'<center><img src="${ctx}/images/waitImg.gif" style="margin-top:8px;"></img><br><span style="font-size:13px; margin-top:5px; color:#FF6A6A">正在更新您的数据，请稍等...</span></center>'
			});
			waitUploadWin.show();
		},

		//查看用户详细信息
        viewUser : function (){
    		var row = user.manage.grid.getSelectionModel().getSelections()[0];
    		var id = row.get("id");
        	var win = new Ext.Window({
    			title:'查看用户',
    			width:660,
		        height:360,
    	       	modal:true,
    	       	resizable:false,
    	       	closable : true, 
        		html:"<iframe width='100%' height='100%' frameborder='0' scrolling='auto' name='iframe' src='${ctx}/system/toViewUserDetail.do?id="+id+"'></iframe>",
    	       	buttonAlign:'center',
    	       	buttons: [{
    	           	text: '关闭',
    	           	handler: function(){
    	       			win.close();
    	           	}
    	   		}]
    	   	});
    		   
        	win.show();
    	/* 	
    		win.load({   
    			url : "${ctx}/system/toViewUserDetail.do",
    		    scripts: false,
    			params : {   
    				id : id
    			}   
    		});    */
        },

        lockupUser : function(id){
        	Ext.Ajax.request({
         	   	url: 'lockupUser.do',
         	   	params: { 
         	   		id: id
         	   	},
         		success: function(response, opts) {
         	      	var result = Ext.decode(response.responseText);
         	      	var flag = result.success;
         	      	if(flag){
         	      		Ext.tooltip.msg('yes', result.msg);
   	       	    		user.manage.grid.getStore().reload();
         	      	}else{
         	      		Ext.tooltip.msg('no', result.msg);
         	      	}
         	   	}
         	});
        },

		//批量导入用户信息数据
        importUser:function(){
        	var userImportWin = new Ext.Window({
				title: '批量导入用户',
		        closable:true,
		        border:false,
		        width:420,
		        height:280,
		        modal:true,
		        plain:false,
		        resizable:false,
		    	bodyStyle: 'background-color: white;',
		        layout :"fit",
		        buttonAlign:'center', 
		    	buttons:[
		 		    {
			    		text:'上传',
		    	       	handler:function(){
		    	       		var file = Ext.getDom("userExcel");
		    	       		var extend = file.value.substring(file.value.lastIndexOf(".")+1);
		    	       		if (extend!='xls'&&extend!='xlsx') {
		    	       			Ext.tooltip.msg('no', '请选择excel文件上传！');
								return;
							}else{
								Ext.Ajax.request({   
							        url:'importUser.do',   
							        isUpload:true,   
							        form:'importUserForm',   
							        success:function(response, opts){   
					         	      	var result = Ext.decode(response.responseText);
					         	      	var flag = result.success;
					         	      	if(flag){
					         	      		Ext.tooltip.msg('yes', result.msg);
					   	       	    		user.manage.grid.getStore().reload();
					   	       	    		userImportWin.close();
					         	      	}else{
					         	      		//Ext.tooltip.msg('no', result.msg);
					         	      		Ext.MessageBox.alert("信息提示",result.msg);
					         	      	} 
							        }   
							    });   

							}
		    	    	}
			    	},
			    	{
	    	       	text:'关闭',
	    	       	handler:function(){
			    		userImportWin.close();
	    	    	}
		    	}]
		    });
		    
        	userImportWin.show();

			var tpl = new Ext.XTemplate( 
					'<div style="margin-top:20px;">',
					'<table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;font-family: \'宋体\', \'sans-serif\'; border:0px;margin:0px;line-height:23px;">',
					'<tr>',
					'<td width="30%" style="border:0; text-align:right; padding-right:5px;padding-top:5px;padding-bottom:5px;">',
				    '<img src="${ctx}/images/prompt_90.gif" width="90" height="90"/>', 
					'</td>',
					'<td width="70%" style="border:0; padding-left:5px; padding-top:5px; padding-bottom:5px;">',
				    '<div style="margin: 0  10 0 10;">&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red;">温馨提示：</span>在批量导入用户信息前，请先下载系统提供的模板，不然批量导入可能会不成功！</div>', 
				    '<p style="margin:5 0 5 20;">用户信息导入模板_2003&nbsp;|&nbsp;<a href="downLoad.do?dowdLoadFlag=user2003" >下载</a></p>',
				    '<p style="margin:5 0 5 20;">单位信息_2003&nbsp;|&nbsp;<a href="${ctx}/system/downLoadUnit.do" >下载</a></p>',
					'</td>',
					'</tr>',
					'</table>',
					'<hr style="margin: 0  10 0 10;"/>',
					'<form id="importUserForm" method="post" action="importUser.do" enctype="multipart/form-data">',
					'<p style="margin:5 0 5 20;">批量上传：<input type="file" name="userExcel" /></p>', 
					'</form>',
					'</div>'
				); 

    			var data = new Object();
    			tpl.overwrite(userImportWin.body, data); 
        },
        
		//进入到用户分配角色
        toRoleList : function(){

        	var roleRecord = Ext.data.Record.create([
        		{name:'id',type:'int'},
        		{name:'roleName',type:'string'},
        		{name:'description',type:'string'}
        	]);
        	
    		
            var	roleStore = new Ext.data.Store({
        		proxy:new Ext.data.HttpProxy({url: '${ctx}/system/getRoleList.do'}),
        		reader:new Ext.data.JsonReader({
        			totalProperty:'totalSize',
        		    root:'list'
        		},
        			roleRecord
        		)
        	});

        	var row = user.manage.grid.getSelectionModel().getSelections()[0];

        	var validRenderer = function(value, cellmeta, record, rowIndex,columnIndex, store) {
				var roleId = record.get('id');//角色ID
        		var userId = row.get('id');//人员ID 
        		var roleIds = row.get('roleIds');//所具有的角色ID 
        		if (roleId != '') {
        			var flag = false;
        			for (var i = 0; i < roleIds.length; i++) {
        				var ids = roleIds[i];
        				if (roleId == ids) {
        					flag = true;
        					break;
        				}
        			}
        			if (flag) {
        				return "<input type='checkbox' checked='checked' onclick = 'javascript:updateUserRole(this,"+ roleId + ","+userId+");'>";
        			} else {
        				return "<input type='checkbox' onclick = 'javascript:updateUserRole(this,"+ roleId + ","+userId+");'>";
        			}
        		} else {
        			return "<input type='checkbox' onclick = 'javascript:updateUserRole(this,"+ roleId + ","+userId+");'>";
        		}
        	 }
        	
        	var p_rm = new Ext.grid.RowNumberer();
        	
        	var p_cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
				header : '角色名称',
				dataIndex : 'roleName',
				sortable : true,
				width : 70
			}, {
				header : '角色描述',
				dataIndex : 'description'
			}, {
				header : '有效标识',
				dataIndex : '',
				width : 30,
				renderer : validRenderer,
				align : 'center'
			}]);

        	rolePanel = new Ext.grid.GridPanel({
				region : 'center',
				height : 360,
				stripeRows : true,
				loadMask : true,
				columnLines : true,
				store : roleStore,
				cm : p_cm,
				sm : new Ext.grid.RowSelectionModel({
					singleSelect : true
				}),
				viewConfig : {
					columnsText : '显示的列',
					scrollOffset : -1,// 滚动条宽度,默认20
					sortAscText : '升序',
					sortDescText : '降序',
					forceFit : true
					// 表格会自动延展每列的长度,使内容填满整个表格
				},
				listeners:{
        			rowdblclick:user.manage.viewUserRole
        		},
				bbar:new Ext.PagingToolbar({
			   		 style:'border-right:0px;border-bottom:0px;border-left:0px',
			         pageSize: 20,
			         store: roleStore,
			         displayInfo: true,
			         displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			         emptyMsg: "无数据",
			         plugins: new Ext.ux.ProgressBarPager()
			    }),
				plugins : new Ext.ux.PanelResizer({
					minHeight : 100
				})
			});

        	roleStore.load({params:{start:0,limit:20}});

        	roleWin = new Ext.Window({
        		title : '分配角色',
        		closable : true,
        		width : 700,
        		height : 360,
        		modal : true,
        		plain : true,
        		border:false,
        		//tbar : ["分配角色说明"],
        		layout : "fit",
        		closeAction : 'hide',
        		items : [rolePanel],
        		buttonAlign : 'center',
        		buttons : [{
        			text : '关闭',
        			handler : function() {
        				roleWin.hide();
        			}
        		}]
        	});
        	roleWin.show();
        },

        //查看角色信息
        viewUserRole : function(record){
        	var row = rolePanel.getSelectionModel().getSelections()[0];
            var viewUserRoleForm = new Ext.form.FormPanel({
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

        	var userRoleWin = new Ext.Window({
				title: '查看角色',
		        closable:true,
		        width:340,
		        height:250,
		        modal:true,
		        plain:true,
		        layout :"fit",

		        items: [
					viewUserRoleForm
		        ],
		        buttonAlign:'center', 
		    	buttons:[{
	    	       	text:'关闭',
	    	       	handler:function(){
		    		userRoleWin.close();
	    	    	}
		    	}]
		    });
        	userRoleWin.show();
		},
        //用户分配角色
		updateUserRole : function(checkbox,roleId,userId){
    		
    		Ext.Ajax.request({
    			url : 'addUserRole.do',
				params : {
    				userId : userId,
    				roleId : roleId,
					checked : checkbox.checked
				},
				success : function(response, opts) {
					var result = Ext.decode(response.responseText);
   	    	      	var flag = result.success;
   	    	      	if(flag){
   	    	      		Ext.tooltip.msg('yes', result.msg);
   	    	      		user.manage.grid.getStore().reload();
   	    	      	}else{
   	    	      		Ext.tooltip.msg('no', result.msg);
   	    	      	}
				}
        	});
        },
        //进入用户分配权限窗口
		updateUserPermission : function(){

        	var row = user.manage.grid.getSelectionModel().getSelections()[0];
    	    var userPermRecord = Ext.data.Record.create([
			    {name:'userPermissionId',type:'int'},
			    {name:'userId',type:'int'},
				{name:'moduleCode',type:'string'},
				{name:'moduleName',type:'string'},
				{name:'moduleDescribe',type:'string'},
				{name:'view',type:'int'},
				{name:'add',type:'int'},
				{name:'update',type:'int'},
				{name:'delete',type:'int'},
				{name:'audit',type:'int'},
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
	     	 	reader:new Ext.data.JsonReader({},userPermRecord)
	     	 });
    	    Ext.Ajax.request({ 
     	 		url:'${ctx}/system/getUserPermissionList.do',
     	 		params:{id:row.get("id")},
     	        success : function(res ,options) {
     	 			var objs= Ext.decode(res.responseText);
     	 			userPermissionPanel.getStore().proxy = new Ext.data.PagingMemoryProxy(objs); 
     	 			userPermissionPanel.getStore().load({params:{start:0,limit:20}});
     			}, 
     	        failure : function(response) { 
     				Ext.tooltip.msg('no', '系统繁忙，请稍后再试！');
     	        }
     		}); 

    	  //用户权限，查看方法
			var viewUserPermissionRenderer = function(value, cellmeta, record, rowIndex, columnIndex, store){
   				var view = record.get('view');
        		var isView = record.get('isView');
        		if(view == 1){
        			if(isView == 1){
        				return '<img title="可查看" src="${ctx}/images/icons/yes.png"  style="cursor: pointer" onclick="javascript:viewPermission('+rowIndex+');"/>';
        			}else{
        				return '<img title="不可查看" src="${ctx}/images/icons/no.png" style="cursor: pointer" onclick="javascript:viewPermission('+rowIndex+');"/>';
        			}
        		}
        		return '';
        	}

			//用户权限，添加方法
			var addUserPermissionRenderer = function(value, cellmeta, record, rowIndex, columnIndex, store){
        		var add = record.get('add');
        		var isAdd = record.get('isAdd');
        		if(add == 1){
        			if(isAdd == 1){
        				return '<img title="可添加" src="${ctx}/images/icons/yes.png" style="cursor: pointer" onclick="javascript:addPermission('+rowIndex+');"/>';
        			}else{
        				return '<img title="不可添加" src="${ctx}/images/icons/no.png"  style="cursor: pointer" onclick="javascript:addPermission('+rowIndex+');"/>';
        			}
        		}
        		return '';
        	}

			//用户权限，修改方法
			var updateUserPermissionRenderer = function(value, cellmeta, record, rowIndex, columnIndex, store){
        		var update = record.get('update');
        		var isUpdate = record.get('isUpdate');
        		if(update == 1){
        			if(isUpdate == 1){
        				return '<img title="可修改" src="${ctx}/images/icons/yes.png" style="cursor: pointer" onclick="javascript:updatePermission('+rowIndex+');"/>';
        			}else{
        				return '<img title="不可修改" src="${ctx}/images/icons/no.png" style="cursor: pointer" onclick="javascript:updatePermission('+rowIndex+');"/>';
        			}
        		}
        		return '';
        	}

			//用户权限，删除方法
			var deleteUserPermissionRenderer = function(value, cellmeta, record, rowIndex, columnIndex, store){
        		var del = record.get('delete');
        		var isDelete = record.get('isDelete');
        		if(del == 1){
        			if(isDelete == 1){
        				return '<img title="可删除" src="${ctx}/images/icons/yes.png"  style="cursor: pointer" onclick="javascript:deletePermission('+rowIndex+');"/>';
        			}else{
        				return '<img title="不可删除" src="${ctx}/images/icons/no.png"  style="cursor: pointer" onclick="javascript:deletePermission('+rowIndex+');"/>';
        			}
        		}
        		return '';
        	}
     		
			var p_rm = new Ext.grid.RowNumberer();
          	
          	var p_cm = new Ext.grid.ColumnModel([
          		p_rm,
          		{header:'模块名称',dataIndex:'moduleName',width:25,sortable:true},
          		{header:'查看',dataIndex:'',renderer:viewUserPermissionRenderer,width:25,align:'center'},
          		{header:'添加',dataIndex:'',renderer:addUserPermissionRenderer,width:25,align:'center'},
          		{header:'修改',dataIndex:'',renderer:updateUserPermissionRenderer,width:25,align:'center'},
          		{header:'删除',dataIndex:'',renderer:deleteUserPermissionRenderer,width:25,align:'center'},
          		{header:'权限说明',dataIndex:'moduleDescribe',sortable:true}
          	]);

          	//点击角色，弹出的该角色对应的权限列表
			userPermissionPanel = new Ext.grid.GridPanel({
          		region:'center',
          		height:360,
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
        			rowdblclick:user.manage.viewRolePermission
        		},
          	   	bbar:new Ext.PagingToolbar({
          	    	pageSize: 20,
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
                  	
			var userPermissionWin = new Ext.Window({
         		title:'用户管理-用户授权',
         	    closable:true,
         	    modal:true,
		        plain:true,
		        border:false,
         	    width:750,
         	    height:360,
         	    layout :"fit",
         	    items: [
					userPermissionPanel
         	    ],
         	    buttonAlign:'center', 
         	    buttons:[{
         	    	text:'关闭',
         	    	handler:function(){
         	    	userPermissionWin.close();
         	    	}
         	 	}]
         	});
			userPermissionWin.show();
        },
    	//查看权限方法
        viewPermission : function(rowIndex){
       		var record = userPermissionPanel.getStore().getAt(rowIndex);
    		var userPermissionId = record.get('userPermissionId');
    		var userId = record.get('userId');
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
	    	user.manage.updateUserPermissions(userPermissionId,userId,isView,isAdd,isUpdate,isDelete,isAudit,moduleCode);
        },

      	//添加权限方法
        addPermission : function (rowIndex){
        	var record = userPermissionPanel.getStore().getAt(rowIndex);
    		var userPermissionId = record.get('userPermissionId');
    		var userId = record.get('userId');
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
	    	user.manage.updateUserPermissions(userPermissionId,userId,isView,isAdd,isUpdate,isDelete,isAudit,moduleCode);
        },
        //修改权限方法
        updatePermission : function(rowIndex){
        	var record = userPermissionPanel.getStore().getAt(rowIndex);
    		var userPermissionId = record.get('userPermissionId');
    		var userId = record.get('userId');
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
	    	user.manage.updateUserPermissions(userPermissionId,userId,isView,isAdd,isUpdate,isDelete,isAudit,moduleCode);
        },
        //删除权限方法
        deletePermission : function(rowIndex){
        	var record = userPermissionPanel.getStore().getAt(rowIndex);
    		var userPermissionId = record.get('userPermissionId');
    		var userId = record.get('userId');
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
	    	user.manage.updateUserPermissions(userPermissionId,userId,isView,isAdd,isUpdate,isDelete,isAudit,moduleCode);
        },

        //更新用户权限
        updateUserPermissions:function(userPermissionId,userId,isView,isAdd,isUpdate,isDelete,isAudit,moduleCode){
        	Ext.Ajax.request({
         		url: '${ctx}/system/updateUserPermission.do',
         	   	params: { 
         			userPermissionId:userPermissionId,
        			userId: userId,
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
  	    				refreshPermission(userId,userPermissionPanel.getBottomToolbar().cursor);
	 	      		}else{
	 	      			Ext.tooltip.msg('no', result.msg);
		 	   	 	}
           	   	}
        	});
        },

        //查看权限说明
        viewRolePermission: function(){
        	var row = userPermissionPanel.getSelectionModel().getSelections()[0];
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
	       	        html: '<div style="width:240px;">'+row.get('moduleName')+'</div>'    
			    },{
			    	fieldLabel:'权限说明',
	       	        xtype: 'box',
	       	        style:'padding-top:5px;',  
	       	        html: '<div style="width:240px;height:180px;overflow:auto;overflow-x:hidden;">'+row.get('moduleDescribe')+'</div>'    
			    }]
			});

        	var rolePermissionWin = new Ext.Window({
				title: '查看权限说明',
		        closable:true,
		        width:400,
		        height:200,
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
        }


        
    }; 
}();

Ext.onReady(function() {
	//------------------------------单位树开始---------------------------------------------------
	var unitTreeLoader = new Ext.tree.TreeLoader({
		preloadChildren: true,
		clearOnLoad: false,
		url : '${ctx}/system/getUnitList.do'
	});
	
	unitTreeLoader.on('beforeload', function(treeLoader, node) {
		treeLoader.baseParams = {
			nodeId : node.attributes.nodeId
		};
	}, this);
  	
	var treePanel = new Ext.tree.TreePanel({
    	title: '单位树(点击右键操作)',
    	id:"treePanel",
        region: 'west',
        width:240,
        minSize: 200,
        maxSize: 250,
		split: true,
        autoScroll: true,
        animate: true,
        animCollapse:true,
        rootVisible: false,
        bodyStyle :'margin: 0px;border-left:0px;border-bottom:0px;',
        headerStyle:'border-top:0px;border-left:0px',
        lines: false,
        //singleExpand: true,
        useArrows: true,
        enableDD: true,
        tbar:new Ext.Toolbar({
		    style:'border-top:0px;border-left:0px',
		    items:[
				{	
					xtype:'button',
					id:"add",
					text:"刷新",
					iconCls : "x-btn-text x-tbar-loading",
					handler : function (){
						treePanel.getRootNode().reload();
						user.manage.grid.getStore().setBaseParam("unitId","");
					}
				},
			   	'->',
			   	{
		            iconCls: 'icon-expand-all',
					tooltip: '展开所有',
		            handler: function(){ treePanel.getRootNode().expand(true); },
		            scope: this
		        },
		        '-',
		        {
		            iconCls: 'icon-collapse-all',
		            tooltip: '折叠所有',
		            handler: function(){ treePanel.getRootNode().collapse(true); },
		            scope: this
		        }]
	        }),
        root: {
			nodeId : 0, 
	        nodeType: 'async',
	      	text: '单位树',
	      	type : 'root',
	      	unitAddress : '',
	      	unitPhone : '',
	    	leaf:false,
	    	draggable: false
	    },
        
        loader : unitTreeLoader,
		
        listeners:{
	    	'containercontextmenu' : function(tree,e){
				var treeMenu = new Ext.menu.Menu({
					items : [{
						text : "添加最上级单位",
						handler : function() {
							treePanel.root.select();
							user.manage.addUnit();
						}
					}]
				});
				treeMenu.showAt(e.getPoint());
			}, /* 从主数据获取数据后，屏蔽此功能*/    
			'movenode' : function(tree, node, oldParent, newParent, index) {
				if(oldParent == newParent){ //改变单位顺序
					var idArray = [];
					var unitChildren = oldParent.childNodes;
					 
					for(var j=0; j < unitChildren.length; j+=1){
					 	var id = unitChildren[j].attributes.nodeId;  
					 	idArray.push(id);               //向数组后添加元素,此时的数组的顺序代表新的树结点顺序
					}

					Ext.Ajax.request({
           	    	   	url: '${ctx}/system/orderUnits.do',
           	    	   	params: { ids: idArray.toString()},
           	    	 	success: function(response, opts) {
           	    	      	var result = Ext.decode(response.responseText);
           	    	      	var flag = result.success;
           	    	      	if(flag){
           	    	      		Ext.tooltip.msg('yes', result.msg);
           	    	      	}else{
           	    	      		Ext.tooltip.msg('no', result.msg);
           	    	      	}
           	    	   	}
           	    	});
				} else { //改变单位父亲结点
					var idArray = [];
					var unitChildren = newParent.childNodes;
					for(var j=0; j< unitChildren.length; j+=1){
					 	var id = unitChildren[j].attributes.nodeId;  
					 	idArray.push(id);               //向数组后添加元素,此时的数组的顺序代表新的树结点顺序
					}
					
					Ext.Ajax.request({
           	    	   	url: '${ctx}/system/updateUnitParent.do',
           	    	   	params: { 
           	    	   		id : node.attributes.nodeId,
           	    	   		parentId : newParent.attributes.nodeId,
           	    	   		ids : idArray.toString()
           	    	   	},
           	    	 	success: function(response, opts) {
           	    	      	var result = Ext.decode(response.responseText);
           	    	      	var flag = result.success;
           	    	     	if(flag){
           	    	     		Ext.tooltip.msg('yes', result.msg);
        	    	      	}else{
           	    	     		Ext.tooltip.msg('no', result.msg);
        	    	      	}
           	    	   	}
           	    	});
				}
			},
			'click' : function(node, event) {
	    		user.manage.grid.getStore().setBaseParam("unitId",node.attributes.nodeId);
	    		user.manage.grid.getStore().load({params:{start:0,limit:20}});
			}
		}
        
    });

	var showTreeMenu = function(node, e){
		node.select();
		if(node.attributes.type != 'other'){
			var dirMenu = new Ext.menu.Menu({
				items : [
				 {
					text : "添加单位",
					handler : function() {
						user.manage.addUnit();
					}
				},{
					text : "修改单位",
					handler : function() {
						user.manage.updateUnit();
					}
				}, {
					text : "查看单位",
					handler : function() {
						user.manage.viewUnit();
					}
				} ,{
					text : "删除单位",
					handler : function() {
						user.manage.deleteUnit();
					}
				} ]
			});
			dirMenu.showAt(e.getPoint());
		}
	};
	// 添加右键菜单
	treePanel.on("contextmenu", showTreeMenu);

	user.manage.setTree(treePanel);

	//------------------------------单位树结束---------------------------------------------------
	/*
	var grid =  new Ext.Panel({
		region: 'center',
		html: 'bbb'
	});*/

	//------------------------------用户grid开始-------------------------------------------------
	var record = Ext.data.Record.create([
 		{name:'id',type:'int'},
 		{name:'userName',type:'string'},
 		{name:'loginName',type:'string'},
 		{name:'jobNumber',type:'string'},
 		{name:'gender',type:'int'},
 	   	{name:'birth',type:'string'},
 		{name:'cellPhone',type:'string'},
 		{name:'homePhone',type:'string'},
 		{name:'officePhone',type:'string'},
 		{name:'job',type:'string'},
 		{name:'pictureURL',type:'string'},
 		{name:'signerPictureURL',type:'string'},
 		{name:'email',type:'string'},
 		{name:'majorIn',type:'string'},
 		{name:'identityCard',type:'string'},
 		{name:'political',type:'string'},
 		{name:'address',type:'string'},
 		{name:'isPrincipal',type:'int'},
 		{name:'isLockup',type:'int'},
 		{name:'unit',type:'string'},
 		{name:'unitId',type:'string'},
 		{name:'roleIds'}
 	]);

   	var store = new Ext.data.Store({
   		proxy:new Ext.data.HttpProxy({url: '${ctx}/system/getUserList.do'}),
   		reader:new Ext.data.JsonReader({
   		   totalProperty:'totalSize',
   		   root:'list'
   		},record)
   	});
                            	
    var rm = new Ext.grid.RowNumberer();
    var sm = new Ext.grid.CheckboxSelectionModel({
		listeners : {
			"selectionchange" : function(win) {
				var length=grid.getSelectionModel().getSelections().length
					if (grid.getSelectionModel().getSelections().length == 1) {
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
  		{header:'姓名',dataIndex:'userName'},
  		/*{header:'性别',
  	  		dataIndex:'gender',
  			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				if(value == 0){
					return '男';
				}else{
					return '女';
				}
				return '';
  			},
  			sortable : true
  		},*/
  		{header:'职务',dataIndex:'job'},
  		{header:'单位',dataIndex:'unit',sortable : true},
  		{header:'角色',
			dataIndex:'',
			width: 50,
			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
	  			/* var loginName = record.get('loginName');
				if(loginName != 'admin'){ */
					return '<img title="点击编辑角色" src="${ctx}/images/icons/role.gif" style="cursor: pointer" onclick="toRoleList();"/>';
			/* 	} 
				return '';*/
			},
			align:'center'
		},
		{header:'权限',
			dataIndex:'',
			width: 50,
			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
			/* 	var loginName = record.get('loginName');
				if(loginName != 'admin'){ */
					return '<img title="点击编辑权限" src="${ctx}/images/icons/permission.gif" style="cursor: pointer" onclick="updateUserPermission();"/>';
				/* } 
				return '';*/
			},
			align:'center'
		}  ,
		{header:'锁定/解锁',
			dataIndex:'',
			width: 50,
			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				/* var loginName = record.get('loginName');
				if(loginName != 'admin'){ */
					var isLockup = record.get('isLockup');
					var id = record.get('id');
					if(isLockup == 0){
						return '<img title="点击锁定用户" src="${ctx}/images/icons/unlock.gif" style="cursor: pointer" onclick="javascript:lockupUser('+id+');"/>';
					}else if(isLockup == 1){
						return '<img title="点击解锁用户" src="${ctx}/images/icons/lock.gif" style="cursor: pointer" onclick="javascript:lockupUser('+id+');"/>';
					}
				/* } 
				return '';*/
			},
			align:'center'
		}  
  	]);
  	cm.defaultSortable = true;
	
   	var grid = new Ext.grid.GridPanel({
   		region:'center',
   		stripeRows: true,
   		bodyStyle :'margin: 0px;border-right:0px',
   		loadMask:true,
   		columnLines: true,
   		store:store,
   		cm: cm,
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
   			    var model = user.manage.grid.getSelectionModel();
   			 model.selectRow(rowIndex);
   			    user.manage.viewUser();
   			}
   		},
   		tbar : new Ext.Toolbar({
			height: 40,
			style:'padding:10px 10px 0px 10px;border-top:0px;border-right:0px',
			items:[
	   	    	'姓名',
	   	    	" ",
	   	    	new Ext.form.TextField({
	   	    		width:'135',
		    		id:'userName'
		    	}),
		    	/*  "&nbsp;",
	   	    	'是否锁定：',
	   	    	new Ext.form.Checkbox({
		    		id:'lock',
		    		checked:false,
		    		boxLabel:''
		    	}),  */
	   		    '&nbsp;',
	   	    	{
	       	    	text :   "查询", 
	       	    	iconCls: "search-button", 
	       	    	handler:function(){
	   		    		store.setBaseParam("userName",Ext.getCmp('userName').getValue());
	   		    		/* store.setBaseParam("isLock",Ext.getCmp('lock').getValue()); */
	       	    		store.load({params:{start:0,limit:20}});
	       			} 
	       	   	},
	   		    '-',
	   	    	{
	       	    	text :   "重置", 
	       	    	iconCls: "reset-button",
	       	    	handler:function(){
	       	   			Ext.getCmp('userName').setValue("");
	       	   			/* Ext.getCmp('lock').setValue(""); */
	       			} 
	       	   	},
 	       	 	'->',
       	 	{
					text : "添加",
	    	    	iconCls: "add-button",
	    	    	handler:function(){
	       	 			user.manage.addUser();
	       	 		}
	    	   	},
	       	 	'-',
	   	    	{
	    	   		text : "修改",
	    	    	iconCls: "update-button",
	    	    	id:'btnUpdateUser',
	    	    	disabled:true,
	    	    	handler: user.manage.updateUser
	       	   	},
	       	 	'-',
	   	    	{
	    	   		text : "删除",
	    	   		id : 'btnDeleteUser',
	    	   		disabled:true,
	    	    	iconCls: "delete-button",
	    	    	handler: user.manage.deleteUser
	       	   	}/* ,
	       		 '-',
	   	    	{
	    	    	iconCls: "help-button",
	    	    	tooltip: '帮助',
	    	    	handler: function() {
	    	    		
	       	   		}
	       	   	} */
	       	]
   		}), 
   	   	bbar:new Ext.PagingToolbar({
   	  		 style:'border-right:0px;border-bottom:0px;',
   	         pageSize: 20,
   	         store: store,
   	         displayInfo: true,
   	         displayMsg: '显示第 {0}条到 {1}条记录，一共 {2} 条',
   	         emptyMsg: "无数据",
   	         plugins: new Ext.ux.ProgressBarPager()
   	    })
   	});
   	
   	store.load({params:{start:0,limit:20}});

   	user.manage.setGrid(grid);
  	//------------------------------用户grid结束-------------------------------------------------

new Ext.Viewport({
	    layout: 'border',
	    bodyStyle :'margin: 0px;border:0px',
	    items: [
	    	treePanel,
	    	grid
	    ]
	});
});

//点击列表上的角色，进入到用户划分角色窗口
function toRoleList(){
	user.manage.toRoleList();
}
//点击复选框，进入用户划分角色功能
function updateUserRole(checkbox, userId,roleId){
	user.manage.updateUserRole(checkbox,userId,roleId);
}
//点击列表上的权限，进入到用户分配权限窗口
function updateUserPermission(){
	user.manage.updateUserPermission();
}
//锁定用户
function lockupUser(id){
	user.manage.lockupUser(id);
}
//用户分配查看方法
function viewPermission(rowIndex){
	user.manage.viewPermission(rowIndex);
}
//用户分配添加方法
function addPermission(rowIndex){
	user.manage.addPermission(rowIndex);
}
//用户修改权限方法
function updatePermission(rowIndex){
	user.manage.updatePermission(rowIndex);
}
//用户删除权限方法
function deletePermission(rowIndex){
	user.manage.deletePermission(rowIndex);
}
</script>
