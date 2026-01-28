package com.nplusone.m4.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.nplusone.m4.datalayer.HomeBundle
import com.nplusone.m4.datalayer.Incorrects
import com.nplusone.m4.ui.components.Tag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// State Holders
// The classes that are responsible for the production
// of UI state and contain the necessary logic for that
// task are called state holders.

//The ViewModel component holds and exposes the state
// the UI consumes. The UI state is application data
// transformed by ViewModel. ViewModel lets your app
// follow the architecture principle of driving the
// UI from the model.
class GameViewModel(id: Int) : ViewModel() {
    val TAG = Tag.name;

    private var _uiState = MutableStateFlow(
        GameUiState(
            id = id,
            op = Op.SUB,
            numDigits = Digits.THREE,
            alignment = NumAlignment.Vertical,
            isOverflow = false,
            language = Language.English,
            isStart = false,
            quantity = Quantity.Twenty,
            isRotate = false,
            padaSelected = Padas.RANDOM,
            selMulSubSkill = MulSubSkill.Twox1,
            selDivSubSkill = DivSubSkill.Simple_2,
            selDivisor = Divisor.RANDOM,
            isLoggerOn = false,
            incorrectsRepo = mutableListOf<Incorrects>(),
            mcqBtnFont = McqBtnFont.LARGE
        )
    )

    // StateFlow is a data holder observable flow that
    // emits the current and new state updates.
    // A backing property lets you return something from
    // a getter other than the exact object.
    // The asStateFlow() makes this mutable state flow a read-only state flow.
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private fun boom(): GameUiState {
         return _uiState.value
    }

    fun onStartToggleBoom(value: Boolean) {
        var cs = boom().copy()
        cs.copy(isStart = value)
        _uiState.value = cs
    }

    fun onStartToggle(value: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isStart = value
            )
        }
    }

    fun onOpChange(opValue: Op) {
        // Save taps for 100 padas and the rest to 20.
        when(opValue){
            Op.PADAS->onNextQuanClick(Quantity.Eighty)
            else ->onNextQuanClick(Quantity.Hundred)
        }

        _uiState.update { currentState ->
            currentState.copy(
                op = opValue
            )
        }
    }

    fun onAddSubDigitsChange(digits: Digits) {
        _uiState.update { currentState ->
            currentState.copy(
                numDigits = digits
            )
        }
    }

    fun onOverflowClick(overflowVal: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isOverflow = overflowVal
            )
        }
    }

    fun onHorizClick(hOrv: NumAlignment) {
        _uiState.update { currentState ->
            currentState.copy(
                alignment = hOrv
            )
        }
    }

    fun onEnglishClick(enOrMr: Language) {
        _uiState.update { currentState ->
            currentState.copy(
                language = enOrMr
            )
        }
    }

    fun onQuantityClick(quantityVal: Quantity) {
        _uiState.update { currentState ->
            currentState.copy(
                quantity = quantityVal
            )
        }
    }

    fun onRotateClick(rotateVal: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isRotate = rotateVal
            )
        }
    }

    fun onPadasClick(padasVal: Padas) {
        val nxt = when (padasVal) {
            Padas.TWO -> Padas.THREE
            Padas.THREE -> Padas.FOUR
            Padas.FOUR -> Padas.FIVE
            Padas.FIVE -> Padas.SIX
            Padas.SIX -> Padas.SEVEN
            Padas.SEVEN -> Padas.EIGHT
            Padas.EIGHT -> Padas.NINE
            Padas.NINE -> Padas.RANDOM
            Padas.RANDOM -> Padas.TWO
        }
        _uiState.update { currentState ->
            currentState.copy(
                padaSelected = nxt
            )
        }
    }

    fun onNextQuanClick(quan: Quantity) {
        val nextQ = when (quan) {
            Quantity.Twenty -> Quantity.Forty
            Quantity.Forty -> Quantity.Sixty
            Quantity.Sixty -> Quantity.Eighty
            Quantity.Eighty -> Quantity.Hundred
            Quantity.Hundred -> Quantity.Twenty
        }
        Log.d(TAG,"quan=$quan,nextQ=$nextQ")
        _uiState.update { currentState ->
            currentState.copy(
                quantity = nextQ
            )
        }
    }

    fun onGenerateClick(homeBundle: HomeBundle) {
        _uiState.update { currentState ->
            currentState.copy(
                num1 = homeBundle.num1,
                num2 = homeBundle.num2,
                score = homeBundle.score,
                totalAttempted = homeBundle.totalAttempted,
                ansIdx = homeBundle.ansIdx,
                ans = homeBundle.ans,
                mcqBtnFont = homeBundle.mcqBtnFont,
                spoofs = homeBundle.spoofs,
                num1Mr = homeBundle.num1Mr,
                num2Mr = homeBundle.num2Mr,
                opSign = homeBundle.opSign
            )
        }

    }

    fun onMulSubSkillChange(msc: MulSubSkill) {
        val nxt = when (msc) {
            MulSubSkill.Twox1 -> MulSubSkill.Threex1
            MulSubSkill.Threex1 -> MulSubSkill.Twox2
            MulSubSkill.Twox2 -> MulSubSkill.Threex2
            MulSubSkill.Threex2 -> MulSubSkill.Twox1
        }
        _uiState.update { currentState ->
            currentState.copy(
                selMulSubSkill = nxt
            )
        }
    }

    fun onDivSubSkillChange(dssc: DivSubSkill) {
        val nxt = when (dssc) {
            DivSubSkill.Simple_2 -> DivSubSkill.Simple_3
            DivSubSkill.Simple_3 -> DivSubSkill.Hard_2
            DivSubSkill.Hard_2 -> DivSubSkill.Hard_3
            DivSubSkill.Hard_3 -> DivSubSkill.Simple_2
        }
        _uiState.update { currentState ->
            currentState.copy(
                selDivSubSkill = nxt
            )
        }
    }

    fun onDivisorChange(d: Divisor) {
        val nxt = when (d) {
            Divisor.TWO -> Divisor.THREE
            Divisor.THREE -> Divisor.FOUR
            Divisor.FOUR -> Divisor.FIVE
            Divisor.FIVE -> Divisor.SIX
            Divisor.SIX -> Divisor.SEVEN
            Divisor.SEVEN -> Divisor.EIGHT
            Divisor.EIGHT -> Divisor.NINE
            Divisor.NINE -> Divisor.RANDOM
            Divisor.RANDOM -> Divisor.TWO
        }
        _uiState.update { currentState ->
            currentState.copy(
                selDivisor = nxt
            )
        }
    }

    fun onLoggerClick(isLoggerOn: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isLoggerOn = isLoggerOn
            )
        }
    }

    fun onUpdateIncorrects(l: MutableList<Incorrects>) {
        _uiState.update { currentState ->
            currentState.copy(
                incorrectsRepo = l
            )
        }
    }
}