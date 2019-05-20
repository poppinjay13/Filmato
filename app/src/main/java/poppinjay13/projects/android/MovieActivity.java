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
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        List<MovieItem> modelClassList = new ArrayList<>();
        modelClassList.add(new MovieItem(R.drawable.pic1,"Deadpool"));
        modelClassList.add(new MovieItem(R.drawable.pic2,"Justice League"));
        modelClassList.add(new MovieItem(R.drawable.pic3,"Pacific Rim Uprising"));
        modelClassList.add(new MovieItem(R.drawable.pic4,"Aquaman"));
        modelClassList.add(new MovieItem(R.drawable.pic5,"Mad Max Fury Road"));
        modelClassList.add(new MovieItem(R.drawable.pic6,"Black Panther"));
        modelClassList.add(new MovieItem(R.drawable.pic7,"Captain Marvel"));

        RecyclerView.Adapter adapter = new MovieAdapter(modelClassList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
