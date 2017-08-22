package com.gw.h5;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.gw.h5.base.BaseToolbarActivity;
import com.gw.h5.util.Utils;
import com.gw.h5.util.WebClientWrapperCompat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by GongWen on 17/8/22.
 */

public class ErrorWebViewActivity extends BaseToolbarActivity {

    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.goSearch)
    TextView goSearchView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.viewSwitcher)
    ViewSwitcher viewSwitcher;
    @BindView(R.id.webView)
    H5WebView webView;
    private boolean hasTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview_error;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView.setWebViewClient(new WebClientWrapperCompat(mWebViewClient));
        webView.setWebChromeClient(mWebChromeClient);
        webView.setH5ViewListener(mH5ErrorListener);
        toolbar.setTitle("WebView错误监听示例");
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            hasTitle = false;
            progressBar.setVisibility(View.VISIBLE);
            viewSwitcher.setDisplayedChild(0);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (!hasTitle) {
                toolbar.setTitle(view.getTitle());
            }
            progressBar.setVisibility(View.GONE);
        }
    };

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            hasTitle = true;
            toolbar.setTitle(view.getTitle());
        }
    };

    private IH5ErrorListener mH5ErrorListener = new IH5ErrorListener() {
        @Override
        public void onError(WebView view, int errorCode, String description, String failingUrl) {
            progressBar.setVisibility(View.GONE);
            viewSwitcher.setDisplayedChild(1);
            StringBuilder sb = new StringBuilder();
            sb.append("title: " + view.getTitle() + "\n");
            sb.append("errorCode: " + errorCode + "\n");
            sb.append("description: " + description + "\n");
            sb.append("failingUrl: " + failingUrl + "\n");
            Utils.toastShort(sb.toString());

        }
    };

    @OnClick(R.id.goSearch)
    void onClick(View mView) {
        if (TextUtils.isEmpty(editText.getText())) {
            Utils.toastShort("Url must be not null！");
        } else {
            String url = editText.getText().toString();
            if (Utils.isValidUrl(url)) {
                webView.loadUrl(url);
            } else {
                Utils.toastShort(url + " is not an invalid url！");
            }
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
