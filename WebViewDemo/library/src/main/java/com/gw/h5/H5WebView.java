package com.gw.h5;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by GongWen on 17/8/21.
 */

public class H5WebView extends WebView {
    protected WebClientWrapper mWebClientWrapper;

    public H5WebView(Context context) {
        this(context, null);
    }

    public H5WebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public H5WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        super.setWebViewClient(client);
    }

    public void setWebViewClient(WebClientWrapper mWebClientWrapper) {
        this.mWebClientWrapper = mWebClientWrapper;
        super.setWebViewClient(mWebClientWrapper);
    }

    public void setWebViewClientWrapper(WebViewClient client) {
        this.mWebClientWrapper = new WebClientWrapper(client);
        super.setWebViewClient(mWebClientWrapper);
    }

    public void setH5ViewListener(IH5ErrorListener mH5ErrorListener) {
        if (mWebClientWrapper != null) {
            mWebClientWrapper.setH5ViewListener(mH5ErrorListener);
        }
    }
}
