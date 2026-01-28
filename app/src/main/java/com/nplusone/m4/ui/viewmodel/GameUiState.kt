package com.nplusone.m4.ui.viewmodel

import com.nplusone.m4.datalayer.Incorrects

enum class Op { ADD, SUB, PADAS, MUL, DIV, RAND }
enum class Digits { ONE, TWO, THREE, FOUR, FIVE }
enum class Places { Units, Tens, Hundreds, Thousands, Tenthousands }
enum class NumAlignment { Vertical, Horizontal }
enum class Language { Marathi, English }
enum class Quantity { Twenty, Forty, Sixty, Eighty, Hundred }
enum class Padas { TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, RANDOM }

// Couple Multiple with overflow as carry
enum class MulSubSkill { Twox1, Threex1, Twox2, Threex2 }
enum class DivSubSkill { Simple_2, Simple_3, Hard_2, Hard_3 }
enum class Divisor { TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, RANDOM }
enum class McqBtnFont {SMALL,MED,LARGE}

data class GameUiState(
    // Settings parameters
    var id: Int,
    var isStart: Boolean,
    var op: Op,
    var numDigits: Digits,
    var isOverflow: Boolean,// carry or borrow based on parent Op
    var alignment: NumAlignment,
    var language: Language,
    var quantity: Quantity,
    var isRotate: Boolean, // Rotate the entire row?
    var padaSelected: Padas,// What has the user selected?
    var selMulSubSkill: MulSubSkill,// What is the sub skill for x?
    var selDivSubSkill: DivSubSkill,
    var selDivisor: Divisor,
    var isLoggerOn: Boolean,
    var incorrectsRepo: MutableList<Incorrects>,

    // Home parameters
    var num1: Int = -1,
    var num2: Int = -1,
    var score: Int = -1,
    var totalAttempted: Int = -1,
    var ans: Int = -1,
    var ansIdx: Int = -1,
    var spoofs: List<String> = listOf("-1", "-2", "-3", "-4"),
    var num1Mr: String = "-1",
    var num2Mr: String = "-1",
    var opSign: String = "?",// needed for Random ops
    var mcqBtnFont:McqBtnFont
)
