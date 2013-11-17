package ws.remote;


import android.app.Activity;
import android.content.Intent;

public class EMail extends Activity {

	private String[] recipients=null;
	private String subject=null;
	private String body=null;
	/**
	 * This method is used to send an E-Mail
	 */
	public void send() {
		Intent sendEmail = new Intent(android.content.Intent.ACTION_SEND);//start an email intent
		sendEmail.setType("plain/text");
		
		//put extra information, if no information is provided, responding field will be left blank
		/*if (recipients != null) {
			sendEmail.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
		}
		
		if(subject != null) {
			sendEmail.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		}
		
		if(body != null) {
			sendEmail.putExtra(android.content.Intent.EXTRA_TEXT, body);
		}*/
		
		startActivity(Intent.createChooser(sendEmail, "Choose an email client:"));//choose email client then start email intent
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
