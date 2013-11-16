package entities;

import java.util.ArrayList;

public interface FavoriteList {
	
	/**
	 * Add a restaurant to one's favorite list
	 * @param restName
	 * @return
	 */
	public boolean addToList(String restName);
	
    /**
	 * Get all restaurants in favorite list
	 * @param restName
	 * @return
	 */
	public ArrayList<String> getList();
	
}
