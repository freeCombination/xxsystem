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
		Ext.define("Resp",{
			extend:"Ext.data.Model",
			fields:[
				{name: "respId"}, 
				{name: "number"}, 
				{name: "name"}, 
				{name: "orgName"},
				{name: "orgId"},
				{name: "rank"},
				{name: "enable"},
				{name: "isDelete"}
			]
		});
		
		Ext.define('Duty', {
	        extend: 'Ext.data.Model',
	        fields: [
	            {name: 'dutyId', type: 'int'},
	            {name: 'number', type: 'string'},
                {name: 'dutyContent', type: 'string'},
                {name: 'dutyType', type: 'string'},
                {name: 'respName', type: 'string'},
                {name: 'respId', type: 'int'}
	        ]
	    });
		
		Ext.define('treeModel', {
		    extend: 'Ext.data.Model',
		    fields: [
		        {name: 'nodeId',type: 'int'}, 
		        {name: 'parentId',type: 'int'}, 
		        {name: 'id',type: 'int',mapping:'nodeId'},
		        {name: 'text',type: 'string'} 
		    ]
		});
		
		//建立数据Store
		var respStore=Ext.create("Ext.data.Store", {
	        pageSize: SystemConstant.commonSize,
	        model:"Resp",
	        remoteSort:true,
			proxy: {
	            type:"ajax",
	            actionMethods: {
                	read: 'POST'
           		},
			    url: "${ctx}/org/getRespList.action",
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
		        	var c = respGrid.getSelectionModel().getSelection();
					if(c.length > 0){
						var enable = 1;
						for (var i = 0; i < c.length; i++) {
							if (c[i].get('enable') != 1) {
								enable = 0;
								break;
							}
						}
						
						if (enable == 1) {
							Ext.getCmp('delRespBtn').setDisabled(false);
						}
						else {
							Ext.getCmp('delRespBtn').setDisabled(true);
						}
					}else{
						Ext.getCmp('delRespBtn').setDisabled(true);
					}
					if(c.length == 1){
						var enable = c[0].get('enable');
						if (enable == 1) {
							Ext.getCmp('updateRespBtn').setDisabled(false);
						}
						else {
							Ext.getCmp('updateRespBtn').setDisabled(true);
						}
					}else{
						Ext.getCmp('updateRespBtn').setDisabled(true);
					}
				}
			}
	    });
		
		var cm=[
				{header:"序号",xtype: "rownumberer",width:60,align:"center",menuDisabled: true,sortable :false},
	            {header: "ID",width: 70,dataIndex: "respId",hidden: true,menuDisabled: true,sortable :false},
	            {header: "岗位编号",width: 200,dataIndex: "number",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}},
	            {header: "名称",width: 200,dataIndex: "name",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}},
	            {header: "所属部门",width: 200,dataIndex: "orgName",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}},
	            {header: "岗位级别",width: 200,dataIndex: "rank",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}},
				{header: "状态",width: 200,dataIndex: "enable",
		            renderer: function(value, cellmeta, record, rowIndex, columnIndex, store){
		                //cellmeta.tdAttr = 'data-qtip="' + orgTypeArr[i].name + '"';
		                var respId = record.get('respId');
		                if(value == 1){
		                    return '<img title="点击锁定岗位" src="'+basePath+'/images/icons/unlock.gif" style="cursor: pointer;padding:0;margin:0;" onclick="lockUnLock(\''+respId+'\',\''+value+'\')"/>';
		                }else{
		                    return '<img title="点击解锁岗位" src="'+basePath+'/images/icons/lock.gif" style="cursor: pointer;padding:0;margin:0;" onclick="lockUnLock(\''+respId+'\',\''+value+'\')"/>';
		                }
		            }
		        }
	         ];
		
		var belongToDeptQuery = Ext.create("Ext.ux.TreePicker", {
		    allowBlank: false,
		    value:'0',
		    id:'respOrgQuery',
		    displayField: 'text',
		    rootVisible: true,
		    valueField: 'id',
		    minPickerHeight: 200, //最小高度，不设置的话有时候下拉会出问题
		    editable: false, //启用编辑，主要是为了清空当前的选择项
		    enableKeyEvents: true, //激活键盘事件
		    store: Ext.create("Ext.data.TreeStore", {
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
		            url :'${ctx}/org/getUnitTreeListNotCheck.action'
		        },
		        root: {
		            expanded: true,
		            id:"0",
		            text:'全部'
		        }
		    })
		});
		
		//grid组件
		var respGrid =  Ext.create("Ext.grid.Panel",{
			title:'岗位管理',
			border:false,
			columnLines: true,
			layout:"fit",
			region: "center",
			width: "100%",
			height: document.body.clientHeight,
			id: "respGrid",
			bbar:  Ext.create("Ext.PagingToolbar", {
				store: respStore,
				displayInfo: true,
				displayMsg: SystemConstant.displayMsg,
				emptyMsg: SystemConstant.emptyMsg
			}),
			columns:cm,
	        selModel:sm,
	     	forceFit : true,
			store: respStore,
			autoScroll: true,
			stripeRows: true,
			tbar: ['岗位编号',
			{
				id: 'respNoQuery',
				width: 100,   
				labelWidth: 70,
				xtype: 'textfield'
			},'&nbsp;岗位名称',
			{
				id: 'respNameQuery',
                width: 100,   
                labelWidth: 70,
                xtype: 'textfield'
			},'&nbsp;所属部门',
			belongToDeptQuery,
            '&nbsp;',
			{
				id:'searchRespBtn',
				xtype:'button',
				disabled:false,
				text:'查询',
				iconCls:'search-button',
				handler:function(){
					var proxy = respStore.getProxy();
					proxy.setExtraParam("respVo.number",Ext.getCmp("respNoQuery").getValue());
					proxy.setExtraParam("respVo.name",Ext.getCmp("respNameQuery").getValue());
					proxy.setExtraParam("respVo.orgId",Ext.getCmp("respOrgQuery").getValue());
					respStore.loadPage(1);
				}
			},'->',
			{
				id:'addRespBtn',
				xtype:'button',
				disabled:false,
				text:'添加',
				//hidden:true,
				iconCls:'add-button',
				handler:function(){
					addRespInfo(null);
				}
			},
			{
				id:'updateRespBtn',
				xtype:'button',
				text:'修改',
				//hidden:true,
				disabled:true,
				iconCls:'edit-button',
				handler:function(){
					var row = respGrid.getSelectionModel().getSelection()[0];
					addRespInfo(row);
				}
			},
			{
				id:'delRespBtn',
				xtype:'button',
				text:'删除',
				//hidden:true,
				disabled:true,
				iconCls:'delete-button',
				handler:function(){
					var ck = respGrid.getSelectionModel().getSelection();
					var itemsArray = new Array();
					for(var i=0;i<ck.length;i++){
						itemsArray.push(ck[i].data.respId);
					}
					var idss = itemsArray.toString();
					
					Ext.Msg.confirm(SystemConstant.alertTitle,"确认删除所选岗位数据吗？",function(btn) {
                        if (btn == 'yes') {
                            Ext.Ajax.request({
                                url : '${ctx}/org/delResps.action',
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
                                    respStore.loadPage(1);
                                }
                            });
                        }
                    });
				}
			}]/* , 
			listeners:{
				'render': function() {
					for(var i = 0;i < userPermissionArr.length;i++){
						if("resp_add_btn" == userPermissionArr[i].name){
							Ext.getCmp('addRespBtn').setVisible(true);
						}
						if("resp_update_btn" == userPermissionArr[i].name){
							Ext.getCmp('updateRespBtn').setVisible(true);
						}
						if("resp_delete_btn" == userPermissionArr[i].name){
							Ext.getCmp('delRespBtn').setVisible(true);
						}
					}
				}
			} */
		});
		respStore.load({params:{start:0,limit:SystemConstant.commonSize}});
		
		Ext.create("Ext.container.Viewport", {
		    layout: "border",
			renderTo: Ext.getBody(),
			items: [respGrid]
		});
		
		function addRespInfo(row){
			var count = 0;
			var oldNumber = '';
			var respId = '';
			if (row) {
				respId = row.get('respId');
				oldNumber = row.get('number');
			}
			
			var belongToDept = Ext.create("Ext.ux.TreePicker", {
			    allowBlank: false,
			    //value:'0',
			    id:'respOrg',
                fieldLabel: '所属部门',
                labelAlign:'right',
                name: 'respVo.orgId',
			    displayField: 'text',
			    rootVisible: true,
			    valueField: 'id',
			    minPickerHeight: 200, //最小高度，不设置的话有时候下拉会出问题
			    editable: false, //启用编辑，主要是为了清空当前的选择项
			    enableKeyEvents: true, //激活键盘事件
			    store: Ext.create("Ext.data.TreeStore", {
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
			            url :'${ctx}/org/getUnitTreeListNotCheck.action'
			        },
			        root: {
			            expanded: true,
			            id:"0",
			            text:'组织机构'
			        },
			        listeners:{
			        	load: function(to, node, records, successful, eOpts ){
			        		node.expandChildren(true);
			        	}
			        }
			    })
			});
			
			var respForm = Ext.create("Ext.form.Panel", {
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
							    id:'respId',
							    name: 'respVo.respId',
							    hidden:true
							},
							{
			                    id:'respNumber',
			                    fieldLabel: '岗位编号',
			                    name: 'respVo.number',
			                    maxLength:25,
			                    regex : new RegExp('^([^<^>])*$'),
			                    regexText : '不能包含特殊字符！',
			                    allowBlank: false,
			                    validator: function(value){
			                        var returnObj = null;
			                        if(value == oldNumber){
			                            return true;
			                        }else{
			                            $.ajax({
			                                url : '${ctx}/org/checkNumber.action',
			                                data:{value:value},
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
			                    }
			                },
			                belongToDept
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
							    id:'respName',
							    fieldLabel: '岗位名称',
							    name: 'respVo.name',
							    regex : new RegExp('^([^<^>])*$'),
			                    regexText : '不能包含特殊字符！',
							    maxLength:50,
							    allowBlank: false
							},
							{
			                    id: 'respRank',
			                    fieldLabel: '岗位级别',
			                    name: 'respVo.rank',
			                    regex : new RegExp('^([^<^>])*$'),
			                    regexText : '不能包含特殊字符！',
			                    maxLength:50
			                }
                        ]
					}
					]}
                ]
            });
			
			var dutyStore = Ext.create("Ext.data.Store", {
		        pageSize: SystemConstant.commonSize,
		        model:"Duty",
		        remoteSort:true,
		        proxy: {
		            type:"ajax",
		            actionMethods: {
		                read: 'POST'
		            },
		            extraParams:{respId : respId},
		            url: "${ctx}/org/getDutyListByRespId.action",
		            reader:{
		                type:'json'
		            },
		            simpleSortMode :true
		        }
		    });
			
			var dutyCm=[
                {header:"序号",xtype: "rownumberer",width:60,align:"center",menuDisabled: true,sortable :false},
                {header: "ID",width: 50,dataIndex: "dutyId",hidden: true,menuDisabled: true,sortable :false},
                {header: "职责编号",width: 100,dataIndex: "number",menuDisabled: true,sortable :false,
                    renderer : function(value, cellmeta, record, rowIndex,
                            columnIndex, store) {
                        cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return value;
                    },
                    field: {
                    	xtype:'textfield',
                    	maxLength:25,
                    	regex : new RegExp('^([^<^>])*$'),
                        regexText : '不能包含特殊字符！',
                    	allowBlank: false
                    }

                },
                {header: "职责内容",width: 200,dataIndex: "dutyContent",menuDisabled: true,sortable :false,
                    renderer : function(value, cellmeta, record, rowIndex,
                            columnIndex, store) {
                        cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return value;
                    },
                    field: {
                    	xtype:'textarea',
                    	style: {
                            marginTop: '38px'
                        },
                    	height:60,
                    	maxLength:1000,
                    	regex : new RegExp('^([^<^>])*$'),
                        regexText : '不能包含特殊字符！',
                        allowBlank: false
                    }
                },
                {header: "职责类型",width: 120,dataIndex: "dutyType",menuDisabled: true,sortable :false,
                    renderer : function(value, cellmeta, record, rowIndex,
                            columnIndex, store) {
                        cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return value;
                    },
                    field: {
                    	xtype:'textarea',
                    	style: {
                            marginTop: '38px'
                        },
                    	height:60,
                    	maxLength:100,
                    	regex : new RegExp('^([^<^>])*$'),
                        regexText : '不能包含特殊字符！',
                        allowBlank: false
                    }
                }
            ];
			
			var dutySm = Ext.create("Ext.selection.CheckboxModel",{
		        injectCheckbox:0,
		        listeners: {
		            selectionchange: function(){
		                var c = dutyGrid.getSelectionModel().getSelection();
		                if (c.length > 0) {
		                    Ext.getCmp('delDutyBtn').setDisabled(false);
		                } else {
		                    Ext.getCmp('delDutyBtn').setDisabled(true);
		                }
		            }
		        }
		    });
			
			var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
		        clicksToEdit: 1/* ,
		        listeners : {
		            beforeedit:function(editor, e, eOpts ){
		                
		            }
		        } */
		    });
			
			var dutyGrid =  Ext.create("Ext.grid.Panel",{
		        border:false,
		        columnLines: true,
		        layout:"fit",
		        region: "center",
		        height: 120,
		        id: "dutyGrid",
		        columns:dutyCm,
		        selModel:dutySm,
		        plugins: [cellEditing],
		        forceFit : true,
		        store: dutyStore,
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
		                    var data= "{ dutyId:" + count
		                            + ", number:''"
                                    + ", dutyContent:''"
                                    + ", dutyType:'' }";

		                    dutyStore.add(eval("("+data+")"));
		                }
		            },
		            {
		                id : 'delDutyBtn',
		                xtype : 'button',
		                disabled : true,
		                text : '删除',
		                iconCls : 'delete-button',
		                handler : function() {
		                    var rows = dutyGrid.getSelectionModel().getSelection();
		                    Ext.Msg.confirm('系统提示','确定要删除这'+rows.length+'条记录吗?',function(btn){
		                        if(btn=='yes'){
		                            for(var i=0; i<rows.length;i++){
		                            	dutyStore.remove(rows[i]);
		                            }
		                            dutyGrid.getView().refresh();//刷新行号
		                        }
		                    });
		                }
		            }
		        ]
		    });
			dutyStore.load();
            
			var winTitle = '添加岗位';
			var formUrl = '${ctx}/org/addResp.action';
			if (row) {
				winTitle = '修改岗位';
	            formUrl = '${ctx}/org/updateResp.action';
			}
			
            var respWin=Ext.create("Ext.window.Window",{
                title: winTitle,
                resizable: false,
                buttonAlign:"center",
                closeAction : 'destroy',
                height: 360,
                width: 600,
                modal:true,
                layout: 'border',
                modal : true,
                items: [respForm, dutyGrid],
                buttons:[{
                    text: SystemConstant.saveBtnText,
                    handler: function(){
                        if(respForm.form.isValid()){
                            Ext.MessageBox.wait("", "添加岗位数据", 
                                {
                                    text:"请稍后..."
                                }
                            );
                            
                            var c = dutyStore.getCount();
                            if (c <= 0) {
                            	Ext.MessageBox.show({
                                    title:'提示信息',
                                    msg:"请填写岗位职责",
                                    buttons: Ext.Msg.YES,
                                    modal : true,
                                    icon: Ext.Msg.INFO
                                });
                            	return false;
                            }
                            
                            var dutyLst = '';
                            for(var i=0; i<dutyStore.getCount(); i++){
                                var re = dutyStore.getAt(i);
                                var number = re.get('number');
                                var dutyContent = re.get('dutyContent');
                                var dutyType = re.get('dutyType');
                                
                                if (!number || !dutyContent || !dutyType) {
                                	Ext.MessageBox.show({
                                        title:'提示信息',
                                        msg:"岗位编号、内容、类型不能为空",
                                        buttons: Ext.Msg.YES,
                                        modal : true,
                                        icon: Ext.Msg.INFO
                                    });
                                    return false;
                                }
                                
                                dutyLst += '&dvoLst[' + i + '].number=' + number
                                		    + '&dvoLst[' + i + '].dutyContent=' + dutyContent
                                		    + '&dvoLst[' + i + '].dutyType=' + dutyType;
                            }
                            
                            respForm.form.submit({
                                url : formUrl,
                                params : dutyLst.substring(1),
                                success : function(form, action) {
                                    new Ext.ux.TipsWindow({
                                        title: SystemConstant.alertTitle,
                                        autoHide: 3,
                                        html:action.result.msg
                                    }).show();
                                    
                                    respStore.load();
                                    respWin.close();
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
                                    respStore.load();
                                    respWin.close();
                                    Ext.MessageBox.hide();
                                 }
                            });
                        }
                    }
                },{
                    text: '关闭',
                    handler: function(){
                        respWin.close();
                    }
                }],
                listeners: {
                	afterrender: function(){
                		if (row) {
                            Ext.getCmp('respId').setValue(row.get('respId'));
                            Ext.getCmp('respNumber').setValue(row.get('number'));
                            Ext.getCmp('respOrg').setValue(row.get('orgId'));
                            //Ext.getCmp('respOrg').setRawValue(row.get('orgName'));
                            Ext.getCmp('respRank').setValue(row.get('rank'));
                            Ext.getCmp('respName').setValue(row.get('name'));
                        }
                	}
                }
             }).show();
		}
		
		lockUnLock = function(respId, enable){
			var title = '确认锁定所选岗位数据吗？';
			if (enable == 0) {
				title = '确认解锁所选岗位数据吗？';
			}
			
			Ext.Msg.confirm(SystemConstant.alertTitle, title, function(btn) {
                if (btn == 'yes') {
                	Ext.Ajax.request({
                        url: '${ctx}/org/lockUnLock.action',
                        async:false,
                        params: {
                            respId: respId,
                            enable: enable
                        },
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
                            respStore.loadPage(1);
                        }
                    });
                }
            });
		};
		
	});
	</script>
</body>
</html>