<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>


<script type="text/javascript">
function login_form_check(var1) {
	var forms = document.getElementById(var1);
	
	if(forms.id.value == "") {
		alert("아이디를 입력해 주세요.");
		return;
	}
	if(forms.password.value == "") {
		alert("비밀번호를 입력해 주세요.");
		return;
	}
	
	forms.submit();
}
</script>
</head>
<body>
	<div class="center-content">
		<h1 class="logo">JBlog</h1>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		<form class="login-form" id="login_form" action="${pageContext.request.contextPath}/user/auth" method="post">
      		<label>아이디</label> <input type="text" name="id" />
      		<label>패스워드</label> <input type="text" name="password" />
      		<input type="button" value="로그인" onclick="login_form_check('login_form');" />
		</form>
	</div>
</body>
</html>
