package com.example.palliativecareguidelines.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.palliativecareguidelines.R;
import com.example.palliativecareguidelines.modules.Topics;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PationtAdapter extends RecyclerView.Adapter<PationtAdapter.ViewHolder> {

    private List<Topics> mData;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ItemClickListener2 itemClickListener2;


    public PationtAdapter(Context context, List<Topics> data, ItemClickListener onClick, ItemClickListener2 onClick2) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener = onClick;
        this.itemClickListener2 = onClick2;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.showpationts, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder , @SuppressLint("RecyclerView") final int position) {

        holder.title.setText(mData.get(position).getTopic_title());
        holder.content.setText(mData.get(position).getTopic_content());
        Picasso.with(mInflater.getContext()).load(mData.get(position).getImage()).into(holder.topicimage);
      holder.card.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              itemClickListener2.onItemClick2(holder.getAdapterPosition(), mData.get(position).getId());

          }
      });

        holder.notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(holder.getAdapterPosition(), mData.get(position).getId());

            }
        });
//
    }
    //  private  int getRandomColor(){
//         List<Integer> colorCode = new ArrayList<>();
//      colorCode.add(R.color.blue);
//      colorCode.add(R.color.orange);
//      colorCode.add(R.color.pink);
//      colorCode.add(R.color.purple);
//      colorCode.add(R.color.yellow);
//      Random random =new Random();
//      int random_color =random.nextInt(colorCode.size());
//      return  random_color;
//  }
    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView content;

        public MaterialButton card;
        public ImageView edit;
        public ImageView topicimage;
        public ImageView notification;

        ViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.topictitle);
            this.notification = itemView.findViewById(R.id.notification);

            this.content = itemView.findViewById(R.id.topicdes);
            this.edit = itemView.findViewById(R.id.edit);
            this.card = itemView.findViewById(R.id.showmore);
            this.topicimage=itemView.findViewById(R.id.imagepationt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

    }


    Topics getItem(int id) {

        return mData.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position, String id);
    }

    public interface ItemClickListener2{
        void onItemClick2(int position, String id);
    }
}
