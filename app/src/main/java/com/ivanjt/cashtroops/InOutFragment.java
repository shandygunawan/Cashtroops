package com.ivanjt.cashtroops;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ivanjt.cashtroops.model.Group;
import com.ivanjt.cashtroops.presenter.TransferPresenter;

/**
 * Fragment for inout dialog
 */
public class InOutFragment extends DialogFragment {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private String mType; // "deposit" or "withdraw"
    private String mUserCashtag;
    private String mGroupCashtag;
    private FirebaseAuth auth;

    public InOutFragment() {
        // Default constructor (don't delete)
    }

    // SETTER
    public void setType(String type){
        mType = type;
    }

    public void setUserCashtag(String cashtag){
        mUserCashtag = cashtag;
    }

    public void setGroupCashtag(String cashtag){
        mGroupCashtag = cashtag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View mDialogView = inflater.inflate(R.layout.dialog_inout, null);
        TextView dialog_title = mDialogView.findViewById(R.id.tv_inout_type);
        final EditText et_amount = mDialogView.findViewById(R.id.et_dialog_inout_amount);

        if(mType.equals("deposit")){
            dialog_title.setText("Deposit to " + mGroupCashtag);
        }
        else if(mType.equals("withdraw")) {
            dialog_title.setText("Withdraw from " + mGroupCashtag);
        }
        else if(mType.equals("QR")){
            dialog_title.setText("Group payment to " + mUserCashtag);
        }
        builder.setView(mDialogView);



        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // add transfer here
//                dialog.cancel();
                auth = FirebaseAuth.getInstance();
                final TransferPresenter presenter = new TransferPresenter(auth);

                final Integer amount = Integer.valueOf(et_amount.getText().toString());

                if(mType.equals("deposit")){
                    Log.d("InOutFragment", amount.toString());
                    presenter.transfer(mUserCashtag, mGroupCashtag, amount);
                }
                else if(mType.equals("withdraw")) {
                    Log.d("InOutFragment", mGroupCashtag);
                    mDatabase.child(Group.PATH_NAME).child(mGroupCashtag).child("withdrawLimit")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(amount < (long)dataSnapshot.getValue()){
                                        presenter.transfer(mGroupCashtag, mUserCashtag, amount);
                                    }
                                    else{

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                }
                else if(mType.equals("QR")){
                    presenter.transfer(mGroupCashtag, mUserCashtag, amount);
                }
                dialog.cancel();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        AlertDialog dialog = builder.create();

        return dialog;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
            Log.d("ABSDIALOGFRAG", "Exception", e);
        }
    }

}
