package com.codestrela.product.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codestrela.product.R;
import com.codestrela.product.databinding.FragmentListDialogBinding;
import com.codestrela.product.viewmodels.ListDialogViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListDialogFragment extends DialogFragment {
     FragmentListDialogBinding binding;
     ListDialogViewModel vm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm=new ListDialogViewModel(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_list_dialog, container, false);
        binding.setVm(vm);
        return binding.getRoot();
    }
}
