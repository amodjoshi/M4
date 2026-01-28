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
import com.nplusone.m4.ui.viewmodel.MulSubSkill
import com.nplusone.m4.ui.viewmodel.NumAlignment
import com.nplusone.m4.ui.viewmodel.Quantity

@Composable
fun Mul(
    gus: GameUiState,
    selMulSubSkill: MulSubSkill,
    isCarry:Boolean,
    onCarryChange:(Boolean)->Unit,
    onMulSubSkillChange: (MulSubSkill) -> Unit,
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
                onMulSubSkillChange(selMulSubSkill)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple40
            )
        ) {
            Text(text = decodeMulSubSkill(selMulSubSkill))
        }

        Button(
            shape = CircleShape,
            onClick = {
                onCarryChange(!gus.isOverflow)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (gus.isOverflow) Purple40 else Purple80
            )
        ) {
            Text(text = "C")
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

fun decodeMulSubSkill(mss:MulSubSkill):String{
    var subSkills = listOf<Skill<MulSubSkill>>(
        Skill(
            skill = MulSubSkill.Twox1,
            display = "2x1"
        ),
        Skill(
            skill = MulSubSkill.Threex1,
            display = "3x1"
        ),
        Skill(
            skill = MulSubSkill.Twox2,
            display = "2x2"
        ),
        Skill(
            skill = MulSubSkill.Threex2,
            display = "3x2"
        )
    )

    val match = subSkills.find { it.skill == mss }
    return match?.display ?: "No match"
}