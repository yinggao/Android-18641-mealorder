package ws.local;

public interface Microphone {
	
	/**
	 * This method will start recording microphone information
	 * @param storeLocation
	 */
	public void startRecord(String storeLocation);
	
	/**
	 * This method will stop recording microphone information
	 */
	public void stopRecord();
}
