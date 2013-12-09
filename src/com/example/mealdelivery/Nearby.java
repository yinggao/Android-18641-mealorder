package com.example.mealdelivery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import entities.RestaurantContainer;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

public class Nearby extends Sidebar {

	ArrayList<RestaurantContainer> allRestaurant = null;
	String[] addressNames = null;
	HashMap<Marker, String> markerAndRestIDMap = new HashMap<Marker, String>();
	
	@SuppressWarnings("unchecked")
	@SuppressLint("ShowToast")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);

		allRestaurant = (ArrayList<RestaurantContainer>) getIntent().getSerializableExtra("AllRestaurant");

		LayoutInflater inflater = getLayoutInflater();

		inflater.inflate(R.layout.nearby, (ViewGroup) findViewById(R.id.container));

		// Get a handle to the Map Fragment
		final GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		if (map == null) {
			Toast.makeText(this, "Unable to load map", Toast.LENGTH_LONG);
			return;
		}

		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		String bestProvider = locationManager.getBestProvider(new Criteria(),true);

		//locationManager.
		Location mylocation = locationManager.getLastKnownLocation(bestProvider);

		map.setMyLocationEnabled(true);
		
		LatLng mylocLatLng = null;
		if( mylocation != null) {//if can not get my location
			mylocLatLng = new LatLng(mylocation.getLatitude(), mylocation.getLongitude());
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocLatLng, 15));
		}
		
		
		map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
			Geocoder geocoder = new Geocoder(Nearby.this, Locale.US);
			@Override
			public void onMapLoaded() {
				//List<String> addressNames = new ArrayList<String>(Arrays.asList("Orient Express", "CMU, pittsburgh", "Little Asia", "Lulu's Noodles"));
				for(RestaurantContainer restaurant : allRestaurant) {
					List<Address> addresses = null;
					try {
						addresses = geocoder.getFromLocationName(restaurant.getAddress(), 10);
						//geocoder.
						//addresses = geocoder.getFromLocationName(addressName, 1);
					} catch (IOException e) {
						e.printStackTrace();
					}
			
					if (addresses != null && addresses.size() != 0) {
						//Address address = addresses.get(0);
						for(Address address:addresses){
							Marker marker = map.addMarker(new MarkerOptions()
									.title(restaurant.getName())
									.snippet(restaurant.getAddress())
									.position(new LatLng(address.getLatitude(), address.getLongitude())));
							markerAndRestIDMap.put(marker, restaurant.getRestId());
						}//end for address
					}//end if addresses
				}//end for addressNames
			}
		});

		
		map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker marker) {
				Intent intent = new Intent(Nearby.this, RestaurantDetail.class);
				intent.putExtra("RestaurantID", Integer.parseInt(markerAndRestIDMap.get(marker)));
				startActivity(intent);
			}
		});
	}//end on create
	
	//A helper function that calculate distance based on lat & lng
	protected double getdistance(double lat1, double lng1, double lat2, double lng2) {
		final double EARTH_R = 6378.137;
	    double lat1rad=lat1*Math.PI/180.0;
	    double lng1rad=lng1*Math.PI/180.0;
	    double lat2rad=lat2*Math.PI/180.0;
	    double lng2rad=lng2*Math.PI/180.0;
	    double a=lat1rad-lat2rad;
	    double b=lng1rad-lng2rad;
	    double dis=2*EARTH_R*Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(lat1rad)*Math.cos(lat1rad)*Math.pow(Math.sin(b/2),2)));
	    return dis;
	}
}


