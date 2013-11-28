package ws.remote;
//package com.example.mealdelivery;

/**
 * Reference:
 * [1] Send attachment: http://blog.csdn.net/aboy123/article/details/9156369
 */
import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

@SuppressLint("SdCardPath")
public class EMail extends Activity {
	File attachement = new File("/sdcard/recordoutput.3gppasd");  

	private String[] recipients=null;
	private String subject=null;
	private String body=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		setRecipients(intent.getStringArrayExtra("Rcipients"));
		setSubject(intent.getStringExtra("Subject"));
		setBody(intent.getStringExtra("Body"));
		send();
		finish();
	}
	/**
	 * This method is used to send an E-Mail
	 */
	public void send() {
		Intent sendEmail = new Intent(android.content.Intent.ACTION_SEND);//start an email intent
		sendEmail.setType("plain/text");
		
		//put extra information, if no information is provided, responding field will be left blank
		if (recipients != null) {
			sendEmail.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
		}
		
		if(subject != null) {
			sendEmail.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		}
		
		if(body != null) {
			sendEmail.putExtra(android.content.Intent.EXTRA_TEXT, body);
		}
		
		sendEmail.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(attachement));
		
		Intent testIntent = Intent.createChooser(sendEmail, "Choose an email client:");
		startActivity(testIntent);//choose email client then start email intent
		//startActivity(sendEmail);
	}

	public void setRecipients(String[] recipients) {
		this.recipients = recipients;
	}
	
	public void setRecipients(Object... args) {
		recipients = new String[args.length];
		for(int i=0;i<args.length;i++) {
			recipients[i] = (String)args[i];
		}
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public EMail withRecipients(String[] recipients) {
		this.setRecipients(recipients);
		return this;
	}
	
	public EMail withRcipients(Object... args) {
		this.setRecipients(args);
		return this;
	}
	
	public EMail withSubject(String subject) {
		this.setSubject(subject);
		return this;
	}
	
	public EMail withBody(String body) {
		this.setBody(body);
		return this;
	}
	
}
