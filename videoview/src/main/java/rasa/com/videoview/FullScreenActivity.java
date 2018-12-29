package rasa.com.videoview;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;

import static rasa.com.videoview.CvideoView.EXTRA_TITLE;

public class FullScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        RelativeLayout relMain = findViewById(R.id.rel_main);
        PlayerCore.getInstance().attachToViewGroup(relMain);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        findViewById(R.id.exo_fullscreen_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final RelativeLayout toolbar=findViewById(R.id.rel_toolbar);

        PlayerView playerView = findViewById(R.id.core_video_view);


        playerView.setControllerVisibilityListener(new PlayerControlView.VisibilityListener() {
            @Override
            public void onVisibilityChange(int visibility) {
                if(visibility==0){
                    toolbar.setVisibility(View.VISIBLE);
                }else if(visibility==8){
                    makeFullScreen();
                    toolbar.setVisibility(View.GONE);
                }
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ((TextView)(findViewById(R.id.text_title))).setText(getIntent().getStringExtra(EXTRA_TITLE));


    }


    private void makeFullScreen(){
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
       makeFullScreen();
        PlayerCore.getInstance().onResume(new VideoListener() {
            @Override
            public void onStartPlaying() {

            }

            @Override
            public void onStopPlaying() {
                finish();
            }

            @Override
            public void onError(ExoPlaybackException error) {

            }

        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        PlayerCore.getInstance().onPause();
    }


}
