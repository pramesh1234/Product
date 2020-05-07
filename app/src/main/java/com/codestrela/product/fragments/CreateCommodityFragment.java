package com.codestrela.product.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.codestrela.product.R;
import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.databinding.FragmentCreateCommodityBinding;
import com.codestrela.product.viewmodels.CreateCommodityViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateCommodityFragment extends Fragment{
    CreateCommodityViewModel vm;
    FragmentCreateCommodityBinding binding;
    RadioButton modeRadioBtn;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm=new CreateCommodityViewModel(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_commodity, container, false);
        binding.setVm(vm);
        ((BaseActivity) getActivity()).setToolbarVisibility(false);

        RadioGroup modeRadioGroup=(RadioGroup) binding.getRoot().findViewById(R.id.rgMode);
        modeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rentId:
                              vm.mode.set("rent");
                        Toast.makeText(getContext(), "rent", Toast.LENGTH_SHORT).show();
                              break;
                    case R.id.saleId:
                                vm.mode.set("sale");
                        Toast.makeText(getContext(), "sale", Toast.LENGTH_SHORT).show();
                }
            }
        });
        RadioGroup typeRadioGroup=(RadioGroup) binding.getRoot().findViewById(R.id.rgType);
        typeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.publicRb:
                        vm.type.set("public");
                        Toast.makeText(getContext(), "public", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.privateRb:
                        vm.type.set("private");
                        Toast.makeText(getContext(), "private", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.groupRb:
                        vm.type.set("group");
                        Toast.makeText(getContext(), "group", Toast.LENGTH_SHORT).show();
                }
            }
        });
       return binding.getRoot();
    }
    public static void addFragment(BaseActivity activity) {
        activity.replaceFragment(new CreateCommodityFragment(), true);
    }
    }

