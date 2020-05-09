package com.codestrela.product.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.codestrela.product.adapters.ContactAdapter;
import com.codestrela.product.data.Contact;
import com.codestrela.product.fragments.MyContactListFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MyContactViewModel {
    MyContactListFragment myContactListFragment;
    FirebaseFirestore db;
    ArrayList<Contact> contacts;
    private static StringBuffer no=new StringBuffer(" ");
    CollectionReference usersCref;


    public ContactAdapter contactAdapter;
    private static final String TAG = "MyContactViewModel";
    public static final  String CONTACT_LIST="contact_list";

    public MyContactViewModel(MyContactListFragment myContactListFragment) {
        this.myContactListFragment = myContactListFragment;

        db = FirebaseFirestore.getInstance();
        contacts=new ArrayList<>();


    }

    public void getContacts() {
        Cursor cursor = myContactListFragment.getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        while (cursor.moveToNext()) {
             final String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            final String mobile = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            // Toast.makeText(getActivity(), "name: " + name, Toast.LENGTH_SHORT).show();




            db.collection("user contact").document(mobile).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                RowContactViewModel viewModel = new RowContactViewModel();
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    CharSequence s=mobile;
                                    no.append(s);
                                    viewModel.contactName.set(name);
                                    viewModel.contactNumber.set(mobile);
                                    contacts.add(new Contact(name,mobile));
                                    Log.e(TAG, "getContacts:" + name + "  phone " + mobile);
                                    viewModel.visiblity.set(false);
                                    Log.e(TAG,"array list: "+contacts.size());
                                } else {
                                    viewModel.contactName.set(name);
                                    viewModel.contactNumber.set(mobile);
                                    viewModel.visiblity.set(true);
                                }
                                SharedPreferences sharedPreferences=myContactListFragment.getActivity().getSharedPreferences("shared preference", Context.MODE_PRIVATE);
                                 SharedPreferences.Editor editor=sharedPreferences.edit();
                                Gson gson=new Gson();
                                String json=gson.toJson(contacts);
                                editor.putString(CONTACT_LIST,json);
                                editor.apply();
                                contactAdapter.add(viewModel);
                            } else {
                               Log.e(TAG,"unsuccessfull");
                            }
                           }
                    }
                    );
            Log.e(TAG, "getContacts: " + no);
            contactAdapter = new ContactAdapter(new ArrayList<RowContactViewModel>());

        }

    }
}
