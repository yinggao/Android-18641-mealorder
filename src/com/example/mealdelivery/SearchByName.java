package com.example.mealdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class SearchByName extends Sidebar {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getIntent();

		LayoutInflater inflater = getLayoutInflater();

		inflater.inflate(R.layout.search,(ViewGroup) findViewById(R.id.container));
		
		TextView show_in_map = (TextView) findViewById(R.id.show_in_map);
		show_in_map.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	Intent intent = new Intent(SearchByName.this, Nearby.class);
				//TODO: put search result into inteng
				startActivity(intent);
		    }
		});
		
		TextView restaurant_pic1 = (TextView) findViewById(R.id.restaurant_pic1);
		
		restaurant_pic1.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	Intent intent = new Intent(SearchByName.this, RestaurantDetail.class);
				//TODO: Put restaurant information into Intent
				startActivity(intent);
		    }
		});
	}
	
}
