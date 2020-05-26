package com.codestrela.product.viewmodels;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.codestrela.product.adapters.GroupCommodityListAdapter;
import com.codestrela.product.fragments.GroupCommodityListFragment;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class GroupCommodityListViewModel {
    FirebaseFirestore db;
    private static final String TAG = "GroupCommodityListViewM";
    RowGroupCommodityList viewModel;
    ArrayList<RowGroupCommodityList> arrayList;
    GroupCommodityListFragment groupCommodityListFragment;
    public GroupCommodityListAdapter adapter;
    public GroupCommodityListViewModel(GroupCommodityListFragment groupCommodityListFragment) {
        this.groupCommodityListFragment=groupCommodityListFragment;
        db=FirebaseFirestore.getInstance();
        arrayList=new ArrayList<>();
        adapter=new GroupCommodityListAdapter(new ArrayList<RowGroupCommodityList>());
        getCommodityList();
    }
    public void getCommodityList(){
        Bundle bundle=groupCommodityListFragment.getArguments();
        String groupId=bundle.getString("groupId");
        db.collection("groups").document(groupId).collection("commodity_list").addSnapshotListener(
                new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e!=null){
                            Log.e(TAG,"error :"+e.getMessage());
                        }
                        for(DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){
                            if(doc.getType()==DocumentChange.Type.ADDED){
                                String name=doc.getDocument().getString("name");
                                String price=doc.getDocument().getString("price");


                                viewModel=new RowGroupCommodityList();
                                viewModel.commodityName.set(name);
                                viewModel.commodityPrice.set(price);
                                arrayList.add(viewModel);
                                Log.e(TAG,"name: "+name);
                            }
                        }
                        adapter.addAll(arrayList);
                    }
                }
        );
    }
}
