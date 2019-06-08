package poppinjay13.projects.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.TransactionType;

import poppinjay13.projects.android.customfonts.EditText_Roboto_Regular;
import poppinjay13.projects.android.customfonts.MyTextView_Roboto_Medium;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener{
    EditText_Roboto_Regular editphone;
    MyTextView_Roboto_Medium btnLipa;
    private LinearLayout mpesa, cards, mpesa_details, card_details;
    public ImageView rightmark1, rightmark2;

    //declare daraja as a global variable
    Daraja daraja;
    String phonenumber;
    String amount;
    public static final String PREFS_NAME = "credentials";
    public static final String key = "phone_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__details);
        //on click
        mpesa = findViewById(R.id.mpesa);
        cards = findViewById(R.id.cards);
        rightmark1 = findViewById(R.id.rightmark1);
        rightmark2 = findViewById(R.id.rightmark2);
        mpesa_details = findViewById(R.id.mpesa_details);
        card_details = findViewById(R.id.card_details);
        card_details.setVisibility(View.GONE);

        mpesa.setOnClickListener(this);
        cards.setOnClickListener(this);

        //pay by mpesa set-up
        editphone = findViewById(R.id.editPhoneNumber);
        btnLipa = findViewById(R.id.btnLipa);

        final SharedPreferences creds = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String number = creds.getString(key, "");
        String num_format = "2547";
        if (number.isEmpty()) {
            editphone.setText(num_format);
        } else {

            editphone.setText(number);
        }
        editphone.setSelection(editphone.getText().length());

        //retrieve amount to be charged
        Intent intent = getIntent();
        int price = intent.getIntExtra("Amount", 0);
        amount = "" + price;
        Log.d("Price", amount);
        btnLipa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get phone number
                phonenumber = editphone.getText().toString().trim();
                if (TextUtils.isEmpty(phonenumber)) {
                    Toast.makeText(PaymentActivity.this, "please insert phone number", Toast.LENGTH_LONG).show();
                    return;
                }
                CheckBox checkBox = findViewById(R.id.contactSave);
                if (checkBox.isChecked()) {
                    // Writing data to SharedPreferences
                    SharedPreferences.Editor editor = creds.edit();
                    editor.putString(key, phonenumber);
                    editor.apply();
                }
                LipaNaMpesa(phonenumber, amount);
            }
        });
        //Init daraja
        daraja = Daraja.with("ioXxKqeAUkSyAMKnQQrTchGDM0RAtG5b", "QGtz94gODybXQm8q", new DarajaListener<AccessToken>() {
            @Override
            public void onResult(@NonNull AccessToken accessToken) {
                Log.i(PaymentActivity.this.getClass().getSimpleName(), accessToken.getAccess_token());
            }

            @Override
            public void onError(String error) {
                Log.e(PaymentActivity.this.getClass().getSimpleName(), error);

            }
        });

        //pay by card data

    }

    private void LipaNaMpesa(String phonenumber, String amount) {
//party A is the number sending the money.It has to be a valid safaricom phone number
// phonenumber is the mobile number to receive the stk pin prompt.The number can be the same as partyA.
//BusinessShort code = PartyB
        final LNMExpress lnmExpress = new LNMExpress(
                "174379",
                "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",
                TransactionType.CustomerBuyGoodsOnline,
                amount,
                "254719653520",
                "174379",
                phonenumber,
                "http://mycallbackurl.com/checkout.php",
                "001ABC",
                "Tickets Purchase"
        );
        //actual method
        daraja.requestMPESAExpress(lnmExpress,
                new DarajaListener<LNMResult>() {
                    @Override
                    public void onResult(@NonNull LNMResult lnmResult) {
                        Log.i(PaymentActivity.this.getClass().getSimpleName(), lnmResult.ResponseDescription);
                        Toast.makeText(getApplicationContext(), "M-PESA Direct Payment Starting", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String error) {
                        Log.i(PaymentActivity.this.getClass().getSimpleName(), error);
                        Toast.makeText(getApplicationContext(),
                                "Please Check Phone Number Format and Device Internet Connection then try again",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.mpesa:

                rightmark1.setImageResource(R.drawable.ic_right);
                rightmark2.setImageResource(R.drawable.ic_round);
                card_details.setVisibility(View.GONE);
                mpesa_details.setVisibility(View.VISIBLE);
                break;

            case R.id.cards:

                rightmark1.setImageResource(R.drawable.ic_round);
                rightmark2.setImageResource(R.drawable.ic_right);
                mpesa_details.setVisibility(View.GONE);
                card_details.setVisibility(View.VISIBLE);
                break;
        }
    }
}
