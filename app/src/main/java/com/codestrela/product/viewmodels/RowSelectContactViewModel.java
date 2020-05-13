package com.codestrela.product.viewmodels;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.codestrela.product.util.BindableBoolean;
import com.codestrela.product.util.BindableString;

public class RowSelectContactViewModel {
    private static final String TAG = "RowSelectContactViewMod";
    ListDialogViewModel vm = new ListDialogViewModel();
    public BindableString contactName = new BindableString();
    public BindableString contactNumber = new BindableString();
    public BindableBoolean contactCheckbox = new BindableBoolean();
    public String stringdata;
    ListDialogViewModel viewModel;


    public void onCli(View v) {
        Log.e(TAG, "cool " + contactCheckbox.get());
    }

    public RowSelectContactViewModel(ListDialogViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.isChecked()) {
                stringdata = buttonView.getText().toString();
                viewModel.data.add(stringdata);
                Log.e(TAG, "clicked " + vm.data.size());
                buttonView.getText();
            }

        }
    };
}
