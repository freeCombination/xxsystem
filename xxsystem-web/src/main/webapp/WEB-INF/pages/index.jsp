<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="common/doc_type.jsp"%>
<html>
<head>
<%@include file="common/meta.jsp"%>
<%@include file="common/taglibs.jsp"%>
<%@include file="common/css.jsp"%>
<%@include file="common/ext.jsp"%>
<title>首页</title>
<link href="" rel="SHORTCUT ICON" />
</head>
<body>
<h2>请选择菜单</h2>
<a href="${ctx}/dict/toDictIndex.action">字典管理</a>
<a href="${ctx}/user/toUserIndex.action">用户管理</a>
<a href="${ctx}/org/toOrgIndex.action">组织管理</a>
<a href="${ctx}/role/toRoleIndex.action">角色管理</a>
<a href="${ctx}/resource/toResourceIndex.action">资源管理</a>
<a href="${ctx}/log/toLogIndex.action">日志管理</a>
<a href="${ctx}/bpm/toPersonManage.do">个人中心</a>
<a href="${ctx}/bpm/toProcessManage.do">流程管理</a>
<a href="${ctx}/bpm/userLogin.do?loginName=wanglc" target="_blank">登录</a>
</body>
</html>