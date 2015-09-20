/**
 * @date 2015-05-27
 * 设置EXTJS控制的默认值，为控制添加默认监听事件或行为等。
 */

/**
 * 初始化消息提示
 */
Ext.QuickTips.init();

/**
 * @author hedjaojun
 * @date 2015-05-28
 * 
 * 默认显示分页信息
 */
Ext.define('Ext.ux.pagingToolbarOverride', {
	override : 'Ext.PagingToolbar',
	displayInfo : true // 第 {0} - {1} 条，共 {2} 条
});

/**
 * @author hedjaojun
 * @date 2015-05-28
 * 
 * 设置表格单元格在鼠标移动上去后，默认出现提示信息
 */
Ext.define('Ext.grid.column.ColumnOverride', {
	override : 'Ext.grid.column.Column',
	menuDisabled: true,
	renderer : function(v , ctx , record){ 
	    ctx.tdAttr = 'data-qtip="' + v + '"';
	    return v; 
	}
});

/**
 * @author zenchao
 * @date 2015-05-29
 * 
 * 设置默认没有排序功能
 */
Ext.define('Ext.grid.header.ContainerOverride', {
	override : 'Ext.grid.header.Container',
	defaults: { // defaults 将会应用所有的子组件上,而不是父容器
		sortable : false
	}
});

/**
 * @author hedjaojun
 * @date 2015-05-29
 * 
 * 设置分页条数为 99
 */
Ext.define('Ext.data.StoreOverride', {
	override : 'Ext.data.Store',
	pageSize: 99
});

/**
 * @author hedjaojun
 * @date 2015-05-29 定义数据格式
 */
Ext.define('Ext.ux.data.proxy.Format', {
    extend: 'Ext.data.proxy.Ajax',
    alias: 'proxy.format',
    actionMethods: {
		read: 'POST'
    },
    reader: {
        type: 'json',
        root: "list",
        totalProperty: "totalSize",
        messageProperty: "msg"
    },
    listeners: {
        exception : function (proxy, response, options, epots) {
            sshframe.FailureProcess.Proxy.apply(this, arguments);
        }
    }
});

 /**
	 * @author zengchao
	 * @date 2015-05-29
	 * 
	 * @edit hedaojun
	 * @data 2015-06-03 复选框,实现根据选择的记录条数，disable 删除，添加等按钮。 使用时，需要为CheckboxModel
	 *       添加 使用时，在列表的button配置里添加 属性 disabledExpr 例：
	 *       disabledExpr:"$selectedRows != 1 || $status =='1'" $selectedRows
	 *       表示选中的记录数不等于1,或者选择行记录的状态为禁用 $status 为modal中定义的字段。
	 * 
	 */
Ext.define("Ext.selection.CheckboxModelOverride", {
	override : 'Ext.selection.CheckboxModel',
	injectCheckbox : 1,
	listeners :{
	    "selectionchange" : function (checkModel){
	    	// 定义计算表达式的函数。
	    	var evalExpr = function(expr){
				var result = false;
				try{ result = eval(expr);  }catch(e){
					 // alert('禁用按钮表达示错误: '+e.name+' '+e.message);
				 }
				 return result;
			}
			var ownerCt=checkModel.view.initialConfig.grid;
	        var selectRows = ownerCt.getSelectionModel().getSelection();
			var selLenth=selectRows.length;
			var buttons = Ext.ComponentQuery.query('button',ownerCt);
			var exps,disabled;
			for(var i=0;i<buttons.length;i++){
				 if(buttons[i].disabledExpr){
					 var exps = buttons[i].disabledExpr;
					 // selLenth为前面定义的选择的行数
					 exps = exps.replace(/\$selectedRows/g, 'selLenth');
					 if(selLenth==0){
						 disabled = evalExpr(exps);
					 }else{
						 for(var j = 0; j<selLenth ; j++) {
							 var data = selectRows[j].data;
							 exps = exps.replace(/\$/g, 'data.');
							 disabled = evalExpr(exps);
							 if(disabled === true){
							 	break;
							 }
						}
					 }
					 if(disabled === true){
						 	buttons[i].setDisabled(true);
					 }else{
						 buttons[i].setDisabled(false);
					 }
				}
			}
		}
    }
});

