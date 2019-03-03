package com.example.lamchard.smartsms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class InscriptionActivity extends AppCompatActivity {

    TextView txtBtnConexion, txtBtnLoginVisiteur;
    EditText editPseudo, editEmail, editPhone, editPassword;
    Button btnInscription;
    ProgressDialog mDialog;
    //Firebase
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //make the activity on full screen
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                //WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_inscription);

        txtBtnConexion = findViewById(R.id.txt_connexion);
        txtBtnLoginVisiteur = findViewById(R.id.txtLoginVisiteur);
        btnInscription = findViewById(R.id.btnInscription);
        editPseudo = findViewById(R.id.editPseudo);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.edit_PhoneNumber);
        editPassword = findViewById(R.id.editPassword);

        mDialog = new ProgressDialog(InscriptionActivity.this);
        mDialog.setMessage("Veuillez patienter SVP...");

        mAuth = FirebaseAuth.getInstance();

        btnInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String pseudo = editPseudo.getText().toString();
                final String email = editEmail.getText().toString();
                final String password = editPassword.getText().toString();
                final String phone = editPhone.toString();

                if(email.isEmpty() || password.isEmpty() || pseudo.isEmpty() || phone.isEmpty()) {

                    showMessage("Veuillez vérifier tous les champs");

                }else{
                    mDialog.show();
                    CreateUserAccount(email, password);
                }

            }
        });

        txtBtnConexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent loginActivity = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(loginActivity);
                finish();
            }
        });

        txtBtnLoginVisiteur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        });
    }

    private void CreateUserAccount(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            mDialog.dismiss();
                            showMessage("Compte créer avec succès");
                            Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(mainActivity);
                            finish();
                        }
                        else {

                            mDialog.dismiss();
                            showMessage("Erreur lors de la création du compte\n" + task.getException().getMessage());
                        }
                    }
                });
    }

    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }
}
