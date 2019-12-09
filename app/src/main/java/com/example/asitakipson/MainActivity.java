package com.example.asitakipson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    String uID;
    EditText editTextEMail;
    EditText editTextSifre;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextEMail=(EditText)findViewById(R.id.text_email);
        editTextSifre=(EditText)findViewById(R.id.edit_text_password);
        mAuth = FirebaseAuth.getInstance();
    }

    //ÜYE OL BUTTON
    public void btnUyeOl(View button) {
        startActivity(new Intent(MainActivity.this,UyeOlActivity.class));


    }

    public void btnGiris(View view){

        Giris();
    }


    private void Giris(){

        String eMail = editTextEMail.getText().toString().trim();
        String sifre = editTextSifre.getText().toString().trim();
        if (eMail.isEmpty()) {
            editTextEMail.setError("E-Mail Alanı Zorunludur");
            editTextEMail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(eMail).matches()) {

            editTextEMail.setError("Geçerli Bir E-Mail Girin");
            editTextEMail.requestFocus();
            return;
        }
        if (sifre.isEmpty()) {
            editTextSifre.setError("Şifre Alanı Zorunludur");
            editTextSifre.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(eMail,sifre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(MainActivity.this,ProfilActivity.class);
                    startActivity(intent);


                }else{
                    Toast.makeText(MainActivity.this, "Kullanıcı Adı veya Şifre Hatalı", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
