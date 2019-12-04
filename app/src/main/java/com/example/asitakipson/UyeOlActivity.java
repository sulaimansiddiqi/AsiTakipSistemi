package com.example.asitakipson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class UyeOlActivity extends AppCompatActivity {


    EditText editTextEMail;
    EditText editTextPass;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uye_ol);

        editTextEMail=findViewById(R.id.EditTextEMail);
        editTextPass=findViewById(R.id.EditTextPassword);
        mAuth = FirebaseAuth.getInstance();
    }

    public void btnKaydet(View v){
        kullaniciKaydet();


    }

    public void btnListele(View v){


    }
    private void kullaniciKaydet(){


        String kullaniciAdi = editTextEMail.getText().toString().trim();
        String sifre = editTextPass.getText().toString().trim();
        if (kullaniciAdi.isEmpty()) {
            editTextEMail.setError("E-Mail Alanı Zorunludur");
            editTextEMail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(kullaniciAdi).matches()) {

            editTextEMail.setError("Geçerli Bir E-Mail Girin");
            editTextEMail.requestFocus();
            return;
        }
        if (sifre.isEmpty()) {
            editTextPass.setError("Şifre Alanı Zorunludur");
            editTextPass.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(kullaniciAdi, sifre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Kullanıcı Kaydedildi", Toast.LENGTH_SHORT).show();

                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                        Toast.makeText(getApplicationContext(), "Bu E-Mail Daha Önce Kaydedilmiş", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}
