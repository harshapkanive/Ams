<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<nav class="navbar navbar-custom sidebar" role="navigation">
    <div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
<!-- 		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-sidebar-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Navigation</a>
		</div> -->
		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse" id="bs-sidebar-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="${pageContext.request.contextPath}/web/home">Home <span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-home"></span></a></li>

		       <c:if test="${USER.role == 'A'}">

					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">User <span class="caret"></span><span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-user"></span></a>
						<ul class="dropdown-menu forAnimate" role="menu">
				       		<li><a href="${pageContext.request.contextPath}/web/user">Create User <span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-user"></span> </a></li>
				       		<li><a href="${pageContext.request.contextPath}/web/searchuser">User Search <span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-search"></span> </a></li>
							<li class="divider"></li>
							<li><a href="${pageContext.request.contextPath}/web/resetpwd">Reset Password</a></li>
						</ul>
					</li>

					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">Project <span class="caret"></span><span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-road"></span></a>
						<ul class="dropdown-menu forAnimate" role="menu">
				       		<li><a href="${pageContext.request.contextPath}/web/project">New Project <span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-road"></span></a></li>
				       		<li><a href="${pageContext.request.contextPath}/web/assignproject">Assign Project<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-briefcase"></span></a></li>
						</ul>
					</li>

 					<li><a href="${pageContext.request.contextPath}/web/reports">Attendance Reports<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-list-alt"></span></a></li>
		       		<li><a href="${pageContext.request.contextPath}/web/psreports">Project Status Reports<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-list-alt"></span></a></li>

					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">Settings <span class="caret"></span><span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-wrench"></span></a>
						<ul class="dropdown-menu forAnimate" role="menu">
				       		<li><a href="${pageContext.request.contextPath}/web/masterdataupload">Data Upload <span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-upload"></span></a></li>
							<li class="divider"></li>
							<li><a href="${pageContext.request.contextPath}/web/test">Bulk Mail <span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-envelope"></span></a></li>
						</ul>
					</li>
		       		
		       		
		       </c:if>

			</ul>
		</div>
	</div>
</nav>