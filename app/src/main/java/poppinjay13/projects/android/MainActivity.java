package poppinjay13.projects.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import poppinjay13.projects.android.customfonts.Button_Roboto_Medium;
import poppinjay13.projects.android.customfonts.MyTextView_Roboto_Regular;

public class MainActivity extends Activity {
    GoogleSignInClient mGoogleSignInClient;
    ArrayList<String> seats = new ArrayList<String>();
    final double base_price = 800.00;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //retrieve movie_data and set it in view
        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");
        TextView textView = findViewById(R.id.main_title);
        textView.setText(title);

        //load cinema spinner in the activity
        Spinner cinema_spinner = findViewById(R.id.spinner_movie);
        final String[] cinemas = {
                "Anga Sky Cinema",
                "Anga IMAX Kenya - 20th Century",
                "Garden City - Century Cinemax",
                "Century Cinemax Junction",
                "Nyali Cinemax",
                "Anga Mega Kisumu",
                "Prestige Cinemas",
                "Westgate Cinema",
                "Anga Diamond Plaza"
        };
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(MainActivity.this, R.layout.spinner_text, cinemas );
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        cinema_spinner.setAdapter(langAdapter);
        cinema_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.d("Spinner", "Cinema Selected:" +cinemas[position]);
                //refresh with new cinema info
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Log.d("Spinner", "No Cinema Selected");
            }

        });

        //load date spinner
        Spinner date_spinner = findViewById(R.id.spinner_date);
        final String[] dates = {
                "Sunday, 20",
                "Monday, 21",
                "Tuesday, 22",
                "Wednesday, 23",
                "Thursday, 24",
                "Friday, 25",
                "Saturday, 26"
        };
        ArrayAdapter<CharSequence> dateAdapter = new ArrayAdapter<CharSequence>(MainActivity.this, R.layout.spinner_text, dates );
        dateAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        date_spinner.setAdapter(dateAdapter);
        date_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.d("Spinner", "Date Selected:" +dates[position]);
                //refresh with new cinema info
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Log.d("Spinner", "No Date Selected");
            }

        });

        //load time spinner
        Spinner time_spinner = findViewById(R.id.spinner_time);
        final String[] times = {
                "07:30 AM",
                "09:30 AM",
                "11:30 AM",
                "01:30 PM",
                "03:30 PM",
                "05:30 PM",
                "07:30 PM",
                "09:30 PM",
                "11:30 PM"
        };
        ArrayAdapter<CharSequence> timeAdapter = new ArrayAdapter<CharSequence>(MainActivity.this, R.layout.spinner_text, times );
        timeAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        time_spinner.setAdapter(timeAdapter);
        time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.d("Spinner", "Time Selected:" +times[position]);
                //refresh with new cinema info
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Log.d("Spinner", "No Time Selected");
            }

        });
    }

    public void logout(View view) {
        Toast.makeText(getApplicationContext(), "Logging you out", Toast.LENGTH_SHORT).show();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }


    public void proceed(View view) {
        int size = seats.size();
        double final_sum =  base_price*size;
        Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
        intent.putExtra("Amount", final_sum);
        startActivity(intent);
        finish();
    }

    public void select(View view) {
        String name = view.getResources().getResourceEntryName(view.getId());
        int view_id = view.getId();
        ImageView iv=findViewById(view_id);
        String image = (String) iv.getTag();
        String available = "R.drawable.ic_availabe_seat1";
        String selected = "R.drawable.ic_selected_seat1";
        if (image.equals(available)) {
            iv.setImageResource(R.drawable.ic_selected_seat1);
            iv.setTag(selected);
            add(name);
        } else if (image.equals(selected)) {
            iv.setImageResource(R.drawable.ic_availabe_seat1);
            iv.setTag(available);
            delete(name);
        } else {
            Log.d("Seats", "This Should Really Not Happen");
        }
    }

    public void add(String id){
        seats.add(id);
        update();
    }
    public void delete(String id){
        seats.remove(id);
        update();
    }
    public void update(){
        int size = seats.size();
        double price = base_price*size;
        MyTextView_Roboto_Regular seats_no = findViewById(R.id.seats_selected);
        Button_Roboto_Medium btn_pay = findViewById(R.id.button_pay);
        seats_no.setText(size+" seats selected");
        btn_pay.setText("Pay Ksh "+price);
    }
}