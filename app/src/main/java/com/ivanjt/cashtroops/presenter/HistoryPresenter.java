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
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Transfer transfer = snapshot.getValue(Transfer.class);

                    if (transfer.getFrom().equals(groupId)) {
                        transfer.setAmount(transfer.getAmount() * (-1));
                    }
                    history.add(transfer);
                }
                view.showHistoryList(history);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
