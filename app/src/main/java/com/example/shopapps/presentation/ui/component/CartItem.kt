package com.example.shopapps.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil3.compose.AsyncImage

@Composable
fun CartItem(
    modifier: Modifier = Modifier,
    imageId: String,
    productName: String,
    category: String,
    price: String,
    orderCount: Int,
    checkedValue: Boolean = false,
    quantityChange: (Int) -> Unit,
    onCheckChange: (Boolean) -> Unit
) {
    var checked by remember { mutableStateOf(checkedValue) }
    var quantity by remember { mutableIntStateOf(orderCount) }
    val totalPrice = price.toDouble() * quantity

    LaunchedEffect(checkedValue) {
        checked = checkedValue
    }

    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .height(120.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckChange,
                modifier = modifier,
                colors = CheckboxDefaults.colors(
                    if (checked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
                ),
            )
        }
        Spacer(Modifier.size(8.dp))
        AsyncImage(
            modifier = Modifier
                .size(90.dp)
                .align(Alignment.CenterVertically),
            model = imageId,
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(productName, fontSize = 14.sp, fontWeight = FontWeight.Medium, maxLines = 2)
            Text(
                text = category,
                fontSize = 12.sp,
                maxLines = 1,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(
                text = "$${"%.2f".format(totalPrice)}",
                fontWeight = FontWeight.Medium
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .size(width = 110.dp, height = 40.dp)
                .padding(4.dp)
                .border(
                    border = BorderStroke(
                        1.dp,
                        color = Color.LightGray
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Text(
                "-",
                fontSize = 22.sp,
                color = Color.LightGray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        quantity -= 1
                        quantityChange(quantity)
                        if (quantity == 0)
                            quantityChange(0)

                        if (checked) onCheckChange(checked)
                    })

            Text(
                orderCount.toString(),
                Modifier.weight(1f),
                fontSize = 23.sp,
                textAlign = TextAlign.Center
            )
            Text(
                "+",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable {
                    quantity += 1
                    quantityChange(quantity)
                    if (checked) onCheckChange(checked)

                }.weight(1f))
        }

    }
}

@Composable
fun CartItemMini(
    modifier: Modifier = Modifier,
    productName: String,
    image: String,
    price: String,
    orderCount: Int,
    totalOrder: Int,
    onDetailOrder: () -> Unit,
) {
    val totalPrice = price.toDouble() * orderCount

    Row(
        modifier = modifier
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp
            )
            .height(80.dp)
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.background
            )
    ) {
        AsyncImage(
            modifier = Modifier
                .size(60.dp),
            model = image,
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = productName,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2
            )
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Row {
                Text(
                    text = "$orderCount x $${"%.2f".format(totalPrice)}",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                if (totalOrder > 1) {
                    Text(
                        text = "and ${totalOrder - 1} more",
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { onDetailOrder() }
                    )
                }
            }
        }
    }
}