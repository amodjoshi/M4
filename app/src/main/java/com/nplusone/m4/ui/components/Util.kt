package com.nplusone.m4.ui.components

import com.nplusone.m4.datalayer.Util
import com.nplusone.m4.ui.viewmodel.Digits
import com.nplusone.m4.ui.viewmodel.Language
import com.nplusone.m4.ui.viewmodel.Op
import com.nplusone.m4.ui.viewmodel.Quantity

object ScreenDim {
    var dpHeight: Float = -1.0f
    var dpWidth: Float = -1.0f
}

object Tag {
    val name = "M4"
}

// Given a number n in English, return language
// qualified string
fun addLeadingZeros(
    op: Op,
    n: Int,
    numDigits: Digits,
    language: Language
): String {
    var numString = if (language == Language.English) {
        n.toString()
    } else {
        Util.gimmeMr(n)
    }
    val mrZ = Util.gimmeMr(0)
    if (op == Op.ADD || op == Op.SUB) {
        val isAdd1Z = (numDigits == Digits.TWO) && (n < 10)
        val isAdd2Z3D = (numDigits == Digits.THREE) && (n < 10)
        val isAdd1Z3D = (numDigits == Digits.THREE) && (n in (10..99))

        if (language == Language.English && isAdd1Z) {
            numString = "0$n"
        }
        if (language == Language.English && isAdd2Z3D) {
            numString = "00$n"
        }
        if (language == Language.English && isAdd1Z3D) {
            numString = "0$n"
        }
        if (language == Language.Marathi && isAdd1Z) {
            numString = "$mrZ${Util.gimmeMr(n)}"
        }
        if (language == Language.Marathi && isAdd2Z3D) {
            numString = "$mrZ$mrZ${Util.gimmeMr(n)}"
        }
        if (language == Language.Marathi && isAdd1Z3D) {
            numString = "$mrZ${Util.gimmeMr(n)}"
        }
    }
    return numString
}