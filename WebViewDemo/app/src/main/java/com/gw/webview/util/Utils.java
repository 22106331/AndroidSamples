package com.gw.webview.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Patterns;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.widget.Toast;

import com.gw.webview.MainApplication;

import java.io.File;

/**
 * Created by GongWen on 17/8/22.
 */

public class Utils {
    public static boolean isValidUrl(String url) {
        return !TextUtils.isEmpty(url) && Patterns.WEB_URL.matcher(url).matches();
    }

    public static void toastShort(CharSequence text) {
        Toast.makeText(MainApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
    }

    public static File getWebViewCacheDir() {
        File cacheDir = new File(MainApplication.getInstance().getCacheDir(), "webview_cache");
        cacheDir.mkdirs();
        return cacheDir;
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MainApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    public static StringBuilder onReceivedError2String(WebResourceRequest request, WebResourceError error) {
        StringBuilder sb = new StringBuilder();
        sb.append("onAboveMReceivedError\n");
        int SDK_INT = Build.VERSION.SDK_INT;
        if (SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sb.append("WebResourceRequest--->\n");
            sb.append("URL:" + request.getUrl() + "\n");
            sb.append("Method:" + request.getMethod() + "\n");
            sb.append("RequestHeaders:" + request.getRequestHeaders().toString() + "\n");
            sb.append("hasGesture:" + request.hasGesture() + "\n");
            sb.append("isForMainFrame:" + request.isForMainFrame() + "\n");
            if (SDK_INT >= Build.VERSION_CODES.N) {
                sb.append("isRedirect:" + request.isRedirect() + "\n");
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                sb.append("WebResourceError--->\n");
                sb.append("ErrorCode:" + error.getErrorCode() + "\n");
                sb.append("Description:" + error.getDescription() + "\n");
            }
        }
        return sb;
    }

    public static StringBuilder onReceivedHttpError2String(WebResourceRequest request, WebResourceResponse errorResponse) {
        StringBuilder sb = new StringBuilder();
        sb.append("onAboveMReceivedHttpError\n");
        sb.append("WebResourceResponse--->\n");
        sb.append("Encoding:" + errorResponse.getEncoding() + "\n");
        sb.append("MimeType:" + errorResponse.getMimeType() + "\n");
        sb.append("getData:" + errorResponse.getData() + "\n");
        int SDK_INT = Build.VERSION.SDK_INT;
        if (SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sb.append("ResponseHeaders:" + errorResponse.getResponseHeaders() + "\n");
            sb.append("StatusCode:" + errorResponse.getStatusCode() + "\n");
            sb.append("ReasonPhrase:" + errorResponse.getReasonPhrase() + "\n");

            sb.append("WebResourceRequest--->" + "\n");
            sb.append("URL:" + request.getUrl() + "\n");
            sb.append("Method:" + request.getMethod() + "\n");
            sb.append("RequestHeaders:" + request.getRequestHeaders().toString() + "\n");
            sb.append("hasGesture:" + request.hasGesture() + "\n");
            sb.append("isForMainFrame:" + request.isForMainFrame() + "\n");
            if (SDK_INT >= Build.VERSION_CODES.N) {
                sb.append("isRedirect:" + request.isRedirect() + "\n");
            }
        }

        return sb;
    }
}
