package br.com.william.onofftodos.ui.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomTextField(
    label: String,
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        label = { Text(label) },
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
    )
}
