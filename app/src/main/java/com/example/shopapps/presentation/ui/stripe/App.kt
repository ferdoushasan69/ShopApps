package com.example.shopapps.presentation.ui.stripe

import androidx.compose.runtime.Composable
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.rememberPaymentSheet


@Composable
fun App() {
    val paymentSheet = rememberPaymentSheet(::onPaymentSheetResult)

}

private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
    // implemented in the next steps
}