/**
 * Reference:
 * [1] Progrmaticlly layout: http://stackoverflow.com/questions/10418929/add-view-programmatically-to-relativelayout
 */
package com.example.mealdelivery;

import java.util.ArrayList;
import java.util.List;

import DBLayout.DragonBroDatabaseHandler;
import DBLayout.RestaurantContainer;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Type;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
//import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class SearchByName extends Sidebar {
	DragonBroDatabaseHandler dbdb = null;
	ArrayList<RestaurantContainer> allRestaurant = new ArrayList<RestaurantContainer>();//Contain all search results, use to communicate with map

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getIntent();

		LayoutInflater inflater = getLayoutInflater();
		inflater.inflate(R.layout.search,
				(ViewGroup) findViewById(R.id.container));

		dbdb = new DragonBroDatabaseHandler(this);

		allRestaurant = dbdb.getAllRestaurantsInfo();
		layout();

		// Add on click
		TextView show_in_map = (TextView) findViewById(R.id.show_in_map);
		show_in_map.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SearchByName.this, Nearby.class);

				intent.putExtra("AllRestaurant", allRestaurant);
				// TODO: put search result into inteng
				startActivity(intent);
			}
		});

		final Spinner categorySelect = (Spinner) findViewById(R.id.categorySelect);
		categorySelect.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String option = (String) categorySelect.getSelectedItem();
				searchByCategory(option);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				return;
			}
		});
		
		Button search_button = (Button) findViewById(R.id.search_button);
		search_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText search_query = (EditText) findViewById(R.id.search_query);
				String name = search_query.getText().toString();
				searchByName(name);
			}
		});

	}

	void searchByCategory(String category) {
		LinearLayout searchList = (LinearLayout) findViewById(R.id.search_list);
		// Clear previous search result
		searchList.removeAllViews();
		allRestaurant = dbdb.getRestaurantsFromCategory(category);
		layout();
	}
	
	void searchByName(String name) {
		LinearLayout searchList = (LinearLayout) findViewById(R.id.search_list);
		// Clear previous search result
		searchList.removeAllViews();
		
		allRestaurant = dbdb.getRestaurantsFromName(name);
		layout();
	}

	void layout() {
		LinearLayout searchList = (LinearLayout) findViewById(R.id.search_list);
		// get the first 10 restaurants
		int NO = 1;

		for (RestaurantContainer restaurant : allRestaurant) {
			// restaurant layout
			RelativeLayout restaurantLayout = new RelativeLayout(this);
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
			((View) restaurantLayout).setBackground(getResources().getDrawable(
					R.drawable.content_bg));
			restaurantLayout.setLayoutParams(linearParams);

			// Restaurant picture
			TextView restaurant_pic = new TextView(this);
			int RestaurantPicID = View.generateViewId();
			restaurant_pic.setId(RestaurantPicID);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			restaurant_pic.setLayoutParams(params);
			width = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 150, getResources()
							.getDisplayMetrics());
			heigth = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 120, getResources()
							.getDisplayMetrics());
			restaurant_pic.getLayoutParams().width = width;
			restaurant_pic.getLayoutParams().height = heigth;
			restaurant_pic.setBackgroundResource(R.drawable.egg);

			// Restaurant Name
			TextView restaurantName = new TextView(this);
			int restaurantNameID = View.generateViewId();
			restaurantName.setId(restaurantNameID);
			params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
			params.addRule(RelativeLayout.RIGHT_OF, RestaurantPicID);
			params.setMargins(21, 18, 0, 0);
			restaurantName.setText(restaurant.getName());
			restaurantName.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			restaurantName.setTypeface(Typeface.DEFAULT_BOLD);
			restaurantName.setLayoutParams(params);

			// Restaurant Infomation
			TextView restaurantInfo = new TextView(this);
			params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_LEFT, restaurantNameID);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
					RelativeLayout.TRUE);
			params.addRule(RelativeLayout.BELOW, restaurantNameID);
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
			restaurantLayout.addView(restaurant_pic);
			restaurantLayout.addView(restaurantName);
			restaurantLayout.addView(restaurantInfo);

			// add Onclick listener
			restaurantLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(SearchByName.this,
							RestaurantDetail.class);
					// TODO: Put restaurant information into Intent/
					startActivity(intent);
				}
			});// End of onclick listener

			searchList.addView(restaurantLayout);

			NO++;
			if (NO > 10) {
				break;
			}

		}
	}
}
