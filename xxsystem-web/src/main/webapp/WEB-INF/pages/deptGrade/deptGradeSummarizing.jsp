<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/doc_type.jsp"%>
<html>
<head>
<%@include file="../common/meta.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/css.jsp"%>
<%@include file="../common/ext.jsp"%>
<title>部门评分汇总</title>
<link href="" rel="SHORTCUT ICON" />
<style type="text/css">
  .x-form-layout-table{
	table-layout: fixed;
  }
  
  .x-grid-td {
    vertical-align: middle !important;
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
		Ext.define("Classify",{
			extend:"Ext.data.Model",
			fields:[
				{name: "classifyId"}, 
				{name: "number"}, 
				{name: "name"}, 
				{name: "orgNames"},
				{name: "orgIds"},
				{name: "electYear"},
				{name: "enable"},
				{name: "isDelete"},
                {name: "hasSubmit"}
			]
		});
		
		Ext.define('GradeRecord', {
	        extend: 'Ext.data.Model',
	        fields: [
	            {name: 'gradeDetailId', type: 'int'},
	            {name: 'classifyName', type: 'string'},
                {name: 'name', type: 'string'},
                {name: 'gradeIndex2Name', type: 'string'},
                {name: 'canpDept', type: 'string'},
                {name: 'score', type: 'string'},
                {name: 'gradeUsr', type: 'string'},
                {name: 'gradeUsrDept', type: 'string'}
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
		
		var cfStore = Ext.create('Ext.data.Store', {
            model: 'Classify',
            proxy: {
               type: 'ajax',
               url: '${ctx}/deptgrade/getAllClassifies.action',
               extraParams:{
                   electYear:Ext.getCmp('electYearQuery') ? Ext.getCmp('electYearQuery').getValue() : Ext.Date.format(new Date(),"Y"),
                   participation: 'true'
               },
               reader: {
                  type: 'json'
               }
            },
            autoLoad: true, 
            listeners:{
                load:function(store, records){
                    var obj = {classifyId:'0', name:'全部'};
                    store.insert(0, obj);
                }
            }
        });
		
		//建立数据Store
		var recordStore=Ext.create("Ext.data.Store", {
	        pageSize: SystemConstant.commonSize,
	        model:"GradeRecord",
	        remoteSort:true,
			proxy: {
	            type:"ajax",
	            actionMethods: {
                	read: 'POST'
           		},
			    url: "${ctx}/deptgrade/queryDeptGradeSummarizing.action",
			    reader: {
				     totalProperty: "totalSize",
				     root: "list"
			    }
	        },
            listeners:{
                load:function(store, records){
                    if (records.length > 0) {
                        mergeCells(recordGrid, [1]);
                    }
                }
            }
		});
		
		var cm=[
				{header:"序号",xtype: "rownumberer",width:60,align:"center",menuDisabled: true,sortable :false},
	            {header: "ID",width: 70,dataIndex: "gradeDetailId",hidden: true,menuDisabled: true,sortable :false},
	            {header: "指标分类",width: 200,dataIndex: "classifyName",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}},
	            {header: "参评部门",width: 200,dataIndex: "canpDept",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}
				},
                {header: "得分",width: 200,dataIndex: "score",menuDisabled: true,sortable :false,
                    renderer : function(value, cellmeta, record, rowIndex,
                            columnIndex, store) {
                        cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return value;
                    }
                }
	         ];
		
		var canpDeptQuery = Ext.create("Ext.ux.TreePicker", {
            allowBlank: false,
            value:'0',
            id:'canpDeptQuery',
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
		recordGrid =  Ext.create("Ext.grid.Panel",{
			title:'部门评分汇总',
			border:false,
			columnLines: true,
			layout:"fit",
			region: "center",
			width: "100%",
			height: document.body.clientHeight,
			id: "recordGrid",
			bbar:  Ext.create("Ext.PagingToolbar", {
				store: recordStore,
				displayInfo: true,
				displayMsg: SystemConstant.displayMsg,
				emptyMsg: SystemConstant.emptyMsg
			}),
			columns:cm,
	     	forceFit : true,
			store: recordStore,
			autoScroll: true,
			stripeRows: true,
			tbar: ['参评年份',
            {
                id: 'electYearQuery',
                width: 100,   
                labelWidth: 70,
                value: Ext.Date.format(new Date(),"Y"),
                xtype: 'textfield',
                listeners :{
                    'render' : function(p){
                        p.getEl().on('click',function(){
                            WdatePicker({readOnly:true,dateFmt:'yyyy',maxDate:Ext.Date.format(new Date(),"Y"),
                            	onpicked:function(){
                                    Ext.getCmp("classifyIdQuery").reset();
                                    cfStore.load({
                                        params:{
                                            'electYear':$dp.cal.getP('y')
                                        },
                                        callback:function(records){
                                            Ext.getCmp("classifyIdQuery").setValue('0');
                                        }
                                    });
                                }
                            });
                            //,onpicked:function(){$dp.$('electYearQuery-inputEl').focus();}
                        });
                    }
                }
            },'&nbsp;指标分类',
            {
                xtype: 'combobox',
                id:'classifyIdQuery',
                store: cfStore,
                valueField: 'classifyId',
                displayField: 'name',
                typeAhead:false,
                allowBlank:false,
                editable:false,
                queryMode: 'remote'
            },'&nbsp;参评部门',canpDeptQuery,
            '&nbsp;',
			{
				id:'searchRecordBtn',
				xtype:'button',
				disabled:false,
				text:'查询',
				iconCls:'search-button',
				handler:function(){
					var proxy = recordStore.getProxy();
					proxy.setExtraParam("electYear",Ext.getCmp("electYearQuery").getValue());
					proxy.setExtraParam("canpDeptId",Ext.getCmp("canpDeptQuery").getValue());
					proxy.setExtraParam("cfId",Ext.getCmp("classifyIdQuery").getValue());
					recordStore.loadPage(1);
				}
			},'->',
			{
				xtype:'button',
                disabled:false,
                text:'查看评分人',
                iconCls:'details-button',
                handler:function(){
                	showGradeUser();
                }
			}]
		});
		
		cfStore.load(function(){
			Ext.getCmp("classifyIdQuery").setValue('0');
		});
		
		recordStore.load({
            params:{
                start:0,
                limit:SystemConstant.commonSize,
                'electYear':Ext.getCmp('electYearQuery').getValue()
            }
        });
		
		function showGradeUser(){

		    //建立用户Model
		    Ext.define("userModel",{
		        extend:"Ext.data.Model",
		        fields:[
		            {name:"userId",mapping:"userId"},
		            {name:"orgName"},
		            {name:"realname"},
		            {name: "flag"}
		         ]
		    });
		    
		    var userCm1=[
		        {xtype: "rownumberer",text:"序号",width:40,align:"center"},
		        {header: "ID",align:'left',dataIndex: "userId",hidden: true,menuDisabled: true,sortable:false},
		        {header: "部门",width: 100,align:'left',dataIndex: "orgName",menuDisabled: true,sortable:false},
		        {header: "姓名",width: 80,align:'center',dataIndex: "realname",menuDisabled: true,sortable:false},
		        {header: "状态",width: 80,align:'center',dataIndex: "flag",menuDisabled: true,sortable:false,
		        	renderer: function(value, cellmeta, record, rowIndex, columnIndex, store){
                        //cellmeta.tdAttr = 'data-qtip="' + orgTypeArr[i].name + '"';
                        var status = '<span style="color:red;">未评分</span>';
                        if(value == 1){
                        	status = '<span style="color:green;">已评分</span>';
                        }
                        return status;
                    }
		        },
                {header: "操作",width: 40,dataIndex: "userId",align:'center',menuDisabled: true,sortable:false,
                    renderer: function(value, cellmeta, record, rowIndex, columnIndex, store){
                    	if (Ext.getCmp('electYearQuery').getValue() == Ext.Date.format(new Date(),"Y")) {
                    		var str = '';
                    		for(var i = 0;i < userPermissionArr.length;i++){
                    			if("deptgrade_rollback_btn" == userPermissionArr[i].name){
                    				str = '<img title="撤回评分" src="${ctx}/images/icons/revokeBack.gif" style="cursor: pointer;padding:0;margin:0;heigth:16px;" onclick="rollback('+value+')"/>';
                    				break;
                                }
                    		}
                    		return str;
                    	}
                    	else {
                    		return '';
                    	}
                    }
                }
		    ];

		    userRoleStore1 = Ext.create('Ext.data.Store', {
		        pageSize: SystemConstant.commonSize,
		        model: 'userModel',
		        proxy: {
		            type: 'ajax',
		            actionMethods: {
		                read: 'POST'
		            },
		            url: '${ctx}/deptgrade/showGradeUser.action',
		            reader:{
		                type: 'json'
		            },
		            autoLoad: true
		        },
	            listeners:{
	                load:function(store, records){
	                    if (records.length > 0) {
	                        mergeCells(userPanel1, [1]);
	                    }
	                }
	            }
		    });

		   userPanel1 = Ext.create(Ext.grid.Panel,{
		        id: "userPanel1",
		        stripeRows: true,
		        border:false,
		        forceFit:true,
		        columnLines: true,
		        autoScroll: true,
		        store : userRoleStore1,
		        columns:userCm1
		    });
		    
		    userRoleStore1.load({
	            params:{
	                'electYear':Ext.getCmp('electYearQuery').getValue()
	            }
	        });
		                    
		    //用户分配角色窗口
		    var userWin1 = Ext.create(Ext.window.Window,{
		        title:"评分人员列表",
		        width:600,
		        height:400,
		        modal:true,
		        resizable:true,
		        closeAction:'destroy',
		        items:[userPanel1],
		        buttonAlign : 'center',
		        buttons:[{
		            text:'关闭',handler:function(){
		            userWin1.close();
		        }}
		        ]
		    }).show();
		}
		
		rollback = function(userId){
			Ext.Msg.confirm(SystemConstant.alertTitle,"确定撤回该用户的评分吗？",function(btn) {
                if (btn == 'yes') {
                    Ext.MessageBox.wait("", "撤销中", 
                        {
                            text:"请稍后..."
                        }
                    );
                    
                    $.ajax({
                        url : '${ctx}/deptgrade/rollback.action',
                        data: {userId : userId, electYear : Ext.getCmp('electYearQuery').getValue()},
                        cache : false,
                        async : false,
                        type : "POST",
                        dataType : 'json',
                        success : function (responseText){
                            var result = responseText; // Ext.decode(responseText);
                            if(result.success == "true"){
                                new Ext.ux.TipsWindow({
                                    title:SystemConstant.alertTitle,
                                    html: result.msg
                                }).show();
                                Ext.MessageBox.hide();
                                userRoleStore1.reload();
                            }else{
                                Ext.MessageBox.hide();
                                Ext.MessageBox.show({
                                    title: SystemConstant.alertTitle,
                                    msg: result.msg,
                                    buttons: Ext.MessageBox.OK,
                                    icon: Ext.MessageBox.INFO
                                });
                            }
                        }
                    });
                }
            });
		}
		
		Ext.create("Ext.container.Viewport", {
		    layout: "border",
			renderTo: Ext.getBody(),
			items: [recordGrid]
		});
	});
	</script>
</body>
</html>