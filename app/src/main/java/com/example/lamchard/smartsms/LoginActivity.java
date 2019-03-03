package com.example.lamchard.smartsms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextView txtTitle,txtBtnLostPassword,txtBtnLoginVisiteur, txtBtnCreateCompte;
    EditText editEmail, editPassword;
    Button btnConnexion;
    Typeface typefaceOpenSans, typefaceRighteous, typefaceCormorantBold;
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

        setContentView(R.layout.activity_login);

        txtTitle = findViewById(R.id.txt_Title);
        txtBtnLoginVisiteur = findViewById(R.id.txt_loginVisiteur);
        txtBtnLostPassword = findViewById(R.id.txt_fogetPassword);
        txtBtnCreateCompte = findViewById(R.id.txt_createCompte);
        btnConnexion = findViewById(R.id.btn_connexion);
        editEmail = findViewById(R.id.edit_email);
        editPassword = findViewById(R.id.edit_password);

        mDialog = new ProgressDialog(LoginActivity.this);
        mDialog.setMessage("Veuillez patienter SVP...");

        mAuth = FirebaseAuth.getInstance();

        //set fonts
        typefaceOpenSans = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");
        typefaceRighteous = Typeface.createFromAsset(getAssets(),"fonts/Righteous-Regular.ttf");
        typefaceCormorantBold = Typeface.createFromAsset(getAssets(), "fonts/CormorantUpright-Bold.ttf");

        //txtTitle.setTypeface(typefaceCormorantBold);
        txtBtnLostPassword.setTypeface(typefaceOpenSans);
        txtBtnLoginVisiteur.setTypeface(typefaceOpenSans);

        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = editEmail.getText().toString();
                final String password = editPassword.getText().toString();

                if(email.isEmpty() || password.isEmpty())
                {
                    showMessage("Veuillez vérifier tous les champs");
                }
                else {

                    mDialog.show();
                    singIn(email,password);
                }

            }
        });

        txtBtnLoginVisiteur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDialog.show();
                updateUI();
            }
        });

        txtBtnCreateCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent inscriptionActivity = new Intent(getApplicationContext(),InscriptionActivity.class);
                startActivity(inscriptionActivity);
                finish();
            }
        });
    }

    private void singIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    updateUI();
                }
                else{

                    mDialog.dismiss();
                    showMessage("Erreur lors de la connexion\n" + task.getException().getMessage());
                }
            }
        });
    }

    private void updateUI() {

        mDialog.dismiss();
        Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            updateUI();
        }
    }
}
