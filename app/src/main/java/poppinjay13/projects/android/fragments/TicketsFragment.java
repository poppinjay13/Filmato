package poppinjay13.projects.android.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import poppinjay13.projects.android.Config;
import poppinjay13.projects.android.R;
import poppinjay13.projects.android.adapters.TicketsAdapter;
import poppinjay13.projects.android.model.Result;
import poppinjay13.projects.android.model.Ticket;
import poppinjay13.projects.android.model.configuration.PrefConfig;
import poppinjay13.projects.android.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TicketsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ShimmerFrameLayout mshimmerFrameLayout;
    private Context context;
    private List<Ticket> tickets;
    private PrefConfig prefConfig = new PrefConfig();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tickets, container, false);
        recyclerView = view.findViewById(R.id.tickets_recycler);
        mshimmerFrameLayout = view.findViewById(R.id.tickets_shimmer);
        context = getContext();
        prefConfig.prefConfig(context);
        //loadTickets();
        return view;
    }

    private void loadTickets() {
        //getting the user values
        String email = prefConfig.readEmail();

        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiInterface service = retrofit.create(ApiInterface.class);

        //Defining the user object as we need to pass it with the call
        final Ticket ticket = new Ticket(email);

        //defining the call
        Call<Result> call = service.getTickets(
                ticket.getEmail()
        );

        //calling the api
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.e("TicketsResponse", call.request().toString());
                tickets = response.body().getTicket();
                recyclerView.setAdapter(new TicketsAdapter(tickets, R.layout.list_item_movie, getContext()));
                mshimmerFrameLayout.stopShimmer();
                mshimmerFrameLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
