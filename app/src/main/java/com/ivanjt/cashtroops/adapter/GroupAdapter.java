package com.ivanjt.cashtroops.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivanjt.cashtroops.GroupOverviewActivity;
import com.ivanjt.cashtroops.R;
import com.ivanjt.cashtroops.model.Group;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    private ArrayList<Group> mGroups;
    private Context mContext;

    public GroupAdapter(Context context, ArrayList<Group> groups) {
        mContext = context;
        mGroups = groups;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grouplist_item, viewGroup, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder viewHolder, int i) {
        viewHolder.bind(mGroups.get(i));
    }

    @Override
    public int getItemCount() {
        return mGroups.size();
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {
        private TextView mGroupNameTextView;
        private TextView mMembersTextView;

        GroupViewHolder(@NonNull View itemView) {
            super(itemView);

            mGroupNameTextView = itemView.findViewById(R.id.tv_group_name);
            mMembersTextView = itemView.findViewById(R.id.tv_group_member_numbers);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, GroupOverviewActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }

        void bind(Group group) {
            mGroupNameTextView.setText(group.getName());
            mMembersTextView.setText(group.getMembers().size() + " Anggota");
        }
    }
}
