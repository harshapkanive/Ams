<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="col-md-10 well-fix">
	<div class="container well">
	 	<div class="container">

			<div> <h3> Attendance Entries for ${EMPID} <span class="glyphicon glyphicon-list-alt"></span> </h3> </div>

			<c:if test="${ATT_ENTRIES ne null }">
			
				<div>
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Project ID</th>
								<th>Project Name</th>
								<th>Location Address</th>
								<th>Latitude</th>
								<th>Longitude</th>
								<!-- <th>Att Date</th> -->
								<th>Att Date</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ATT_ENTRIES}" var="ae">
								<tr>
									<td>${ae.pId}</td>
									<td>${ae.projectName}</td>
									<td>${ae.addressLocation}</td>
									<td>${ae.lat}</td>
									<td>${ae.lon}</td>
									<%-- <td>${ae.date}</td> --%>
									<td>${ae.timestamp}</td>
									<%-- <td><img width="100" height="100" src="<c:url value='/web/viewimage-${ae.id}' />"/></td> --%>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			
			</c:if>

		</div>
	</div>
</div>

