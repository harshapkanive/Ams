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
	
		<div>	
				
		 	<form:form action="${pageContext.request.contextPath}/web/resetpwd" method="POST" modelAttribute="user" class="well form-horizontal">
				<fieldset>
					<legend>Reset User Password <span class="glyphicon glyphicon-user"></span> </legend>
				
					<div class="form-group">
						<label class="col-md-4 control-label" for="firstName" >First Name</label>  
						<div class="col-md-4 inputGroupContainer">
						  <div class="input-group">
						  <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
						  	<form:input type="text" path="firstName" id="firstName" class="form-control" placeholder="First Name"/>
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
					  	
						<table class="table">
				    		<thead>
					      		<tr>
							        <th>Employee Id</th>
							        <th>First Name</th>
							        <th>E Mail</th>
							        <th>Reset Password</th>
								</tr>
					    	</thead>
				    		<tbody>
							<c:forEach items="${USERLIST}" var="user">
								<tr>
									<td>${user.empId}</td>
									<td>${user.firstName}</td>
									<td>${user.email}</td>
									<td><a href="<c:url value='/web/resetpwd-${user.empId}' />" class="btn">Reset Password </a></td>
								</tr>
							</c:forEach>
				    		</tbody>
				    	</table>
				</c:if>	
			</div>		
			
		</div>
	
		</div>
	</div>
</div>
	
			