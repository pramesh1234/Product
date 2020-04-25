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
import com.codestrela.product.util.BindableString;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class EmailSignInViewModel {
    EmailSignInFragment emailSignInFragment;
    public BindableString userEmail=new BindableString();
    public BindableString userPassword=new BindableString();
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    public EmailSignInViewModel(EmailSignInFragment emailSignInFragment) {
        this.emailSignInFragment=emailSignInFragment;
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
    }
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(emailSignInFragment.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            HomeFragment.addFragment((BaseActivity)emailSignInFragment.getActivity());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(emailSignInFragment.getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // [END_EXCLUDE]
                        }

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }
    public void onSignInClicked(View view){

    }
    private boolean validateForm() {
        boolean valid = true;

        if (TextUtils.isEmpty(userEmail.get())) {
            Toast.makeText(emailSignInFragment.getActivity(), "Email is empty", Toast.LENGTH_SHORT).show();
            valid = false;
        }


        if (TextUtils.isEmpty(userPassword.get())) {
            Toast.makeText(emailSignInFragment.getActivity(), "Password Required", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }
    public void onNewUser(View view){
        EmailRegisterFragment.addFragment((BaseActivity) emailSignInFragment.getActivity());
    }
}
