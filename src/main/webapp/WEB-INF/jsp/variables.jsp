<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:import url="common/header.jsp">
	<c:param name="pageTitle">Variables</c:param>
	<c:param name="activeNav" value="variables" />
</c:import>

<div id="mainContent">
	<div id="variables">
	<h3 class ="text-left">Variables</h3>
	<table class="table table-striped table-bordered table-hover">
  	<thead>
  	<tr> 
  		<th scope="col">Name</th>
  		<th scope="col">Desc</th>
  		<th scope="col">Type</th>
  		<th scope="col">EngUnits</th>
  		<th scope="col">Precison</th>
  		<th scope="col">Max Scale</th>
  		<th scope="col">Min Scale</th>
  		<th scope="col">Snapshot Rate</th>
  		<th scope="col">Snapshot Thold</th>
  		<th scope="col">Active</th>	
  		<th scope="col">Last Value</th>
  		<th scope="col">Last SampleTime</th>
  		<th scope="col">Last Quality</th>
  		<th scope="col">Desc Global</th>
  		<th scope="col">var id</th>
  		<th scope="col">srvr id</th>
  		<th scope="col"> </th>
  		<th scope="col"> </th>
  	<tr>
  	</thead>
  	<tbody>
		<c:forEach var="variable" items="${variables}">
	  	<tr> 
			<th scope="row">
				<c:url var="variableInputdHref" value="/variableInput">
					<c:param name="varId">${variable.varId}</c:param>
				</c:url>
				<a href="${variableInputdHref}">${variable.varName}</a>
			</th>
			<td>${variable.varDesc}</td>
			<td>${variable.varType}</td>
			<td>${variable.engUnits}</td>
			<td>${variable.precison}</td>
			<td>${variable.maxScale}</td>
			<td>${variable.minScale}</td>
			<td>${variable.snapshotRate}</td>
			<td>${variable.snapshotTreshold}</td>
			<td>${variable.active}</td>
			<td>${variable.lastValue}</td>
			<td>${variable.lastSampleTime}</td>
			<td>${variable.lastQuality}</td>	
			<td>${variable.varDescG}</td>
			<td>${variable.varId}</td>
			<td>${variable.serverId}</td>	
			<td scope="row">
				<c:url var="variableDeleteHref" value="/variableDelete">
					<c:param name="varId">${variable.varId}</c:param>
				</c:url>
				<a href="${variableDeleteHref}">delete</a>
			</td>
				
			<td scope="row">
				<c:url var="variableHistoryInsertHref" value="/variableHistoryInsert">
				</c:url>
				<form id="variableInput" action="${variableHistoryInsertHref}" method="GET">
					<input name="varId" value=${variable.varId} hidden="hidden" />
					<input id="varValueInput" type="Number"
					name="varValue" />
					<input name="submit" value="Submit" type="submit" />
				</form>
			</td>
	  	</tr>
		</c:forEach>
	</tbody>
	</table>
	</div>
</div>

<c:url var="variableInputHref" value="/variableInput"></c:url>
<a href="${variableInputHref}">Add Variable</a>

<c:import url="common/footer.jsp" />