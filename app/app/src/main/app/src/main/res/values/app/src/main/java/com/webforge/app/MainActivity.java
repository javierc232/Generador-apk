package com.webforge.app;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        setContentView(webView);

        try {
            File file = new File(getFilesDir(), "url.txt");
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                webView.loadUrl(br.readLine());
            } else {
                webView.loadUrl("file:///android_asset/index.html");
            }
        } catch (Exception e) {
            webView.loadUrl("file:///android_asset/index.html");
        }
    }
}
