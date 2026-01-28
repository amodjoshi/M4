package com.nplusone.m4.ui.settings

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
import com.nplusone.m4.ui.theme.Purple40
import com.nplusone.m4.ui.theme.Purple80
import com.nplusone.m4.ui.viewmodel.Digits
import com.nplusone.m4.ui.viewmodel.GameUiState
import com.nplusone.m4.ui.viewmodel.Language
import com.nplusone.m4.ui.viewmodel.NumAlignment
import com.nplusone.m4.ui.viewmodel.Op
import com.nplusone.m4.ui.viewmodel.Quantity


@Composable
fun AddSub(
    gus: GameUiState,
    parentOp: Op,
    selSubOpDigits: Digits,
    onAddSubDigitsChange: (Digits) -> Unit,
    onOverflowClick: (Boolean) -> Unit,
    onNextQuanClick:(Quantity)->Unit,
    onHorizClick: (NumAlignment) -> Unit,
    onEnglishClick: (Language) -> Unit
) {

    val subOpDigits = setOf<Skill<Digits>>(
        Skill(
            skill = Digits.ONE,
            display = "1"
        ),
        Skill(
            skill = Digits.TWO,
            display = "2"
        ),
        Skill(
            skill = Digits.THREE,
            display = "3"
        )
    )

    ButtonBar(
        sel = selSubOpDigits,
        coll = subOpDigits,
        onClick = {
            onAddSubDigitsChange(it)
        },
        shape = CircleShape
    )

    // C-Carry
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically

    ) {
        val carryOrBorrowSel = if (parentOp == Op.ADD) "C" else "B"
        Button(
            shape = CircleShape,
            onClick = {
                onOverflowClick(!gus.isOverflow)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (gus.isOverflow) Purple40 else Purple80
            )
        ) {
            Text(text = carryOrBorrowSel)
        }
    }

    // Q-20,40,60,80,100,H-Alignment,E-Language
    MetaData(
        gus = gus,
        onNextQuanClick = { onNextQuanClick(it) },
        onHorizClick = { onHorizClick(it) },
        onEnglishClick = { onEnglishClick(it) }
    )

}

