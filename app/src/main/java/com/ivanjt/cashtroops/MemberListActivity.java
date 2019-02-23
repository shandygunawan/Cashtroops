package com.ivanjt.cashtroops;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ivanjt.cashtroops.adapter.MemberAdapter;
import com.ivanjt.cashtroops.model.Group;
import com.ivanjt.cashtroops.model.User;

import java.util.ArrayList;
import java.util.Map;

public class MemberListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MemberAdapter adapter;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members);

        //Get intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("groupId");

        mRecyclerView = findViewById(R.id.rv_members);
        adapter = new MemberAdapter(this, users);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DatabaseReference groupRef = mDatabase.child(Group.PATH_NAME).child(id);
        groupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();

                Group group = dataSnapshot.getValue(Group.class);
                Map<String, Boolean> members = group.getMembers();

                for (String key : members.keySet()) {
                    DatabaseReference userRef = mDatabase.child(User.PATH_NAME).child(key);
                    userRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            users.add(user);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
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
