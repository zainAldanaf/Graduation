package com.example.palliativecareguidelines.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.palliativecareguidelines.Doctors.sendrequest;
import com.example.palliativecareguidelines.Pationts.ProfileActivity;
import com.example.palliativecareguidelines.R;
import com.example.palliativecareguidelines.modules.Contacts;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Finddoctors extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference Userref;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Userref = FirebaseDatabase.getInstance().getReference().child("Doctors");
        view=inflater.inflate(R.layout.fragment_finddoctors, container, false);
        recyclerView=view.findViewById(R.id.finddoctorsrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Contacts> options = new FirebaseRecyclerOptions.Builder<Contacts>()
                .setQuery(Userref, Contacts.class)
                .build();

        FirebaseRecyclerAdapter<Contacts, Findpationt.FindFriendsViewHolder> adapter = new FirebaseRecyclerAdapter<Contacts, Findpationt.FindFriendsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Findpationt.FindFriendsViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull Contacts model) {
                holder.username.setText(model.getName());
                holder.userstatus.setText(model.getStatus());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String visit_user_id = getRef(position).getKey();
                        Intent profileintent = new Intent(getContext(), sendrequest.class);
                        profileintent.putExtra("visit_user_id", visit_user_id);
                        startActivity(profileintent);
                    }
                });
            }

            @NonNull
            @Override
            public Findpationt.FindFriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.userdisplay,parent,false);
                Findpationt.FindFriendsViewHolder viewHolder=new Findpationt.FindFriendsViewHolder(view);
                return viewHolder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    public static class FindFriendsViewHolder extends RecyclerView.ViewHolder {
        TextView username,userstatus;
        public FindFriendsViewHolder(@NonNull View itemView) {
            super(itemView);

            username=itemView.findViewById(R.id.users_profile_name);
            userstatus=itemView.findViewById(R.id.users_status);
        }
    }
}