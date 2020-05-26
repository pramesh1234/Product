package com.codestrela.product.viewmodels;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.fragments.GroupCommodityListFragment;
import com.codestrela.product.fragments.GroupTabFragment;
import com.codestrela.product.util.BindableString;

public class RowGroupListViewModel {
    private static final String TAG = "RowGroupListViewModel";
    public BindableString groupName = new BindableString();
    String groupId;
    GroupTabFragment groupTabFragment;

    public RowGroupListViewModel(GroupTabFragment groupTabFragment, String groupId) {
        this.groupId = groupId;
        this.groupTabFragment=groupTabFragment;
    }
    public void onGroupRowClicked(View view){
        Bundle bundle=new Bundle();
        bundle.putString("groupId",groupId);
        Log.e(TAG,"groupId :"+groupId);
        GroupCommodityListFragment fragment=new GroupCommodityListFragment();
        fragment.setArguments(bundle);
        fragment.addFragment((BaseActivity) groupTabFragment.getActivity(),fragment);
    }
}
