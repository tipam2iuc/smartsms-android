package com.example.lamchard.smartsms.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lamchard.smartsms.models.Discussion;
import com.example.lamchard.smartsms.R;

import java.util.List;

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.CustomViewHolder> {

    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName,textViewLastMessage,textViewTimeLastMessage;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewNameUserDisc);
            textViewLastMessage = itemView.findViewById(R.id.textViewLastMessageUserDisc);
            textViewTimeLastMessage = itemView.findViewById(R.id.textViewTimeLastMessageUserDisc);
        }
    }
    private List<Discussion> discussionList;

    public DiscussionAdapter(List<Discussion>  discussionList) {

        this.discussionList = discussionList;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_discussion_model;
    }

    @NonNull
    @Override
    public DiscussionAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DiscussionAdapter.CustomViewHolder holder, int position) {
        holder.textViewName.setText(discussionList.get(position).getName());
        holder.textViewLastMessage.setText(discussionList.get(position).getLastMessage());
        holder.textViewTimeLastMessage.setText(discussionList.get(position).getTimeForLastMessage());
    }

    @Override
    public int getItemCount() {
        return discussionList.size();
    }
}
