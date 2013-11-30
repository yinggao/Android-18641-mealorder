package com.example.mealdelivery;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Mine extends Sidebar {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getIntent();

		LayoutInflater inflater = getLayoutInflater();

		inflater.inflate(R.layout.mine,
				(ViewGroup) findViewById(R.id.container));

		
		TextView dish_pic1 = (TextView) findViewById(R.id.dish_pic1);
		
		dish_pic1.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	Intent intent = new Intent(Mine.this, RestaurantDetail.class);
				//TODO: Put restaurant information into Intent
				startActivity(intent);
		    }
		});
		
		RelativeLayout his_list1 = (RelativeLayout) findViewById(R.id.history1);
		his_list1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		    	Intent intent = new Intent(Mine.this, RestaurantDetail.class);
				//TODO: Put restaurant information into Intent
				startActivity(intent);
			}
		});
		
		RelativeLayout fav_list1 = (RelativeLayout) findViewById(R.id.favorite1);
		fav_list1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		    	Intent intent = new Intent(Mine.this, RestaurantDetail.class);
				//TODO: Put restaurant information into Intent
				startActivity(intent);
			}
		});


	}

}
