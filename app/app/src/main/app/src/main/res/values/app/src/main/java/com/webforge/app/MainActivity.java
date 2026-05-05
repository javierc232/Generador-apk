package com.webforge.app;

import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;
import android.webkit.JavascriptInterface; 
import com.getcapacitor.BridgeActivity;
import android.widget.Toast; // Para mostrar mensajes rápidos

public class MainActivity extends BridgeActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 1. Puente específico para tu VPN (para que siga funcionando)
        this.bridge.getWebView().addJavascriptInterface(this, "AndroidVPN");

        // 2. NUEVO: Puente Universal para cualquier otra app
        this.bridge.getWebView().addJavascriptInterface(this, "AndroidNative");
    }

    // --- FUNCIONES UNIVERSALES ---
    @JavascriptInterface
    public void mostrarMensaje(String texto) {
        // Esto permite que cualquier HTML mande un aviso al celular
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    // --- TU FUNCIÓN DE VPN (SE QUEDA IGUAL) ---
    @JavascriptInterface
    public void prepararVPN() {
        Intent intent = VpnService.prepare(this);
        if (intent != null) {
            startActivityForResult(intent, 1);
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
