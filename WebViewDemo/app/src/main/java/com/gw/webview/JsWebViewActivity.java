package com.gw.webview;

import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.gw.webview.base.BaseToolbarActivity;
import com.gw.webview.js.JsInterface;
import com.gw.webview.util.Utils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by GongWen on 17/8/22.
 * Android中Java和JavaScript交互
 * http://droidyue.com/blog/2014/09/20/interaction-between-java-and-javascript-in-android/index.html
 * Android：你要的WebView与 JS 交互方式 都在这里了
 * http://blog.csdn.net/carson_ho/article/details/64904691
 */

public class JsWebViewActivity extends BaseToolbarActivity {
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview_js;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setTitle("WebView Js 交互示例");

        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JsInterface(), "android");
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("file:///android_asset/java_js_interaction.html");
    }

    @OnClick({R.id.sayHelloView, R.id.alertMessageView, R.id.sumToJavaView1, R.id.sumToJavaView2})
    void click(View mView) {
        String command;
        switch (mView.getId()) {
            //java调用js，无参数无返回值
            case R.id.sayHelloView:
                command = "javascript:sayHello()";
                webView.loadUrl(command);
                break;
            //java调用js，有参数无返回值
            case R.id.alertMessageView:
                command = "javascript:alertMessage(\"this is a content!\")";
                webView.loadUrl(command);
                break;
            //java调用js，有参数有返回值
            case R.id.sumToJavaView1:
                command = "javascript:sumToJava1(1,4)";
                webView.loadUrl(command);
                break;
            //java调用js，有参数有返回值
            case R.id.sumToJavaView2:
                command = "javascript:sumToJava(1,4)";
                webView.evaluateJavascript(command, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Utils.toastShort("The sum of a:" + 1 + ",b:" + 4 + " is " + value + "!");

                    }
                });
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
