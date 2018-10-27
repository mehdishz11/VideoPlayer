package rasa.com.videoview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by AMiR Ehsan on 4/11/2017 AD.
 */

 class CustomTextView extends android.support.v7.widget.AppCompatTextView {
    public CustomTextView(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "VideoPlayer IRAN Sans.ttf");
        this.setTypeface(face);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "VideoPlayer IRAN Sans.ttf");
        this.setTypeface(face);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "VideoPlayer IRAN Sans.ttf");
        this.setTypeface(face);
    }

}
