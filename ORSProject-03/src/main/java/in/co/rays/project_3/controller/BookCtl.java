package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.BookDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.BookModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "BookCtl", urlPatterns = {"/ctl/BookCtl"})
public class BookCtl extends BaseCtl {

		private static final long serialVersionUID = 1L;

		protected void preload(HttpServletRequest request) {

		}

		@Override
		protected boolean validate(HttpServletRequest request) {

		    boolean pass = true;

		    if (DataValidator.isNull(request.getParameter("title"))) {
		        request.setAttribute("title",
		                PropertyReader.getValue("error.require", "Book Title"));
		        pass = false;
		    }

		    if (DataValidator.isNull(request.getParameter("author"))) {
		        request.setAttribute("author",
		                PropertyReader.getValue("error.require", "Author"));
		        pass = false;
		    }

		    if (DataValidator.isNull(request.getParameter("price"))) {
		        request.setAttribute("price",
		                PropertyReader.getValue("error.require", "Price"));
		        pass = false;
		    } else if (!DataValidator.isDouble(request.getParameter("price"))) {
		        request.setAttribute("price",
		                PropertyReader.getValue("error.double", "Price"));
		        pass = false;
		    }

		    if (DataValidator.isNull(request.getParameter("publicationYear"))) {
		        request.setAttribute("publicationYear",
		                PropertyReader.getValue("error.require", "Publication Year"));
		        pass = false;
		    } else if (!DataValidator.isInteger(request.getParameter("publicationYear"))) {
		        request.setAttribute("publicationYear",
		                PropertyReader.getValue("error.integer", "Publication Year"));
		        pass = false;
		    }

		    return pass;
		}
		
		@Override
		protected BaseDTO populateDTO(HttpServletRequest request) {

			BookDTO dto = new BookDTO();

			dto.setId(DataUtility.getLong(request.getParameter("id")));
			dto.setTitle(DataUtility.getString(request.getParameter("title")));
			dto.setAuthor(DataUtility.getString(request.getParameter("author")));
			dto.setPrice(DataUtility.getDouble(request.getParameter("price")));
			dto.setPublicationYear(
			        DataUtility.getInt(request.getParameter("publicationYear")));
			populateBean(dto, request);

			return dto;
		}
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
		        throws ServletException, IOException {

		    long id = DataUtility.getLong(request.getParameter("id"));

		    BookModelInt model = ModelFactory.getInstance().getBookModel();

		    if (id > 0) {

		        try {
		        	BookDTO dto = model.findByPk(id);
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
			System.out.println("BookCtl doPost Called");

		    String op = DataUtility.getString(request.getParameter("operation"));

		    BookModelInt model = ModelFactory.getInstance().getBookModel();

		    long id = DataUtility.getLong(request.getParameter("id"));

		    BookDTO dto = (BookDTO) populateDTO(request);

		    if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

		        try {

		            if (id > 0) {
		                model.update(dto);
		                ServletUtility.setSuccessMessage("Book is successfully updated", request);
		            } else {
		                long pk = model.add(dto);
		                dto.setId(pk);
		                ServletUtility.setSuccessMessage("Book is successfully added", request);
		            }
		            System.out.println("BookCtl doPost Completed");
		            ServletUtility.setDto(dto, request);

		        } catch (DuplicateRecordException e) {

		            ServletUtility.setDto(dto, request);
		            ServletUtility.setErrorMessage("Book with title is already exists", request);

		        } catch (ApplicationException e) {
		            ServletUtility.handleException(e, request, response);
		            return;
		        }

		        ServletUtility.forward(getView(), request, response);

		    } else if (OP_CANCEL.equalsIgnoreCase(op)) {

		        ServletUtility.redirect(ORSView.BOOK_LIST_CTL, request, response);
		        return;

		    } else if (OP_RESET.equalsIgnoreCase(op)) {

		        ServletUtility.redirect(ORSView.BOOK_CTL, request, response);
		        return;
		    }

		    ServletUtility.forward(getView(), request, response);
		}
		@Override
		protected String getView() {
			return ORSView.BOOK_VIEW;
		}


}
