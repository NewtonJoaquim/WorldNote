package com.worldnote.pdm.worldnote;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static int REGISTER_USER = 0;
    public static int USER_CREATED = 1;

    EditText edtLogin;
    EditText edtPassword;
    Button btnLogin;
    TextView btnRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLogin = findViewById(R.id.edtLogin);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        updateUI(mAuth.getCurrentUser());
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Log.d("login", "starting ListNotes");
            Intent i = new Intent(LoginActivity.this, ListNotes.class);
            startActivity(i);
            finish();
        } else {
            Log.d("login", "user is null");
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnLogin) {
            //Log.d("flux", "doing login");
            //Intent i = new Intent(this, CreateNote.class);
            //startActivity(i);
            String email = edtLogin.getText().toString();
            String password = edtPassword.getText().toString();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("tag", "signInWithEmail:success");
                                updateUI(mAuth.getCurrentUser());
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("tag", "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else if (view == btnRegister){
            Intent i = new Intent(this, RegisterActivity.class);
            startActivityForResult(i, LoginActivity.REGISTER_USER);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("login", "onActivityResult");
        Log.d("login", "resultCode: " + resultCode);
        Log.d("login", "requestCode: " + requestCode);
        if (resultCode == USER_CREATED) {
            updateUI(mAuth.getCurrentUser());
        }
    }
}
