package DBLayout;

public class DishContainer {

	private String dishId;
	private String restId;
	private String name;
	private String description;
	private String audioPath;
	private String photoPath;
	
	public DishContainer(String dishId, String restId, String name,
			String description, String audioPath, String photoPath) {
		this.dishId = dishId;
		this.restId = restId;
		this.name = name;
		this.description = description;
		this.audioPath = audioPath;
		this.photoPath = photoPath;
	}

	public String getDishId() {
		return dishId;
	}

	public void setDishId(String dishId) {
		this.dishId = dishId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAudioPath() {
		return audioPath;
	}

	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
}
