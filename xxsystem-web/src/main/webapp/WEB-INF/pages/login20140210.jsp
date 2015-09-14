<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ include file="common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<body>
<form action="${ctx}/user/login.do">
<table>
<tr>
	<td><input type="radio" name="loginType" id="localLogin" value="0" checked/>本地登录</td>
	<td><input type="radio" name="loginType" id="jcptLogin" value="1"/>集成平台登录</td>
</tr>
<tr>
	<td><input type="radio" name="userType" id="local" value="1" checked/>本地用户</td>
	<td><input type="radio" name="userType" id="remote" value="0"/>中油用户</td>
</tr>
<tr><td>用户名：</td><td><input type="text" name="username"/></td></tr>
<tr><td>密码：</td> <td> <input type="password" name="password"/></td></tr>
 <tr><td colspan="2" align="center">  <input type="submit" value="登录"/></td></tr>
</table>
</form>
</body>
</html>