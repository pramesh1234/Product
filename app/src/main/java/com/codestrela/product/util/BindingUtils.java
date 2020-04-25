package com.codestrela.product.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TimePicker;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.core.util.Pair;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.codestrela.product.R;

import java.util.Calendar;

/**
 * Created by Gourav on 09-02-2017.
 */

public class BindingUtils {

    @BindingConversion
    public static String convertBindableToString(BindableString bindableString) {
        return bindableString.get();
    }

    @BindingAdapter({"app:binding"})
    public static void bindEditText(AppCompatAutoCompleteTextView view, final BindableString bindableString) {
        Pair<BindableString, TextWatcherAdapter> pair = (Pair) view.getTag(R.id.bound_observable);
        if (pair == null || pair.first != bindableString) {
            if (pair != null) {
                view.removeTextChangedListener(pair.second);
            }
            TextWatcherAdapter watcher = new TextWatcherAdapter() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    bindableString.set(s.toString());
                }
            };
            view.setTag(R.id.bound_observable, new Pair<>(bindableString, watcher));
            view.addTextChangedListener(watcher);
        }
        String newValue = bindableString.get();
        if (!view.getText().toString().equals(newValue)) {
            view.setText(newValue);
        }
    }

    @BindingAdapter({"app:binding"})
    public static void bindEditText(EditText view, final BindableString bindableString) {
        Pair<BindableString, TextWatcherAdapter> pair = (Pair) view.getTag(R.id.bound_observable);
        if (pair == null || pair.first != bindableString) {
            if (pair != null) {
                view.removeTextChangedListener(pair.second);
            }
            TextWatcherAdapter watcher = new TextWatcherAdapter() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    bindableString.set(s.toString());
                }
            };
            view.setTag(R.id.bound_observable, new Pair<>(bindableString, watcher));
            view.addTextChangedListener(watcher);
        }
        String newValue = bindableString.get();
        if (!view.getText().toString().equals(newValue)) {
            view.setText(newValue);
        }
    }

    @BindingAdapter({"app:bindVisibility"})
    public static void bindVisibility(View view, final BindableBoolean bindableBoolean) {
        if (bindableBoolean.get()) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter({"app:bindVisibility"})
    public static void bindVisibility(View view, boolean bindableBoolean) {
        if (bindableBoolean) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("setRecyclerViewAdapter")
    public static void setRecyclerViewAdapter(RecyclerView view, RecyclerView.Adapter adapter) {
        view.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        view.setLayoutManager(mLayoutManager);
        view.setAdapter(adapter);
    }

    @BindingAdapter("setRecyclerViewGridAdapter")
    public static void setRecyclerViewGridAdapter(RecyclerView view, RecyclerView.Adapter adapter) {
        view.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 3);
        view.setLayoutManager(mLayoutManager);
        view.setAdapter(adapter);
    }

    @BindingAdapter("setRecyclerViewHorizontalAdapter")
    public static void setRecyclerViewHorizontalAdapter(RecyclerView view, RecyclerView.Adapter adapter) {
        view.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        view.setLayoutManager(linearLayoutManager);
        view.setAdapter(adapter);
    }

    @BindingAdapter({"android:src"})
    public static void bindImageRes(ImageView view, int imgRes) {
        view.setImageResource(imgRes);
    }

    @BindingAdapter({"android:src"})
    public static void bindImageBitmap(ImageView view, Bitmap imgBitmap) {
        view.setImageBitmap(imgBitmap);
    }


    @BindingAdapter({"listener", "status"})
    public static void listener(SwitchCompat view, CompoundButton.OnCheckedChangeListener checkedChangeListener, BindableBoolean bindableBoolean) {
        view.setOnCheckedChangeListener(checkedChangeListener);
        view.setChecked(bindableBoolean.get());
    }

    @BindingAdapter({"listener", "status"})
    public static void listener(AppCompatCheckBox view, CompoundButton.OnCheckedChangeListener checkedChangeListener, BindableBoolean bindableBoolean) {
        view.setOnCheckedChangeListener(checkedChangeListener);
        view.setChecked(bindableBoolean.get());
    }

    @BindingAdapter({"loadUrl"})
    public static void loadUrl(ImageView view, BindableString imgUrl) {
        if (imgUrl.get().length() > 0) {
            Glide.with(view.getContext())
                    .load(imgUrl.get())
                    .fitCenter()
                    .placeholder(R.color.colorAccent)
                    .into(view);
        }
    }

    @BindingAdapter("setImageRecyclerViewAdapter")
    public static void setImageRecyclerViewAdapter(RecyclerView view, RecyclerView.Adapter adapter) {
        view.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        view.setLayoutManager(mLayoutManager);
        view.setAdapter(adapter);
    }

    @BindingAdapter({"app:bindTextWatcher"})
    public static void bindTextWatcher(EditText view, TextWatcher textWatcher) {
        view.addTextChangedListener(textWatcher);
    }

    @BindingAdapter({"app:setRadioGroupListener"})
    public static void setRadioGroupListener(RadioGroup v, RadioGroup.OnCheckedChangeListener onCheckedChangeListener) {
        v.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    @BindingAdapter({"android:onDateChanged"})
    public static void setDate(DatePicker view, DatePicker.OnDateChangedListener listener) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        view.init(year, month, day, listener);
        view.updateDate(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        view.setMinDate(calendar.getTimeInMillis() - 1000);
    }

    @BindingAdapter({"android:OnTimeChangedListener"})
    public static void setTime(TimePicker view, TimePicker.OnTimeChangedListener listener) {
        view.setOnTimeChangedListener(listener);
    }

    @BindingAdapter({"setAdapter"})
    public static void setAdapter(ViewPager viewPager, FragmentStatePagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }

    @BindingAdapter({"setExpandableAdapter", "setOnChildClickListener"})
    public static void setExpandableAdapter(ExpandableListView expandableListView, BaseExpandableListAdapter adapter, ExpandableListView.OnChildClickListener onChildClickListener) {
        expandableListView.setAdapter(adapter);
        expandableListView.setOnChildClickListener(onChildClickListener);
    }

    @BindingAdapter({"app:setSpinnerAdapter"})
    public static void setSpinnerAdapter(AppCompatSpinner spinner, ArrayAdapter<String> adapter) {
        spinner.setAdapter(adapter);
    }

    @BindingAdapter({"app:setAutoAdapter"})
    public static void setAutoAdapter(AutoCompleteTextView autoCompleteTextView, ArrayAdapter adapter) {
        autoCompleteTextView.setAdapter(adapter);
    }

    @BindingAdapter({"app:setCheckBoxListener"})
    public static void setCheckBoxListener(AppCompatCheckBox checkBox, final OnCheckListener onCheckListener) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onCheckListener.onCheckChanged(isChecked, (String) buttonView.getText());
            }
        });
    }

    @BindingAdapter({"app:setRatingBarChangeListener", "app:setRating"})
    public static void setRatingBarChangeListener(AppCompatRatingBar appCompatRatingBar, RatingBar.OnRatingBarChangeListener listener, BindableString rating) {
        appCompatRatingBar.setRating(Float.parseFloat(rating.get()));
        appCompatRatingBar.setOnRatingBarChangeListener(listener);
    }

    @BindingAdapter({"app:setMax", "app:setCompleted"})
    public static void setProgressBar(ProgressBar progressBar, BindableString max, BindableString completed) {
        progressBar.setMax(Integer.parseInt(max.get()));
        progressBar.setProgress(Integer.parseInt(completed.get()));
    }

    @BindingAdapter({"app:setBg"})
    public static void setBg(View view, int res) {
        view.setBackgroundResource(res);
    }

    @BindingAdapter({"app:setBgColor"})
    public static void setBgColor(View view, BindableString res) {
        view.setBackgroundColor(Color.parseColor(res.get()));
    }

    @BindingAdapter({"app:setBgColor"})
    public static void setBgColor(CardView view, BindableString res) {
        if (res.get().length() > 0)
            view.setCardBackgroundColor(Color.parseColor(res.get()));
    }

    @BindingAdapter({"app:setBg"})
    public static void setBg(View view, BindableString res) {
        view.setBackgroundResource(Integer.parseInt(res.get()));
    }
    @BindingAdapter({"app:pager"})
    public static void bindViewPagerTabs(final TabLayout view, final ViewPager pagerView) {
        view.setupWithViewPager(pagerView, true);
    }
}