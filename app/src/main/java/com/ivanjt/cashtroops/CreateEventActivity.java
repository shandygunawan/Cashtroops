package com.ivanjt.cashtroops;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ivanjt.cashtroops.model.Event;
import com.ivanjt.cashtroops.presenter.CreateEventPresenter;
import com.ivanjt.cashtroops.view.CreateEventView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateEventActivity extends AppCompatActivity implements CreateEventView {
    private EditText mDateEditText;
    private EditText mBudgetEditText;
    private EditText mNameEditText;
    private EditText mDescriptionEditText;
    private CreateEventPresenter presenter;
    private ImageView mDone;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    // Calendar for date's EditText
    final Calendar mCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get intent
        Intent intent = getIntent();
        final String groupId = intent.getStringExtra("groupId");

        /* Settings Date Picker for Date's EditText */
        mDateEditText = findViewById(R.id.et_event_create_date);
        mBudgetEditText = findViewById(R.id.et_event_create_budget);
        mNameEditText = findViewById(R.id.et_event_create_name);
        mDescriptionEditText = findViewById(R.id.et_event_create_desc);
        mDone = findViewById(R.id.iv_event_create_check);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateEditText();
            }
        };

        // Set Listener
        mDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateEventActivity.this, date, mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        presenter = new CreateEventPresenter(this);

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mEventRef = mDatabase.child(Event.PATH_NAME);
                String eventId = mEventRef.push().getKey();
                String title = mNameEditText.getText().toString();
                String desc = mDescriptionEditText.getText().toString();
                String date = mDateEditText.getText().toString();
                long budget = Long.parseLong(mBudgetEditText.getText().toString());

                Event event = new Event(eventId, groupId, date, budget, title, desc);
                presenter.createEvent(event);

                finish();
            }
        });
    }

    private void updateDateEditText() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mDateEditText.setText(sdf.format(mCalendar.getTime()));
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }
}
