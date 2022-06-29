package com.scanny.qrcodereader.mappers

import com.scanny.domain.entities.Volume
import com.scanny.domain.entities.VolumeInfo
import com.scanny.qrcodereader.entities.Code

class CodesMapper {

    fun fromCodeToVolume(code: Code): Volume {
        return Volume(
            0,
            VolumeInfo(
                text = code.text,
                timestamp = code.timestamp,
                phone = code.phone,
                email = code.email,
                url = code.url,
                sms = code.sms,
                geoPoint = code.geoPoint,
                wifiSSID = code.wifiSSID,
                wifiPass = code.wifiPass,
                wifiEncryptedType = code.wifiEncryptedType,
                type = code.type,
                favourite = code.favourite
            )
        )
    }

    fun fromVolumeToCodes(volumes: List<Volume>): List<Code> {
        val codes = arrayListOf<Code>()
        for (volume in volumes) {
            codes.add(
                Code(
                    timestamp = volume.volumeInfo.timestamp,
                    text= volume.volumeInfo.text,
                    email =volume.volumeInfo.email,
                    phone =volume.volumeInfo.phone,
                    url = volume.volumeInfo.url,
                    sms = volume.volumeInfo.sms,
                    geoPoint = volume.volumeInfo.geoPoint,
                    wifiSSID = volume.volumeInfo.wifiSSID,
                    wifiPass = volume.volumeInfo.wifiPass,
                    wifiEncryptedType = volume.volumeInfo.wifiEncryptedType,
                    type = volume.volumeInfo.type,
                    favourite = volume.volumeInfo.favourite
                )
            )
        }
        return codes.sortedBy { it.timestamp }
    }

}