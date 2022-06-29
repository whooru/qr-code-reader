package com.scanny.qrcodereader.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import com.appodeal.ads.Appodeal
import com.explorestack.consent.Consent
import com.explorestack.consent.ConsentInfoUpdateListener
import com.explorestack.consent.ConsentManager
import com.explorestack.consent.exception.ConsentManagerException
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.scanny.qrcodereader.App
import com.scanny.qrcodereader.core.AdManager
import com.scanny.qrcodereader.presentation.content.MainContent
import com.scanny.qrcodereader.presentation.list.CodesViewModel
import com.scanny.qrcodereader.ui.theme.QrCodeReaderTheme


@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {

    lateinit var detector: BarcodeScanner

    val codesViewModel: CodesViewModel by viewModels {
        CodesViewModel.CodesViewModelFactory(
            (application as App).getCodesUseCase,
            (application as App).addCodeUseCase,
            (application as App).deleteCodesUseCase,
            (application as App).updateCodeUserCase,
            (application as App).getFavouritesCodesUseCase,
            (application as App).codesMapper,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ConsentManager.getInstance(this)
            .requestConsentInfoUpdate(
                "#####",
                object : ConsentInfoUpdateListener {
                    override fun onConsentInfoUpdated(consent: Consent) {
                        Appodeal.initialize(
                            this@MainActivity,
                            "####",
                            Appodeal.NATIVE or Appodeal.INTERSTITIAL,
                            consent
                        )
                        Appodeal.cache(this@MainActivity, Appodeal.NATIVE)
                        Appodeal.cache(this@MainActivity, Appodeal.INTERSTITIAL)
                    }

                    override fun onFailedToUpdateConsentInfo(exception: ConsentManagerException) {
                        // User's consent status failed to update.
                        val errorCode = exception.code
                        val reason = exception.reason
                        Appodeal.initialize(
                            this@MainActivity,
                            "####",
                            Appodeal.NATIVE or Appodeal.INTERSTITIAL
                        )
                        Appodeal.cache(this@MainActivity, Appodeal.NATIVE)
                        Appodeal.cache(this@MainActivity, Appodeal.INTERSTITIAL)
                    }
                })

        detector = BarcodeScanning.getClient(
            BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                .build()
        )
        val adManager = AdManager()
        setContent {
            QrCodeReaderTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainContent(detector = detector, viewModel = codesViewModel, context = this, adManager = adManager)
                }
            }
        }
    }
}







