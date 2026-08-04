package in.co.rays.project_3.dto;

public class CustomerDTO extends BaseDTO{
	
	private long Id;
	private String accountNo;
	private String name;
	private double balance;
	
	
	public Long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String getKey() {
		
		return "id" + "";
	}

	@Override
	public String getValue() {

		return "name" ;
	}

}
