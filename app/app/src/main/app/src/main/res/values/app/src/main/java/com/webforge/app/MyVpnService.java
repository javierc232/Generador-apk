package com.webforge.app;

import android.net.VpnService;
import android.content.Intent;
import android.os.ParcelFileDescriptor;
import android.util.Log;

public class MyVpnService extends VpnService {
    private ParcelFileDescriptor vpnInterface = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("DiabloVPN", "Iniciando tunel nativo...");
        
        Builder builder = new Builder();
        
        try {
            // Esto configura la red virtual interna del celular
            builder.setSession("ElectricidadVPN")
                   .addAddress("10.0.0.2", 24)
                   .addDnsServer("8.8.8.8")
                   .addRoute("0.0.0.0", 0); // Captura todo el tráfico

            vpnInterface = builder.establish();
            Log.d("DiabloVPN", "Interfaz VPN establecida.");
        } catch (Exception e) {
            Log.e("DiabloVPN", "Error al iniciar: " + e.getMessage());
        }
        
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (vpnInterface != null) {
            try {
                vpnInterface.close();
                vpnInterface = null;
            } catch (Exception e) {
                Log.e("DiabloVPN", "Error al cerrar.");
            }
        }
        super.onDestroy();
    }
}
