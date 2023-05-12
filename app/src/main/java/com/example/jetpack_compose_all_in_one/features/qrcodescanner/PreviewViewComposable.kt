package com.example.jetpack_compose_all_in_one.features.qrcodescanner

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import java.util.concurrent.Executors

@Composable
fun QrCodeScanner() {
    AndroidView({context ->
        val cameraExecutor = Executors.newSingleThreadExecutor()
        val previewView = PreviewView(context).also {
            it.scaleType = PreviewView.ScaleType.FILL_CENTER
        }
        val cameraProvideFuture = ProcessCameraProvider.getInstance(context)
        cameraProvideFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProvideFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
            val imageCapture = ImageCapture.Builder().build()
            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, BarCodeAnalyser{
                        Toast.makeText(context, "Barcode found", Toast.LENGTH_SHORT).show()
                    })
                }
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    context as ComponentActivity, cameraSelector, preview, imageCapture, imageAnalyzer
                )
            } catch (exc : Exception){
                Log.e("DEBUG", "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(context))
        previewView
    },
    modifier = Modifier
        .size(width = 250.dp, height = 250.dp))
}