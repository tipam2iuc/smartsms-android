package com.example.lamchard.smartsms;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lamchard.smartsms.adapters.DiscussionAdapter;
import com.example.lamchard.smartsms.models.Discussion;
import com.example.lamchard.smartsms.models.Message;
import com.example.lamchard.smartsms.models.SmsManagers;

import java.util.ArrayList;
import java.util.List;

public class FragmentDiscussion extends Fragment {

    private static final int PERMISSIONS_REQUESR_READ_CONTACTS = 100;

    private View view;
    private RecyclerView recyclerView;
    public static DiscussionAdapter discussionAdapter;

    public static List<Discussion> discussions;

    public FragmentDiscussion(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.discussion_fragment,container,false);

        recyclerView = view.findViewById(R.id.recyclerViewDiscussion);

        discussionAdapter = new DiscussionAdapter(discussions);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(discussionAdapter);

        return view;
    }

//    private void testSimulationDisc(){
//        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
//        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
//        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
//        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
//        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
//        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
//        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
//        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
//        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
//        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
//        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
//        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
//        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
//        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
//        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
//        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
//        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
//    }
}
