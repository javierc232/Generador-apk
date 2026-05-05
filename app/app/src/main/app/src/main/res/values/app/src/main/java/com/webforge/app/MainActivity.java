package com.webforge.app;

import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;
import android.webkit.JavascriptInterface; 
import com.getcapacitor.BridgeActivity;
import android.widget.Toast;

public class MainActivity extends BridgeActivity {
    
    // --- 1. CARGAR MOTOR C++ ---
    // Esto busca el archivo 'native-lib' que configuraste en el CMakeLists.txt
    static {
        System.loadLibrary("native-lib");
    }

    // Declaramos la función que vive en el archivo .cpp
    public native String stringFromJNI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Puentes para que el HTML hable con Java
        this.bridge.getWebView().addJavascriptInterface(this, "AndroidVPN");
        this.bridge.getWebView().addJavascriptInterface(this, "AndroidNative");
    }

    // --- FUNCIONES UNIVERSALES ---
    
    @JavascriptInterface
    public void probarMotorCpp() {
        // Esta función llama al C++ y muestra el resultado en un mensaje
        final String mensajeDesdeCpp = stringFromJNI();
        runOnUiThread(() -> 
            Toast.makeText(MainActivity.this, mensajeDesdeCpp, Toast.LENGTH_LONG).show()
        );
    }

    @JavascriptInterface
    public void mostrarMensaje(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    // --- FUNCIONES VPN ---
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
