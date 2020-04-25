package com.codestrela.product.viewmodels;

import android.view.View;

import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.fragments.HomeFragment;
import com.codestrela.product.fragments.PhoneRegisterFragment;
import com.codestrela.product.util.BindableString;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PhoneRegisterViewModel {
    public BindableString name = new BindableString();
    public BindableString email = new BindableString();
    FirebaseFirestore db;
    Map<String, Object> userData = new HashMap<>();
    PhoneRegisterFragment phoneRegisterFragment;
    FirebaseAuth mAuth;
    String phoneNumber;

    public PhoneRegisterViewModel(PhoneRegisterFragment phoneRegisterFragment, String phoneNumber) {
        this.phoneRegisterFragment = phoneRegisterFragment;
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        this.phoneNumber = phoneNumber;

    }

    public void onPhoneRegister(View view) {
        userData.put("Name", name.get());
        userData.put("Email", email.get());
        userData.put("Phone Number", phoneNumber);
        db.collection("users").document(mAuth.getUid()).set(userData);
        HomeFragment.addFragment((BaseActivity) phoneRegisterFragment.getActivity());
    }
}
