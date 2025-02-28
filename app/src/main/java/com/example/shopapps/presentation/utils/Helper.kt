package com.example.shopapps.presentation.utils

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp

object DialogHelper {

    @Composable
    fun ShowDialog(
        title: String?,
        textContent: String?,
        textConfirm: String = "OK",
        textConfirmSize: Float = 14f,
        onConfirm: () -> Unit = {},
        onDismiss: () -> Unit = {}
    ) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {
                Text(text = title ?: "Title", fontSize = 18.sp)
            },
            text = {
                Text(text = textContent ?: "Message", fontSize = 14.sp)
            },
            confirmButton = {
                TextButton(onClick = {
                    onConfirm()
                    onDismiss()
                }) {
                    Text(textConfirm, fontSize = textConfirmSize.sp)
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("Cancel", fontSize = textConfirmSize.sp)
                }
            }
        )
    }

    @Composable
    fun ShowDialogWarning(
        title: String?,
        textContent: String?,
        onConfirm: () -> Unit = {},
        onDismiss: () -> Unit = {}
    ) {
        ShowDialog(
            title = title,
            textContent = textContent,
            textConfirm = "Proceed",
            onConfirm = onConfirm,
            onDismiss = onDismiss
        )
    }

    @Composable
    fun ShowDialogSuccess(
        title: String?,
        textContent: String?,
        textConfirm: String = "OK",
        textConfirmSize: Float = 14f,
        onConfirm: () -> Unit = {}
    ) {
        ShowDialog(
            title = title,
            textContent = textContent,
            textConfirm = textConfirm,
            textConfirmSize = textConfirmSize,
            onConfirm = onConfirm
        )
    }

    @Composable
    fun ShowDialogError(
        onConfirm: () -> Unit,
        onDismiss: () -> Unit
    ) {
        AlertDialog(
            onDismissRequest = { onDismiss()},
            title = { Text(text = "Error") },
            text = { Text("An error occurred. Please try again.") },
            confirmButton = {
                TextButton(onClick = { onConfirm()}) {
                    Text("OK")
                }
            }
        )
    }

}
