package com.ivanjt.cashtroops.view;

import com.ivanjt.cashtroops.model.Transfer;

import java.util.ArrayList;

public interface HistoryView {
    void showHistoryList(ArrayList<Transfer> history);
}
