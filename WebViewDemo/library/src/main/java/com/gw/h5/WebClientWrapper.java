package com.gw.h5;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by GongWen on 17/8/21.
 */

public class WebClientWrapper extends WebViewClient {
    protected WebViewClient mWebViewClient;
    protected IH5ErrorListener mH5ErrorListener;

    public WebClientWrapper(WebViewClient webViewClient) {
        this.mWebViewClient = webViewClient;
    }

    public void setWebViewClient(WebViewClient mWebViewClient) {
        this.mWebViewClient = mWebViewClient;
    }

    public void setH5ViewListener(IH5ErrorListener mH5ErrorListener) {
        this.mH5ErrorListener = mH5ErrorListener;
    }

    @Deprecated
    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {
        mWebViewClient.onReceivedError(view, errorCode, description, failingUrl);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M && mH5ErrorListener != null) {
            mH5ErrorListener.onError(view, errorCode, description.toString(), failingUrl);
        }
    }

    // These errors usually indicate inability to connect to the server.
    @TargetApi(Build.VERSION_CODES.M)
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        mWebViewClient.onReceivedError(view, request, error);
        if (mH5ErrorListener != null) {
            mH5ErrorListener.onError(view, error.getErrorCode(), error.getDescription().toString(), request.getUrl().toString());

        }
    }

    /**
     * Notify the host application that an HTTP error has been received from the server
     * while loading a resource
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void onReceivedHttpError(
            WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        mWebViewClient.onReceivedHttpError(view, request, errorResponse);
        if (mH5ErrorListener != null) {
            mH5ErrorListener.onError(view, errorResponse.getStatusCode(), errorResponse.getReasonPhrase(), request.getUrl().toString());
        }
    }


    //*********************************************************//
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        mWebViewClient.onPageStarted(view, url, favicon);
    }

    public void onPageFinished(WebView view, String url) {
        mWebViewClient.onPageFinished(view, url);
    }

    @Deprecated
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return mWebViewClient.shouldOverrideUrlLoading(view, url);
    }

    @TargetApi(Build.VERSION_CODES.N)
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return mWebViewClient.shouldOverrideUrlLoading(view, request);
    }

    public void onLoadResource(WebView view, String url) {
        mWebViewClient.onLoadResource(view, url);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void onPageCommitVisible(WebView view, String url) {
        mWebViewClient.onPageCommitVisible(view, url);
    }

    @Deprecated
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        return mWebViewClient.shouldInterceptRequest(view, url);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WebResourceResponse shouldInterceptRequest(WebView view,
                                                      WebResourceRequest request) {
        return mWebViewClient.shouldInterceptRequest(view, request);
    }

    @Deprecated
    public void onTooManyRedirects(WebView view, Message cancelMsg,
                                   Message continueMsg) {
        mWebViewClient.onTooManyRedirects(view, cancelMsg, continueMsg);
    }


    public void onFormResubmission(WebView view, Message dontResend,
                                   Message resend) {
        mWebViewClient.onFormResubmission(view, dontResend, resend);
    }

    public void doUpdateVisitedHistory(WebView view, String url,
                                       boolean isReload) {
        mWebViewClient.doUpdateVisitedHistory(view, url, isReload);
    }

    public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                   SslError error) {
        mWebViewClient.onReceivedSslError(view, handler, error);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        mWebViewClient.onReceivedClientCertRequest(view, request);
    }

    public void onReceivedHttpAuthRequest(WebView view,
                                          HttpAuthHandler handler, String host, String realm) {
        mWebViewClient.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        return mWebViewClient.shouldOverrideKeyEvent(view, event);
    }

    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        mWebViewClient.onUnhandledKeyEvent(view, event);
    }

    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        mWebViewClient.onScaleChanged(view, oldScale, newScale);
    }

    public void onReceivedLoginRequest(WebView view, String realm,
                                       String account, String args) {
        mWebViewClient.onReceivedLoginRequest(view, realm, account, args);
    }
}
