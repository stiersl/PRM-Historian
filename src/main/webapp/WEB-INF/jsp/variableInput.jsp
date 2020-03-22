<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:import url="common/header.jsp">
	<c:param name="pageTitle">variableInput</c:param>
	<c:param name="activeNav" value="variableInput" />
</c:import>

<h2>New Variable Creation</h2>

<body>
	<c:url var="variableInputUrl" value="/variableInput" />
	<form:form id="variableInput" action="${variableInputUrl}" method="POST"
		modelAttribute="variableData">
		
		<div class="inputElement">
			<label class="fnt-bold" for="varNameInput">Variable Name:</label>
			<form:input class="form-control" id="varNameInput" type="text"
				name="varName" path="varName" placeholder="enter Variable Name"
				required="required" />
			<form:errors path="varName" class="error" />
		</div>
		<div class="inputElement">
			<label class="fnt-bold" for="varDescInput">Variable Description:</label>
			<form:input class="form-control" id="varDescInput" type="text"
				name="varDesc" path="varDesc" placeholder="enter Variable Description" />
			<form:errors path="varDesc" class="error" />
		</div>
		
		<div class="input-group mb-3">
  			<div class="input-group-prepend">
    		<label class="input-group-text" for="inputvarType">Variable Type:</label>
  			</div>
  			<form:select class="custom-select" id="inputvarType" name="varType" path="varType" >
    			<option selected value="N">Numeric</option>
    			<option value="S">String</option>
    			<option value="B">Boolean</option>
  			</form:select>
  			<form:errors path="varType" class="error" />
		</div>

        <div class="inputElement">
			<label class="fnt-bold" for="engUnitsInput">Engineering Units:</label>
			<form:input class="form-control" id="engUnitsInput" type="text"
				name="engUnits" path="engUnits" placeholder="enter Eng Units" />
			<form:errors path="engUnits" class="error" />
		</div>
		
        <div class="inputElement">
			<label class="fnt-bold" for="precisonInput">Precision:</label>
			<form:input class="form-control" id="precisonInput" type="number" value = "0"
				name="precison" path="precison" placeholder="enter precison" />
			<form:errors path="precison" class="error" />
		</div>
		
        <div class="inputElement">
			<label class="fnt-bold" for="maxScaleInput">Maximum Scale:</label>
			<form:input class="form-control" id="maxScaleInput" type="number"
				name="maxScale" path="maxScale" placeholder="enter max Scale" />
			<form:errors path="maxScale" class="error" />
		</div>
		
	    <div class="inputElement">
			<label class="fnt-bold" for="minScaleInput">Minimum Scale:</label>
			<form:input class="form-control" id="minScaleInput" type="number"
				name="minScale" path="minScale" placeholder="enter min Scale" />
			<form:errors path="minScale" class="error" />
		</div>	
		
	    <div class="inputElement">
			<label class="fnt-bold" for="snapshotRateInput">Snapshot Rate:(seconds)</label>
			<form:input class="form-control" id="snapshotRateInput" type="number" value = "60"
				name="snapshotRate" path="snapshotRate" placeholder="enter SnapshotRate.." />
			<form:errors path="snapshotRate" class="error" />
		</div>	
		
		
		<div class="inputElement">
			<label class="fnt-bold" for="snapshotTresholdInput">Snapshot Threshold:</label>
			<form:input class="form-control" id="snapshotTresholdInput" type="number" value = "0"
				name="snapshotTreshold" path="snapshotTreshold" placeholder="enter Snapshot Threshold.." />
			<form:errors path="snapshotTreshold" class="error" />
		</div>	
		
		 <div class="inputElement">
			<label class="fnt-bold" for="serverIdInput">Server ID</label>
			<form:input class="form-control" id="serverIdInput" type="number" value = "1"
				name="serverId" path="serverId" placeholder="enter Server id" />
			<form:errors path="serverId" class="error" />
		</div>
				
		<div class="input-group mb-3">
  			<div class="input-group-prepend">
    		<label class="input-group-text" for="inputActive">Active:</label>
  			</div>
  			<form:select class="custom-select" id="inputActive" name="active" path="active" >
    			<option selected value="true">True</option>
    			<option value="false">false</option>
  			</form:select>
  			<form:errors path="active" class="error" />
		</div>

		<form:input  type="text" name="varId" path="varId" />
			

		<div class="inputElement">
			<p></p>
			<input class="btn btn-primary" name="submit" value="Submit"
				type="submit" />
		</div>
		<p class = "error"> ${error} </p>
	</form:form>
</body>

<c:import url="common/footer.jsp" />