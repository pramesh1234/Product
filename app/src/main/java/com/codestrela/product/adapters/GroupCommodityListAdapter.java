package com.codestrela.product.adapters;

import com.codestrela.product.R;
import com.codestrela.product.base.recycler.RecyclerBaseAdapter;
import com.codestrela.product.viewmodels.RowGroupCommodityList;

import java.util.ArrayList;

public class GroupCommodityListAdapter extends RecyclerBaseAdapter {
    ArrayList<RowGroupCommodityList> list;

    public GroupCommodityListAdapter(ArrayList<RowGroupCommodityList> list) {
        this.list = list;
    }

    @Override
    protected Object getObjForPosition(int position) {
        return list.get(position);
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.row_group_commodity_list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public  void addAll(ArrayList<RowGroupCommodityList> arrayList){
        this.list.addAll(arrayList);
        notifyDataSetChanged();

    }
}
