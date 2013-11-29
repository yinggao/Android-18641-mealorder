package com.example.mealdelivery;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ws.remote.EMail;


import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public class RestaurantDetail extends Sidebar {
	
	private static final String OUTPUT_FILE= "/sdcard/recordoutput.3gpp";
	private MediaPlayer mediaPlayer;
	private MediaRecorder recorder;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getIntent();

		LayoutInflater inflater = getLayoutInflater();

		inflater.inflate(R.layout.restaurant,
				(ViewGroup) findViewById(R.id.container));

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

				final Button startBtn = (Button) popupView.findViewById(R.id.start_record);
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
						Intent email = new Intent(RestaurantDetail.this, EMail.class);
						List<String> list = new ArrayList<String>();
						list.add("test@test.com");
						String[] mailList = new String[list.size()];
						mailList = list.toArray(mailList);
						email.putExtra("Rcipients", mailList);
						email.putExtra("Subject", "Order meals from XXXXX");
						email.putExtra("Body", "I want this this and taht");
						startActivity(email);
//			            new EMail().withRcipients("test@test.com")
//		                   .withSubject("Order meal")
//		                   .withBody("I want this this and that")
//		                   .send();
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
}
