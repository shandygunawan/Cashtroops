package com.ivanjt.cashtroops.presenter;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ivanjt.cashtroops.model.Transfer;
import com.ivanjt.cashtroops.view.HistoryView;

import java.util.ArrayList;
import java.util.Map;

public class HistoryPresenter {
    private HistoryView view;
    private ArrayList<Transfer> history = new ArrayList<>();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public HistoryPresenter(HistoryView view) {
        this.view = view;
    }

    public void getHistoryList(final String groupId) {
        history.clear();

        DatabaseReference transRef = mDatabase.child(Transfer.PATH_NAME);
        transRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Transfer> list = (Map<String, Transfer>) dataSnapshot.getValue();

                if (list != null) {
                    for (String key : list.keySet()) {
                        Transfer transfer = list.get(key);
                        if (transfer.getFrom().equals(groupId) || transfer.getTo().equals(groupId)) {
                            history.add(transfer);
                        }
                    }
                }

                view.showHistoryList(history);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
