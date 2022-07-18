package com.example.firsttask.ui.whirligig;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.firsttask.R;

import java.util.ArrayList;

public class WhirligigViewPager extends PagerAdapter {

    private Context context;
    private ArrayList<WhirligigModel> array;

//    private int mPosition = 0;

    public WhirligigViewPager(Context context, ArrayList<WhirligigModel> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        WhirligigModel item = array.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.layout_prompt, container, false);

        TextView title = itemView.findViewById(R.id.tv_whirligig_title);
        TextView subTitle = itemView.findViewById(R.id.tv_whirligig_text);
        ImageView imageView = itemView.findViewById(R.id.iv_whirligig_promp);

        title.setText(item.getTitle());
        subTitle.setText(item.getSubTitle());
        imageView.setImageResource(item.getImage());

        container.addView(itemView);

//        if(mPosition >= array.size() - 1){
//            mPosition = 0;
//        }else {
//            mPosition++;
//        }

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object view) {
        container.removeView((View) view);
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

}
