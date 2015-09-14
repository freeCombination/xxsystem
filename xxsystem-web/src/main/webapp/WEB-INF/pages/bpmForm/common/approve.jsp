<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglibs.jsp"%>
<%@include file="../../common/ext.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>审核人填写</title>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/icons.css" />
<script type="text/javascript" src="${ctx}/scripts/extjs/SpecialCharFilter.js"></script>
<script type="text/javascript" src="${ctx}/scripts/common/SystemConstant.js"></script>
<script type="text/javascript">
Ext.Loader.setConfig({enabled: true});
Ext.Loader.setPath('Ext.ux', '${ctx}/scripts/extjs/ux');

Ext.require([
                 'Ext.toolbar.Paging',
                 'Ext.ux.ProgressBarPager',
                 'Ext.ux.TreePicker',
                 'Ext.form.*',
                 'Ext.grid.column.Action',
                 'Ext.tree.Panel',
                 'Ext.data.*',
                 'Ext.selection.CheckboxModel',
                 'Ext.tip.QuickTipManager'
             
         ]);
Ext.onReady(function() {
	var formFlow=Ext.create(Ext.form.Panel,{
	    autoScroll:false,
		fieldDefaults:{
                      labelAlign: 'right',
                      msgTarget : 'side',//在该组件的下面显示错误提示信息   取值为'qtip','side','title','under'
                      labelWidth:80
                      },
		 renderTo:Ext.getBody(),
		 buttonAlign:'center',
		 height:600,
		 border:false,
	     bodyStyle:'padding:5px 5px 0',
	     items:[{
            xtype:'fieldset',
            title: '业务数据',
	        layout:'anchor',
	        anchor:'90%',
            collapsible: true,
            autoHeight:true,
            defaultType: 'textfield',
            items :[{
                    fieldLabel: '业务标题',
                	width:450,
                	 anchor:'90%',
        			id:"businessTitle",
        			readOnly:true
                },{ 
                	fieldLabel:'业务内容',
            		xtype : 'htmleditor',
        	        height : 150,
        	        width:450,
        	         anchor:'90%',
        	        enableSourceEdit:false,
        	        autoScroll:true,
        	        id:'businessContent',
    			    readOnly:true
    		}]
        },{
            xtype:'fieldset',
            title: '审批数据',
             layout:'anchor',
	        anchor:'90%',
            collapsible: true,
            buttonAlign:'center',
            autoHeight:true,
            defaultType: 'textfield',
            items :[{
    			xtype:"hidden",
    			name:"taskId",
    			value:'${param.taskId}'
    		},{
    			xtype:"hidden",
    			name:"pass",
    			value:'true'
    		},
    		{
    			xtype:"hidden",
    			name:"opinion"
    		},
    		{
    		xtype:"hidden",
    		name:"processInstanceId",
    		value:'${param.processInstanceId}'
    	     },
    	     {
    	 		xtype:"hidden",
    			name:"businessId",
    			value:'${param.businessId}'
    		     },
    		    {
    			 		xtype:"hidden",
    					name:"baseType",
    					value:'${param.baseType}'
    				},
    		{
    					 xtype:"hidden",
    					name:"realType",
    					value:'${param.realType}'
    	   },{
    		   
    		    	fieldLabel:'所有人员意见',
    		    	xtype:'displayfield',
    		    	 anchor:'90%',
    		    	 height:210,
    		    	border:true,
    		    	id:"opinions"
                },{ 
                 	fieldLabel:'意见',
    		    	xtype:'textarea',
    		    	id:"initOpinion",
    		    	allowBlank:false,
	                anchor:'90%',
    		        blankText : '意见不能为空',
    		        maxLength:50,
    		        vtype:'inputCharFilter',
    		    	name:"initOpinion"
    		}]
    	  }]
	    ,
	     
       buttons:[{
			text:'提交',
			id:'passBtn',
			xtype:'button',
			disabled:true
			
		}]
      
       
	});
	
	if("${param.viewFlag}"=="true"){
		return;
	}
	 Ext.Ajax.request({ 
	 		url:'${ctx}/bpm/getHistoryTask.do',
	 		params: { 
	 		  taskId: '${param.taskId}'
	 		},
	        success : function(response ,options) {
	    			var error=false;
	    			try{
	    			var result = Ext.decode(response.responseText);
	    			if(!result.success){
	    				error=true;
	    			}
	    			}catch(e){
	    				error=true;
	    				Ext.Msg.alert("提示","获取历史数据失败，系统故障，或者是未登陆！");
	    			}
	    			if(error){
	    				return;
	    			}
	    			if(!result){
	    				Ext.Msg.alert("提示","获取历史数据失败，系统故障，或者是未登陆！");
	    				return;
	    			}
	    		    if(result.data==null){
	    		    	Ext.getCmp("passBtn").setDisabled(false);
	    		    	Ext.getCmp("passBtn").btnEl.dom.onclick=function(){
	    		    		if(!formFlow.getForm().isValid()){
	    						return;
	    					}
	    					 formFlow.getForm().findField("opinion").setValue(Ext.getCmp("initOpinion").getValue());
	    					 formFlow.getForm().submit({
	    		                url:'${ctx}/bpm/compeleteTask.do',
	    		                waitTitle:"请等待...",
	    		        		waitMsg:"正在提交...",
	    		                success:function(form,action){
	    		           	       	if(action.result.success)
	    		           	       	{
	    		           	       	    Ext.Msg.alert(SystemConstant.alertTitle,'提交成功！');
	    		           	       	  try{
	    		           	             parent.refleshToDoTask();
	    		           	       	  }catch(e){
	    		           	       		 window.location.reload();
	    		           	       	  }
	    		           	        }else{
	    		           	        Ext.Msg.alert(SystemConstant.alertTitle, action.result.msg);
	    		           	        }
	    		          	    },
	    		              	failure:function(form,action){
	    		              	 Ext.Msg.alert(SystemConstant.alertTitle, action.result.msg);
	    		              	}
	    		            }); 
	    		    	};
	    		    }else{
	    		    	Ext.getCmp("initOpinion").setReadOnly(true);
	    		    	Ext.getCmp("initOpinion").setValue(result.data.description);
	    		    }
	        },   
			   failure: function(response, opts) {
				     Ext.Msg.alert("提示","获取历史数据失败，系统可能出现故障,或者未登陆系统!");
			   }
		}); 
	 
	 Ext.Ajax.request({ 
	 		url:'${ctx}/bpm/getBusinessData.do',
	 		params: { 
	 		  taskId: '${param.taskId}',
	 		  processInstanceId:'${param.processInstanceId}',
	 		  baseType:'${param.baseType}',
	 		  businessId:'${param.businessId}'
	 		},
	        success : function(res ,options) {
	 			var objs= Ext.decode(res.responseText);
	        	if(!objs.success){
	        		Ext.Msg.alert("提示", '系统繁忙，请稍后再试！'+objs.msg);
	 				return;
	 			}
	 			Ext.getCmp("businessTitle").setValue(objs.data.businessTitle);
	 			Ext.getCmp("businessContent").setValue(objs.data.businessContent);
	 			var tpl = new Ext.XTemplate(
	 					'<div style="padding: 30 10 10 10;overflow-y:auto;height:200px">',
	 					'<table  border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;font-family: \'宋体\', \'sans-serif\'; border:0px;line-height:23px;">',
	 					'<tpl for="approvingArray">', 
	 					'<tr>',
	 					'<td  align="center">',
	 		       		'<p style="margin: 5 0;" align="left">{num}.{opinion}</p>',
	 		   			'</td>',
	 		   			'</tr>',
	 		   			'</tpl>',
	 		   			'<table>',
	 		   			'</div>'
	 				);
	 			
	 		    var approvingArray = [];
	 		    for(var i=0;i<objs.data.opinions.length;i++){
	 		    	var obj=new Object();
	 		    	obj.opinion=objs.data.opinions[i];
	 		    	obj.num=i+1;
	 		    	approvingArray.push(obj);
	 		    }
	 			var data = new Object();
	 			data.approvingArray = approvingArray;
	 			tpl.overwrite(Ext.getCmp("opinions").bodyEl , data);
			}, 
	        failure : function(response) { 
	        	Ext.Msg.alert("提示", '系统繁忙，请稍后再试！');
	        }
		});
});
</script>
</head>
<body>
</body>
</html>