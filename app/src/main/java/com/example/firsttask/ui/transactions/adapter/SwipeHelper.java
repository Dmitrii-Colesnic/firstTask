package com.example.firsttask.ui.transactions.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public abstract class SwipeHelper extends ItemTouchHelper.SimpleCallback {

    int buttonWidth;
    private RecyclerView recyclerView;
    private List<MyButton> buttonList;
    private GestureDetector gestureDetector;
    private int swipePosition = -1;
    private float swipeThreshold = 0.5f;
    private Map<Integer, List<MyButton>> buttonBuffer;
    private Queue<Integer> removeQuery;

    private GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return super.onSingleTapUp(e);
        }
    };

    public SwipeHelper(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    private class MyButton {
        private String text;
        private int imageResId, textSize, color, pos;
        private RectF clickRegion;
        private MyButtonClickListener listener;
        private Context context;
        private Resources resources;

        public MyButton(Context context, String text, int imageResId, int textSize, int color, int pos, RectF clickRegion, MyButtonClickListener listener) {
            this.text = text;
            this.imageResId = imageResId;
            this.textSize = textSize;
            this.color = color;
            this.pos = pos;
            this.clickRegion = clickRegion;
            this.listener = listener;
            this.context = context;
            resources = context.getResources();
        }

        public boolean onClick(float x, float y){
            if (clickRegion != null  &&  clickRegion.contains(x,y)){
                listener.onClick(pos);
                return true;
            }
            return false;
        }


    }

}
