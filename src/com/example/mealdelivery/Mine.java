package com.example.mealdelivery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class Mine extends Sidebar {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getIntent();

		LayoutInflater inflater = getLayoutInflater();

		inflater.inflate(R.layout.mine,
				(ViewGroup) findViewById(R.id.container));
	}

}
