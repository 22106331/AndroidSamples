package com.gw.h5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gw.h5.base.BaseToolbarActivity;

import butterknife.OnClick;

public class MainActivity extends BaseToolbarActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setTitle("MainActivity");
    }

    @OnClick(R.id.goErrorWebActivity)
    void onClick(View mView) {
        switch (mView.getId()) {
            case R.id.goErrorWebActivity:
                startActivity(new Intent(MainActivity.this, ErrorWebViewActivity.class));
                break;
        }
    }
}
