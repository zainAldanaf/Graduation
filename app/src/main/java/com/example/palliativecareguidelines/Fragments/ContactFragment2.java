package com.example.palliativecareguidelines.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.palliativecareguidelines.R;
import com.example.palliativecareguidelines.modules.Contacts;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment2 extends Fragment {

    private View view;
    private RecyclerView myrecyclerview;
    private DatabaseReference contactsRef,userRef;
    private FirebaseAuth mauth;
    private String currentUserId;
    public ContactFragment2() {
        // Required empty public constructor
    }

///////////////////////////
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_contact2, container, false);
        myrecyclerview=view.findViewById(R.id.contacts_recyclerview_list2);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        mauth=FirebaseAuth.getInstance();
        currentUserId=mauth.getCurrentUser().getUid();

        contactsRef= FirebaseDatabase.getInstance().getReference().child("Contacts").child(currentUserId);
        userRef=FirebaseDatabase.getInstance().getReference().child("Users");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions options=new FirebaseRecyclerOptions.Builder<Contacts>()
                .setQuery(contactsRef,Contacts.class)
                .build();

        FirebaseRecyclerAdapter<Contacts,ContactsViewHolder> adapter=new FirebaseRecyclerAdapter<Contacts, ContactsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ContactsViewHolder holder, int position, @NonNull Contacts model) {

                String userId=getRef(position).getKey();
                userRef.child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            if(dataSnapshot.child("userState").hasChild("state"))
                            {
                                String state=dataSnapshot.child("userState").child("state").getValue().toString();
                                String date=dataSnapshot.child("userState").child("date").getValue().toString();
                                String time=dataSnapshot.child("userState").child("time").getValue().toString();

                                if(state.equals("online"))
                                {
                                    holder.onlineIcon.setVisibility(View.VISIBLE);
                                }
                                else if(state.equals("offline"))
                                {
                                    holder.onlineIcon.setVisibility(View.INVISIBLE);
                                }
                            }
                            else
                            {
                                holder.onlineIcon.setVisibility(View.INVISIBLE);
                            }

                            if(dataSnapshot.hasChild("image"))
                            {
                                String image=dataSnapshot.child("image").getValue().toString();
                                String name=dataSnapshot.child("name").getValue().toString();
                                String status=dataSnapshot.child("status").getValue().toString();

                                holder.username.setText(name);
                                holder.userstatus.setText(status);
                            }
                            else
                            {

                                String name=dataSnapshot.child("name").getValue().toString();
                                String status=dataSnapshot.child("status").getValue().toString();

                                holder.username.setText(name);
                                holder.userstatus.setText(status);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.userdisplay2,parent,false);
                ContactsViewHolder viewHolder=new ContactsViewHolder(view);
                return viewHolder;
            }
        };

        myrecyclerview.setAdapter(adapter);
        adapter.startListening();
    }

    public static class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView username,userstatus;
        ImageView onlineIcon;
        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);

            username=itemView.findViewById(R.id.users_profile_name);
            userstatus=itemView.findViewById(R.id.users_status);
            onlineIcon=itemView.findViewById(R.id.users_online_status);
        }
    }
}
