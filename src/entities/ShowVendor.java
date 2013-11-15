package entities;

import java.util.ArrayList;

public interface ShowVendor {

	/**
	 * return restaurant's name 
	 * @return
	 */
	public String getName();
	
	/**
	 * return restaurant's address
	 * @return
	 */
	public String getAddress();

	/**
	 * return restaurant's dishes
	 * @return
	 */
	public ArrayList<String> getDishes();
	
	/**
	 * return restaurant's location
	 * @return
	 */
	public String getCoordinate();
	
	/**
	 * return audio description of one dish
	 * @param dishName
	 * @return
	 */
	public Object getAudoiDescription(String dishName);
}
