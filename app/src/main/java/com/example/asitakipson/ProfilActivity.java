package com.example.asitakipson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.IOException;

public class ProfilActivity extends AppCompatActivity {


    private static  final   int CHOOSE_IMAGE =101;
    ImageView imageView;
    EditText editText;
    Uri uriProfileImage;
    ProgressBar progressBar;
    String profileImageUrl;
    FirebaseAuth firebaseAuth;
    String uID;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        firebaseAuth =FirebaseAuth.getInstance();
        editText =(EditText)findViewById(R.id.editTextDisplayName);
        imageView=(ImageView)findViewById(R.id.imageView);

        progressBar = findViewById(R.id.progresbar) ;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        Bundle extras = getIntent().getExtras();
        uID = mAuth.getUid();
        kullaniciBilgiGetir();

    }
    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    private void kullaniciBilgiGetir() {
        FirebaseUser user = mAuth.getCurrentUser();
       if(user!=null) {
           if (user.getPhotoUrl() != null) {

               Glide.with(this)
                       .load(user.getPhotoUrl().toString())
                       .into(imageView);
           }
           if (user.getDisplayName() != null) {

               editText.setText(user.getDisplayName());
           }
       }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_IMAGE && resultCode==RESULT_OK && data!= null&& data.getData()!=null){


            uriProfileImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriProfileImage);
                imageView.setImageBitmap(bitmap);
                uploadImageToFirebaseStorage();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    public  void buttonKaydet(View v){

        resimKaydet();
    }
    private void showImageChooser(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile Image"), CHOOSE_IMAGE  );
    }
    private void uploadImageToFirebaseStorage(){

        StorageReference profilFileImageRef =
                FirebaseStorage.getInstance().getReference("profilepics/"+System.currentTimeMillis()+".jpg");

        if(uriProfileImage!=null){
            progressBar.setVisibility(View.VISIBLE);
            profilFileImageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);
                            // profileImageUrl = taskSnapshot.getDownloadUrl().toString();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                        }
                    });

        }

    }


    private void resimKaydet() {

        String displayName = editText.getText().toString();
        if(displayName.isEmpty()){

            editText.setError("İsim Gerekli");
            editText.requestFocus();
            return;
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null&&profileImageUrl!=null){

            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build();
            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ProfilActivity.this, "Profil Güncellendi", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }
    public void buttonAsiKaydet(View v) {

        Intent i = new Intent(getApplicationContext(),AsiEkle.class);
        i.putExtra("sendUD",uID);
        startActivity(i);
    }

    public void buttonAsiListele(View v){

        Intent i = new Intent(getApplicationContext(),AsiListele.class);
        i.putExtra("sendUD",uID);
        startActivity(i);

    }




}
