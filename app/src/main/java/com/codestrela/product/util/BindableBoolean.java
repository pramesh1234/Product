package com.codestrela.product.util;

import androidx.databinding.BaseObservable;

/**
 * Created by Gourav on 09-02-2017.
 */

public class BindableBoolean extends BaseObservable {
    boolean mValue;

    public BindableBoolean(boolean mValue) {
        this.mValue = mValue;
    }

    public boolean get() {
        return mValue;
    }

    public void set(boolean value) {
        if (mValue != value) {
            this.mValue = value;
            notifyChange();
        }
    }
}
