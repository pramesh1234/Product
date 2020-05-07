package com.codestrela.product.viewmodels;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.fragments.EmailRegisterFragment;
import com.codestrela.product.fragments.EmailSignInFragment;
import com.codestrela.product.fragments.HomeFragment;
import com.codestrela.product.util.BindableBoolean;
import com.codestrela.product.util.BindableString;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class EmailRegisterViewModel {
    FirebaseAuth mAuth;
    public BindableString name = new BindableString();
    public BindableString email = new BindableString();
    public BindableString number = new BindableString();
     boolean present=false;
    public BindableString password = new BindableString();
    public BindableBoolean running = new BindableBoolean();
    private static final String TAG = "EmailRegisterViewModel";
    FirebaseFirestore db;
    EmailRegisterFragment emailRegisterFragment;

    public EmailRegisterViewModel(EmailRegisterFragment emailRegisterFragment) {
        this.emailRegisterFragment = emailRegisterFragment;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    private void createAccount(final String email, String password) {
        // [START create_user_with_email]
        if (!validateForm()) {
            return;
        }
        running.set(false);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(emailRegisterFragment.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(emailRegisterFragment.getActivity(), "Authentication Succeed.", Toast.LENGTH_SHORT).show();
                            String userId = mAuth.getUid();
                            Map<String, Object> saveDetail = new HashMap<>();
                            saveDetail.put("Phone Number", number.get());
                            saveDetail.put("Email", email);
                            saveDetail.put("Name", name.get());
                            db.collection("users").document(userId).set(saveDetail);
                            Map<String, Object> userContact = new HashMap<>();
                            userContact.put("userId",userId);
                            db.collection("user contact").document(number.get()).set(userContact);

                            HomeFragment.addFragment((BaseActivity) emailRegisterFragment.getActivity());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(emailRegisterFragment.getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }

                        // [START_EXCLUDE]
                        running.set(true);
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    public void onRegister(View view) {

        Log.e(TAG, "onResponse: " + email.get());
        Query q1=db.collection("users").whereEqualTo("Email",email.get());
       q1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
               QuerySnapshot document = task.getResult();

               Toast.makeText(emailRegisterFragment.getActivity(), "present", Toast.LENGTH_SHORT).show();
               present=true;
           }
       });
       if(present){
           Toast.makeText(emailRegisterFragment.getActivity(), "teree", Toast.LENGTH_SHORT).show();
       }else {
           createAccount(email.get(), password.get());
       }
    }

    private boolean validateForm() {
        boolean valid = true;

        if (TextUtils.isEmpty(email.get())) {
            Toast.makeText(emailRegisterFragment.getActivity(), "Email is empty", Toast.LENGTH_SHORT).show();
            valid = false;
        }


        if (TextUtils.isEmpty(password.get())) {
            Toast.makeText(emailRegisterFragment.getActivity(), "Password Required", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }

    public void onAlreadyUser(View view) {
        EmailSignInFragment.addFragment((BaseActivity) emailRegisterFragment.getActivity());
    }
}

