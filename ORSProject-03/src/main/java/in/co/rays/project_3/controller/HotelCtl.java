package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.HotelDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.RoleModelInt;
import in.co.rays.project_3.model.HotelModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;
	
@WebServlet(urlPatterns = { "/ctl/HotelCtl" })
public class HotelCtl extends BaseCtl {
	
		private static final long serialVersionUID = 1L;
		private static Logger log = Logger.getLogger(HotelCtl.class);

		protected void preload(HttpServletRequest request) {
	
			
		}

		protected boolean validate(HttpServletRequest request) {
			
			boolean pass = true;
			
			  // Hotel Name Validation
		    if (DataValidator.isNull(request.getParameter("hotelName"))) {
		        request.setAttribute("hotelName",
		                PropertyReader.getValue("error.require", "Hotel Name"));
		        pass = false;

		    } else if (!DataValidator.isName(request.getParameter("hotelName"))) {
		        request.setAttribute("hotelName",
		                "Please enter correct Hotel Name");
		        pass = false;
		    }

		    // Location Validation
		    if (DataValidator.isNull(request.getParameter("location"))) {
		        request.setAttribute("location",
		                PropertyReader.getValue("error.require", "Location"));
		        pass = false;

		    } else if (!DataValidator.isName(request.getParameter("location"))) {
		        request.setAttribute("location",
		                "Please enter correct Location");
		        pass = false;
		    }

		    // Rating Validation
		    if (DataValidator.isNull(request.getParameter("rating"))) {
		        request.setAttribute("rating",
		                PropertyReader.getValue("error.require", "Rating"));
		        pass = false;

		    } else {
		        try {
		            double rating = DataUtility.getDouble(request.getParameter("rating"));

		            if (rating < 1 || rating > 5) {
		                request.setAttribute("rating",
		                        "Rating should be between 1 and 5");
		                pass = false;
		            }

		        } catch (Exception e) {
		            request.setAttribute("rating",
		                    "Please enter valid Rating");
		            pass = false;
		        }
		    }

		    // Contact Number Validation
		    if (DataValidator.isNull(request.getParameter("contactNo"))) {
		        request.setAttribute("contactNo",
		                PropertyReader.getValue("error.require", "Contact No"));
		        pass = false;

		    } else if (!DataValidator.isPhoneNo(request.getParameter("contactNo"))) {
		        request.setAttribute("contactNo",
		                "Please Enter Valid Contact Number");
		        pass = false;
		    }

			return pass;

		}

		protected BaseDTO populateDTO(HttpServletRequest request) {
			
			HotelDTO dto = new HotelDTO();

			dto.setId(DataUtility.getLong(request.getParameter("id")));

			dto.setHotelName(DataUtility.getString(request.getParameter("hotelName")));

			dto.setLocation(DataUtility.getString(request.getParameter("location")));

			dto.setRating(DataUtility.getDouble(request.getParameter("rating")));

			dto.setContactNo(DataUtility.getString(request.getParameter("contactNo")));

			populateBean(dto, request);

			log.debug("HotelRegistrationCtl Method populatedto Ended");

			return dto;
		}

		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {
			log.debug("HotelCtl Method doGet Started");
			String op = DataUtility.getString(request.getParameter("operation"));
			// get model
			HotelModelInt model = ModelFactory.getInstance().getHotelModel();
			long id = DataUtility.getLong(request.getParameter("id"));
			if (id > 0 || op != null) {
				
				HotelDTO dto = null;
				try {
					dto = model.findByPK(id);
					ServletUtility.setDto(dto, request);
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e);
					ServletUtility.handleDBDown(getView(), request, response);
					return;
				}
			}
			ServletUtility.forward(getView(), request, response);
		}

		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {
			String op = DataUtility.getString(request.getParameter("operation"));
			// get model
			HotelModelInt model = ModelFactory.getInstance().getHotelModel();
			long id = DataUtility.getLong(request.getParameter("id"));
			if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
				HotelDTO dto = (HotelDTO) populateDTO(request);
				try {
					if (id > 0) {
						model.update(dto);
						ServletUtility.setSuccessMessage("Data Updated successfully", request);
						ServletUtility.setDto(dto, request);
					} else {

						try {
							model.add(dto);
							ServletUtility.setSuccessMessage("Data saved successfully ", request);
						} catch (ApplicationException e) {
							log.error(e);
							ServletUtility.handleDBDown(getView(), request, response);
							return;
						} catch (DuplicateRecordException e) {
							ServletUtility.setDto(dto, request);
							ServletUtility.setErrorMessage("Login id already exists", request);
						}

					}

				} catch (ApplicationException e) {
					log.error(e);
					ServletUtility.handleDBDown(getView(), request, response);
					return;
				} catch (DuplicateRecordException e) {
					ServletUtility.setDto(dto, request);
					ServletUtility.setErrorMessage("Login id already exists", request);
				}
			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				HotelDTO dto = (HotelDTO) populateDTO(request);
				try {
					model.delete(dto);
					ServletUtility.redirect(ORSView.HOTEL_LIST_CTL, request, response);
					return;
				} catch (ApplicationException e) {
					log.error(e);
					ServletUtility.handleDBDown(getView(), request, response);
					return;
				}

			} else if (OP_CANCEL.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.HOTEL_LIST_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.HOTEL_CTL, request, response);
				return;
			}
			ServletUtility.forward(getView(), request, response);

			log.debug("HotelCtl Method doPostEnded");
		}

	@Override
	protected String getView() {
		return ORSView.HOTEL_VIEW;
	}

}
