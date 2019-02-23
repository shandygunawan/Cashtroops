package com.ivanjt.cashtroops;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.ivanjt.cashtroops.presenter.TransferPresenter;

/**
 * Fragment for inout dialog
 */
public class InOutFragment extends DialogFragment {

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
        final EditText et_amount = mDialogView.findViewById(R.id.et_dialog_inout_amount);

        builder.setView(mDialogView);

        builder.setPositiveButton("Transfer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // add transfer here
//                dialog.cancel();
                auth = FirebaseAuth.getInstance();
                TransferPresenter presenter = new TransferPresenter(auth);

                Integer amount = Integer.valueOf(et_amount.getText().toString());

                if(mType.equals("deposit")){
                    Log.d("InOutFragment", amount.toString());
                    presenter.transfer(mUserCashtag, mGroupCashtag, amount);
                }
                else {
                    presenter.transfer(mGroupCashtag, mUserCashtag, amount);
                }

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

}
