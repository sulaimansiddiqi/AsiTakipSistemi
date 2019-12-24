package com.bakyuz.asitakipson;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AsiEkle extends AppCompatActivity {


    DatabaseReference db;
    EditText editTextAsiAdi;
    EditText editTextHastahaneAdi;
    EditText editTextAsiTarihi;
    CalendarView calendarViewTarih;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asi_ekle);

        Bundle extras = getIntent().getExtras();
        String uID = extras.getString("sendUD");


        editTextAsiAdi = findViewById(R.id.EditTextAsiAdi);
        editTextHastahaneAdi = findViewById(R.id.EditTextHastahane);
        editTextAsiTarihi=findViewById(R.id.EditTextAsiTarihi);
        editTextAsiTarihi.addTextChangedListener(new DateMask());
        String eMail;



        if(uID!=null){
            db = FirebaseDatabase.getInstance().getReference(uID).child("Asilar");

        }else{
            Toast.makeText(this, "UID Boş Olamaz", Toast.LENGTH_SHORT).show();
        }

    }

    public void btnAsiKaydet(View button) {

        if(!editTextAsiAdi.getText().toString().isEmpty() &&
            !editTextAsiTarihi.getText().toString().isEmpty()&&
            !editTextHastahaneAdi.getText().toString().isEmpty()
        ){
        Asi asi = new Asi();
        asi.setAsiAdi(editTextAsiAdi.getText().toString());
        asi.setHastahaneAdi(editTextHastahaneAdi.getText().toString());
        asi.setAsiTarih(editTextAsiTarihi.getText().toString());
        asi.setAsiDurum(false);
        String asiID =db.push().getKey();
        asi.setAsiId(asiID);

        db.child(asiID).setValue(asi).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AsiEkle.this);
                alertDialog.setTitle("Aşı Eklendi");

                alertDialog
                        .setMessage("Geri dönmek istiyor musunuz?")
                        .setCancelable(false)
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                AsiEkle.this.finish();
                            }
                        })
                        .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                                editTextAsiAdi.setText("");
                                editTextHastahaneAdi.setText("");
                            }
                        });

                AlertDialog alert = alertDialog.create();
                alert.show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AsiEkle.this, "Aşı Ekleme Esnasında Hata Oluştu !!", Toast.LENGTH_SHORT).show();
                    }
                });
        }else Toast.makeText(this, "Gerekli Alanları Doldurunuz !!", Toast.LENGTH_SHORT).show();


    }

    public void tarihAc(View v){

        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        datePickerDialog = new DatePickerDialog(AsiEkle.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editTextAsiTarihi.setText(dayOfMonth + "/" + (month+1) + "/" + year);
            }
        }, day,month,year);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
}


