package com.ivanjt.cashtroops;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ivanjt.cashtroops.adapter.HistoryAdapter;
import com.ivanjt.cashtroops.model.Transfer;
import com.ivanjt.cashtroops.presenter.HistoryPresenter;
import com.ivanjt.cashtroops.view.HistoryView;

import java.util.ArrayList;

public class GroupHistoryActivity extends AppCompatActivity implements HistoryView {
    private RecyclerView mRecyclerView;
    private HistoryPresenter presenter;
    private HistoryAdapter adapter;
    private ArrayList<Transfer> transfers = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_history);

        //Set reference to view
        Toolbar toolbar = findViewById(R.id.toolbar);

        //Set toolbar
        setSupportActionBar(toolbar);

        //Get reference to view
        mRecyclerView = findViewById(R.id.rv_history);

        //Get actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Get intent
        Intent intent = getIntent();
        String groupId = intent.getStringExtra("groupId");

        adapter = new HistoryAdapter(transfers, this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Get presenter
        presenter = new HistoryPresenter(this);
        presenter.getHistoryList(groupId);
    }

    @Override
    public void showHistoryList(ArrayList<Transfer> history) {
        transfers.clear();
        transfers.addAll(history);
        adapter.notifyDataSetChanged();
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
