package com.example.palliativecareguidelines.Doctors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.palliativecareguidelines.Adapters.DoctorAdapter;
import com.example.palliativecareguidelines.AppSettingPage;
import com.example.palliativecareguidelines.R;
import com.example.palliativecareguidelines.modules.Topics;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class DoctorHome extends AppCompatActivity implements DoctorAdapter.ItemClickListener ,DoctorAdapter.ItemClickListener2 {
FloatingActionButton fba;
    FloatingActionButton chatfba;
    FloatingActionButton profileFab;
    FloatingActionButton settingFab;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Topics> items;
    DoctorAdapter adapter;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    RecyclerView rv;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);

        fba=findViewById(R.id.fab);
        chatfba=findViewById(R.id.chatfab);
        profileFab = findViewById(R.id.profileFab);
        settingFab = findViewById(R.id.settingFab);

        rv = findViewById(R.id.recyclerview);
        items = new ArrayList<Topics>();
        adapter =new DoctorAdapter(this,items,this,this);
       getTopics();
        fba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorHome.this, AddTopicsScreen.class));

            }
        });

        chatfba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorHome.this, ChatDoctor.class));

            }
        });

        profileFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorHome.this, DoctorProfile.class));

            }
        });

        settingFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorHome.this, Settings.class));

            }
        });


    }

    public  void getTopics(){
        db.collection("DoctorTopic").get()
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
                                    String title = documentSnapshot.getString("topic_address");
                                    String content = documentSnapshot.getString("topic_details");
                                    String video = documentSnapshot.getString("topic_img");
                                    String image = documentSnapshot.getString("topic_video");


                                    Topics note = new Topics(id, title ,content,image,video);
                                    items.add(note);

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
    public  void DeleteTopic(final Topics topics){
        AlertDialog.Builder alertDialogBuilderLabelDelete = new AlertDialog.Builder(this);
        alertDialogBuilderLabelDelete.create();
        alertDialogBuilderLabelDelete.setIcon(R.drawable.trash);
        alertDialogBuilderLabelDelete.setCancelable(false);
        alertDialogBuilderLabelDelete.setTitle("Delete label");
        alertDialogBuilderLabelDelete.setMessage("Are you sure to delete?");
        alertDialogBuilderLabelDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogBox, int id) {
                db.collection("Topics").document(topics.getId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                items.remove(topics);
                                Toast.makeText(DoctorHome.this, " Removed Successfully", Toast.LENGTH_SHORT).show();
                                rv.setAdapter(adapter);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("logData","get failed with delete");
                            }
                        });

            }
        });
        alertDialogBuilderLabelDelete.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        dialogBox.cancel();
                    }
                });        alertDialogBuilderLabelDelete.show();
    }
    public void updateTopic() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("TopicDOC");
        final View customLayout = getLayoutInflater().inflate(R.layout.editscreen, null);
        builder.setView(customLayout);

        builder.setPositiveButton("Update",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        Updatetitle = customLayout.findViewById(R.id.Updatetitle);
//                        Updatenote = customLayout.findViewById(R.id.Updatenote);

//                        db.collection("Notes").document(note.getId()).
//                                update("title", Updatetitle.getText().toString(),"note",Updatenote.getText().toString())
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        Log.d("dareen", "DocumentSnapshot successfully updated!");
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Log.w("dareen", "Error updating document", e);
//                                    }
//                                });
//                    }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }














    ////////////////////////////////////////////////////////////////////
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.profiledoctor, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profiledoctor:
                startActivity(new Intent(DoctorHome.this, DoctorProfile.class));

                break;
            case R.id.chatdoctor:
                startActivity(new Intent(DoctorHome.this, AppSettingPage.class));
                break;

        }

        return true;
    }

    @Override
    public void onItemClick(int position, String id) {
        Intent intent=new Intent(DoctorHome.this, updateTopic.class);
       intent.putExtra("id",items.get(position).getId());
       startActivity(intent);
    }

    @Override
    public void onItemClick2(int position, String id) {
            DeleteTopic(items.get(position));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}