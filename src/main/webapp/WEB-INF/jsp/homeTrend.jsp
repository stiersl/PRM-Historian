<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:import url="common/header.jsp">
	<c:param name="pageTitle">Trending</c:param>
	<c:param name="activeNav" value="trending" />
</c:import>

<div id="mainContent">
	<div id="variables">
	<h3 class ="text-left">Variables</h3>
	<table class="table table-striped table-bordered table-hover">
  	<thead>
  	<tr> 
  		<th scope="col" class="col-xs">Variable</th> 
  		<th scope="col" class="col-xs">Description</th>
  		<th scope="col" class="col-xs"></th>
  	<tr>
  	</thead>
  	<tbody>
		<c:forEach var="variable" items="${variables}">
	  	<tr> 
			<th scope="row">${variable.varName}</th>
			<td>${variable.varDesc}</td>
			<td>
				<c:url var="homeTrendHref" value="/homeTrend">
					<c:param name="varId">${variable.varId}</c:param>
				</c:url>
				<a href="${homeTrendHref}">show history</a>
			</td>
	  	</tr>
		</c:forEach>
	</tbody>
	</table>
	</div>

	<div id="history">
		<h3 class ="text-center">${var.varName} - ${var.varDesc}</h3>
		<Table class="table table-striped table-bordered">
			<thead>
	  			<tr> 
	  			<th scope="col" class="col-xs">Timestamp</th> 
	  			<th scope="col" class="col-xs">Value (${var.engUnits})</th>  
	  			<th scope="col" class="col-xs">Quality</th> <tr>
	  		</thead>
	  		<tbody>
	  			<c:forEach var="variableHistory" items="${variableHistories}">
	  				<tr> 
	  					<th scope="row">
	  					<fmt:parseDate value="${variableHistory.sampleTime}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="sampleTime" type="both"/>
	        				<fmt:formatDate pattern="MM/dd/yyyy HH:mm:ss" value="${sampleTime}"/>
	  					</th>
	  					<td>${variableHistory.varValue}</td>
	  					<td>${variableHistory.quality}</td> 
	  				</tr>
	  			</c:forEach>
	  		</tbody>
		</Table>
	</div>
</div>
<c:import url="common/footer.jsp" />