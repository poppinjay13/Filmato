package poppinjay13.projects.android.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import poppinjay13.projects.android.Config;
import poppinjay13.projects.android.R;
import poppinjay13.projects.android.fragments.MovieFragment;
import poppinjay13.projects.android.fragments.SettingsFragment;
import poppinjay13.projects.android.fragments.TicketsFragment;
import poppinjay13.projects.android.model.configuration.PrefConfig;

public class NavigationActivity extends AppCompatActivity {
    private TextView mTextMessage;
    PrefConfig prefConfig = new PrefConfig();
    Context context = this;
    GoogleSignInClient mGoogleSignInClient;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new MovieFragment();
                    return loadFragment(fragment);
                case R.id.navigation_tickets:
                    fragment = new TicketsFragment();
                    return loadFragment(fragment);
                case R.id.navigation_profile:
                    fragment = new SettingsFragment();
                    return loadFragment(fragment);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        prefConfig.prefConfig(context);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        loadFragment(new MovieFragment());
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.movie_frame, fragment)
                    .commit();
            return true;
        }
        return  false;
    }


    public void logout(View view) {
        Toast.makeText(getApplicationContext(), "Logging you out", Toast.LENGTH_SHORT).show();

        prefConfig.logOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

}
