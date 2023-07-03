<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.12/css/dataTables.jqueryui.min.css">
<link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.2.2/css/buttons.dataTables.min.css">


  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/dataTables.jqueryui.min.js"></script>
<script src="https://cdn.datatables.net/buttons/1.2.2/js/dataTables.buttons.min.js"></script>

<!-- <script src="//cdn.datatables.net/buttons/1.2.2/js/buttons.flash.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js"></script>
<script src="//cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/pdfmake.min.js"></script>
<script src="//cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/vfs_fonts.js"></script>
<script src="//cdn.datatables.net/buttons/1.2.2/js/buttons.html5.min.js"></script>
<script src="//cdn.datatables.net/buttons/1.2.2/js/buttons.print.min.js"></script>
 -->

 	<script type="text/javascript">
	$(document).ready(function() {
		
		$('#result').hide();
		
		$( "#startDate" ).datepicker({
	    	dateFormat: 'dd/mm/yy'
	     });			

		$( "#endDate" ).datepicker({
	    	dateFormat: 'dd/mm/yy'
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
		        	console.log(JSON.stringify(data));
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
		  
		  $('#btnGenPDF').click(function(){

				 if((!$.trim($("#pId").val()).length)) { // zero-length string AFTER a trim
					 	$("#pId").val('');
					 	$("#pId").focus();
			           alert('Please select Project ID');
				 	return;
			     }
				 
					var criteria = $("#pId").val();
					
					 if($.trim($("#startDate").val()).length) {
						 var stdt = $("#startDate").val().replace(/\//g,"~");
						 criteria = criteria + '-' + stdt; 
					 }
					 else
					{
						 criteria = criteria + '-' + 0;
					}

					 if($.trim($("#endDate").val()).length) {
						 var enddt = $("#endDate").val().replace(/\//g,"~");
						 criteria = criteria + '-' + enddt; 
					 } 
					 else
					{
							 criteria = criteria + '-' + 0;
					}				 

					 var formAction = '${pageContext.request.contextPath}/web/reports/psrgenpdf-' + criteria;
					
					 $("#genpdfform").attr('action', formAction); 
					 $("#genpdfform").submit();			 
			  
		  });
		  
			$('#btnreport').click(function() {
				
				 if((!$.trim($("#pId").val()).length)) { // zero-length string AFTER a trim
					 	$("#pId").val('');
					 	$("#pId").focus();
			           alert('Please select Project ID');
				 	return;
			     }
				 
				var criteria = $("#pId").val();
				
				 if($.trim($("#startDate").val()).length) {
					 var stdt = $("#startDate").val().replace(/\//g,"~");
					 criteria = criteria + '-' + stdt; 
				 }
				 else
				{
					 criteria = criteria + '-' + 0;
				}

				 if($.trim($("#endDate").val()).length) {
					 var enddt = $("#endDate").val().replace(/\//g,"~");
					 criteria = criteria + '-' + enddt; 
				 } 
				 else
				{
						 criteria = criteria + '-' + 0;
				}				 

				 var dataurl = '${pageContext.request.contextPath}/web/reports/psr-' + criteria;
				 
				 $('#result').show();
				 
				 $('#psrtable').DataTable({
					 	"processing" : true,
		  		      	"destroy": true,
		  		      	"scrollY": true,
		  		        "ajax" : {
		  		            "url" : dataurl,
		  		            dataSrc : ''
		  		        },
		  		        "columns" : [ {
		  		            "data" : "empId"
		  		        }, {
		  		            "data" : "firstName"
		  		        }, {
		  		            "data" : "pId"
		  		        }, {
		  		            "data" : "projectName"
		  		        },{
		  		            "data" : "statusDesc"
		  		        },{
		  		            "data" : "timeStamp"
		  		        },{
		  		            "data" : "id", "render": function(data, type, row) {
		  		              return '<img src="/ams/web/psimg-'+data+'" />';
		  		          }
		  		        }]
		  		    }); 				 
				 
		    });			  
		  
		  
	});	
	
	</script>

</head>
<body>

<div class="col-md-10 well-fix">
	<div class="container well">
	 	<div class="container">

			<div> <h3> Project Status Reports <span class="glyphicon glyphicon-list-alt"></span> </h3> </div>

	<div class="well form-horizontal">
		<fieldset>
		
		<div class="form-group">
			  <label class="col-md-4 control-label" for="pId" >Project ID</label>  
			  <div class="col-md-4 inputGroupContainer">
			  	<div class="input-group">
			  		<span class="input-group-addon"><i class="glyphicon glyphicon-road"></i></span>
					<input type="text" id="pId" class="form-control" placeholder="Project ID" required="true" />
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
					<input type="text" id="projectName" class="form-control" placeholder="Project Name" required="true" />
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
					  	<input type="text" id="startDate" class="form-control" placeholder="Start Date"/>
				    </div>
				  </div>
			</div>

			<div class="form-group">
				<label class="col-md-4 control-label" for="endDate" >End Date</label>  
				  <div class="col-md-4 inputGroupContainer">
				  <div class="input-group">
					  <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					  	<input type="text" id="endDate" class="form-control" placeholder="End Date" />
				    </div>
				  </div>
			</div>


			<div class="form-group">
				<label class="col-md-4 control-label"></label>
				  <div class="col-md-4">
					<input type="button" id="btnreport" value="Get Report" class="btn btn-primary btn-sm"/> &nbsp; <a href="#" id="clearlink">Clear</a>	
				  </div>

				<form method="get" id="genpdfform">
						<input type="button" id="btnGenPDF" value="Gen PDF" class="btn btn-primary btn-sm"/>
				</form>				

			</div>

		</fieldset>				
	</div>
	
	<div id="result"> 

		<table id="psrtable" class="display" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>Emp Id</th>
					<th>First Name</th>
					<th>Project Id</th>
					<th>Project Name</th>
					<th>Status</th>
					<th>Date</th>
					<th>Image</th>
				</tr>
			</thead>
	
			<tbody>
			</tbody>
		</table>
	</div>	

		</div>
	</div>
</div>


</body>
</html>