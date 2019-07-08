package poppinjay13.projects.android.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.TransactionType;

import java.util.ArrayList;
import java.util.Arrays;

import poppinjay13.projects.android.Config;
import poppinjay13.projects.android.R;
import poppinjay13.projects.android.customfonts.EditText_Roboto_Regular;
import poppinjay13.projects.android.customfonts.MyTextView_Roboto_Medium;
import poppinjay13.projects.android.model.Payment;
import poppinjay13.projects.android.model.Result;
import poppinjay13.projects.android.model.Ticket;
import poppinjay13.projects.android.model.configuration.PrefConfig;
import poppinjay13.projects.android.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener{
    EditText_Roboto_Regular editphone;
    MyTextView_Roboto_Medium btnLipa;
    private LinearLayout mpesa, cards, mpesa_details, card_details;
    public ImageView rightmark1, rightmark2;
    PrefConfig prefConfig = new PrefConfig();
    Context context = this;
    ArrayList<String> seatsArr = new ArrayList<>();
    //declare daraja as a global variable
    Daraja daraja;
    String phonenumber, amount, cinema, date, time, movie, seats;
    int price;
    public static final String PREFS_NAME = "credentials";
    public static final String key = "phone_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__details);
        //on click
        prefConfig.prefConfig(context);
        Toolbar toolbar = findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Payment Activity");

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
        if (number == null) {
            editphone.setText(num_format);
        } else {

            editphone.setText(number);
        }
        editphone.setSelection(editphone.getText().length());

        //retrieve amount to be charged
        Intent intent = getIntent();
        movie = intent.getStringExtra("Movie");
        Log.d("Extra Movie", movie);
        cinema = intent.getStringExtra("Cinema");
        Log.d("Extra Cinema", cinema);
        date = intent.getStringExtra("Date");
        Log.d("Extra Date", date);
        time = intent.getStringExtra("Time");
        Log.d("Extra time", time);
        price = intent.getIntExtra("Amount", 0);
        Log.d("Extra amount", "" +price);
        amount = "" + price;
        Log.d("Extra Price", amount);
        seatsArr = intent.getStringArrayListExtra("Seats");
        String[] str = GetStringArray(seatsArr);
        seats = Arrays.toString(str);
        Log.d("Extra Seats", seats);

        btnLipa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get phone number
                phonenumber = editphone.getText().toString().trim();
                if (TextUtils.isEmpty(phonenumber)) {
                    Toast.makeText(PaymentActivity.this, "please enter phone number", Toast.LENGTH_LONG).show();
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

    private void LipaNaMpesa(final String phonenumber, final String amount) {
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
                        //logPayment(phonenumber,amount);
                        //createTicket(movie,cinema,date,time,price);
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

    private void createTicket(String cinema,String movie, String date, String time, int prices) {
        String email = prefConfig.readEmail();
        String price = Integer.toString(prices);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiInterface service = retrofit.create(ApiInterface.class);

        //Defining the ticket object as we need to pass it with the call
        final Ticket ticket = new Ticket(email,movie,cinema,date,time,seats,price);

        //defining the call
        Call<Result> call = service.logTicket(
                ticket.getEmail(),
                ticket.getMovie(),
                ticket.getCinema(),
                ticket.getDate(),
                ticket.getTime(),
                ticket.getSeats(),
                ticket.getPrice()
        );

        //calling the api
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //displaying the message from the response as toast
                Toast.makeText(getApplicationContext(),"The ticket has been reserved", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(PaymentActivity.this, NavigationActivity.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "The payment transaction was unsuccessful", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void logPayment(String phonenumber, String amount) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiInterface service = retrofit.create(ApiInterface.class);

        //Defining the payment object as we need to pass it with the call
        final Payment payment = new Payment(phonenumber, amount);

        //defining the call
        Call<Result> call = service.logPayment(
                payment.getPhone(),
                payment.getAmount()
        );

        //calling the api
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //displaying the message from the response as toast
                Toast.makeText(getApplicationContext(),"The transaction is being processed", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(PaymentActivity.this, NavigationActivity.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "The payment transaction was unsuccessful", Toast.LENGTH_LONG).show();
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
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    // Function to convert ArrayList<String> to String[]
    public static String[] GetStringArray(ArrayList<String> arr)
    {

        // declaration and initialise String Array
        String str[] = new String[arr.size()];

        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {

            // Assign each value to String array
            str[j] = arr.get(j);
        }

        return str;
    }
}
