/**
 * Reference:
 * [1] How to get all checked box: http://stackoverflow.com/questions/19027843/android-get-text-of-all-checked-checkboxes-in-listview
 */

package com.example.mealdelivery;

import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import ws.remote.EMail;
import DBLayout.DishContainer;
import DBLayout.DragonBroDatabaseHandler;
import DBLayout.FavoriteListContainer;
import DBLayout.HistoryListContainer;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SdCardPath")
public class RestaurantDetail extends Sidebar {

	private DragonBroDatabaseHandler dbdb = null;
	private static final String OUTPUT_FILE = "/sdcard/recordoutput.3gpp";
	private MediaPlayer mediaPlayer;
	private MediaPlayer vPlayer;
	private MediaRecorder recorder;
	private String restIDstr=null;
	private Boolean hasRecord = false;
	private TextView btnListen;
	private Button checkBtn;
	private List<String> dishBag = new ArrayList<String>();
	private Boolean isPlaying = false;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getIntent();
		dbdb = new DragonBroDatabaseHandler(this);
		dishBag = new ArrayList<String>();

		LayoutInflater inflater = getLayoutInflater();

		inflater.inflate(R.layout.restaurant,
				(ViewGroup) findViewById(R.id.container));

		final int restaurantID = getIntent().getIntExtra("RestaurantID", -1);
		if (restaurantID != -1) {
			// show restaurant detail info
			RestaurantContainer restaurant = dbdb.getRestaurantInfo(String
					.valueOf(restaurantID));
			showInfo(restaurant);
			restIDstr = String.valueOf(restaurantID);
		}

		final String toEmail = dbdb.getRestaurantInfo(
				String.valueOf(restaurantID)).getEmail();

		final TextView btnVoicePop = (TextView) findViewById(R.id.voice);

		final TextView btnFeedback = (TextView) findViewById(R.id.feedback);

