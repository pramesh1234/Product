package com.codestrela.product.viewmodels;

import android.view.View;

import androidx.annotation.NonNull;

import com.codestrela.product.R;
import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.fragments.HomeFragment;
import com.codestrela.product.fragments.PhoneSignInFragment;
import com.codestrela.product.util.BindableString;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeViewModel {
    FirebaseAuth mAuth;
    HomeFragment homeFragment;
    FirebaseFirestore db;
    DocumentReference documentReference;
    public BindableString cusName = new BindableString();
    public BindableString cusNumber = new BindableString();
    public BindableString cusEmail = new BindableString();
    GoogleSignInClient mGoogleSignInClient;

    public HomeViewModel(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        String userId = mAuth.getUid();
        documentReference = db.collection("users").document(userId);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(homeFragment.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(homeFragment.getActivity(),gso);
        showData();

    }

    public void onSignOut(View view) {
        mAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(homeFragment.getActivity(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
        PhoneSignInFragment.addFragment((BaseActivity) homeFragment.getActivity());
    }

    public void showData() {

        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("Name");
                            String Email = documentSnapshot.getString("Email");
                            String Number = documentSnapshot.getString("Phone Number");
                            cusName.set(name);
                            cusEmail.set(Email);
                            cusNumber.set(Number);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}
