package com.codestrela.product.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.codestrela.product.data.Contact;
import com.codestrela.product.fragments.GroupTabFragment;
import com.codestrela.product.fragments.ListDialogFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GroupTabViewModel {
    GroupTabFragment groupTabFragment;
    ArrayList<Contact> contacts;
    private static final String TAG = "GroupTabViewModel";
    public static final String CONTACT_LIST="contact_list";
    FragmentManager fm;
    ListDialogFragment tv;

    public GroupTabViewModel(GroupTabFragment groupTabFragment) {
        this.groupTabFragment=groupTabFragment;
        fm=groupTabFragment.getActivity().getSupportFragmentManager();
        tv=new ListDialogFragment();
    }
    public void onCreateGroup(View view){
      tv.show(fm,"fma");
    }
}
