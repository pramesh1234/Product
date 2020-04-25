package com.codestrela.product.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.codestrela.product.R;
import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.base.fragment.BaseFragment;
import com.codestrela.product.databinding.FragmentSplashBinding;
import com.codestrela.product.viewmodels.SplashViewModel;


public class SplashFragment extends BaseFragment {
    FragmentSplashBinding binding;
    SplashViewModel vm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new SplashViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_splash, container, false);
        binding.setVm(vm);
        return binding.getRoot();
    }

    public static void addFragment(BaseActivity activity) {
        activity.replaceFragment(new SplashFragment(), false);
    }
}
