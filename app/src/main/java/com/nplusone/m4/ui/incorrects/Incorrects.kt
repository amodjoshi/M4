package com.nplusone.m4.ui.incorrects

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nplusone.m4.datalayer.Incorrects
import com.nplusone.m4.ui.components.ScreenDim
import com.nplusone.m4.ui.settings.TAG
import com.nplusone.m4.ui.viewmodel.GameUiState

@Composable
fun IncorrectsScreen(
    gus: GameUiState
) {
    Column(
        modifier = Modifier
            .size(width = Dp(ScreenDim.dpWidth), height = Dp(ScreenDim.dpHeight))
            .border(
                width = Dp.Hairline,
                color = Color(0xFF800080),
                shape = RectangleShape
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        gus.incorrectsRepo.forEach {
            MessageRow(it)
        }
    }
}

@Composable
fun MessageRow(
    i: Incorrects
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()

    ) {
        Text(text = i.sum)
        Text(text = i.correctAns)
        Text(text = i.pressedVal)
    }
}