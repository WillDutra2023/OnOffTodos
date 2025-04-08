package br.com.william.onofftodos.ui.components

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ButtonOutline(text: String, onClick: () -> Unit, modifier: Modifier) {
    OutlinedButton(
        modifier = modifier,
        onClick = { onClick() }) {
        Text(text)
    }
}