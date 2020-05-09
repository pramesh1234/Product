package com.codestrela.product.adapters;

import com.codestrela.product.R;
import com.codestrela.product.base.recycler.RecyclerBaseAdapter;
import com.codestrela.product.viewmodels.RowSelectContactViewModel;

import java.util.ArrayList;

public class SelectContactAdapter extends RecyclerBaseAdapter {
    ArrayList<RowSelectContactViewModel> viewmodel;

    public SelectContactAdapter(ArrayList<RowSelectContactViewModel> viewmodel) {
        this.viewmodel = viewmodel;
    }

    @Override
    protected Object getObjForPosition(int position) {
        return viewmodel.get(position);
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.row_select_contact;
    }

    @Override
    public int getItemCount() {
        return viewmodel.size();
    }
    public void addAll(ArrayList<RowSelectContactViewModel> viewmodel){
        this.viewmodel.addAll(viewmodel);
        notifyDataSetChanged();
    }
}
