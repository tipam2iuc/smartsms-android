package com.example.lamchard.smartsms;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lamchard.smartsms.Adapters.DiscussionAdapter;
import com.example.lamchard.smartsms.Models.Discussion;
import com.example.lamchard.smartsms.Models.Message;
import com.example.lamchard.smartsms.Models.SmsManagers;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class FragmentDiscussion extends Fragment {

    private static final int PERMISSIONS_REQUESR_READ_CONTACTS = 100;

    private static final int CONVERSATION_REQUEST = 2;

    private View view;
    private RecyclerView recyclerView;
    public static DiscussionAdapter discussionAdapter;

    public static List<Discussion> discussions;

    public FragmentDiscussion() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.discussion_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewDiscussion);

        discussions = new ArrayList<>();
        discussions.add(new Discussion("Yves", "677738977","Yo","05:23"));

        discussionAdapter = new DiscussionAdapter(discussions);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(discussionAdapter);

        discussionAdapter.setOnItemClickListener(new DiscussionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Discussion discussion) {

                Intent intent = new Intent(container.getContext(), ConversationActivity.class);
                intent.putExtra(ConversationActivity.EXTRA_Name, discussion.getName());
                intent.putExtra(ConversationActivity.EXTRA_PhoneNumber, discussion.getPhoneNumber());
                startActivityForResult(intent, CONVERSATION_REQUEST);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CONVERSATION_REQUEST && resultCode == RESULT_OK) {
            Toast.makeText(getContext(), "Message", Toast.LENGTH_SHORT).show();
        }
    }
}
