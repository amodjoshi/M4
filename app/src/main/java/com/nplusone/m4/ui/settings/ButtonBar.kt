package com.nplusone.m4.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.nplusone.m4.ui.theme.Purple40
import com.nplusone.m4.ui.theme.Purple80
import com.nplusone.m4.ui.viewmodel.Digits

@Composable
fun <T> ButtonBar(
    sel: T,
    coll: Set<Skill<T>>,
    onClick: (T) -> Unit,
    shape:Shape
) {
    // Number of digits
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (item in coll) {
            AtomButton(
                skill = item.skill,
                buttonText = item.display,
                color = resolveColor(sel,item.skill),
                shape = shape,
                onClick = {
                    onClick(it)
                })
        }
    }

}

fun <T> resolveColor(sel: T, pressed: T): Color {
    return if (sel == pressed) Purple40 else Purple80
}

@Composable
fun <T>AtomButton(
    skill: T,
    buttonText: String,
    color: Color,
    shape: Shape,
    onClick: (T) -> Unit
) {
    Button(
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        onClick = { onClick(skill) }
    ) {
        Text(text = buttonText)
    }
}

//fun resolveColor(subOpDigits: Digits): Color {
//    return if (selSubOpDigits == subOpDigits) Purple40 else Purple80
//}