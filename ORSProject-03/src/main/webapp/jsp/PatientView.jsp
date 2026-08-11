<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.controller.PatientCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Patient View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style type="text/css">
i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

.input-group-addon {
	box-shadow: 9px 8px 7px #001a33;
}

.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/wall.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 75px;

	/* background-size: 100%; */
}
</style>

</head>

<body class="hm">
	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>
	</div>
	<div>

		<main>
		<form action="<%=ORSView.PATIENT_CTL%>" method="post">
			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.PatientDTO"
				scope="request"></jsp:useBean>
			<div class="row pt-3">
				<!-- Grid column -->
				<div class="col-md-4 mb-4"></div>
				<div class="col-md-4 mb-4">
					<div class="card input-group-addon">
						<div class="card-body">
							<%
								long id = DataUtility.getLong(request.getParameter("id"));

								if (dto.getPatientName() != null && dto.getId() > 0) {
							%>
							<h3 class="text-center default-text text-primary">UPDATE
								PATIENT</h3>
							<%
								} else {
							%>
							<h3 class="text-center default-text text-primary">ADD
								PATIENT</h3>
							<%
								}
							%>

							<div>

								<H4 align="center">
									<%
										if (!ServletUtility.getSuccessMessage(request).equals("")) {
									%>
									<div class="alert alert-success alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=ServletUtility.getSuccessMessage(request)%>
									</div>
									<%
										}
									%>
								</H4>

								<H4 align="center">
									<%
										if (!ServletUtility.getErrorMessage(request).equals("")) {
									%>
									<div class="alert alert-danger alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=ServletUtility.getErrorMessage(request)%>
									</div>
									<%
										}
									%>

								</H4>

								<input type="hidden" name="id" value="<%=dto.getId()%>">
								<input type="hidden" name="createdBy"
									value="<%=dto.getCreatedBy()%>"> <input type="hidden"
									name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
									type="hidden" name="createdDatetime"
									value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
								<input type="hidden" name="modifiedDatetime"
									value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">
							</div>

							<div class="md-form">

								<span class="pl-sm-5"><b>Patient Name:</b> <span
									style="color: red;">*</span></span> </br>

								<div class="col-sm-12">
									<div class="input-group">

										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-user grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>

										<input type="text" class="form-control" name="patientName"
											placeholder="Enter Patient Name"
											value="<%=DataUtility.getStringData(dto.getPatientName())%>">

									</div>
								</div>

								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("patientName", request)%>
								</font></br> <span class="pl-sm-5"><b>Disease:</b> <span
									style="color: red;">*</span></span> </br>

								<div class="col-sm-12">
									<div class="input-group">

										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-medkit grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>

										<input type="text" class="form-control" name="disease"
											placeholder="Enter Disease"
											value="<%=DataUtility.getStringData(dto.getDisease())%>">

									</div>
								</div>

								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("disease", request)%>
								</font></br> <span class="pl-sm-5"><b>Doctor Name:</b> <span
									style="color: red;">*</span></span> </br>

								<div class="col-sm-12">
									<div class="input-group">

										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-user-md grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>

										<input type="text" class="form-control" name="doctorName"
											placeholder="Enter Doctor Name"
											value="<%=DataUtility.getStringData(dto.getDoctorName())%>">

									</div>
								</div>

								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("doctorName", request)%>
								</font></br> <span class="pl-sm-5"> <b>Date Of Admission</b> <span
									style="color: red;">*</span>
								</span></br>

								<div class="col-sm-12">
									<div class="input-group">

										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-calendar grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>

										<input type="text" id="datepicker2" name="admissionDate"
											class="form-control" placeholder="Date Of Admission"
											readonly="readonly"
											value="<%=DataUtility.getDateString(dto.getAdmissionDate())%>">

									</div>
								</div>

								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("admissionDate", request)%>
								</font></br>
								<div class="col-md-4 mb-4"></div>

								<%
									if (dto.getPatientName() != null && dto.getId() > 0) {
								%>

								<div class="text-center">

									<input type="submit" name="operation"
										class="btn btn-success btn-md" style="font-size: 17px"
										value="<%=PatientCtl.OP_UPDATE%>"> <input
										type="submit" name="operation" class="btn btn-warning btn-md"
										style="font-size: 17px" value="<%=PatientCtl.OP_CANCEL%>">
								</div>
								<%
									} else {
								%>
								<div class="text-center">

									<input type="submit" name="operation"
										class="btn btn-success btn-md" style="font-size: 17px"
										value="<%=PatientCtl.OP_SAVE%>"> <input type="submit"
										name="operation" class="btn btn-warning btn-md"
										style="font-size: 17px" value="<%=PatientCtl.OP_RESET%>">
								</div>
								<%
									}
								%>

							</div>

						</div>
					</div>
		</form>
		</main>
		<div class="col-md-4 mb-4"></div>

	</div>

</body>
<%@include file="FooterView.jsp"%>

</body>
</html>