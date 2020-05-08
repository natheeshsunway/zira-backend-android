package com.example.zira;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private WebView mWebview ;
    ProgressDialog progressDoalog;


    String ziraUrl="https://zira-frontend.herokuapp.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        final boolean[] flag = {true};
        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Zira is preparing Now");
        progressDoalog.setTitle("ZIRA");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDoalog.show();

        mWebview  = new WebView(this);

        mWebview.getSettings().setJavaScriptEnabled(true);
        final Activity activity = this;
        mWebview.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });
        mWebview .loadUrl(ziraUrl);
        setContentView(mWebview);
        mWebview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressDoalog.incrementProgressBy(progress);
                }
                else
                {
                    progressDoalog.dismiss();
                }
            }


        });

        mWebview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                if(flag[0])
                    Toast.makeText(getApplicationContext(), "ZIRA now Online", Toast.LENGTH_SHORT).show();
                flag[0] =false;

            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
