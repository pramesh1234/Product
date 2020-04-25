package com.codestrela.product.viewmodels;

import android.os.Bundle;
import android.view.View;

import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.fragments.EmailSignInFragment;
import com.codestrela.product.fragments.PhoneLoginFragment;
import com.codestrela.product.fragments.PhoneSignInFragment;
import com.codestrela.product.util.BindableString;

public class PhoneSignInViewModel {
    PhoneSignInFragment phoneSignInFragment;
    public BindableString phoneNumber = new BindableString();

    public PhoneSignInViewModel(PhoneSignInFragment phoneSignInFragment) {
        this.phoneSignInFragment = phoneSignInFragment;
    }

    public void onSubmitClicked(View view) {
        PhoneLoginFragment fragment = new PhoneLoginFragment();
        Bundle bundle = new Bundle();
        bundle.putString("phoneNumber", phoneNumber.get());
        fragment.setArguments(bundle);
        fragment.addFragment((BaseActivity) phoneSignInFragment.getActivity(), fragment);
    }

    public void onSignInClicked(View view) {
        EmailSignInFragment.addFragment((BaseActivity) phoneSignInFragment.getActivity());
    }

}