		btnFeedback.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent email = new Intent(RestaurantDetail.this,
						EMail.class);
				List<String> list = new ArrayList<String>();
				list.add(toEmail);
				String[] mailList = new String[list.size()];
				mailList = list.toArray(mailList);
				email.putExtra("Rcipients", mailList);
				email.putExtra("Subject", "Give feedback");
				email.putExtra("Body", "I like it......");
				startActivity(email);
			}
		});

		btnVoicePop.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				LayoutInflater layoutInflater = (LayoutInflater) RestaurantDetail.this
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				View popupView = layoutInflater.inflate(R.layout.voice_popup,
						(ViewGroup) findViewById(R.id.pop_element), false);
				final PopupWindow popupWindow = new PopupWindow(popupView, 80,
						400);

				popupWindow.setOutsideTouchable(false);
				popupWindow.setFocusable(true);

				final Button startBtn = (Button) popupView
						.findViewById(R.id.start_record);

				checkBtn = (Button) popupView.findViewById(R.id.check);
				Button sendBtn = (Button) popupView.findViewById(R.id.send);

				startBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						hasRecord = true;
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
							if (checkBtn.getText().equals("OK")) {
								checkBtn.setText("check");
								stopPlayingRecording();
							} else {
								checkBtn.setText("OK");
								playRecording();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

				sendBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						killMediaPlayer(vPlayer);
						killMediaPlayer(mediaPlayer);
						if (hasRecord) {
							Intent email = new Intent(RestaurantDetail.this,
									EMail.class);
							List<String> list = new ArrayList<String>();
							list.add(toEmail);
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
						} else {
							Toast.makeText(RestaurantDetail.this,
									"Please record first!", Toast.LENGTH_LONG)
									.show();
						}
					}
				});

				// dismiss pop up window
				Button btnDismiss = (Button) popupView
						.findViewById(R.id.dismiss);
				btnDismiss.setOnClickListener(new Button.OnClickListener() {

					@Override
					public void onClick(View v) {
						try {
							stopPlayingRecording();
							stopPlayingDescription();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// TODO Auto-generated method stub
						popupWindow.dismiss();
					}
				});

				popupWindow.showAsDropDown(btnVoicePop, 0, 0);
				// popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
			}
		});
		
		TextView btnOrder = (TextView) findViewById(R.id.order_button);
		btnOrder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent email = new Intent(RestaurantDetail.this, EMail.class);
				List<String> list = new ArrayList<String>();
				list.add(toEmail);
				String[] mailList = new String[list.size()];
				mailList = list.toArray(mailList);
				email.putExtra("Rcipients", mailList);
				email.putExtra("Subject", "Order meals from CMU");
				StringBuilder body = new StringBuilder();
				body.append("I want These dishes:\n");
				int i = 1;
				for (String dishName : dishBag) {
					body.append(i + "." + dishName + "\n");
					i++;
				}
				email.putExtra("Body", body.toString());
				dbdb.addToHistoryList(new HistoryListContainer(dbdb.getCurrentUser(),
						 dateToString(new Date()), restIDstr));
				startActivity(email);
			}
		});
		
		TextView addfavorite = (TextView) findViewById(R.id.addfavorite);
		addfavorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				dbdb.addToFavoriteList(new FavoriteListContainer(restIDstr, dbdb.getCurrentUser()));
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

	private void killMediaPlayer(MediaPlayer mediaPlayer) {
		if (mediaPlayer != null) {
			try {
				mediaPlayer.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void playRecording() throws Exception {
		killMediaPlayer(mediaPlayer);
		mediaPlayer = new MediaPlayer();
		mediaPlayer
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					public void onCompletion(MediaPlayer mp) {
						checkBtn.setText("check");
					}
				});
		mediaPlayer.setDataSource(OUTPUT_FILE);
		mediaPlayer.prepare();
		mediaPlayer.start();
	}

	private void stopPlayingRecording() throws Exception {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
		}
	}

	private void playDescription(String voicePath) throws Exception {
		killMediaPlayer(vPlayer);
		vPlayer = new MediaPlayer();
		vPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				btnListen.setBackgroundResource(R.drawable.listen);
				isPlaying = false;
			}
		});
		vPlayer.setDataSource(voicePath);
		vPlayer.prepare();
		isPlaying = true;
		vPlayer.start();
	}

	private void stopPlayingDescription() throws Exception {
		if (vPlayer != null) {
			isPlaying = false;
			vPlayer.stop();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		killMediaRecorder();
		killMediaPlayer(mediaPlayer);
		killMediaPlayer(vPlayer);
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
			int dishInfoID = View.generateViewId();
			dishInfo.setId(dishInfoID);
			dishInfo.setText(dish.getDescription());
			dishInfo.setTypeface(null, Typeface.ITALIC);
			dishInfo.setLayoutParams(params);
			
			// Listen description
			TextView linstenBtn = new TextView(this);
			params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_LEFT, dishNameID);
			params.addRule(RelativeLayout.BELOW, dishInfoID);
			marginTop = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 20, getResources()
							.getDisplayMetrics());
			params.setMargins(0, marginTop, 0, 0);
			linstenBtn.setLayoutParams(params);
			width = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 50, getResources()
							.getDisplayMetrics());
			heigth = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 50, getResources()
							.getDisplayMetrics());
			linstenBtn.getLayoutParams().width = width;
			linstenBtn.getLayoutParams().height = heigth;
			linstenBtn.setBackgroundResource(R.drawable.listen);
			
			final String dishID = dish.getDishId();
			linstenBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String dishVoiceFile = dbdb.getDishInfo(dishID,
							String.valueOf(restIDstr)).getAudioPath();
					// TODO hard code dishVoiceFile same as record! Please delete
					// it!!
					dishVoiceFile = OUTPUT_FILE;

					try {
						if (isPlaying) {
							btnListen.setBackgroundResource(R.drawable.listen);
							stopPlayingDescription();
						} else {
							btnListen.setBackgroundResource(R.drawable.listen_stop);
							playDescription(dishVoiceFile);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			});
			

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
			dishLayout.addView(linstenBtn);

			dishList.addView(dishLayout);
		}
	}
	
	public String dateToString(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("MM/dd/yyyy");
		return formater.format(date);
	}
}
