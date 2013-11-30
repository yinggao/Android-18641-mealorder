package utilities;

import java.util.ArrayList;


public class Config {
	static ArrayList<String> options = null;

	public static ArrayList<String> createOptions() {
		options = new ArrayList<String>();
		options.add("user");
		options.add("Search");
		options.add("Nearby");
		options.add("Shake");
		options.add("Mine");
		options.add("Signout");
		return options;

	}
}
