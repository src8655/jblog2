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
</head>
<body>
	<div id="container">
		<div id="header">
			<h1>${blogVo.title}</h1>
			<c:import url="/WEB-INF/views/includes/blog-navigation.jsp"></c:import>
		</div>
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<h4>${mainPostVo.title}</h4>
					<p>
						${mainPostVo.content}
					<p>
				</div>
				<ul class="blog-list">
					<c:forEach items="${mainPostList}" var="plist">
						<li><a href="${pageContext.request.contextPath}/${blogVo.blogId}/${plist.categoryNo}/${plist.no}">${plist.title}</a> <span>${plist.regDate}</span>	</li>
					</c:forEach>
				</ul>
			</div>
		</div>

		<div id="extra" style="padding-top:10px;">
			<div class="blog-logo">
				<c:if test="${blogVo.logo eq null}">
					<img src="${pageContext.request.contextPath}/assets/images/spring-logo.jpg">
				</c:if>
				<c:if test="${blogVo.logo ne null}">
					<img src="${pageContext.request.contextPath}/${blogVo.logo}">
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