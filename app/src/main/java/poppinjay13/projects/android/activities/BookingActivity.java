package poppinjay13.projects.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

import poppinjay13.projects.android.R;
import poppinjay13.projects.android.customfonts1.Button_Roboto_Medium;
import poppinjay13.projects.android.customfonts1.MyTextView_Roboto_Regular;

public class BookingActivity extends Activity {
    GoogleSignInClient mGoogleSignInClient;
    ArrayList<String> seats = new ArrayList<String>();
    final int base_price = 800;
    String cinema,date,time,movie;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //retrieve movie_data and set it in view
        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");
        TextView textView = findViewById(R.id.main_title);
        textView.setText(title);
        movie = title;

        //load cinema spinner in the activities
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
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(BookingActivity.this, R.layout.spinner_text, cinemas );
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        cinema_spinner.setAdapter(langAdapter);
        cinema_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.d("Spinner Cinema", "Cinema Selected:" +cinemas[position]);
                //refresh with new cinema info
                refreshSeats(cinemas,position);
                cinema = cinemas[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Log.d("Spinner", "No Cinema Selected");
            }

        });

        //load date spinner
        Spinner date_spinner = findViewById(R.id.spinner_date);
        final String[] dates = {
                "8-7-2019",
                "9-7-2019",
                "10-7-2019",
                "11-7-2019",
                "12-7-2019",
                "13-7-2019",
                "14-7-2019"
        };
        ArrayAdapter<CharSequence> dateAdapter = new ArrayAdapter<CharSequence>(BookingActivity.this, R.layout.spinner_text, dates );
        dateAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        date_spinner.setAdapter(dateAdapter);
        date_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.d("Spinner Date", "Date Selected:" +dates[position]);
                //refresh with new date info
                date = dates[position];
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
        ArrayAdapter<CharSequence> timeAdapter = new ArrayAdapter<CharSequence>(BookingActivity.this, R.layout.spinner_text, times );
        timeAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        time_spinner.setAdapter(timeAdapter);
        time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.d("Spinner Time", "Time Selected:" +times[position]);
                //refresh with new time info
                time = times[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Log.d("Spinner", "No Time Selected");
            }

        });
    }

    private void refreshSeats(String[] cinemas, int position) {
        //Intent intent = new Intent(BookingActivity.this, BookingActivity.class);
        //startActivity(intent);
        //cinema_spinner.setSelection(position);
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
                        Intent intent = new Intent(BookingActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }


    public void proceed(View view) {
        int size = seats.size();
        int final_sum =  base_price*size;
        if(size == 0){
            Toast.makeText(BookingActivity.this, "No seats have been selected", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(BookingActivity.this, PaymentActivity.class);
            intent.putExtra("Movie", movie);
            intent.putExtra("Cinema", cinema);
            intent.putExtra("Date", date);
            intent.putExtra("Time", time);
            intent.putExtra("Seats", seats);
            intent.putExtra("Amount", final_sum);
            intent.putExtra("Seats",seats);
            startActivity(intent);
            finish();
        }
    }

    private static ArrayList<View> getViewsByTag(ViewGroup root, String tag){
        ArrayList<View> views = new ArrayList<View>();
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(getViewsByTag((ViewGroup) child, tag));
            }

            final Object tagObj = child.getTag();
            if (tagObj != null && tagObj.equals(tag)) {
                views.add(child);
                //child.setBackgroundResource(R.id.ic_taken_seat1);
            }

        }
        return views;
    }

    public void select(View view) {
        String name = view.getResources().getResourceEntryName(view.getId());
        int view_id = view.getId();
        ImageView iv=findViewById(view_id);
        String image = (String) iv.getTag();
        String available = "R.drawable.ic_available_seat1";
        String selected = "R.drawable.ic_selected_seat1";
        if (image.equals(available)) {
            iv.setImageResource(R.drawable.ic_selected_seat1);
            iv.setTag(selected);
            add(name);
        } else if (image.equals(selected)) {
            iv.setImageResource(R.drawable.ic_available_seat1);
            iv.setTag(available);
            delete(name);
        } else {
            Log.d("Seats Error", "This Should Really Not Happen");
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
        int price = base_price*size;
        MyTextView_Roboto_Regular seats_no = findViewById(R.id.seats_selected);
        Button_Roboto_Medium btn_pay = findViewById(R.id.button_pay);
        seats_no.setText(size+" seats selected");
        btn_pay.setText("Pay Ksh "+price);
    }
}