package com.codestrela.product.viewmodels;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.fragments.GmailRegisterFragment;
import com.codestrela.product.fragments.GmailRegisterTwoFragment;
import com.codestrela.product.util.AppUtil;
import com.codestrela.product.util.BindableString;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class GmailRegisterViewModel {
    FirebaseFirestore db;
    GmailRegisterFragment gmailRegisterFragment;
    String phoneNumber;
    public BindableString phoneNo = new BindableString();
    public GmailRegisterViewModel(GmailRegisterFragment gmailRegisterFragment) {
        this.gmailRegisterFragment=gmailRegisterFragment;
        db=FirebaseFirestore.getInstance();
        phoneNumber="+91"+phoneNo.get();
        }
    public void onSubmit(View view){


        db.collection("db_v1").document("barter_doc").collection("users").whereEqualTo("Phone Number","+91"+phoneNo.get())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().isEmpty()){
                                Bundle bundle=new Bundle();
                                bundle.putString("phoneNo",phoneNo.get());
                                GmailRegisterTwoFragment gmailRegisterTwoFragment=new GmailRegisterTwoFragment();
                                Toast.makeText(gmailRegisterFragment.getContext(), "Not present", Toast.LENGTH_SHORT).show();
                                gmailRegisterTwoFragment.setArguments(bundle);
                                gmailRegisterTwoFragment.addFragment((BaseActivity) gmailRegisterFragment.getActivity(),gmailRegisterTwoFragment);
                            }else {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Toast.makeText(gmailRegisterFragment.getContext(), "Phone number is already present"+document.getId(), Toast.LENGTH_SHORT).show();

                                }

                            }
                        }
                    }
                });
    }
}
