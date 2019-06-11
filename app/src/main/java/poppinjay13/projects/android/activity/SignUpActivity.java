package poppinjay13.projects.android.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import poppinjay13.projects.android.R;
import poppinjay13.projects.android.SignUpPOJO;
import poppinjay13.projects.android.customfonts.EditText__SF_Pro_Display_Light;
import poppinjay13.projects.android.customfonts.MyTextView_Roboto_Regular;

public class SignUpActivity extends AppCompatActivity {
    EditText__SF_Pro_Display_Light name, email, password;
    MyTextView_Roboto_Regular sign_up;
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    SignUpPOJO signUpResponsesData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password_signup);
        sign_up = findViewById(R.id.sign_up_button);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate(name) && validate(email) && validate(password)) {
                    signUp();
                }
            }
        });
    }

    private void signUp() {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        // Api is a class in which we define a method getClient() that returns the API Interface class object
        // registration is a POST request type method in which we are sending our field's data
        /*Api.getClient().registration(name.getText().toString().trim(),
                email.getText().toString().trim(),
                password.getText().toString().trim(),
                "email", new Callback<SignUpPOJO>() {
                    @Override
                    public void success(SignUpPOJO signUpResponse, Response response) {
                        // in this method we will get the response from API
                        progressDialog.dismiss(); //dismiss progress dialog
                        signUpResponsesData = signUpResponse;
                        // display the message getting from web api
                        Toast.makeText(SignUpActivity.this, signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        // if error occurs in network transaction then we can get the error in this method.
                        Toast.makeText(SignUpActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss(); //dismiss progress dialog

                    }
                });*/
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