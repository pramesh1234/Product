package com.codestrela.product.adapters;

import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.codestrela.product.R;
import com.codestrela.product.base.recycler.RecyclerBaseAdapter;
import com.codestrela.product.viewmodels.RowCommodityViewModel;
import com.codestrela.product.viewmodels.RowContactViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ContactAdapter extends RecyclerBaseAdapter implements Filterable {
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
    public void add(RowContactViewModel rowContactViewModel) {
        this.contactArrayList.add(rowContactViewModel);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<RowContactViewModel> filteredList=new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filteredList.addAll(contactArrayList);
            }else {
               for(RowContactViewModel contact: contactArrayList){
                   if(contact.contactName.get().toLowerCase().contains(constraint.toString().toLowerCase())){
                       filteredList.add(contact);
                   }
               }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactArrayList.clear();
                contactArrayList.addAll((Collection<? extends RowContactViewModel>) results.values);
                notifyDataSetChanged();
        }
    };
}
