<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
				<c:url value="/web/userupdate" var="userActionUrl" />
			</c:when>
			<c:otherwise>
				<c:url value="/web/user" var="userActionUrl" />
			</c:otherwise>
		</c:choose>		
		
		<form:form class="well form-horizontal" method="POST" modelAttribute="user" action="${userActionUrl}" id="user_form">
			<fieldset> 
			
			<c:choose>
				<c:when test="${edit}">
					<legend>
					 Edit User <span class="glyphicon glyphicon-user"></span> &nbsp; <span class="glyphicon glyphicon-edit"></span>
					</legend>
				</c:when>
				<c:otherwise>
				  	<legend>Create User <span class="glyphicon glyphicon-user"></span> </legend>
				</c:otherwise>
			</c:choose>				
				
					<div class="form-group">
						  <label class="col-md-4 control-label" for="empId" >Employee ID</label>  
						  <div class="col-md-4 inputGroupContainer">
						  	<div class="input-group">
						  		<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
								<c:choose>
									<c:when test="${edit}">
										<form:input type="text" path="empId" id="empId" class="form-control" disabled="true"/>
										<form:input type="hidden" path="empId" id="hdnempId"/>
									</c:when>    
									<c:otherwise>
										<form:input type="text" path="empId" id="empId" class="form-control" placeholder="Employee ID" required="true" />
										<div class="has-error">
											<form:errors path="empId" class="help-inline"/>
										</div>
									</c:otherwise>
								</c:choose>
						    </div>
						  </div>
					</div>
					
					<div class="form-group">
					  <label class="col-md-4 control-label" for="firstName" >First Name</label>  
					  <div class="col-md-4 inputGroupContainer">
					  <div class="input-group">
					  <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
					  	<form:input type="text" path="firstName" id="firstName" class="form-control" placeholder="First Name" required="true"/>
						<div class="has-error">
							<form:errors path="firstName" class="help-inline"/>
						</div>
					    </div>
					  </div>
					</div>
					
					<div class="form-group">
					  <label class="col-md-4 control-label" for="lastName" >Last Name</label> 
					    <div class="col-md-4 inputGroupContainer">
					    <div class="input-group">
					  <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
					  <form:input type="text" path="lastName" id="lastName" class="form-control" placeholder="Last Name"  />
						<div class="has-error">
							<form:errors path="lastName" class="help-inline"/>
						</div>
					    </div>
					  </div>
					</div>					
					
<%-- 				<div class="form-group">
				  <label class="col-md-4 control-label" for="password" >Password</label> 
				    <div class="col-md-4 inputGroupContainer">
				    <div class="input-group">
				  	<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
						<c:choose>
							<c:when test="${edit}">
								<form:input type="password" path="password" id="password" class="form-control" disabled="true" />
							</c:when>    
							<c:otherwise>
								<form:input type="password" path="password" id="password" class="form-control" placeholder="Password" required="true" />
								<div class="has-error">
									<form:errors path="empId" class="help-inline"/>
								</div>
							</c:otherwise>
						</c:choose>
						<div class="has-error">
							<form:errors path="password" class="help-inline"/>
						</div>				  
				    </div>
				  </div>
				</div> --%>					
					
				<div class="form-group">
				  <label class="col-md-4 control-label" for="email">E-Mail</label>  
				    <div class="col-md-4 inputGroupContainer">
				    <div class="input-group">
				        <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
						<form:input type="email" path="email" id="email" class="form-control" placeholder="E-Mail Address" required="true" />
						<div class="has-error">
							<form:errors path="email" class="help-inline"/>
						</div>
				    </div>
				  </div>
				</div>
					
				<div class="form-group">
				  <label class="col-md-4 control-label"></label>
				  <div class="col-md-4">
					<c:choose>
 						<c:when test="${edit}">
 							<input type="submit" value="Update User" class="btn btn-primary btn-sm"/>		
 						</c:when>
 						<c:otherwise>
 							<input type="submit" value="Create User" class="btn btn-primary btn-sm"/>	
 						</c:otherwise>
 					</c:choose>
				  </div>
				</div>

			</fieldset>
	
		</form:form>
		
		<hr>

		<c:if test="${newuser ne null }">
			<div class="container">
			  <table class="table table-bordered">
			    <thead>
			      <tr>
			        <th>Emp Id</th>
			        <th>First Name</th>
			        <th>Last Name</th>
			        <th>Email</th>
			      </tr>
			    </thead>
			    <tbody>
			      <tr>
			        <td><a href="<c:url value='/web/user-edit-${newuser.empId}' />">${newuser.empId}</a></td>
					<td>${newuser.firstName}</td>
					<td>${newuser.lastName}</td>
					<td>${newuser.email}</td>
			      </tr>
			     </tbody>
			  </table>
			</div>			
		</c:if>
	</div>
	
	</div>
</div>		
