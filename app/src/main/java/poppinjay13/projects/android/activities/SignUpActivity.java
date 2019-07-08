package poppinjay13.projects.android.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

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

public class SignUpActivity extends AppCompatActivity {
    EditText__SF_Pro_Display_Light mname, memail, mpassword;
    MyTextView_Roboto_Regular sign_up;
    GoogleSignInClient mGoogleSignInClient;
    PrefConfig prefConfig = new PrefConfig();
    Context context = this;
    private static final int RC_SIGN_IN = 9001;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mname = findViewById(R.id.name);
        memail = findViewById(R.id.email);
        mpassword = findViewById(R.id.password_signup);
        sign_up = findViewById(R.id.sign_up_button);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate(mname) && validate(memail) && validate(mpassword)) {
                    signUp();
                }
            }
        });
    }

    private void signUp() {

        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing You Up...");
        progressDialog.show();

        //getting the user values

        final String name = mname.getText().toString().trim();
        final String email = memail.getText().toString().trim();
        String password = mpassword.getText().toString().trim();


        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiInterface service = retrofit.create(ApiInterface.class);

        //Defining the user object as we need to pass it with the call
        final User user = new User(name, email, password);

        //defining the call
        Call<Result> call = service.createUser(
                user.getName(),
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
                if(response.body() == null){
                    Toast.makeText(getApplicationContext(), "The Server appears to be offline. Please try again later", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this, NavigationActivity.class);
                    startActivity(intent);

                    prefConfig.prefConfig(context);
                    prefConfig.writeName(name);
                    prefConfig.writeEmail(email);
                    prefConfig.writeLoginStatus(true);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validate(EditText editText) {
        // check the lenght of the enter data in EditText and give error if its empty
        if (editText.getText().toString().trim().length() > 0) {
            return true; // returs true if field is not empty
        }
        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }

    public void login(View view) {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}