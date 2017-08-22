package com.gw.webview.util;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gw.h5.WebClientWrapper;

/**
 * Created by GongWen on 17/8/21.
 */

public class WebClientWrapperCompat extends WebClientWrapper {
    private boolean isError = false;

    public WebClientWrapperCompat(WebViewClient webViewClient) {
        super(webViewClient);
    }

    @Deprecated
    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        isError = true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        isError = true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void onReceivedHttpError(
            WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
        isError = true;
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        mWebViewClient.onPageStarted(view, url, favicon);
        isError = false;
    }

    public void onPageFinished(WebView view, String url) {
        mWebViewClient.onPageFinished(view, url);
        if (isError) return;
        if (mH5ErrorListener != null && judgeError4Finished(view, url)) {
            mH5ErrorListener.onError(view, ERROR_UNKNOWN, "UNKNOWN", url);
        }
    }

    public boolean judgeError4Finished(WebView view, String url) {
        String title = view.getTitle();
        return url.endsWith(title)
                || url.contains("404")
                || url.contains("500")
                || url.contains("不存在")
                || url.contains("Error")
                || url.contains("错误")
                || url.contains("not available");
    }
}
