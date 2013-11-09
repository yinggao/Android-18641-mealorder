package com.example.mealdelivery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class RestaurantDetail extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant);

		Intent myIntent = getIntent();
	}
}
