<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/doc_type.jsp"%>
<html>
<head>
<%@include file="../common/meta.jsp"%>
<%@include file="../common/taglibs.jsp"%>
<%@include file="../common/css.jsp"%>
<%@include file="../common/ext.jsp"%>
<title>组织管理</title>
<link href="" rel="SHORTCUT ICON" />
<script type="text/javascript" src="${ctx}/scripts/org/TreeGrid.js"></script>
<script type="text/javascript" src="${ctx}/scripts/org/OrgGrid.js"></script>
<script type="text/javascript" src="${ctx}/scripts/org/OrgStore.js"></script>
<script type="text/javascript" src="${ctx}/scripts/org/OrgWin.js"></script>
</head>
<body>
	<script type="text/javascript">
		var selectNode=null;
		Ext.onReady(function() {
			 Ext.create("Ext.container.Viewport", {
				layout: "border",
				border:true,
				renderTo: Ext.getBody(),
				items: [treePanel,
					{
						region:'center',
						layout:'fit',
						border:false,
						items: [orgGrid]
					}
				]
			});				
		});
	</script>
</body>
</html>