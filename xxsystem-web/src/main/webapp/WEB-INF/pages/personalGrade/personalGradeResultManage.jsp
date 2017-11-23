<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/doc_type.jsp"%>
<html>
<head>
<%@include file="../common/meta.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/css.jsp"%>
<%@include file="../common/ext.jsp"%>
<title>其他人员评分</title>
<script type="text/javascript" src="${ctx}/scripts/personalGradeResult/PersonalDutyResultDetailsGrid.js"></script>
<script type="text/javascript" src="${ctx}/scripts/personalGradeResult/PersonalDutyResultGrid.js"></script>
<script type="text/javascript" src="${ctx}/scripts/personalGradeResult/PersonalGradeResultGrid.js"></script>
<script type="text/javascript" src="${ctx}/scripts/personalGradeResult/PersonalGradeResultWin.js"></script>



<link href="" rel="SHORTCUT ICON" />
<style type="text/css">
  .x-form-layout-table{
	table-layout: fixed;
  }
  
  .x-grid-td {
    vertical-align: middle !important;
  }
  
  .custom-grid-row{
    height:45px;
  }
  
  .custom_font_size{
    font-size:16px;
  }
</style>
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
			items: [grade.personalGradeResult.PersonalGradeResultGrid]
		});
		grade.personalGradeResult.PersonalGradeResultStore.load();
});

</script>
</html>
