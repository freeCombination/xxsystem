/**
 * 个人评分列表
 * @date 20150919
 * @author wujl
 */

/**
 * 定义Model
 */
Ext.define("grade.personalGrade.PersonalGradeModel",{
					extend:"Ext.data.Model",
					fields:[
						{name:'id'},
						{name:'gradeYear'},
						{name:'problem'},
						{name:'workPlan'},
						{name:'compositeScores'},
						{name:'status'},
						{name:'totalPersonCount'},
						{name:'commitPersonCount'},
						{name:'title'}
					]
	});

/**
 * 定义Store
 */
grade.personalGrade.PersonalGradeStore = Ext.create('Ext.data.Store', {
	model : 'grade.personalGrade.PersonalGradeModel',
	proxy : {
		type : "format",
		url : basePath + "/personalGrade/getPersonalGradeForUserSelfList.action"
	}
});

grade.personalGrade.PersonalGradeStore.addListener('beforeload', function(st, rds, opts) {
	if (rds.length > 0) {
		//Ext.getCmp('generate_btn').hide();
	}else{
		//Ext.getCmp('generate_btn').show();
	}
	grade.personalGrade.PersonalGradeGrid.getSelectionModel().clearSelections();
	Ext.getCmp('edit-button').setDisabled(true);
	Ext.getCmp('submit-button').setDisabled(true);
});

/**
 * 列表字段
 */
var cm = [
		{
			xtype : "rownumberer",
			text : '序号',
			width : 60,
			align : "center"
		}, {
			header : "id",
			dataIndex : "id",
			hidden : true
		}, {
			header : "标题",
			dataIndex : "title"
		},
		{
			header : "年份",
			dataIndex : "gradeYear"
		},
		{
			header : "综合得分",
			dataIndex : "compositeScores"
		},
		{
			header : "已提交人数/总人数",
			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				var totalPersonCount = record.get('totalPersonCount');
				var commitPersonCount = record.get('commitPersonCount');
				var status = record.get('status');
				var personalGradeId = record.get('id');
				if (status == '0') {
					return "" ;
				} else {
					return "<span><a style='ext-decoration:underline;color:#5555FF;cursor:pointer' title='查看未评分人员列表' onclick='getResultPersonList("+personalGradeId+");'>"+commitPersonCount + "/" + totalPersonCount+"</a></span>";
				}
			}
		},
		{
			header : "状态",
			dataIndex : "status",
			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				if(value == '0'){
					cellmeta.attr = 'ext:qtip="' + '草稿' + '<br/>"';
					return '草稿';
				}else if(value == '1'){
					cellmeta.attr = 'ext:qtip="' + '已提交' + '<br/>"';
					return '已提交';
				}else if(value == '2'){
					cellmeta.attr = 'ext:qtip="' + '已归档' + '<br/>"';
					return '已归档';
				}
			}
		}
          ];

/**
 * 查看未评分人员列表
 */
var getResultPersonList = function(personalGradeId){
	var proxy = grade.personalUser.PersonalUserStore.getProxy();
	proxy.setExtraParam("personalGradeId", personalGradeId);
	//proxy.setExtraParam("state", 0);
	grade.personalUser.PersonalUserStore.load();
	grade.personalUser.PersonalUserWin.show();
};

/**
 * 定义Grid
 */
