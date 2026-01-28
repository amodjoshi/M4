package com.nplusone.m4.ui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nplusone.m4.ui.components.ScreenDim
import com.nplusone.m4.ui.components.addLeadingZeros
import com.nplusone.m4.ui.settings.TAG
import com.nplusone.m4.ui.viewmodel.GameUiState
import com.nplusone.m4.ui.viewmodel.Language
import com.nplusone.m4.ui.viewmodel.MulSubSkill
import com.nplusone.m4.ui.viewmodel.NumAlignment
import com.nplusone.m4.ui.viewmodel.Op

@Composable
fun HomeScreen(
    gus: GameUiState,
    onClickHandler: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .size(width = Dp(ScreenDim.dpWidth), height = Dp(ScreenDim.dpHeight))
            .border(
                width = Dp.Hairline,
                color = Color(0xFF800080),
                shape = RectangleShape
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surfaceTint)
                .padding(horizontal = 10.dp, vertical = 4.dp)
                .align(alignment = Alignment.Start),
            text = gus.score.toString()
                    + "/"
                    + gus.totalAttempted.toString(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
        // Horizontal or vertical?
        Box(
            modifier = Modifier
                .size(
                    width = Dp(ScreenDim.dpWidth / 2),
                    height = Dp(ScreenDim.dpHeight / 4),
                )
            //.background(Color.Magenta),
        ) {
            val n1 = if (gus.num1 > -1)
                addLeadingZeros(gus.op, gus.num1, gus.numDigits, gus.language)
            else
                ""
            val n2 = if (gus.num2 > -1)
                addLeadingZeros(gus.op, gus.num2, gus.numDigits, gus.language)
            else
                ""
            val sign = if (gus.opSign == "?") "" else gus.opSign
            val spaces = when (gus.op) {
                Op.ADD -> "  "
                Op.SUB -> " "
                Op.PADAS -> "  "
                Op.MUL -> " "
                Op.DIV -> "  "
                else -> " "
            }
            if (gus.alignment == NumAlignment.Vertical) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        text = "$spaces$n1",
                        style = MaterialTheme.typography.displayLarge
                    )
                    Text(
                        text = "$sign$n2",
                        style = MaterialTheme.typography.displayLarge
                    )
                }
            } else {
                Text(
                    text = "$n1$sign$n2",
                    style = MaterialTheme.typography.displayMedium,
                    fontSize = 40.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        McqButtons(
            names = gus.spoofs,
            onClickHandler = onClickHandler,
            enabled = gus.isStart,
            mcqBtnFont = gus.mcqBtnFont


        )
    }
}



