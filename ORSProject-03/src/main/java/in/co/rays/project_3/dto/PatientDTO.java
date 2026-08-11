package in.co.rays.project_3.dto;

import java.util.Date;

public class PatientDTO extends BaseDTO {

	private String patientName;
	private String disease;
	private String doctorName;
	private Date admissionDate;

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public Date getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(Date admissionDate) {
		this.admissionDate = admissionDate;
	}

	@Override
	public String getKey() {
		 
		return id + "";
	}

	@Override
	public String getValue() {
	    return patientName + " - " + disease;
	}
}
