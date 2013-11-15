package ws.remote;

public interface EMail {

	/**
	 * This method is used to send an E-Mail
	 * @param content
	 * @return
	 */
	public boolean sendEmail(Object content);
}
