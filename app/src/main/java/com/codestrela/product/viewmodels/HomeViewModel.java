package com.codestrela.product.viewmodels;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codestrela.product.adapters.HomeAdapter;
import com.codestrela.product.R;
import com.codestrela.product.adapters.MyCommoditiesAdapter;
import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.fragments.HomeFragment;
import com.codestrela.product.fragments.MyAccountFragment;
import com.codestrela.product.fragments.PhoneSignInFragment;
import com.codestrela.product.util.BindableString;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class HomeViewModel {
    FirebaseAuth mAuth;
    HomeFragment homeFragment;
    FirebaseFirestore db;
    DocumentReference documentReference;
    public BindableString cusName = new BindableString();
    public BindableString cusNumber = new BindableString();
    public BindableString cusEmail = new BindableString();
    GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "HomeViewModel";
    public HomeAdapter mViewPagerAdapter;

    public HomeViewModel(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        String userId = mAuth.getUid();
        documentReference = db.collection("users").document(userId);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(homeFragment.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(homeFragment.getActivity(),gso);
        showData();
        mViewPagerAdapter = new HomeAdapter(homeFragment.getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);


    }

    public void onSignOut(View view) {
        mAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(homeFragment.getActivity(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
        PhoneSignInFragment.addFragment((BaseActivity) homeFragment.getActivity());
    }

    public void showData() {

        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("Name");
                            String Email = documentSnapshot.getString("Email");
                            String Number = documentSnapshot.getString("Phone Number");
                            cusName.set(name);
                            cusEmail.set(Email);
                            cusNumber.set(Number);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
      public void onAccountClicked(View view){
          MyAccountFragment.addFragment((BaseActivity)homeFragment.getActivity());
      }
      public void onBottomSheet(View view){
          BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(homeFragment.getActivity());
          bottomSheetDialog.setContentView(R.layout.dialog_fragment);

          final MyCommoditiesAdapter myCommoditiesAdapter = new MyCommoditiesAdapter(new ArrayList<RowCommodityViewModel>());
          RecyclerView recyclerView = (RecyclerView) bottomSheetDialog.findViewById(R.id.recyclerView);
          FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();

          final ArrayList<RowCommodityViewModel> viewModels=new ArrayList<>();
          firebaseFirestore.collection("Commodities").addSnapshotListener(new EventListener<QuerySnapshot>() {
              @Override
              public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                  for(DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){
                      if(doc.getType()==DocumentChange.Type.ADDED){
                          String name=doc.getDocument().getString("name");
                          String price=doc.getDocument().getString("price");
                           RowCommodityViewModel viewModel =new RowCommodityViewModel();                          viewModel.name.set(name);
                          viewModel.price.set(price);
                          Log.e(TAG,"bottomsheet: "+name);
                          viewModels.add(viewModel);
                      }
                  }
                  myCommoditiesAdapter.addAll(viewModels);
              }
          });


          recyclerView.setHasFixedSize(true);
          recyclerView.setLayoutManager(new LinearLayoutManager(homeFragment.getActivity()));
          recyclerView.setAdapter(myCommoditiesAdapter);

          bottomSheetDialog.show();
      }

}
