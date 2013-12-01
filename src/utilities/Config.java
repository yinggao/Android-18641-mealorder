package utilities;

import java.util.ArrayList;

import DBLayout.DragonBroDatabaseHandler;


public class Config {
	static ArrayList<String> options = null;
	

	public static ArrayList<String> createOptions(String currentUser) {
		options = new ArrayList<String>();
		options.add(currentUser);
		options.add("Search");
		options.add("Nearby");
		options.add("Shake");
		options.add("Mine");
		options.add("Signout");
		return options;

	}
}
