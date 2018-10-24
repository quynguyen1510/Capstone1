package com.example.quynguyen.capstone_vinmartsystem;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    Button btnCreateAcc, btnCancel;
    EditText edtFullname, edtUser , edtEmail, edtPassword,edtPhoneNumber;
    User user ;
    Fragment_Profile fragment_profile = new Fragment_Profile();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtFullname = (EditText) findViewById(R.id.edtFullname);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPhoneNumber = (EditText) findViewById(R.id.edtPhone);
        btnCreateAcc = (Button) findViewById(R.id.btnCreateAcc);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                user = new User(edtFullname.getText().toString(),edtEmail.getText().toString(),edtUser.getText().toString(),edtPassword.getText().toString(),edtPhoneNumber.getText().toString());
                bundle.putParcelable("Account",user);
                fragment_profile.setArguments(bundle);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}
