package com.codestrela.product.viewmodels;

import android.view.View;

import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.fragments.CreateCommodityFragment;
import com.codestrela.product.fragments.MyAccountFragment;

public class MyAccountViewModel {
    MyAccountFragment myAccountFragment;
    public MyAccountViewModel(MyAccountFragment myAccountFragment) {
        this.myAccountFragment=myAccountFragment;
    }
    public void onCreateCommodity(View view){
        CreateCommodityFragment.addFragment((BaseActivity) myAccountFragment.getActivity());
    }
}
