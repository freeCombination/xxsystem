<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ include file="common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录成功</title>
</head>
<body>
 登录成功! 
 <br>
 <a href="${ctx}/system/logManage.do" target="_blank">测试日志管理</a>
 <a  href="${ctx}/system/userManage.do"  target="_blank">测试用户管理</a>
 <a  href="${ctx}/system/roleManage.do"  target="_blank">测试角色管理</a>
 <a  href="${ctx}/system/dictionaryManage.do"  target="_blank">测试字典管理</a>
 <a  href="${ctx}/toIndex.do"  target="_blank">不需要权限</a>
 <a  href="${ctx}/system/logout.do">注销</a>
</body>
</html>