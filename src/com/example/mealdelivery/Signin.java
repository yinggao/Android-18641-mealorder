package com.example.mealdelivery;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Signin extends Sidebar {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getIntent();

		LayoutInflater inflater = getLayoutInflater();

		inflater.inflate(R.layout.signin,
				(ViewGroup) findViewById(R.id.container));
	}
	
	public void signinMethod(View view) {
		//Build a new intent
		Intent intent = new Intent(this, SearchByName.class);
		
		//TODO: authenticate user
		startActivity(intent);
	}
	
	public void signupMethod(View view) {
		//Build a new intent
		Intent intent = new Intent(this, Signup.class);
		startActivity(intent);
	}
}
