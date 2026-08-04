package in.co.rays.project_3.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.co.rays.project_3.dto.CustomerDTO;
import in.co.rays.project_3.model.CustomerHibImpl;
import in.co.rays.project_3.model.CustomerModelInt;

public class CustomerModelTest {
	
	public static CustomerModelInt model = new CustomerHibImpl();
	
	public static void main(String[] args) throws Exception {
		addTest();
	}

	public static void addTest() throws Exception {
		
		CustomerDTO dto = new CustomerDTO();
		System.out.println("add METHOD STARTED");
		dto.setAccountNo("SBI123");
		dto.setName("AJAY SHARMA");
		dto.setBalance(2000.34);
		dto.setCreatedBy("admin");
		dto.setModifiedBy("admin");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		System.out.println("add");
		 long pk = model.add(dto); 
		System.out.println(pk + "data ADDED successfully  "); 
	}
}
