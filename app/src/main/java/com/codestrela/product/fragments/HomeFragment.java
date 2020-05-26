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
        return binding.getRoot();
    }

    public static void addFragment(BaseActivity activity) {
        activity.replaceFragment(new HomeFragment(), true);
    }
}