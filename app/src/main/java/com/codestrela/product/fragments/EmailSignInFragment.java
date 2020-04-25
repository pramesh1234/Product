package com.codestrela.product.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.codestrela.product.R;
import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.databinding.FragmentEmailSignInBinding;
import com.codestrela.product.viewmodels.EmailSignInViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmailSignInFragment extends Fragment {
    EmailSignInViewModel vm;
    FragmentEmailSignInBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new EmailSignInViewModel(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_email_sign_in, container, false);
        binding.setVm(vm);
        ((BaseActivity) getActivity()).setToolbarVisibility(true);
        return binding.getRoot();
    }

    public static void addFragment(BaseActivity activity) {
        activity.replaceFragment(new EmailSignInFragment(), true);
    }
}
