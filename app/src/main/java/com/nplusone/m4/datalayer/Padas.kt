package com.nplusone.m4.datalayer

import com.nplusone.m4.ui.viewmodel.Digits
import com.nplusone.m4.ui.viewmodel.Language
import com.nplusone.m4.ui.viewmodel.McqBtnFont
import com.nplusone.m4.ui.viewmodel.Padas

object Padas {

    fun generate(
        homeBundle: HomeBundle,
        selectedPadas: Padas,
        language: Language
    ): HomeBundle {
        homeBundle.opSign = "x"
        val num1 = (0..10).random()

        val num2 = when (selectedPadas) {
            Padas.TWO -> 2
            Padas.THREE -> 3
            Padas.FOUR -> 4
            Padas.FIVE -> 5
            Padas.SIX -> 6
            Padas.SEVEN -> 7
            Padas.EIGHT -> 8
            Padas.NINE -> 9
            Padas.RANDOM -> (0..10).random()
        }

        homeBundle.num1 = num1
        homeBundle.num2 = num2
        homeBundle.num1Mr = Util.gimmeMr(num1)
        homeBundle.num2Mr = Util.gimmeMr(num2)
        var spoofs = genSpoofs(num1, num2, language)
        val ansIdx = (0..3).random()
        val ans = num1 * num2
        homeBundle.ans = ans
        spoofs[ansIdx] = if (language == Language.Marathi) {
            Util.gimmeMr(ans)
        } else {
            ans.toString()
        }
        homeBundle.spoofs = spoofs
        homeBundle.ansIdx = ansIdx
        homeBundle.mcqBtnFont= McqBtnFont.LARGE
        return homeBundle
    }

    fun genSpoofs(n1: Int, n2: Int, language: Language): MutableList<String> {
        val ans = n1 * n2;
        var coll = mutableListOf<Int>(
            if (ans == 0) (1..10).random() else (n1 + 1) * n2,
            if (ans == 0) (1..10).random() else (n1 + 2) * n2,
            if (ans == 0) (1..10).random() else (n1 + 3) * n2,
            if (ans == 0) (1..10).random() else (n1 + 4) * n2,
            if (ans == 0 && n1 == 0) n2 else n1,
            if (ans == 0 && n2 == 0) n1 else n2,
            if (n1 * n2 > 10) (n1 - 1) * n2 else (n1 * n2) + 1
        )
        // Make sure coll is clean of ans
        //coll.removeIf { it == ans }
        return coll.purge(ans).shuffled().take(4).map {
            if (language == Language.Marathi) {
                Util.gimmeMr(
                    it
                )
            } else {
                it.toString()
            }
        }
            .toMutableList()
    }
}