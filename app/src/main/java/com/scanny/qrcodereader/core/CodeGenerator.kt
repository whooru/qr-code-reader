package com.scanny.qrcodereader.core

import com.scanny.qrcodereader.entities.Code
import com.google.mlkit.vision.barcode.Barcode
import java.sql.Timestamp

class CodeGenerator {

    fun generateFromBarcode(item: Barcode): Code {
        return if (item.url != null) {
            Code(
                timestamp = Timestamp(System.currentTimeMillis()).time,
                text = item.rawValue,
                email = item.email?.address,
                phone = item.phone?.number,
                url = item.url?.url,
                sms = item.sms?.phoneNumber + "?body=" + item.sms?.message,
                wifiSSID = item.wifi?.ssid.toString(),
                wifiPass = item.wifi?.password.toString(),
                wifiEncryptedType = item.wifi?.encryptionType.toString(),
                geoPoint = item.geoPoint?.toString(),
                type = Type.URL.index
            )
        } else if (item.phone != null) {
            Code(
                timestamp = Timestamp(System.currentTimeMillis()).time,
                text = item.rawValue,
                email = item.email?.address,
                phone = item.phone?.number,
                url = item.url?.url,
                sms = item.sms?.phoneNumber + "?body=" + item.sms?.message,
                geoPoint = item.geoPoint?.toString(),
                wifiSSID = item.wifi?.ssid.toString(),
                wifiPass = item.wifi?.password.toString(),
                wifiEncryptedType = item.wifi?.encryptionType.toString(),
                type = Type.PHONE.index
            )
        } else if (item.email != null) {
            Code(
                timestamp = Timestamp(System.currentTimeMillis()).time,
                text = item.rawValue,
                email = item.email?.address.toString(),
                phone = item.phone?.number,
                url = item.url?.url,
                sms = item.sms?.phoneNumber + "?body=" + item.sms?.message,
                geoPoint = item.geoPoint?.toString(),
                wifiSSID = item.wifi?.ssid.toString(),
                wifiPass = item.wifi?.password.toString(),
                wifiEncryptedType = item.wifi?.encryptionType.toString(),
                type = Type.MAIL.index
            )
        } else if (item.geoPoint != null) {
            Code(
                timestamp = Timestamp(System.currentTimeMillis()).time,
                text = item.rawValue,
                email = item.email?.address,
                phone = item.phone?.number,
                url = item.url?.url,
                sms = item.sms?.phoneNumber + "?body=" + item.sms?.message,
                geoPoint = item.geoPoint?.toString(),
                wifiSSID = item.wifi?.ssid.toString(),
                wifiPass = item.wifi?.password.toString(),
                wifiEncryptedType = item.wifi?.encryptionType.toString(),
                type = Type.GEOPOINT.index
            )
        } else if (item.sms != null) {
            Code(
                timestamp = Timestamp(System.currentTimeMillis()).time,
                text = item.sms?.phoneNumber + " msg: " + item.sms?.message,
                email = item.email?.address,
                phone = item.sms?.phoneNumber,
                url = item.url?.url,
                sms = "sms:" + item.sms?.phoneNumber + "?body=" + item.sms?.message,
                geoPoint = item.geoPoint?.toString(),
                wifiSSID = item.wifi?.ssid.toString(),
                wifiPass = item.wifi?.password.toString(),
                wifiEncryptedType = item.wifi?.encryptionType.toString(),
                type = Type.SMS.index
            )
        } else if (item.wifi != null) {
            Code(
                timestamp = Timestamp(System.currentTimeMillis()).time,
                text = item.rawValue,
                email = item.email?.address,
                phone = item.sms?.phoneNumber,
                url = item.url?.url,
                sms = item.sms?.phoneNumber + "?body=" + item.sms?.message,
                geoPoint = item.geoPoint?.toString(),
                wifiSSID = item.wifi?.ssid.toString(),
                wifiPass = item.wifi?.password.toString(),
                wifiEncryptedType = item.wifi?.encryptionType.toString(),
                type = Type.WIFI.index
            )
        } else {
            if (item.rawValue.startsWith("sms:")) {
                Code(
                    timestamp = Timestamp(System.currentTimeMillis()).time,
                    text = item.rawValue.removePrefix("sms:").replace("?body=", " msg: "),
                    email = item.email?.address,
                    phone = item.phone?.number,
                    url = item.url?.url,
                    sms = item.rawValue,
                    geoPoint = item.geoPoint?.toString(),
                    wifiSSID = item.wifi?.ssid.toString(),
                    wifiPass = item.wifi?.password.toString(),
                    wifiEncryptedType = item.wifi?.encryptionType.toString(),
                    type = Type.SMS.index
                )
            } else {
                Code(
                    timestamp = Timestamp(System.currentTimeMillis()).time,
                    text = item.rawValue,
                    email = item.email?.address,
                    phone = item.phone?.number,
                    url = item.url?.url,
                    sms = item.sms?.phoneNumber + "?body=" + item.sms?.message,
                    geoPoint = item.geoPoint?.toString(),
                    wifiSSID = item.wifi?.ssid.toString(),
                    wifiPass = item.wifi?.password.toString(),
                    wifiEncryptedType = item.wifi?.encryptionType.toString(),
                    type = Type.TEXT.index
                )
            }
        }

    }

}