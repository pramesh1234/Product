package com.codestrela.product.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.codestrela.product.adapters.SelectContactAdapter;
import com.codestrela.product.data.Contact;
import com.codestrela.product.fragments.ListDialogFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListDialogViewModel {
    private static final String TAG = "ListDialogViewModel";
    public static final String CONTACT_LIST = "contact_list";
    public RowSelectContactViewModel viewModel;
    ListDialogFragment listDialogFragment;
    ArrayList<RowSelectContactViewModel> selectViewmodel;
    ArrayList<Contact> contacts;
    public SelectContactAdapter adapter;

    public ListDialogViewModel(ListDialogFragment listDialogFragment) {
        this.listDialogFragment = listDialogFragment;
        adapter = new SelectContactAdapter(new ArrayList<RowSelectContactViewModel>());

        selectViewmodel = new ArrayList<>();
        onContact();
    }

    public void onContact() {
        contacts = new ArrayList<>();

        SharedPreferences sharedPreferences = listDialogFragment.getActivity().getSharedPreferences("shared preference", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(CONTACT_LIST, null);
        Toast.makeText(listDialogFragment.getActivity(), "" + json, Toast.LENGTH_SHORT).show();
        Type type = new TypeToken<ArrayList<Contact>>() {
        }.getType();
        contacts = gson.fromJson(json, type);
        Log.e(TAG, "array size: " + contacts.toString());
        for (int i = 0; i < contacts.size(); i++) {
            viewModel = new RowSelectContactViewModel();
            String name = contacts.get(i).getName();
            viewModel.contactName.set(name);
            viewModel.contactNumber.set(contacts.get(i).getNumber());
            selectViewmodel.add(viewModel);
        }
        adapter.addAll(selectViewmodel);

    }
}
