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
import com.nplusone.m4.ui.viewmodel.GameUiState
import com.nplusone.m4.ui.viewmodel.Language
import com.nplusone.m4.ui.viewmodel.NumAlignment
import com.nplusone.m4.ui.viewmodel.Op
import com.nplusone.m4.ui.viewmodel.Padas
import com.nplusone.m4.ui.viewmodel.Quantity

@Composable
fun Padas(
    gus: GameUiState,
    selPadasSubSkill: Padas,
    onPadasSubSkillChange: (Padas) -> Unit,
    onNextQuanClick: (Quantity) -> Unit,
    onHorizClick: (NumAlignment) -> Unit,
    onEnglishClick: (Language) -> Unit
) {
//    val subSkill = listOf<Skill<Padas>>(
//        Skill(
//            skill = Padas.TWO,
//            display = "2"
//        ),
//        Skill(
//            skill = Padas.THREE,
//            display = "3"
//        ), Skill(
//            skill = Padas.FOUR,
//            display = "4"
//        ),
//        Skill(
//            skill = Padas.FIVE,
//            display = "5"
//        ), Skill(
//            skill = Padas.SIX,
//            display = "6"
//        ),
//        Skill(
//            skill = Padas.SEVEN,
//            display = "7"
//        ), Skill(
//            skill = Padas.EIGHT,
//            display = "8"
//        ),
//        Skill(
//            skill = Padas.NINE,
//            display = "9"
//        ),
//        Skill(
//            skill = Padas.RANDOM,
//            display = "R"
//        )
//    )

    Button(
        shape = CircleShape,
        onClick = {
            onPadasSubSkillChange(selPadasSubSkill)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Purple40
        )
    ) {
        Text(text = decodePadas(selPadasSubSkill))
    }

    // Q-20,40,60,80,100,H-Alignment,E-Language
    MetaData(
        gus = gus,
        onNextQuanClick = { onNextQuanClick(it) },
        onHorizClick = { onHorizClick(it) },
        onEnglishClick = { onEnglishClick(it) }
    )


}

fun decodePadas(p: Padas): String {
    return when (p) {
        Padas.TWO -> "2"
        Padas.THREE -> "3"
        Padas.FOUR -> "4"
        Padas.FIVE -> "5"
        Padas.SIX -> "6"
        Padas.SEVEN -> "7"
        Padas.EIGHT -> "8"
        Padas.NINE -> "9"
        Padas.RANDOM -> "R"
    }

}
