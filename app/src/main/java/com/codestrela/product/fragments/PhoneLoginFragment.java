package com.codestrela.product.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.codestrela.product.R;
import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.databinding.FragmentPhoneLoginBinding;
import com.codestrela.product.viewmodels.PhoneLoginViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneLoginFragment extends Fragment {
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    String PhoneNumber;
    private String verficationId;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    PhoneLoginViewModel vm;
    FragmentPhoneLoginBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PhoneNumber = "+91" + getArguments().getString("phoneNumber");
        vm = new PhoneLoginViewModel(this, PhoneNumber);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_phone_login, container, false);
        binding.setVm(vm);
        ((BaseActivity) getActivity()).setToolbarVisibility(true);
        return binding.getRoot();
    }

    public static void addFragment(BaseActivity activity, Fragment fragment) {
        activity.replaceFragment(fragment, true);
    }
}
