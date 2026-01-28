package com.nplusone.m4.datalayer

import com.nplusone.m4.ui.viewmodel.Digits
import com.nplusone.m4.ui.viewmodel.DivSubSkill
import com.nplusone.m4.ui.viewmodel.Divisor
import com.nplusone.m4.ui.viewmodel.Language
import com.nplusone.m4.ui.viewmodel.MulSubSkill
import com.nplusone.m4.ui.viewmodel.Op

object Random {
    fun generate(
        homeBundle: HomeBundle,
        language: Language
    ): HomeBundle {
        return when ((0..3).random()) {
            0 -> Add.generate(
                homeBundle = homeBundle,
                numDigits = Digits.THREE,
                isCarry = true,
                language = language
            )

            1 -> Sub.generate(
                homeBundle = homeBundle,
                numDigits = Digits.THREE,
                isBorrow = true,
                language = language
            )

            2 -> Mul.generate(
                homeBundle = homeBundle,
                selMulSubSkill = MulSubSkill.Threex2,
                isOverflow = true,
                language = language
            )

            3 -> Div.generate(
                homeBundle = homeBundle,
                selDivSubSkill = DivSubSkill.Hard_3,
                selDivisor = Divisor.RANDOM,
                language = language
            )

            else -> {
                Div.generate(
                    homeBundle = homeBundle,
                    selDivSubSkill = DivSubSkill.Hard_3,
                    selDivisor = Divisor.RANDOM,
                    language = language
                )
            }
        }
    }
}