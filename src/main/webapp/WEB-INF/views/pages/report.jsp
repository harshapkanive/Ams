<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%-- <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> --%>
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

<script src="//cdn.datatables.net/buttons/1.2.2/js/buttons.flash.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js"></script>
<script src="//cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/pdfmake.min.js"></script>
<script src="//cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/vfs_fonts.js"></script>
<script src="//cdn.datatables.net/buttons/1.2.2/js/buttons.html5.min.js"></script>
<script src="//cdn.datatables.net/buttons/1.2.2/js/buttons.print.min.js"></script>

<script type="text/javascript">
  
  		$(document).ready(function() {
  			
			$( "#empId" ).autocomplete({
				source: '${pageContext.request.contextPath}/web/employeeIdlist.json'
			});	

			$( "#startDate" ).datepicker({
		    	dateFormat: 'dd/mm/yy'
		     });			

			$( "#endDate" ).datepicker({
		    	dateFormat: 'dd/mm/yy'
		     });			

			$( "#reportDate" ).datepicker({
		    	dateFormat: 'dd/mm/yy'
		     });	

			$( "#attDumpDate" ).datepicker({
		    	dateFormat: 'dd/mm/yy'
		     });	

			$( "#defstartDate" ).datepicker({
		    	dateFormat: 'dd/mm/yy'
		     });			

			$( "#defendDate" ).datepicker({
		    	dateFormat: 'dd/mm/yy'
		     });			
			
			
			$("#clearlink").click(function(){
				$("#reportDate").val('');
				$('#result').hide();
			});

			$("#dmpclearlink").click(function(){
				$("#attDumpDate").val('');
				$('#attDumpResult').hide();
			});
			
			
			$("#emprepclearlink").click(function(){
				$("#empId").val('');
				$("#startDate").val('');
				$("#endDate").val('');
				$('#result').hide();
			});

			
			$("#defclearlink").click(function(){
				$("#defstartDate").val('');
				$("#defendDate").val('');
				$('#defresult').hide();
			});
			
			$('#result').hide();
			$('#attDumpResult').hide();
			$('#empreportdiv').hide();
			$('#defreportdiv').hide();
			$('#dailyreportdiv').hide();
			$('#attDumpdiv').hide();
			$('#defresult').hide();
			
			
			$('#dailyreportlink').click(function(){
				$('#dailyreportdiv').toggle();
				$('#attDumpdiv').hide();
				$('#empreportdiv').hide();
				$('#defreportdiv').hide();
			});
			
			$('#empreportlink').click(function(){
				$('#empreportdiv').toggle();
				$('#attDumpdiv').hide();
				$('#dailyreportdiv').hide();
				$('#defreportdiv').hide();
			});

			$('#attDumplink').click(function(){
				$('#attDumpdiv').toggle();
				$('#empreportdiv').hide();
				$('#dailyreportdiv').hide();
				$('#defreportdiv').hide();
			});

			$('#defreportlink').click(function(){
				$('#attDumpdiv').hide();
				$('#empreportdiv').hide();
				$('#dailyreportdiv').hide();
				$('#defreportdiv').toggle();
			});
			
			$('#btndailyreport').click(function(){
				if(!$.trim($("#reportDate").val()).length) {
					alert('Please select a Date');
					$("#reportDate").focus();
					return;
				}
				else
				{
					var repDate = $("#reportDate").val().replace(/\//g,"~");
					var dataurl = '${pageContext.request.contextPath}/web/reports/attenddaily-' + repDate;
					
					 $('#result').show();
					 
	 				 $('#attendTable').DataTable({
	 						dom: 'Bfrtip',
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
			  		        	"data" : "addressLocation"
			  		        },{
			  		            "data" : "date"
			  		        },{
			  		            "data" : "totalTime"
			  		        }],
			  		      buttons: ['excel', 'pdf', 'print']
			  		    }); 					
				}
				
			});
			
			$('#btnAttDump').click(function(){
				if(!$.trim($("#attDumpDate").val()).length) {
					alert('Please select a Date');
					$("#attDumpDate").focus();
					return;
				}
				else
				{
					var dumpDate = $("#attDumpDate").val().replace(/\//g,"~");
					var dataurl = '${pageContext.request.contextPath}/web/reports/attenddump-' + dumpDate;
					
					 $('#attDumpResult').show();
					 
	 				 $('#attDumpTable').DataTable({
	 						dom: 'Bfrtip',
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
			  		        	"data" : "addressLocation"
			  		        },{
			  		            "data" : "date"
			  		        },{
			  		            "data" : "timestamp"
			  		        }],
			  		      buttons: ['excel', 'pdf', 'print']
			  		    }); 					
				}
				
			});			
			
			$('#btndefreport').click(function() {
				
				var criteria='';
				
				 if(!$.trim($("#defstartDate").val()).length) {
						alert('Please select Start Date');
						$("#defstartDate").focus();
						return;
				}
				 if ($.trim($("#defstartDate").val()).length) {
					 var stdt = $("#defstartDate").val().replace(/\//g,"~");
					 criteria = criteria + '-' + stdt; 
				 }
				 if(!$.trim($("#defendDate").val()).length) {
						alert('Please select End Date');
						$("#defendDate").focus();
						return;
				}
				 if ($.trim($("#defendDate").val()).length) {
					 var enddt = $("#defendDate").val().replace(/\//g,"~");
					 criteria = criteria + '-' + enddt; 
				 } 				
				
				 var dataurl = '${pageContext.request.contextPath}/web/reports/attenddefaulter-' + criteria;
				 
				 $('#defresult').show();
				 
				 $('#defReportTable').DataTable({
						dom: 'Bfrtip',
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
		  		            "data" : "pa.pId"
		  		        }, {
		  		            "data" : "pa.projectName"
		  		        }],
		  		      buttons: ['excel', 'pdf', 'print']
		  		    }); 				 
			});
			
			
			
			$('#btnreport').click(function() {
			//enhancement to get monthly report			
/* 				 if(!$.trim($("#empId").val()).length) { // zero-length string AFTER a trim
					 	$("#empId").val('');
					 	$("#empId").focus();
			           alert('Please select Employee');
				 	return;
			     }
 */				 
				var criteria = $("#empId").val();
				
				 if(!$.trim($("#startDate").val()).length) {
						alert('Please select Start Date');
						$("#startDate").focus();
						return;
				}
				 if ($.trim($("#startDate").val()).length) {
					 var stdt = $("#startDate").val().replace(/\//g,"~");
					 criteria = criteria + '-' + stdt; 
				 }
				/*  else
				{
					 criteria = criteria + '-' + 0;
				} */
				 if(!$.trim($("#endDate").val()).length) {
						alert('Please select End Date');
						$("#endDate").focus();
						return;
				}
				 if ($.trim($("#endDate").val()).length) {
					 var enddt = $("#endDate").val().replace(/\//g,"~");
					 criteria = criteria + '-' + enddt; 
				 } 
/* 				 else
				{
						 criteria = criteria + '-' + 0;
				}				 
 */
				 var dataurl = '${pageContext.request.contextPath}/web/reports/attend-' + criteria;
				 
				 $('#result').show();
				 
 				 $('#attendTable').DataTable({
 						dom: 'Bfrtip',
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
		  		        	"data" : "addressLocation"
		  		        },{
		  		            "data" : "date"
		  		        },{
		  		            "data" : "totalTime"
		  		        }],
		  		      buttons: ['excel', 'pdf', 'print']
		  		    }); 				 
				 
		    });			
   			
  		});
  	

 </script>

