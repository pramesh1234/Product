package com.codestrela.product.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codestrela.product.R;
import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.databinding.FragmentGroupCommodityListBinding;
import com.codestrela.product.databinding.FragmentGroupListDialogBinding;
import com.codestrela.product.viewmodels.GroupCommodityListViewModel;

public class GroupCommodityListFragment extends Fragment {
     FragmentGroupCommodityListBinding binding;
     GroupCommodityListViewModel viewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       viewModel=new GroupCommodityListViewModel(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_group_commodity_list, container, false);
        binding.setVm(viewModel);
        ((BaseActivity) getActivity()).setToolbarVisibility(true);
        return binding.getRoot();
    }
    public static void addFragment(BaseActivity activity, Fragment fragment) {
        activity.replaceFragment(fragment, true);
    }
}
