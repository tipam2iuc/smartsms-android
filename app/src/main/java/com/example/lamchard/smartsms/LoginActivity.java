package com.example.lamchard.smartsms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    TextView txtTitle,txtBtnLostPassword,txtBtnLoginVisiteur, txtBtnCreateCompte;
    EditText editEmail, editPassword;
    Button btnConnexion;
    Typeface typefaceOpenSans, typefaceRighteous, typefaceCormorantBold;
    ProgressDialog mDialog;
    ImageButton btn_google;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private  final int DEFULT_PACKAGE_CODE = 1;


    //Firebase
    FirebaseAuth mAuth;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    private  final  int RC_SIGN_IN = 0;

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
        btn_google = findViewById(R.id.btn_google);

        mDialog = new ProgressDialog(LoginActivity.this);
        mDialog.setMessage("Veuillez patienter SVP...");

        mAuth = FirebaseAuth.getInstance();

        //getSMSCOnversationlist();

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

        // google auth
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
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
        final  String myPackageName = getPackageName();
        mDialog.dismiss();
        // set appli as default before start
        if (!Telephony.Sms.getDefaultSmsPackage(this).equals(myPackageName)) {
            Intent intent =
                    new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME,
                    myPackageName);
            startActivityForResult(intent, DEFULT_PACKAGE_CODE);
        }
        else {
            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainActivity);
            finish();
        }
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

    //////////////////////////////
    //////////////////////////////

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
                updateUI();
            }
        }
        else{
            // requestCode= DEFAULT_PACKAGE_CODE
            if(resultCode == RESULT_OK)
                updateUI();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI();
                        }

                        // ...
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void getSMSCOnversationlist() {
        Uri SMS_INBOX = Uri.parse("content://sms/conversations/");
        Cursor c = getContentResolver().query(SMS_INBOX, null,null, null, "date desc");

        String[] count = new String[c.getCount()];
        String[] snippet = new String[c.getCount()];
        String[] thread_id = new String[c.getCount()];


        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {

            count[i] = c.getString(c.getColumnIndexOrThrow("msg_count")).toString();
            thread_id[i] = c.getString(c.getColumnIndexOrThrow("thread_id")).toString();
            snippet[i] = c.getString(c.getColumnIndexOrThrow("snippet")).toString();
            Toast.makeText(this, count[i] + " - " + thread_id[i] + " - " + snippet[i]+ " - " , Toast.LENGTH_LONG).show();
            c.moveToNext();

        }
        c.close();
    }
}
