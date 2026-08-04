<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.controller.CustomerCtl"%>
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
<title>Customer View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style type="text/css">

i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	 padding-bottom: 11px; 
	 background-color: #ebebe0;
}
.input-group-addon{
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
	</div>
	<div >

		<main>
		<form action="<%=ORSView.CUSTOMER_CTL%>" method="post">
			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.CustomerDTO" scope="request"></jsp:useBean>
			<div class="row pt-3">
				<!-- Grid column -->
				<div class="col-md-4 mb-4"></div>
				<div class="col-md-4 mb-4">
					<div class="card input-group-addon">
						<div class="card-body">
							<%
							  long id=DataUtility.getLong(request.getParameter("id"));
							
								if (dto.getName()!=null && dto.getId() > 0) {
							%>
							<h3 class="text-center default-text text-primary">UPDATE CUSTOMER</h3>
							<%
								} else {
							%>
							<h3 class="text-center default-text text-primary">ADD CUSTOMER</h3>
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
								<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>"> 
								<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>"> 
								<input type="hidden" name="createdDatetime"	value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
								<input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">
							</div>
							
							<div class="md-form">
								
		<span class="pl-sm-5"><b>Account Number:</b>
		<span style="color: red;">*</span></span> </br>
		<div class="col-sm-12">
          <div class="input-group">
            <div class="input-group-prepend">
               <div class="input-group-text"><i class="fa fa-user grey-text" style="font-size: 1rem;"></i> </div>
            </div>
              <input type="text" class="form-control" name="accountNo" placeholder="Enter Account Number" value="<%=DataUtility.getStringData(dto.getAccountNo())%>">
          </div>
        </div>
	<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("accountNo", request)%></font></br>
				
	<span class="pl-sm-5"><b>Name:</b>
		<span style="color: red;">*</span></span> </br>
		<div class="col-sm-12">
          <div class="input-group">
            <div class="input-group-prepend">
               <div class="input-group-text"><i class="fa fa-user grey-text" style="font-size: 1rem;"></i> </div>
            </div>
              <input type="text" class="form-control" name="name" placeholder="Enter Name" value="<%=DataUtility.getStringData(dto.getName())%>">
          </div>
        </div>
	<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("name", request)%></font></br>
				
	<span class="pl-sm-5"><b>Balance:</b>
		<span style="color: red;">*</span></span> </br>
		<div class="col-sm-12">
          <div class="input-group">
            <div class="input-group-prepend">
               <div class="input-group-text"><i class="fa fa-user grey-text" style="font-size: 1rem;"></i> </div>
            </div>
              <input type="text" class="form-control" name="balance" placeholder="Enter Balance" value="<%=DataUtility.getStringData(dto.getBalance())%>">
          </div>
        </div>
	<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("balance", request)%></font></br>			
</main>
          	<div class="col-md-4 mb-4"></div>
          	
          	<%
							if (dto.getName()!=null && dto.getId() > 0) {
							%>

							<div class="text-center">

								<input type="submit" name="operation" class="btn btn-success btn-md" 
								       style="font-size: 17px" 
								       value="<%=CustomerCtl.OP_UPDATE%>"> 
								<input type="submit" name="operation" class="btn btn-warning btn-md"
									   style="font-size: 17px" 
									   value="<%=CustomerCtl.OP_CANCEL%>">
							</div>
							<%
								} else {
							%>
							<div class="text-center">

								<input type="submit" name="operation"
									   class="btn btn-success btn-md" style="font-size: 17px"
									   value="<%=CustomerCtl.OP_SAVE%>"> 
								<input type="submit" name="operation" 
								       class="btn btn-warning btn-md"
									   style="font-size: 17px" value="<%=CustomerCtl.OP_RESET%>">
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