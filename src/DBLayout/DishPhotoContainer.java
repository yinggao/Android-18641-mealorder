package DBLayout;

public class DishPhotoContainer {

	private String id;
	private String contentPath;
	
	public DishPhotoContainer(String id, String contentPath) {
		this.id = id;
		this.contentPath = contentPath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContentPath() {
		return contentPath;
	}

	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}
}
