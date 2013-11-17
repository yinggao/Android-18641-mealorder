package DBLayout;

public class FavoriteListContainer {
	
	private String restId;
	private String email;
	
	public FavoriteListContainer(String restId, String email) {
		this.restId = restId;
		this.email = email;
	}

	public String getRestId() {
		return restId;
	}

	public void setRestId(String restId) {
		this.restId = restId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
