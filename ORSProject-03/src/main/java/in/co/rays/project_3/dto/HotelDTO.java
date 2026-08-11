package in.co.rays.project_3.dto;

public class HotelDTO extends BaseDTO {
	
	private String hotelName;
	private String location;
	private double rating;
	private String contactNo;

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	@Override
	public String getKey() {
		 
		return id + "";
	}

	@Override
	public String getValue() {

		return hotelName + location;
	}

}
