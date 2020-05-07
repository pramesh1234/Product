package com.codestrela.product.adapters;

import com.codestrela.product.R;
import com.codestrela.product.base.recycler.RecyclerBaseAdapter;
import com.codestrela.product.viewmodels.RowCommodityViewModel;

import java.util.ArrayList;

public class MyCommoditiesAdapter extends RecyclerBaseAdapter {

    ArrayList<RowCommodityViewModel> commodityViewModelArrayList;

    public MyCommoditiesAdapter(ArrayList<RowCommodityViewModel> commodityViewModelArrayList) {
        this.commodityViewModelArrayList = commodityViewModelArrayList;
    }


    @Override
    protected Object getObjForPosition(int position) {
        return commodityViewModelArrayList.get(position);
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.row_commodity;
    }

    @Override
    public int getItemCount() {
        return commodityViewModelArrayList.size();
    }
    public void addAll(ArrayList<RowCommodityViewModel> rowCommodityViewModels) {
        this.commodityViewModelArrayList.addAll(rowCommodityViewModels
        );
        notifyDataSetChanged();
    }
}
