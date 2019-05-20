package poppinjay13.projects.android;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.TransactionType;

public class PaymentActivity extends AppCompatActivity {
    EditText editTextPhone;
    Button btnLipa;

    //declare daraja as a global variable
    Daraja daraja;
    String phonenumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        editTextPhone =findViewById(R.id.editText);
        btnLipa =findViewById(R.id.sendButton);
        btnLipa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get phone number
                phonenumber = editTextPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phonenumber)) {
                    Toast.makeText(PaymentActivity.this, "please insert phone number", Toast.LENGTH_LONG).show();
                    return;
                }
                LipaNaMpesa(phonenumber);
            }
        });
        //Init daraja
        daraja = Daraja.with("ioXxKqeAUkSyAMKnQQrTchGDM0RAtG5b", "QGtz94gODybXQm8q", new DarajaListener<AccessToken>() {
            @Override
            public void onResult(@NonNull AccessToken accessToken) {
                Log.i(PaymentActivity.this.getClass().getSimpleName(),accessToken.getAccess_token());
              //  Toast.makeText(PaymentActivity.this,"TOKEN :"+ accessToken.getAccess_token(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Log.e(PaymentActivity.this.getClass().getSimpleName(),error);

            }
        });
    }

    private void LipaNaMpesa(String phonenumber) {
//party A is the number sending the money.It has to be a valid safaricom phone number
// phonenumber is the mobile number to receive the stk pin prompt.The number can be the sam as partyA.
//BusinessShort code = PartyB
        final LNMExpress lnmExpress = new LNMExpress(
                "174379",
                "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",
                TransactionType.CustomerBuyGoodsOnline,
                "50",
                "254708374149",
                "174379",
                phonenumber,
                "http://mycallbackurl.com/checkout.php",
                "001ABC",
                "Goods Paymnet"
        );
        //actual method
        daraja.requestMPESAExpress(lnmExpress,
                new DarajaListener<LNMResult>() {
                    @Override
                    public void onResult(@NonNull LNMResult lnmResult) {
                        Log.i(PaymentActivity.this.getClass().getSimpleName(), lnmResult.ResponseDescription);
                    }

                    @Override
                    public void onError(String error) {
                        Log.i(PaymentActivity.this.getClass().getSimpleName(), error);
                    }
                });

    }
}
