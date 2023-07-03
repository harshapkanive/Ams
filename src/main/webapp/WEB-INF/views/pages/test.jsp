<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

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

		<legend> Sending Bulk Password Mail </legend>
		
		<p> <b>Note:</b> This is one time activity of sending initial passwords by Email to all users. This needs to be triggered after employee
			data has been successfully uploaded. This activity regenerates all User passwords and sends the password to respective User's email Id.To reset password for a single User, please use 
			<a href="${pageContext.request.contextPath}/web/resetpwd"><i>Reset Password</i></a> feature. 
		</p>
		<p> <span style="color:red"> <b> Caution: </b> This is a time consuming activity and hence done as background job. 
			This should be used only once during initial set up </span> 
		</p> 
		
		<form action="${pageContext.request.contextPath}/web/test/sendbulkmail">
			<input type="submit" value="Send Bulk Mail"/>
		</form>
 
 </div>
</div>
</div>
		
</body>
</html>