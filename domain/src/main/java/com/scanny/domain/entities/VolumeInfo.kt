package com.scanny.domain.entities

data class VolumeInfo(
    val text: String,
    val timestamp: Long,
    val phone: String?,
    val email: String?,
    val url: String?,
    val sms: String?,
    val geoPoint: String?,
    val wifiSSID: String?,
    val wifiPass: String?,
    val wifiEncryptedType: String?,
    val type: Int,
    var favourite: Boolean = false
)