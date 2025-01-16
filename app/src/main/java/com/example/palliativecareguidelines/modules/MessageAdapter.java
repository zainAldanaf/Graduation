package com.example.palliativecareguidelines.modules;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.palliativecareguidelines.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Messages> UserMessageList;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    public MessageAdapter(List<Messages> UserMessageList)
    {
        this.UserMessageList=UserMessageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_messages_layout,parent,false);
       MessageViewHolder messageViewHolder=new MessageViewHolder(view);
       mAuth=FirebaseAuth.getInstance();

       return messageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder holder, final int position) {
        String messagesenderid=mAuth.getCurrentUser().getUid();
        final Messages messages=UserMessageList.get(position);

        String fromuserid=messages.getFrom();
        String frommessagetype=messages.getType();
        userRef= FirebaseDatabase.getInstance().getReference().child("Users").child(fromuserid);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.receivermessagetext.setVisibility(View.GONE);
        holder.sendermessagetext.setVisibility(View.GONE);
        holder.messageSenderPicture.setVisibility(View.GONE);
        holder.messageReceiverPicture.setVisibility(View.GONE);
        if(frommessagetype.equals("text"))
        {
            if(fromuserid.equals(messagesenderid))
            {
                holder.sendermessagetext.setVisibility(View.VISIBLE);
                holder.sendermessagetext.setBackgroundResource(R.drawable.sender_message_layout);
                holder.sendermessagetext.setText(messages.getMessage()+"\n \n"+messages.getTime()+" - "+messages.getDate());
            }
            else
            {
                holder.receivermessagetext.setVisibility(View.VISIBLE);


                holder.receivermessagetext.setBackgroundResource(R.drawable.receiver_message_layout);
                holder.receivermessagetext.setText(messages.getMessage()+"\n \n"+messages.getTime()+" - "+messages.getDate());
            }
        }
    }

    @Override
    public int getItemCount() {
        return UserMessageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView sendermessagetext,receivermessagetext;
        public ImageView messageSenderPicture,messageReceiverPicture;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            sendermessagetext=itemView.findViewById(R.id.sender_message_text);
            receivermessagetext=itemView.findViewById(R.id.receiver_message_text);
            messageSenderPicture=itemView.findViewById(R.id.message_sender_image_view);
            messageReceiverPicture=itemView.findViewById(R.id.message_receiver_image_view);
        }
    }


}
