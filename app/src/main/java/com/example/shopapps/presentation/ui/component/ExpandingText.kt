package com.example.shopapps.presentation.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.substring
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import com.example.shopapps.presentation.ui.theme.primary

private const val minimized_maxLines = 3

@Composable
fun ExpandingText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit
) {
    var isExpanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    var isClickable by remember { mutableStateOf(false) }
    var finalText by remember { mutableStateOf(text) }

    var textLayoutResult = textLayoutResultState.value

    LaunchedEffect(textLayoutResult) {
        if (textLayoutResult == null) return@LaunchedEffect

        when{
            isExpanded ->{
                finalText = "$text show less"
            }
            !isExpanded && textLayoutResult!!.hasVisualOverflow->{
                val lastCharIndex = textLayoutResult!!.getLineEnd(minimized_maxLines-1)
                val showMoreString = "...Show more"
                val adjustText = text
                    .substring(startIndex = 0, endIndex = lastCharIndex)
                    .dropLast(showMoreString.length)
                    .dropLastWhile { it == ' ' || it == ',' }
                finalText = "$adjustText$showMoreString"
                isClickable = true
            }
        }
    }

    val annotatedString = buildAnnotatedString {
        val showMoreIndex  = finalText.indexOf("...Show More")
        val showLessIndex = finalText.indexOf("Show Less")

        if (showMoreIndex != -1){
            val endIndex = showMoreIndex + "...Show More".length
            append(finalText.substring(0,showLessIndex))
            withStyle(style = SpanStyle(
                color = primary,
                fontWeight = FontWeight.SemiBold
            )){
                append(finalText.substring(showMoreIndex,endIndex))
            }
            append(finalText.substring(endIndex))
        }else if(showLessIndex != -1){
            val endIndex = showLessIndex + "Show Less".length
            append(finalText.substring(0,endIndex))
            withStyle(style = SpanStyle(
               color = primary,
                fontWeight = FontWeight.SemiBold
            )
            ){
                append(finalText.substring(showLessIndex,endIndex))
            }
            append(finalText.substring(endIndex))
        }else{
            append(finalText)
        }

    }
    Text(
        text = annotatedString,
        modifier = modifier.fillMaxWidth()
            .clickable(enabled = isClickable){
                isExpanded = !isExpanded
            }
            .animateContentSize()
        ,
        color = MaterialTheme.colorScheme.primary,
        fontSize = fontSize,
        maxLines = if(isExpanded) Int.MAX_VALUE else minimized_maxLines,
        onTextLayout = {textLayoutResultState.value = it},
    )
}