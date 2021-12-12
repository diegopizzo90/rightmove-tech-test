package com.diegopizzo.rightmovetechtest.ui.theme

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun DefaultText(text: String, modifier: Modifier = Modifier) {
    Text(text = text, color = Berry, fontSize = 14.sp, modifier = modifier)
}