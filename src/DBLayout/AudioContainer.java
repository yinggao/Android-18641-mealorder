package DBLayout;

public class AudioContainer {

	private String id;
	private String contentPath;
	
	public AudioContainer(String id, String contentPath) {
		this.id = id;
		this.contentPath = contentPath;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPath() {
		return contentPath;
	}
	public void setPath(String content_Path) {
		this.contentPath = content_Path;
	}
}
