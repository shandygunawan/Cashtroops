package com.ivanjt.cashtroops.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivanjt.cashtroops.R;
import com.ivanjt.cashtroops.model.Transfer;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private ArrayList<Transfer> transfers;
    private Context mContext;

    public HistoryAdapter(ArrayList<Transfer> transfers, Context mContext) {
        this.transfers = transfers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.historylist_item, viewGroup, false);
        return new HistoryAdapter.HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder historyViewHolder, int i) {
        historyViewHolder.bind(transfers.get(i));
    }

    @Override
    public int getItemCount() {
        return transfers.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView mDepositor;
        private TextView mDate;
        private TextView mAmount;

        HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            mDepositor = itemView.findViewById(R.id.tv_depositor_name);
            mDate = itemView.findViewById(R.id.tv_deposit_date);
            mAmount = itemView.findViewById(R.id.tv_deposit_amount);
        }

        void bind(Transfer transfer) {
            mDepositor.setText(transfer.getFrom());
            mDate.setText(transfer.getDate());

            if (transfer.getAmount() < 0) {
                mAmount.setText("- Rp " + (transfer.getAmount() * (-1)));
            } else {
                mAmount.setText("+ Rp " + transfer.getAmount());
            }
        }
    }
}
