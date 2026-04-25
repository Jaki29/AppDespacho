package com.jakandra.appdespacho

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val correo = intent.getStringExtra("correo") ?: "Sin correo"

        setContent {
            PantallaPrincipal(correo = correo)
        }
    }
}
