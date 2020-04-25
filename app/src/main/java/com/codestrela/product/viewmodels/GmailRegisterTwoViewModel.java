package com.codestrela.product.viewmodels;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.codestrela.product.R;
import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.fragments.GmailRegisterTwoFragment;
import com.codestrela.product.fragments.HomeFragment;
import com.codestrela.product.util.BindableString;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GmailRegisterTwoViewModel {
    GmailRegisterTwoFragment gmailRegisterTwoFragment;
    String PhoneNumber;
    private boolean mVerificationInProgress = false;
    public BindableString verificationCode=new BindableString();
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String verficationId;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public GmailRegisterTwoViewModel(GmailRegisterTwoFragment gmailRegisterTwoFragment, String PhoneNumber) {
        this.gmailRegisterTwoFragment = gmailRegisterTwoFragment;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        this.PhoneNumber=PhoneNumber;
        startPhoneNumberVerification(PhoneNumber);
    }
    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                gmailRegisterTwoFragment.getActivity(),               // Activity (for callback binding)
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
                                String userId = mAuth.getUid();
                                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(gmailRegisterTwoFragment.getActivity());
                                if (acct != null) {
                                    String personName = acct.getDisplayName();
                                    String personEmail = acct.getEmail();

                                    Map<String, Object> saveNo = new HashMap<>();
                                    saveNo.put("Phone Number", PhoneNumber);
                                    saveNo.put("Name", personName);
                                    saveNo.put("Email", personEmail);
                                    db.collection("users").document(userId).set(saveNo);
                                    HomeFragment.addFragment((BaseActivity) gmailRegisterTwoFragment.getActivity());
                                }
                            } else {

                                HomeFragment.addFragment((BaseActivity) gmailRegisterTwoFragment.getActivity());
                                Toast.makeText(gmailRegisterTwoFragment.getActivity(), "second", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(gmailRegisterTwoFragment.getActivity(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(gmailRegisterTwoFragment.getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };
    public void onBtnClicked(View view){
        String code = verificationCode.get();
        if (code.isEmpty() || code.length() < 6) {
            Toast.makeText(gmailRegisterTwoFragment.getActivity(), "code is empty", Toast.LENGTH_SHORT).show();
        } else {
            verifycode(code);
        }
    }
}
