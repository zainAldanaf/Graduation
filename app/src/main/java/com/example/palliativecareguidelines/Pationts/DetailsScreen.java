package com.example.palliativecareguidelines.Pationts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.palliativecareguidelines.R;
import com.example.palliativecareguidelines.modules.Topics;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DetailsScreen extends AppCompatActivity {
   TextView title;
   TextView des;
    String link;
    PlayerView playerView;
    SimpleExoPlayer player;
    int currentwindow=0;//screen
    boolean playWhenReady=true;//when finish download video show it
    long playBackPosition=0;//back button

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_screen);
        title = findViewById(R.id.topic_title);
        des = findViewById(R.id.topic_des);
        link=getIntent().getStringExtra("link");
        playerView=findViewById(R.id.videoView2);
        title.setText(getIntent().getStringExtra("title"));
        des.setText(getIntent().getStringExtra("content"));

    }
    public void initVideo(){
        player=new SimpleExoPlayer.Builder(DetailsScreen.this).build();
        playerView.setPlayer(player);

        Uri uri=Uri.parse(link);
        DataSource.Factory datasource=new DefaultDataSourceFactory(DetailsScreen.this,"video");

        MediaSource mediaSource=new ProgressiveMediaSource.Factory(datasource).createMediaSource(MediaItem.fromUri(uri));
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentwindow,playBackPosition);
        player.prepare(mediaSource,false,false);
    }

    public void releaseVideo(){
        if (player != null ){
            playWhenReady = player.getPlayWhenReady();
            playBackPosition=player.getCurrentPosition();
            currentwindow=player.getCurrentWindowIndex();
            player.release();
            player=null;
        }
    }
    @Override
    protected void onStart() {
        initVideo();
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseVideo();

    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseVideo();
    }
}