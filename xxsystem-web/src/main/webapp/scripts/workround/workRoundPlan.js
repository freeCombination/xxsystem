/**
 * 生成倒班计划
 */
var basePath=(function(){
	var href=window.location.href;
	var host=window.location.host;
	var index = href.indexOf(host)+host.length+1; //host结束位置的索引（包含/）
	return href.substring(0,href.indexOf('/',index));
})(window);

//根据规则ID获得规则的详细信息;
getRuleInfomation = function(ruleId,cycleDays,ruleName,org_id) {
	var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';

	var role_name;
	var role_cycleDays;
	//建立数据Store
	var roleStore = Ext.create("Ext.data.Store",
			{
				pageSize : SystemConstant.commonSize,
				model : "WorkTurnsRule",
				remoteSort : true,
				proxy : {
					type : "ajax",
					url : basePath+"/workturnsrule/getWorkTurnsRuleById.action",
					reader : {
						totalProperty : "totalSize",
						root : "list"
					},
					simpleSortMode : true
				},
				sorters : [ {
					property : "id",
					direction : "ASC"
				} ]
			});
	
	//这里负责往form里放入什么样的格子
	var roleForm = Ext.create("Ext.form.Panel", {
		layout : 'absolute',
		bodyStyle : 'padding:15px 50px 0 0',
		border : false,
		labelAlign : 'right',
		fieldDefaults : {
			labelWidth : 100,
			labelAlign : 'right'
		},
		defaults : {
			anchor : '60%'
		},
		defaultType : 'textfield',
		items : [ {
			name : 'workTurnsRule.id',
			value : ruleId,
			hidden : true
		}, { 
			x: 0,          
            y: 10,
            anchor:'50%',
			id : 'rolename',
			fieldLabel : '规则名称',
			name : 'workTurnsRule.name',
			readOnly : true,
			disabled : false,
			hidden : false,
			beforeLabelTextTpl : required,
			maxLength : 25,
			height:20,
			allowBlank : false
		}, {
			x: 250,          
            y: 10,  
            anchor:'100%',
			id : 'rolecycleDays',
			fieldLabel : '周期',
			name : 'workTurnsRule.cycleDays',
			regex : new RegExp('^([\u4E00-\u9FFF]*-*\\w*)*$'),
			regexText : '不能包含特殊字符',
			maxLength : 10,
			readOnly : true,
			minValue : 1,
			hidden : false,
			allowBlank : false,
			disabled : false,
			height:20,
			beforeLabelTextTpl : required
		},{
			x: 0,          
            y: 40, 
            anchor:'50%',
			id : 'rolestartTime',
			fieldLabel : '开始时间',
			height:20,
			format: 'Y-m-d',
			xtype : 'datefield',
			minValue:new Date(),
			value: new Date(),
			listeners:{
				'change':function(obj, newValue, oldValue){
					var end = Ext.getCmp('roleendTime').getValue();
					if(end < newValue){
						Ext.getCmp('roleendTime').setValue(newValue);
					}
					Ext.getCmp('roleendTime').setMinValue(newValue);
					var end2 = Ext.getCmp('roleendTime').getValue();
					Ext.getCmp('rolePlanDays').setValue(((end2-newValue)/1000/60/60/24)+1);
				}
			}
		},{
			x: 250,          
            y: 40,  
            anchor:'100%',
			id : 'roleendTime',
			fieldLabel : '结束时间',
			height:20,
			format: 'Y-m-d',
			xtype : 'datefield',
			minValue:new Date(),
			value:new Date(),
			listeners:{
				'change':function(obj, newValue, oldValue){
					var start = Ext.getCmp('rolestartTime').getValue();
					Ext.getCmp('rolePlanDays').setValue(((newValue-start)/1000/60/60/24)+1);
				}
			}
		},
		{
			x: 0,          
            y: 75,  
            anchor:'50%',
			id : 'rolePlanDays',
			fieldLabel : '计划天数',
			maxLength : 10,
			minValue : 1,
			hidden : false,
			width : 100,
			allowBlank : true,
			disabled : false,
			readOnly:true,
			height:20,
			xtype : 'textareafield',
			listeners:{
				'render':function(obj){
					var start = Ext.getCmp('rolestartTime').getValue();
					var end = Ext.getCmp('roleendTime').getValue();
					Ext.getCmp('rolePlanDays').setValue(((end-start)/1000/60/60/24)+1);
				}
			}
		}]
	});

	//这里是规则修改pannel 负责把form装配到pannel上面
	roleWin = Ext.create(
					"Ext.window.Window",
					{
						title : '批量生成倒班计划',
						resizable : false,
						buttonAlign : "center",
						height : 250,
						width : 550,
						modal : true,
						layout : 'fit',
						modal : true,
						items : [ roleForm ],
						listeners : {
							afterrender : function() {
								roleStore.load(function() {
											Ext.getCmp('rolename').setValue(role_name);
											Ext.getCmp('rolecycleDays').setValue(role_cycleDays);
										});
							}
						},
						buttons : [{
									text : SystemConstant.saveBtnText,
									handler : function() {
										var rolePlanDays = Ext.getCmp('rolePlanDays').getValue();
										if (rolePlanDays<=0) {
											Ext.MessageBox.show({
					    						   title:'提示信息',
					    						   msg:"时间设置不正确！",
					    						   buttons: Ext.Msg.OK,
					    						   modal : true,
					    						   icon: Ext.Msg.INFO
					    					});
							    			return;
										}
										if (roleForm.form.isValid()) {
											var rolePlanDays = Ext.getCmp('rolePlanDays').getValue();
											var roleId = ruleId;
											var rolestartTime = Ext.getCmp('rolestartTime').getValue();
											var planEndDate = Ext.getCmp('roleendTime').getValue();
											if (rolePlanDays%cycleDays!=0) {
												Ext.MessageBox.show({
													title: SystemConstant.alertTitle,
													msg: '计划天数必须是周期的整数倍！',
													buttons: Ext.MessageBox.OK,
													icon: Ext.MessageBox.ERROR
												});
											}else{
												Ext.MessageBox.wait("","生成倒班计划",{
													text : "请稍后..."
												});
												Ext.Ajax.request({
													url : basePath+"/workturnsrule/batchProduceWorkPlans.action",
													params : {ruleId: roleId,planDays:rolePlanDays,planStartDate:rolestartTime,planEndDate:planEndDate},
													timeout : 60*1000,
													success : function(res, options) {
														var data = Ext.decode(res.responseText);
														if(data.success){
															Ext.MessageBox.hide();
															new Ext.ux.TipsWindow(
																	{
																		title : SystemConstant.alertTitle,
																		autoHide : 3,
																		html : data.msg
																	}).show();
															roleWin.close();
														}else{
															roleStore.loadPage(1);
															Ext.MessageBox.hide();
														 	Ext.MessageBox.show({
																title: SystemConstant.alertTitle,
																msg: data.msg,
																buttons: Ext.MessageBox.OK,
																icon: Ext.MessageBox.ERROR
															});
														}
													}
												});
											}
										}
									}
								}
								/*, {
									text : '下一步',
									handler : function() {
										var rolePlanDays = Ext.getCmp('rolePlanDays').getValue();
										var roleId = ruleId;
										var rolestartTime = Ext.getCmp('rolestartTime').getValue();
										if (rolePlanDays%cycleDays!=0) {
											Ext.MessageBox.show({
												title: SystemConstant.alertTitle,
												msg: '计划天数必须是周期的整数倍！',
												buttons: Ext.MessageBox.OK,
												icon: Ext.MessageBox.ERROR
											});
										}else{
											Ext.Ajax.request({
												url : basePath+"/workturnsrule/batchProduceWorkPlans.action",
												params : {ruleId: roleId,planDays:rolePlanDays,planStartDate:rolestartTime},
												success : function(res, options) {
													var data = Ext.decode(res.responseText);
													if(data.success){
														roleWin.close();
														getPlanDetial(ruleId,cycleDays,ruleName, org_id);
													}else{
														roleStore.loadPage(1);
													 	new Ext.ux.TipsWindow(
																{
																	title : SystemConstant.alertTitle,
																	autoHide : 3,
																	html : data.msg
																}).show();
													 	return false;
													}
												}
											});
										}
									}
								}*/	, {
									text : '关闭',
									handler : function() {
										roleWin.close();
									}
								} ],
						listeners:{
							'afterrender':function(){
								Ext.Ajax.request({
									url : basePath+"/workturnsrule/getWorkTurnsRuleById.action",
									params : {
										role_id : ruleId
									},
									success : function(res, options) {
										var data = Ext.decode(res.responseText);
										Ext.getCmp("rolename").setValue(data.name);
										Ext.getCmp("rolecycleDays").setValue(data.cycleDays);
									}
								});
							}
						}
					});
	roleWin.show();
};

