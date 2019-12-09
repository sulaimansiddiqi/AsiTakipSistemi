package com.example.asitakipson;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AsiEkle extends AppCompatActivity {


    DatabaseReference db;
    EditText editTextAsiID;
    EditText editTextHastahaneAdi;
    EditText editTextAsiTarihi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asi_ekle);

        Bundle extras = getIntent().getExtras();
        String uID = extras.getString("sendUD");
        Toast.makeText(this, uID, Toast.LENGTH_SHORT).show();

        editTextAsiID = findViewById(R.id.EditTextAsiID);
        editTextHastahaneAdi = findViewById(R.id.EditTextHastahane);
        editTextAsiTarihi=findViewById(R.id.EditTextAsiTarihi);
        String eMail;

        if(uID!=null){
            db = FirebaseDatabase.getInstance().getReference(uID).child("Asilar");

        }else{
            Toast.makeText(this, "UID Boş Olamaz", Toast.LENGTH_SHORT).show();
        }

    }

    public void btnAsiKaydet(View button) {

        Asi asi = new Asi();
        asi.setAsiID(editTextAsiID.getText().toString());
        asi.setHastahaneAdi(editTextHastahaneAdi.getText().toString());
        asi.setAsiTarih(editTextAsiTarihi.getText().toString());
        db.push().setValue(asi);
        

    }

}


