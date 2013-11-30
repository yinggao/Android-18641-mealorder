package com.example.mealdelivery;


import DBLayout.DragonBroDatabaseHandler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Signin extends Activity {
	private DragonBroDatabaseHandler dbdb;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getIntent();

		setContentView(R.layout.signin);
		
		dbdb = new DragonBroDatabaseHandler(this);

	}
	
	public void signinMethod(View view) {
		EditText userNameText = (EditText)findViewById(R.id.username);
		String userName = userNameText.getText().toString();
		EditText passwordText = (EditText)findViewById(R.id.password);
		String password = passwordText.getText().toString();
		if (!dbdb.existStudent(userName)) {
			TextView errText = (TextView)findViewById(R.id.errinput);
			errText.setText("User doesn't exist!");
			passwordText.setText("");
		}
		else if (!dbdb.login(userName, password)) {
			TextView errText = (TextView)findViewById(R.id.errinput);
			errText.setText("Password is wrong!");
			passwordText.setText("");
		} else {
			TextView errText = (TextView)findViewById(R.id.errinput);
			errText.setText("");
			//Build a new intent
			Intent intent = new Intent(this, SearchByName.class);
			startActivity(intent);
		}
	}
	
	public void signupMethod(View view) {
		//Build a new intent
		Intent intent = new Intent(this, Signup.class);
		startActivity(intent);
	}
}
