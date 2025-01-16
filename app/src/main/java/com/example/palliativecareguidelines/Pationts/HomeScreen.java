package com.example.palliativecareguidelines.Pationts;

import static com.example.palliativecareguidelines.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.palliativecareguidelines.Adapters.DoctorAdapter;
import com.example.palliativecareguidelines.Adapters.PationtAdapter;
import com.example.palliativecareguidelines.R;
import com.example.palliativecareguidelines.modules.Topics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HomeScreen extends AppCompatActivity implements PationtAdapter.ItemClickListener2, PationtAdapter.ItemClickListener {
    SearchView searchView;
    ListView listView;
    ArrayList<String> list;

    FirebaseFirestore firebaseFirestore;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Topics> items;
    DoctorAdapter[] myListData;
    private FirebaseAnalytics mFirebaseAnalytics;

    PationtAdapter adapter;
    EditText Updatetitle;
    EditText Updatenote;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    RecyclerView rv;
    @SuppressLint({"MissingInflatedId", "CommitTransaction"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        rv = findViewById(R.id.recyclerview);
        items = new ArrayList<Topics>();
        adapter =new PationtAdapter(this,items,this,this);
        getTopics();
        searchView = findViewById(R.id.searchview);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);



        FirebaseMessaging.getInstance().subscribeToTopic("Topics")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.e("a","Done");
                    }
                })     .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("a","Failed");

                    }
                })  ;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               searchtopic(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchtopic(newText);

                return false;
            }
        });
    }

    private void searchtopic(String query) {
        db.collection("Topics").whereEqualTo("topic_title",query).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    if (documentSnapshot.exists()) {
                        items.clear();
                        String id = documentSnapshot.getId();
                        String title = documentSnapshot.getString("topic_title");
                        String content = documentSnapshot.getString("topic_content");
                        String video = documentSnapshot.getString("topic_video");
                        String image = documentSnapshot.getString("topic_image");


                        Topics topics = new Topics(id, title ,content,image,video);
                        items.add(topics);

                        rv.setLayoutManager(layoutManager);
                        rv.setHasFixedSize(true);
                        rv.setAdapter(adapter);
                        ;
                        adapter.notifyDataSetChanged();
                        Log.e("LogDATA", items.toString());

                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.chatitem, menu);

        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.chat:
                startActivity(new Intent(HomeScreen.this, ChatActivity.class));

                break;
            case R.id.profile:
                startActivity(new Intent(HomeScreen.this, ProfilePAtiont.class));

                break;

        }

           return true;
    }
    public  void getTopics(){
        db.collection("Topics").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.d("alaa", "onSuccess: LIST EMPTY");
                            return;
                        } else {
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                if (documentSnapshot.exists()) {
                                    String id = documentSnapshot.getId();
                                    String title = documentSnapshot.getString("topic_title");
                                    String content = documentSnapshot.getString("topic_content");
                                    String video = documentSnapshot.getString("topic_video");
                                    String image = documentSnapshot.getString("topic_image");


                                    Topics topics = new Topics(id, title ,content,image,video);
                                    items.add(topics);

                                    rv.setLayoutManager(layoutManager);
                                    rv.setHasFixedSize(true);
                                    rv.setAdapter(adapter);
                                    ;
                                    adapter.notifyDataSetChanged();
                                    Log.e("LogDATA", items.toString());

                                }
                            }
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("LogDATA", "get failed with ");


                    }
                });
    }

    @Override
    public void onItemClick(int position, String id) {

    }

    @Override
    public void onItemClick2(int position, String id) {
        btnEvent("topic","topics","MaterialButton");
        Intent intent= new Intent(this,DetailsScreen.class);
       intent.putExtra("title", items.get(position).getTopic_title());
       intent.putExtra("content", items.get(position).getTopic_content());
        StorageReference storageReference;
       SimpleDateFormat Format=new SimpleDateFormat("yyyy_MM_dd_HH_mm_s", Locale.CANADA);
       Date date1=new Date();
        String filename1= Format.format(date1);
      storageReference= FirebaseStorage.getInstance().getReference("videos.mp4/");
       storageReference.getDownloadUrl().addOnSuccessListener( video_Uri -> {
          String link=video_Uri.toString();
        intent.putExtra("link",link);
           startActivity(intent);
       });

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
    public  void btnEvent(String id,String name,String content){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, content);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}
