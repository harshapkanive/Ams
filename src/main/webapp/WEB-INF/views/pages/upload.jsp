<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
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

			<legend> Master Data Upload </legend>
			<p> <b>Note:</b> This is one time activity of setting up initial project Data </p> 
			<p> Upload Project Data first and then upload Employee Data. The excel files need to be in <span style="color:red"> <b> .xlsx format </b> </span> </p>
	
		<div class="well form-horizontal" >
			<form:form action="processProjData" method="post"
										enctype="multipart/form-data">
					<div>
					<p><span style="color:red"> <b>Data Format:</b> Project ID, Project Name(Should be Unique),Project Description,Start Date,Location </span></p>

					<b>Project Data</b> Upload: 
					 </div>
					<input name="projectxlsx" type="file"  > 
				<br>
				<input type="submit" value="Upload Project Data" >
			</form:form>
		</div>
		<hr>
		<div class="well form-horizontal">
			<form:form action="processEmpData" method="post"
				enctype="multipart/form-data">
				<div>
				<p> <span style="color:red"> <b> Data Format: </b> Employee ID, Email ID, First Name, Last Name,Project Name, Work Location,Project ID </span> </p>
				<b>Employee Data</b> Upload:</div>
				<input name="employeexlsx" type="file">
				<br>
				<input type="submit" value="Upload Employee Data">
			</form:form>
		</div>

		</div>
	</div>
</div>


</body>
</html>
