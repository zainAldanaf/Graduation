package com.example.palliativecareguidelines.Doctors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.palliativecareguidelines.Pationts.ProfileActivity;
import com.example.palliativecareguidelines.R;
import com.example.palliativecareguidelines.modules.Contacts;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FindPationts extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference Userref;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pationts);

        Userref= FirebaseDatabase.getInstance().getReference().child("Users");

        recyclerView=findViewById(R.id.find_pationts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(" Pationts");



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Contacts> options=new FirebaseRecyclerOptions.Builder<Contacts>()
                .setQuery(Userref,Contacts.class)
                .build();


        FirebaseRecyclerAdapter<Contacts,FindFriendsViewHolder> adapter=
                new FirebaseRecyclerAdapter<Contacts, FindFriendsViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull FindFriendsViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull Contacts model) {
                        holder.username.setText(model.getName());
                        holder.userstatus.setText(model.getStatus());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String visit_user_id=getRef(position).getKey();
                                Intent profileintent=new Intent(FindPationts.this, ProfileActivity.class);
                                profileintent.putExtra("visit_user_id",visit_user_id);
                                startActivity(profileintent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public FindFriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.userdisplay2,parent,false);
                        FindFriendsViewHolder viewHolder=new FindFriendsViewHolder(view);
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
