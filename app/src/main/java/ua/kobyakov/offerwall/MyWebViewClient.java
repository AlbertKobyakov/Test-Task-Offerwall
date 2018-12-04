package ua.kobyakov.offerwall;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);

        Log.d("MyWebViewClient", "shouldOverrideUrlLoading");

        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        Log.d("MyWebViewClient", "onPageFinished");
    }
}
