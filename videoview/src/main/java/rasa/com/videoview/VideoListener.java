package rasa.com.videoview;

import com.google.android.exoplayer2.ExoPlaybackException;

public interface VideoListener {
    void onStartPlaying();
    void onStopPlaying();
    void onError(ExoPlaybackException error);


}
