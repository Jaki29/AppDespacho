package com.jakandra.appdespacho

// Importaciones básicas de Android y Jetpack Compose
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.text.input.PasswordVisualTransformation
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.database.FirebaseDatabase
import com.google.android.gms.location.LocationServices
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.app.Activity
import androidx.core.app.ActivityCompat

// Clase principal de la aplicación
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppDespacho()
        }
    }
}

@Composable
fun AppDespacho() {

    // Variable para controlar si el usuario ya inició sesión
    var sesionIniciada by remember { mutableStateOf(false) }

    // Variable para guardar el correo ingresado
    var correoUsuario by remember { mutableStateOf("") }

    PantallaLogin(
        onLoginExitoso = { correo ->
            correoUsuario = correo
        }
    )
}

@Composable
fun PantallaLogin(onLoginExitoso: (String) -> Unit) {

    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(text = "Inicio de sesión")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo electrónico") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {

            auth.signInWithEmailAndPassword(correo, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        mensaje = "Inicio de sesión exitoso"
                        if (ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

                            fusedLocationClient.lastLocation.addOnSuccessListener { location ->

                                if (location != null) {
                                    val database = FirebaseDatabase.getInstance()
                                    val ref = database.getReference("usuarios")

                                    val datos = mapOf(
                                        "correo" to correo,
                                        "latitud" to location.latitude,
                                        "longitud" to location.longitude
                                    )

                                    ref.push().setValue(datos)
                                }

                                val intent = Intent(context, MenuActivity::class.java)
                                intent.putExtra("correo", correo)
                                context.startActivity(intent)
                            }

                        } else {
                            ActivityCompat.requestPermissions(
                                context as Activity,
                                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                                1
                            )
                            mensaje = "Solicitando permiso de ubicación"

                            val intent = Intent(context, MenuActivity::class.java)
                            intent.putExtra("correo", correo)
                            context.startActivity(intent)
                        }

                        val intent = Intent(context, MenuActivity::class.java)
                        intent.putExtra("correo", correo)
                        context.startActivity(intent)
                    } else {
                        mensaje = "Correo o contraseña incorrectos"
                    }
                }

        }) {
            Text("Iniciar sesión")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = mensaje)
    }
}
@Composable
fun PantallaPrincipal(correo: String) {

    // Variable para guardar el monto ingresado por el usuario
    var monto by remember { mutableStateOf("") }

    // Variable para mostrar la distancia calculada
    var distanciaTexto by remember { mutableStateOf("") }

    // Variable para mostrar el resultado del despacho
    var resultadoDespacho by remember { mutableStateOf("") }

    // Variable para mostrar el estado de la temperatura
    var resultadoTemperatura by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Mostramos el correo con el que "inició sesión"
        Text(text = "Bienvenido: $correo")

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Cálculo de despacho con control de temperatura")

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para ingresar monto
        OutlinedTextField(
            value = monto,
            onValueChange = { monto = it },
            label = { Text("Ingrese monto de compra") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para calcular despacho
        Button(onClick = {

            // Convertimos el texto ingresado a número
            val montoInt = monto.toIntOrNull() ?: 0

            // Coordenadas simuladas del usuario
            val latUsuario = -33.4489
            val lonUsuario = -70.6693

            // Coordenadas de la Plaza de Armas
            val latPlaza = -33.4372
            val lonPlaza = -70.6506

            // Calculamos la distancia
            val distancia = calcularDistancia(
                latUsuario,
                lonUsuario,
                latPlaza,
                lonPlaza
            )

            // Mostramos la distancia con dos decimales
            distanciaTexto = "Distancia calculada: ${"%.2f".format(distancia)} km"

            // Cálculo del costo de despacho
            val costo = when {
                montoInt > 50000 && distancia <= 20 -> 0
                montoInt in 25000..49999 -> (150 * distancia).toInt()
                montoInt < 25000 -> (300 * distancia).toInt()
                else -> 0
            }

            // Resultado del despacho
            resultadoDespacho = when {
                montoInt > 50000 && distancia <= 20 ->
                    "Resultado despacho: Despacho GRATIS"

                montoInt > 50000 && distancia > 20 ->
                    "Resultado despacho: No aplica despacho gratis por superar los 20 km"

                else ->
                    "Resultado despacho: Costo despacho: $$costo"
            }

            // Simulación de temperatura del congelador
            val temperatura = (-20..5).random()

            // Validamos si la temperatura supera el límite
            resultadoTemperatura = if (temperatura > -10) {
                "⚠️ ALERTA: Temperatura alta del congelador ($temperatura °C)"
            } else {
                "✅ Temperatura OK del congelador ($temperatura °C)"
            }

        }) {
            Text("Calcular despacho")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostramos distancia
        Text(text = distanciaTexto)

        Spacer(modifier = Modifier.height(16.dp))

        // Mostramos resultado despacho
        Text(text = resultadoDespacho)

        Spacer(modifier = Modifier.height(16.dp))

        // Mostramos estado de temperatura
        Text(text = resultadoTemperatura)
    }
}

// Función para calcular la distancia entre dos coordenadas usando Haversine
fun calcularDistancia(
    lat1: Double,
    lon1: Double,
    lat2: Double,
    lon2: Double
): Double {

    // Radio de la Tierra en kilómetros
    val R = 6371

    // Diferencias de latitud y longitud convertidas a radianes
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)

    // Fórmula Haversine
    val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(Math.toRadians(lat1)) *
            Math.cos(Math.toRadians(lat2)) *
            Math.sin(dLon / 2) * Math.sin(dLon / 2)

    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

    // Retornamos la distancia final en kilómetros
    return R * c
}