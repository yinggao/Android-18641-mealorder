package com.example.mealdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Signup extends Sidebar {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getIntent();

		LayoutInflater inflater = getLayoutInflater();

		inflater.inflate(R.layout.signup,
				(ViewGroup) findViewById(R.id.container));
	}
	
	public void signupMethod(View view) {
		Intent intent = new Intent(this, SearchByName.class);
		
		//TODO: signup a user, if valid, else provide him the error imfomation. put him/her into database
		//TODO: authenticate the new user, then login.
		
		startActivity(intent);
	}

}
