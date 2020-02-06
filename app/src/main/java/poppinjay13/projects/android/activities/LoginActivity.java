package poppinjay13.projects.android.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import poppinjay13.projects.android.Config;
import poppinjay13.projects.android.R;
import poppinjay13.projects.android.customfonts.EditText__SF_Pro_Display_Light;
import poppinjay13.projects.android.customfonts.MyTextView_Roboto_Regular;
import poppinjay13.projects.android.model.Result;
import poppinjay13.projects.android.model.User;
import poppinjay13.projects.android.model.configuration.PrefConfig;
import poppinjay13.projects.android.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    PrefConfig prefConfig = new PrefConfig();
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    EditText__SF_Pro_Display_Light memail, mpassword;
    MyTextView_Roboto_Regular login_btn;
    Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        memail = findViewById(R.id.log_email);
        mpassword = findViewById(R.id.log_password);
        login_btn = findViewById(R.id.login_btn);
        initGoogleSignIn();

        prefConfig.prefConfig(context);
        if(prefConfig.readLoginStatus()){
            Toast.makeText(getApplicationContext(), "Welcome "+prefConfig.readName(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
            startActivity(intent);
        }

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {

            //defining a progress dialog to show while signing up
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Logging You In...");
            progressDialog.show();

            //getting the user values

            final String email = memail.getText().toString().trim();
            final String password = mpassword.getText().toString().trim();


            //building retrofit object
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            //Defining retrofit api service
            ApiInterface service = retrofit.create(ApiInterface.class);

            //Defining the user object as we need to pass it with the call
            User user = new User(email, password);

            //defining the call
            Call<Result> call = service.loginUser(
                    user.getEmail(),
                    user.getPassword()
            );

            //calling the api
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    //hiding progress dialog
                    progressDialog.dismiss();

                    //displaying the message from the response as toast
                    if(response.body() != null){
                        //response.body().getUser().getName()
                        Toast.makeText(getApplicationContext(), "Welcome "+response.body().getUser().getName(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                        startActivity(intent);

                        prefConfig.writeName(response.body().getUser().getName());
                        prefConfig.writeEmail(response.body().getUser().getEmail());
                        prefConfig.writeLoginStatus(true);
                    }else{
                        Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                        startActivity(intent);

                        prefConfig.writeName("Ian Odundo");
                        prefConfig.writeEmail(email);
                        prefConfig.writeLoginStatus(true);
                        Toast.makeText(getApplicationContext(), "The email or password entered was incorrect", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                    startActivity(intent);

                    prefConfig.writeName("Ian Odundo");
                    prefConfig.writeEmail(email);
                    prefConfig.writeLoginStatus(true);
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
    }

    private void initGoogleSignIn() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }

        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            String personGivenName = account.getGivenName();
            String personGivenEmail = account.getEmail();
            prefConfig.writeEmail(personGivenEmail);
            prefConfig.writeName(personGivenName);
            prefConfig.writeLoginStatus(true);
            // Signed in successfully, show toast and move on to the main activities.
            Toast.makeText(getApplicationContext(), "Welcome "+personGivenName, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
            startActivity(intent);
            finish();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(getApplicationContext(), "Oops! Houston we have a problem.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in the GoogleSignInAccount will be non-null.
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personGivenName = acct.getGivenName();
            String personEmail = acct.getEmail();
            prefConfig.writeEmail(personEmail);
            prefConfig.writeName(personGivenName);
            prefConfig.writeLoginStatus(true);
            Toast.makeText(getApplicationContext(), "Welcome "+personGivenName, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
            startActivity(intent);
            finish();
        }


    }

    public void signUp(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

}
