/**
 * Reference:
 * [1] How to get all checked box: http://stackoverflow.com/questions/19027843/android-get-text-of-all-checked-checkboxes-in-listview
 */

package com.example.mealdelivery;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ws.remote.EMail;

import DBLayout.DishContainer;
import DBLayout.DragonBroDatabaseHandler;
import DBLayout.RestaurantContainer;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("SdCardPath")
public class RestaurantDetail extends Sidebar {
	DragonBroDatabaseHandler dbdb = null;
	private static final String OUTPUT_FILE = "/sdcard/recordoutput.3gpp";
	private MediaPlayer mediaPlayer;
	private MediaRecorder recorder;
	List<String> dishBag = new ArrayList<String>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbdb = new DragonBroDatabaseHandler(this);

		LayoutInflater inflater = getLayoutInflater();

		inflater.inflate(R.layout.restaurant,
				(ViewGroup) findViewById(R.id.container));

		int restaurantID = getIntent().getIntExtra("RestaurantID", -1);
		if (restaurantID != -1) {
			// show restaurant detail info
			RestaurantContainer restaurant = dbdb.getRestaurantInfo(String
					.valueOf(restaurantID));
			showInfo(restaurant);
		}

		final TextView btnVoicePop = (TextView) findViewById(R.id.voice);

