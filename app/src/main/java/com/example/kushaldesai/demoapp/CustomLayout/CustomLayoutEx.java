package com.example.kushaldesai.demoapp.CustomLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.kushaldesai.demoapp.R;

/**
 * Created by Kushal.Desai on 4/15/2016.
 */
public class CustomLayoutEx extends LinearLayout {

    private ImageView mImage;
    private View mView;

    public CustomLayoutEx(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CustomLayoutEx, 0, 0);
        String titleText = a.getString(R.styleable.CustomLayoutEx_titleText);
        int valueColor = a.getColor(R.styleable.CustomLayoutEx_valueColor,android.R.color.holo_blue_dark);
        a.recycle();

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.merging_layout, this, true);

        TextView title = (TextView) getChildAt(0);
        title.setText(titleText);

        mView = getChildAt(1);
        mView.setBackgroundColor(valueColor);

        mImage = (ImageView) getChildAt(2);
    }

    public CustomLayoutEx(Context context){
        this(context,null);
    }

    public void setValueColor(int color) {
        mView.setBackgroundColor(color);
    }

    public void setImageVisible(boolean visible) {
        mImage.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

}
