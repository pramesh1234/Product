package com.codestrela.product.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.fragments.HomeFragment;
import com.codestrela.product.fragments.PhoneLoginFragment;
import com.codestrela.product.fragments.PhoneRegisterFragment;
import com.codestrela.product.util.BindableString;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;

public class PhoneLoginViewModel {
    PhoneLoginFragment phoneLoginFragment;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    String PhoneNumber;
    private static final String TAG = "PhoneLoginViewModel";
    private String verficationId;
    FirebaseFirestore db;
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String KEY = "documentIdKey";
    public BindableString verificationCode = new BindableString();
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;

    public PhoneLoginViewModel(PhoneLoginFragment phoneLoginFragment, String PhoneNumber) {
        this.phoneLoginFragment = phoneLoginFragment;
        this.PhoneNumber = PhoneNumber;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        startPhoneNumberVerification(PhoneNumber);
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                phoneLoginFragment.getActivity(),               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    private void verifycode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verficationId, code);
        SignInWithCredentials(credential);
    }

    private void SignInWithCredentials(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                            if (isNew) {
                                PhoneRegisterFragment fragment = new PhoneRegisterFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("phoneNo", PhoneNumber);
                                Log.e("PhoneLoginViewModel", "dumdum " + PhoneNumber);
                                fragment.setArguments(bundle);
                                fragment.addFragment((BaseActivity) phoneLoginFragment.getActivity(), fragment);
                            } else {
                                checkUser();
                                HomeFragment.addFragment((BaseActivity) phoneLoginFragment.getActivity());
                                Toast.makeText(phoneLoginFragment.getActivity(), "second", Toast.LENGTH_SHORT).show();
                            }
                            String userId = mAuth.getUid();
                            Map<String, Object> saveNo = new HashMap<>();
                            saveNo.put("Phone Number", "");
                            db.collection("users").document(userId).set(saveNo);
                        } else {
                            Toast.makeText(phoneLoginFragment.getActivity(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verficationId = s;
            mResendToken = forceResendingToken;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                verifycode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(phoneLoginFragment.getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };

    public void onLogin(View view) {
        String code = verificationCode.get();
        verifycode(code);

    }
    public void onResendClicked(View view){
        resendVerificationCode(PhoneNumber,mResendToken);
        Toast.makeText(phoneLoginFragment.getActivity(), "code sent successfully", Toast.LENGTH_SHORT).show();
    }
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                phoneLoginFragment.getActivity(),               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    public void checkUser(){
        Log.e(TAG, "+91"+PhoneNumber );
        Toast.makeText(phoneLoginFragment.getActivity(), "+91"+PhoneNumber, Toast.LENGTH_SHORT).show();
        db.collection("db_v1").document("barter_doc").collection("users").whereEqualTo("Phone Number",PhoneNumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().isEmpty()){
                                Toast.makeText(phoneLoginFragment.getContext(), "Not present", Toast.LENGTH_SHORT).show();
                            }else {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    saveData(phoneLoginFragment.getContext(),document.getId());
                                    Toast.makeText(phoneLoginFragment.getContext(), "Phone number is already present"+document.getId(), Toast.LENGTH_SHORT).show();

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
