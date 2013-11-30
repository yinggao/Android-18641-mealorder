package com.example.mealdelivery;

import java.io.File;

import DBLayout.DragonBroDatabaseHandler;
import DBLayout.StudentContainer;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Signup extends Activity {
	private DragonBroDatabaseHandler dbdb;
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;
	private Uri fileUri;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getIntent();

		setContentView(R.layout.signup);
		dbdb = new DragonBroDatabaseHandler(this);		
	}
	
	public void signinMethod(View view) {
		Intent intent = new Intent(this, Signin.class);			
		startActivity(intent);
	}
	
	public void signupMethod(View view) {
		EditText userNameText = (EditText)findViewById(R.id.username);
		String userName = userNameText.getText().toString();
		EditText passwordText = (EditText)findViewById(R.id.password);
		String password = passwordText.getText().toString();
		EditText firstNameText = (EditText)findViewById(R.id.firstname);
		String firstName = firstNameText.getText().toString();
		EditText lastNameText = (EditText)findViewById(R.id.lastname);
		String lastName = lastNameText.getText().toString();
		EditText phoneNumberText = (EditText)findViewById(R.id.phonenumber);
		String phoneNumber = phoneNumberText.getText().toString();
		EditText addressText = (EditText)findViewById(R.id.address);
		String address = addressText.getText().toString();
		TextView errinput = (TextView)findViewById(R.id.errinput);
		if (!userName.equals("") && dbdb.existStudent(userName)) {
			errinput.setText("User already exists!");
		}
		if (userName.equals("") || password.equals("") ||
				firstName.equals("") || lastName.equals("") ||
				phoneNumber.equals("") || address.equals("")) {
			errinput.setText("Please fill in every input box!");
			passwordText.setText("");
		} else {
			errinput.setText("");
			StudentContainer newStudent;
        	if (fileUri != null) {//If user took a photo
        		fileUri = renamePhoto(userName);
    			newStudent = new StudentContainer(userName, password,
    					firstName, lastName, address, phoneNumber, fileUri.toString());
        	} else {// no photo
    			newStudent = new StudentContainer(userName, password,
    					firstName, lastName, address, phoneNumber, null);
        	}
			dbdb.addStudent(newStudent);
			dbdb.login(userName, password);
			Intent intent = new Intent(this, SearchByName.class);			
			startActivity(intent);
		}
	}

	public void takePhoto(View view) {
		if (ExistSDCard()) {
	    	// create Intent to take a picture and return control to the calling application
	        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        
	        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
	        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
	
	        // start the image capture Intent
	        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		}
    }
	
	 /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
          return Uri.fromFile(getOutputMediaFile(type));
    }
    	
    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                  Environment.DIRECTORY_PICTURES), "UserPhoto");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("UserPhoto", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "IMG_temp" + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
            	ImageView image = (ImageView)findViewById(R.id.camera);
            	image.setImageURI(fileUri);
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }
    }
    
    /** Create a File for saving an image or video */
    private static Uri renamePhoto(String newName){
    	File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                  Environment.DIRECTORY_PICTURES), "UserPhoto");

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("UserPhoto", "failed to create directory");
                return null;
            }
        }

        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
        "IMG_temp" + ".jpg");
        File newPath = new File(mediaStorageDir.getPath() + File.separator
        		+ newName + ".jpg");
        mediaFile.renameTo(newPath);
        
        return Uri.fromFile(newPath);
    }
    
    private boolean ExistSDCard() {  
	if (android.os.Environment.getExternalStorageState().equals(  
		android.os.Environment.MEDIA_MOUNTED)) {  
		return true;  
	} else  
		return false;  
	}
}
