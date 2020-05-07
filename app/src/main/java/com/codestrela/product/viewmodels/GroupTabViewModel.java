package com.codestrela.product.viewmodels;

import android.view.View;

import com.codestrela.product.fragments.GroupTabFragment;

public class GroupTabViewModel {
    GroupTabFragment groupTabFragment;
    public GroupTabViewModel(GroupTabFragment groupTabFragment) {
        this.groupTabFragment=groupTabFragment;
    }
    public void onCreateGroup(View view){
    
    }
}
