package com.example.mealdelivery;


import java.io.File;
import java.util.ArrayList;

import entities.HistoryListContainer;
import entities.ImageLoader;
import entities.RestaurantContainer;

import DBLayout.DragonBroDatabaseHandler;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Mine extends Sidebar {
	DragonBroDatabaseHandler dbdb = null;
	private ImageLoader imageLoader;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		imageLoader = ImageLoader.getInstance();
		dbdb = new DragonBroDatabaseHandler(this);

		LayoutInflater inflater = getLayoutInflater();

		inflater.inflate(R.layout.mine,
				(ViewGroup) findViewById(R.id.container));
		
		layout();

	}
	
	@SuppressLint("NewApi")
	void layout() {
		ArrayList<HistoryListContainer> historyRests = dbdb.getHistoryList(dbdb.getCurrentUser());
		LinearLayout historyList = (LinearLayout) findViewById(R.id.history_list);
		for (HistoryListContainer historyRest : historyRests) {
			RestaurantContainer restaurant = dbdb.getRestaurantInfo(historyRest.getRestId());

			// restaurant layout
			RelativeLayout restLayout = new RelativeLayout(this);
			restLayout.setId(Integer.valueOf(restaurant.getRestId()));
			// change to dp unit
			int width = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 400, getResources()
							.getDisplayMetrics());
			int heigth = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 180, getResources()
							.getDisplayMetrics());
			LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
					width, heigth);
			linearParams.setMargins(0, 50, 0, 0);
			((View) restLayout).setBackground(getResources().getDrawable(
					R.drawable.content_bg));
			restLayout.setLayoutParams(linearParams);

			// Restaurant picture
			ImageView rest_pic = new ImageView(this);
			int RestaurantPicID = View.generateViewId();
			rest_pic.setId(RestaurantPicID);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			rest_pic.setLayoutParams(params);
			width = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 150, getResources()
							.getDisplayMetrics());
			heigth = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 120, getResources()
							.getDisplayMetrics());
			rest_pic.getLayoutParams().width = width;
			rest_pic.getLayoutParams().height = heigth;
			String photoFilePath = getRestaurantPhoto(restaurant.getRestId());
			if (photoFilePath != null) {
				Bitmap bitmap = imageLoader.getBitmapFromMemoryCache(photoFilePath);
				if (bitmap == null) {
					bitmap = BitmapFactory.decodeFile(photoFilePath);
					imageLoader.addBitmapToMemoryCache(photoFilePath, bitmap);
				}
				rest_pic.setImageBitmap(bitmap);
			} else {
				rest_pic.setBackgroundResource(R.drawable.egg);
			}

			// Restaurant Name
			TextView restName = new TextView(this);
			int restNameID = View.generateViewId();
			restName.setId(restNameID);
			params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
			params.addRule(RelativeLayout.RIGHT_OF, RestaurantPicID);
			params.setMargins(21, 18, 0, 0);
			restName.setText(restaurant.getName());
			restName.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			restName.setTypeface(Typeface.DEFAULT_BOLD);
			restName.setLayoutParams(params);

			// Time
			TextView orderTime = new TextView(this);
			params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_LEFT, restNameID);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
					RelativeLayout.TRUE);
			params.addRule(RelativeLayout.BELOW, restNameID);
			int marginTop = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 20, getResources()
							.getDisplayMetrics());
			params.setMargins(0, marginTop, 0, 0);
			orderTime.setText(historyRest.getVisitDate());
			orderTime.setTypeface(null, Typeface.ITALIC);
			orderTime.setLayoutParams(params);


			// Add views to layout
			restLayout.addView(rest_pic);
			restLayout.addView(restName);
			restLayout.addView(orderTime);
			
			// add Onclick listener
			restLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(Mine.this, RestaurantDetail.class);
					intent.putExtra("RestaurantID",view.getId());
					startActivity(intent);
				}
			});// End of onclick listener

			historyList.addView(restLayout);
		}//end history list
		
		LinearLayout favoriteList = (LinearLayout) findViewById(R.id.favorite_list);
		ArrayList<String> favoriteID = dbdb.getFavoriteList(dbdb.getCurrentUser());
		for (String ID: favoriteID) {
			RestaurantContainer restaurant = dbdb.getRestaurantInfo(ID);
			
			// restaurant layout
			RelativeLayout restLayout = new RelativeLayout(this);
			restLayout.setId(Integer.valueOf(restaurant.getRestId()));
			// change to dp unit
			int width = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 400, getResources()
							.getDisplayMetrics());
			int heigth = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 180, getResources()
							.getDisplayMetrics());
			LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
					width, heigth);
			linearParams.setMargins(0, 50, 0, 0);
			((View) restLayout).setBackground(getResources().getDrawable(
					R.drawable.content_bg));
			restLayout.setLayoutParams(linearParams);

			// Restaurant picture
			ImageView rest_pic = new ImageView(this);
			int RestaurantPicID = View.generateViewId();
			rest_pic.setId(RestaurantPicID);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			rest_pic.setLayoutParams(params);
			width = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 150, getResources()
							.getDisplayMetrics());
			heigth = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 120, getResources()
							.getDisplayMetrics());
			rest_pic.getLayoutParams().width = width;
			rest_pic.getLayoutParams().height = heigth;
			String photoFilePath = getRestaurantPhoto(restaurant.getRestId());
			if (photoFilePath != null) {
				Bitmap bitmap = imageLoader.getBitmapFromMemoryCache(photoFilePath);
				if (bitmap == null) {
					bitmap = BitmapFactory.decodeFile(photoFilePath);
					imageLoader.addBitmapToMemoryCache(photoFilePath, bitmap);
				}
				rest_pic.setImageBitmap(bitmap);
			} else {
				rest_pic.setBackgroundResource(R.drawable.egg);
			}

			// Restaurant Name
			TextView restName = new TextView(this);
			int restNameID = View.generateViewId();
			restName.setId(restNameID);
			params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
			params.addRule(RelativeLayout.RIGHT_OF, RestaurantPicID);
			params.setMargins(21, 18, 0, 0);
			restName.setText(restaurant.getName());
			restName.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			restName.setTypeface(Typeface.DEFAULT_BOLD);
			restName.setLayoutParams(params);

			// Restaurant Infomation
			TextView restaurantInfo = new TextView(this);
			params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_LEFT, restNameID);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
					RelativeLayout.TRUE);
			params.addRule(RelativeLayout.BELOW, restNameID);
			int marginTop = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 20, getResources()
							.getDisplayMetrics());
			params.setMargins(0, marginTop, 0, 0);
			restaurantInfo.setText(restaurant.getAddress() + '\n'
					+ restaurant.getPhone() + '\n' + restaurant.getEmail()
					+ '\n' + restaurant.getBusinessHour());
			restaurantInfo.setTypeface(null, Typeface.ITALIC);
			restaurantInfo.setLayoutParams(params);


			// Add views to layout
			restLayout.addView(rest_pic);
			restLayout.addView(restName);
			restLayout.addView(restaurantInfo);
			
			// add Onclick listener
			restLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(Mine.this, RestaurantDetail.class);
					intent.putExtra("RestaurantID",view.getId());
					startActivity(intent);
				}
			});// End of onclick listener
			//historyForm.addView(restLayout);
			favoriteList.addView(restLayout);
		}//end history favorite list
	}
	
	private String getRestaurantPhoto(String id) {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                  Environment.DIRECTORY_PICTURES), "RestaurantPhoto");

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("RestaurantPhoto", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "rest_" + id + ".jpg");
        
        return mediaFile.toString();
	}
}
