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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ivanjt.cashtroops.model.Group;
import com.ivanjt.cashtroops.model.User;
import com.ivanjt.cashtroops.model.Wallet;

public class GroupOverviewActivity extends AppCompatActivity {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private TextView mBalanceTextView;
    private TextView mNameTextView;
    private TextView mCashTagTextView;
    private ImageView mHistoryImageButton;
    private ImageView mEventImageButton;
    private ImageView mMemberImageButton;
    private String uid;
    private User user;
    public final static int QR_REQUEST = 0;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    InOutFragment depositFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_overview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get uid
        uid = mAuth.getUid();
        mDatabase.child(User.PATH_NAME).child(uid).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        user = dataSnapshot.getValue(User.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        //Get intent
        final Intent intent = getIntent();
        final String groupId = intent.getStringExtra("groupId");

        //Get reference to view
        mEventImageButton = findViewById(R.id.iv_overview_group_event);
        mNameTextView = findViewById(R.id.tv_overview_group_title);
        mCashTagTextView = findViewById(R.id.tv_overview_group_cashtag_value);
        mBalanceTextView = findViewById(R.id.tv_overview_group_balance_value);
        mHistoryImageButton = findViewById(R.id.iv_overview_group_history);
        mMemberImageButton = findViewById(R.id.iv_overview_group_member);

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

        mEventImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(GroupOverviewActivity.this, EventListActivity.class);
                intent1.putExtra("groupId", groupId);
                startActivity(intent1);
            }
        });

        mMemberImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(GroupOverviewActivity.this, MemberListActivity.class);
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

    public void initializeButtons() {
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

        // Get Group's Cashtag
        String groupCashtag = mCashTagTextView.getText().toString();
        String[] groupCashtagArray = groupCashtag.split("@");
        depositFragment = new InOutFragment();

        switch (view.getId()) {
            case R.id.iv_overview_group_qr:
                IntentIntegrator scanIntegrator = new IntentIntegrator(com.ivanjt.cashtroops.GroupOverviewActivity.this);
                scanIntegrator.setPrompt("Scan QR Cashtag");
                scanIntegrator.setBeepEnabled(true);
                scanIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                scanIntegrator.setCaptureActivity(CaptureActivityPortrait.class);
                scanIntegrator.setOrientationLocked(true);
                scanIntegrator.setBarcodeImageEnabled(true);
                scanIntegrator.initiateScan();
                break;
            case R.id.iv_overview_group_deposit:
                depositFragment.setType("deposit");
                depositFragment.setUserCashtag(user.getCashTag());
                depositFragment.setGroupCashtag(groupCashtagArray[1]);
                depositFragment.show(getSupportFragmentManager(), "deposit");

                break;
            case R.id.iv_overview_group_withdraw:
                depositFragment.setType("withdraw");
                depositFragment.setUserCashtag(user.getCashTag());
                depositFragment.setGroupCashtag(groupCashtagArray[1]);
                depositFragment.show(getSupportFragmentManager(), "withdraw");

                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String id = scanningResult.getContents();
        String groupCashtag = mCashTagTextView.getText().toString();
        String[] groupCashtagArray = groupCashtag.split("@");
        depositFragment = new InOutFragment();
        depositFragment.setType("QR");
        depositFragment.setUserCashtag(id);
        depositFragment.setGroupCashtag(groupCashtagArray[1]);
        depositFragment.show(getSupportFragmentManager(), "QR");

    }
}
