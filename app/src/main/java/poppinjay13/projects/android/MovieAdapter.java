package poppinjay13.projects.android;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<MovieItem> modelClassList;

    // Constructor of the class
    public MovieAdapter(List<MovieItem> modelClassList) {
        this.modelClassList = modelClassList;
    }
    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int listPosition) {
        int resource = modelClassList.get(listPosition).getImageResource();
        String title = modelClassList.get(listPosition).getTitle();
        holder.setData(resource,title);
        
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return modelClassList.size();
    }



    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private ImageView imageView;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.movie_title);
            imageView = itemView.findViewById(R.id.movie_view);
        }

        private void setData(int resource, final String titleText){
            imageView.setImageResource(resource);
            title.setText(titleText);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Clicked", "Movie Selection: " + titleText);
                    Intent intent = new Intent(imageView.getContext(), MainActivity.class );
                    imageView.getContext().startActivity(intent);
                }
            });
        }
        @Override
        public void onClick(View view) {
            //implemented for OnClickListener
        }
    }
}