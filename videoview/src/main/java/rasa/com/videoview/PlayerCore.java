package rasa.com.videoview;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

class PlayerCore extends RelativeLayout {


    private SimpleExoPlayer player;
    private String videoUrl;
    private static PlayerCore instance;
    private PlayerView playerView;

    private VideoListener videoListener;
    private ProgressBar progressBar;
    private RelativeLayout relWarning;
    private TextView textRetry;

    boolean hasError = false;

    private ImageView imgThumbnail;


    public PlayerCore(Context context) {
        super(context);
        init();
    }

    public PlayerCore(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayerCore(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public static PlayerCore getInstance(Context context) {
        if (instance == null) {
            instance = new PlayerCore(context);
        }
        return instance;
    }

    public static PlayerCore getInstance() {
        return instance;
    }

    private void init() {
        inflate(getContext(), R.layout.rel_player_core, this);
        playerView = findViewById(R.id.core_video_view);
        progressBar = findViewById(R.id.progressBar);
        relWarning = findViewById(R.id.rel_warning);
        textRetry = findViewById(R.id.text_retry);
        imgThumbnail = findViewById(R.id.img_holder_thumbnail);

    }

    public void attachToViewGroup(RelativeLayout relParent) {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }

        relParent.addView(this);
    }

    public void onResume(VideoListener videoListener) {
        if (player != null) {
            player.setPlayWhenReady(true);
        }
        setVideoListener(videoListener);
    }

    public void onPause() {
        if (player != null) {
            player.setPlayWhenReady(false);
        }
    }

    public void onDestroy() {
        releasePlayer();
    }


    public void loadVideo(final String url,final String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            imgThumbnail.setVisibility(VISIBLE);
            Picasso.get().load(imageUrl).into(imgThumbnail);
        } else {
            imgThumbnail.setVisibility(GONE);
        }



        if (url == null) return;


        if (player == null) {
            videoUrl = url;
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            Uri uri = Uri.parse(videoUrl);

            MediaSource mediaSource = buildMediaSource(uri);
            player.prepare(mediaSource, false, false);


            player.addListener(new Player.DefaultEventListener() {
                @Override
                public void onLoadingChanged(boolean isLoading) {
                    super.onLoadingChanged(isLoading);
                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    super.onPlayerStateChanged(playWhenReady, playbackState);
                    if (!hasError) {

                        relWarning.setVisibility(GONE);


                        if (videoListener != null) {
                            if (playbackState == Player.STATE_ENDED) {
                                videoListener.onStopPlaying();
                            } else if (playbackState == Player.STATE_READY) {
                                videoListener.onStartPlaying();
                            }
                        }

                        if (playbackState == Player.STATE_IDLE || playbackState == Player.STATE_BUFFERING) {
                            progressBar.setVisibility(VISIBLE);
                        } else {

                            progressBar.setVisibility(GONE);

                            if((videoUrl!=null && videoUrl.isEmpty()) && (videoUrl.toLowerCase().contains(".mp3") ||videoUrl.toLowerCase().contains(".aar"))){

                            }else{
                            imgThumbnail.setVisibility(GONE);
                            }
                        }
                    }

                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {

                    hasError = true;

                    relWarning.setVisibility(VISIBLE);
                    imgThumbnail.setVisibility(VISIBLE);
                    progressBar.setVisibility(GONE);

                    textRetry.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            videoUrl = "";
                            releasePlayer();
                            loadVideo(url,imageUrl);
                        }
                    });

                    if (videoListener != null) {
                        videoListener.onError(error);
                    }
                    super.onPlayerError(error);
                }
            });


        } else if (!url.equals(videoUrl)) {
            releasePlayer();
            loadVideo(url,imageUrl);
            return;
        }


        player.seekTo(player.getCurrentPosition() + 1);
        player.setPlayWhenReady(true);

        player.clearVideoSurface();
        player.setVideoSurfaceView(
                (SurfaceView) playerView.getVideoSurfaceView());

        playerView.setPlayer(player);
    }


    private void setVideoListener(VideoListener videoListener) {
        this.videoListener = videoListener;
    }

    private void releasePlayer() {

        videoUrl = "";
        hasError = false;
        imgThumbnail.setVisibility(GONE);

        if (player != null) {
            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

}
