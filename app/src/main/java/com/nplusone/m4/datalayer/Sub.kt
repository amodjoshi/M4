package com.nplusone.m4.datalayer

import android.util.Log
import com.nplusone.m4.ui.settings.TAG
import com.nplusone.m4.ui.viewmodel.Digits
import com.nplusone.m4.ui.viewmodel.Language
import com.nplusone.m4.ui.viewmodel.McqBtnFont
import com.nplusone.m4.ui.viewmodel.Op

object Sub {

    fun generate(
        homeBundle: HomeBundle,
        numDigits: Digits,
        isBorrow: Boolean,
        language: Language
    ): HomeBundle {
        homeBundle.opSign = "-"
        val numPair = when (numDigits) {
            Digits.ONE -> gen1dig(isBorrow, numDigits)
            Digits.TWO -> gen2dig(isBorrow, numDigits)
            else -> gen3dig(isBorrow, numDigits)
        }

        // First number has to be larger!
        homeBundle.num1 = if (numPair.first >= numPair.second) numPair.first else numPair.second
        homeBundle.num2 = if (numPair.first >= numPair.second) numPair.second else numPair.first
        homeBundle.num1Mr = Util.gimmeMr(homeBundle.num1)
        homeBundle.num2Mr = Util.gimmeMr(homeBundle.num2)
        var spoofs = Sub.genSpoofs(
            numPair.first,
            numPair.second,
            numDigits
        )
        val ans = homeBundle.num1 - homeBundle.num2
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
        val ans = n1 - n2
        var coll = mutableListOf<Int>(
            ans + 10,
            ans + 20,
            ans + 30,
            ans + 40,
            if (ans > 0) (0..(ans - 1)).random() else n1 + 1,
            n1,
            n2,
            n2 + 1,
            if (ans > 10) ans - 10 else n2 + 2
        )
        // Remove if there is answer in the collection
        return coll.purge(ans).shuffled().take(4).toMutableList()


    }

    private fun gen3dig(
        isBorrow: Boolean,
        numDigits: Digits
    ): Pair<Int, Int> {
        val pUnits = gen1dig(isBorrow, numDigits)
        val pTens = gen1dig(isBorrow, numDigits)
        // MSD of second number has to be smaller
        val pHun = gen1dig(isBorrow, Digits.ONE)
        // Wipe off the 0 in the Hundred's number of the top number
        val isWipeHunTo0 = isBorrow && ((0..1).random() == 0)
        var num0 = pHun.first * 100 + pTens.first * 10 + pUnits.first * 1
        val num1 = pHun.second * 100 + pTens.second * 10 + pUnits.second * 1
        if (isWipeHunTo0) {
            num0 = (num0 - pTens.first * 10)
        }
        return Pair(num0, num1)
    }


    private fun gen2dig(
        isBorrow: Boolean,
        numDigits: Digits
    ): Pair<Int, Int> {
        val pUnits = gen1dig(isBorrow, numDigits)
        // Request MSD to have no borrow issues
        val pTens = gen1dig(isBorrow, Digits.ONE)
        val num0 = pTens.first * 10 + pUnits.first * 1
        val num1 = pTens.second * 10 + pUnits.second * 1
        return Pair(num0, num1)
    }

    private fun gen1dig(
        isBorrow: Boolean,
        numDigits: Digits
    ): Pair<Int, Int> {
        val num0 = Util.generateRandomInt(0, 9)
        val num1 = if (isBorrow && numDigits != Digits.ONE) {
            Util.generateRandomInt(0, 9)
        } else {
            if (num0 > 0)
                Util.generateRandomInt(0, num0 - 1)
            else
                0
        }
        return Pair(num0, num1)
    }
}