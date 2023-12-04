package com.example.broadcastreciverwifi

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

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
            val isConnected = (wifiStateExtra == WifiManager.WIFI_STATE_ENABLED)

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

        checkAndRequestPermissions()

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

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            // Check if all permissions are granted
            if (permissions.all { it.value }) {
                // All permissions granted, perform your actions here
                Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show()
            } else {
                // Some permissions are denied, inform the user or handle it accordingly
                Toast.makeText(this, "Falta algun permiso", Toast.LENGTH_SHORT).show()
            }
        }
    private fun checkAndRequestPermissions() {
        val permissionsToRequest = arrayOf(
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE
        )

        val permissionsNotGranted = permissionsToRequest.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsNotGranted.isNotEmpty()) {
            // Request the permissions
            requestPermissionLauncher.launch(permissionsNotGranted.toTypedArray())
        } else {
            // All permissions are already granted, perform your actions here
            Toast.makeText(this, "Ahora todos los permisos se han concedido", Toast.LENGTH_SHORT).show()
        }
    }


}