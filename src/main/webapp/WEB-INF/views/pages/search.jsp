<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

	<script type="text/javascript">
	
	$(document).ready(function() {
			$( "#empId" ).autocomplete({
				source: '${pageContext.request.contextPath}/web/employeeIdlist.json'
			});	
			
			$("#clearlink").click(function(){
				$(this).closest('form').find("input[type=text]").val("");
				$("#searchresult").hide();
			});
			
			
	});	
	</script>


<div class="col-md-10 well-fix">
	<div class="container well">
	 	<div class="container">
			
			<c:if test="${message != null}">
				<div class="alert alert-success"> 
					<i class="glyphicon glyphicon-thumbs-up"></i> <c:out value="${message}"/> 
				</div>
			</c:if>	
			<c:if test="${error != null}">
				<div class="alert alert-danger">
					<p>${error}</p>
				</div>
			</c:if>
		
	 	<form:form action="${pageContext.request.contextPath}/web/searchuser" method="POST" modelAttribute="user" class="well form-horizontal">
			<fieldset>
			
			<legend>
				User Search <span class="glyphicon glyphicon-search"></span>
			</legend> 
					
			<div class="form-group">
			  <label class="col-md-4 control-label" for="firstName" >First Name</label>  
				  <div class="col-md-4 inputGroupContainer">
					  <div class="input-group">
					  <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
					  	<form:input type="text" path="firstName" id="firstName" class="form-control" placeholder="First Name" />
						<div class="has-error">
							<form:errors path="firstName" class="help-inline"/>
						</div>
				    </div>
				  </div>
			</div>

			<div class="form-group">
				  <label class="col-md-4 control-label" for="empId" >Employee ID</label>  
					  <div class="col-md-4 inputGroupContainer">
					  	<div class="input-group">
					  		<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
							<form:input type="text" path="empId" id="empId" class="form-control" placeholder="Employee ID"/>
							<div class="has-error">
								<form:errors path="empId" class="help-inline"/>
							</div>
				    </div>
				  </div>
			</div>
			
			<div class="form-group">
			  <label class="col-md-4 control-label"></label>
			  <div class="col-md-4">
					<input type="submit" value="Search" class="btn btn-primary btn-sm"/>  &nbsp; <a href="#" id="clearlink">Clear</a>	
			  </div>
			</div>			
			
			</fieldset>
		</form:form>
		
		<div id="searchresult">
			<c:if test="${USERLIST ne null }">

					  <!-- Default panel contents -->
<!-- 				  	<div class="panel-heading">
				  		<span class="lead">Search Result </span> <span class="glyphicon glyphicon-list"></span>
				  	</div> -->
				  	
					<table class="table">
			    		<thead>
				      		<tr>
						        <th>Employee Id</th>
						        <th>First Name</th>
						        <th>Edit User</th>
						        <th>In Activate <span class="glyphicon glyphicon-ban-circle"></span></th>
							</tr>
				    	</thead>
			    		<tbody>
						<c:forEach items="${USERLIST}" var="user">
							<tr>
								<td>${user.empId}</td>
								<td>${user.firstName}</td>
								<%-- <td><a href="<c:url value='/web/viewattd-${user.empId}' />" class="btn">View Attd <span class="glyphicon glyphicon-eye-open"></span></a></td> --%>
								<td><a href="<c:url value='/web/user-edit-${user.empId}' />" class="btn">Edit User <span class="glyphicon glyphicon-edit"></span> </a></td>
								<td><a href="<c:url value='/web/userinactive-${user.empId}' />" class="btn">Inactivate <span class="glyphicon glyphicon-ban-circle"></span> </a></td>
							</tr>
						</c:forEach>
			    		</tbody>
			    	</table>
					
					</c:if>	
				</div>		
			
		</div>
	</div>
</div>	