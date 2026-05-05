package com.webforge.app;

import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;
import android.webkit.JavascriptInterface; // Importante para conectar con el HTML
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Esta línea registra la "puerta" para que el HTML pueda hablar con Java
        this.bridge.getWebView().addJavascriptInterface(this, "AndroidVPN");
    }

    // El @JavascriptInterface es el que permite que el HTML vea esta función
    @JavascriptInterface
    public void prepararVPN() {
        Intent intent = VpnService.prepare(this);
        if (intent != null) {
            startActivityForResult(intent, 1); // Cambiado a 1 para mejor control
        } else {
            onActivityResult(1, RESULT_OK, null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Intent intent = new Intent(this, MyVpnService.class);
            startService(intent);
        }
    }
}
