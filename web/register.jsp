<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
    <script type="text/javascript" src="js/jquery-2.2.2.js"></script>
	<script type="text/javascript" src="js/register.js"></script>
	
</head>
<body>

	<div id="d1">

		<h3>欢迎注册</h3>

		<form action="register" method="post">

			<p>
				用户名:<input type="text" id="userName" name="userName">
			</p>

			<p>
				密码:<input type="password" id="password" name="password">
			</p>

			<p>
				确认密码:<input type="password" id="password2" name="password2">
			</p>

			<p>
				<input type="hidden" id="roleId" name="roleId" value="2">
			</p>

			<p>
				<input type="hidden" id="salt" name="salt" value="">
			</p>

			<p>
				<input type="button"  value="注册" onclick="register()">
			</p>
		</form>
	</div>
</body>
</html>