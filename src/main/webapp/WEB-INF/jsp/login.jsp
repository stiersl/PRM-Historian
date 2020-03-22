<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:import url="common/header.jsp">
	<c:param name="pageTitle">Login</c:param>
	<c:param name="activeNav" value="login" />
</c:import>

<h2>Login</h2>

<body>
	<c:url var="loginUrl" value="/login" />
	<form:form id="login" action="${loginUrl}" method="POST"
		modelAttribute="loginData">

		<div class="inputElement">
			<label class="fnt-bold" for="emailInput">Email Address</label>
			<form:input class="form-control" id="email" type="email" name="email"
				path="email" required="required" />
			<form:errors path="email" class="error" />
		</div>

		<div class="inputElement">
			<label class="fnt-bold" for="passwordInput">Password</label>
			<form:input class="form-control" id="passwordInput" type="password"
				name="password" path="password" required="required" />
			<form:errors path="password" class="error" />
		</div>

		<div class="inputElement">
			<p></p>
			<input class="btn btn-primary" name="submit" value="Submit"
				type="submit" />
		</div>
	</form:form>
</body>

<c:import url="common/footer.jsp" />