package com.ivanjt.cashtroops;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.ivanjt.cashtroops.presenter.RegisterPresenter;
import com.ivanjt.cashtroops.view.RegisterView;

public class RegisterActivity extends AppCompatActivity implements RegisterView {
    private EditText mEmailEditText, mPasswordEditText, mPhoneNumberEditText, mNameEditText;
    private Button mRegisterButton;
    private TextView mSignInTextView;
    private ProgressBar mProgressBar;
    private FirebaseAuth auth;
    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mSignInTextView = findViewById(R.id.tv_login);
        mPhoneNumberEditText = findViewById(R.id.et_phone_number);
        mNameEditText = findViewById(R.id.et_name);
        mRegisterButton = findViewById(R.id.bt_register);
        mEmailEditText = findViewById(R.id.et_email_address);
        mPasswordEditText = findViewById(R.id.et_password);
        mProgressBar = findViewById(R.id.progressBar);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        //Initialize presenter
        presenter = new RegisterPresenter(this, auth);

        mSignInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.register(mNameEditText.getText().toString(), Long.parseLong(mPhoneNumberEditText.getText().toString()), mEmailEditText.getText().toString(), mPasswordEditText.getText().toString(), RegisterActivity.this);
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
