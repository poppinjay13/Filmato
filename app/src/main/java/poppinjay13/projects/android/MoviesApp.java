package poppinjay13.projects.android;

import android.app.Application;
import android.os.SystemClock;
import android.util.Log;

public class MoviesApp extends Application {
    private static final String TAG = "Filmato";

    @Override
    public void onCreate() {
        super.onCreate();
        long startTime = SystemClock.elapsedRealtime();

        //initialization code here...

        long elapsed = SystemClock.elapsedRealtime() - startTime;
        Log.i(TAG, "Initialized in " + elapsed + " ms");
    }
}
