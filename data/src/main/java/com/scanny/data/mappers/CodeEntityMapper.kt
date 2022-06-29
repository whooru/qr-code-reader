package com.scanny.data.mappers

import com.scanny.data.entities.CodeEntity
import com.scanny.domain.entities.Volume
import com.scanny.domain.entities.VolumeInfo
import java.sql.Timestamp

class CodeEntityMapper {
    fun toCodeEntity(volume: Volume): CodeEntity {
        return CodeEntity(
            id = volume.id,
            timestamp = Timestamp(System.currentTimeMillis()).time,
            text = volume.volumeInfo.text,
            email = volume.volumeInfo.email,
            phone = volume.volumeInfo.phone,
            url = volume.volumeInfo.url,
            sms = volume.volumeInfo.sms,
            geoPoint = volume.volumeInfo.geoPoint,
            wifiSSID= volume.volumeInfo.wifiSSID,
            wifiPass= volume.volumeInfo.wifiPass,
            wifiEncryptedType = volume.volumeInfo.wifiEncryptedType,
            type = volume.volumeInfo.type,
            favourite = volume.volumeInfo.favourite

        )
    }

    fun toVolume(codeEntity: CodeEntity): Volume {
        return Volume(
            codeEntity.id,
            VolumeInfo(
                text = codeEntity.text,
                timestamp = codeEntity.timestamp,
                email = codeEntity.email,
                phone = codeEntity.phone,
                url = codeEntity.url,
                sms = codeEntity.sms,
                geoPoint = codeEntity.geoPoint,
                wifiSSID = codeEntity.wifiSSID,
                wifiPass = codeEntity.wifiPass,
                wifiEncryptedType = codeEntity.wifiEncryptedType,
                type = codeEntity.type,
                favourite = codeEntity.favourite
            )
        )
    }
}