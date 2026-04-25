# AppDespacho
Aplicación móvil de gestión de despacho con Firebase Authentication, Realtime Database y GPS

## 🎯 Objetivo del proyecto

Desarrollar una aplicación móvil funcional que integre autenticación, base de datos en la nube y servicios de ubicación, simulando un sistema real de gestión de despachos.

# 🚚 AppDespacho

Aplicación móvil desarrollada en **Android Studio** utilizando **Kotlin y Jetpack Compose**, enfocada en la gestión de despachos a domicilio mediante integración con **Firebase** y uso de **GPS**.

---

## 📱 Descripción

**AppDespacho** es una aplicación que permite a los usuarios iniciar sesión de forma segura mediante correo electrónico y contraseña utilizando **Firebase Authentication**.

Una vez autenticado, el usuario accede a un menú principal donde puede:

- Calcular el costo de despacho
- Visualizar información relevante
- Registrar su ubicación en tiempo real

La aplicación simula un sistema real de logística, integrando ubicación geográfica y monitoreo de condiciones como temperatura.

---

## 🚀 Tecnologías utilizadas

- 🧠 Kotlin  
- 🎨 Jetpack Compose  
- 🔥 Firebase Authentication  
- 🗄️ Firebase Realtime Database  
- 📍 Google Play Services (GPS)  
- 💻 Android Studio  
- 🐙 GitHub  

---

## ⚙️ Funcionalidades principales

✔ Inicio de sesión con correo y contraseña  
✔ Validación de usuario con Firebase  
✔ Navegación a menú principal  
✔ Obtención de ubicación GPS  
✔ Almacenamiento de datos en Firebase  
✔ Cálculo automático de despacho  
✔ Simulación de control de temperatura  

---

## 📦 Reglas de negocio

- 🟢 Compras sobre $50.000 → **Despacho GRATIS** (hasta 20 km)  
- 🟡 Compras entre $25.000 y $49.999 → **$150 por km**  
- 🔴 Compras menores a $25.000 → **$300 por km**  

---

## 📍 Uso del GPS

La aplicación obtiene la ubicación del usuario (latitud y longitud) al momento de iniciar sesión y la almacena en **Firebase Realtime Database**, permitiendo simular un sistema de seguimiento de despachos.

---

## 🧊 Control de temperatura

Se implementa una simulación de temperatura del congelador:

- ✅ Temperatura bajo el límite → estado correcto  
- ⚠️ Temperatura sobre el límite → alerta en pantalla  

---

## 👩‍💻 Historias de usuario

| Historia | Estado |
|--------|--------|
| Como usuario, quiero iniciar sesión con correo y contraseña | ✅ |
| Como usuario, quiero acceder al menú principal tras autenticarme | ✅ |
| Como usuario, quiero calcular el costo de despacho | ✅ |
| Como usuario, quiero registrar mi ubicación GPS | ✅ |
| Como administrador, quiero almacenar datos en Firebase | ✅ |
| Como usuario, quiero recibir alertas de temperatura | ✅ |

---

## 🗂️ Gestión del proyecto

El proyecto fue desarrollado en un **repositorio único de GitHub**, aplicando control de versiones mediante commits.

Aunque el desarrollo fue realizado de forma individual, se utilizaron prácticas de trabajo colaborativo como:

- Organización mediante historias de usuario  
- Documentación en README.md  
- Uso de Git como herramienta de control de versiones  

---

## 📸 Evidencias

Se recomienda incluir en el informe:

- 📷 Pantalla de Login  
- 📷 Usuario en Firebase Authentication  
- 📷 Datos en Realtime Database  
- 📷 Aplicación ejecutándose en Android  
- 📷 Repositorio GitHub  

