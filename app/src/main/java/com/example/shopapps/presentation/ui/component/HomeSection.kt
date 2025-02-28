package com.example.shopapps.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeSection(
    title : String,
    title2 : String = "See All",
    content : @Composable ()-> Unit,
    navigateToSeeAll:()->Unit
) {
    Column {
        if (title != ""){
            SectionText(
                text = title,
                text2 = title2,
                navigateToMyProduct = navigateToSeeAll
            )
        }
        content()
    }
}