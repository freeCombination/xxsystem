<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>日志管理</title>
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
	<script type="text/javascript">
		var globalBase = "${ctx}";
		var imageUrl = globalBase + '/images/s.gif';
		Ext.BLANK_IMAGE_URL = imageUrl;
		Ext.QuickTips.init();

		Ext.namespace('log'); 

		log.manage = function() { 
			var grid;
			 // 公共空间 
		    return { 
				setGrid : function (grid){
					this.grid = grid;
		        },
		    	viewLog : function(){
		    		var row = log.manage.grid.getSelectionModel().getSelections()[0];
		    		
		        	var viewLogForm = new Ext.form.FormPanel({
		        		bodyStyle :'padding:20 0 0 0', 
					    layout : 'form', 
					    border:true,
					    labelAlign:'right',
					    labelWidth:80,
					    defaultType:'textfield',
					    autoScroll:true,
					    defaults:{
					        width:300,
					      	allowBlank:false,
					      	msgTarget : 'side'//在该组件的下面显示错误提示信息   取值为'qtip','side','title','under'
					    },
					    items:[{
				        	fieldLabel:'IP地址',
		        	        xtype: 'box',
		        	        style:'padding-top:3px;',  
		        	        html: '<div style="width:300px;">'+row.get('ip')+'</div>'    
					    },{
					    	fieldLabel:'操作人',
		        	        xtype: 'box',
		        	        style:'padding-top:3px;',  
		        	        html: '<div style="width:300px;">'+row.get('submitUser')+'</div>'    
					    },{
					    	fieldLabel:'操作时间',
		        	        xtype: 'box',
		        	        style:'padding-top:3px;',  
		        	        html: '<div style="width:300px;">'+ new Date(row.get('submitDate')).format('Y-m-d H:i:s') +'</div>'  
					    },{
					    	fieldLabel:'操作内容',
		        	        xtype: 'box',
		        	        style:'padding-top:3px;',  
		        	        html: '<div style="width:300px;height:100px;overflow:auto;overflow-x:hidden;">'+row.get('content')+'</div>'  
					    }]
					});
		    		var win = new Ext.Window({
						title:'查看日志',
						width:450,
						height:290,
						layout :"fit",
						plain:true,
						modal:true,
						border:false,
						closable:true,
						buttonAlign:'center',
						
						items: [
							viewLogForm
				        ],
				        
						buttons:[{
							text:'关闭',
							handler:function() {
								win.hide();
							}
						}]
					}); 	
					 win.show();
		    	 } 
		  	  }  
		  	 
		}();

		Ext.onReady(function() {
			var logRecord = Ext.data.Record.create([
				{name:'id' , type:'int'},
				{name:'ip' , type:'string'},
				{name:'submitUser' , type:'string'},
				{name:'content' , type:'string'},
				{name:'submitDate',type : 'date', mapping : 'submitDate.time', dateFormat : 'yyyy-MM-dd HH:mm:ss',
		       		convert : function(value) { 
		       	    	return new Date(value);   
		       	    }
		  	    }
			]);
			
			var store = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({url: 'getLogList.do'}),
				reader:new Ext.data.JsonReader({
					totalProperty: 'totalSize',
					root:'list'
				} , logRecord)
			});
			
			var rm = new Ext.grid.RowNumberer();
			
			var cm = new Ext.grid.ColumnModel([
				rm,
				{header:'IP地址', dataIndex:'ip' , sortable:true , width:40},
				{header:'操作人'	, dataIndex:'submitUser' ,sortable:true ,width:40},
				{header:'操作时间',dataIndex:'submitDate',
					renderer : function(value) {
					 	return new Date(value).format('Y-m-d H:i:s'); 	
					},
					sortable : true,
					width:40
				},
				{header:'操作内容', dataIndex:'content', sortable:false}
			]);
			
			cm.defaultSortable = true;
			
			var grid = new Ext.grid.GridPanel({
				bodyStyle:'border-bottom:0px;border-left:0px;border-right:0px',
				stripeRows: true,
				loadMask:true,
				columnLines:true,
				store:store,
				cm:cm,
				sm:new Ext.grid.RowSelectionModel({singleSelect : true}),
				viewConfig:{
					columnsText:'显示的列',
					scrollOffset:-1,
					sortAscText:'升序',
					sortDescText:'降序',
					forceFit:true
				},
				
				listeners:{
					rowdblclick: function(gridThis,rowIndex,e){
		   			    var model = log.manage.grid.getSelectionModel();
		   			 model.selectRow(rowIndex);
		   			    log.manage.viewLog();
		   			}
				},
				
				tbar:new Ext.Toolbar({
					height: 40,
					style:'padding:10px 10px 0px 10px;border-top:0px;border-right:0px;border-left:0px',
					items:[
					'操作人：',
					' ',
					new Ext.form.TextField({
						width:100,
						id:'submitUser'
					}),
					'&nbsp;',
					'操作关键字：',
					' ',
					new Ext.form.TextField({
						width:100,
						id:'content'
					}),
					'&nbsp;',
					'开始时间：',
					' ',
					new Ext.form.DateField({
						id:'startDate',
						name:'startDate',
						width:100,
						format:'Y-m-d',
						editable:false,
						emptyText:'请选择...'
					}),
					'&nbsp;',
					'结束时间：',
					' ',
					new Ext.form.DateField({
						id:'endDate',
						name:'endDate',
						width:100,
						format:'Y-m-d',
						editable:false,
						emptyText:'请选择...'
					}),
					'&nbsp;',
					{ 
						text: '查询',
					  	iconCls:'search-button',
					  	handler:function(){
					  		store.setBaseParam("submitUser",Ext.getCmp('submitUser').getValue());
					  		store.setBaseParam("content",Ext.getCmp('content').getValue());
					  		store.setBaseParam("startDate",Ext.get('startDate').dom.value);
					  		store.setBaseParam("endDate",Ext.get('endDate').dom.value);
						  	store.load({params:{start:0,limit:20}});
					  	}
					},
					'-',
					{
						text : '重置',
						iconCls:'reset-button',
						handler:function(){
							Ext.get('submitUser').dom.value = '';
							Ext.get('content').dom.value = '';
							Ext.getCmp('startDate').setValue('');
							Ext.getCmp('endDate').setValue('');
						}	
					}
				]
				}),
				bbar:new Ext.PagingToolbar(
					{
					style:'border-right:0px;border-bottom:0px;border-left:0px',
					pageSize: 20,
					store: store,
					displayInfo: true,
					displayMsg: '显示第{0}条到{1}记录,一共{2}条',
					  plugins: new Ext.ux.ProgressBarPager()
			   	    }),
			   	    plugins:new Ext.ux.PanelResizer({
			   	        minHeight: 100
			   	    })
			});
			
			store.load({params:{start:0,limit:20}});
			log.manage.setGrid(grid);
			new Ext.Viewport({
			    layout: 'fit',
			    bodyStyle :'margin: 0px;border:0px',
			    items: [
                  grid
			    ]
			});
		});

	</script>
</head>

<body>
</body>
</html>
