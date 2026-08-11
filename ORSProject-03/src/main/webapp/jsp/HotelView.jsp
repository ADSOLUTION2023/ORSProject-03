<%@page import="in.co.rays.project_3.controller.HotelCtl"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.controller.UserCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hotel view</title>
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
		<form action="<%=ORSView.HOTEL_CTL%>" method="post">
			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.HotelDTO"
				scope="request"></jsp:useBean>
			<div class="row pt-3">
				<!-- Grid column -->
				<div class="col-md-4 mb-4"></div>
				<div class="col-md-4 mb-4">
					<div class="card input-group-addon">
						<div class="card-body">
							<!-- ye code box create karta h -->
							<%
								long id = DataUtility.getLong(request.getParameter("id"));

								if (dto.getHotelName() != null && dto.getId() > 0) {
							%>
							<h3 class="text-center default-text text-primary">UPDATE
								HOTEL</h3>
							<%
								} else {
							%>
							<h3 class="text-center default-text text-primary">ADD HOTEL</h3>
							<%
								}
							%>
							<!--Body-->
							<div>
								<%
									List list = (List) request.getAttribute("roleList");
								%>

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

								<span class="pl-sm-5"><b>Hotel Name</b> <span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-building grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="hotelName"
											placeholder="Enter Hotel Name"
											value="<%=DataUtility.getStringData(dto.getHotelName())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("hotelName", request)%></font></br>
								<span class="pl-sm-5"><b>Location</b> <span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-map-marker grey-text"
													style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="location"
											placeholder="Enter Location"
											value="<%=DataUtility.getStringData(dto.getLocation())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("location", request)%></font></br>

								<span class="pl-sm-5"><b>Rating</b> <span
									style="color: red;">*</span></span> </br>

								<div class="col-sm-12">
									<div class="input-group">

										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-star grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>

										<%
											HashMap<String, String> map = new HashMap<String, String>();

											map.put("1", "Poor");
											map.put("2", "Below Average");
											map.put("3", "Average");
											map.put("4", "Good");
											map.put("5", "Excellent");

											String htmlList = HTMLUtility.getList("rating", String.valueOf((int) dto.getRating()), map);
										%>

										<%=htmlList%>

									</div>
								</div>

								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("rating", request)%>
								</font></br> <span class="pl-sm-5"><b>Contact No.</b> <span
									style="color: red;">*</span></span> </br>

								<div class="col-sm-12">
									<div class="input-group">

										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-phone grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>

										<input type="text" class="form-control" name="contactNo"
											placeholder="Enter Contact Number"
											value="<%=DataUtility.getStringData(dto.getContactNo())%>">

									</div>
								</div>

								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("contactNo", request)%>
								</font></br>
								<%
							if (dto.getHotelName()!=null && dto.getId() > 0) {
							%>

							<div class="text-center">

								<input type="submit" name="operation" class="btn btn-success btn-md" 
								       style="font-size: 17px" 
								       value="<%=HotelCtl.OP_UPDATE%>"> 
								<input type="submit" name="operation" class="btn btn-warning btn-md"
									   style="font-size: 17px" 
									   value="<%=HotelCtl.OP_CANCEL%>">
							</div>
							<%
								} else {
							%>
							<div class="text-center">

								<input type="submit" name="operation"
									   class="btn btn-success btn-md" style="font-size: 17px"
									   value="<%=HotelCtl.OP_SAVE%>"> 
								<input type="submit" name="operation" 
								       class="btn btn-warning btn-md"
									   style="font-size: 17px" value="<%=HotelCtl.OP_RESET%>">
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