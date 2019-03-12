package com.example.lamchard.smartsms;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lamchard.smartsms.Adapters.DiscussionAdapter;
import com.example.lamchard.smartsms.Adapters.MessageAdapter;
import com.example.lamchard.smartsms.Models.Discussion;
import com.example.lamchard.smartsms.Models.Message;

import java.util.ArrayList;
import java.util.List;

public class FragmentDiscussion extends Fragment {

    private static final int PERMISSIONS_REQUESR_READ_CONTACTS = 100;

    private View view;
    private RecyclerView recyclerView;
    private DiscussionAdapter discussionAdapter;
    private List<Discussion> discussionList;

    public FragmentDiscussion(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.discussion_fragment,container,false);

        recyclerView = view.findViewById(R.id.recyclerViewDiscussion);
        discussionList = new ArrayList<>();

        testSimulationDisc();

        discussionAdapter = new DiscussionAdapter(discussionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(discussionAdapter);

        return view;
    }

    private void testSimulationDisc(){
        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
        discussionList.add(new Discussion("Kouayip yves","Bonsoir gar l'apk est disponible ..?","9:30"));
    }
}
