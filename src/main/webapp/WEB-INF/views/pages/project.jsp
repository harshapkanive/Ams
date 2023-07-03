<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

  	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  	<script src="<c:url value='/static/js/jquery-3.1.1.min.js' />"></script>
 	<script src="<c:url value='/static/js/jquery-ui.min.js' />"></script>  
  
  <script>
  $( function() {
    $( "#startDate" ).datepicker({
    	dateFormat: 'dd/mm/yy'
    	});
  } );
  </script>

</head>

<body>

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
			
			<c:choose>
				<c:when test="${edit}">
					<c:url value="/web/projectupdate" var="userActionUrl" />
				</c:when>
				<c:otherwise>
					<c:url value="/web/project" var="userActionUrl" />
				</c:otherwise>
			</c:choose>		
				 	
			
 	 	<form:form method="POST" modelAttribute="project" class="well form-horizontal" action="${userActionUrl}" >
			<fieldset>
			
			<c:choose>
				<c:when test="${edit}">
					<legend>
					 Update Project <span class="glyphicon glyphicon-road"></span> &nbsp; <span class="glyphicon glyphicon-edit"></span>
					</legend>
				</c:when>
				<c:otherwise>
				  	<legend>Create Project <span class="glyphicon glyphicon-road"></span> </legend>
				</c:otherwise>
			</c:choose>				

			<div class="form-group">
				  <label class="col-md-4 control-label" for="pId" >Project ID</label>  
					  <div class="col-md-4 inputGroupContainer">
						  	<div class="input-group">
						  		<span class="input-group-addon"><i class="glyphicon glyphicon-road"></i></span>
								<c:choose>
									<c:when test="${edit}">
										<form:input type="text" path="pId" id="pId" class="form-control" disabled="true"/>
										<form:input type="hidden" path="pId" id="hdnpId"/>
									</c:when>    
									<c:otherwise>
										<form:input type="text" path="pId" id="pId" class="form-control" placeholder="Project ID" required="true" />
										<div class="has-error">
											<form:errors path="pId" class="help-inline"/>
										</div>
									</c:otherwise>
								</c:choose>
					    </div>
				  </div>
			</div>

			<div class="form-group">
			  <label class="col-md-4 control-label" for="name" >Project Name</label>  
				  <div class="col-md-4 inputGroupContainer">
					  <div class="input-group">
					  <span class="input-group-addon"><i class="glyphicon glyphicon-road"></i></span>
					  	<form:input type="text" path="name" id="name" class="form-control" placeholder="Project Name" required="true"/>
						<div class="has-error">
							<form:errors path="name" class="help-inline"/>
						</div>
				    </div>
				  </div>
			</div>
			
			<div class="form-group">
				<label class="col-md-4 control-label" for="description" >Project Description</label>  
				  <div class="col-md-4 inputGroupContainer">
				  <div class="input-group">
					  <span class="input-group-addon"><i class="glyphicon glyphicon-road"></i></span>
					  	<form:input type="text" path="description" id="description" class="form-control" placeholder="Project Description" />
						<div class="has-error">
							<form:errors path="description" class="help-inline"/>
						</div>
				    </div>
				  </div>
			</div>

			<div class="form-group">
				<label class="col-md-4 control-label" for="location" >Project Location</label>  
				  <div class="col-md-4 inputGroupContainer">
				  <div class="input-group">
					  <span class="input-group-addon"><i class="glyphicon glyphicon-road"></i></span>
					  	<form:input type="text" path="location" id="location" class="form-control" placeholder="Project Location" required="true" />
						<div class="has-error">
							<form:errors path="location" class="help-inline"/>
						</div>
				    </div>
				  </div>
			</div>
			
			<div class="form-group">
				<label class="col-md-4 control-label" for="startDate" >Start Date</label>  
				  <div class="col-md-4 inputGroupContainer">
				  <div class="input-group">
					  <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					  	<form:input type="text" path="startDate" id="startDate" class="form-control" placeholder="Start Date" required="true" />
						<div class="has-error">
							<form:errors path="startDate" class="help-inline"/>
						</div>
				    </div>
				  </div>
			</div>

			<div class="form-group">
				  <label class="col-md-4 control-label"></label>
				  <div class="col-md-4">
					<c:choose>
 						<c:when test="${edit}">
 							<input type="submit" value="Update Project" class="btn btn-primary btn-sm"/>		
 						</c:when>
 						<c:otherwise>
 							<input type="submit" value="Create Project" class="btn btn-primary btn-sm"/>	
 						</c:otherwise>
 					</c:choose>
				  </div>
			</div>
	
			</fieldset>
		</form:form>
		
		<hr>			

 		<c:if test="${newproject ne null }">
			<div class="container">
			  <table class="table table-nonfluid">
			    <thead>
			      <tr>
			        <th>Project Id</th>
			        <th>Project Name</th>
			        <th>Project Desc</th>
			        <th>Location</th>
			        <th>Start Date</th>
			      </tr>
			    </thead>
			    <tbody>
			      <tr>
			        <td><a href="<c:url value='/web/project-edit-${newproject.pId}' />">${newproject.pId}</a></td>
					<td>${newproject.name}</td>
					<td>${newproject.description}</td>
					<td>${newproject.location}</td>
					<td>${newproject.startDate}</td>
			      </tr>
			     </tbody>
			  </table>
			</div>			
		</c:if> 
	
		</div>
	</div>
</div>		
				
</body>
</html>

