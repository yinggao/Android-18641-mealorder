package entities;

public interface StudentAccessManagement {

	/**
	 * Decide whether an account exist and the password is correct
	 * @param userName
	 * @return
	 */
	public boolean validateAccount(String userName);
	
	/**
	 * Create a new account based on user input
	 * @param userName
	 * @param password
	 * @param lastName
	 * @param firstName
	 * @param address
	 * @param phoneNumber
	 * @return
	 */
	public boolean createStudent(String userName, String password, String lastName,
			String firstName, String address, String phoneNumber);
}
