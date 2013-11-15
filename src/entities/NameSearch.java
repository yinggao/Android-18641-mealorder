package entities;

import java.util.ArrayList;

public interface NameSearch {

	/**
	 * Search database to find whether this restaurant exist.
	 * If yes, return all information of the restaurant.
	 * @param restName
	 * @return
	 */
	public Object findRestaurant(String restName);
	
	/**
	 * Search database to find restaurant that has similar name as
	 * what user typed.
	 * @param restName
	 * @return
	 */
	public ArrayList<Object> findSimilarRestaurant(String restName);
	
	/**
	 * Check whether the restaurant exits in database
	 * @param restName
	 * @return
	 */
	public boolean existRestaurant(String restName);
	
	/**
	 * Generate similar name as what user inputs, and return
	 * @return
	 */
	public ArrayList<String> getSimilarName(String restName);
}
