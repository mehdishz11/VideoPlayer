package rasa.com.videoplayer;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

import rasa.com.videoview.CvideoView;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_GALLERY_VIDEO = 1;
    CvideoView cvideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cvideoView=findViewById(R.id.video_player);

//        cvideoView.setTitle("تست ویدئو");
//        cvideoView.loadVideo("http://79.175.155.143/jamak//Multimedia/636753023853348093.WhatsApp%20Video%202018-10-15%20at%2002.59.28.mp4");

//        video sample
//          cvideoView.load("http://clips.vorwaerts-gmbh.de/VfE_html5.mp4","تست ویدئو","http://www.templatesperfect.com/wp-content/uploads/2018/04/Learn-Codding-600x292.jpg");
//        audio sample

        cvideoView.load("http://79.175.155.143/malmal//Multimedia/636769427119262745.بابابزرگ من.mp3","تست ویدئو","http://79.175.155.143/malmal//Multimedia/Pic/636770363690626981.636631011035472617.photo_2018-05-28_10-33-58.jpg");

        Button btnChangeVieo=findViewById(R.id.btn_change_video);
        btnChangeVieo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                cvideoView.loadVideo("https://storage.tarafdari.com/contents/user276468/content-sound/1.sedaye_baroon.mp3","صدای بارون","https://storage.tarafdari.com/contents/user276468/content-sound/sattar_-_sedaaye_baroon.jpg");
                testVideoPlayer();
            }
        });
    }
    
    
    private void testVideoPlayer(){
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_TAKE_GALLERY_VIDEO);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
                Uri selectedImageUri = data.getData();
                cvideoView.load(selectedImageUri,"صدای بارون","https://storage.tarafdari.com/contents/user276468/content-sound/sattar_-_sedaaye_baroon.jpg");


                // OI FILE Manager
                String filemanagerstring = selectedImageUri.getPath();



                // MEDIA GALLERY
                String selectedImagePath = getPath(selectedImageUri);
                Log.d("VideoPlayer", "onActivityResult: "+selectedImagePath);
                if (selectedImagePath != null) {
                    File file=new File(selectedImagePath);
//                cvideoView.loadVideo(Uri.fromFile(file),"صدای بارون","https://storage.tarafdari.com/contents/user276468/content-sound/sattar_-_sedaaye_baroon.jpg");

                }
            }
        }
    }

    // UPDATED!
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if(cursor!=null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        else return null;
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