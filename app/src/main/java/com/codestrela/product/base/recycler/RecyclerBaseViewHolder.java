package com.codestrela.product.base.recycler;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Gourav on 08-02-2017.
 */

public class RecyclerBaseViewHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding binding;

    public RecyclerBaseViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Object obj) {
        binding.setVariable(com.codestrela.product.BR.vm, obj);
        binding.executePendingBindings();
    }
}
