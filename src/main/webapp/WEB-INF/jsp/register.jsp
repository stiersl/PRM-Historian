<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:import url="common/header.jsp">
	<c:param name="pageTitle">Register</c:param>
	<c:param name="activeNav" value="register" />
</c:import>

<h2>New User Registration</h2>

<body>
	<c:url var="registrationUrl" value="/register" />
	<form:form id="registration" action="${registrationUrl}" method="POST"
		modelAttribute="registerData">
		<div>All fields are required unless otherwise noted.</div>
		<div class="inputElement">
			<label class="fnt-bold" for="firstNameInput">First Name</label>
			<form:input class="form-control" id="firstNameInput" type="text"
				name="firstName" path="firstName" placeholder="enter first name"
				required="required" />
			<form:errors path="firstName" class="error" />
		</div>
		<div class="inputElement">
			<label class="fnt-bold" for="lastNameInput">Last Name</label>
			<form:input class="form-control" id="lastNameInput" type="text"
				name="lastName" path="lastName" placeholder="enter last name"
				required="required" />
			<form:errors path="lastName" class="error" />
		</div>
		<div class="inputElement">
			<label class="fnt-bold" for="phoneNumberInput">Phone Number
				(optional)</label>
			<form:input class="form-control" id="phoneNumberInput" type="tel"
				name="phoneNumber" path="phoneNumber"
				pattern="\\([0-9]{3}\\)[0-9]{3}-[0-9]{4}"
				placeholder="(XXX)XXX-XXXX" />
		</div>

		<div class="inputElement">
			<label class="fnt-bold" for="emailInput">Email</label>
			<form:input class="form-control" id="emailInput" type="email"
				name="email" path="email" placeholder="enter email"
				required="required" />
			<form:errors path="email" class="error" />
		</div>

		<div class="inputElement">
			<label class="fnt-bold" for="emailConfirmationInput">Confirm
				Email</label>
			<form:input class="form-control" id="emailConfirmationInput"
				type="email" name="emailConfirmation" path="emailConfirmation"
				placeholder="confirm email" required="required" />
			<form:errors path="emailMatching" class="error" />
		</div>

		<div class="inputElement">
			<label class="fnt-bold" for="passwordInput">Password</label>
			<form:input class="form-control" id="passwordInput" type="password"
				name="password" path="password" placeholder="enter password"
				required="required" />
			<form:errors path="password" class="error" />
		</div>

		<div class="inputElement">
			<label class="fnt-bold" for="passwordConfirmationInput">Confirm</label>
			<form:input class="form-control" id="passwordConfirmationInput"
				type="password" name="passwordConfirmation"
				path="passwordConfirmation" placeholder="confirm password"
				required="required" />
			<form:errors path="passwordMatching" class="error" />
		</div>

		<div class="inputElement">
			<label class="fnt-bold" for="birthdateInput">Birth Date</label>
			<form:input class="form-control" id="birthdateInput" type="date"
				name="birthdate" path="birthdate" placeholder="enter birthdate"
				required="required" />
			<form:errors path="birthdate" class="error" />
		</div>

		<div class="inputElement">
			<p></p>
			<input class="btn btn-primary" name="submit" value="Submit"
				type="submit" />
		</div>
	</form:form>
</body>

<c:import url="common/footer.jsp" />