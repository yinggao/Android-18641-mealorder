/**
 * Reference:
 * [1] How to get all checked box: http://stackoverflow.com/questions/19027843/android-get-text-of-all-checked-checkboxes-in-listview
 */

package com.example.mealdelivery;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ws.remote.EMail;
import DBLayout.DishContainer;
import DBLayout.DragonBroDatabaseHandler;
import DBLayout.FavoriteListContainer;
import DBLayout.HistoryListContainer;
import DBLayout.RestaurantContainer;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import entities.ImageLoader;

@SuppressLint("SdCardPath")
public class RestaurantDetail extends Sidebar {

	private DragonBroDatabaseHandler dbdb = null;
	private static final String OUTPUT_FILE = "/storage/emulated/0/Music/DishAudio/dish_order.3gpp";

	private MediaPlayer mediaPlayer;
	private MediaPlayer vPlayer;
	private MediaRecorder recorder;
	private String restIDstr=null;
	private Boolean hasRecord = false;
	private Button checkBtn;
	private List<String> dishBag = new ArrayList<String>();
	private Boolean isPlaying = false;
	private PopupWindow popupWindow;
	private ImageLoader imageLoader;
	private int columnWidth;
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);

		getIntent();
		dbdb = new DragonBroDatabaseHandler(this);
		dishBag = new ArrayList<String>();
		imageLoader = ImageLoader.getInstance();
		
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
				email.putExtra("HasAudio", false);
				email.putExtra("Subject", "Give feedback");
				email.putExtra("Body", "I like it......");
				startActivity(email);
			}
		});

		final TextView btnVoicePop = (TextView) findViewById(R.id.voice);
		btnVoicePop.setOnClickListener(new View.OnClickListener() {
				
			@Override
			public void onClick(View v) {
				LayoutInflater layoutInflater = (LayoutInflater) RestaurantDetail.this
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				View popupView = layoutInflater.inflate(R.layout.voice_popup,
						(ViewGroup) findViewById(R.id.pop_element), false);
				popupWindow = new PopupWindow(popupView, 80,
						400);
				popupWindow.setBackgroundDrawable(new BitmapDrawable());
				popupWindow.setOutsideTouchable(true);
				popupWindow.setFocusable(true);
				popupWindow.setTouchInterceptor(customPopUpTouchListenr);

				final Button startBtn = (Button) popupView
						.findViewById(R.id.start_record);
				startBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						
						hasRecord = true;
						try {
							if (mediaPlayer != null) {
								stopPlayingRecording();
							}
							if (startBtn.getText().equals("stop")) {

								startBtn.setText("record");
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

				checkBtn = (Button) popupView.findViewById(R.id.check);
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

				Button sendBtn = (Button) popupView.findViewById(R.id.send);
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
							email.putExtra("HasAudio", true);
							email.putExtra("Subject", "Order meals from CMU");
							email.putExtra("Body", "I want this this and that");
							startActivity(email);
							hasRecord = false;
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

//				// dismiss pop up window
//				Button btnDismiss = (Button) popupView
//						.findViewById(R.id.dismiss);
//				btnDismiss.setOnClickListener(new Button.OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						try {
//							stopPlayingRecording();
//							stopPlayingDescription();
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						// TODO Auto-generated method stub
//						popupWindow.dismiss();
//					}
//				});

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
				email.putExtra("HasAudio", false);
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
				Toast.makeText(getBaseContext(), "Add to favorite list", Toast.LENGTH_SHORT).show();
			}
		});
		
		TextView nearBy = (TextView) findViewById(R.id.direction);
		nearBy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
    	    	ArrayList<RestaurantContainer> allRestaurant = new ArrayList<RestaurantContainer>();
    	    	allRestaurant.add(dbdb.getRestaurantInfo(restIDstr));
    	    	Intent intent = new Intent(RestaurantDetail.this, Nearby.class);
    	    	intent.putExtra("AllRestaurant", allRestaurant);
    	    	startActivity(intent);
			}
		});

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		   // TODO Auto-generated method stub
		   if (popupWindow != null && popupWindow.isShowing()) {
		   popupWindow.dismiss();
		   }
		   return super.onTouchEvent(event);
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

	private void playDescription(String voicePath, TextView linstenBtn) throws Exception {
		final TextView linstenFianl = linstenBtn;
		if (vPlayer != null) {
			killMediaPlayer(vPlayer);
		}
		vPlayer = new MediaPlayer();
		vPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				linstenFianl.setBackgroundResource(R.drawable.listen);
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
		ImageView rest_pic = (ImageView)findViewById(R.id.picture);
		columnWidth = rest_pic.getWidth();
		String photoFilePath = getRestaurantPhoto(restaurant.getRestId());
		if (photoFilePath != null) {
			Bitmap bitmap = imageLoader.getBitmapFromMemoryCache(photoFilePath);
			if (bitmap == null) {
				bitmap = BitmapFactory.decodeFile(photoFilePath);
//				bitmap = ImageLoader.decodeSampledBitmapFromResource(
//						photoFilePath, columnWidth);
				imageLoader.addBitmapToMemoryCache(photoFilePath, bitmap);
			}
			rest_pic.setImageBitmap(bitmap);
		} else {
			rest_pic.setBackgroundResource(R.drawable.egg);
		}
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

			// Dish picture
			ImageView dish_pic = new ImageView(this);
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
			String photoFilePath = getDishPhoto(restId, dish.getDishId());
			if (photoFilePath != null) {
				Bitmap bitmap = imageLoader.getBitmapFromMemoryCache(photoFilePath);
				if (bitmap == null) {
					bitmap = BitmapFactory.decodeFile(photoFilePath);
//					bitmap = ImageLoader.decodeSampledBitmapFromResource(
//							photoFilePath, columnWidth);
					imageLoader.addBitmapToMemoryCache(photoFilePath, bitmap);
				}
				dish_pic.setImageBitmap(bitmap);
			} else {
				dish_pic.setBackgroundResource(R.drawable.egg);
			}
			
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
			final TextView linstenBtn = new TextView(this);
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
			final String restID = restId;
			linstenBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String dishVoiceFile = dbdb.getDishInfo(dishID,
							String.valueOf(restIDstr)).getAudioPath();
					// TODO hard code dishVoiceFile same as record! Please delete
					// it!!
//					dishVoiceFile = OUTPUT_FILE;
					dishVoiceFile = getDishAudio(restID, dishID);

					try {
						if (isPlaying) {
							linstenBtn.setBackgroundResource(R.drawable.listen);
							stopPlayingDescription();
						} else {
							linstenBtn.setBackgroundResource(R.drawable.listen_stop);
							if (dishVoiceFile != null) {
								playDescription(dishVoiceFile, linstenBtn);
							}
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
	
	private String getDishPhoto(String restId, String dishId) {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                  Environment.DIRECTORY_PICTURES), "DishPhoto");

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("DishPhoto", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "dish_" + restId + "_" + dishId + ".jpg");
        
        return mediaFile.toString();
	}
	
	private String getDishAudio(String restId, String dishId) {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                  Environment.DIRECTORY_MUSIC), "DishAudio");

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("DishAudio", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "dish_" + restId + "_" + dishId + ".3gpp");
        
        return mediaFile.toString();
	}
	
	View.OnTouchListener customPopUpTouchListenr = new View.OnTouchListener(){

	    @SuppressLint("NewApi")
		@Override
	    public boolean onTouch(View arg0, MotionEvent arg1) {
	    	try {
				stopRecording();
				stopPlayingRecording();
				stopPlayingDescription();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	        return false;
	    }

	};
}
