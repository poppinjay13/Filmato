package poppinjay13.projects.android.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import poppinjay13.projects.android.Config;
import poppinjay13.projects.android.R;
import poppinjay13.projects.android.adapters.GenresAdapter;
import poppinjay13.projects.android.model.movie.MovieResponse;
import poppinjay13.projects.android.rest.API;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ActivityMovieDetails extends AppCompatActivity {
    private final static String TAG = ActivityMovieDetails.class.getSimpleName();
    private Context context;
    private static Retrofit retrofit = null;
    private Toolbar toolbar;
    private TextView tv_title, tv_original_title;
    private WebView webView;
    private ImageView img_movie;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView genres_recycler_view;
    private AppBarLayout appBarLayout;
    private String title, overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        this.context = getApplicationContext();

        //Find views
        toolbar = findViewById(R.id.toolbar);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        coordinatorLayout = findViewById(R.id.main_content);
        progressBar = findViewById(R.id.progressBar);
        img_movie = findViewById(R.id.image);
        tv_title = findViewById(R.id.title);
        tv_original_title = findViewById(R.id.original_title);
        webView = findViewById(R.id.desc);
        appBarLayout = findViewById(R.id.appbar);
        genres_recycler_view = findViewById(R.id.genres_recycler_view);

        setSupportActionBar(toolbar);

        final androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();

        genres_recycler_view.setHasFixedSize(true);
        genres_recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        Intent iGet = getIntent();
        int movieId = iGet.getIntExtra("movieId", 0);


        API.movies().movieDetails(movieId, Config.API_KEY).enqueue(new retrofit2.Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                final MovieResponse movie = response.body();

                title = movie.getTitle();
                tv_original_title.setText(title);
                overview = movie.getOverview();
                if (actionBar != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setTitle(title);
                }

                appBarLayout.setExpanded(true);

                // hiding & showing the title when toolbar_payment expanded & collapsed
                appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    boolean isShow = false;
                    int scrollRange = -1;

                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        if (scrollRange == -1) {
                            scrollRange = appBarLayout.getTotalScrollRange();
                        }
                        if (scrollRange + verticalOffset == 0) {
                            collapsingToolbarLayout.setTitle(title);
                            isShow = true;
                        } else if (isShow) {
                            collapsingToolbarLayout.setTitle(title);
                            isShow = false;
                        }
                    }
                });
                collapsingToolbarLayout.setTitle(title);
                renderMovie(movie);
                progressBar.setVisibility(View.GONE);
                coordinatorLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Error loading!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void renderMovie(MovieResponse movie) {
        webView.setBackgroundColor(Color.parseColor("#ffffff"));
        webView.setFocusableInTouchMode(false);
        webView.setFocusable(false);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        WebSettings ws = webView.getSettings();
        Resources res = getResources();
        ws.setDefaultFontSize(15);
        String mimeType = "text/html; charset=UTF-8";
        String encoding = "utf-8";
        String text = "<html>"
                + "<head>"
                + "<style type=\"text/css\">body{color: #525252;}"
                + "</style></head>"
                + "<body><h3>Synopsis:</h3>"
                + movie.getOverview()
                + "</body>"
                + "</html>";

        webView.loadData(text, mimeType, encoding);

        Picasso.with(this)
                .load(Config.IMAGE_URL_BASE_PATH + movie.getBackdropPath())
                .placeholder(R.drawable.loader)
                .into(img_movie, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap bitmap = ((BitmapDrawable) img_movie.getDrawable()).getBitmap();
                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {

                            }
                        });
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(getApplicationContext(), "Error loading picture!", Toast.LENGTH_SHORT).show();
                    }
                });

        genres_recycler_view.setAdapter(
                new GenresAdapter(
                        movie.getGenres(),
                        R.layout.list_item_genre,
                        context
                )
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        } else {
            return super.onOptionsItemSelected(menuItem);
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void book(View view) {
        Intent intent = new Intent(ActivityMovieDetails.this, BookingActivity.class);
        intent.putExtra("Title",title);
        startActivity(intent);
    }
}
