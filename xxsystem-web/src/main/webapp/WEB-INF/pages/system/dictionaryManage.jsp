<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>字典管理</title>
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

Ext.namespace('dictionary'); 

dictionary.manage = function() { 
	var grid;
	var tree;
	var hiddenPkgs = [];

	var filter;

	function getTreeFilter(){
		if(!filter){
			filter = new Ext.tree.TreeFilter(dictionary.manage.tree, {
		 	  	clearBlank: true,
		 	  	autoClear: true
		 	});
		}
		return filter;
	}
	
	//得到grid中选择记录的id,例如1,2,3
	function getSelectRecordIds(){
	    var records = dictionary.manage.grid.getSelectionModel().getSelections();
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
  
    // 公共空间 
    return { 
        setGrid : function(grid){
			this.grid = grid;
        },

        setTree : function (tree){
            this.tree = tree;
        },

        //添加字典
        addDictionary : function(){
        	var curNode = this.tree.getSelectionModel().getSelectedNode();
        	var addDictionaryForm = new Ext.form.FormPanel({
        	    bodyStyle :'padding:15px 5px 0 0',
        		border :false,
        	    layout : 'form', 
        	    labelAlign:'right',
        	    labelWidth:80,
        	    defaults:{
        	      	width:200,
        	      	allowBlank:false
        	    },
        	    items:[{
			        xtype:'hidden',
			        name:'dictionary.code',
			        value: curNode.attributes.code
			    },{
			    	fieldLabel:'字典类型',
        	        xtype: 'box',
        	        style:'padding-top:5px;',  
        	        html: '<div style="width:200px;">'+curNode.attributes.text+'</div>'    
			    },{
        		    xtype : 'textfield',
        	        fieldLabel:'字典名称',
        	        name:'dictionary.name',
        	        blankText : '字典名称不能为空', 
        	        maxLength:200,
        	        msgTarget : 'side'
        	    }]
        	});

        	var addDictionaryWin = new Ext.Window({
        		title:'添加字典',
                closable:true,
                width:360,
                height:150,
                modal:true,
                plain:true,
                layout :"fit",

                items: [
					addDictionaryForm
                ],
                buttonAlign:'center', 
            	buttons:[{
            		text:'保存',
            	    handler:function(){
                	    if(!addDictionaryForm.getForm().isValid()){
                	    	return;
                	    }
                	    addDictionaryForm.getForm().submit({
        	                url:'addDictionary.do',
        	                success:function(form,action){
        	                	Ext.tooltip.msg('yes', action.result.msg);
        	                	addDictionaryWin.close(); 
                	    		dictionary.manage.grid.getStore().reload();
                      	    },
        	              	failure:function(form,action){
                      	    	Ext.tooltip.msg('no', action.result.msg);
        	              	}
                        });
            	    }
            	    },{
            	       	text:'重置',
            	       	handler:function(){
							addDictionaryForm.getForm().reset();
            	       	}
            	    },{
            	       	text:'关闭',
            	       	handler:function(){
            	    		addDictionaryWin.close();
            	       	}
            	    }]
            });
            
        	addDictionaryWin.show();
        },

        //删除字典
        deleteDictionary : function(){
        	var ids = getSelectRecordIds();//所选择删除行的ID
   	        if(ids==false){
   	       		Ext.tooltip.msg('no', '请选择要操作的行！');
   	       		return
   	        }
   	        
   	       	Ext.MessageBox.confirm('信息提示：', '确定要删除这'+dictionary.manage.grid.getSelectionModel().getSelections().length+'条数据?', function(btn){
  	       	   if(btn == 'yes'){
   	       	    	Ext.Ajax.request({
	   	       	   		url: 'deleteDictionary.do',
	   	       	    	params: { ids: ids},
	   	       	    	success: function(response, opts) {
	   	       	    	  	var result = Ext.decode(response.responseText);
						 	var flag = result.success;
				 	      	if(flag){
				 	      		Ext.tooltip.msg('yes', result.msg);
				 	      		dictionary.manage.grid.getStore().reload();
				 	      	}else{
				 	      		Ext.tooltip.msg('no', result.msg);
					 	    }
   	       	    	   	}
  	       	    	});
   	       	    }
   	       	});
        },

        //搜索字典类型
        filterDictionaryType : function (e){
        	var filter = getTreeFilter();
         	
       		var text = e.target.value;
       		Ext.each(dictionary.manage.hiddenPkgs, function(n){
       			n.ui.show();
       		});
       	  	
       	 	if(!text){
       	 		filter.clear();           
       	        return;
       	    }  

       	 	dictionary.manage.tree.expandAll();

       		var re = new RegExp(Ext.escapeRe(text), 'i');
       	     
       		filter.filterBy(function(n){
       	    	var textval = n.text;
       	        return !n.isLeaf() || re.test(n.text);
       	    });

       	 	dictionary.manage.hiddenPkgs = [];
       	 	dictionary.manage.tree.root.cascade(function(n) {
       			if(!n.isLeaf()&& n.ui.ctNode.offsetHeight<3){
       	        	n.ui.hide();
       	        	dictionary.manage.hiddenPkgs.push(n);
       	        }
       	    });
       	 },

       	resetDictionaryType : function (){
			var filter = getTreeFilter();
       	 	filter.clear();     
        },

        changePosition : function(id,targetId){
        	 Ext.Ajax.request({
     	    	url:'changePosition.do',
     	    	params:{
     	    		id : id,
     	    		targetId:targetId
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
    }; 
}();

Ext.onReady(function() {
	//------------------------------字典类型树开始---------------------------------------------------
	var dictionaryTreeLoader = new Ext.tree.TreeLoader({
		preloadChildren: true,
		clearOnLoad: false,
		url : 'getDictionaryTypeList.do'
	});
	
	dictionaryTreeLoader.on('beforeload', function(treeLoader, node) {}, this);
	
	var treePanel = new Ext.tree.TreePanel({
    	title: '字典类型',
        region: 'west',
        width:200,
        minSize: 200,
        maxSize: 280,
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
        enableDD: false,
        tbar : new Ext.Toolbar({
		    style:'border-top:0px;border-left:0px;padding-top:4px;padding-left:4px;',
		    items:[
				new Ext.form.TextField({
					width:150,
					id : 'txtDictionaryType',
					emptyText:'查找字典类型',
			    	enableKeyEvents: true,
					listeners:{
						render: function(f){
					      	f.el.on('keydown', dictionary.manage.filterDictionaryType, f, {buffer: 350});
					    }
					}
				}),
				' ',
				{
	       	    	text :   "重置", 
	       	    	iconCls: "reset-button",
	       	    	handler:function(){
	       	   			Ext.getCmp('txtDictionaryType').setValue("");
	       	   			dictionary.manage.resetDictionaryType();
	       			} 
	       	   	}
			]
	    }),
        root: {
	        nodeType: 'async',
	      	text: '字典类别',
	    	leaf:false,
	    	code : '',
	    	draggable: false
	    },
        
        loader : dictionaryTreeLoader,

        listeners:{
			'click' : function(node, event) {
	    		Ext.getCmp('btnAddDictionary').setDisabled(false);
	    		dictionary.manage.grid.getStore().setBaseParam("code",node.attributes.code);
	    		dictionary.manage.grid.getStore().load({params:{start:0,limit:20}});
				treePanel.setTitle('字典类型('+ node.attributes.text +')')
			}
		}
		
    });

	dictionary.manage.setTree(treePanel);
	//------------------------------字典类型树结束---------------------------------------------------

	//------------------------------字典grid开始-------------------------------------------------
	var record = Ext.data.Record.create([
 		{name:'id',type:'int'},
 		{name:'name',type:'string'},
 		{name:'type',type:'string'},
 		{name:'code',type:'string'}
 	]);

   	var store = new Ext.data.Store({
   		proxy:new Ext.data.HttpProxy({url: 'getDictionaryList.do'}),
   		reader:new Ext.data.JsonReader({
   		   totalProperty:'totalSize',
   		   root:'list'
   		},record)
   	});
                            	
    var rm = new Ext.grid.RowNumberer();
    var sm = new Ext.grid.CheckboxSelectionModel({
		listeners : {
			"selectionchange" : function(win) {
				if (grid.getSelectionModel().getSelections() != "") {
					var currentRecords = grid.getSelectionModel().getSelections();
					if (currentRecords.length>1) {
			       		Ext.getCmp('btn_up').setDisabled(true);
						Ext.getCmp('btn_down').setDisabled(true);
					}else {
						Ext.getCmp('btnDeleteDictionary').setDisabled(false);
						Ext.getCmp('btn_up').setDisabled(false);
						Ext.getCmp('btn_down').setDisabled(false);
					}
				} else {
					Ext.getCmp('btnDeleteDictionary').setDisabled(true);
					Ext.getCmp('btn_up').setDisabled(true);
					Ext.getCmp('btn_down').setDisabled(true);
				}
			}
		}
	});
	
  	var cm = new Ext.grid.ColumnModel([
  	 	rm,sm,
  		{
  	  		header:'字典名称（双击修改）',
  	  		dataIndex:'name',
	  	  	menuDisabled : true,
			editor : new Ext.form.TextField({
				maxLength : 200,
				allowBlank : false,
				blankText : '字典名称不能为空' 
			})
  	  	}
  	]);
  	cm.defaultSortable = true;
	
   	var grid = new Ext.grid.EditorGridPanel({
   		region:'center',
   		stripeRows: true,
   		bodyStyle :'margin: 0px;border-right:0px',
   		loadMask:true,
   		columnLines: true,
   		trackMouseOver:true, //鼠标特效
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
			afteredit : function(e){
				var row = e.record;
				var id = row.get("id");
				var newName = e.value; //修改后的值,  e.originalValue 修改前的值 grid.getStore().rejectChanges();
	         	Ext.Ajax.request({
			    	url:'updateDictionary.do',
			    	params:{
			    		id : id, 
			    		name : newName 
			    	},
			    	success: function(response, opts) {
						var result = Ext.decode(response.responseText);
					 	var flag = result.success;
			 	      	if(flag){
			 	      		Ext.tooltip.msg('yes', result.msg);
			 	      		grid.getStore().reload();
			 	      	}else{
			 	      		Ext.tooltip.msg('no', result.msg);
				 	    }
			   	   	}
	    	 	});
			}
   		},
   		tbar : new Ext.Toolbar({
			height: 40,
			style:'padding:10px 10px 0px 10px;border-top:0px;border-right:0px',
			items:[
	   	    	'字典名称：',
	   	    	new Ext.form.TextField({
		    		id:'name',
		    		width:'135'
		    	}),
		    	'&nbsp;',
	   	    	{
	       	    	text :   "查询", 
	       	    	iconCls: "search-button", 
	       	    	handler:function(){
	   		    		store.setBaseParam("name",Ext.getCmp('name').getValue());
	       	    		store.load({params:{start:0,limit:20}});
	       			} 
	       	   	},
	   		    '-',
	   	    	{
	       	    	text :   "重置", 
	       	    	iconCls: "reset-button",
	       	    	handler:function(){
	       	   			Ext.getCmp('name').setValue("");
	       			} 
	       	   	},
	       	 	'->',
	       	 	{
		       	   	text :  '', 
	       	    	iconCls: "up",
	       	    	id:'btn_up',
	       	    	text : "上移", 
	       	    	disabled : true,
	       	    	tooltip:'上移',
	       	    	handler:function(){
				       	var currentStore = grid.getStore();
				       	var currentRecord = grid.getSelectionModel().getSelected();
				       	var index = currentStore.indexOf(currentRecord);
				       	var tagart_index = index-1;
						if (index==0) {
							Ext.tooltip.msg('no', '已经在最上面，不能上移！');
							return;
						}
				       	var targetRecord = currentStore.getAt(tagart_index); // 获取插入行的记录
				       	var currendId = currentRecord.get('id');
				       	var targetId = targetRecord.get('id');
				       	if (index==-1) {
							Ext.tooltip.msg('no', '请选中一行再上移！');
							return;
						}
						//上移
						if (index > 0) {
							currentStore.remove(currentRecord);
							currentStore.insert(index - 1, currentRecord);
							currentStore.remove(targetRecord);
							currentStore.insert(index, targetRecord);
							grid.getSelectionModel().selectRow(index - 1);

							dictionary.manage.changePosition(currendId,targetId);
						 }
	       			}
		       	 },
		       	'&nbsp;',
		       	{
		       	   	text :  '', 
	       	    	iconCls: "down",
	       	    	id:'btn_down',
	       	    	text : "下移", 
	       	    	disabled : true,
	       	    	tooltip:'下移',
	       	    	handler:function(){
						var currentStore = grid.getStore();
				       	var currentRecord = grid.getSelectionModel().getSelected();
				       	var index = currentStore.indexOf(currentRecord);
						if (index == currentStore.getCount() - 1) {
							Ext.tooltip.msg('no', '已经在最下面，不能下移！');
							return;
						}
				       	var tagart_index = index+1;
				       	var targetRecord = currentStore.getAt(tagart_index); // 获取插入行的记录
				        var currendId = currentRecord.get('id');
				       	var targetId = targetRecord.get('id');
						if (index==-1) {
							Ext.tooltip.msg('no', '请选中一行再下移!');
							return;
						}
						//下移
						if (index < currentStore.getCount() - 1) {
							currentStore.remove(currentRecord);
							currentStore.insert(index + 1, currentRecord);
							currentStore.remove(targetRecord);
							currentStore.insert(index, targetRecord);
							grid.getSelectionModel().selectRow(index + 1);
							 
							dictionary.manage.changePosition(currendId,targetId);
						}
	       			}
		       	 },
		     	'&nbsp;',
	       	 	{
					text : "添加",
	    	    	iconCls: "add-button",
	    	    	id : 'btnAddDictionary',
	    	    	disabled : true,
	    	    	handler:function(){
	       	 			dictionary.manage.addDictionary();
	       	 		}
	    	   	},
	    	   	'-',
	    		{
					text : "删除",
	    	    	iconCls: "delete-button",
	    	    	id : 'btnDeleteDictionary',
	    	    	disabled : true,
	    	    	handler:function(){
	    	   			dictionary.manage.deleteDictionary()
	       	 		}
	    	   	}/* ,
				'-',
	   	    	{
	    	    	iconCls: "help-button",
	    	    	tooltip: '帮助',
	    	    	handler: function() {
	    	    		if(Ext.getCmp("helpWin")) {
	    	    			Ext.getCmp("helpWin").close();
	    	    		}
	    	    		openHelpWin("5.9.4");
	       	   		}
	       	   	} */
	       	]
   		}), 
   	   	bbar:new Ext.PagingToolbar({
   	  		 style:'border-right:0px;border-bottom:0px;',
   	         pageSize: 20,
   	         store: store,
   	         displayInfo: true,
   	         displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
   	         emptyMsg: "无数据",
   	         plugins: new Ext.ux.ProgressBarPager()
   	    }),
   	    plugins:new Ext.ux.PanelResizer({
   	        minHeight: 100
   	    })
   	});
   	
   	dictionary.manage.setGrid(grid);
   	
	new Ext.Viewport({
	    layout: 'border',
	    bodyStyle :'margin: 0px;border:0px',
	    items: [
           treePanel,
          grid
	    ]
	});
});
</script>
