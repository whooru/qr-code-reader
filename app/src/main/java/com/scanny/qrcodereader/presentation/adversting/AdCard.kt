package com.scanny.qrcodereader.presentation.adversting

import android.content.Context
import android.util.Log
import android.view.View
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.size
import com.appodeal.ads.Appodeal
import com.appodeal.ads.MrecView
import com.appodeal.ads.NativeAd
import com.appodeal.ads.NativeAdView
import com.appodeal.ads.native_ad.views.NativeAdViewAppWall
import com.appodeal.ads.native_ad.views.NativeAdViewNewsFeed
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.scanny.qrcodereader.presentation.MainActivity

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AdCard(modifier: Modifier, context: Context, nativeAds: List<NativeAd>?) {
    val nativeAd = (NativeAdViewAppWall(context))
    Card(
        modifier
            .fillMaxWidth()
    ) {
        if (nativeAds != null && nativeAds.isNotEmpty()) {
            if (nativeAds.isNotEmpty()) {
                nativeAd.setNativeAd(nativeAds[0])
            }
            AndroidView(modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(),
                factory = {
                    nativeAd
                })
        }
    }
}
