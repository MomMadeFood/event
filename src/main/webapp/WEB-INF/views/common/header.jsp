<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>coupon event</title>
		<%--
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" />
		<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
		--%>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap-4.6.0-dist/css/bootstrap.min.css"/>
		<script src="${pageContext.request.contextPath}/resources/jquery/jquery-3.5.1.min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/bootstrap-4.6.0-dist/js/bootstrap.bundle.min.js"></script>
	
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/app.css"/>
	</head>
	<body>
		<div class="d-flex flex-column vh-100">
			<nav class="navbar navbar-expand-sm bg-dark navbar-dark text-white font-weight-bold justify-content-between">
				<a class="navbar-brand" href="/"> 
					<img src="${pageContext.request.contextPath}/resources/images/logo.png" width="30" height="30" class="d-inline-block align-top">
					엄마손집밥
				</a>
				<div>
					<div>

					</div>
				</div>
			</nav>
	
			<div class="flex-grow-1 container-fluid">
				<div class="row h-100">
					<div class="col-md-4 p-3 bg-dark">
						<div class="h-100 d-flex flex-column">
							<div class="flex-grow-1" style="height: 0px; overflow-y: auto; overflow-x: hidden;">
								<%@ include file="/WEB-INF/views/common/menu.jsp" %>
							</div>
						</div>
					</div>
	
					<div class="col-md-8 p-3">
						<div class=" h-100 d-flex flex-column">
							<div class="flex-grow-1 overflow-auto pr-3" style="height: 0px">