package com.example.quynguyen.capstone_vinmartsystem;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_Detail_Profile extends Fragment {

    TextView txtEmail, txtPhoneNumber, txtUserName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_profile,container,false);

        txtUserName = (TextView) view.findViewById(R.id.txtUserName);
        txtEmail = (TextView) view.findViewById(R.id.txtEmail);
        txtPhoneNumber = (TextView) view.findViewById(R.id.txtPhoneNumber);

        Bundle bundle = getArguments();

        if(bundle != null){
            User user = bundle.getParcelable("User");
            txtUserName.setText(user.getFullName());
            txtEmail.setText(user.getEmail());
            txtPhoneNumber.setText(user.getPhoneNumber());
        }else{
            Toast.makeText(getContext(),"Ko có ",Toast.LENGTH_LONG).show();
        }
        return view;
    }
}
