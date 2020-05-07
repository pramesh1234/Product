package com.codestrela.product.viewmodels;

import android.view.View;
import android.widget.Toast;

import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.fragments.CreateCommodityFragment;
import com.codestrela.product.fragments.MyAccountFragment;
import com.codestrela.product.fragments.MyCommoditiesFragment;
import com.codestrela.product.fragments.MyContactListFragment;

public class MyAccountViewModel {
    MyAccountFragment myAccountFragment;
    public MyAccountViewModel(MyAccountFragment myAccountFragment) {
        this.myAccountFragment=myAccountFragment;
    }
    public void onCreateCommodity(View view){
        CreateCommodityFragment.addFragment((BaseActivity) myAccountFragment.getActivity());
    }
    public void onMyCommodity(View view){
        MyCommoditiesFragment.addFragment((BaseActivity) myAccountFragment.getActivity());
    }
    public void onMyContact(View view){
        Toast.makeText(myAccountFragment.getContext(), "fafa", Toast.LENGTH_SHORT).show();
        MyContactListFragment.addFragment((BaseActivity) myAccountFragment.getActivity());
    }
}
