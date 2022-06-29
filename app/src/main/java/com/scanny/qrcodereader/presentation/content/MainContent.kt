package com.scanny.qrcodereader.presentation.content

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.appodeal.ads.Appodeal
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.scanny.qrcodereader.core.AdManager
import com.scanny.qrcodereader.presentation.camera.CameraCapture
import com.scanny.qrcodereader.presentation.MainActivity
import com.scanny.qrcodereader.presentation.adversting.AdCard
import com.scanny.qrcodereader.presentation.list.CodeList
import com.scanny.qrcodereader.presentation.list.CodesViewModel


@ExperimentalPermissionsApi
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    detector: BarcodeScanner,
    viewModel: CodesViewModel,
    context: Context,
    adManager: AdManager
) {
    val codes by viewModel.codes.observeAsState()
    val favouriteCodes by viewModel.favouriteCodes.observeAsState()
    val isAllPageOpen by viewModel.isAllOpen.observeAsState()
    viewModel.getCodes()
    viewModel.getFavouriteCodes()
    var nativeAds = adManager.nativeAds.observeAsState()
    var isInterstitialIsLoaded = adManager.isInterstitialLoaded.observeAsState()
    Column {
        AdCard(modifier = modifier.weight(1f), context, nativeAds.value)

        CameraCapture(
            modifier = modifier.weight(2f),
            onImageFile = {
            },
            detector = detector,
            viewModel = viewModel
        )


        Row {
            Button(
                onClick = {
                    if (isInterstitialIsLoaded.value == true) {
                        Appodeal.show(context as MainActivity, Appodeal.INTERSTITIAL)
                        adManager.refreshInterstitial()
                    }
                    viewModel.changePage(true)
                },
                modifier = Modifier.weight(1f),
                shape = AbsoluteCutCornerShape(0f, 0f, 0f, 0f),
            ) {
                Text(text = "All", color = MaterialTheme.colors.primaryVariant)
            }
            Button(
                onClick = {
                    if (isInterstitialIsLoaded.value == true) {
                        Appodeal.show(context as MainActivity, Appodeal.INTERSTITIAL)
                        adManager.refreshInterstitial()
                    }
                    viewModel.changePage(false)
                },
                modifier = Modifier.weight(1f),
                shape = AbsoluteCutCornerShape(0f, 0f, 0f, 0f),
            ) {
                Text(
                    text = "Favourite",
                    color = MaterialTheme.colors.primaryVariant
                )
            }
        }
        if (isAllPageOpen == true) {
            codes?.let {
                CodeList(
                    codes = it,
                    modifier = Modifier.weight(1f),
                    context
                )
            }
        } else {
            favouriteCodes?.let {
                CodeList(
                    codes = it,
                    modifier = Modifier.weight(1f),
                    context = context
                )
            }
        }
    }
}
