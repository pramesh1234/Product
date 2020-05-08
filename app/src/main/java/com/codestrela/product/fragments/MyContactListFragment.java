package com.codestrela.product.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codestrela.product.R;
import com.codestrela.product.adapters.ContactAdapter;
import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.databinding.FragmentMyContactListBinding;
import com.codestrela.product.viewmodels.MyContactViewModel;
import com.codestrela.product.viewmodels.RowContactViewModel;

import java.util.ArrayList;

public class MyContactListFragment extends Fragment {
    FragmentMyContactListBinding binding;
    MyContactViewModel vm;
    public ContactAdapter contactAdapter;
    private static final String TAG = "MyContactListFragment";
    RecyclerView mRecyclerview;
    RowContactViewModel viewModel;
    ArrayList<RowContactViewModel> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new MyContactViewModel(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_contact_list, container, false);
        binding.setVm(vm);
        viewModel=new RowContactViewModel();
        mRecyclerview=(RecyclerView) binding.getRoot().findViewById(R.id.contactRecyclerview);
        data=new ArrayList<RowContactViewModel>();
        ((BaseActivity) getActivity()).setToolbarVisibility(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            getActivity().requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
           vm.getContacts();
        }
        return binding.getRoot();
    }

    private void getContacts() {
        Cursor cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String mobile = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
           // Toast.makeText(getActivity(), "name: " + name, Toast.LENGTH_SHORT).show();
            viewModel.contactName.set(name);
            viewModel.contactNumber.set(mobile);
            Log.e(TAG, "getContacts: "+name+"  phone "+mobile );
            data.add(viewModel);
        }
        contactAdapter=new ContactAdapter(data);


        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerview.setAdapter(contactAdapter);
    }

    public static void addFragment(BaseActivity activity) {
        activity.replaceFragment(new MyContactListFragment(), true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                vm.getContacts();
            }
        }
    }
}
