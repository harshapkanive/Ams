<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>

<head>

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Infra Support Engineering, Attendance Manager">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title><tiles:getAsString name="title" /></title>

    <!-- Bootstrap Core CSS -->
	<link href="<c:url value='/static/css/bootstrap.min.css' />"  rel="stylesheet"></link>

    <!-- Custom CSS -->
	<link href="<c:url value='/static/css/stylesheet.css' />"  rel="stylesheet"></link>

    <!-- jQuery -->
    <script src="<c:url value='/static/js/jquery.js' />"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="<c:url value='/static/js/bootstrap.min.js' />"></script>

  <script type="text/javascript">
  function htmlbodyHeightUpdate(){
		var height3 = $( window ).height()
		var height1 = $('.nav').height()+50
		height2 = $('.main').height()
		if(height2 > height3){
			$('html').height(Math.max(height1,height3,height2)+10);
			$('body').height(Math.max(height1,height3,height2)+10);
		}
		else
		{
			$('html').height(Math.max(height1,height3,height2));
			$('body').height(Math.max(height1,height3,height2));
		}
		
	}
	$(document).ready(function () {
		htmlbodyHeightUpdate()
		$( window ).resize(function() {
			htmlbodyHeightUpdate()
		});
		$( window ).scroll(function() {
			height2 = $('.main').height()
  			htmlbodyHeightUpdate()
		});
	});
  
  </script>

</head>
 
<body>
		<header id="header">
			<tiles:insertAttribute name="header" />
		</header>

		<section id="site-content">
			<tiles:insertAttribute name="body" />
		</section>
		
		<footer id="footer">
			<tiles:insertAttribute name="footer" />
		</footer>		
</body>
</html>