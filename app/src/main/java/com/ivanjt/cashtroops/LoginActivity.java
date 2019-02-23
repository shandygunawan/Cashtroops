package com.ivanjt.cashtroops;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.ivanjt.cashtroops.presenter.LoginPresenter;
import com.ivanjt.cashtroops.view.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private EditText mEmailEditText, mPasswordEditText;
    private FirebaseAuth auth;
    private ProgressBar mProgressBar;
    private TextView mRegisterTextView;
    private Button mLoginButton;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailEditText = findViewById(R.id.et_email_address);
        mPasswordEditText = findViewById(R.id.et_password);
        mProgressBar = findViewById(R.id.progressBar);
        mRegisterTextView = findViewById(R.id.tv_register);
        mLoginButton = findViewById(R.id.bt_login);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        //Initialize presenter;
        presenter = new LoginPresenter(this, auth);

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        mRegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.login(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString(), LoginActivity.this);
            }
        });
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage() {
        mPasswordEditText.setError(getString(R.string.minimum_password));
    }
}
