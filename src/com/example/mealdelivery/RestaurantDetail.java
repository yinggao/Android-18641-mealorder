package com.example.mealdelivery;

import java.io.File;

import com.google.android.gms.games.Player;
import com.google.android.gms.internal.fi;

import ws.remote.EMail;
import DBLayout.DragonBroDatabaseHandler;
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
import android.widget.Toast;

public class RestaurantDetail extends Sidebar {

	private static final String OUTPUT_FILE = "/sdcard/recordoutput.3gpp";
	private MediaPlayer mediaPlayer;
	private MediaPlayer vPlayer;
	private MediaRecorder recorder;
	private Boolean hasRecord = false;
	private TextView btnListen;
	private Button checkBtn;

	// TODO get params from intent
	private String restID = "1";
	private String dishID = "1";

	private DragonBroDatabaseHandler dbdb;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getIntent();

		LayoutInflater inflater = getLayoutInflater();

		inflater.inflate(R.layout.restaurant,
				(ViewGroup) findViewById(R.id.container));

		// get database information
		dbdb = new DragonBroDatabaseHandler(this);
		final String toEmail = dbdb.getRestaurantInfo(restID).getEmail();

		final TextView btnVoicePop = (TextView) findViewById(R.id.voice);

		final TextView btnFeedback = (TextView) findViewById(R.id.feedback);
		
		TextView btnOrder = (TextView) findViewById(R.id.order);
		
		btnOrder.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new EMail().withRcipients(toEmail)
				.withSubject("Order meal")
				.withBody("I want to order.....")
				.send();
			}
		});
		
		// TODO: since .xml is hard code right now, change id 
		btnListen = (TextView) findViewById(R.id.dish_listen1);
		btnListen.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String dishVoiceFile = dbdb.getDishInfo(dishID, restID).getAudioPath();
				// TODO hard code dishVoiceFile same as record! Please delete it!!
				dishVoiceFile = OUTPUT_FILE;
				
				try {
					if (btnListen.getBackground().equals(getResources().getDrawable(R.drawable.listen_stop))) {
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

		btnFeedback.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new EMail().withRcipients(toEmail)
				.withSubject("Give feedback")
				.withBody("How do you like us? ......")
				.send();
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
				checkBtn = (Button) popupView
						.findViewById(R.id.check);
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
							new EMail().withRcipients("test@test.com")
									.withSubject("Order meal")
									.withBody("I want this this and that")
									.send();
						} else {
							Toast.makeText(RestaurantDetail.this, "Please record first!", Toast.LENGTH_LONG).show();
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
		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
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
		    	btnListen.setBackgroundResource(R.drawable.listen);; // finish current activity
		    }
		});
		vPlayer.setDataSource(voicePath);
		vPlayer.prepare();
		vPlayer.start();
	}

	private void stopPlayingDescription() throws Exception {
		if (vPlayer != null) {
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
}
