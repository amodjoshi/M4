package com.nplusone.m4.datalayer

import com.nplusone.m4.ui.viewmodel.Language
import com.nplusone.m4.ui.viewmodel.McqBtnFont
import com.nplusone.m4.ui.viewmodel.MulSubSkill
import com.nplusone.m4.ui.viewmodel.Op

object Mul {
    fun generate(
        homeBundle: HomeBundle,
        selMulSubSkill: MulSubSkill,
        isOverflow: Boolean,
        language: Language
    ): HomeBundle {
        homeBundle.opSign = "x"
        return when (selMulSubSkill) {
            MulSubSkill.Twox1 -> twoby1(homeBundle, isOverflow, language)
            MulSubSkill.Threex1 -> threeby1(homeBundle, isOverflow, language)
            MulSubSkill.Twox2 -> twoby2(homeBundle, isOverflow, language)
            MulSubSkill.Threex2 -> threeby2(homeBundle, isOverflow, language)
        }
    }

    private fun threeby2(
        homeBundle: HomeBundle,
        isOverflow: Boolean,
        language: Language
    ): HomeBundle {
        var unitsP: Int = 1;
        var tensP: Int = 1;
        var hunP: Int = 1
        var n2 = (0..99).random()
        if (isOverflow) {
            unitsP = (0..9).random()
            tensP = (0..9).random()
            hunP = (1..9).random()
        } else {
            unitsP = randNoOverflow(n2)
            tensP = randNoOverflow(n2)
            hunP = randNoOverflow(n2)
        }
        var n1 = (100 * hunP) + (10 * tensP) + unitsP

        return populate(n1, n2, homeBundle, language)
    }

    private fun twoby2(
        homeBundle: HomeBundle,
        isOverflow: Boolean,
        language: Language
    ): HomeBundle {
        var unitsP: Int = 1;
        var tensP: Int = 1
        var n2 = (0..99).random()
        if (isOverflow) {
            unitsP = (0..9).random()
            tensP = (0..9).random()
        } else {
            unitsP = randNoOverflow(n2)
            tensP = randNoOverflow(n2)
        }
        var n1 = (10 * tensP) + unitsP

        return populate(n1, n2, homeBundle, language)
    }

    private fun threeby1(
        homeBundle: HomeBundle,
        isOverflow: Boolean,
        language: Language
    ): HomeBundle {
        var unitsP: Int = 1;
        var tensP: Int = 1;
        var hunP: Int = 1
        var n2 = (0..9).random()
        if (isOverflow) {
            unitsP = (0..9).random()
            tensP = (0..9).random()
            hunP = (1..9).random()
        } else {
            unitsP = randNoOverflow(n2)
            tensP = randNoOverflow(n2)
            hunP = randNoOverflow(n2)
        }
        var n1 = (100 * hunP) + (10 * tensP) + unitsP

        return populate(n1, n2, homeBundle, language)
    }

    private fun twoby1(
        homeBundle: HomeBundle,
        isOverflow: Boolean,
        language: Language
    ): HomeBundle {
        var unitsP: Int = 1;
        var tensP: Int = 1
        var n2 = (0..9).random()
        if (isOverflow) {
            unitsP = (0..9).random()
            tensP = (0..9).random()
        } else {
            unitsP = randNoOverflow(n2)
            tensP = randNoOverflow(n2)
        }
        var n1 = (10 * tensP) + unitsP

        return populate(n1, n2, homeBundle, language)
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
        var spoofs = genSpoofs(
           n1,n2
        )
        val ans = homeBundle.num1 * homeBundle.num2
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
        val ans = n1 * n2;
        var coll = mutableListOf<Int>(
            if (ans == 0) (1..10).random() else (n1 + 1) * n2,
            if (ans == 0) (1..10).random() else (n1 + 2) * n2,
            if (ans == 0) (1..10).random() else (n1 + 3) * n2,
            if (ans == 0) (1..10).random() else (n1 + 4) * n2,
            if (ans == 0 && n1 == 0) n2 else n1,
            if (ans == 0 && n2 == 0) n1 else n2,
            if (ans > 10) (n1 - 1) * n2 else (n1 * n2) + 1,
            ans+10,
            ans+20,
            ans+30,
            ans+100,
            ans+200,
            ans+300,
            if(ans > 100) ans - 100 else ans+40,
            if(ans > 100) ans - 10 else ans+50,
            if(ans > 100) ans - 20 else ans+60,

        )
        // Make sure coll is clean of ans
        return coll.purge(ans).shuffled().take(4).toMutableList()
    }

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
        return if (l != null) l[rand] else 1
    }
}