package com.codestrela.product.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.codestrela.product.adapters.SelectContactAdapter;
import com.codestrela.product.data.Contact;
import com.codestrela.product.fragments.ListDialogFragment;
import com.codestrela.product.util.BindableBoolean;
import com.codestrela.product.util.BindableString;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListDialogViewModel {
    public BindableString groupName = new BindableString();
    private static final String TAG = "ListDialogViewModel";
    public static final String CONTACT_LIST = "contact_list";
    public RowSelectContactViewModel viewModel;
    ArrayList<String> groupMembersid;
    public BindableBoolean progressShow = new BindableBoolean();
    public BindableBoolean contactShow = new BindableBoolean();

    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    ListDialogFragment listDialogFragment;


    ArrayList<RowSelectContactViewModel> selectViewmodel;
    ArrayList<Contact> contacts;

    public ArrayList<String> data = new ArrayList<>();

    public SelectContactAdapter adapter;

    public ListDialogViewModel() {
    }


    public ListDialogViewModel(ListDialogFragment listDialogFragment) {
        this.listDialogFragment = listDialogFragment;
        adapter = new SelectContactAdapter(new ArrayList<RowSelectContactViewModel>());
        firebaseFirestore = FirebaseFirestore.getInstance();
        selectViewmodel = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        groupMembersid = new ArrayList<>();
        progressShow.set(false);
        contactShow.set(true);
        onContact();
    }

    public void onContact() {
        contacts = new ArrayList<>();

        SharedPreferences sharedPreferences = listDialogFragment.getActivity().getSharedPreferences("shared preference", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(CONTACT_LIST, null);
        Type type = new TypeToken<ArrayList<Contact>>() {
        }.getType();
        contacts = gson.fromJson(json, type);
        Log.e(TAG, "array size: " + contacts.toString());
        for (int i = 0; i < contacts.size(); i++) {
            viewModel = new RowSelectContactViewModel(this);
            String name = contacts.get(i).getName();
            viewModel.contactName.set(name);
            viewModel.contactNumber.set(contacts.get(i).getNumber());
            selectViewmodel.add(viewModel);
        }

        adapter.addAll(selectViewmodel);

    }

    public void onSubmitContact(View view) {
        progressShow.set(true);
        contactShow.set(false);
        for (int i = 0; i < data.size(); i++) {


            firebaseFirestore.collection("user contact").document(data.get(i)).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    DocumentSnapshot document = task.getResult();
                                    String data = document.getString("userId");
                                    groupMembersid.add(data);
                                }
                            }
                            else{
                                Toast.makeText(listDialogFragment.getActivity(), "the task is unsuccessfull", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        delay(3);

    }

    public void delay(int seconds) {
        final int milliseconds = seconds * 1000;
        listDialogFragment.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DocumentReference doc = firebaseFirestore.collection("groups").document();
                        String groupId = doc.getId();
                        String userId = mAuth.getUid();
                        groupMembersid.add(userId);
                        Map<String, Object> group = new HashMap<>();
                        group.put("groupMembers", groupMembersid);
                        group.put("groupName", groupName.get());
                        group.put("groupAdmin", userId);
                        group.put("groupId", groupId);
                        doc.set(group);
                        listDialogFragment.getDialog().dismiss();
                    }
                }, milliseconds);
            }
        });
    }

    public void onCancelPressed(View view) {
        listDialogFragment.getDialog().dismiss();
    }

}