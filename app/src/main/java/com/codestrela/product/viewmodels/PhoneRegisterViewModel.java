package com.codestrela.product.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.fragments.GmailRegisterTwoFragment;
import com.codestrela.product.fragments.HomeFragment;
import com.codestrela.product.fragments.PhoneRegisterFragment;
import com.codestrela.product.util.BindableString;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class PhoneRegisterViewModel {
    public BindableString name = new BindableString();
    public BindableString email = new BindableString();
    FirebaseFirestore db;
    Map<String, Object> userData = new HashMap<>();
    PhoneRegisterFragment phoneRegisterFragment;
    FirebaseAuth mAuth;
    String phoneNumber;
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String KEY = "documentIdKey";

    public PhoneRegisterViewModel(PhoneRegisterFragment phoneRegisterFragment, String phoneNumber) {
        this.phoneRegisterFragment = phoneRegisterFragment;
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        this.phoneNumber = phoneNumber;

    }

    public void onPhoneRegister(View view) {

        db.collection("db_v1").document("barter_doc").collection("users").whereEqualTo("Email",email.get())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().isEmpty()){
                                userData.put("Name", name.get());
                                userData.put("Email", email.get());
                                userData.put("Phone Number", phoneNumber);
                                checkUser();
                                db.collection("db_v1").document("barter_doc").collection("users").document().set(userData);
                                HomeFragment.addFragment((BaseActivity) phoneRegisterFragment.getActivity());
                                }else {

                                    Toast.makeText(phoneRegisterFragment.getContext(), "Email is already present", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }
    public void checkUser(){

        db.collection("db_v1").document("barter_doc").collection("users").whereEqualTo("Phone Number",phoneNumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().isEmpty()){
                                Toast.makeText(phoneRegisterFragment.getContext(), "Not present", Toast.LENGTH_SHORT).show();
                            }else {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    saveData(phoneRegisterFragment.getContext(),document.getId());
                                    Toast.makeText(phoneRegisterFragment.getContext(), "Phone number is already present"+document.getId(), Toast.LENGTH_SHORT).show();

                                }

                            }
                        }
                    }
                });
    }

    public static void saveData(Context context, String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY, id);
        editor.apply();
    }
}
