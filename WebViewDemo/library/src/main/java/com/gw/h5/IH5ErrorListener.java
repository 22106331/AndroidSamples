package com.gw.h5;

import android.webkit.WebView;

/**
 * Created by GongWen on 17/8/21.
 */

public interface IH5ErrorListener {
    void onError(WebView view, int errorCode, String description, String failingUrl);
}
