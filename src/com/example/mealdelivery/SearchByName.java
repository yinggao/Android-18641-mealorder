package com.example.mealdelivery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SearchByName extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

		Intent myIntent = getIntent();
//		TextView title = (TextView)findViewById(R.id.txtTitle);
//	    title.setText("Search");
	}
}
