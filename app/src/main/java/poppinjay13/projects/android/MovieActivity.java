package poppinjay13.projects.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RecyclerView recyclerView;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        recyclerView = findViewById(R.id.movies_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        String description = "";
        int rating = 5;
        List<MovieItem> modelClassList = new ArrayList<>();
        modelClassList.add(new MovieItem(R.drawable.pic1,"Deadpool",description,rating));
        modelClassList.add(new MovieItem(R.drawable.pic2,"Justice League",description,rating));
        modelClassList.add(new MovieItem(R.drawable.pic3,"Pacific Rim Uprising",description,rating));
        modelClassList.add(new MovieItem(R.drawable.pic4,"Aquaman",description,rating));
        modelClassList.add(new MovieItem(R.drawable.pic5,"Mad Max Fury Road",description,rating));
        modelClassList.add(new MovieItem(R.drawable.pic6,"Black Panther",description,rating));
        modelClassList.add(new MovieItem(R.drawable.pic7,"Captain Marvel",description,rating));

        RecyclerView.Adapter adapter = new MovieAdapter(modelClassList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
