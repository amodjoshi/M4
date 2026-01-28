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
import com.nplusone.m4.ui.viewmodel.DivSubSkill
import com.nplusone.m4.ui.viewmodel.Divisor
import com.nplusone.m4.ui.viewmodel.GameUiState
import com.nplusone.m4.ui.viewmodel.Language
import com.nplusone.m4.ui.viewmodel.NumAlignment
import com.nplusone.m4.ui.viewmodel.Quantity

@Composable
fun Div(
    gus: GameUiState,
    selDivSubSkill: DivSubSkill,
    onDivSubSkillChange: (DivSubSkill) -> Unit,
    selDivisor: Divisor,
    onDivisorChange: (Divisor) -> Unit,
    onNextQuanClick: (Quantity) -> Unit,
    onHorizClick: (NumAlignment) -> Unit,
    onEnglishClick: (Language) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Button(
            shape = CircleShape,
            onClick = {
                onDivSubSkillChange(selDivSubSkill)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple40
            )
        ) {
            Text(text = decodeDivSubSkill(selDivSubSkill))
        }

        Button(
            shape = CircleShape,
            onClick = {
                onDivisorChange(selDivisor)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple40
            )
        ) {
            Text(text = decodeDivisor(selDivisor))
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

fun decodeDivSubSkill(dss: DivSubSkill): String {
    var subSkills = listOf<Skill<DivSubSkill>>(
        Skill(
            skill = DivSubSkill.Simple_2,
            display = "Bas_2"
        ),
        Skill(
            skill = DivSubSkill.Simple_3,
            display = "Bas_3"
        ),
        Skill(
            skill = DivSubSkill.Hard_2,
            display = "Hard_2"
        ),
        Skill(
            skill = DivSubSkill.Hard_3,
            display = "Hard_3"
        )
    )
    val match = subSkills.find { it.skill == dss }
    return match?.display ?: "Nada"
}

fun decodeDivisor(d: Divisor): String {
    return when (d) {
        Divisor.TWO -> "2"
        Divisor.THREE -> "3"
        Divisor.FOUR -> "4"
        Divisor.FIVE -> "5"
        Divisor.SIX -> "6"
        Divisor.SEVEN -> "7"
        Divisor.EIGHT -> "8"
        Divisor.NINE -> "9"
        Divisor.RANDOM -> "R"
    }


}