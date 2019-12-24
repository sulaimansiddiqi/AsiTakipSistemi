package com.bakyuz.asitakipson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AsiListele extends AppCompatActivity implements CustomRecyclerViewAdapter.OnNoteListener{

    String eMail;
    String uID;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    RecyclerView recyclerView;
    CustomRecyclerViewAdapter adapter;
    DatabaseReference db;
    TextView baslikTv;
    List<Asi> AsiList = new ArrayList<>();
    DatabaseReference myRef2;
    int asiSayisi;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    @Override

    protected void onStart() {
        super.onStart();


   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asi_listele);

        Bundle extras = getIntent().getExtras();
        uID= extras.getString("sendUD");

        myRef2 = FirebaseDatabase.getInstance().getReference(uID).child("Asilar");
       baslikTv=findViewById(R.id.asiBaslik);
        recyclerView = findViewById(R.id.recyclerview);
        eMail= user.getEmail().toString();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        getir();


    }


    public void onNoteClick(int position) {
        Asi asi = AsiList.get(position);


       showUpdateDeleteDialog(asi.getAsiId(),asi.getAsiAdi(),asi.getHastahaneAdi(),asi.getAsiTarih());

    }
    private void showUpdateDeleteDialog(final String asiId , String asiName, String asiHastane, String asiTarih) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = dialogView.findViewById(R.id.EditTextAsiID);
        //editTextName.setHint(asiName);

        final   EditText spinnerGenre =  dialogView.findViewById(R.id.EditTextHastahaneAdi);
        //spinnerGenre.setHint(asiHastane);
        final TextView textViewtarihUpdate = dialogView.findViewById(R.id.textViewTarihUpdate);

        final Button buttonUpdate = dialogView.findViewById(R.id.buttonUpdateAsi);
        final  Button buttonDelete = dialogView.findViewById(R.id.buttonDeleteAsi);
        final  Button buttonTarih  = dialogView.findViewById(R.id.buttonTarihUpdate);

        buttonTarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(AsiListele.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        textViewtarihUpdate.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                }, day,month,year);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        dialogBuilder.setTitle("Güncelleme-Silme Ekranı");
        final  AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String asiAdi = editTextName.getText().toString().trim();
                String genre = spinnerGenre.getText().toString().trim();
                String tarih = textViewtarihUpdate.getText().toString().trim();
                if (!TextUtils.isEmpty(asiAdi) && !TextUtils.isEmpty(genre)) {
                    updateAsi(asiId,asiAdi, genre,tarih);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteAsi(asiId);
                b.dismiss();
            }
        });
    }

    private boolean deleteAsi(String id) {



        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(uID);
        Query applesQuery = ref.child("Asilar").child(id);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
                Toast.makeText(getApplicationContext(), "Aşı silindi.", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Hata!.\n"+databaseError.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        return true;
    }

    private boolean updateAsi(String id,String asiAdi, String hastahaneAdi,String tarih) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference(uID).child("Asilar").child(id);
        Asi asi = new Asi(id,asiAdi, hastahaneAdi,tarih);
        dR.setValue(asi);


        Toast.makeText(getApplicationContext(), "Aşı Güncellendi.", Toast.LENGTH_LONG).show();
        return true;
    }

   public void getir(){

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AsiList.clear();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        Asi asi1 = ds.getValue(Asi.class);
                        if (asi1 != null) {
                            AsiList.add(asi1);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                asiSayisi=AsiList.size();
                String asiText = String.format("Hoş Geldiniz ! %2d Adet Aşınız Listelenmiştir",asiSayisi);
                baslikTv.setText(asiText);
            }

            {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){}

        });

        adapter = new CustomRecyclerViewAdapter(AsiList,this);
       recyclerView.setAdapter(adapter);
    }





}
