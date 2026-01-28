package com.nplusone.m4.datalayer

import android.util.Log
import com.nplusone.m4.ui.settings.TAG
import com.nplusone.m4.ui.viewmodel.Digits
import com.nplusone.m4.ui.viewmodel.Language
import com.nplusone.m4.ui.viewmodel.McqBtnFont
import com.nplusone.m4.ui.viewmodel.Op

object Add {

    fun generate(
        homeBundle: HomeBundle,
        numDigits: Digits,
        isCarry: Boolean,
        language: Language
    ): HomeBundle {
        val numPair = when (numDigits) {
            Digits.ONE -> gen1dig(isCarry)
            Digits.TWO -> gen2dig(isCarry)
            else -> gen3dig(isCarry)
        }
        homeBundle.opSign = "+"
        homeBundle.num1 = numPair.first
        homeBundle.num2 = numPair.second
        homeBundle.num1Mr = Util.gimmeMr(numPair.first)
        homeBundle.num2Mr = Util.gimmeMr(numPair.second)

        var spoofs = genSpoofs(
            numPair.first,
            numPair.second,
            numDigits
        )
        val ans = homeBundle.num1 + homeBundle.num2
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

    private fun genSpoofs(n1: Int, n2: Int, numDigits: Digits): MutableList<Int> {
        val ans = n1 + n2
        var coll = mutableListOf<Int>(
            if (ans > 10) ans + 10 else ans + 1,
            if (ans > 10) ans + 20 else ans + 2,
            if (ans > 10) ans + 30 else ans + 3,
            if (ans > 10) ans + 40 else ans + 4,
            if (ans > 20) ans - 10 else ans + 5,
            if (ans > 20) ans - 20 else ans + 6,
            if (ans > 20) ans - 30 else ans + 7,
            if (ans > 20) ans - 40 else ans + 8,
            n1,
            n2,
            n1 + 1,
            n2 + 1,
            if (ans > 0) ans - 1 else n1 + 2,
            if (ans > 1) ans - 2 else n2 + 2,
            if (ans > 10) ans - 10 else n1 + 3,
            if (ans > 20) ans - 20 else n2 + 3,
            if (ans == 3) 6 else n1 + 4,
            if (ans == 6) 3 else n2 + 4
        )
        // Pick 4 at random early to make room for
        // the intelligent spoof, if one is present
        var firstPassList = coll.purge(ans).shuffled().take(4).toMutableList()

        // Intelligent spoof where:
        // 1. Ans > 9
        // 2. Units place add > 9
        // 3. Then swap units place with the 2 digit number
        if (ans > 9) {
            val unitsAdd = n1.units() + n2.units()
            if (unitsAdd > 9) {
                val tensAdd = n1.tens() + n2.tens()
                val goldSpoof = tensAdd * 100 + unitsAdd
                firstPassList.add(goldSpoof)
            }
        }

        if (ans > 99) {
            val unitsAdd = n1.units() + n2.units()
            if (unitsAdd > 9) {
                val tensAdd = n1.tens() + n2.tens()
                val goldSpoof = tensAdd * 100 + unitsAdd
                firstPassList.add(goldSpoof)
            }
            val tensAdd = n1.tens() + n2.tens()
            if (tensAdd > 9) {
                val hundAdd = n1.hund() + n2.hund()
                val goldSpoof = hundAdd*1000 + tensAdd + unitsAdd
                firstPassList.add(goldSpoof)
            }

        }
        return firstPassList.purge(ans).shuffled().take(4).toMutableList()


    }

    private fun gen3dig(isCarry: Boolean): Pair<Int, Int> {
        val pUnits = gen1dig(isCarry)
        val pTens = gen1dig(isCarry)
        val pHun = gen1dig(isCarry)
        val num0 = pHun.first * 100 + pTens.first * 10 + pUnits.first * 1
        val num1 = pHun.second * 100 + pTens.second * 10 + pUnits.second * 1
        return Pair(num0, num1)
    }

    private fun gen2dig(isCarry: Boolean): Pair<Int, Int> {
        val pUnits = gen1dig(isCarry)
        val pTens = gen1dig(isCarry)
        val num0 = pTens.first * 10 + pUnits.first * 1
        val num1 = pTens.second * 10 + pUnits.second * 1
        return Pair(num0, num1)
    }

    private fun gen1dig(isCarry: Boolean): Pair<Int, Int> {
        val num0 = Util.generateRandomInt(0, 9)
        val num1 = if (isCarry) {
            Util.generateRandomInt(0, 9)
        } else {
            val diff = 10 - num0;
            Util.generateRandomInt(0, diff - 1)
        }
        return Pair(num0, num1)
    }
}