package com.scanny.qrcodereader.presentation.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.camera.core.*
import androidx.camera.core.impl.utils.executor.CameraXExecutors
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import com.scanny.qrcodereader.core.PreviewAnalyzer
import com.scanny.qrcodereader.core.getCameraProvider
import com.scanny.qrcodereader.presentation.list.CodesViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.scanny.qrcodereader.presentation.CameraPreview
import com.scanny.qrcodereader.presentation.Permission
import java.io.File


@SuppressLint("RestrictedApi")
@ExperimentalPermissionsApi
@Composable
fun CameraCapture(
    modifier: Modifier = Modifier,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    onImageFile: (File) -> Unit = { },
    detector: BarcodeScanner,
    viewModel: CodesViewModel
) {

    val context = LocalContext.current
    Permission(
        permission = Manifest.permission.CAMERA,
        rationale = "Permissions for working with camera",
        permissionNotAvailableContent = {
            Column(modifier) {
                Text("O noes! No Camera!")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    })
                }) {
                    Text("Open Settings")
                }
            }
        }
    ) {

        Box(modifier = modifier) {
            var scale by remember { mutableStateOf(0.1f) }
            val lifecycleOwner = LocalLifecycleOwner.current
            var previewUseCase by remember {
                mutableStateOf<UseCase>(
                    Preview.Builder().build()
                )
            }

            val imageCaptureUseCase by remember {
                mutableStateOf(
                    ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                        .build()
                )
            }

            val imageAnalysis: ImageAnalysis =
                ImageAnalysis.Builder()
                    .build()
                    .also {
                        it.setAnalyzer(
                            CameraXExecutors.highPriorityExecutor(),
                            PreviewAnalyzer(detector = detector, viewModel = viewModel)
                        )
                    }

            Box(modifier = Modifier.pointerInput(Unit) {
                detectTransformGestures { _, _, zoom, _ ->
                    scale = when {
                        scale < 0.1f -> 0.1f
                        scale > 1f -> 1f
                        else -> scale * zoom
                    }
                }
            }) {
                CameraPreview(
                    modifier = Modifier.fillMaxSize(),
                    onUseCase = {
                        previewUseCase = it
                    }
                )

            }
            previewUseCase.camera?.cameraControl?.setLinearZoom(scale)
            LaunchedEffect(previewUseCase) {
                val cameraProvider = context.getCameraProvider()
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        previewUseCase,
                        imageCaptureUseCase,
                        imageAnalysis
                    )



                } catch (ex: Exception) {
                    Log.e("CameraCapture", "Failed to bind camera use cases", ex)
                }

            }
        }
    }
}
