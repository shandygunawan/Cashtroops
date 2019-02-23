package com.ivanjt.cashtroops.presenter;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ivanjt.cashtroops.model.Transfer;
import com.ivanjt.cashtroops.model.Wallet;
import com.ivanjt.cashtroops.view.TransferView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransferPresenter {
    private TransferView transferView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public TransferPresenter(TransferView view, FirebaseAuth auth) {
        mAuth = auth;
        transferView = view;
    }

    public void transfer(final String from, final String to, final int amount){
        DatabaseReference mTransferRef = mDatabase.child(Transfer.PATH_NAME);
        final DatabaseReference mWalletRef = mDatabase.child(Wallet.PATH_NAME);
        String id = mTransferRef.push().getKey();
        Format f = new SimpleDateFormat("MM/dd/yy");
        String strDate = f.format(new Date());
        Transfer transfer = new Transfer(id, from , to, amount, strDate);
        mWalletRef.child(from).child("amount")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int senderAmount = (int) dataSnapshot.getValue();
                        mWalletRef.child(from).child("amount").setValue(senderAmount - amount);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        mWalletRef.child(to).child("amount")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int receiverAmount = (int) dataSnapshot.getValue();
                        mWalletRef.child(to).child("amount").setValue(receiverAmount + amount);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        mTransferRef.child(id).setValue(transfer);
    }
}