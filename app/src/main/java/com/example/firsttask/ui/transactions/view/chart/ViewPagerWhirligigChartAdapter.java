package com.example.firsttask.ui.transactions.view.chart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerWhirligigChartAdapter extends FragmentStateAdapter {

    private ModelWhirligigChart model;
    private FragmentActivity fragmentActivit;

    public ViewPagerWhirligigChartAdapter(@NonNull FragmentActivity fragmentActivity, ModelWhirligigChart model) {
        super(fragmentActivity);
        this.model = model;
        this.fragmentActivit = fragmentActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        final FragmentManager fm = ((AppCompatActivity) fragmentActivit).getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (position == 0){
            ColumnChartFragment fragment = new ColumnChartFragment();
            Bundle b = new Bundle();
            b.putParcelable("UNIQUE_KEY", model);
            fragment.setArguments(b);
            return fragment;
        }
        else{
            PieChartFragment fragment = new PieChartFragment();
            Bundle b = new Bundle();
            b.putParcelable("UNIQUE_KEY", model);
            fragment.setArguments(b);
            return fragment;
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
