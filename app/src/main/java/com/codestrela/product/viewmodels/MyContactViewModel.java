package com.codestrela.product.viewmodels;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;

import com.codestrela.product.fragments.MyContactListFragment;

public class MyContactViewModel {
    MyContactListFragment myContactListFragment;
    public MyContactViewModel(MyContactListFragment myContactListFragment) {
        this.myContactListFragment=myContactListFragment;

    }



}
