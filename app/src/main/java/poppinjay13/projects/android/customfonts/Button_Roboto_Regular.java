package poppinjay13.projects.android.customfonts;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;


public class Button_Roboto_Regular extends android.support.v7.widget.AppCompatButton {

    public Button_Roboto_Regular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Button_Roboto_Regular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Button_Roboto_Regular(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");
            setTypeface(tf);
        }
    }
}
