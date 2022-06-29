package com.scanny.qrcodereader.presentation.list

import android.content.Context
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.scanny.qrcodereader.entities.Code
import com.scanny.qrcodereader.presentation.CodeItem
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CodeList(codes: List<Code>, modifier: Modifier, context: Context) {
    LazyColumn(modifier) {
        items(codes.reversed()) { code ->
            CodeItem(code, context, modifier = modifier.height(40.dp))
        }
    }
}