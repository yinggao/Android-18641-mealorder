package DBLayout;

public class FavoriteListContainer {
	
	private String restId;
	private String rank;
	private String addDate;
	
	public FavoriteListContainer(String restId, String rank, String addDate) {
		this.restId = restId;
		this.rank = rank;
		this.addDate = addDate;
	}

	public String getRestId() {
		return restId;
	}

	public void setRestId(String restId) {
		this.restId = restId;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getAddDate() {
		return addDate;
	}

	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
}