/**
 * @author hedjaojun
 * @date 2015-05-28
 * 
 * 设置表格列边框，强制适应页面等
 */
Ext.define('Ext.grid.PanelOverride', {
	override : 'Ext.grid.Panel',
	columnLines : true, // 列边框线
	forceFit : true, // 列表自适应页面
	autoScroll: true,
	stripeRows: true
});

 /**
	 * @author zengchao
	 * @date 2015-06-01
	 * 
	 * 弹出框中的Panel
	 */
Ext.define("Ext.form.PanelOverride",{
	extend : 'Ext.form.Panel',
	frame: false,   
	layout:'column',
	bodyPadding: 0, 
	region:'east',
	border: 0,
	border:false,
	defaults :{
		border:false,
		bodyStyle :'padding-right:10px'
	},
	fieldDefaults: {  
		labelWidth: 80,
		labelAlign: 'right',
		anchor: '100%'
	}
});

Ext.define("Ext.form.action.ActionOverride",{
    override : 'Ext.form.action.Action',
    waitTitle : '请等待...',
    waitMsg : '正在提交...'
});

 /**
	 * @author zengchao
	 * @date 2015-06-01
	 * 
	 * 为弹出的form中的录入控件增加必填项标示*
	 * 
	 */
Ext.override(Ext.form.field.Base,{// 针对form中的基本组件
　　initComponent:function(){
        if(this.allowBlank!==undefined && !this.allowBlank){
        	if(this.fieldLabel){
        		this.fieldLabel = "<span style='color:red;'>*</span>" + this.fieldLabel;
        	}
        }
        this.callParent(arguments);
　　}
});
Ext.override(Ext.container.Container,{// 针对form中的容器组件
			initComponent:function(){ 
			if(this.allowBlank!==undefined && !this.allowBlank){ 
			if(this.fieldLabel){ this.fieldLabel = "<span style='color:red;'>*</span>" + this.fieldLabel;
		     }
		}
			this.callParent(arguments); 
   }
});

 /**
	 * @author zengchao
	 * @date 2015-06-01
	 * 
	 * 弹出框windows 在不同的展示中可能会对layout进行多种调整，默认为auto，这样的窗口会从上至下的去展示items中的组件；
	 * 
	 */
Ext.define("Ext.window.WindowOverride",{
	override : 'Ext.window.Window',
	closable: true,
	resizable: false,
	buttonAlign: "center",
	modal: true,
	layout : 'fit',
	closeAction : 'destroy'
});

 /**
	 * @author zengchao
	 * @date 2015-06-02
	 * 
	 * xtype:'combo'在from表单中 默认显示为 请选择
	 * 
	 */
Ext.define("Ext.form.ComboBoxOverride",{
	override : 'Ext.form.ComboBox',
	typeAhead:false,
	editable:false,
	queryMode: 'local'
});

/**
 * @author hedaojun
 * @date 2015-06-30
 * 根据表达式判断返回一个布尔值的方法
 *
 */
var evalExpr = function(expr){
	var result = false;
	try{
		result = eval(expr);
	 }catch(e){
		 Ext.Msg.showError('计算表达式错误: '+e.name+' '+e.message);
	 }
	 return result;
};

/**
 * 根据表达式判断返回一个布尔值
 * @author hedaojun
 * @date 2015-06-30
 */
var evalExpr = function(expr){
	var result = false;
	try{
		result = eval(expr);
	 }catch(e){
		// alert('计算表达式错误: '+e.name+' '+e.message);
	 }
	 return result;
};
/**
 * @author zengchao
 * @date 2015-06-30
 * grid.column中的操作列，图标按钮控制，需要配置showExpr表达式参数来确定根据什么条件显示此按钮
 * 例： showExpr:'$d$=="同意"||$e$==3'  /$d$=="同意" 表示model中取得的行数据等于 "同意"的时候显示
 *
 */
