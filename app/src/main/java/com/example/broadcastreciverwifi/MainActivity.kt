package com.example.broadcastreciverwifi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var checkConnectionTv: TextView

    private val checkConnectionReciver = object: BroadcastReceiver(){

        // Contexto e Intent
        override fun onReceive(context: Context?, intent: Intent?) {

            /*val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

            if (isConnected){
                checkConnectionTv.text = "Conexion Wifi encendida"
            } else {
                checkConnectionTv.text = "Conexion Wifi desconectada"
            }*/
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkConnectionTv = findViewById(R.id.tv_checkConnection)

        //registerReceiver(checkConnectionReciver, IntentFilter(Intent.ACTION_))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(checkConnectionReciver)
    }

    //
}