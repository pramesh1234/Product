package com.codestrela.product.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.codestrela.product.adapters.GroupListAdapter;
import com.codestrela.product.data.Contact;
import com.codestrela.product.data.Group;
import com.codestrela.product.fragments.GroupTabFragment;
import com.codestrela.product.fragments.ListDialogFragment;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GroupTabViewModel {
    FirebaseFirestore db;
    GroupTabFragment groupTabFragment;
    ArrayList<Contact> contacts;
    private static final String TAG = "GroupTabViewModel";
    public static final String GROUP_LIST="group_list";
    FragmentManager fm;
   public GroupListAdapter adapter;
    ListDialogFragment tv;
    RowGroupListViewModel viewModel;
    ArrayList<Group> group;
    ArrayList<RowGroupListViewModel> groupList;

    public GroupTabViewModel(GroupTabFragment groupTabFragment) {
        this.groupTabFragment=groupTabFragment;
        fm=groupTabFragment.getActivity().getSupportFragmentManager();
        db=FirebaseFirestore.getInstance();

        adapter=new GroupListAdapter(new ArrayList<RowGroupListViewModel>());
        tv=new ListDialogFragment();
        groupList();
    }
    public void groupList(){
        db.collection("groups").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                groupList =new ArrayList<>();
                group=new ArrayList<>();
                for(DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType()==DocumentChange.Type.ADDED){
                    String name=doc.getDocument().getString("groupName");
                    String groupId=doc.getDocument().getString("groupId");
                    viewModel=new RowGroupListViewModel(groupTabFragment,groupId);
                    viewModel.groupName.set(name);
                    group.add(new Group(viewModel.groupName.get(),groupId));
                    groupList.add(viewModel);
                }
            }
                SharedPreferences sharedPreferences=groupTabFragment.getActivity().getSharedPreferences("shared preference", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                Gson gson=new Gson();
                String json=gson.toJson(group);
                editor.putString(GROUP_LIST,json);
                editor.apply();
                adapter.addAll(groupList);
            }

        });
    }
    public void onCreateGroup(View view){
      tv.show(fm,"fma");
    }
}
