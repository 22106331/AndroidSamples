package com.gw.webview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gw.webview.base.BaseToolbarActivity;
import com.gw.webview.js.JsInterface;
import com.gw.webview.util.Utils;

import butterknife.BindView;

/**
 * Created by GongWen on 17/8/22.
 * Android 使WebView支持HTML5 Video（全屏）播放的方法
 * http://blog.csdn.net/zrzlj/article/details/8050633
 */

public class SettingsWebViewActivity extends BaseToolbarActivity {
    private static final String TAG = "SettingsWebViewActivity";
    @BindView(R.id.webView)
    WebView webView;

    private View myView = null;
    private WebChromeClient.CustomViewCallback myCallback = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview_settings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setTitle("WebView Settings示例");

        WebSettings mWebSettings = webView.getSettings();
        //WebView 常用Settings
        mWebSettings.setUseWideViewPort(true);//让WebView支持可任意比例缩放
        mWebSettings.setSupportZoom(true);//让WebView支持缩放
        mWebSettings.setBuiltInZoomControls(true);//启用WebView内置缩放功能
        //设置缓存模式
        if (Utils.isNetworkAvailable()) {
            mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        mWebSettings.setAppCacheEnabled(true);//开启Application H5 Caches 功能
        mWebSettings.setAppCachePath(Utils.getWebViewCacheDir().getAbsolutePath());
        mWebSettings.setDomStorageEnabled(true);//让WebView支持DOM storage API
        mWebSettings.setPluginState(WebSettings.PluginState.ON);//让WebView支持插件

        mWebSettings.setDefaultTextEncodingName("utf-8");// 设置编码格式
        mWebSettings.setDisplayZoomControls(true);//设置WebView使用内置缩放机制时，是否展现在屏幕缩放控件上
//        mWebSettings.setUserAgentString("用户代理");//设置WebView的访问UserAgent
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//排版适应屏幕
        mWebSettings.setSavePassword(true);
        mWebSettings.setSaveFormData(true);
        mWebSettings.supportMultipleWindows();//多窗口
        mWebSettings.setAllowFileAccess(true);//设置允许访问文件
        mWebSettings.setNeedInitialFocus(true);//当webview调用requestFocus时为webview设置节点
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);////设置脚本是否允许自动打开弹窗
        mWebSettings.setLoadsImagesAutomatically(true);//支持自动加载图片
        mWebSettings.setGeolocationEnabled(true);//启用地理定位
        mWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);//设置渲染优先级
        webView.setInitialScale(35);//设置缩放比例
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);//设置滚动条隐藏

        mWebSettings.setJavaScriptEnabled(true);//启用javascript支持
        webView.addJavascriptInterface(new JsInterface(), "android");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.i(TAG, "newProgress: " + newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.i(TAG, "title: " + title);
            }

            //https://stackoverflow.com/questions/18271991/html5-video-remove-overlay-play-icon
            @Override
            public Bitmap getDefaultVideoPoster() {
                return Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888);
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
                Log.i(TAG, "onShowCustomView");
                if (myCallback != null) {
                    myCallback.onCustomViewHidden();
                    myCallback = null;
                    return;
                }
                ViewGroup parent = (ViewGroup) webView.getParent();
                parent.removeView(webView);
                parent.addView(view);
                myView = view;
                myCallback = callback;
            }

            @Override
            public void onHideCustomView() {
                super.onHideCustomView();
                Log.i(TAG, "onHideCustomView");
                if (myView != null) {
                    if (myCallback != null) {
                        myCallback.onCustomViewHidden();
                        myCallback = null;
                    }
                    ViewGroup parent = (ViewGroup) myView.getParent();
                    parent.removeView(myView);
                    parent.addView(webView);
                    myView = null;
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (Utils.isValidUrl(url)) {
                    webView.loadUrl(url);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        //下载文件
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                Log.i(TAG, "DownloadListener - url: " + url);
            }
        });
        webView.loadUrl("https://wap.baidu.com/");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        //WebView 设置
        super.onDestroy();
        if (webView != null) {
            //http://www.jianshu.com/p/613391e267b2
            //https://stackoverflow.com/questions/5267639/how-to-safely-turn-webview-zooming-on-and-off-as-needed
            webView.setVisibility(View.GONE);
            long timeout = ViewConfiguration.getZoomControlsTimeout();
            webView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    webView.destroy();
                }
            }, timeout);
        }
    }
}
