package com.nplusone.m4.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nplusone.m4.ui.viewmodel.GameUiState
import com.nplusone.m4.ui.viewmodel.Language
import com.nplusone.m4.ui.viewmodel.NumAlignment
import com.nplusone.m4.ui.viewmodel.Quantity

@Composable
fun Random(
    gus: GameUiState,
    onNextQuanClick: (Quantity) -> Unit,
    onHorizClick: (NumAlignment) -> Unit,
    onEnglishClick: (Language) -> Unit
    ){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically

    ) {
        // Q-20,40,60,80,100,H-Alignment,E-Language
        MetaData(
            gus = gus,
            onNextQuanClick = { onNextQuanClick(it) },
            onHorizClick = { onHorizClick(it) },
            onEnglishClick = { onEnglishClick(it) }
        )
    }
}