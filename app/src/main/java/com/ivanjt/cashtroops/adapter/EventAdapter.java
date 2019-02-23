package com.ivanjt.cashtroops.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivanjt.cashtroops.R;
import com.ivanjt.cashtroops.model.Event;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private Context context;
    private ArrayList<Event> events;

    public EventAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event_list, viewGroup, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int i) {
        holder.bind(events.get(i));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mBudgetTextView;

        EventViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.tv_event_list_title);
            mBudgetTextView = itemView.findViewById(R.id.tv_event_list_budget_target);
            mDateTextView = itemView.findViewById(R.id.tv_event_list_date);
        }

        void bind(Event event) {
            String[] str = event.getDate().split("/");

            mTitleTextView.setText(event.getTitle());
            mDateTextView.setText(str[1] + "\n/" + str[0]);
            mBudgetTextView.setText("Target budget: Rp " + String.valueOf(event.getTargetAmount()));
        }
    }
}
