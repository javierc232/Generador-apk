package com.webforge.app;

import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Aquí podrías agregar lógica adicional si fuera necesario
    }

    // Esta función es la que llamaremos para activar la LLAVE
    public void prepararVPN() {
        Intent intent = VpnService.prepare(this);
        if (intent != null) {
            // Si el sistema pide permiso, lanzamos el cartel oficial
            startActivityForResult(intent, 0);
        } else {
            // Si ya tiene permiso, iniciamos el servicio directamente
            onActivityResult(0, RESULT_OK, null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Intent intent = new Intent(this, MyVpnService.class);
            startService(intent);
        }
    }
}
