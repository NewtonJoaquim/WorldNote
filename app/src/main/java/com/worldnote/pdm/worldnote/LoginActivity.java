package com.worldnote.pdm.worldnote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtLogin;
    EditText edtPassword;
    Button btnLogin;
    TextView btnRegister;
    ToggleButton tgSaveCred;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLogin = findViewById(R.id.edtLogin);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        tgSaveCred = findViewById(R.id.tgSaveCred);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnLogin) {
            Log.d("flux", "doing login");
        } else if (view == btnRegister){
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        }
    }
}
