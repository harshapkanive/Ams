<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <!-- Navigation -->
    <nav class="navbar navbar-custom navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
               <a class="primary-nav-logo" href="#"></a> 
              <!-- <h3>Attendance Management System</h3> -->
            </div>
            
            <!-- Collect the nav links, forms, and other content for toggling -->
             <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right" >
                	<c:if test="${not empty USER}">
	                    <li>
	                        <a href="<c:url value="/web/logout" />">Logout <span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-log-out"></span></a>
	                    </li>
                    </c:if>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>