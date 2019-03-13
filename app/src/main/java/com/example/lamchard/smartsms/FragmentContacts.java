package com.example.lamchard.smartsms;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lamchard.smartsms.adapters.ContactAdapter;
import com.example.lamchard.smartsms.models.Contact;

import java.util.ArrayList;
import java.util.List;

public class FragmentContacts extends Fragment {

    View view;
    private RecyclerView recyclerView;
    private List<Contact> contacts;

    public FragmentContacts() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contacts_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
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
        contacts.add(new Contact("Suffo sokante", "+237696587632", R.drawable.sandy_contact));
        contacts.add(new Contact("Amelia", "+237 668396573", R.drawable.sandy_contact));
        contacts.add(new Contact("Sandy", "+237 695397564", R.drawable.sandy_contact));
        contacts.add(new Contact("Amanda", "+237 655387765", R.drawable.sandy_contact));
    }
}
