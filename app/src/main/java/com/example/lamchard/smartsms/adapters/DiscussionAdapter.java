package com.example.lamchard.smartsms.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.lamchard.smartsms.models.Discussion;
import com.example.lamchard.smartsms.R;

import java.util.List;

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.CustomViewHolder> {

    private List<Discussion> discussionList;
    private OnItemClickListener listener;

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewLastMessage, textViewTimeLastMessage;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewNameUserDisc);
            textViewLastMessage = itemView.findViewById(R.id.textViewLastMessageUserDisc);
            textViewTimeLastMessage = itemView.findViewById(R.id.textViewTimeLastMessageUserDisc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(discussionList.get(position));
                    }
                }
            });
        }
    }

    public DiscussionAdapter(List<Discussion> discussionList) {

        this.discussionList = discussionList;
    }

    public interface OnItemClickListener {
        void onItemClick(Discussion discussion);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public DiscussionAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_discussion_model, parent, false);
        return new DiscussionAdapter.CustomViewHolder(itemView);
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
