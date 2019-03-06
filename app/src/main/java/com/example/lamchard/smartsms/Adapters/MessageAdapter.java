package com.example.lamchard.smartsms.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lamchard.smartsms.Models.Message;
import com.example.lamchard.smartsms.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.CustomViewHolder> {

    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textMessage);
        }
    }
    private List<Message> messagesList;

    public MessageAdapter(List<Message>  messages) {

        this.messagesList = messages;
    }

    @Override
    public int getItemViewType(int position) {
        if(messagesList.get(position).isMe()){
            return R.layout.item_bubble_send;
        }
        return R.layout.item_bubble_receve;
    }

    @NonNull
    @Override
    public MessageAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.CustomViewHolder holder, int position) {
        holder.textView.setText(messagesList.get(position).getTextMessage());
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }
}
