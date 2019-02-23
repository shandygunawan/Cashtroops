package com.ivanjt.cashtroops;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ivanjt.cashtroops.model.Group;
import com.ivanjt.cashtroops.model.User;
import com.ivanjt.cashtroops.model.Wallet;

public class GroupCreateActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser;
    private User user;
    private EditText mGroupNameEditText;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_create);

        //Get reference to view
        Toolbar toolbar = findViewById(R.id.toolbar);
        mGroupNameEditText = findViewById(R.id.et_group_name);

        //Set toolbar
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get current user
        mUser = mAuth.getCurrentUser();
        final String uid = mUser.getUid();

        //Get db reference
        final DatabaseReference mUserRef = mDatabase.child(User.PATH_NAME).child(uid);

        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference mGroupRef = mDatabase.child(Group.PATH_NAME);
        final DatabaseReference mWalletRef = mDatabase.child(Wallet.PATH_NAME);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupId = mGroupRef.push().getKey();
                String walletId = mWalletRef.push().getKey();

                String groupName = mGroupNameEditText.getText().toString();

                if (groupId != null && walletId != null) {
                    Group group = new Group(groupId, groupName, walletId, 15, uid);
                    Wallet wallet = new Wallet(walletId, 0);

                    group.addMember(uid);
                    user.addGroup(groupId);

                    mWalletRef.child(walletId).setValue(wallet);
                    mUserRef.setValue(user);
                    mGroupRef.child(groupId).setValue(group).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent = new Intent(GroupCreateActivity.this, GroupOverviewActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
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
