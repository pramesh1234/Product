package com.codestrela.product.viewmodels;

import android.os.Bundle;
import android.view.View;

import com.codestrela.product.base.activity.BaseActivity;
import com.codestrela.product.fragments.GmailRegisterFragment;
import com.codestrela.product.fragments.GmailRegisterTwoFragment;
import com.codestrela.product.util.AppUtil;
import com.codestrela.product.util.BindableString;

public class GmailRegisterViewModel {
    GmailRegisterFragment gmailRegisterFragment;
    public BindableString phoneNo = new BindableString();
    public GmailRegisterViewModel(GmailRegisterFragment gmailRegisterFragment) {
        this.gmailRegisterFragment=gmailRegisterFragment;
        }
    public void onSubmit(View view){
        Bundle bundle=new Bundle();
        bundle.putString("phoneNo",phoneNo.get());
        GmailRegisterTwoFragment gmailRegisterTwoFragment=new GmailRegisterTwoFragment();
        gmailRegisterTwoFragment.setArguments(bundle);
        gmailRegisterTwoFragment.addFragment((BaseActivity) gmailRegisterFragment.getActivity(),gmailRegisterTwoFragment);

    }
}