</head>
<body>

<div class="col-md-10 well-fix">
	<div class="container well">
	 	<div class="container">
	 	
	 		<div> <h3> Attendance Reports <span class="glyphicon glyphicon-list-alt"></span> </h3> </div>
	 		
			<div>
				<a href="#" id="dailyreportlink">Daily Report</a>
				<br>
				<a href="#" id="attDumplink">Attendance Dump</a>
				<br>
				<a href="#" id="empreportlink">Employee Report</a>
				<br>
				<a href="#" id="defreportlink">Defaulters Report</a>
				
			</div>	 		

	<br>
	<div id="dailyreportdiv" class="well form-horizontal">
	
			<div class="form-group">
				<label class="col-md-4 control-label" for="reportDate" >Report Date</label>  
				  <div class="col-md-4 inputGroupContainer">
				  <div class="input-group">
					  <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					  	<input type="text" id="reportDate" class="form-control" placeholder="Report Date"/>
				    </div>
				  </div>
			</div>

			<div class="form-group">
				  <label class="col-md-4 control-label"></label>
				  <div class="col-md-4">
					<input type="submit" id="btndailyreport" value="Daily Report" class="btn btn-primary btn-sm"/>&nbsp; <a href="#" id="clearlink">Clear</a>	
				  </div>
			</div>
	</div>
	
	<div id="attDumpdiv" class="well form-horizontal">

			<div class="form-group">
				<label class="col-md-4 control-label" for="attDumpDate" >Attendance Dump Date</label>  
				  <div class="col-md-4 inputGroupContainer">
				  <div class="input-group">
					  <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					  	<input type="text" id="attDumpDate" class="form-control" placeholder="Dump Date"/>
				    </div>
				  </div>
			</div>

			<div class="form-group">
				  <label class="col-md-4 control-label"></label>
				  <div class="col-md-4">
					<input type="submit" id="btnAttDump" value="Attendance Dump" class="btn btn-primary btn-sm"/> &nbsp; <a href="#" id="dmpclearlink">Clear</a>	
				  </div>
			</div>	
	</div>
	
	<div id="empreportdiv" class="well form-horizontal">
		
		<fieldset>
		<div class="form-group">
			  <label class="col-md-4 control-label" for="empId" >Employee ID</label>  
			  <div class="col-md-4 inputGroupContainer">
			  	<div class="input-group">
			  		<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
					<input type="text" id="empId" class="form-control" placeholder="Employee ID" required="true" />
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
					<input type="submit" id="btnreport" value="Get Report" class="btn btn-primary btn-sm"/> &nbsp; <a href="#" id="emprepclearlink">Clear</a>	
				  </div>
			</div>
		</fieldset>			
	</div>

	<div id="defreportdiv" class="well form-horizontal">
		
		<fieldset>
			<div class="form-group">
				<label class="col-md-4 control-label" for="defstartDate" >Start Date</label>  
				  <div class="col-md-4 inputGroupContainer">
				  <div class="input-group">
					  <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					  	<input type="text" id="defstartDate" class="form-control" placeholder="Start Date"/>
				    </div>
				  </div>
			</div>

			<div class="form-group">
				<label class="col-md-4 control-label" for="defendDate" >End Date</label>  
				  <div class="col-md-4 inputGroupContainer">
				  <div class="input-group">
					  <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					  	<input type="text" id="defendDate" class="form-control" placeholder="End Date" />
				    </div>
				  </div>
			</div>
			
			<div class="form-group">
				  <label class="col-md-4 control-label"></label>
				  <div class="col-md-4">
					<input type="submit" id="btndefreport" value="Defaulter Report" class="btn btn-primary btn-sm"/> &nbsp; <a href="#" id="defclearlink">Clear</a>	
				  </div>
			</div>
		</fieldset>			
	</div>

	<div id="result"> 

		<table id="attendTable" class="display" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>Emp Id</th>
					<th>First Name</th>
					<th>Project Id</th>
					<th>Project Name</th>
					<th>Location</th>
					<th>Attnd Date</th>
					<th>Total Time (Hrs) </th>
				</tr>
			</thead>
	
			<tbody>
			</tbody>
		</table>
	</div>

	<div id="attDumpResult"> 

		<table id="attDumpTable" class="display" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>Emp Id</th>
					<th>First Name</th>
					<th>Project Id</th>
					<th>Project Name</th>
					<th>Location</th>
					<th>Attnd Date</th>
					<th>Time Stamp </th>
				</tr>
			</thead>
	
			<tbody>
			</tbody>
		</table>
	</div>

	<div id="defresult"> 
		<table id="defReportTable" class="display" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>Emp Id</th>
					<th>First Name</th>
					<th>Project Id</th>
					<th>Project Name</th>
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