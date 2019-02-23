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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    private ImageView mHistoryImageButton;

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
        mHistoryImageButton = findViewById(R.id.iv_overview_group_history);

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

        initializeButtons();
        mHistoryImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(GroupOverviewActivity.this, GroupHistoryActivity.class);
                intent1.putExtra("groupId", groupId);
                startActivity(intent1);
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

    public int getImage(String imageName) {

        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());

        return drawableResourceId;
    }

    public void initializeButtons(){
        // Get the resource from the XML file
        String[] overviewButtonImages = getResources().getStringArray(R.array.overview_buttons_img);
        String[] overviewButtonResources = getResources().getStringArray(R.array.overview_buttons_resources);

        // Load images to ImageViews using Glide
        Glide.with(this).load(getImage(overviewButtonImages[0])).into((ImageView) findViewById(R.id.iv_overview_group_information));
        Glide.with(this).load(getImage(overviewButtonImages[1])).into((ImageView) findViewById(R.id.iv_overview_group_add_member));
        Glide.with(this).load(getImage(overviewButtonImages[2])).into((ImageView) findViewById(R.id.iv_overview_group_member));
        Glide.with(this).load(getImage(overviewButtonImages[3])).into((ImageView) findViewById(R.id.iv_overview_group_deposit));
        Glide.with(this).load(getImage(overviewButtonImages[4])).into((ImageView) findViewById(R.id.iv_overview_group_event));
        Glide.with(this).load(getImage(overviewButtonImages[5])).into((ImageView) findViewById(R.id.iv_overview_group_history));
        Glide.with(this).load(getImage(overviewButtonImages[6])).into((ImageView) findViewById(R.id.iv_overview_group_withdraw));
        Glide.with(this).load(getImage(overviewButtonImages[7])).into((ImageView) findViewById(R.id.iv_overview_group_qr));
        Glide.with(this).load(getImage(overviewButtonImages[8])).into((ImageView) findViewById(R.id.iv_overview_group_settings));

    }

    public void handleTransaction(View view) {
        switch (view.getId()) {
            case R.id.iv_overview_group_qr:

                break;
            case R.id.iv_overview_group_deposit:

                break;
            case R.id.iv_overview_group_withdraw:

                break;
        }
    }
}
