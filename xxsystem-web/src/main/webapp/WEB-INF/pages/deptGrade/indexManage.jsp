<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/doc_type.jsp"%>
<html>
<head>
<%@include file="../common/meta.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/css.jsp"%>
<%@include file="../common/ext.jsp"%>
<title>指标管理</title>
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
		Ext.define("Index",{
			extend:"Ext.data.Model",
			fields:[
				{name: "indexId"}, 
				{name: "number"}, 
				{name: "name"}, 
				{name: "classifyName"},
				{name: "classifyId"},
				{name: "grade"},
				{name: "remark"}
			]
		});
		
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
                {name: "isDelete"}
            ]
        });
		
		//建立数据Store
		var indexStore=Ext.create("Ext.data.Store", {
	        pageSize: SystemConstant.commonSize,
	        model:"Index",
	        remoteSort:true,
			proxy: {
	            type:"ajax",
	            actionMethods: {
                	read: 'POST'
           		},
			    url: "${ctx}/deptgrade/getIndexList.action",
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
		        	var c = indexGrid.getSelectionModel().getSelection();
					if(c.length > 0){
						Ext.getCmp('delIndexBtn').setDisabled(false);
					}else{
						Ext.getCmp('delIndexBtn').setDisabled(true);
					}
					if(c.length == 1){
						Ext.getCmp('updateIndexBtn').setDisabled(false);
					}else{
						Ext.getCmp('updateIndexBtn').setDisabled(true);
					}
				}
			}
	    });
		
		var cm=[
				{header:"序号",xtype: "rownumberer",width:60,align:"center",menuDisabled: true,sortable :false},
	            {header: "ID",width: 70,dataIndex: "indexId",hidden: true,menuDisabled: true,sortable :false},
	            {header: "指标编号",width: 100,dataIndex: "number",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}},
	            {header: "指标名称",width: 100,dataIndex: "name",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}},
	            {header: "指标分类",width: 100,dataIndex: "classifyName",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}},
	            {header: "考核分值",width: 60,dataIndex: "grade",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}},
				{header: "考核说明",width: 200,dataIndex: "remark",
		            renderer: function(value, cellmeta, record, rowIndex, columnIndex, store){
		            	cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return value;
		            }
		        }
	         ];
		
		//grid组件
		var indexGrid =  Ext.create("Ext.grid.Panel",{
			title:'指标管理',
			border:false,
			columnLines: true,
			layout:"fit",
			region: "center",
			width: "100%",
			height: document.body.clientHeight,
			id: "indexGrid",
			bbar:  Ext.create("Ext.PagingToolbar", {
				store: indexStore,
				displayInfo: true,
				displayMsg: SystemConstant.displayMsg,
				emptyMsg: SystemConstant.emptyMsg
			}),
			columns:cm,
	        selModel:sm,
	     	forceFit : true,
			store: indexStore,
			autoScroll: true,
			stripeRows: true,
			tbar: ['指标编号',
			{
				id: 'indexNoQuery',
				width: 100,   
				labelWidth: 70,
				xtype: 'textfield'
			},'&nbsp;指标名称',
			{
				id: 'indexNameQuery',
                width: 100,   
                labelWidth: 70,
                xtype: 'textfield'
			},'&nbsp;参评年份',
            {
                id: 'electYearQuery',
                width: 100,   
                labelWidth: 70,
                value: Ext.Date.format(new Date(),"Y"),
                xtype: 'textfield',
                listeners :{
                    'render' : function(p){
                        p.getEl().on('click',function(){
                            WdatePicker({readOnly:true,dateFmt:'yyyy',maxDate:Ext.Date.format(new Date(),"Y")});
                            //,onpicked:function(){$dp.$('electYearQuery-inputEl').focus();}
                        });
                    }
                }
            },
            '&nbsp;',
			{
				id:'searchRespBtn',
				xtype:'button',
				disabled:false,
				text:'查询',
				iconCls:'search-button',
				handler:function(){
					var proxy = indexStore.getProxy();
					proxy.setExtraParam("indexVo.number",Ext.getCmp("indexNoQuery").getValue());
					proxy.setExtraParam("indexVo.name",Ext.getCmp("indexNameQuery").getValue());
					proxy.setExtraParam("indexVo.electYear",Ext.getCmp("electYearQuery").getValue());
					indexStore.loadPage(1);
				}
			},'->',
			{
				id:'addIndexBtn',
				xtype:'button',
				disabled:false,
				text:'添加',
				//hidden:true,
				iconCls:'add-button',
				handler:function(){
					addIndex(null);
				}
			},
			{
				id:'updateIndexBtn',
				xtype:'button',
				text:'修改',
				//hidden:true,
				disabled:true,
				iconCls:'edit-button',
				handler:function(){
					var row = indexGrid.getSelectionModel().getSelection()[0];
					addIndex(row);
				}
			},
			{
				id:'delIndexBtn',
				xtype:'button',
				text:'删除',
				//hidden:true,
				disabled:true,
				iconCls:'delete-button',
				handler:function(){
					var ck = indexGrid.getSelectionModel().getSelection();
					var itemsArray = new Array();
					for(var i=0;i<ck.length;i++){
						itemsArray.push(ck[i].data.indexId);
					}
					var idss = itemsArray.toString();
					
					Ext.Msg.confirm(SystemConstant.alertTitle,"确认删除所选指标数据吗？",function(btn) {
                        if (btn == 'yes') {
                            Ext.Ajax.request({
                                url : '${ctx}/deptgrade/delIndexes.action',
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
                                    indexStore.loadPage(1);
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
							Ext.getCmp('addIndexBtn').setVisible(true);
						}
						if("resp_update_btn" == userPermissionArr[i].name){
							Ext.getCmp('updateIndexBtn').setVisible(true);
						}
						if("resp_delete_btn" == userPermissionArr[i].name){
							Ext.getCmp('delIndexBtn').setVisible(true);
						}
					}
				}
			} */
		});
		indexStore.load({
			params:{
				start:0,
				limit:SystemConstant.commonSize,
				'indexVo.electYear':Ext.getCmp('electYearQuery').getValue()
			}
		});
		
		Ext.create("Ext.container.Viewport", {
		    layout: "border",
			renderTo: Ext.getBody(),
			items: [indexGrid]
		});
		
		function addIndex(row){
			var count = 0;
			var oldNumber = '';
			var indexId = '';
			if (row) {
				indexId = row.get('indexId');
				oldNumber = row.get('number');
			}
			
			var  cfStore = Ext.create('Ext.data.Store', {
			    model: 'Classify',
			    proxy: {
			       type: 'ajax',
			       url: basePath+'/deptgrade/getAllClassifies.action',
			       reader: {
			          type: 'json'
			       }
			    },
			    autoLoad: true
			});
			
			var index1Form = Ext.create("Ext.form.Panel", {
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
							    id:'indexVoIndexId',
							    name: 'indexVo.indexId',
							    hidden:true
							},
							{
			                    id:'indexVoNumber',
			                    fieldLabel: '指标编号',
			                    name: 'indexVo.number',
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
			                                url : '${ctx}/deptgrade/checkIndexNumber.action',
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
			                {
                                xtype: 'combobox',
                                fieldLabel: '指标分类',
                                id:'indexVoClassifyId',
                                name: 'indexVo.classifyId',
                                store: cfStore,
                                valueField: 'classifyId',
                                displayField: 'name',
                                typeAhead:false,
                                allowBlank:false,
                                editable:false,
                                queryMode: 'remote'
                            }
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
							    id:'indexVoName',
							    fieldLabel: '指标名称',
							    name: 'indexVo.name',
							    regex : new RegExp('^([^<^>])*$'),
			                    regexText : '不能包含特殊字符！',
							    maxLength:50,
							    allowBlank: false
							},
							{
			                    id: 'indexVoGrade',
			                    fieldLabel: '考核分值',
			                    name: 'indexVo.grade',
			                    regex : new RegExp('^([^<^>])*$'),
			                    regexText : '不能包含特殊字符！',
			                    maxLength:50,
                                allowBlank: false
			                }
                        ]
					},
					{
                        layout: 'form',
                        columnWidth: 1,
                        labelWidth:60,
                        defaultType:'textfield',
                        defaults:{
                            labelAlign:'right'
                        },
                        border: false,
                        items: [
                            {
                                id:'indexVoRemark',
                                xtype:'textarea',
                                rows:3,
                                fieldLabel: '考核说明',
                                name: 'indexVo.remark',
                                //regex : new RegExp('^([^<^>])*$'),
                                //regexText : '不能包含特殊字符！',
                                maxLength:1000
                            }
                        ]
                    }
					]}
                ]
            });
			
			var index2Store = Ext.create("Ext.data.Store", {
		        pageSize: SystemConstant.commonSize,
		        model:"Index",
		        remoteSort:true,
		        proxy: {
		            type:"ajax",
		            actionMethods: {
		                read: 'POST'
		            },
		            extraParams:{index1Id : indexId},
		            url: "${ctx}/deptgrade/getIndex2ListByIndex1Id.action",
		            reader:{
		                type:'json'
		            },
		            simpleSortMode :true
		        }
		    });
			
			var index2Cm=[
                {header:"序号",xtype: "rownumberer",width:60,align:"center",menuDisabled: true,sortable :false},
                {header: "ID",width: 50,dataIndex: "indexId",hidden: true,menuDisabled: true,sortable :false},
                {header: "二级指标编号",width: 100,dataIndex: "number",menuDisabled: true,sortable :false,
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
                {header: "二级指标名称",width: 100,dataIndex: "name",menuDisabled: true,sortable :false,
                    renderer : function(value, cellmeta, record, rowIndex,
                            columnIndex, store) {
                        cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return value;
                    },
                    field: {
                        xtype:'textfield',
                        maxLength:50,
                        regex : new RegExp('^([^<^>])*$'),
                        regexText : '不能包含特殊字符！',
                        allowBlank: false
                    }

                },
                {header: "考核分值",width: 60,dataIndex: "grade",menuDisabled: true,sortable :false,
                    renderer : function(value, cellmeta, record, rowIndex,
                            columnIndex, store) {
                        cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return value;
                    },
                    field: {
                        xtype:'textfield',
                        maxLength:10,
                        regex : new RegExp('^([^<^>])*$'),
                        regexText : '不能包含特殊字符！',
                        allowBlank: false
                    }

                },
                {header: "考核说明",width: 200,dataIndex: "remark",menuDisabled: true,sortable :false,
                    renderer : function(value, cellmeta, record, rowIndex,
                            columnIndex, store) {
                        cellmeta.tdAttr = 'data-qtip="' + value + '"';
                        return value;
                    },
                    field: {
                    	xtype:'textarea',
                    	style: {
                            marginTop: '80px'
                        },
                    	height:100,
                    	maxLength:1000
                    	//regex : new RegExp('^([^<^>])*$'),
                        //regexText : '不能包含特殊字符！',
                    }
                }
            ];
			
			var index2Sm = Ext.create("Ext.selection.CheckboxModel",{
		        injectCheckbox:0,
		        listeners: {
		            selectionchange: function(){
		                var c = index2Grid.getSelectionModel().getSelection();
		                if (c.length > 0) {
		                    Ext.getCmp('delIndex2Btn').setDisabled(false);
		                } else {
		                    Ext.getCmp('delIndex2Btn').setDisabled(true);
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
			
			var index2Grid =  Ext.create("Ext.grid.Panel",{
		        border:false,
		        columnLines: true,
		        layout:"fit",
		        region: "center",
		        height: 120,
		        id: "index2Grid",
		        columns:index2Cm,
		        selModel:index2Sm,
		        plugins: [cellEditing],
		        forceFit : true,
		        store: index2Store,
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
		                    var data= "{ indexId:" + count
		                            + ", number:''"
                                    + ", name:''"
                                    + ", grade:''"
                                    + ", remark:'' }";

		                    index2Store.add(eval("("+data+")"));
		                }
		            },
		            {
		                id : 'delIndex2Btn',
		                xtype : 'button',
		                disabled : true,
		                text : '删除',
		                iconCls : 'delete-button',
		                handler : function() {
		                    var rows = index2Grid.getSelectionModel().getSelection();
		                    Ext.Msg.confirm('系统提示','确定要删除这'+rows.length+'条记录吗?',function(btn){
		                        if(btn=='yes'){
		                            for(var i=0; i<rows.length;i++){
		                            	index2Store.remove(rows[i]);
		                            }
		                            index2Grid.getView().refresh();//刷新行号
		                        }
		                    });
		                }
		            }
		        ]
		    });
			index2Store.load();
            
			var winTitle = '添加指标';
			var formUrl = '${ctx}/deptgrade/addIndex.action';
			if (row) {
				winTitle = '修改指标';
	            formUrl = '${ctx}/deptgrade/updateIndex.action';
			}
			
            var index2Win=Ext.create("Ext.window.Window",{
                title: winTitle,
                resizable: false,
                buttonAlign:"center",
                closeAction : 'destroy',
                height: 400,
                width: 600,
                modal:true,
                layout: 'border',
                items: [index1Form, index2Grid],
                buttons:[{
                    text: SystemConstant.saveBtnText,
                    handler: function(){
                        if(index1Form.form.isValid()){
                            Ext.MessageBox.wait("", "添加指标数据", 
                                {
                                    text:"请稍后..."
                                }
                            );
                            
                            var index2Lst = '';
                            var c = index2Store.getCount();
                            if (c > 0) {
                            	for(var i=0; i<index2Store.getCount(); i++){
                                    var re = index2Store.getAt(i);
                                    var number = re.get('number');
                                    var name = re.get('name');
                                    var grade = re.get('grade');
                                    var remark = re.get('remark');
                                    
                                    if (!number || !name || !grade) {
                                        Ext.MessageBox.show({
                                            title:'提示信息',
                                            msg:"指标编号、名称、分值不能为空",
                                            buttons: Ext.Msg.YES,
                                            modal : true,
                                            icon: Ext.Msg.INFO
                                        });
                                        return false;
                                    }
                                    
                                    index2Lst += '&index2Lst[' + i + '].number=' + number
                                                + '&index2Lst[' + i + '].name=' + name
                                                + '&index2Lst[' + i + '].grade=' + grade
                                                + '&index2Lst[' + i + '].remark=' + remark;
                                }
                            	
                            	index2Lst = index2Lst.substring(1)
                            }
                            
                            index1Form.form.submit({
                                url : formUrl,
                                params : index2Lst,
                                success : function(form, action) {
                                    new Ext.ux.TipsWindow({
                                        title: SystemConstant.alertTitle,
                                        autoHide: 3,
                                        html:action.result.msg
                                    }).show();
                                    
                                    indexStore.load();
                                    index2Win.close();
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
                                    indexStore.load();
                                    index2Win.close();
                                    Ext.MessageBox.hide();
                                 }
                            });
                        }
                    }
                },{
                    text: '关闭',
                    handler: function(){
                        index2Win.close();
                    }
                }],
                listeners: {
                	afterrender: function(){
                		if (row) {
                            Ext.getCmp('indexVoIndexId').setValue(row.get('indexId'));
                            Ext.getCmp('indexVoNumber').setValue(row.get('number'));
                            Ext.getCmp('indexVoClassifyId').setValue(row.get('classifyId'));
                            Ext.getCmp('indexVoName').setValue(row.get('name'));
                            Ext.getCmp('indexVoGrade').setValue(row.get('grade'));
                            Ext.getCmp('indexVoRemark').setValue(row.get('remark'));
                        }
                	}
                }
             }).show();
		}
	});
	</script>
</body>
</html>