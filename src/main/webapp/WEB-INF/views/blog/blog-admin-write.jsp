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
<script src="${pageContext.request.contextPath}/assets/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
function form_write_check(var1) {
	var form = document.getElementById(var1);
	if(form.title.value == "") {
		alert("제목을 입력해 주세요.");
		return;
	}
	if(CKEDITOR.instances.contents.getData() == "") {
		alert("내용 입력해 주세요.");
		return;
	}
	
	form.submit();
}
</script>
</head>
<body>
	<div id="container">
		<div id="header">
			<h1>${blogVo.title}</h1>
			<c:import url="/WEB-INF/views/includes/blog-navigation.jsp"></c:import>
		</div>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<c:import url="/WEB-INF/views/includes/blog-admin-navigation.jsp"></c:import>
				<form action="${pageContext.request.contextPath}/${blogVo.blogId}/admin/write" method="post" id="form_write">
			      	<table class="admin-cat-write" style="width:100%;">
			      		<tr>
			      			<td class="t">제목</td>
			      			<td>
			      				<input type="text" size="60" name="title" style="float:left;padding:6px 0 6px 0;width:89%" />
				      			<select name="categoryNo" style="float:right;padding:5px 0 5px 0;width:10%;">
				      				<c:forEach items="${categoryList}" var="cdata">
				      					<option value="${cdata.no}">${cdata.name}</option>
				      				</c:forEach>
				      			</select>
				      		</td>
			      		</tr>
			      		<tr>
			      			<td class="t">내용</td>
			      			<td>
				      			<textarea name="content" id="contents"></textarea>
				      			<script>
								    CKEDITOR.replace('contents', {
								    	width : '100%',
								    	height : '300px'
								    });
								</script>
			      			</td>
			      		</tr>
			      		<tr>
			      			<td>&nbsp;</td>
			      			<td class="s"><input type="button" value="포스트하기" onclick="form_write_check('form_write');" /></td>
			      		</tr>
			      	</table>
				</form>
			</div>
		</div>
		<div id="footer">
			<p>
				<strong>${blogVo.title}</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>