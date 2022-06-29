package com.scanny.qrcodereader.presentation.item

import android.content.*
import android.content.Context.CLIPBOARD_SERVICE
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.scanny.qrcodereader.core.Type
import com.scanny.qrcodereader.core.WifiConnect
import com.scanny.qrcodereader.entities.Code


@Composable
fun CodeTextItem(code: Code, context: Context, modifier: Modifier) {

    val modifier = modifier
        .wrapContentWidth()
        .padding(horizontal = 8.dp)
        .clickable {
            try {
                when (code.type) {
                    Type.WIFI.index -> {
                        WifiConnect().connect(
                            context = context,
                            password = code.wifiPass.toString(),
                            ssid = code.wifiSSID.toString()
                        )
                    }
                    Type.TEXT.index -> {
                        val clipboard: ClipboardManager? =
                            context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
                        val clip: ClipData = ClipData.newPlainText("Copy", code.text)
                        clipboard?.setPrimaryClip(clip)
                        Toast
                            .makeText(context, "Text copied", Toast.LENGTH_SHORT)
                            .show()
                    }
                    Type.SMS.index -> {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(code.sms)
                        context.startActivity(intent)
                    }
                    else -> {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(code.text)
                        context.startActivity(intent)
                    }
                }
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                val clipboard: ClipboardManager? =
                    context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
                val clip: ClipData = ClipData.newPlainText("Copy", code.text)
                clipboard?.setPrimaryClip(clip)
                Toast
                    .makeText(context, "Text copied", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    val text = when (code.type) {
        Type.SMS.index -> {
            code.text
        }
        Type.PHONE.index -> {
            code.phone.toString()
        }
        Type.MAIL.index -> {
            code.email.toString()
        }
        Type.URL.index -> {
            code.text
        }
        Type.GEOPOINT.index -> {
            code.text
        }
        Type.TEXT.index -> {
            code.text
        }
        Type.WIFI.index -> {
            "Wifi: " + code.wifiSSID.toString() + " Pass: " + code.wifiPass.toString()
        }
        else -> ""
    }

    return Text(
        text = text,
        textAlign = TextAlign.Start,
        modifier = modifier.fillMaxWidth()
    )
}