package entities;

public class HistoryListContainer {
	
	private String email;
	private String visitDate;
	private String restId;
	
	public HistoryListContainer(String email, String visitDate, 
		String restId) {
		this.email = email;
		this.visitDate = visitDate;
		this.restId = restId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}

	public String getRestId() {
		return restId;
	}

	public void setRestId(String restId) {
		this.restId = restId;
	}
	

}
