package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.CustomerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.CustomerModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.RoleModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "CustomerCtl", urlPatterns = { "/ctl/CustomerCtl" })
public class CustomerCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	protected void preload(HttpServletRequest request) {

	}

	@Override
	protected boolean validate(HttpServletRequest request) {

	    boolean pass = true;

	    if (DataValidator.isNull(request.getParameter("accountNo"))) {
	        request.setAttribute("accountNo",
	                PropertyReader.getValue("error.require", "Account Number"));
	        pass = false;
	    }

	    if (DataValidator.isNull(request.getParameter("name"))) {
	        request.setAttribute("name",
	                PropertyReader.getValue("error.require", "Customer Name"));
	        pass = false;
	    }

	    if (DataValidator.isNull(request.getParameter("balance"))) {
	        request.setAttribute("balance",
	                PropertyReader.getValue("error.require", "Balance"));
	        pass = false;
	    } else if (!DataValidator.isDouble(request.getParameter("balance"))) {
	        request.setAttribute("balance",
	                PropertyReader.getValue("error.double", "Balance"));
	        pass = false;
	    }

	    return pass;
	}
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		CustomerDTO dto = new CustomerDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setAccountNo(DataUtility.getString(request.getParameter("accountNo")));
		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setBalance(DataUtility.getDouble(request.getParameter("balance")));

		populateBean(dto, request);

		return dto;
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    long id = DataUtility.getLong(request.getParameter("id"));

	    CustomerModelInt model = ModelFactory.getInstance().getCustomerModel();

	    if (id > 0) {

	        try {
	        	CustomerDTO dto = model.findByPk(id);
	            ServletUtility.setDto(dto, request);

	        } catch (ApplicationException e) {
	            ServletUtility.handleException(e, request, response);
	            return;
	        }
	    }

	    ServletUtility.forward(getView(), request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		System.out.println("CustomerCtl doPost Called");

	    String op = DataUtility.getString(request.getParameter("operation"));

	    CustomerModelInt model = ModelFactory.getInstance().getCustomerModel();

	    long id = DataUtility.getLong(request.getParameter("id"));

	    CustomerDTO dto = (CustomerDTO) populateDTO(request);

	    if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

	        try {

	            if (id > 0) {
	                model.update(dto);
	                ServletUtility.setSuccessMessage("Customer is successfully updated", request);
	                ServletUtility.setDto(dto, request);
	            } else {
	                long pk = model.add(dto);
	                dto.setId(pk);
	                ServletUtility.setSuccessMessage("Customer is successfully added", request);
	                ServletUtility.setDto(dto, request);
	            }

	            ServletUtility.setDto(dto, request);

	        } catch (DuplicateRecordException e) {

	            ServletUtility.setDto(dto, request);
	            ServletUtility.setErrorMessage("Account Number already exists", request);

	        } catch (ApplicationException e) {
	            ServletUtility.handleException(e, request, response);
	            return;
	        }

	        ServletUtility.forward(getView(), request, response);

	    } else if (OP_CANCEL.equalsIgnoreCase(op)) {

	        ServletUtility.redirect(ORSView.CUSTOMER_LIST_CTL, request, response);
	        return;

	    } else if (OP_RESET.equalsIgnoreCase(op)) {

	        ServletUtility.redirect(ORSView.CUSTOMER_CTL, request, response);
	        return;
	    }

	    ServletUtility.forward(getView(), request, response);
	}
	@Override
	protected String getView() {
		return ORSView.CUSTOMER_VIEW;
	}

}
