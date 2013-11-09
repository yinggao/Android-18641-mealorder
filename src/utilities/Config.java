package utilities;

import java.util.ArrayList;


public class Config {
	static ArrayList<String> options = null;

	public static ArrayList<String> createOptions() {
		options = new ArrayList<String>();
		options.add("Signin");
		options.add("Signup");
		options.add("Search");
		options.add("Test4");
		options.add("Test5");
		options.add("Test6");
		
		return options;

	}
}
