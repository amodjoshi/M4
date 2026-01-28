package com.nplusone.m4.datalayer

import android.util.Log
import com.nplusone.m4.ui.settings.TAG
import com.nplusone.m4.ui.viewmodel.GameUiState
import com.nplusone.m4.ui.viewmodel.Op

object Mapper {
    private var homeBundles = mutableListOf<HomeBundle>(
        HomeBundle(),
        HomeBundle(),
        HomeBundle(),
        HomeBundle()
    )

    fun startFlow(gus: GameUiState): HomeBundle {
        homeBundles[gus.id] = HomeBundle()
        gus.incorrectsRepo.clear()
        Log.d(TAG, "startFlow:homeBundles =$homeBundles")

        // -1 button click indicates the start flow
        bookKeep(gus, -1)
        generate(gus)
        return homeBundles[gus.id]
    }

    fun mcqButtonClickFlow(
        gus: GameUiState,
        clickedButtonId: Int
    ): HomeBundle {
        bookKeep(gus, clickedButtonId)
        generate(gus)
        return homeBundles[gus.id]
    }

    fun generate(
        gus: GameUiState
    ) {

        var homeBundle = homeBundles[gus.id]
        when (gus.op) {
            Op.ADD -> Add.generate(
                homeBundle = homeBundle,
                numDigits = gus.numDigits,
                isCarry = gus.isOverflow,
                language = gus.language
            )

            Op.SUB -> Sub.generate(
                homeBundle = homeBundle,
                numDigits = gus.numDigits,
                isBorrow = gus.isOverflow,
                language = gus.language
            )

            Op.PADAS -> Padas.generate(
                homeBundle = homeBundle,
                selectedPadas = gus.padaSelected,
                language = gus.language
            )

            Op.MUL -> Mul.generate(
                homeBundle = homeBundle,
                selMulSubSkill = gus.selMulSubSkill,
                isOverflow = gus.isOverflow,
                language = gus.language
            )

            Op.DIV -> Div.generate(
                homeBundle = homeBundle,
                selDivSubSkill = gus.selDivSubSkill,
                selDivisor = gus.selDivisor,
                language = gus.language
            )
            Op.RAND->Random.generate(
                homeBundle = homeBundle,
                language = gus.language
            )

        }

    }

    private fun bookKeep(
        gus: GameUiState,
        buttonId: Int
    ) {
        val homeBundle = homeBundles[gus.id]
        if (buttonId < 0) { // Start
            homeBundle.score = 0
            homeBundle.totalAttempted = 0
        } else {//MCQ button click flow
            homeBundle.totalAttempted += 1
            if (buttonId == gus.ansIdx) {
                homeBundle.score += 1
            } else {//
                val i = Incorrects(
                    sum = "${gus.num1}${gus.opSign}${gus.num2}",
                    correctAns = Util.ans(gus.num1, gus.num2, gus.opSign),
                    pressedVal = gus.spoofs[buttonId]
                )
                gus.incorrectsRepo.add(i)

            }

        }

    }

}