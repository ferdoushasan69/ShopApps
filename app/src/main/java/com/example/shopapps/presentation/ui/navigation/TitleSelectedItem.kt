package com.example.shopapps.presentation.ui.navigation


import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class TitleSelectedItem(
    val title : String,
    val selectedItem : ImageVector,
    val unSelectedItem : ImageVector
)
