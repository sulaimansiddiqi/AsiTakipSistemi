package com.example.asitakipson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AsiListele extends AppCompatActivity {
    String eMail;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ListView lv;
    CustomAdapter adapter;
    DatabaseReference db;
    TextView baslikTv;
    List<Asi> AsiList = new ArrayList<>();
    DatabaseReference myRef2;
    int asiSayisi;
    @Override

    protected void onStart() {
        super.onStart();

   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asi_listele);

        Bundle extras = getIntent().getExtras();
        String uID = extras.getString("sendUD");

        myRef2 = FirebaseDatabase.getInstance().getReference(uID).child("Asilar");
        baslikTv=findViewById(R.id.asiBaslik);
        lv = findViewById(R.id.lvListele);
        eMail= user.getEmail().toString();
        getir();



    }

    private void getir(){
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Toast.makeText(AsiListele.this, "OndataChange", Toast.LENGTH_SHORT).show();
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

        adapter = new CustomAdapter(this, R.layout.listview_item_layout, AsiList);
        lv.setAdapter(adapter);

    }


}
