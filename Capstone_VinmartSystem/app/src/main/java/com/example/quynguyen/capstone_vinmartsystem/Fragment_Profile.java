package com.example.quynguyen.capstone_vinmartsystem;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class Fragment_Profile extends Fragment {

    Button btnLogin;
    EditText edtFullname, edtUser , edtEmail, edtPassword;
    TextView txtRegister;
    String fullName , email, phone;
    Fragment_Home fragment_home;
    public static final int REQUEST_CODE = 1997;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile,container,false);

        edtUser = view.findViewById(R.id.edtUserLogin);
        edtPassword = view.findViewById(R.id.edtPasswordLogin);
        btnLogin = view.findViewById(R.id.btnLogin);
        txtRegister = view.findViewById(R.id.txtRegister);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),RegisterActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtUser.getText().toString().equals("admin") && edtPassword.getText().toString().equals("123456")){
                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("Account",new User(fullName,email,edtUser.getText().toString(),edtPassword.getText().toString(),phone));
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                User user = data.getExtras().getParcelable("Account");
                edtUser.setText(user.getUserName());
                edtPassword.setText(user.getPassWord());
                fullName = user.getFullName();
                email = user.getEmail();
                phone = user.getPhoneNumber();
            }
        }
    }
}
