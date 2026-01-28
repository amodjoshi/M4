package com.nplusone.m4.ui.settings

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.nplusone.m4.ui.theme.Purple40
import com.nplusone.m4.ui.theme.Purple80
import com.nplusone.m4.ui.viewmodel.GameUiState
import com.nplusone.m4.ui.viewmodel.Language
import com.nplusone.m4.ui.viewmodel.NumAlignment
import com.nplusone.m4.ui.viewmodel.Quantity

// Q-20,40,60,80,100,H-Alignment,E-Language
@Composable
fun MetaData(
    gus: GameUiState,
    onNextQuanClick:(Quantity)->Unit,
    onHorizClick:(NumAlignment)->Unit,
    onEnglishClick:(Language)->Unit
) {
    // Q-20,40,60,80,100,H-Alignment,E-Language
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Log.d(TAG,"quantity=${gus.quantity}")

        val quanVal = decodeQ(gus.quantity)
        Log.d(TAG,"quanVal=$quanVal")
        Button(
            shape = CircleShape,
            onClick = {
                onNextQuanClick(gus.quantity)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple40
            )
        ) {
            Text(text = quanVal.toString(),
                fontSize = if(quanVal == 100) 12.sp else 15.sp)
        }

        Button(
            shape = CircleShape,
            onClick = {
                val hOrV =
                    if (gus.alignment == NumAlignment.Horizontal) NumAlignment.Vertical else NumAlignment.Horizontal
                onHorizClick(hOrV)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (gus.alignment == NumAlignment.Horizontal) Purple40 else Purple80
            )
        ) {
            Text(text = "H")
        }

        Button(
            shape = CircleShape,
            onClick = {
                val enOrMr =
                    if (gus.language == Language.English) Language.Marathi else Language.English
                onEnglishClick(enOrMr)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (gus.language == Language.English) Purple40 else Purple80
            )
        ) {
            Text(text = "E")
        }
    }
}

fun decodeQ(q: Quantity): Int {
    return when (q) {
        Quantity.Twenty -> 20
        Quantity.Forty -> 40
        Quantity.Sixty -> 60
        Quantity.Eighty -> 80
        Quantity.Hundred -> 100
    }

}