Ext.define('Ext.grid.column.ActionOverride', {
	override : 'Ext.grid.column.Action',
	iconClsArray : new Array(),
	renderer : function (value, cellmeta, record, rowIndex, columnIndex, store) {
		for (var i = 0; i < this.items.length; i++) {
			var exps = this.items[i].showExpr;
			this.iconClsArray.push(this.items[i].iconCls);
			var patt = new RegExp('&&');
			var relation = patt.test(exps);
			//存在&&于关系
			if (exps && relation == true) {
				var showExprArray = exps.split("&&");//拆分表达式
				for (var x in showExprArray) {//遍历判断
					var expses = showExprArray[x]
					if (ExprJudge(expses) == false) {
						this.items[i].iconCls = 'null-button';
						break;
					} else {
						this.items[i].iconCls = this.iconClsArray[i];
					}
				}
			} 
			//存在||或关系
			else if (exps && relation == false) {
				showExprArray = exps.split("||");
				for (var x in showExprArray) {
					var expses = showExprArray[x]
					if (ExprJudge(expses) == true) {
						this.items[i].iconCls = this.iconClsArray[i];
						break;
					} else {
						this.items[i].iconCls = 'null-button';
					}
				}
			}
		}
		//字段组合成表达式并判断返回布尔值
		function ExprJudge(expses) {
			var str = expses.substring(expses.lastIndexOf("$") + 1, expses.length);
			var parameter = expses.substring(expses.indexOf("$") + 1, expses.lastIndexOf("$"));
			var Exprvalue = record.get(parameter);
			if (typeof(Exprvalue) === 'string') {
				disabled = evalExpr('"' + Exprvalue + '"' + str);
				return disabled;
			} else {
				disabled = evalExpr(Exprvalue + str);
				return disabled;
			}
		}
	}
});


 /**
 * @author zengchao
 * @date 2015-07-01
 * 
 * 创建searchButton按钮类，按钮作用于gird中tbar下的查询按钮
 * 1， 
 * 2，遍历tbar下的查询条件，对存在dataIndex的控件提取值，用于对columns列隐藏；
 * 例：dataIndex=a&b，标示columns中dataIndex值为a和b的都隐藏
 * 注：在显示的时候如果前置条件存在a值并且隐藏掉了，那么当前条件如果dataIndex=a&b，并且值为全部的时候a不会被显示出来
 */
Ext.define("Ext.SearchBtn", {
	extend : 'Ext.Button',
	xtype : 'searchBtn',
	text : "查询",
    iconCls : "search-button",
	handler : function () {
		var tbarItems = this.ownerCt.items;
		var myGrid=this.ownerCt.ownerCt;
		var gridColumns = this.ownerCt.ownerCt.columns;
		var dataIndexArray=new Array();
		for (var i = 0; i < tbarItems.length; i++) {
			var hideIndex =(tbarItems.items)[i].dataIndex
			var patt = new RegExp('&');
			var relation = patt.test(hideIndex);
		    if (relation == false&&hideIndex) {
		    	dataIndexArray.push(hideIndex);
		    }
		}
		for (var i = 0; i < tbarItems.length; i++) {
			if ((tbarItems.items)[i].dataIndex && (tbarItems.items)[i].value != 'all') {
				var hideIndex = (tbarItems.items)[i].dataIndex
				var hideIndexArray = hideIndex.split("&"); //拆分表达式
				for (var y in hideIndexArray) { //遍历判断
					for (var x in gridColumns) {
						if (myGrid.columns[x].dataIndex == hideIndexArray[y]) {
							gridColumns[x].hide();
						}
					}
				}
			} else if ((tbarItems.items)[i].dataIndex && (tbarItems.items)[i].value == 'all') {
				
				var hideIndex = (tbarItems.items)[i].dataIndex
				var hideIndexArray = hideIndex.split("&"); 
				var patt = new RegExp('&');
			    var relation = patt.test(hideIndex);
				if (relation == true) {
				for(var z in dataIndexArray){
					Ext.Array.remove(hideIndexArray,dataIndexArray[z])
				}
				for (var y in hideIndexArray) {
					for (var x in gridColumns) {
						if (myGrid.columns[x].dataIndex == hideIndexArray[y]) {
							gridColumns[x].show();
						}
					}
				}
			  }else if(relation == false){
				 for (var y in hideIndexArray) {
					for (var x in gridColumns) {
						if (myGrid.columns[x].dataIndex == hideIndexArray[y]) {
							gridColumns[x].show();
						}
					}
				}
			  }
			  
			}
		}
	}
});