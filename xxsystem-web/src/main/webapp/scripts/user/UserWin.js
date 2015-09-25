/**
 * 用户管理的添加修改功能
 * @date 20150706
 * @author wujl
 */

Ext.define('sshframe.user.RespModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'respId'},
        {name: 'name'}
    ]
	});

sshframe.user.respStore = Ext.create('Ext.data.Store', {
 	model: 'sshframe.user.RespModel',
 	proxy: {
  	   type: 'ajax',
  	   url: basePath+'/org/getAllResp.action',
   	   reader: {
   	      type: 'json'
  	   }
 	},
 	autoLoad: true
	});

sshframe.user.UserForm = Ext.create("Ext.form.Panel", {
	//bodyStyle :'padding:5px 30px 0 5px',
	border :false,
	autoScroll : false,
	layout : 'fit',
	items:[
		{
			xtype: 'tabpanel',
			border:false,
			items: [
				{
					layout : 'column',
					border : false,
					baseCls : 'x-plain',
					title:'基本信息',
					bodyStyle :'padding:5px 30px 0 0px',
					items : [
						{
							columnWidth : .5,
							layout : 'form',
							baseCls : 'x-plain',
							border : false,
							labelWidth:80,
							defaultType:'textfield',
							defaults:{
								width:200,
								labelAlign:'right'
							},
							items : [
								{
									id:'userId',
									name : 'user.userId',
									hidden : true
								},
								{ 
									allowBlank:false, 
									regex : /^[\w.\-\u4e00-\u9fa5]+$/,
							        regexText : '不能包含特殊字符',
							        fieldLabel: '姓名', 
							        name: 'user.realname',
							        emptyText: '真实姓名',
							        maxLength:20
							    },
							    { 
							    	allowBlank:false, 
							    	fieldLabel: '登录名', 
							    	name: 'user.username',
							    	emptyText: '登录名',
					              	regex : /^[\w-]{4,16}$/,
					              	regexText : '登录名由4到16位数字、字母、下划线、中划线组成，请检查',
					              	emptyText: '登录名不能为空',
					              	maxLength:16
									
								},
								{ 
									allowBlank:false, 
									fieldLabel: '密码',
									maxLength:18,
									minLength:6, 
									name: 'user.password', 
									inputType: 'password',
									minLength:SystemConstant.passwordMinLength,
									regex : /^[^\u4e00-\u9fa5]{0,}$/
								},
								{ 
									allowBlank:false, 
									fieldLabel: '确认密码',
									maxLength:18,
									minLength:6,
									name: 'confirmPassword', 
									inputType: 'password',
									minLength:SystemConstant.passwordMinLength,
									regex : /^[^\u4e00-\u9fa5]{0,}$/,
									vtype:"updateValidatePassword",//自定义的验证类型
						    		vtypeText : '确认密码错误，请检查'
					     		},
						        { xtype: 'radiogroup',layout:"column", columns: [40, 40], fieldLabel:'性别',isFormField:true,
				    				items:[{
				          				xtype:"radio",
				          				boxLabel:SystemConstant.gender_male,
				          				checked:true,
				          				name: "user.gender",
				          				inputValue:SystemConstant.gender_male
				    				},{
				          				xtype:"radio",
				         				boxLabel:SystemConstant.gender_female,
				          				checked:false,
				          				name: "user.gender",
				          				inputValue:SystemConstant.gender_female
				    				}]
								},
								{ fieldLabel: '身份证号码',regex : /^[\w.\-\u4e00-\u9fa5]+$/, regexText : '不能包含特殊字符',emptyText: '', name: 'user.idCard',maxLength:18},
								{ fieldLabel: '民族',regex : /^[\w.\-\u4e00-\u9fa5]+$/, regexText : '不能包含特殊字符',name: 'user.nationality',emptyText: '',maxLength: 50},
								{ fieldLabel: '籍贯',regex : /^[\w.\-\u4e00-\u9fa5]+$/, regexText : '不能包含特殊字符',name: 'user.birthPlace',emptyText: '',maxLength: 50},
								{ xtype: 'datefield',format : 'Y-m-d', fieldLabel: '出生日期', name: 'user.birthDay', maxValue: new Date(),editable:false}
							]},
						{
							columnWidth : .5,
							layout : 'form',
							baseCls : 'x-plain',
							border : false,
							labelWidth:80,
							defaultType:'textfield',
							defaults:{
								width:220,
								labelAlign:'right'
							},	
							items : [
								{ allowBlank:false, id:'addOrgIds', name: 'orgIds',xtype:'hidden',
									listeners:{
										'change':function(text, newValue, oldValue){
											Ext.getCmp('respCombobox').reset();
											
											var proxy = sshframe.user.respStore.getProxy();
											proxy.setExtraParam("orgId", newValue);
											sshframe.user.respStore.load();
										}
									}
								},
								{
									fieldLabel:'组织部门',
									id:'orgNames',
									name:'orgNames',
									labelAlign:'right',
									xtype:'textfield',
									readOnly:true,
									minWidth: 250,
									allowBlank:false,
									blankText : '组织部门不能为空',
									listeners:{
										'focus':function(){
											var userId = Ext.getCmp('userId').getValue();
											if(userId && userId != 0){
												chooseOrganization('orgNames','addOrgIds',userId);
											}else{
												chooseOrganization('orgNames','addOrgIds');
											}
										}
									}
								},
								{
									xtype: 'combobox',
									fieldLabel: '岗位',
									id:'respCombobox',
									name: 'user.responsibilities.pkRespId',
									store: sshframe.user.respStore,
									valueField: 'respId',
									displayField: 'name',
									typeAhead:false,
									allowBlank:false,
									editable:false,
									queryMode: 'remote'
								},
								{ fieldLabel: 'ERP ID',regex : /^[\w.\-\u4e00-\u9fa5]+$/,regexText : '不能包含特殊字符',emptyText: '', name: 'user.erpId',maxLength:10},
								{ fieldLabel: '电话',name: 'user.mobileNo',emptyText: '',regex : /^[1][0-9]{10}$/,regexText : '手机号码由数字1开头的11位数字组成，请检查'},
								{ fieldLabel: '办公号码',maxLength: 12,name: 'user.phoneNo',emptyText: '', regex : /^[\d-]*$/,regexText : '电话号码只能包含数字和“-”，请检查',maxLength:12},
								{ fieldLabel: '集团短号',name: 'user.shortNo',emptyText: '',regex:/^\d{6}$/,regexText:'集团短号由6位数字组成，请检查'},
								{ fieldLabel: 'Email',maxLength:50,name: 'user.email', vtype: 'email',regex:/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/}
							]
						}
					]
				},
				{
					layout : 'column',
					border : false,
					baseCls : 'x-plain',
					title:'扩展信息',
					bodyStyle :'padding:5px 30px 0 0px',
					items : [
						{
							columnWidth : .5,
							layout : 'form',
							baseCls : 'x-plain',
							border : false,
							labelWidth:80,
							defaultType:'textfield',
							defaults:{
								width:200,
								labelAlign:'right'
							},
							items : [
								{
									regex : new RegExp('^([^<^>])*$'),
				                    regexText : '不能包含特殊字符！',
								    fieldLabel: '入党团时间', 
								    name: 'user.partyDate',
								    maxLength:100
								},
								{
									regex : new RegExp('^([^<^>])*$'),
				                    regexText : '不能包含特殊字符！',
								    fieldLabel: '参加工作时间', 
								    name: 'user.jobStartDate',
								    maxLength:100
								},
								{
									regex : new RegExp('^([^<^>])*$'),
				                    regexText : '不能包含特殊字符！',
								    fieldLabel: '职务及任职时间', 
								    name: 'user.officeHoldingDate',
								    maxLength:100
								},
								{
									regex : new RegExp('^([^<^>])*$'),
				                    regexText : '不能包含特殊字符！',
								    fieldLabel: '学历', 
								    name: 'user.educationBackground',
								    maxLength:100
								},
								{
									regex : new RegExp('^([^<^>])*$'),
				                    regexText : '不能包含特殊字符！',
								    fieldLabel: '职称及确定时间', 
								    name: 'user.technicaTitles',
								    maxLength:100
								},
								{
									regex : new RegExp('^([^<^>])*$'),
								    regexText : '不能包含特殊字符！',
								    fieldLabel: '进所时间', 
								    name: 'user.comeDate',
								    maxLength:100
								},
								{
									regex : new RegExp('^([^<^>])*$'),
				                    regexText : '不能包含特殊字符！',
								    fieldLabel: '岗位工资', 
								    name: 'user.postWage',
								    maxLength:100
								}
							]
						},
						{
							columnWidth : .5,
							layout : 'form',
							baseCls : 'x-plain',
							border : false,
							labelWidth:80,
							defaultType:'textfield',
							defaults:{
								width:220,
								labelAlign:'right'
							},	
							items : [
								{
									xtype: 'textarea',
									regex : new RegExp('^([^<^>])*$'),
				                    regexText : '不能包含特殊字符！',
								    fieldLabel: '技能', 
								    name: 'user.skill',
								    rows:3,
								    maxLength:1000
								},
								{
									xtype: 'textarea',
									regex : new RegExp('^([^<^>])*$'),
				                    regexText : '不能包含特殊字符！',
								    fieldLabel: '业绩', 
								    name: 'user.performance',
								    rows:3,
								    maxLength:1000
								},
								{
									xtype: 'textarea',
									regex : new RegExp('^([^<^>])*$'),
				                    regexText : '不能包含特殊字符！',
								    fieldLabel: '用工信息', 
								    name: 'user.employmentInfo',
								    rows:3,
								    maxLength:1000
								},
								{
									xtype: 'textarea',
									regex : new RegExp('^([^<^>])*$'),
				                    regexText : '不能包含特殊字符！',
								    fieldLabel: '培训情况', 
								    name: 'user.trainInfo',
								    rows:3,
								    maxLength:1000
								}
							]
						}
					]
				}
			]
		}
	] 
});

Ext.apply(Ext.form.VTypes,{
	updateValidatePassword:function(val,field){//val指这里的文本框值，field指这个文本框组件
		return (val== sshframe.user.UserForm.getForm().findField("user.password").getValue());
	}
});

sshframe.user.UserWin = Ext.create("Ext.window.Window", {
    width : 850,
    height: 360,
	items : [ sshframe.user.UserForm ],
	buttons : [ {
		text : '确定',
		handler : function() {
			if (sshframe.user.UserForm.form.isValid()) {
				sshframe.user.UserForm.form.submit({
					success : function(form, action) {
						Ext.Msg.showTip(action.result.msg);
	                	var store = sshframe.user.userGrid.getStore();
	     				store.loadPage(1);
						sshframe.user.UserWin.close();
					},
					failure : function(form, action) {
						Ext.Msg.showError(action.result.msg);
					}
				});
			}
		}
	}, {
		text : '关闭',
		handler : function() {
			sshframe.user.UserWin.close();
		}
	} ]
});
