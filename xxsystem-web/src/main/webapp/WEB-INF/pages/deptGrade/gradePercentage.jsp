<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/doc_type.jsp"%>
<html>
<head>
<%@include file="../common/meta.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/css.jsp"%>
<%@include file="../common/ext.jsp"%>
<title>权重管理</title>
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
		
		Ext.define("Percentage",{
            extend:"Ext.data.Model",
            fields:[
                {name: "perId"}, 
                {name: "classifyName"}, 
                {name: "classifyId"}, 
                {name: "receiptsNum"},
                {name: "roleName"},
                {name: "roleId"},
                {name: "percentage"},
                {name: "remark"}
            ]
        });
		
		Ext.define("Role",{
            extend:"Ext.data.Model",
            fields:[
                {name: "roleId"}, 
                {name: "roleName"}
            ]
        });
		
		var cfStore = Ext.create('Ext.data.Store', {
            model: 'Classify',
            proxy: {
               type: 'ajax',
               url: '${ctx}/deptgrade/getAllClassifies.action',
               extraParams:{
            	   electYear:Ext.getCmp('electYearQuery') ? Ext.getCmp('electYearQuery').getValue() : Ext.Date.format(new Date(),"Y")
               },
               reader: {
                  type: 'json'
               }
            },
            autoLoad: false
        });
		
		var roleStore = Ext.create('Ext.data.Store', {
            model: 'Role',
            proxy: {
               type: 'ajax',
               url: '${ctx}/deptgrade/getAllRole.action',
               reader: {
                  type: 'json'
               }
            },
            autoLoad: true
        });
		
		//建立数据Store
		var perStore=Ext.create("Ext.data.Store", {
	        pageSize: SystemConstant.commonSize,
	        model:"Percentage",
	        remoteSort:true,
			proxy: {
	            type:"ajax",
	            actionMethods: {
                	read: 'POST'
           		},
			    url: "${ctx}/deptgrade/getBaseListByCfId.action",
			    reader: {
			    	type: 'json'
			    }
	        }
		});
		
		var cm=[
				{header:"序号",xtype: "rownumberer",width:60,align:"center",menuDisabled: true,sortable :false},
	            {header: "ID",width: 70,dataIndex: "perId",hidden: true,menuDisabled: true,sortable :false},
	            {header: "roleId",width: 70,dataIndex: "roleId",hidden: true,menuDisabled: true,sortable :false},
	            {header: "角色",width: 200,dataIndex: "roleName",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						cellmeta.tdAttr = 'data-qtip="' + value + '"';
						return value;
					},
                    field: {
                        xtype:'combo',
                        allowBlank: false,
                        editable: false,
                        store: roleStore,
                        queryMode: 'remote',
                        displayField: 'roleName',
                        valueField: 'roleName'
                    }
	            },
	            {header: "权重",width: 200,dataIndex: "percentage",menuDisabled: true,sortable :false,
					renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
						var showStr = Math.round(value * 100) + "%";
						cellmeta.tdAttr = 'data-qtip="' + showStr + '"';
						return showStr;
					},
                    field: {
                        xtype:'textfield',
                        maxLength:10,
                        regex : new RegExp('^[0-9]+(.[0-9]{1,2})?$'),
                        regexText : '保留两位小数！',
                        allowBlank: false
                    }
				},
	            {header: "备注",width: 300,dataIndex: "remark",menuDisabled: true,sortable :false,
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
                        maxLength:1000,
                        regex : new RegExp('^([^<^>])*$'),
                        regexText : '不能包含特殊字符！'
                    }
				}
	         ];
		
		var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
            clicksToEdit: 1
        });
		
		var perSm = Ext.create("Ext.selection.CheckboxModel",{
            injectCheckbox:0,
            listeners: {
                selectionchange: function(){
                	if (Ext.getCmp("electYearQuery").getValue() == Ext.Date.format(new Date(),"Y")) {
                		var c = perGrid.getSelectionModel().getSelection();
                        if (c.length > 0) {
                            Ext.getCmp('delPerBtn').setDisabled(false);
                        } else {
                            Ext.getCmp('delPerBtn').setDisabled(true);
                        }
                	}
                	else {
                		Ext.getCmp('delPerBtn').setDisabled(true);
                	}
                }
            }
        });
		
		var oldNumber = '';
		var count = 0;
		//grid组件
		var perGrid =  Ext.create("Ext.grid.Panel",{
			title:'权重管理',
			border:false,
			columnLines: true,
			layout:"fit",
			region: "center",
			width: "100%",
			height: document.body.clientHeight,
			id: "perGrid",
			columns:cm,
			plugins: [cellEditing],
			selModel:perSm,
	     	forceFit : true,
			store: perStore,
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
                            		if ($dp.cal.getP('y') == Ext.Date.format(new Date(),"Y")) {
                                        Ext.getCmp("addPersBtn").setDisabled(false);
                                        Ext.getCmp("addPercentageBtn").setDisabled(false);
                                    }
                                    else {
                                        Ext.getCmp("addPersBtn").setDisabled(true);
                                        Ext.getCmp("addPercentageBtn").setDisabled(true);
                                    }
                            		
                            		Ext.getCmp("classifyId").reset();
                            		cfStore.load({
                                        params:{
                                            'electYear':$dp.cal.getP('y')
                                        },
                                        callback:function(records){
                                        	if (records.length > 0) {
	                                            var selectedId = records[0].get('classifyId');
	                                            Ext.getCmp("classifyId").setValue(selectedId);
                                        	}
                                            //perStore.load({params:{cfId:selectedId}});
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
                id:'classifyId',
                store: cfStore,
                valueField: 'classifyId',
                displayField: 'name',
                typeAhead:false,
                allowBlank:false,
                editable:false,
                queryMode: 'remote', 
                listeners:{
                    'change': function(comb, newValue, oldValue) {
                        perStore.load({
                        	params:{cfId:newValue},
                        	callback: function(records, operation, success) {
                        		if (records.length > 0) {
	                        		Ext.getCmp("receiptsNum").setValue(records[0].get('receiptsNum'));
                        		}else{
                        			Ext.getCmp("receiptsNum").setValue('');
                        		}
                            }
                        });
                    }
                }
			},'&nbsp;&nbsp;单据编号',
			{
				id: 'receiptsNum',
                width: 160,   
                labelWidth: 70,
                xtype: 'textfield',
                regex : new RegExp('^([^<^>])*$'),
                regexText : '不能包含特殊字符！',
                allowBlank: false/* ,
                validator: function(value){
                    var returnObj = null;
                    if(value == oldNumber){
                        return true;
                    }else{
                        $.ajax({
                            url : '${ctx}/deptgrade/checkreceiptsNum.action',
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
                } */
            },
            '->',
            {
                xtype:'button',
                id:'addPersBtn',
                disabled:false,
                text:'添加',
                iconCls:'add-button',
                handler:function(){
                    count++;
                    //拼接一个数据格式;
                    var data= "{ indexId:" + count
                            + ", roleId:''"
                            + ", roleName:''"
                            + ", percentage:''"
                            + ", remark:'' }";

                    perStore.add(eval("("+data+")"));
                }
            },
			{
				id:'addPercentageBtn',
				xtype:'button',
				text:'保存',
				iconCls:'save-button',
				handler:function(){
					if (Ext.getCmp("classifyId").isValid() && Ext.getCmp("receiptsNum").isValid()) {
						var data = '';
                        var c = perStore.getCount();
                        if (c > 0) {
                        	Ext.MessageBox.wait("", "保存中", 
                                {
                                    text:"请稍后..."
                                }
                            );
                        	
                        	var receiptsNum = Ext.getCmp("receiptsNum").getValue();
                        	var classifyId = Ext.getCmp("classifyId").getValue();
                        	
                            for(var i=0; i<c; i++){
                                var re = perStore.getAt(i);
                                var perId = re.get('perId');
                                var roleName = re.get('roleName');
                                var roleId = roleStore.findRecord('roleName', roleName).get('roleId');
                                var percentage = re.get('percentage');
                                var remark = re.get('remark');
                                
                                if (!percentage || !roleName) {
                                	Ext.MessageBox.hide();
                                    Ext.MessageBox.show({
                                        title:'提示信息',
                                        msg:"权重、权重不能为空",
                                        buttons: Ext.Msg.YES,
                                        modal : true,
                                        icon: Ext.Msg.INFO
                                    });
                                    return false;
                                }
                                
                                data += '&perLst[' + i + '].roleId=' + roleId
                                            + '&perLst[' + i + '].classifyId=' + classifyId
                                            + '&perLst[' + i + '].receiptsNum=' + receiptsNum
                                            + '&perLst[' + i + '].percentage=' + percentage
                                            + '&perLst[' + i + '].remark=' + remark;
                            }
                            
                            data = data.substring(1)
                            
                            $.ajax({
                                url : '${ctx}/deptgrade/savePercentage.action',
                                data: data,
                                cache : false,
                                async : false,
                                type : "POST",
                                dataType : 'json',
                                success : function (responseText){
                                	var result = responseText; // Ext.decode(responseText);
                                    if(result.success){
                                        new Ext.ux.TipsWindow({
                                            title:SystemConstant.alertTitle,
                                            html: result.msg
                                        }).show();
                                        Ext.MessageBox.hide();
                                        perStore.reload();
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
					}
				}
			},{
                id : 'delPerBtn',
                xtype : 'button',
                disabled : true,
                text : '删除',
                iconCls : 'delete-button',
                handler : function() {
                    var rows = perGrid.getSelectionModel().getSelection();
                    Ext.Msg.confirm('系统提示','确定要删除这'+rows.length+'条记录吗?',function(btn){
                        if(btn=='yes'){
                            for(var i=0; i<rows.length;i++){
                            	perStore.remove(rows[i]);
                            }
                            perGrid.getView().refresh();//刷新行号
                        }
                    });
                }
            }], 
			listeners:{
				'render': function() {
					oldNumber = '';
					cfStore.load({
			            params:{
			                'electYear':Ext.Date.format(new Date(),"Y")
			            },
			            callback:function(records){
	                        var selectedId = records[0].get('classifyId');
	                        Ext.getCmp("classifyId").setValue(selectedId);
	                        //perStore.load({params:{cfId:selectedId}});
	                    }
			        });
				}
			}
		});
		
		Ext.create("Ext.container.Viewport", {
		    layout: "border",
			renderTo: Ext.getBody(),
			items: [perGrid]
		});
		
	});
	</script>
</body>
</html>