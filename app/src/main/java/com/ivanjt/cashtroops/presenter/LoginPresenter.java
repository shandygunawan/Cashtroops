package com.ivanjt.cashtroops.presenter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ivanjt.cashtroops.MainActivity;
import com.ivanjt.cashtroops.R;
import com.ivanjt.cashtroops.view.LoginView;

public class LoginPresenter {
    private LoginView view;
    private FirebaseAuth mAuth;

    public LoginPresenter(LoginView loginView, FirebaseAuth auth) {
        view = loginView;
        mAuth = auth;
    }

    public void login(String email, final String password, final Activity activity) {
        if (email.isEmpty()) {
            view.showToastMessage("Enter your email address");
            return;
        }

        if (password.isEmpty()) {
            view.showToastMessage("Enter your password");
            return;
        }

        view.showProgressBar();

        //authenticate user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        view.hideProgressBar();
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                view.showErrorMessage();
                            } else {
                                view.showToastMessage(activity.getString(R.string.auth_failed));
                            }
                        } else {
                            Intent intent = new Intent(activity, MainActivity.class);
                            activity.startActivity(intent);
                            activity.finish();
                        }
                    }
                });
    }
}
