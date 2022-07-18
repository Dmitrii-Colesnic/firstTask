package com.example.firsttask.ui.transactions.view.chart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.firsttask.databinding.FragmentWhirligigChartBinding;

public class WhirligigChartFragment extends Fragment {

    private FragmentWhirligigChartBinding binding;

    private ViewPagerWhirligigChartAdapter viewPagerAdapter;
    private ViewPager2 viewPager;

    private ModelWhirligigChart data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWhirligigChartBinding.inflate(inflater, container, false);

        Bundle b = getArguments();
        if(b!=null) {
            data = b.getParcelable("UNIQUE_KEY");
            createWhirligigChartAdapter();
        }

        return binding.getRoot();
    }

    private void createWhirligigChartAdapter() {
        viewPager = binding.viewPager2;
        viewPagerAdapter = new ViewPagerWhirligigChartAdapter(getActivity(), data);
        viewPager.setAdapter(viewPagerAdapter);
        binding.dotsIndicator.attachTo(viewPager);
    }
}