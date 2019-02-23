package com.ivanjt.cashtroops;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ivanjt.cashtroops.model.Group;
import com.ivanjt.cashtroops.model.Wallet;

public class GroupOverviewActivity extends AppCompatActivity {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private TextView mBalanceTextView;
    private TextView mNameTextView;
    private TextView mCashTagTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_overview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get intent
        final Intent intent = getIntent();
        final String groupId = intent.getStringExtra("groupId");

        //Get reference to view
        mNameTextView = findViewById(R.id.tv_overview_group_title);
        mCashTagTextView = findViewById(R.id.tv_overview_group_cashtag_value);
        mBalanceTextView = findViewById(R.id.tv_overview_group_balance_value);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(GroupOverviewActivity.this, CreateEventActivity.class);
                intent1.putExtra("groupId", groupId);
                startActivity(intent1);
            }
        });

        //Create db group ref
        mDatabase.child(Group.PATH_NAME).child(groupId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Group group = dataSnapshot.getValue(Group.class);

                        mCashTagTextView.setText("@" + group.getCashTag());
                        mNameTextView.setText(group.getName());

                        DatabaseReference walletRef = mDatabase.child(Wallet.PATH_NAME).child(group.getCashTag());
                        walletRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Wallet wallet = dataSnapshot.getValue(Wallet.class);
                                mBalanceTextView.setText("Rp " + String.valueOf(wallet.getAmount()));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
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

    public void initializeButtons(){
        // Get the resource from the XML file
        String[] overviewButtonResources = getResources().getStringArray(R.array.overview_buttons);
    }

}
