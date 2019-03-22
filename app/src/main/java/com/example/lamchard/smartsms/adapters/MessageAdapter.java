package com.example.lamchard.smartsms.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lamchard.smartsms.Models.Discussion;
import com.example.lamchard.smartsms.Models.Message;
import com.example.lamchard.smartsms.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.CustomViewHolder> {

    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView textView, textViewTime;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textMessage);
            textViewTime = itemView.findViewById(R.id.textTime);
        }
    }
    private List<Message> messagesList;
    private Message lastMessage;

    public MessageAdapter(List<Message>  messages) {

        this.messagesList = messages;
    }

    public MessageAdapter() {

        this.messagesList = new ArrayList<>();
    }

    public void addMessageList(Discussion discussion){
        if(discussion != null && messagesList != null){

            boolean isMe;

            if(discussion.getType().contains("2")){
                isMe = true;
            }else{
                isMe = false;
            }

            Log.i("INFOTYPE",isMe == true ? "true" : "false");

            verificationOfType(discussion.getDate(), discussion.getTime());

            this.messagesList.add(new Message(discussion.getMessage(), isMe, Message.TypeMessage.Conversation,discussion.getTime(),discussion.getDate()));
        }

        notifyDataSetChanged();
    }

    private void verificationOfType(String date, String heure){

        if(this.messagesList.size() == 0)
        {
            if(!currentDate().contentEquals(date)){
                this.messagesList.add(new Message(date + " à " + heure,Message.TypeMessage.LineStart));
            }
            else
            {
                this.messagesList.add(new Message("Aujourd'hui" + " à " + heure,Message.TypeMessage.LineStart));
            }

        }else{
            if(!this.messagesList.get(this.messagesList.size()-1).getDate().contentEquals(date)){
                if(!currentDate().contentEquals(date))
                {
                    this.messagesList.add(new Message(date + " à " + heure,Message.TypeMessage.LineStart));
                }else if(currentDate().contentEquals(date)){
                    this.messagesList.add(new Message("Aujourd'hui" + " à " + heure,Message.TypeMessage.LineStart));
                }
            }
        }
    }

    private String currentDate(){
        long time = System.currentTimeMillis();

        Date date = new Date(Long.parseLong(String.valueOf(time)));

        Log.i("DateTime",DateFormat.getDateInstance().format(date));
        return DateFormat.getDateInstance().format(date);
    }

    public void addMessageList(List<Discussion> discussions){

        //boolean isopen;
        for(Discussion discussion: discussions){
            if(discussion != null && messagesList != null){

                boolean isMe;

                if(discussion.getType().contains("2")){
                    isMe = true;
                }else{
                    isMe = false;
                }

                verificationOfType(discussion.getDate(), discussion.getTime());


                Message.TypeMessage typeMessage;

                if(this.messagesList.size() != 0 ){
                    if(this.messagesList.get(this.messagesList.size()-1).getType() != Message.TypeMessage.LineStart &&
                            this.messagesList.get(this.messagesList.size()-1).isMe() == isMe){
                        if(this.messagesList.get(this.messagesList.size()-1).getTime().contentEquals(discussion.getTime())){
                            if(this.messagesList.get(this.messagesList.size()-1).getType() == Message.TypeMessage.BlockEnd){

                                this.messagesList.get(this.messagesList.size()-1).setType(Message.TypeMessage.BlockContent);

                            }else{
                                this.messagesList.get(this.messagesList.size()-1).setType(Message.TypeMessage.BlockStart);
                            }
                            typeMessage = Message.TypeMessage.BlockEnd;
                        }else{
                            typeMessage = Message.TypeMessage.Conversation;
                        }
                    }else{
                        typeMessage = Message.TypeMessage.Conversation;
                    }
                }else{
                    typeMessage = Message.TypeMessage.Conversation;
                }

                this.messagesList.add(new Message(discussion.getMessage(), isMe, typeMessage,discussion.getTime(),discussion.getDate()));

            }
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(messagesList.get(position).getType() == Message.TypeMessage.Conversation) {
            if(messagesList.get(position).isMe()){
                return R.layout.item_bubble_send;
            }

            return R.layout.item_bubble_receve;

        }else if(messagesList.get(position).getType() != Message.TypeMessage.Conversation && messagesList.get(position).getType() != Message.TypeMessage.LineStart) {
            if(!messagesList.get(position).isMe()){
                if(messagesList.get(position).getType() == Message.TypeMessage.BlockStart){
                    return R.layout.item_bubble_receve_blocktimestart;
                }else if(messagesList.get(position).getType() == Message.TypeMessage.BlockContent){
                    return R.layout.item_bubble_receve_blocktimecontent;
                }else if(messagesList.get(position).getType() == Message.TypeMessage.BlockEnd){
                    return R.layout.item_bubble_receve_blocktimeend;
                }else if(messagesList.get(position).getType() == Message.TypeMessage.Debut){
                    return R.layout.item_bubble_start_conversation;
                }else{
                    return R.layout.item_bubble_send;
                }
            }else{
                if(messagesList.get(position).getType() == Message.TypeMessage.BlockStart){
                    return R.layout.item_bubble_send_blocktimestart;
                }else if(messagesList.get(position).getType() == Message.TypeMessage.BlockContent){
                    return R.layout.item_bubble_send_blocktimecontent;
                }else if(messagesList.get(position).getType() == Message.TypeMessage.BlockEnd){
                    return R.layout.item_bubble_send_blocktimeend;
                }else if(messagesList.get(position).getType() == Message.TypeMessage.Debut){
                    return R.layout.item_bubble_start_conversation;
                }else{
                    return R.layout.item_bubble_receve;
                }
            }

        }else if(messagesList.get(position).getType() == Message.TypeMessage.LineStart) {
            return R.layout.line_start_conversation;
        }else{
            return R.layout.layout_null;
        }
    }

    @NonNull
    @Override
    public MessageAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.CustomViewHolder holder, int position) {
        if(holder.textView != null)holder.textView.setText(messagesList.get(position).getTextMessage());
        if(holder.textViewTime != null)holder.textViewTime.setText(messagesList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }
}
