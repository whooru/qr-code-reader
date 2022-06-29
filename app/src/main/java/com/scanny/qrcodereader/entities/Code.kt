package com.scanny.qrcodereader.entities

data class Code(
//    val id: Int,
    val timestamp: Long,
    val text: String,
    val email: String?,
    val phone: String?,
    val url: String?,
    val sms: String?,
    val geoPoint: String?,
    val wifiSSID: String?,
    val wifiPass: String?,
    val wifiEncryptedType: String?,
    val type: Int,
    var favourite: Boolean = false
) {
}