grade.personalGrade.PersonalGradeGrid = Ext.create("Ext.grid.Panel", {
	title : '个人自评',
	region : "center",
	bbar : Ext.create("Ext.PagingToolbar", {
		store : grade.personalGrade.PersonalGradeStore
	}),
	selModel : Ext.create("Ext.selection.CheckboxModel"),
	store : grade.personalGrade.PersonalGradeStore,
	columns : cm,
	tbar : [ '&nbsp;年份',
	{
		id:'gradeYear',
		name:'gradeYear',
		width:80,
		xtype:"textfield",
		value:Ext.Date.format(new Date(),"Y"),
		listeners :{
				'render' : function(p){
					p.getEl().on('click',function(){
						WdatePicker({readOnly:true,dateFmt:'yyyy',maxDate:Ext.Date.format(new Date(),"Y"),onpicked:function(){$dp.$('reportDate-inputEl').focus();}});
					});
				}
		}
	}, {
		text : "查询",
		iconCls : "search-button",
		handler : function(button) {
			grade.personalGrade.PersonalGradeStore.getProxy().setExtraParam("gradeYear", button.prev().getValue());
			grade.personalGrade.PersonalGradeStore.loadPage(1);
		}
	}, '->', {
		xtype : 'button',
		id:'generate_btn',
		//hidden:true,
		text : '生成个人评分',
		iconCls : 'start-button',
		handler : function() {
			grade.personalGrade.generatePersonalGrade();
		}
		},
	{
		xtype : 'button',
		id:'edit-button',
		text : '编辑',
		disabledExpr : "$selectedRows != 1 || $status!='0'",// $selected 表示选中的记录数不等于1
		disabled : true,
		iconCls : 'edit-button',
		handler : function() {
			grade.personalGrade.EditPersonalGrade();
		}
	},{
		xtype : 'button',
		text : '提交',
		id:'submit-button',
		disabledExpr : "$selectedRows == 0 || $status=='1' || $status == '2'",// $selected 表示选中的记录数不等于1
		disabled : true,
		iconCls : 'icon-sendReview',
		handler : function() {
			grade.personalGrade.SubmitPersonalGrade();
		}
	} ]
});

/**
 * 编辑个人评分
 */
grade.personalGrade.EditPersonalGrade = function() {
	grade.personalGrade.PersonalGradeWin.setTitle('编辑');
	var row = grade.personalGrade.PersonalGradeGrid.getSelectionModel().getSelection()
	var id = row[0].data.id;
	var basicForm = grade.personalGrade.PersonalGradeWin.down('form').getForm();
	basicForm.reset();
	basicForm.url = basePath + '/personalGrade/editPersonalGrade.action';
	basicForm.findField('id').setValue(id);
	basicForm.load({
		url : basePath + '/personalGrade/getPersonalGradeById.action',
		params : {
			id : id
		}
	});
	grade.personalGrade.PersonalGradeWin.show();
};

/**
 * 生成个人总结评分
 */
grade.personalGrade.generatePersonalGrade = function(){
	var gradeYear = Ext.getCmp('gradeYear').getValue();
	Ext.Msg.confirm(SystemConstant.alertTitle, "确认要生成" + gradeYear + "年个人评分吗？", function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url : basePath + '/personalGrade/generatePersonalGrade.action',
				params : {
					gradeYear : gradeYear
				},
				success : function(res, options) {
					var data = Ext.decode(res.responseText);
					if (data.success) {
						Ext.Msg.showTip(data.msg);
						grade.personalGrade.PersonalGradeStore.loadPage(1);
					} else {
						Ext.Msg.showInfo(data.msg);
					}
				},
				failure : sshframe.FailureProcess.Ajax
			});
		}
	});
};

/**
 * 提交个人评分 预留可选多个提交
 */
grade.personalGrade.SubmitPersonalGrade = function() {
	
	var rows = grade.personalGrade.PersonalGradeGrid.getSelectionModel().getSelection();
	var ids = "";
	for (var i = 0; i < rows.length; i++) {
		ids += (rows[i].data.id + ",");
	}
	ids = ids.substring(0, ids.length - 1);
	Ext.Msg.confirm(SystemConstant.alertTitle, "确认提交这" + rows.length + "条数据吗?提交后不可修改!", function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url : basePath + '/personalGrade/submitPersonalGrade.action',
				params : {
					ids : ids
				},
				success : function(res, options) {
					var data = Ext.decode(res.responseText);
					if (data.success) {
						Ext.Msg.showTip(data.msg);
						grade.personalGrade.PersonalGradeStore.loadPage(1);
					} else {
						Ext.Msg.showError(data.msg);
					}
				},
				failure : sshframe.FailureProcess.Ajax
			});
		}
	});
	
};



