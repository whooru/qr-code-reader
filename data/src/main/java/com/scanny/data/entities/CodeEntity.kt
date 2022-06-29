package com.scanny.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "code", indices = [Index(value = ["text"], unique = true)])
data class CodeEntity(

    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "timestamp") val timestamp: Long,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "phone") val phone: String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "sms") val sms: String?,
    @ColumnInfo(name = "geo_point") val geoPoint: String?,
    @ColumnInfo(name = "wifi_ssid") val wifiSSID: String?,
    @ColumnInfo(name = "wifi_pass") val wifiPass: String?,
    @ColumnInfo(name = "wifi_encrypted_type") val wifiEncryptedType: String?,
    @ColumnInfo(name = "type") val type: Int,
    @ColumnInfo(name = "favourite") var favourite: Boolean = false
    ) {
}