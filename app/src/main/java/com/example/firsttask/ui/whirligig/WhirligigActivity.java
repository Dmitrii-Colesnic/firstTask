package com.example.firsttask.ui.whirligig;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.firsttask.R;
import com.example.firsttask.databinding.ActivityWhirligigBinding;
import com.example.firsttask.ui.authentication.view.AuthenticationActivity;

import java.util.ArrayList;

public class WhirligigActivity extends AppCompatActivity {

    private ActivityWhirligigBinding binding;

    private int mPosition=0;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWhirligigBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<WhirligigModel> array = new ArrayList<>();

        mViewPager = binding.viewPagerPlaceWhirligig;
        mViewPager.setAdapter(new WhirligigViewPager(this, defineWhirligigModelArray()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                drawCircles(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        mViewPager.setCurrentItem(mPosition);
        drawCircles(mPosition);

        binding.tvWhirligigNext.setOnClickListener(l->{
            if( mPosition==3 ){
                startActivity(new Intent(this, AuthenticationActivity.class));
                finish();
            } else {
                mPosition++;
                mViewPager.setCurrentItem(mPosition);
                drawCircles(mPosition);
            }
        });

        binding.tvWhirligigSkip.setOnClickListener(l->{
            startActivity(new Intent(this, AuthenticationActivity.class));
            finish();
        });
    }

    private ArrayList<WhirligigModel> defineWhirligigModelArray(){
        ArrayList<WhirligigModel> array = new ArrayList<>();
        WhirligigModel promp1 = new WhirligigModel(R.string.utility_billing, R.string.utility_billing_text, R.drawable.onboarding1);
        array.add(promp1);
        WhirligigModel promp2 = new WhirligigModel(R.string.quick_payments, R.string.quick_payments_text, R.drawable.onboarding2);
        array.add(promp2);
        WhirligigModel promp3 = new WhirligigModel(R.string.store_bank_cards, R.string.store_bank_cards_text, R.drawable.onboarding3);
        array.add(promp3);
        WhirligigModel promp4 = new WhirligigModel(R.string.pay_with_one_invoice, R.string.pay_with_one_invoice_text, R.drawable.onboarding4);
        array.add(promp4);

        return array;
    };

    private void drawCircles(int position) {

        mPosition = position;

        binding.circle1.setBackgroundResource(R.drawable.whirligig_progress_circle_background);

        binding.circle2.setBackgroundResource(R.drawable.whirligig_progress_circle_background);

        binding.circle3.setBackgroundResource(R.drawable.whirligig_progress_circle_background);

        binding.circle4.setBackgroundResource(R.drawable.whirligig_progress_circle_background);

        if (position == 0) {
            binding.circle1.setBackgroundResource(R.drawable.whirligig_progress_circle_selected_background);
            binding.tvWhirligigNext.setText(R.string.Next);
        } else if (position == 1) {
            binding.circle2.setBackgroundResource(R.drawable.whirligig_progress_circle_selected_background);
            binding.tvWhirligigNext.setText(R.string.Next);
        } else if (position == 2) {
            binding.circle3.setBackgroundResource(R.drawable.whirligig_progress_circle_selected_background);
            binding.tvWhirligigNext.setText(R.string.Next);

        } else if (position == 3) {
            binding.circle4.setBackgroundResource(R.drawable.whirligig_progress_circle_selected_background);
            binding.tvWhirligigNext.setText(R.string.finish);
        }

    }
}