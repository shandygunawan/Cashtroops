package com.ivanjt.cashtroops.view;

public interface LoginView {
    void showProgressBar();

    void hideProgressBar();

    void showToastMessage(String message);

    void showErrorMessage();
}
