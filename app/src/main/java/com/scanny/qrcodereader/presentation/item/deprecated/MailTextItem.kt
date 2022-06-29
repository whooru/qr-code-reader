package com.scanny.qrcodereader.presentation.item.deprecated

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.scanny.qrcodereader.entities.Code

@Deprecated("Old fun, don't use it")
@Composable
fun MailTextItem(code: Code, modifier: Modifier) {
    Text(
        text = code.email.toString(),
        modifier = modifier
    )
}