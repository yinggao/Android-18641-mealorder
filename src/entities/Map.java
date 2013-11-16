package entities;

public interface Map {
	
	/**
	 * Use GPS signal to find user's current location
	 * @return
	 */
	public String getCurrentLocationGPS();
	
	/**
	 * Use Wifi signal to find user's current location
	 * @return
	 */
	public String getCurrentLocationWifi();
	
	/**
	 * Show coordinate as well as related information on MAP 
	 * @param coordinate
	 * @param info 
	 * @return
	 */
	public boolean showLocationOnMap(String coordinate, Object info);
}
