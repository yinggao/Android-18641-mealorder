package DBLayout;

public class RestaurantContainer {
	
	private String restId;
	private String name;
	private String address;
	private String phone;
	private String businessHour;
	private String location;
	private String category;
	
	public RestaurantContainer(String restId, String name, String address,
			String phone, String businessHour, String location,
			String category) {
		this.restId = restId;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.businessHour = businessHour;
		this.location = location;
		this.category = category;
	}

	public String getRestId() {
		return restId;
	}

	public void setRestId(String restId) {
		this.restId = restId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBusinessHour() {
		return businessHour;
	}

	public void setBusinessHour(String businessHour) {
		this.businessHour = businessHour;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
