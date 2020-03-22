<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title><c:out value="${param.pageTitle}" /> - Process
	Reliability Monitor</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
		integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
		crossorigin="anonymous">
<link href="<c:url value="/css/site.css"/>" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<nav class="navbar navbar-expand navbar-dark bg-dark">
		<img id="sa-image" src="<c:url value="/img/SABull.png"/>"
			alt="SA Bull" /> <span class="navbar-brand"> Process
			Reliability Monitor</span>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav">
				<li class="nav-item ${param.activeNav == 'trending'? 'active':''}">
					<c:url value="/homeTrend" var="homeTrendUrl" /> <a class="nav-link"
					href="${homeTrendUrl}">Trending</a>
				</li>
				<li class="nav-item ${param.activeNav == 'variables'? 'active':''}">
					<c:url value="/variables" var="variablesUrl" /> <a class="nav-link"
					href="${variablesUrl}">Variables</a>
				</li>
				<li class="nav-item ${param.activeNav == 'register'? 'active':''}">
					<c:url value="/register" var="registerUrl" /> <a class="nav-link"
					href="${registerUrl}">New User</a>
				</li>
				<li class="nav-item ${param.activeNav == 'login'? 'active':''}">
					<c:url value="/login" var="loginUrl" /> <a class="nav-link"
					href="${loginUrl}">Login</a>
				</li>
			</ul>
		</div>
	</nav>

<div class="container-fluid">
