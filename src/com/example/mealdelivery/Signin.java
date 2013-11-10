package com.example.mealdelivery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class Signin extends Sidebar {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getIntent();

		LayoutInflater inflater = getLayoutInflater();

		inflater.inflate(R.layout.signin,
				(ViewGroup) findViewById(R.id.container));
	}
}
