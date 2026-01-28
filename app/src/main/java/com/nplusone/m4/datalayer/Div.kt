package com.nplusone.m4.datalayer

import com.nplusone.m4.ui.viewmodel.DivSubSkill
import com.nplusone.m4.ui.viewmodel.Divisor
import com.nplusone.m4.ui.viewmodel.Language
import com.nplusone.m4.ui.viewmodel.Digits
import com.nplusone.m4.ui.viewmodel.McqBtnFont
import com.nplusone.m4.ui.viewmodel.Op

object Div {

    fun generate(
        homeBundle: HomeBundle,
        selDivSubSkill: DivSubSkill,
        selDivisor: Divisor,
        language: Language

    ): HomeBundle {
        homeBundle.opSign = "/"
        return when (selDivSubSkill) {
            DivSubSkill.Simple_2 -> gen(homeBundle, Digits.TWO, selDivisor, language, false)
            DivSubSkill.Simple_3 -> gen(homeBundle, Digits.THREE, selDivisor, language, false)
            DivSubSkill.Hard_2 -> gen(homeBundle, Digits.TWO, selDivisor, language, true)
            DivSubSkill.Hard_3 -> gen(homeBundle, Digits.THREE, selDivisor, language, true)
        }
    }

    private fun gen(
        homeBundle: HomeBundle,
        numeratorDigits: Digits,
        selDivisor: Divisor,
        language: Language,
        isHard: Boolean
    ): HomeBundle {
        var unitsP = 1
        var tensP = 1
        var hunP = 1
        var num = 1
        var ans = 1

        var den: Int = divisorToInt(selDivisor)

        if (numeratorDigits == Digits.TWO && !isHard) {
            unitsP = randNoOverflow(den)
            tensP = randNoOverflow(den)
            num = 10 * tensP + unitsP
        }

        if (numeratorDigits == Digits.THREE && !isHard) {
            unitsP = randNoOverflow(den)
            tensP = randNoOverflow(den)
            hunP = randNoOverflow(den)
            num = 100 * hunP + 10 * tensP + unitsP
        }

        if (numeratorDigits == Digits.TWO && isHard) {
            ans = (10..99).random()
            num = ans * den
        }

        if (numeratorDigits == Digits.THREE && isHard) {
            ans = (100..999).random()
            num = ans * den
        }
        return populate(num, den, homeBundle, language)
    }

    private fun populate(
        n1: Int,
        n2: Int,
        homeBundle: HomeBundle,
        language: Language
    ): HomeBundle {
        homeBundle.num1 = n1
        homeBundle.num2 = n2
        homeBundle.num1Mr = Util.gimmeMr(n1)
        homeBundle.num2Mr = Util.gimmeMr(n2)
        var spoofs = genSpoofs(n1, n2)
        val ans = homeBundle.num1 / homeBundle.num2
        val ansIdx = (0..3).random()
        spoofs[ansIdx] = ans

        val isSmallFont = spoofs.any { it > 9999 }
        val isMedFont = spoofs.any { it in 1000..9999 }
        homeBundle.mcqBtnFont = when {
            isSmallFont -> McqBtnFont.SMALL
            isMedFont -> McqBtnFont.MED
            else -> McqBtnFont.LARGE
        }
        homeBundle.ans = ans
        homeBundle.ansIdx = ansIdx
        homeBundle.spoofs = if (language == Language.English) {
            spoofs.map { it.toString() }.toList()
        } else {
            spoofs.toList().toMr<Int>()
        }
        return homeBundle
    }

    private fun genSpoofs(
        n1: Int,
        n2: Int
    ): MutableList<Int> {
        val ans = n1 / n2;
        var coll = mutableListOf<Int>(
            if (ans > 10) ans - 10 else ans + 10,
            if (ans > 100) ans - 20 else ans + 20,
            ans + 30,
            ans + 40,
            ans + 1,
            ans + 2,
            ans + 3,
            ans + 4,
            if (ans > 10) ans - 5 else ans + 5,
            if (ans > 20) ans - 15 else ans + 15,
            if (ans > 30) ans - 25 else ans + 25
            )
        // Make sure coll is clean of ans
        return coll.purge(ans).shuffled().take(4).toMutableList()
    }

    private fun divisorToInt(d: Divisor): Int {
        return when (d) {
            Divisor.TWO -> 2
            Divisor.THREE -> 3
            Divisor.FOUR -> 4
            Divisor.FIVE -> 5
            Divisor.SIX -> 6
            Divisor.SEVEN -> 7
            Divisor.EIGHT -> 8
            Divisor.NINE -> 9
            Divisor.RANDOM -> (1..9).random()
        }

    }

    // Given a divisor, return a simple numerator digit
    private fun randNoOverflow(d: Int): Int {
        val repo = hashMapOf<Int, List<Int>>(
            0 to listOf<Int>(0, 1, 2, 3, 4, 5, 6, 7, 8, 9),
            1 to listOf<Int>(0, 1, 2, 3, 4, 5, 6, 7, 8, 9),
            2 to listOf<Int>(0, 1, 2, 3, 4),
            3 to listOf<Int>(0, 1, 2, 3),
            4 to listOf<Int>(0, 1, 2),
            5 to listOf<Int>(0, 1),
            6 to listOf<Int>(0, 1),
            7 to listOf<Int>(0, 1),
            8 to listOf<Int>(0, 1),
            9 to listOf<Int>(0, 1),
        )
        val l = repo[d]
        val rand = (0 until (l?.size ?: 1)).random()
        return if (l != null) d * l[rand] else d
    }
}