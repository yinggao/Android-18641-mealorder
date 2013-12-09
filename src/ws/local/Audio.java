package ws.local;

import java.io.IOException;

import android.media.MediaPlayer;

public class Audio {

	public void playAudio(MediaPlayer mediaPlayer) throws IllegalStateException, IOException {
		mediaPlayer.prepare();
		mediaPlayer.start();
	}
	
	public void stopAudio(MediaPlayer mediaPlayer) {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
		}
	}
}
