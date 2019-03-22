package com.example.lamchard.smartsms;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;

import com.example.lamchard.smartsms.Adapters.ContactAdapter;
import com.example.lamchard.smartsms.Models.Contact;

import java.util.ArrayList;
import java.util.List;

public class FragmentContacts extends Fragment {

    View view;
    private RecyclerView recyclerView;
    private List<Contact> contacts;
    private Button dialog_btn_call, dialog_btn_message;

    public FragmentContacts() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contacts_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_conversation);
        dialog_btn_call = view.findViewById(R.id.dialog_btn_call);
        dialog_btn_message = view.findViewById(R.id.dialog_btn_message);

        ContactAdapter contactAdapter = new ContactAdapter(contacts, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(contactAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contacts = new ArrayList<>();

        contacts.add(new Contact("Kouayip yves", "+237 655396973", R.drawable.sandy_contact));
        contacts.add(new Contact("Suffo sokamte", "+237696587632", R.drawable.sandy_contact));
        contacts.add(new Contact("Amelia", "+237 668396573", R.drawable.sandy_contact));
        contacts.add(new Contact("Sandy", "+237 695397564", R.drawable.sandy_contact));
        contacts.add(new Contact("Amanda", "+237 655387765", R.drawable.sandy_contact));


    }

    private void getContact() {
//        String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,
//                ContactsContract.CommonDataKinds.Phone._ID};

    }
}
