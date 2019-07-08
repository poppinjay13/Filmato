package poppinjay13.projects.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import poppinjay13.projects.android.R;
import poppinjay13.projects.android.model.Ticket;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.TicketsViewHolder> {

    private List<Ticket> tickets;
    private int rowLayout;
    private Context context;

    public TicketsAdapter(List<Ticket> tickets, int rowLayout, Context context) {
        this.tickets = tickets;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    //A view holder inner class where we get reference to the views in the layout using their ID

    public static class TicketsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ticketsLayout;
        TextView movie;
        TextView cinema;
        TextView date;
        TextView time;

        public TicketsViewHolder(View v) {
            super(v);
            ticketsLayout = v.findViewById(R.id.ticket_layout);
            movie = v.findViewById(R.id.ticket_movie_name);
            cinema = v.findViewById(R.id.ticket_cinema_name);
            date = v.findViewById(R.id.ticket_movie_date);
            time = v.findViewById(R.id.ticket_movie_time);
        }
    }


    @Override
    public TicketsAdapter.TicketsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new TicketsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TicketsViewHolder holder, final int position) {
        holder.movie.setText(tickets.get(position).getMovie());
        holder.cinema.setText(tickets.get(position).getCinema());
        holder.date.setText(tickets.get(position).getDate());
        holder.time.setText(tickets.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }
}
