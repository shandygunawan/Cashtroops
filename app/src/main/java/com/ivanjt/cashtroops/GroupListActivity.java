
package com.ivanjt.cashtroops;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ivanjt.cashtroops.adapter.GroupAdapter;
import com.ivanjt.cashtroops.model.Group;
import com.ivanjt.cashtroops.model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class GroupListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private GroupAdapter mAdapter;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser;
    private ArrayList groupList = new ArrayList();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        //Set reference to view
        mRecyclerView = findViewById(R.id.rv_group_list);
        Toolbar toolbar = findViewById(R.id.toolbar);

        //Set toolbar
        setSupportActionBar(toolbar);

        //Get current user
        mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

        //Set adapter
        mAdapter = new GroupAdapter(this, groupList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GroupListActivity.this, GroupCreateActivity.class));
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get reference to database
        DatabaseReference userRef = mDatabase.child(User.PATH_NAME).child(uid);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                HashMap<String, Boolean> groups = (HashMap<String, Boolean>) user.getGroups();

                groupList.clear();
                if (groups != null) {
                    for (String groupId : groups.keySet()) {
                        if (groups.get(groupId)) {
                            DatabaseReference groupRef = mDatabase.child(Group.PATH_NAME).child(groupId);
                            groupRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Group group = dataSnapshot.getValue(Group.class);
                                    groupList.add(group);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
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
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
