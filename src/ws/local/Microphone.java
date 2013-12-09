package ws.local;

import java.io.File;

import android.media.MediaRecorder;

public class Microphone {
	private MediaRecorder recorder;
	private static final String OUTPUT_FILE = "/storage/emulated/0/Music/DishAudio/dish_order.3gpp";
	
	public void beginRecording() throws Exception {
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

	public void stopRecording() throws Exception {
		if (recorder != null) {
			recorder.stop();
		}
	}

	private void killMediaRecorder() {
		if (recorder != null) {
			recorder.release();
		}
	}
}
