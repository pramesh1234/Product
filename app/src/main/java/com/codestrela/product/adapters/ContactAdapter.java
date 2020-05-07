package com.codestrela.product.adapters;

import android.widget.ArrayAdapter;

import com.codestrela.product.R;
import com.codestrela.product.base.recycler.RecyclerBaseAdapter;
import com.codestrela.product.viewmodels.RowCommodityViewModel;
import com.codestrela.product.viewmodels.RowContactViewModel;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerBaseAdapter {
    ArrayList<RowContactViewModel> contactArrayList;

    public ContactAdapter(ArrayList<RowContactViewModel> contactArrayList) {
        this.contactArrayList = contactArrayList;
    }

    @Override
    protected Object getObjForPosition(int position) {
        return contactArrayList.get(position);
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.row_contact;
    }

    @Override
    public int getItemCount() {
        return contactArrayList.size();
    }
    public void addAll(ArrayList<RowContactViewModel> rowContactViewModels) {
        this.contactArrayList.addAll(rowContactViewModels);
        notifyDataSetChanged();
    }
}
