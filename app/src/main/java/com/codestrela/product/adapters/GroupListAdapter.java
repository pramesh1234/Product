package com.codestrela.product.adapters;

import com.codestrela.product.R;
import com.codestrela.product.base.recycler.RecyclerBaseAdapter;
import com.codestrela.product.viewmodels.RowGroupListViewModel;

import java.util.ArrayList;

public class GroupListAdapter extends RecyclerBaseAdapter {
    ArrayList<RowGroupListViewModel> arrayList;

    public GroupListAdapter(ArrayList<RowGroupListViewModel> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    protected Object getObjForPosition(int position) {
        return arrayList.get(position);
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.row_group_list;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public void addAll(ArrayList<RowGroupListViewModel> viewModels){
        this.arrayList.addAll(viewModels);
        notifyDataSetChanged();
    }
}
