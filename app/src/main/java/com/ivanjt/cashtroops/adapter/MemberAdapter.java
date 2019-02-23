package com.ivanjt.cashtroops.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivanjt.cashtroops.R;
import com.ivanjt.cashtroops.model.User;

import java.util.ArrayList;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {
    private ArrayList<User> users;
    private Context context;

    public MemberAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.members_item, viewGroup, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder memberViewHolder, int i) {
        memberViewHolder.bind(users.get(i));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class MemberViewHolder extends RecyclerView.ViewHolder {
        private TextView mNameTextView;
        private TextView mPhoneNumberTextView;
        private TextView mCashTagTextView;

        MemberViewHolder(@NonNull View itemView) {
            super(itemView);

            mNameTextView = itemView.findViewById(R.id.tv_name);
            mPhoneNumberTextView = itemView.findViewById(R.id.tv_phone_number);
            mCashTagTextView = itemView.findViewById(R.id.tv_cashtag);
        }

        void bind(User user) {
            mNameTextView.setText(user.getName());
            mCashTagTextView.setText("@" + user.getCashTag());
            mPhoneNumberTextView.setText("+62" + user.getPhoneNumber());
        }
    }
}
