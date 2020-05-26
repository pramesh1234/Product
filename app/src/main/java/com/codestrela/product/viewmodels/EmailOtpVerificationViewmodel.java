package com.codestrela.product.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.fragments.EmailOtpVerificationFragment;
import com.codestrela.product.fragments.GmailRegisterTwoFragment;
import com.codestrela.product.fragments.HomeFragment;
import com.codestrela.product.fragments.PhoneRegisterFragment;
import com.codestrela.product.util.BindableString;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;

public class EmailOtpVerificationViewmodel {
    String number,email,password,name;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private boolean mVerificationInProgress = false;
    private String verficationId;
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String KEY = "documentIdKey";
    FirebaseFirestore db;
    public BindableString verificationCode = new BindableString();
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailOtpVerificationVie";
    EmailOtpVerificationFragment emailOtpVerificationFragment;
    public EmailOtpVerificationViewmodel(EmailOtpVerificationFragment emailOtpVerificationFragment) {
        this.emailOtpVerificationFragment=emailOtpVerificationFragment;
        Bundle bundle=emailOtpVerificationFragment.getArguments();
        email=bundle.getString("email");
        number="+91"+bundle.getString("phoneNumber");
        password=bundle.getString("password");
        name=bundle.getString("name");
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        Toast.makeText(emailOtpVerificationFragment.getContext(), ""+email, Toast.LENGTH_SHORT).show();

    }
    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                emailOtpVerificationFragment.getActivity(),               // Activity (for callback binding)
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
                            createAccount(email,password);
                        } else {
                            Toast.makeText(emailOtpVerificationFragment.getActivity(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(emailOtpVerificationFragment.getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };

    public void onRegister(View view) {
        String code = verificationCode.get();
        verifycode(code);

    }
    public void onResendClick(View view){
        resendVerificationCode(number,mResendToken);
        Toast.makeText(emailOtpVerificationFragment.getActivity(), "code sent successfully", Toast.LENGTH_SHORT).show();
    }
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                emailOtpVerificationFragment.getActivity(),               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    private void createAccount(final String email, String password) {
        // [START create_user_with_email]

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(emailOtpVerificationFragment.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(emailOtpVerificationFragment.getActivity(), "Authentication Succeed.", Toast.LENGTH_SHORT).show();
                            Map<String, Object> saveDetail = new HashMap<>();
                            saveDetail.put("Phone Number", number);
                            saveDetail.put("Email", email);
                            saveDetail.put("Name", name);
                            db.collection("db_v1").document("barter_doc").collection("users").document().set(saveDetail);
                            checkUser();
                            HomeFragment.addFragment((BaseActivity) emailOtpVerificationFragment.getActivity());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(emailOtpVerificationFragment.getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }
    public void checkUser(){

        db.collection("db_v1").document("barter_doc").collection("users").whereEqualTo("Phone Number",number)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().isEmpty()){
                                 Toast.makeText(emailOtpVerificationFragment.getContext(), "Not present", Toast.LENGTH_SHORT).show();
                                 }else {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    saveData(emailOtpVerificationFragment.getContext(),document.getId());
                                    Toast.makeText(emailOtpVerificationFragment.getContext(), "Phone number is already present"+document.getId(), Toast.LENGTH_SHORT).show();

                                }

                            }
                        }
                    }
                });
    }

    public static void saveData(Context context,String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY, id);
        editor.apply();
    }
}
