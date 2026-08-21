package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.PatientDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.PatientModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "PatientCtl", urlPatterns = { "/ctl/PatientCtl" })
public class PatientCtl extends BaseCtl {
	
			private static final long serialVersionUID = 1L;

		protected void preload(HttpServletRequest request) {

		}

		@Override
		protected boolean validate(HttpServletRequest request) {

		    boolean pass = true;

		    if (DataValidator.isNull(request.getParameter("patientName"))) {
		        request.setAttribute("patientName",
		                PropertyReader.getValue("error.require", "Patient Name"));
		        pass = false;
		    } else if (!DataValidator.isName(request.getParameter("patientName"))) {
		        request.setAttribute("patientName",
		                "Please enter correct Patient Name");
		        pass = false;
		    }

		    if (DataValidator.isNull(request.getParameter("disease"))) {
		        request.setAttribute("disease",
		                PropertyReader.getValue("error.require", "Disease"));
		        pass = false;
		    }

		    if (DataValidator.isNull(request.getParameter("doctorName"))) {
		        request.setAttribute("doctorName",
		                PropertyReader.getValue("error.require", "Doctor Name"));
		        pass = false;
		    } else if (!DataValidator.isName(request.getParameter("doctorName"))) {
		        request.setAttribute("doctorName",
		                "Please enter correct Doctor Name");
		        pass = false;
		    }

		    if (DataValidator.isNull(request.getParameter("admissionDate"))) {
		        request.setAttribute("admissionDate",
		                PropertyReader.getValue("error.require", "Admission Date"));
		        pass = false;
		    } else if (!DataValidator.isDate(request.getParameter("admissionDate"))) {
		        request.setAttribute("admissionDate",
		                PropertyReader.getValue("error.date", "Admission Date"));
		        pass = false;
		    }

		    return pass;
		}
		
		@Override
		protected BaseDTO populateDTO(HttpServletRequest request) {

			PatientDTO dto = new PatientDTO();

			dto.setId(DataUtility.getLong(request.getParameter("id")));

			dto.setPatientName(
			        DataUtility.getString(request.getParameter("patientName")));

			dto.setDisease(
			        DataUtility.getString(request.getParameter("disease")));

			dto.setDoctorName(
			        DataUtility.getString(request.getParameter("doctorName")));

			dto.setAdmissionDate(
			        DataUtility.getDate(request.getParameter("admissionDate")));
			populateBean(dto, request);

			return dto;
		}
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
		        throws ServletException, IOException {

		    long id = DataUtility.getLong(request.getParameter("id"));

		    PatientModelInt model = ModelFactory.getInstance().getPatientModel();

		    if (id > 0) {

		        try {
		        	PatientDTO dto = model.findByPk(id);
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
			System.out.println("PatientCtl doPost Called");

		    String op = DataUtility.getString(request.getParameter("operation"));

		    PatientModelInt model = ModelFactory.getInstance().getPatientModel();

		    long id = DataUtility.getLong(request.getParameter("id"));

		    PatientDTO dto = (PatientDTO) populateDTO(request);

		    if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

		        try {

		            if (id > 0) {
		                model.update(dto);
		                ServletUtility.setSuccessMessage("Patient is successfully updated", request);
		                ServletUtility.setDto(dto, request);
		            } else {
		                long pk = model.add(dto);
		                dto.setId(pk);
		                ServletUtility.setSuccessMessage("Patient is successfully added", request);
		                ServletUtility.setDto(dto, request);
		                ServletUtility.forward(getView(), request, response);
		            }

		            ServletUtility.setDto(dto, request);

		        } catch (DuplicateRecordException e) {

		            ServletUtility.setDto(dto, request);
		            ServletUtility.setErrorMessage("Patient already exists", request);

		        } catch (ApplicationException e) {
		            ServletUtility.handleException(e, request, response);
		            return;
		        }

		        ServletUtility.forward(getView(), request, response);

		    } else if (OP_CANCEL.equalsIgnoreCase(op)) {

		        ServletUtility.redirect(ORSView.PATIENT_LIST_CTL, request, response);
		        return;

		    } else if (OP_RESET.equalsIgnoreCase(op)) {

		        ServletUtility.redirect(ORSView.PATIENT_CTL, request, response);
		        return;
		    }

		    ServletUtility.forward(getView(), request, response);
		}
	


	@Override
	protected String getView() {
		 
		return ORSView.PATIENT_VIEW;
	}

}
