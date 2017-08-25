<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
    <base href="<%=basePath%>">
    
    <title>小狗网</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery-2.2.2.js"></script>
	<script type="text/javascript" src="js/register.js"></script>
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<script type="text/javascript">
		function login(){
			$.ajax({
				url:"login",
				type:"post",
				data:{userName:$("#userName").val(),password:$("#password").val(),verification:$("#verification").val()},
				success:function(data){
					data=$.parseJSON(data);
					alert(data.errorMsg);
				},
			     error:function(e){
	                    alert("错误！！");
	                    
	                }
				
			});
		}
	</script>
  
  </head>
  
  <body>
  	
  	<p>${row}</p>
  	
    <form action="login" method="post">
    
    <p>用户名:<input type="text" id="userName" name="userName" >${errorMsg}</p>
    
    <p>密码    :<input type="password" id="password"  name="password" ></p>
    
    <p>验证码:<input type="text"  id="verification" name="verification" >
    
    <img title="看不清？点击刷新！" id="validateCodeImg" src="validateCode" onclick="javascript:reloadValidateCode();"/>
    
    </p>
    
    <p><input type="button" onclick="login()" value="登录">
    
       <a href="register.jsp"><input type="button"  value="注册"></a>
                        
    </p>       
    
    </form>

  </body>
  
</html>
