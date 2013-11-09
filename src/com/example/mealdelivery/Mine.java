package com.example.mealdelivery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Mine extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine);

		Intent myIntent = getIntent();
	}

}
