package com.nplusone.m4.ui.settings

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.nplusone.m4.ui.theme.Purple40
import com.nplusone.m4.ui.theme.Purple80
import com.nplusone.m4.ui.theme.PurpleGrey40
import com.nplusone.m4.ui.theme.PurpleGrey80
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.nplusone.m4.ui.components.Logger
import com.nplusone.m4.ui.components.Tag
import com.nplusone.m4.ui.viewmodel.Digits
import com.nplusone.m4.ui.viewmodel.DivSubSkill
import com.nplusone.m4.ui.viewmodel.Divisor
import com.nplusone.m4.ui.viewmodel.GameUiState
import com.nplusone.m4.ui.viewmodel.Language
import com.nplusone.m4.ui.viewmodel.MulSubSkill
import com.nplusone.m4.ui.viewmodel.NumAlignment
import com.nplusone.m4.ui.viewmodel.Op
import com.nplusone.m4.ui.viewmodel.Quantity
import com.nplusone.m4.ui.viewmodel.Padas

val TAG = Tag.name

data class Skill<T>(
    val skill: T,
    val display: String
)

@Composable
fun SettingsScreen(
    gus: GameUiState,
    onNavToHome: (Boolean) -> Unit,
    onOpChange: (Op) -> Unit,
    onAddSubDigitsChange: (Digits) -> Unit,
    onOverflowClick: (Boolean) -> Unit,
    onHorizClick: (NumAlignment) -> Unit,
    onEnglishClick: (Language) -> Unit,
    onQuantityClick: (Quantity) -> Unit,
    onRotateClick: (Boolean) -> Unit,
    onPadasSelected: (Padas) -> Unit,
    onNextQuanClick: (Quantity) -> Unit,
    onMulSubSkillChange: (MulSubSkill) -> Unit,
    onDivSubSkillChange:(DivSubSkill)->Unit,
    onDivisorChange:(Divisor)->Unit,
    onLogClick:()->Unit
) {
    val selOp = gus.op
    val opMap1 = setOf<Skill<Op>>(
        Skill(
            skill = Op.ADD,
            display = "+"
        ),
        Skill(
            skill = Op.SUB,
            display = "-"
        ),
        Skill(
            skill = Op.PADAS,
            display = "P"
        )
    )

    val opMap2 = setOf<Skill<Op>>(
        Skill(
            skill = Op.MUL,
            display = "x"
        ),
        Skill(
            skill = Op.DIV,
            display = "/"
        ),
        Skill(
            skill = Op.RAND,
            display = "R"
        ),
    )


    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SwitchMinimalExample(
                checked = gus.isStart,
                onToggle = { value ->
                    onNavToHome(value)
                }
            )
            Button(
                shape = CircleShape,
                onClick = {
                    onRotateClick(!gus.isRotate)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (gus.isRotate) Purple40 else Purple80
                )
            ) {
                Text(text = "R")
            }
        }
        ButtonBar(
            sel = gus.op,
            coll = opMap1,
            onClick = { opValue ->
                onOpChange(opValue)
            },
            shape = RoundedCornerShape(5.dp)
        )
        ButtonBar(
            sel = gus.op,
            coll = opMap2,
            onClick = { opValue ->
                onOpChange(opValue)
            },
            shape = RoundedCornerShape(5.dp)
        )

        if (selOp == Op.ADD) {

            AddSub(
                gus,
                Op.ADD,
                gus.numDigits,
                onAddSubDigitsChange,
                onOverflowClick,
                onNextQuanClick,
                onHorizClick,
                onEnglishClick
            )
        }
        if (selOp == Op.SUB) {

            AddSub(
                gus,
                Op.SUB,
                gus.numDigits,
                onAddSubDigitsChange,
                onOverflowClick,
                onNextQuanClick,
                onHorizClick,
                onEnglishClick
            )
        }

        // Padas
        if (selOp == Op.PADAS) {
            Padas(
                gus = gus,
                selPadasSubSkill = gus.padaSelected,
                onPadasSubSkillChange = {
                    //Log.d(TAG,"Padas $it clicked..")
                    onPadasSelected(it)
                },
                onNextQuanClick = onNextQuanClick,
                onHorizClick = onHorizClick,
                onEnglishClick = onEnglishClick
            )
        }

        //Mul
        if (selOp == Op.MUL) {
            Mul(
                gus = gus,
                selMulSubSkill = gus.selMulSubSkill,
                isCarry = gus.isOverflow,
                onCarryChange = onOverflowClick,
                onMulSubSkillChange = onMulSubSkillChange,
                onNextQuanClick = onNextQuanClick,
                onHorizClick = onHorizClick,
                onEnglishClick = onEnglishClick
            )
        }

        // Div
        if(selOp == Op.DIV){

            Div(
                gus = gus,
                selDivSubSkill = gus.selDivSubSkill,
                onDivSubSkillChange = onDivSubSkillChange,
                selDivisor = gus.selDivisor,
                onDivisorChange = onDivisorChange,
                onNextQuanClick = onNextQuanClick,
                onHorizClick = onHorizClick,
                onEnglishClick = onEnglishClick
            )
        }

        // Random
        if(selOp == Op.RAND){

            Random(
                gus = gus,
                onNextQuanClick = onNextQuanClick,
                onHorizClick = onHorizClick,
                onEnglishClick = onEnglishClick
            )
        }
        
        // Version and copyrights
        val gradientColors = listOf(Purple80, Purple40)

        Text(text = "\u00A9-Nplusone-v1.1",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = gradientColors
                )
            )
            )

    }// Column end
}


@Composable
fun SwitchMinimalExample(
    checked: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Switch(
        checked = checked,
        onCheckedChange = {
            Log.d(TAG, "Switch toggle val = $it")
            onToggle(it)
        }
    )
}