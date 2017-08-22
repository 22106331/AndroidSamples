package com.gw.webview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gw.webview.base.BaseToolbarActivity;

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

    @OnClick({R.id.goErrorWebActivity, R.id.goJsWebActivity, R.id.goSettingsWebActivity})
    void onClick(View mView) {
        switch (mView.getId()) {
            case R.id.goErrorWebActivity:
                startActivity(new Intent(MainActivity.this, ErrorWebViewActivity.class));
                break;
            case R.id.goJsWebActivity:
                startActivity(new Intent(MainActivity.this, JsWebViewActivity.class));
                break;
            case R.id.goSettingsWebActivity:
                startActivity(new Intent(MainActivity.this, SettingsWebViewActivity.class));
                break;
        }
    }
}
