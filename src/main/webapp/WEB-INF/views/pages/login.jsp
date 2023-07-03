<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Page Content -->
<div class="container">
	<div id="loginbox" style="margin-top: 50px;" 
		class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
		<div class="panel panel-info">
			<div class="panel-heading">
				<div class="panel-title">Attendance Management System - Sign In</div>
			</div>

			<div style="padding-top: 30px" class="panel-body">

				<div style="display: none" id="login-alert"
					class="alert alert-danger col-sm-12"></div>

				<c:url var="loginUrl" value="/web/login" />

				<form id="loginform" action="${loginUrl}" method="post" class="form-horizontal" role="form">

							<c:if test="${param.error != null}">
								<div class="alert alert-danger">
									<p>Invalid username and password.</p>
								</div>
							</c:if>
							<c:if test="${param.logout != null}">
								<div class="alert alert-success">
									<p>You have been logged out successfully.</p>
								</div>
							</c:if>					

					<div style="margin-bottom: 25px" class="input-group">
						<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span> 
						<input id="empId" name="empId" type="text" class="form-control" value=""
							placeholder="Employee ID" required>
					</div>

					<div style="margin-bottom: 25px" class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-lock"></i></span> <input id="password" name="password"
							type="password" class="form-control" placeholder="password" required>
					</div>


					<div style="margin-top: 10px" class="form-group">

						<div class="col-sm-12 controls">
							<input type="submit" class="btn btn-primary center-block" value="Log in">
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

</div>