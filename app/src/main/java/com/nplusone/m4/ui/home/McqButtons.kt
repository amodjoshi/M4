package com.nplusone.m4.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nplusone.m4.ui.viewmodel.McqBtnFont

@Composable
fun McqButtons(
    names: List<String>,
    onClickHandler: (Int) -> Unit,
    enabled: Boolean,
    mcqBtnFont: McqBtnFont
) {
    Column() {
        ButtonsHouse(
            nameA = names[0],
            nameB = names[1],
            onClickA = {
                onClickHandler(0)
            },
            onClickB = {
                onClickHandler(1)
            },
            modifier = Modifier
                .weight(0.25f)
                .fillMaxWidth(1f),
            enabled = enabled,
            mcqBtnFont = mcqBtnFont
        )

        ButtonsHouse(
            nameA = names[2],
            nameB = names[3],
            onClickA = {
                onClickHandler(2)
            },
            onClickB = {
                onClickHandler(3)
            },
            modifier = Modifier
                .weight(0.25f)
                .fillMaxWidth(1f),
            enabled = enabled,
            mcqBtnFont = mcqBtnFont
        )
    }

}

@Composable
fun ButtonsHouse(
    nameA: String,
    nameB: String,
    modifier: Modifier = Modifier,
    onClickA: () -> Unit,
    onClickB: () -> Unit,
    enabled: Boolean,
    mcqBtnFont: McqBtnFont
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier

    ) {
        Button(
            onClick = { onClickA() },
            modifier = Modifier
                .weight(1f),
            enabled = enabled
        ) {
            Text(
                nameA,
                fontSize = when (mcqBtnFont) {
                    McqBtnFont.SMALL -> 14.sp
                    McqBtnFont.MED -> 16.sp
                    else -> 20.sp
                },
                fontWeight = FontWeight.ExtraBold
            )
        }
        Button(
            onClick = { onClickB() },
            modifier = Modifier
                .weight(1f),
            enabled = enabled

        ) {
            Text(
                nameB,
                fontSize = when (mcqBtnFont) {
                    McqBtnFont.SMALL -> 14.sp
                    McqBtnFont.MED -> 16.sp
                    else -> 20.sp
                },
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}