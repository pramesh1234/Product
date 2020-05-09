package com.codestrela.product.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codestrela.product.data.Contact;
import com.codestrela.product.fragments.GroupTabFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GroupTabViewModel {
    GroupTabFragment groupTabFragment;
    ArrayList<Contact> contacts;
    private static final String TAG = "GroupTabViewModel";
    public GroupTabViewModel(GroupTabFragment groupTabFragment) {
        this.groupTabFragment=groupTabFragment;
    }
    public void onCreateGroup(View view){
       contacts=new ArrayList<>();
        SharedPreferences sharedPreferences=groupTabFragment.getActivity().getSharedPreferences("shared preference", Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("task list",null);
        Toast.makeText(groupTabFragment.getActivity(), ""+json, Toast.LENGTH_SHORT).show();
        Type type=new TypeToken<ArrayList<Contact>>(){}.getType();
        contacts=gson.fromJson(json,type);
        Log.e(TAG,"array size: "+contacts.size());
    }
}
