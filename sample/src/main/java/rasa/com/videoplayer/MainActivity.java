package rasa.com.videoplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import rasa.com.videoview.CvideoView;

public class MainActivity extends AppCompatActivity {

    CvideoView cvideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cvideoView=findViewById(R.id.video_player);

//        cvideoView.setTitle("تست ویدئو");
//        cvideoView.loadVideo("http://79.175.155.143/jamak//Multimedia/636753023853348093.WhatsApp%20Video%202018-10-15%20at%2002.59.28.mp4");

        cvideoView.loadVideo("http://clips.vorwaerts-gmbh.de/VfE_html5.mp4","تست ویدئو","http://www.templatesperfect.com/wp-content/uploads/2018/04/Learn-Codding-600x292.jpg");

        Button btnChangeVieo=findViewById(R.id.btn_change_video);
        btnChangeVieo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cvideoView.loadVideo("https://storage.tarafdari.com/contents/user276468/content-sound/1.sedaye_baroon.mp3","صدای بارون","https://storage.tarafdari.com/contents/user276468/content-sound/sattar_-_sedaaye_baroon.jpg");

            }
        });
    }



    @Override
    protected void onPause() {
        super.onPause();
        cvideoView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cvideoView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cvideoView.onResume(null);
    }
}