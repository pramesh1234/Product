package com.codestrela.product.viewmodels;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.codestrela.product.adapters.ContactAdapter;
import com.codestrela.product.fragments.MyContactListFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyContactViewModel {
    MyContactListFragment myContactListFragment;
    RowContactViewModel viewModel;
    FirebaseFirestore db;
    private static StringBuffer no=new StringBuffer(" ");
    CollectionReference usersCref;
    ArrayList<RowContactViewModel> data;

    public ContactAdapter contactAdapter;
    private static final String TAG = "MyContactViewModel";

    public MyContactViewModel(MyContactListFragment myContactListFragment) {
        this.myContactListFragment = myContactListFragment;
        data = new ArrayList<RowContactViewModel>();
        db = FirebaseFirestore.getInstance();


    }

    public void getContacts() {
        Cursor cursor = myContactListFragment.getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        while (cursor.moveToNext()) {
             final String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            final String mobile = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            // Toast.makeText(getActivity(), "name: " + name, Toast.LENGTH_SHORT).show();
            viewModel = new RowContactViewModel();
            viewModel.contactName.set(name);
            viewModel.contactNumber.set(mobile);
            viewModel.visiblity.set(true);
            db.collection("user contact").document(mobile).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.getResult().exists()) {
                                CharSequence s=mobile;
                                no.append(s);
                                Log.e(TAG, "getContacts:" + name + "  phone " + mobile);
                                viewModel.visiblity.set(false);
                            } else {
                                viewModel.visiblity.set(true);
                            }
                        }
                    });
            data.add(viewModel);
            Log.e(TAG, "getContacts: " + no);
        }
        contactAdapter = new ContactAdapter(new ArrayList<RowContactViewModel>());
        contactAdapter.addAll(data);

    }


}
