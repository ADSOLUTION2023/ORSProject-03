package in.co.rays.project_3.dto;

public class BookDTO extends BaseDTO {

	private String title;
	private String author;
	private Double price;
	private Integer publicationYear;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(Integer publicationYear) {
		this.publicationYear = publicationYear;
	}

	@Override
	public String getKey() {

		return "id" + "";
	}

	@Override
	public String getValue() {

		return "title";
	}

}
