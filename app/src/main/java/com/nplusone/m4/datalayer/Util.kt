package com.nplusone.m4.datalayer

import com.nplusone.m4.ui.viewmodel.Digits
import com.nplusone.m4.ui.viewmodel.Op
import com.nplusone.m4.ui.viewmodel.Places
import com.nplusone.m4.ui.viewmodel.Quantity

object Util {

    fun decodeQ(q: Quantity): Int {
        return when (q) {
            Quantity.Twenty -> 20
            Quantity.Forty -> 40
            Quantity.Sixty -> 60
            Quantity.Eighty -> 80
            Quantity.Hundred -> 100
        }
    }

    fun generateRandomInt(min: Int, max: Int): Int {
        return (min..max).random()
    }

    private fun gimmeNumDigits(n: Int): Digits {
        return if (n in 0..9) {
            Digits.ONE
        } else if (n in 10..99) {
            Digits.TWO
        } else if (n in 100..999) {
            Digits.THREE
        } else if (n in 1000..9999) {
            Digits.FOUR
        } else {
            Digits.FIVE
        }
    }

    fun modOrDiv(num: Int): HashMap<Places, Int> {
        var hm = HashMap<Places, Int>()
        when (gimmeNumDigits(num)) {
            Digits.ONE -> hm[Places.Units] = num%10
            Digits.TWO -> {
                hm[Places.Units] = num%10
                hm[Places.Tens] = (num % 100) / 10
            }
            Digits.THREE -> {
                hm[Places.Units] = num%10
                hm[Places.Tens] = (num % 100) / 10
                hm[Places.Hundreds] = (num % 1000) / 100
            }
            Digits.FOUR -> {
                hm[Places.Units] = num%10
                hm[Places.Tens] = (num % 100) / 10
                hm[Places.Hundreds] = (num % 1000) / 100
                hm[Places.Thousands] = (num % 10000) / 1000
            }
            Digits.FIVE -> {
                hm[Places.Units] = num%10
                hm[Places.Tens] = (num % 100) / 10
                hm[Places.Hundreds] = (num % 1000) / 100
                hm[Places.Thousands] = (num % 10000) / 1000
                hm[Places.Tenthousands] = (num % 100000) / 10000
            }

//            Digits.ONE -> Triple(num % 10, 0, 0)
//            Digits.TWO -> Triple(num % 10, (num % 100) / 10, 0)
//            Digits.THREE -> Triple(num % 10, (num % 100) / 10, (num % 1000) / 100)
        }

        return hm
    }

    fun ans(n1:Int,n2:Int,opSign:String):String{
        return when(opSign){
            "+"->(n1+n2).toString()
            "-"->(n1-n2).toString()
            "x"->(n1*n2).toString()
            else->(n1/n2).toString()
        }
    }

    fun decodeOp(op: Op):String{
        return when(op){
            Op.ADD->"+"
            Op.SUB->"-"
            Op.MUL->"x"
            Op.DIV->"/"
            Op.PADAS->"x"
            else->"?"
        }

    }
    // Upto thousand
    fun gimmeMr(num: Int): String {
        val EnToMrMap = mapOf(
            "0" to "\u0966",
            "1" to "\u0967",
            "2" to "\u0968",
            "3" to "\u0969",
            "4" to "\u096A",
            "5" to "\u096B",
            "6" to "\u096C",
            "7" to "\u096D",
            "8" to "\u096E",
            "9" to "\u096F"
        )
        val hm = modOrDiv(num)
        val units = hm[Places.Units];
        val tens = hm[Places.Tens];
        val hun = hm[Places.Hundreds];
        val thou = hm[Places.Thousands];
        val tenThou = hm[Places.Tenthousands];

        val unitsStr = EnToMrMap[units.toString()]
        val tensStr = EnToMrMap[tens.toString()]
        val hunStr = EnToMrMap[hun.toString()]
        val thouStr = EnToMrMap[thou.toString()]
        val tenThouStr = EnToMrMap[tenThou.toString()]

        return when (gimmeNumDigits(num)) {
            Digits.ONE -> "$unitsStr"
            Digits.TWO -> "$tensStr$unitsStr"
            Digits.THREE -> "$hunStr$tensStr$unitsStr"
            Digits.FOUR -> "$thouStr$hunStr$tensStr$unitsStr"
            Digits.FIVE -> "$tenThouStr$thouStr$hunStr$tensStr$unitsStr"
        }

    }
}