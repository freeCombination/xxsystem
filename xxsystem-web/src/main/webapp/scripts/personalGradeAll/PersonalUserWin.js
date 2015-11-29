/**
 * 个人评分窗口
 * @date 20150919
 * @author wujl
 */


grade.personalUser.PersonalUserWin = Ext.create("Ext.window.Window", {
	height : 400,
	width : 600,
	layout: 'border',
	title:'评分人员列表',
	items : [grade.personalUser.PersonalUserGrid],
	buttons : [{
		text : '关闭',
		handler : function() {
			grade.personalUser.PersonalUserWin.close();
		}
	}]
});



