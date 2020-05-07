package com.codestrela.product.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codestrela.product.R;
import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.databinding.FragmentMyCommoditiesBinding;
import com.codestrela.product.viewmodels.MyCommoditiesViewModel;

public class MyCommoditiesFragment extends Fragment {
   MyCommoditiesViewModel vm;
   FragmentMyCommoditiesBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm=new MyCommoditiesViewModel(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_my_commodities, container, false);
        binding.setVm(vm);
        ((BaseActivity)getActivity()).setToolbarVisibility(true);
        return binding.getRoot();
    }
    public static void addFragment(BaseActivity activity) {
        activity.replaceFragment(new MyCommoditiesFragment(), false);
    }
}
