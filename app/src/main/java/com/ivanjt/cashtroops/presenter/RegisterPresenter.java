package com.ivanjt.cashtroops.presenter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ivanjt.cashtroops.MainActivity;
import com.ivanjt.cashtroops.model.User;
import com.ivanjt.cashtroops.model.Wallet;
import com.ivanjt.cashtroops.view.RegisterView;

public class RegisterPresenter {
    private RegisterView view;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public RegisterPresenter(RegisterView registerView, FirebaseAuth auth) {
        mAuth = auth;
        view = registerView;
    }

    public void register(final String name, final long phoneNumber, String email, String password, final Activity activity) {
        if (email.isEmpty()) {
            view.showToastMessage("Enter your email address");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            view.showToastMessage("Enter your password");
            return;
        }

        if (password.length() < 6) {
            view.showErrorMessage();
            return;
        }

        view.showProgressBar();

        //register user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            view.showToastMessage("Sign up successfully!");

                            String cashTag = mDatabase.child(Wallet.PATH_NAME).push().getKey();
                            String id = task.getResult().getUser().getUid();

                            Wallet wallet = new Wallet(cashTag, 0);
                            User user = new User(id, name, cashTag, phoneNumber);

                            mDatabase.child(User.PATH_NAME).child(id).setValue(user);
                            mDatabase.child(Wallet.PATH_NAME).child(cashTag).setValue(wallet);

                            activity.startActivity(new Intent(activity, MainActivity.class));
                            activity.finish();
                        } else {
                            view.showToastMessage("Unable to sign up");
                        }
                        view.hideProgressBar();
                    }
                });
    }
}
