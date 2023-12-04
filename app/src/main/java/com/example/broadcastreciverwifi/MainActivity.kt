package com.example.broadcastreciverwifi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var checkConnectionTv: TextView

    private val checkConnectionReciver = object : BroadcastReceiver() {

        // Contexto e Intent
        override fun onReceive(context: Context?, intent: Intent?) {

            // Obtenemos el estado de la conexion
            // Como clave le pasaremos la constante EXTRA_WIFI_STATE y como valor le pasaremos la constante WIFI_STATE_UNKNOWN ya que no sabemos si esta activa o no
            val wifiStateExtra = intent?.getIntExtra(
                WifiManager.EXTRA_WIFI_STATE,
                WifiManager.WIFI_STATE_UNKNOWN
            )

            // Comprobamos si la conexion esta activa con un booleano, y tambien comparamos si el estado de la conexion esta habilitada
            val isConnected = wifiStateExtra == WifiManager.WIFI_STATE_ENABLED

            // Si es true mostramos el mensaje de conexion activa
            if (isConnected) {
                checkConnectionTv.text = "Conexion Wifi encendida"
            } else {
                checkConnectionTv.text = "Conexion Wifi desconectada"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkConnectionTv = findViewById(R.id.tv_checkConnection)

        // Registramos el broadcast receiver para que se ejecute cuando cambie el estado de la conexion
        registerReceiver(
            checkConnectionReciver,
            IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        // Desregistramos el broadcast receiver para evitar un consumo de recursos cuando se cierre la app
        unregisterReceiver(checkConnectionReciver)
    }

}