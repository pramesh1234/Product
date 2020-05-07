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
import com.codestrela.product.databinding.FragmentPhoneRegisterBinding;
import com.codestrela.product.viewmodels.PhoneRegisterViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneRegisterFragment extends Fragment {
    PhoneRegisterViewModel vm;
    FragmentPhoneRegisterBinding binding;
    String phoneNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phoneNumber = getArguments().getString("phoneNo");
        vm = new PhoneRegisterViewModel(this, phoneNumber);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_phone_register, container, false);
        binding.setVm(vm);
        ((BaseActivity) getActivity()).setToolbarVisibility(true);
        return binding.getRoot();
    }

    public static void addFragment(BaseActivity activity, Fragment fragment) {
        activity.replaceFragment(fragment, false);
    }
}
