package com.example.shopapps.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearchChange: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(
            horizontal = 16.dp,
        )
    ) {
        Text(
            "Search Products",
            modifier = Modifier.padding(
                start = 16.dp,
                top = 8.dp
            ),
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
        )
        Spacer(Modifier.height(8.dp))
        TextField(onValueChange = {

            searchQuery = it
            onSearchChange(it)
        },
            maxLines = 1,
            placeholder = { Text("Search product") },
            value = searchQuery,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            modifier = Modifier.clip(RoundedCornerShape(32.dp))
                .fillMaxWidth())

    }
}