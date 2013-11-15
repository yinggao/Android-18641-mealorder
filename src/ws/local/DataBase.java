package ws.local;

public interface DataBase {

	/**
	 * This method is used to get the information of a student
	 * @param name
	 * @return
	 */
	public Object getStudentInfo(String studentName);
	
	/**
	 * This method is used to get the information of a restaurant
	 * @param restaurantName
	 * @return
	 */
	public Object getRestaurantInfo(String restaurantName);
	
	/**
	 * This method is used to get the information of an audio record
	 * @param audioID
	 * @return
	 */
	public Object getAudioInfo(String audioID);
	
	/**
	 * This method is used to get the information of dish provided by
	 * a restaurant
	 * @param restaurantName
	 * @param dishID
	 * @return
	 */
	public Object getDishInfo(String restaurantName, String dishID);
}
