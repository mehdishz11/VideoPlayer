package rasa.com.videoview;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class CvideoView extends RelativeLayout {

    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    private String title="";

    public CvideoView(Context context) {
        super(context);
        init();
    }

    public CvideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CvideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.rel_video_player, this);

    }

    private void attachVideoHere(){
        PlayerCore.getInstance(getContext()).attachToViewGroup(this);

        findViewById(R.id.exo_fullscreen_icon).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ImageView)findViewById(R.id.exo_fullscreen_icon)).setImageResource(R.drawable.ic_fullscreen_skrink);
                getContext().startActivity(new Intent(getContext(),FullScreenActivity.class).putExtra(EXTRA_TITLE,title));
            }
        });

        ((ImageView)findViewById(R.id.exo_fullscreen_icon)).setImageResource(R.drawable.ic_fullscreen_expand);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void loadVideo(String videoUrl,String title) {
        setTitle(title);
        PlayerCore.getInstance(getContext()).loadVideo(videoUrl);
    }

    public void loadVideo(String videoUrl) {
        PlayerCore.getInstance(getContext()).loadVideo(videoUrl);
    }


    public void onResume(VideoListener videoListener) {
        attachVideoHere();
        PlayerCore.getInstance(getContext()).onResume(videoListener);
    }

    public void onPause() {
        PlayerCore.getInstance(getContext()).onPause();
    }

    public void onDestroy() {
        PlayerCore.getInstance(getContext()).onDestroy();
    }
}
