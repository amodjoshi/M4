package com.nplusone.m4.datalayer

import com.nplusone.m4.ui.viewmodel.McqBtnFont
import com.nplusone.m4.ui.viewmodel.Op

data class HomeBundle (
    // Op needed for Random selection
    var opSign:String = "?",
    var num1:Int =-1,
    var num2:Int = -1,
    var score: Int = -1,
    var totalAttempted: Int = -1,
    var ansIdx: Int = -1,
    var ans:Int = 1,
    var spoofs: List<String> = mutableListOf("-1", "-2","-3","-4"),
    var num1Mr:String = "-1",
    var num2Mr:String = "-1",
    var mcqBtnFont: McqBtnFont = McqBtnFont.LARGE
)