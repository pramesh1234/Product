package com.codestrela.product.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.codestrela.product.R;
import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.databinding.FragmentGmailRegisterBinding;
import com.codestrela.product.util.AppUtil;
import com.codestrela.product.viewmodels.GmailRegisterViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GmailRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GmailRegisterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    GmailRegisterViewModel vm;
    FragmentGmailRegisterBinding binding;
    EditText etPhone;
    String phoneNo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new GmailRegisterViewModel(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gmail_register, container, false);
        ((BaseActivity) getActivity()).setToolbarVisibility(true);
        binding.setVm(vm);
        etPhone = (EditText) binding.getRoot().findViewById(R.id.etPhone);
        phoneNo = etPhone.getText().toString();
        Button submitBtn = (Button) binding.getRoot().findViewById(R.id.subBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("phoneNo", phoneNo);
                GmailRegisterFragment gmailRegisterFragment = new GmailRegisterFragment();
                gmailRegisterFragment.setArguments(bundle);
                AppUtil.showToast(getActivity(), "hhh");
                GmailRegisterTwoFragment.addFragment((BaseActivity) getActivity(), gmailRegisterFragment);

            }
        });
        return binding.getRoot();

    }

    public static void addFragment(BaseActivity activity) {
        activity.replaceFragment(new GmailRegisterFragment(), true);
    }
}
