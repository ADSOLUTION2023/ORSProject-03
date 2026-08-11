package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.PatientDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.PatientModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "PatientListCtl", urlPatterns = { "/ctl/PatientListCtl" })
public class PatientListCtl extends BaseCtl {

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

			int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
			int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

			pageNo = (pageNo == 0) ? 1 : pageNo;
			pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

			PatientDTO dto = new PatientDTO();

			PatientModelInt model = ModelFactory.getInstance().getPatientModel();

			try {

				List list = model.search(dto, pageNo, pageSize);

				List next = model.search(dto, pageNo + 1, pageSize);

				request.setAttribute("list", list);

				if (next == null || next.size() == 0) {
					request.setAttribute("nextListSize", 0);
				} else {
					request.setAttribute("nextListSize", next.size());
				}

				request.setAttribute("pageNo", pageNo);
				request.setAttribute("pageSize", pageSize);

				ServletUtility.forward(getView(), request, response);

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
			}
		}

		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			String op = DataUtility.getString(request.getParameter("operation"));

			int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
			int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
			String[] ids = request.getParameterValues("ids");
			pageNo = (pageNo == 0) ? 1 : pageNo;
			pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

			PatientDTO dto = (PatientDTO) populateDTO(request);

			PatientModelInt model = ModelFactory.getInstance().getPatientModel();

			try {

				if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

					if (OP_SEARCH.equalsIgnoreCase(op)) {
						pageNo = 1;
					} else if (OP_NEXT.equalsIgnoreCase(op)) {
						pageNo++;
					} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
						pageNo--;
					}

				} else if (OP_NEW.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.PATIENT_CTL, request, response);
					return;
				} else if (OP_RESET.equalsIgnoreCase(op)) {

					ServletUtility.redirect(ORSView.PATIENT_LIST_CTL, request, response);
					return;
				} else if (OP_DELETE.equalsIgnoreCase(op)) {
					pageNo = 1;
					if (ids != null && ids.length > 0) {
						PatientDTO deletedto = new PatientDTO();
						for (String id : ids) {
							deletedto.setId(DataUtility.getLong(id));
							model.delete(deletedto);
							ServletUtility.setSuccessMessage("Data Successfully Deleted!", request);
						}
					} else {
						ServletUtility.setErrorMessage("Select atleast one record", request);
					}
				}
				if (OP_BACK.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.PATIENT_LIST_CTL, request, response);
					return;
				}
				dto = (PatientDTO) populateDTO(request);
				List list = model.search(dto, pageNo, pageSize);
				List next = model.search(dto, pageNo + 1, pageSize);

				request.setAttribute("list", list);

				if (next == null || next.size() == 0) {
					request.setAttribute("nextListSize", 0);
				} else {
					request.setAttribute("nextListSize", next.size());
				}

				request.setAttribute("pageNo", pageNo);
				request.setAttribute("pageSize", pageSize);

				ServletUtility.forward(getView(), request, response);

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
			}
		}

		@Override
		protected String getView() {
			return ORSView.PATIENT_LIST_VIEW;
		}

}
