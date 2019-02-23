package com.ivanjt.cashtroops;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ivanjt.cashtroops.adapter.EventAdapter;
import com.ivanjt.cashtroops.model.Event;

import java.util.ArrayList;

public class EventListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private EventAdapter adapter;
    private ArrayList<Event> events = new ArrayList<>();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        //Get action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Event List");
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Get intent
        Intent intent = getIntent();
        final String groupId = intent.getStringExtra("groupId");

        //reference to view
        mRecyclerView = findViewById(R.id.rv_event_list);
        adapter = new EventAdapter(this, events);

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Get events for group
        DatabaseReference eventsRef = mDatabase.child(Event.PATH_NAME);
        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                events.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event event = snapshot.getValue(Event.class);

                    if (event.getGroupId().equals(groupId)) {
                        events.add(event);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
