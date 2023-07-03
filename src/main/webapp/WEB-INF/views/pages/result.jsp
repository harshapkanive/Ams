<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>
		<div>
			<c:if test="${USERLIST ne null }">

					  <!-- Default panel contents -->
				  	<div class="panel-heading">
				  		<span class="lead">Search Result </span> <span class="glyphicon glyphicon-list"></span>
				  	</div>
				  	
					<table class="table">
			    		<thead>
				      		<tr>
						        <th>Employee Id</th>
						        <th>First Name</th>
						        <th>Attendance <span class="glyphicon glyphicon-time"></span></th>
						        <th>Edit User</th>
							</tr>
				    	</thead>
			    		<tbody>
						<c:forEach items="${USERLIST}" var="user">
							<tr>
								<td>${user.empId}</td>
								<td>${user.firstName}</td>
								<td><a href="<c:url value='/web/viewattd-${user.empId}' />" class="btn">View Attd <span class="glyphicon glyphicon-eye-open"></span></a></td>
								<td><a href="<c:url value='/web/user-edit-${user.empId}' />" class="btn">Edit User <span class="glyphicon glyphicon-edit"></span> </a></td>
							</tr>
						</c:forEach>
			    		</tbody>
			    	</table>
					
		</c:if>	
	</div>

</body>
</html>


