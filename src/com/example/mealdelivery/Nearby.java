package com.example.mealdelivery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

public class Nearby extends Sidebar {

	@SuppressLint("ShowToast")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);

		getIntent();

		LayoutInflater inflater = getLayoutInflater();

		inflater.inflate(R.layout.nearby, (ViewGroup) findViewById(R.id.container));

		

		// Get a handle to the Map Fragment
		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
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

		
		Geocoder geocoder = new Geocoder(this, Locale.US);
		List<String> addressNames = new ArrayList<String>(Arrays.asList("Orient Express", "CMU, pittsburgh", "Little Asia", "Lulu's Noodles"));
		for(String addressName : addressNames) {
			List<Address> addresses = null;
			try {
				addresses = geocoder.getFromLocationName(addressName, 10,  mylocLatLng.latitude-5, mylocLatLng.longitude-5, mylocLatLng.latitude+5, mylocLatLng.longitude+5);
				//geocoder.
				//addresses = geocoder.getFromLocationName(addressName, 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			if (addresses != null && addresses.size() != 0) {
				//Address address = addresses.get(0);
				for(Address address:addresses){
					map.addMarker(new MarkerOptions()
							.title(addressName)
							.snippet(address.getSubThoroughfare() + " " + address.getThoroughfare())
							.position(new LatLng(address.getLatitude(), address.getLongitude())));
				}//end for address
			}//end if addresses
		}//end for addressNames
	}//end on create
}
//xlg is absolutly wrong!

