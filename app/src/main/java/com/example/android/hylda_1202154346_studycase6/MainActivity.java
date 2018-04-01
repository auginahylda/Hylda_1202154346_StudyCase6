package com.example.android.hylda_1202154346_studycase6;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

@SuppressLint("NewApi")
public class MainActivity extends Fragment {

    //deklarasi variable
    RecyclerView nRecView;
    DatabaseReference nDatabaseRef;
    ArrayList<db_post> list;
    Adapter2 adapterP;

    public homeall() {
        // Required empty public constructor
    }


    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homeall, container, false);
        nRecView = view.findViewById(R.id.inirecyclerview2);
        list = new ArrayList<>();
        nDatabaseRef = FirebaseDatabase.getInstance().getReference().child("post");
        adapterP = new Adapter2(list, this.getContext());

        //menampilkan recyclerview nya
        nRecView.setHasFixedSize(true);
        nRecView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        nRecView.setAdapter(adapterP);
        //listener apabila nilai yang terdapat pada virebase berubah
        nDatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //mendapatkan nilai dari post yang diunggah
                db_post nPost = dataSnapshot.getValue(db_post.class);
                nPost.key = dataSnapshot.getKey();
                list.add(nPost);
                adapterP.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
}
