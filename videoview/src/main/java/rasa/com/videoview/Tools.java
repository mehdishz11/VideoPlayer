package rasa.com.videoview;

import android.net.Uri;

public class Tools {
    private static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%20";

    public static String encodeUrl(String url){
        return Uri.encode(url, ALLOWED_URI_CHARS);
    }
}
