package poppinjay13.projects.android.activities;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import poppinjay13.projects.android.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}