		btnVoicePop.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				LayoutInflater layoutInflater = (LayoutInflater) RestaurantDetail.this
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				View popupView = layoutInflater.inflate(R.layout.voice_popup,
						(ViewGroup) findViewById(R.id.pop_element), false);
				final PopupWindow popupWindow = new PopupWindow(popupView,
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

				popupWindow.setOutsideTouchable(false);
				popupWindow.setFocusable(true);

				final Button startBtn = (Button) popupView
						.findViewById(R.id.start_record);
				Button checkBtn = (Button) popupView.findViewById(R.id.check);
				Button sendBtn = (Button) popupView.findViewById(R.id.send);

				startBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						try {
							if (mediaPlayer != null) {
								stopPlayingRecording();
							}
							if (startBtn.getText().equals("stop")) {

								startBtn.setText("start");
								stopRecording();

							} else {
								startBtn.setText("stop");
								beginRecording();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

				checkBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						try {
							playRecording();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

				sendBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent email = new Intent(RestaurantDetail.this,
								EMail.class);
						List<String> list = new ArrayList<String>();
						list.add("test@test.com");
						String[] mailList = new String[list.size()];
						mailList = list.toArray(mailList);
						email.putExtra("Rcipients", mailList);
						email.putExtra("Subject", "Order meals from XXXXX");
						email.putExtra("Body", "I want this this and taht");
						startActivity(email);
						// new EMail().withRcipients("test@test.com")
						// .withSubject("Order meal")
						// .withBody("I want this this and that")
						// .send();
					}
				});

				// dismiss pop up window
				Button btnDismiss = (Button) popupView
						.findViewById(R.id.dismiss);
				btnDismiss.setOnClickListener(new Button.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						popupWindow.dismiss();
					}
				});

				popupWindow.showAsDropDown(btnVoicePop, 0, 0);
				// popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
			}
		});
		
		Button order_button = (Button) findViewById(R.id.order_button);
		order_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent email = new Intent(RestaurantDetail.this,
						EMail.class);
				List<String> list = new ArrayList<String>();
				list.add("test@test.com");
				String[] mailList = new String[list.size()];
				mailList = list.toArray(mailList);
				email.putExtra("Rcipients", mailList);
				email.putExtra("Subject", "Order meals from CMU");
				StringBuilder body = new StringBuilder();
				body.append("I want These dishes:\n");
				int i=1;
				for(String dishName: dishBag) {
					body.append(i+"." + dishName+"\n");
					i++;
				}
				
				email.putExtra("Body", body.toString());
				startActivity(email);
			}
		});

	}

	private void beginRecording() throws Exception {
		killMediaRecorder();
		File outFile = new File(OUTPUT_FILE);
		if (outFile.exists()) {
			outFile.delete();
		}
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(OUTPUT_FILE);
		recorder.prepare();
		recorder.start();
	}

	private void stopRecording() throws Exception {
		if (recorder != null) {
			recorder.stop();
		}
	}

	private void killMediaRecorder() {
		if (recorder != null) {
			recorder.release();
		}
	}

	private void killMediaPlayer() {
		if (mediaPlayer != null) {
			try {
				mediaPlayer.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void playRecording() throws Exception {
		killMediaPlayer();
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDataSource(OUTPUT_FILE);
		mediaPlayer.prepare();
		mediaPlayer.start();
	}

	private void stopPlayingRecording() throws Exception {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		killMediaRecorder();
		killMediaPlayer();
	}

	protected void showInfo(RestaurantContainer restaurant) {
		// Set Name
		TextView name = (TextView) findViewById(R.id.name);
		name.setText(restaurant.getName());

		// Set description
		TextView info = (TextView) findViewById(R.id.descrption);

		String dishInfo = restaurant.getAddress() + "\n"
				+ restaurant.getPhone() + "\n" + restaurant.getBusinessHour()
				+ "\n" + restaurant.getEmail() + "\n";
		info.setText(dishInfo);

		layoutAllDishes(restaurant.getRestId());
	}

	@SuppressLint("NewApi")
	void layoutAllDishes(String restId) {
		LinearLayout dishList = (LinearLayout) findViewById(R.id.dish_list);

		ArrayList<DishContainer> allDishes = dbdb.getDishesInfo(restId);
		for (DishContainer dish : allDishes) {
			// restaurant layout
			RelativeLayout dishLayout = new RelativeLayout(this);
			dishLayout.setId(Integer.valueOf(dish.getDishId()));
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
			((View) dishLayout).setBackground(getResources().getDrawable(
					R.drawable.content_bg));
			dishLayout.setLayoutParams(linearParams);

			// Restaurant picture
			TextView dish_pic = new TextView(this);
			int RestaurantPicID = View.generateViewId();
			dish_pic.setId(RestaurantPicID);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			dish_pic.setLayoutParams(params);
			width = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 150, getResources()
							.getDisplayMetrics());
			heigth = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 120, getResources()
							.getDisplayMetrics());
			dish_pic.getLayoutParams().width = width;
			dish_pic.getLayoutParams().height = heigth;
			dish_pic.setBackgroundResource(R.drawable.dish);

			// Dish Name
			TextView dishName = new TextView(this);
			int dishNameID = View.generateViewId();
			dishName.setId(dishNameID);
			params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
			params.addRule(RelativeLayout.RIGHT_OF, RestaurantPicID);
			params.setMargins(21, 18, 0, 0);
			dishName.setText(dish.getName());
			dishName.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			dishName.setTypeface(Typeface.DEFAULT_BOLD);
			dishName.setLayoutParams(params);

			// Dish Infomation
			TextView dishInfo = new TextView(this);
			params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_LEFT, dishNameID);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
					RelativeLayout.TRUE);
			params.addRule(RelativeLayout.BELOW, dishNameID);
			int marginTop = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 20, getResources()
							.getDisplayMetrics());
			params.setMargins(0, marginTop, 0, 0);

			dishInfo.setText(dish.getDescription());
			dishInfo.setTypeface(null, Typeface.ITALIC);
			dishInfo.setLayoutParams(params);

			// Add Check Box
			CheckBox checkBox = new CheckBox(RestaurantDetail.this);
			params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			params.setMargins(0, 0, 20, 20);
			checkBox.setLayoutParams(params);
			checkBox.setText("Add to bag");

			// Add to bag listener
			final String dishNameString = dish.getName();
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if (isChecked) {// Add to bag
						dishBag.add(dishNameString);
					} else {// Remove from bag
						dishBag.remove(dishNameString);
					}
				}
			});

			// Add views to layout
			dishLayout.addView(dish_pic);
			dishLayout.addView(dishName);
			dishLayout.addView(dishInfo);
			dishLayout.addView(checkBox);

			dishList.addView(dishLayout);
		}
	}
}
