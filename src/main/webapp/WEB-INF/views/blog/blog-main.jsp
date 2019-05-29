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

<style type="text/css">
.blog-content-header {
border-bottom:1px solid #e6e6e6;
width:100%;
overflow:hidden;
}
.blog-content-header h2 {
float:left;
width:49%;
height:35px;
line-height:35px;
padding:0px;
margin:0px;
overflow:hidden;
}
.blog-content-header div {
float:right;
width:49%;
height:35px;
line-height:35px;
font-size:15px;
text-align:right;
padding:0px;
margin:0px;
overflow:hidden;
}
.blog-content-url {
font-size:12px;
text-align:right;
width:100%;
padding:5px 0 5px 0;
margin:0px;
overflow:hidden;
}
.blog-content-url a {
text-decoration:none;
color:#cccccc;
font-weight:normal;
}
.blog-content-url a:hover {
text-decoration:underline;
}
.blog-list-h {
height:40px;
line-height:40px;
font-weight:bold;
color:#636363;
border-top:1px solid #b1b1b1;
border-bottom:1px solid #b1b1b1;
padding:0px;
margin:0px;
overflow:hidden;
}
</style>

</head>
<body>
	<div id="container">
		<div id="header">
			<h1><a href="${pageContext.request.contextPath}/${blogVo.blogId}" style="color:#ffffff;">${blogVo.title}</a></h1>
			<c:import url="/WEB-INF/views/includes/blog-navigation.jsp"></c:import>
		</div>
		<div id="wrapper">
			<div id="content">
				<c:if test="${mainPostVo eq null}">
				<div class="blog-content">
					<h2 style="width:100%;text-align:center;padding:0px;margin-top:50px;">포스트가 없습니다</h2>
				</div>
				</c:if>
				<c:if test="${mainPostVo ne null}">
				<div class="blog-content">
					<div class="blog-content-header">
						<h2><span style="color:blue;">[${mainPostVo.categoryName}]</span> ${mainPostVo.title}</h2>
						<div>${mainPostVo.regDate}</div>
					</div>
					<div class="blog-content-url">
						<a href="${blogURL}/${blogVo.blogId}/${mainPostVo.categoryNo}/${mainPostVo.no}">${blogURL}/${blogVo.blogId}/${mainPostVo.categoryNo}/${mainPostVo.no}</a>
					</div>
					<p>
						${mainPostVo.content}
					<p>
				</div>
				<h3 class="blog-list-h">
					이 블로그 
					<c:if test="${hasCategory eq false}"><span style="color:#cf8b0c;">전체</span></c:if>
					<c:if test="${hasCategory eq true}"><span style="color:#cf8b0c;">${mainPostVo.categoryName}</span></c:if>
					 카테고리 글
				</h3>
				<ul class="blog-list">
					<c:forEach items="${mainPostList}" var="plist">
						<li>
							<a href="${pageContext.request.contextPath}/${blogVo.blogId}/${plist.categoryNo}/${plist.no}">
								[${plist.categoryName}] ${plist.title}
							</a>
							<span>${plist.regDate}</span>
						</li>
					</c:forEach>
				</ul>
				</c:if>
			</div>
		</div>

		<div id="extra" style="padding-top:10px;">
			<div class="blog-logo">
				<c:if test="${blogVo.logo eq null}">
					<img src="${pageContext.request.contextPath}/assets/images/spring-logo.jpg" style="width:180px;" />
				</c:if>
				<c:if test="${blogVo.logo ne null}">
					<img src="${pageContext.request.contextPath}/${blogVo.logo}" style="width:180px;" />
				</c:if>
			</div>
		</div>
		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:forEach items="${categoryList}" var="cdata">
					<li><a href="${pageContext.request.contextPath}/${blogVo.blogId}/${cdata.no}">${cdata.name}</a></li>
				</c:forEach>
			</ul>
		</div>
		
		<div id="footer">
			<p>
				<strong>${blogVo.title}</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>