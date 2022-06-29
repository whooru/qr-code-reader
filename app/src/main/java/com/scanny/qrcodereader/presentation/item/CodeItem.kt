package com.scanny.qrcodereader.presentation

import android.content.Context

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.scanny.qrcodereader.R
import com.scanny.qrcodereader.core.Type
import com.scanny.qrcodereader.entities.Code
import com.scanny.qrcodereader.presentation.item.CodeTextItem
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@ExperimentalPermissionsApi
@Composable
fun CodeItem(code: Code, context: Context, modifier: Modifier) {
    val offsetX = remember { mutableStateOf(0f) }
    val codesViewModel = (context as MainActivity).codesViewModel
    val refreshButtons = codesViewModel.refreshButtons.observeAsState(false)
    val deleteBtnIsActive = remember { mutableStateOf(false) }
    val favouriteBtnIsActive = remember { mutableStateOf(false) }
    val changingNow = remember { mutableStateOf(false) }

    if (refreshButtons.value) {
        if (!changingNow.value) {
            deleteBtnIsActive.value = false
            favouriteBtnIsActive.value = false
        }
        changingNow.value = false
        codesViewModel.stopRefreshButtons()
    }

    Card(elevation = 1.dp) {
        Row(modifier =
        Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), 0) }
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = {
                        changingNow.value = true
                    },
                    onDragEnd = {
                        when {
                            offsetX.value >= size.width.toFloat() / 4 -> {
                                deleteBtnIsActive.value = false
                                favouriteBtnIsActive.value = !favouriteBtnIsActive.value
                            }
                            offsetX.value <= 0 - size.width.toFloat() / 4 -> {
                                favouriteBtnIsActive.value = false
                                deleteBtnIsActive.value = !deleteBtnIsActive.value
                            }
                            else -> {
                                deleteBtnIsActive.value = false
                                favouriteBtnIsActive.value = false
                            }
                        }
                        offsetX.value = 0f
                        codesViewModel.refreshButtons()
                    }) { change, dragAmount ->
                    change.consumeAllChanges()
                    offsetX.value = (offsetX.value + dragAmount)
                        .coerceIn(0 - size.width.toFloat(), size.width.toFloat() - 50.dp.toPx())
                }
            }
        ) {
            if (favouriteBtnIsActive.value) {
                Button(
                    modifier = Modifier.fillMaxHeight(),
                    onClick = {
                        codesViewModel.addToFavourites(code)
                        favouriteBtnIsActive.value = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.orangec)
                    ),
                    shape = AbsoluteCutCornerShape(0f, 0f, 0f, 0f)
                ) {
                    Text(text = "Favourite", color = MaterialTheme.colors.primaryVariant)
                }
            }
            val type = Type.values()[code.type - 1]
            Image(
                painter = painterResource(id = type.toImage()),
                contentDescription = type.toString(),
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 8.dp)
                    .align(Alignment.CenterVertically)
            )
            CodeTextItem(
                code = code,
                context = context,
                modifier = modifier
                    .weight(2f)
                    .wrapContentHeight()
                    .align(Alignment.CenterVertically)
            )
            if (deleteBtnIsActive.value) {
                Button(
                    modifier = Modifier.fillMaxHeight(),
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            codesViewModel.deleteCode(code)
                            deleteBtnIsActive.value = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.redc)),
                    shape = AbsoluteCutCornerShape(0f, 0f, 0f, 0f)
                ) {
                    Text(text = "Delete", color = MaterialTheme.colors.primaryVariant)
                }
            }
        }
        Divider(modifier = Modifier.height(4.dp), color = MaterialTheme.colors.background)
    }
}

