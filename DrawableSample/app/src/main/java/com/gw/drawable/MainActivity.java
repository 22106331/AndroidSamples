package com.gw.drawable;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.gw.drawable.tool.StateDrawableUtil;
import com.gw.drawable.tool.Util;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.textView);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        //Util.setBackground(textView, StateDrawableUtil.getStateListDrawable(Color.RED, Color.BLUE));

        GradientDrawable normalDrawable = new GradientDrawable();
        normalDrawable.setShape(GradientDrawable.OVAL);
        normalDrawable.setColor(Color.RED);
        GradientDrawable pressedDrawable = new GradientDrawable();
        pressedDrawable.setShape(GradientDrawable.OVAL);
        pressedDrawable.setColor(Color.BLUE);
        Util.setBackground(textView, StateDrawableUtil.getStateListDrawable(normalDrawable, pressedDrawable));

        Util.setBackground(imageView, StateDrawableUtil.getTintListDrawable(this, R.mipmap.test, Color.RED, Color.BLUE));
    }
}
