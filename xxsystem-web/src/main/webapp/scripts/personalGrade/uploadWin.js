		var importUserWin = new Ext.Window({
   			title: '导入',
			closable:true,
			width:400,
			closeAction:'hide',
			height:150,
			modal:true,
			resizable:false,
			layout :"fit",
			buttonAlign:'center',
			html:'<form id="importUserFormDom" style="padding: 25 0 0 50" action="'+basePath+'/personalGrade/uploadPersonalDutyExcel.action" method="post" enctype="multipart/form-data">'+
			     'EXCEL文件：<input type="file" name="uploadAttach" id="uploadAttach" onchange="javascript:$(\'#filename\').val(this.value);"></input><br />'+
				 '<span id="uploadTip" style="color: red;"></span>'+
				 '<input type="hidden" name="filename" id="filename"></input>'+
				 '</form>',
	             buttons:[{
	            	text:SystemConstant.uploadBtnText,
	                handler:importUserInfo
	                },{
	            	   text:SystemConstant.closeBtnText,
	            	   handler:function(){
	            	   	 importUserWin.hide();
	            	   }
	            	}]
   				});
		
		function importUserInfo(){
			if($('#uploadAttach').val()=="" || null==$('#uploadAttach').val()){
				$('#uploadTip').text('请先选择Excel文件');
				setTimeout("$('#uploadTip').text('')",1500);
				return;
			}
			var fileURL = $('#uploadAttach').val();
			var type = fileURL.substring(fileURL.lastIndexOf(".")+1).toLowerCase();
			if (type != 'xls'&&type != 'xlsx') {
				Ext.Msg.showInfo('文件格式错误,支持[.xls]结尾的excel格式！');
				$('#uploadAttach').val('');
				return;
			}
			Ext.MessageBox.wait("", "导入数据", 
					{
						text:"请稍后..."
					}
				);
			var personalGradeId = Ext.getCmp('personalGradeId').getValue();
			Ext.Ajax.request({
				url:basePath+'/personalGrade/uploadPersonalDutyExcel.action',   
		        isUpload:true,   
		        form:'importUserFormDom',
		        params : 
		        {
		        	personalGradeId:personalGradeId
		        },
		        success:function(response, opts){
		 	      	var result = Ext.decode(response.responseText);
		 	      	var flag = result.msg;
		 	      	if(flag=="importSuccess"){
		 	      		Ext.MessageBox.hide();
		 	      		importUserWin.hide();
						var proxy = grade.personalDuty.PersonalDutyStore.getProxy();
						proxy.setExtraParam("personalGradeId",Ext.getCmp("personalGradeId").getValue());
						grade.personalDuty.PersonalDutyStore.load();
		 	      	}else{
		 	      		Ext.Msg.showError(flag);
		 	      	}
		        } 
			});
		};