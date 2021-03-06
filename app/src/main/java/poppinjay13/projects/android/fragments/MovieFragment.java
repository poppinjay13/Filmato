package poppinjay13.projects.android.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import poppinjay13.projects.android.Config;
import poppinjay13.projects.android.R;
import poppinjay13.projects.android.activities.ActivityMovieDetails;
import poppinjay13.projects.android.adapters.MoviesAdapter;
import poppinjay13.projects.android.model.movies.TicketItem;
import poppinjay13.projects.android.model.movies.MoviesResponse;
import poppinjay13.projects.android.rest.API;
import retrofit2.Call;
import retrofit2.Response;


public class MovieFragment extends Fragment {
    private static final String TAG = "MovieActivity";

    private Context context;
    private RecyclerView recyclerView = null;
    private List<TicketItem> movies;
    private ShimmerFrameLayout mshimmerFrameLayout;
    private SwipeRefreshLayout swiper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_recycler, container, false);
        //Find views
        recyclerView = view.findViewById(R.id.recycler_view);
        mshimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        swiper = view.findViewById(R.id.swipe);
        context = getContext();

        //setting an setOnRefreshListener on the SwipeDownLayout
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mshimmerFrameLayout.setVisibility(View.VISIBLE);
                mshimmerFrameLayout.startShimmer();
                loadMovies();
            }
        });
        loadMovies();
        return view;
    }

    private void loadMovies() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addOnItemTouchListener(new MovieFragment.RecyclerTouchListener(context, recyclerView, new MovieFragment.ClickListener() {
            @Override
            public void onClick(View view, final int position) {

                Toast.makeText(getActivity(), "Opening Movie ... ", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(context, ActivityMovieDetails.class);
                        intent.putExtra("movieId", movies.get(position).getId());
                        startActivity(intent);
                    }
                }, 400);

            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity(), "Movie: "+movies.get(position).getTitle(), Toast.LENGTH_SHORT).show();

            }
        }));

        API.movies().nowPlaying(Config.API_KEY).enqueue(new retrofit2.Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                Log.e(TAG, call.request().toString());
                movies = response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getContext()));
                mshimmerFrameLayout.stopShimmer();
                mshimmerFrameLayout.setVisibility(View.GONE);
                swiper.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
                Toast.makeText(getActivity(), "Please check your internet connection and reload", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MovieFragment.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MovieFragment.ClickListener clickListener) {

            this.clickListener = clickListener;

            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

}
