package com.scanny.qrcodereader.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.appodeal.ads.Appodeal
import com.appodeal.ads.InterstitialCallbacks
import com.appodeal.ads.NativeAd
import com.appodeal.ads.NativeCallbacks

class AdManager() {

    private val _isAdLoaded = MutableLiveData(false)
    val isAdLoaded: LiveData<Boolean> = _isAdLoaded

    private val _isInterstitialLoaded = MutableLiveData(false)
    var isInterstitialLoaded: LiveData<Boolean> = _isInterstitialLoaded

    private val _nativeAds = MutableLiveData<List<NativeAd>>()
    val nativeAds: LiveData<List<NativeAd>> = _nativeAds

    fun refreshInterstitial(){
        _isInterstitialLoaded.postValue(false)
    }

    fun refreshNativeAd(){
        _isAdLoaded.postValue(false)
    }


    fun setAdCallbacks(){
        Appodeal.setNativeCallbacks(object : NativeCallbacks {
            override fun onNativeLoaded() {
                if (isAdLoaded.value == false) {
                    val nativeAd: List<NativeAd> = Appodeal.getNativeAds(1)
                    _nativeAds.postValue(nativeAd)
                    _isAdLoaded.postValue(true)
                }
            }

            override fun onNativeFailedToLoad() {
            }

            override fun onNativeShown(nativeAd: NativeAd?) {
            }

            override fun onNativeShowFailed(nativeAd: NativeAd?) {
            }

            override fun onNativeClicked(nativeAd: NativeAd?) {
            }

            override fun onNativeExpired() {
            }
        })

        Appodeal.setInterstitialCallbacks(object : InterstitialCallbacks {
            override fun onInterstitialLoaded(isPrecache: Boolean) {
                _isInterstitialLoaded.postValue(true)
            }

            override fun onInterstitialFailedToLoad() {
            }

            override fun onInterstitialShown() {
            }

            override fun onInterstitialShowFailed() {
            }

            override fun onInterstitialClicked() {
            }

            override fun onInterstitialClosed() {
            }

            override fun onInterstitialExpired() {
            }
        })
    }

}