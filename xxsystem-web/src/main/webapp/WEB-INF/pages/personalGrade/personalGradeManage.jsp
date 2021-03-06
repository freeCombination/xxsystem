<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/doc_type.jsp"%>
<html>
<head>
<%@include file="../common/meta.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/css.jsp"%>
<%@include file="../common/ext.jsp"%>
<title>个人评分</title>
<script type="text/javascript" src="${ctx}/scripts/personalGrade/PersonalDutyGrid.js"></script>
<script type="text/javascript" src="${ctx}/scripts/personalGrade/PersonalDutyViewGrid.js"></script>
<script type="text/javascript" src="${ctx}/scripts/personalGrade/PersonalGradeGrid.js"></script>
<script type="text/javascript" src="${ctx}/scripts/personalGrade/PersonalGradeWin.js"></script>
<script type="text/javascript" src="${ctx}/scripts/personalGrade/PersonalGradeViewWin.js"></script>
<script type="text/javascript" src="${ctx}/scripts/personalGrade/PersonalUserGrid.js"></script>
<script type="text/javascript" src="${ctx}/scripts/personalGrade/PersonalUserWin.js"></script>
<script type="text/javascript" src="${ctx}/scripts/personalGrade/uploadWin.js"></script>

<link href="" rel="SHORTCUT ICON" />
</head>
<body>
</body>

<script type="text/javascript">

Ext.onReady(function(){
	Ext.QuickTips.init(); 
	//自动引入其他需要的js
	 Ext.require([
        "Ext.grid.*",
        "Ext.toolbar.Paging",
        "Ext.form.*",
        "Ext.data.*"
	]);
	
		Ext.create("Ext.container.Viewport", {
		    layout: "border",
			renderTo: Ext.getBody(),
			items: [grade.personalGrade.PersonalGradeGrid]
		});
		grade.personalGrade.PersonalGradeStore.getProxy().setExtraParam("gradeYear", Ext.getCmp('gradeYear').getValue());
		grade.personalGrade.PersonalGradeStore.loadPage(1);
});

</script>
</html>
