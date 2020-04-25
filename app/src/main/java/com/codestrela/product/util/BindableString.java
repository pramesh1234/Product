package com.codestrela.product.util;

import androidx.databinding.BaseObservable;


/**
 * Created by Gourav on 09-02-2017.
 */

public class BindableString extends BaseObservable {
    private String value;

    public String get() {
        return value != null ? value : "";
    }

    public void set(String value) {
        if (!Objects.equals(this.value, value)) {
            this.value = value;
            notifyChange();
        }
    }

    public boolean isEmpty() {
        return value == null || value.isEmpty();
    }
}