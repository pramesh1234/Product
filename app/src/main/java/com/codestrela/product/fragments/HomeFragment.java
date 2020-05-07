package com.codestrela.product.fragments;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.codestrela.product.R;
import com.codestrela.product.adapters.MyCommoditiesAdapter;
import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.databinding.FragmentHomeBinding;
import com.codestrela.product.viewmodels.HomeViewModel;
import com.codestrela.product.viewmodels.RowCommodityViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    HomeViewModel vm;
    FragmentHomeBinding binding;
    ImageView bottomBtn;
    RowCommodityViewModel viewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new HomeViewModel(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.setVm(vm);
        ViewPager viewPager = binding.getRoot().findViewById(R.id.event_view_pager);
        TabLayout tabLayout = binding.getRoot().findViewById(R.id.event_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        ((BaseActivity) getActivity()).setToolbarVisibility(false);
        bottomBtn=(ImageView) binding.getRoot().findViewById(R.id.addCom);
        bottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(getActivity());
                bottomSheetDialog.setContentView(R.layout.dialog_fragment);

                final MyCommoditiesAdapter myCommoditiesAdapter = new MyCommoditiesAdapter(new ArrayList<RowCommodityViewModel>());
                RecyclerView recyclerView = (RecyclerView) bottomSheetDialog.findViewById(R.id.recyclerView);
                FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
                viewModel =new RowCommodityViewModel();
                final ArrayList<RowCommodityViewModel> viewModels=new ArrayList<>();
                firebaseFirestore.collection("Commodities").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for(DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){
                            if(doc.getType()==DocumentChange.Type.ADDED){
                                String name=doc.getDocument().getString("name");
                                String price=doc.getDocument().getString("price");
                                Toast.makeText(getActivity(), ""+name, Toast.LENGTH_SHORT).show();
                                viewModel.name.set(name);
                                viewModel.price.set(price);
                                viewModels.add(viewModel);
                            }
                        }
                        myCommoditiesAdapter.addAll(viewModels);
                    }
                });


                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(myCommoditiesAdapter);

                bottomSheetDialog.show();
            }
        });
        return binding.getRoot();
    }

    public static void addFragment(BaseActivity activity) {
        activity.replaceFragment(new HomeFragment(), false);
    }
}