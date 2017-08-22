package com.gw.webview.js;

import android.webkit.JavascriptInterface;

import com.gw.webview.util.Utils;

/**
 * Created by GongWen on 17/8/22.
 */

public class JsInterface {
    @JavascriptInterface
    public void toastMessage(String message) {
        Utils.toastShort(message);
    }

    @JavascriptInterface
    public void sumToJava(int a, int b) {
        Utils.toastShort("The sum of a:" + a + ",b:" + b + " is " + (a + b) + "!");
    }

    @JavascriptInterface
    public int sum(int x, int y) {
        return x + y;
    }
}
