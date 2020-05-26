package com.codestrela.product.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.codestrela.product.R;
import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.databinding.FragmentPhoneSignInBinding;
import com.codestrela.product.viewmodels.PhoneSignInViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static android.content.Context.MODE_PRIVATE;


public class PhoneSignInFragment extends Fragment {
FragmentPhoneSignInBinding binding;
PhoneSignInViewModel vm;
    SignInButton signInButton;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String KEY = "documentIdKey";
    GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new PhoneSignInViewModel(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_phone_sign_in, container, false);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        signInButton = (SignInButton) binding.getRoot().findViewById(R.id.signIn);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        db=FirebaseFirestore.getInstance();
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        binding.setVm(vm);
        return binding.getRoot();
    }

    public static void addFragment(BaseActivity activity) {
        activity.replaceFragment(new PhoneSignInFragment(), false  );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                //Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //        Log.d(TAG, "signInWithCredential:success");
                            boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                            if (isNew) {
                                GmailRegisterFragment.addFragment((BaseActivity) getActivity());
                                Toast.makeText(getActivity(), "first", Toast.LENGTH_SHORT).show();
                                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
                                if (acct != null) {
                                    String personEmail = acct.getEmail();
                                    checkUser(personEmail);
                                }
                            } else {
                                HomeFragment.addFragment((BaseActivity) getActivity());
                                Toast.makeText(getActivity(), "second", Toast.LENGTH_SHORT).show();
                                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
                                if (acct != null) {
                                    String personEmail = acct.getEmail();
                                    checkUser(personEmail);
                                }
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            //         Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //  Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            Toast.makeText(getActivity(), "Sign in Failed", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                tv.setGravity(Gravity.LEFT);
                return;
            }
            if (v instanceof ImageView) {
                ImageView tv = (ImageView) v;
                //   tv.setImageResource(R.drawable.bhuklogo);
                return;
            }
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    public void checkUser(String email){

        db.collection("db_v1").document("barter_doc").collection("users").whereEqualTo("Email",email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().isEmpty()){
                                Toast.makeText(getContext(), "Not present", Toast.LENGTH_SHORT).show();
                            }else {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    saveData(getContext(),document.getId());
                                    Toast.makeText(getContext(), "Phone number is already present"+document.getId(), Toast.LENGTH_SHORT).show();

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
