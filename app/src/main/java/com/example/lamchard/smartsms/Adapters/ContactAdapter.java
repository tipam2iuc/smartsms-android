package com.example.lamchard.smartsms.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lamchard.smartsms.Models.Contact;
import com.example.lamchard.smartsms.R;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {

    private List<Contact> contacts;
    Context context;
    Dialog mDialog;

    public ContactAdapter(List<Contact> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        holder.tv_name.setText(contacts.get(position).getName());
        holder.tv_phone.setText(contacts.get(position).getPhone());
        holder.img.setImageResource(contacts.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ContactHolder extends RecyclerView.ViewHolder {

        private LinearLayout item_contact;
        private TextView tv_name;
        private TextView tv_phone;
        private ImageView img;

        public ContactHolder(@NonNull View itemView) {
            super(itemView);
            item_contact = (LinearLayout) itemView.findViewById(R.id.contact_item_id);
            tv_name = (TextView)itemView.findViewById(R.id.name_contact);
            tv_phone = (TextView)itemView.findViewById(R.id.phone_contact);
            img = (ImageView) itemView.findViewById(R.id.img_contact);
        }
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_contact,parent, false);
        final  ContactHolder contactHolder = new ContactHolder(v);

        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.dialog_contact);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        contactHolder.item_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView dialog_name_tv = (TextView)mDialog.findViewById(R.id.dialog_name_id);
                TextView dialog_phone_tv = (TextView)mDialog.findViewById(R.id.dialog_phone_id);
                ImageView dialog_contact_img = (ImageView)mDialog.findViewById(R.id.dialog_img);
                dialog_name_tv.setText(contacts.get(contactHolder.getAdapterPosition()).getName());
                dialog_phone_tv.setText(contacts.get(contactHolder.getAdapterPosition()).getPhone());
                dialog_contact_img.setImageResource(contacts.get(contactHolder.getAdapterPosition()).getPhoto());
                mDialog.show();
            }
        });

        return contactHolder;
    }
}
