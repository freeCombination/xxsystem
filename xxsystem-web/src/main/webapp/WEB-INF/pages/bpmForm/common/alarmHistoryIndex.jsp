<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/doc_type.jsp"%>
<html>
<head>
<%@include file="../common/meta.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/css.jsp"%>
<%@include file="../common/ext.jsp"%>


<title>报警历史页面</title>
<link href="" rel="SHORTCUT ICON" />
</head>
<body id="other"></body>
<script type="text/javascript">
Ext.onReady(function(){
	//自动引入其他需要的js
	 Ext.require([
        "Ext.grid.*",
        "Ext.toolbar.Paging",
        "Ext.form.*",
        "Ext.data.*"
	]);
	Ext.QuickTips.init();
	
	//建立Model模型对象
	Ext.define("alarmHistory",{
		extend:"Ext.data.Model",
		fields:[
			{name: "id",mapping:"id"}, 
			{name: "location"}, 
			{name: "department"},
			{name: "equipment"},
			{name: "maxValue"},
			{name: "minValue"},
			{name: "alarmValue"},
			{name: "device"},
			{name: "alarmDate",mapping:"alarmDate.time",type:"date",dateFormat:"time"}
		]
	});
	//建立数据Store
	var alarmStore=Ext.create("Ext.data.Store", {
        pageSize: commonSize,
        model:"alarmHistory",
        remoteSort:true,
		proxy: {
            type:"ajax",
		    url: "${ctx}/alarmHistory/getHistorys.action",
		    reader: {
			     totalProperty: "totalSize",
			     root: "list"
		    },
        simpleSortMode :true
        },
        sorters:[{
            property:"id",
            direction:"ASC"
        }]
	});
	
	//行选择模型
	var sm=Ext.create("Ext.selection.CheckboxModel",{
    	listeners: {
	        selectionchange: {
	            element: "el", 
	            fn: function(){
				}
	        }
		}
    });
	var cm=[{xtype: "rownumberer",text:"序号",width:30,align:"left"},
            {header: "ID",width: 70,dataIndex: "id",hidden: true,menuDisabled: true},
            {header: "位号",width: 200,dataIndex: "location",menuDisabled: false},
            {header: "部门",width: 200,dataIndex: "department",menuDisabled: true},
            {header: "装置",width: 200,dataIndex: "equipment",menuDisabled: true},
            {header: "上限值",width: 200,dataIndex: "maxValue",menuDisabled: true},
            {header: "下限值",width: 200,dataIndex: "minValue",menuDisabled: true},
            {header: "报警值",width: 200,dataIndex: "alarmValue",menuDisabled: true},
            {header: "设备名",width: 200,dataIndex: "device",menuDisabled: true},
            {header: "报警时间",width: 200,dataIndex: "alarmDate",menuDisabled: true,
            	renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')}];
	
	//grid组件
	var alarmGrid =  Ext.create("Ext.grid.Panel",{
		columnLines: true,
		width: "100%",
		height: document.body.clientHeight-50,
		id: "alarmGrid",
		bbar:  Ext.create("Ext.PagingToolbar", {
			store: alarmStore,
			id: "pagger",
			displayInfo: true,
			displayMsg: "显示第 {0} 条到 {1} 条记录，一共 {2} 条",
			emptyMsg: "没有数据"
		}),
        selModel:sm,
     	forceFit : true,
		store: alarmStore,
		autoScroll: true,
		stripeRows: true,
		columns:cm,
		tbar: ["->",{text:"添加",id:"addAlarm",iconCls: "add-button",handler: function(){
				FormWin.show();
    		}},"-",
			{text:"修改",id:"updateAlarm",disabled: false,iconCls: "edit-button",handler:function(){
				Ext.Ajax.request({ 
				 		url: '${ctx}/alarmHistory/addOrUpdateHistory.action',
					    method: "post",
					    params:{location: "xx"}, 
					    success: function(response, config){ 
					    	 Ext.example.msg('提示信息','修改数据');
					    }, 
					    failure: function(){ 
					        Ext.MessageBox.alert("提示信息" , "请求失败" ); 
					    }
					});
			}},"-",
			{text:"删除",id:"deleteAlarm",disabled:false,iconCls: "delete-button",handler:function(btn){
				  Ext.example.msg('提示信息','删除数据');
			}}]
	});
	alarmStore.loadPage(1);
	
	var alarmForm = Ext.create('Ext.form.Panel', {
		bodyStyle :'padding:10 0 0 0',
        defaultType: 'textfield',
        border: false,
        fieldDefaults: {
	        msgTarget : 'side',
            labelAlign: 'right',
            labelWidth: 60,
            anchor: '-20'// anchor width by percentage
        },
        items: [{
            fieldLabel: '位号',
            allowBlank : false,
            id: 'location'
        }, {
            fieldLabel: '部门',
            id: 'organment'
        }, {
        	fieldLabel: '装置',
        	id: 'equipment'
        }, {
        	fieldLabel: '上限值',
        	id: 'maxValue'
        }, {
        	fieldLabel: '下限值',
        	id: 'minValue'
        }, {
        	fieldLabel: '报警值',
        	id: 'alarmValue'
        }, {
        	fieldLabel: '设备名',
        	id: 'device'
        }, {
        	fieldLabel: '报警时间',
        	id: 'alarmDate'
        }]
    });
	
	var FormWin=Ext.create("Ext.window.Window", {
	    title : "添加信息",
	    closable : true,
	    width : 400,
	    height : 350,
	    layout : "fit",
	    modal : true,
	    closeAction : "hide",
	    items : alarmForm,
	    buttonAlign : "center",
	    buttons : [ {
	        text : "保存",
	        handler : function() {
	        	alert(Ext.getCmp("location").getValue());
				 /* Ext.Ajax.request({ 
				 	url: '${ctx}/alarmHistory/addOrUpdateHistory.action',
					    method: "post",
					    params:{location: Ext.getCmp("location").getValue() }, 
					    success: function(response, config){ 
					     var json = Ext.JSON.decode(response.responseText); 
					     if(json.success==true){
					     	Ext.MessageBox.alert("提示信息",json.msg);
					     	FormWin.hide();
					     }
					    }, 
					    failure: function(){ 
					        Ext.MessageBox.alert("提示信息" , "请求失败" ); 
					    }
					});*/
	        }
	    }, {
	        text : "关闭",
	        handler : function() {
	        	FormWin.hide();
	        }
	    }]
	});
                    
	
	
	
	
	//树
	
	 var treeStore = Ext.create("Ext.data.TreeStore", {
	        proxy: {
	            type: "ajax",
	            url: "${ctx}/alarmHistory/getEquipmentTree.action"
	        }
	    });
	 
	 var treePanel=Ext.create("Ext.tree.Panel", {
	       	store: treeStore,
	     	id:"gradeTree",
	        height: document.body.clientHeight,
	        width: 250,
	        useArrows: true,
	        root: {
	        	text:"部门装置",
	        	id:"0"
	        },
	        rootVisible : true,
			tbar:new Ext.Toolbar({
				style:"border-top:0px;border-left:0px",
				items:[{
		            iconCls: "icon-expand-all",
		            text:"展开",
					tooltip: "展开所有",
		            handler: function(){ treePanel.getRootNode().expand(true); },
		            scope: this
		        },"-",{
		            iconCls: "icon-collapse-all",
		            text:"折叠",
		            tooltip: "折叠所有",
		            handler: function(){ treePanel.getRootNode().collapse(true); },
		            scope: this
		        }]
	        })
	    });
	 treePanel.on("itemclick",function(view,record,item,index,e,opts){  
	     //获取当前点击的节点  
	      var treeNode=record.raw;  
	      var text=treeNode.text;  
	      alert(text);
 	});
     
	
	
	
	
	
	//视图
	new Ext.Viewport({
		layout: "border",
		renderTo: "other",
		items: [ {
			region: "west",
			border: false,
			items: [treePanel]
		},{
			region: "center",
			border: false,
			items: [alarmGrid]
		}]
	});
	
});
</script>
</html>
