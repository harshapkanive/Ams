<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

 	<script type="text/javascript">
	$(document).ready(function() {

		if(${message != null})
		{
			$("#tablediv").show();	
		}
		else
		{
			$("#tablediv").hide();
		}
		
		
		$("#clearlink").click(function(){
			$(this).closest('form').find("input[type=text]").val("");
			$("#tablediv").hide();
		});
		
		$( "#empId" ).autocomplete({
			minLength: 2,
			source: '${pageContext.request.contextPath}/web/employeeIdlist.json'
		});

  		$( "#pId" ).autocomplete({
 			minLength: 1,
			source: '${pageContext.request.contextPath}/web/projectIdlist.json'
		});	 
		
		$( "#projectName" ).autocomplete({
			minLength: 2,
			source: '${pageContext.request.contextPath}/web/projectnamelist.json'
		});
		
		 $("#ProjectNameLink").click(function() {
			  var url = "${pageContext.request.contextPath}/web/get-projectbyName-" + $( "#projectName" ).val();
		        $.getJSON(url, function(data) {
		        	//console.log(JSON.stringify(data));
		        	$( "#pId" ).val(data['pId']);
		        	
		        });		        
		        
		    });			
		
		  $("#ProjectIDLink").click(function() {
			  var url = "${pageContext.request.contextPath}/web/get-project-" + $( "#pId" ).val();
		        $.getJSON(url, function(data) {
		        	//console.log(JSON.stringify(data));
		        	$( "#projectName" ).val(data['name'] + "," +data['location']);
		        	
		        });		        
		        
		    });		
		
	
		  $( "#startDate" ).datepicker({
		    	dateFormat: 'dd/mm/yy'
		 });		
		
		  $("#checkassignlink").click(function() {
			  var url = "${pageContext.request.contextPath}/web/get-assign-" + $( "#empId" ).val();
		        //alert(url);
		       /*  $.get(url, function(jsondata, status){
		        	var data = JSON.parse(jsondata);
		            alert("Data: " + data + "\nStatus: " + status);
		        }); */
		        
		        $.getJSON(url, function(data) {
		        	$('#tablediv').show();
		        	$('#pa_table').find('tbody').empty();
		        	//console.log(JSON.stringify(data));
		         	 $.each(data, function(index, pa) {
		            	var editurl = "${pageContext.request.contextPath}/web/projectassign-edit-";
		         		//$("#patablebody").append('<tr><td>'+pa.id+'</td><td>'+pa.empId+'</td><td>'+pa.projectName+'</td> <td>'+pa.startDate+'</td></tr>');
		         		$("#patablebody").append('<tr><td>'+pa.empId+'</td><td>'+pa.pId+'</td><td>'+pa.projectName+'</td> <td>'+pa.startDate+'</td>');
		         		$("#patablebody").append('<td><a href="' + editurl + pa.id + '" >Edit</a></td></tr>');
		         	 });  
		        });		        
		        
		    });		  
		  
	});	
	
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
				<c:url value="/web/assignprojectupdate" var="userActionUrl" />
			</c:when>
			<c:otherwise>
				<c:url value="/web/assignproject" var="userActionUrl" />
			</c:otherwise>
		</c:choose>		
		
	 	<form:form action="${userActionUrl}" modelAttribute="projectAssignment" method="POST" class="well form-horizontal">
			<fieldset>

			<legend>
				Assign Project <span class="glyphicon glyphicon-briefcase"></span>
			</legend>

			<div class="form-group">
				  <label class="col-md-4 control-label" for="empId" >Employee ID</label>  
				  <div class="col-md-4 inputGroupContainer">
				  	<div class="input-group">
			  		<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
					<c:choose>
						<c:when test="${edit}">
							<form:input type="text" path="empId" id="empId" class="form-control" disabled="true"/>
								<form:input type="hidden" path="empId" id="hdnempId"/>
								<form:input type="hidden" path="id" id="id"/>
						</c:when>    
						<c:otherwise>
							<form:input type="text" path="empId" id="empId" class="form-control" placeholder="Employee ID" required="true" />
							<div class="has-error">
								<form:errors path="empId" class="help-inline"/>
							</div>
						</c:otherwise>
					</c:choose>
				    </div>
				    		<div>
								<a href="#" id="checkassignlink">
		          						<span class="glyphicon glyphicon-search"></span>
		          						Check Assignments
		        				</a>
							</div>        				
				  </div>
			</div>

			<div class="form-group">
				  <label class="col-md-4 control-label" for="pId" >Project ID</label>  
					  <div class="col-md-4 inputGroupContainer">
						  	<div class="input-group">
					  		<span class="input-group-addon"><i class="glyphicon glyphicon-road"></i></span>
								<form:input type="text" path="pId" id="pId" class="form-control" placeholder="Project ID" required="true" />
								<div class="has-error">
									<form:errors path="pId" class="help-inline"/>
								</div>
					    	</div>
							<div>
								<a href="#" id="ProjectIDLink">
          							<span class="glyphicon glyphicon-search"></span>
          									Get Info
        							</a>
							</div>								
				  </div>
			</div>

			<div class="form-group">
			  <label class="col-md-4 control-label" for="projectName" >Project Name</label>  
				  <div class="col-md-4 inputGroupContainer">
					  <div class="input-group">
					  <span class="input-group-addon"><i class="glyphicon glyphicon-road"></i></span>
					  	<form:input type="text" path="projectName" id="projectName" class="form-control" placeholder="Project Name"/>
						<div class="has-error">
							<form:errors path="projectName" class="help-inline"/>
						</div>
				    </div>
				    
					<div>
							<a href="#" id="ProjectNameLink">
          						<span class="glyphicon glyphicon-search"></span>
          						Get Info
        					</a>
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
 							<input type="submit" value="Update" class="btn btn-primary btn-sm"/> &nbsp;  <a href="<c:url value='projectassign-del-${projectAssignment.id}-${projectAssignment.empId}' />" id="clearlink">Delete</a>		
 						</c:when>
 						<c:otherwise>
 							<input type="submit" value="Assign Project" class="btn btn-primary btn-sm"/> &nbsp; <a href="#" id="clearlink">Clear</a>	
 						</c:otherwise>
 					</c:choose>
				  </div>
			</div>
			
		</fieldset>
	</form:form>			
		
	
	<hr>
			<div id="tablediv">
			  <table class="table table-striped" id="pa_table">
			    <thead>
			      <tr>
			        <th>Emp Id</th>
			        <th>Project Id</th>
			        <th>Project Name</th>
			        <th>Start Date</th>
			        <th>Edit</th>
			      </tr>
			    </thead>
			    <tbody id="patablebody">
			    <c:if test="${projAssignList ne null }">
			      	<c:forEach items="${projAssignList}" var="pa">
						<tr>
							<td>${pa.empId}</td>
							<td>${pa.pId}</td>
							<td>${pa.projectName}</td>
							<td>${pa.startDate}</td>
							<td><a href="<c:url value='/web/projectassign-edit-${pa.id}'/>" class="btn">Edit<span class="glyphicon glyphicon-edit"></span> </a></td>
						</tr>
					</c:forEach>
			    </c:if>
  		     	</tbody>
			  </table>	
			</div>

		</div>
	</div>
</div>			
		
</body>
</html>