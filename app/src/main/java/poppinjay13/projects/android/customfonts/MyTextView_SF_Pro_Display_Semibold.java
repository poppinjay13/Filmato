package poppinjay13.projects.android.customfonts;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class MyTextView_SF_Pro_Display_Semibold extends androidx.appcompat.widget.AppCompatTextView {

    public MyTextView_SF_Pro_Display_Semibold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextView_SF_Pro_Display_Semibold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView_SF_Pro_Display_Semibold(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/SF-Pro-Display-Semibold.otf");
            setTypeface(tf);
        }
    }

}