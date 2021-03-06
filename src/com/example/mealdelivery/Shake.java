package com.example.mealdelivery;

import java.util.Random;

import DBLayout.DragonBroDatabaseHandler;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class Shake extends Sidebar implements SensorEventListener {
	private SensorManager sensorManager;
	private DragonBroDatabaseHandler dbdb;
	
	@Override
	public void onCreate(Bundle savedInstanceState){

		super.onCreate(savedInstanceState);
		
		getIntent();

		LayoutInflater inflater = getLayoutInflater();

		inflater.inflate(R.layout.shake,
				(ViewGroup) findViewById(R.id.container));

		dbdb = new DragonBroDatabaseHandler(this);
		sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
		// add listener. The listener will be HelloAndroid (this) class
		sensorManager.registerListener(this, 
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
		/*	More sensor speeds (taken from api docs)
		    SENSOR_DELAY_FASTEST get sensor data as fast as possible
		    SENSOR_DELAY_GAME	rate suitable for games
		 	SENSOR_DELAY_NORMAL	rate (default) suitable for screen orientation changes
		*/
	}

	public void onAccuracyChanged(Sensor sensor,int accuracy){
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		dbdb = new DragonBroDatabaseHandler(this);
		sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
		// add listener. The listener will be HelloAndroid (this) class
		sensorManager.registerListener(this, 
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	public void onSensorChanged(SensorEvent event){
        int sensorType = event.sensor.getType();
        float[] values = event.values;
        if (sensorType == Sensor.TYPE_ACCELEROMETER){
            if (Math.abs(values[0]) > 14 || Math.abs(values[1]) > 14 || Math.abs(values[2]) > 14){
            	Random generator = new Random();
            	sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        		int id = generator.nextInt(dbdb.getRestaurantNum()) + 1;
            	Intent intent  = new Intent(Shake.this, RestaurantDetail.class);
            	intent.putExtra("RestaurantID",id);
    	    	startActivity(intent);              
            }
        }
	}
}