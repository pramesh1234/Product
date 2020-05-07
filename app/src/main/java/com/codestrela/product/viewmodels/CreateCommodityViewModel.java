package com.codestrela.product.viewmodels;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.codestrela.product.fragments.CreateCommodityFragment;
import com.codestrela.product.util.BindableBoolean;
import com.codestrela.product.util.BindableString;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateCommodityViewModel {
    public BindableString name=new BindableString();
    public BindableString unit=new BindableString();
    public BindableString price=new BindableString();
    public BindableString spection=new BindableString();
    public BindableString mode=new BindableString();
    public BindableString type=new BindableString();
    public FirebaseFirestore db;
    public FirebaseAuth mAuth;
    RadioGroup modeRadioGroup;
    CreateCommodityFragment createCommodityFragment;
    public CreateCommodityViewModel(CreateCommodityFragment createCommodityFragment) {
        this.createCommodityFragment=createCommodityFragment;
        db=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
    }

    public void onSubmit(View view){
        String userId=mAuth.getUid();
        Map<String, Object> commodity = new HashMap<>();
        commodity.put("name", name.get());
        commodity.put("unit", unit.get());
        commodity.put("price", price.get());
        commodity.put("spection",spection.get());
        commodity.put("mode",mode.get());
        commodity.put("type",type.get());

        db.collection("users").document(userId).collection("commodity_list").document().set(commodity);

    }
}
