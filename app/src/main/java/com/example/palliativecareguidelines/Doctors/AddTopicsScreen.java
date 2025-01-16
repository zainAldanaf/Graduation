package com.example.palliativecareguidelines.Doctors;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.palliativecareguidelines.R;
import com.example.palliativecareguidelines.modules.Topics;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddTopicsScreen extends AppCompatActivity {
    ImageView imageView;
    VideoView videoView;
    Button Choosevideo;
    Uri videouri;
    MediaController mediaController;
    Button chooseimage;
    EditText address;
    EditText cotent;
    Button add_btn;
    Uri imageUri;
    StorageReference storageReference;
    StorageReference storageReference2;

    ProgressDialog progressDialog;

    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topics_screen);
        progressDialog=new ProgressDialog(this);
        imageView=findViewById(R.id.image_add);
        chooseimage=findViewById(R.id.choose_image);
        videoView=findViewById(R.id.videoView);
        Choosevideo=findViewById(R.id.choose_video);
        videoView.setMediaController(mediaController);
        videoView.start();
        add_btn=findViewById(R.id.add_btn);
        cotent=findViewById(R.id.topic_details);
        address=findViewById(R.id.topic_address);
        firebaseFirestore=FirebaseFirestore.getInstance();


        Choosevideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                    Intent intent=new Intent();
                    intent.setType("video/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent,101);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                     permissionToken.continuePermissionRequest();
                    }
                }).check();
            }
        });


        chooseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadtopic();
//                uploadVideo();

            }
        });
    }
    public  void selectImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null && data.getData()!= null){
            imageUri=data.getData();
            imageView.setImageURI(imageUri);

        }else if (requestCode == 101 && data != null && data.getData()!= null){
            videouri=data.getData();
            videoView.setVideoURI(videouri);
        }
    }




    public void uploadtopic(){
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy_MM_dd_HH_mm_s", Locale.CANADA);
        Date date1=new Date();
        String filename1= simpleDateFormat1.format(date1);
        storageReference = FirebaseStorage.getInstance().getReference("videos/" + filename1);
        storageReference.getDownloadUrl().addOnSuccessListener( video_Uri -> {
            storageReference.putFile(videouri);
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy_MM_dd_HH_mm_s", Locale.CANADA);
            Date date=new Date();
            String filename= simpleDateFormat.format(date);

        storageReference= FirebaseStorage.getInstance().getReference("images.png/"+filename);
        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageView.setImageURI(null);

                storageReference.getDownloadUrl().addOnSuccessListener(imageUri-> {

                        String title=address.getText().toString();
                        String content=cotent.getText().toString();
                    Map<String, Object> topic = new HashMap<>();
                    topic.put("topic_name", title.toString());
                    topic.put("topic_content", content.toString());
                    topic.put("topic_image", imageUri.toString());
                    topic.put("topic_video", video_Uri.toString());

                    firebaseFirestore.collection("Topics")
                            .add(topic)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                    Toast.makeText(AddTopicsScreen.this, " Added Successfully", Toast.LENGTH_SHORT).show();
                                     notify();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("TAG", "Error adding document", e);
                                }
                            });

                });

                Toast.makeText(AddTopicsScreen.this, "uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddTopicsScreen.this, "failed", Toast.LENGTH_SHORT).show();

            }
        });
        });
    }




}
