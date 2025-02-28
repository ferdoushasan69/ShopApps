package com.example.shopapps.presentation.model

import androidx.annotation.DrawableRes
import com.example.shopapps.R

data class HorizontalPagerContent(
    val title : String,
    @DrawableRes val img :Int,
    val desc : String
)

fun getList():List<HorizontalPagerContent>{
    return listOf(
        HorizontalPagerContent(
            title = "Discover the Best Deals!",
            img = R.drawable.onboarding1,
            desc = "Find the latest trends, exclusive discounts, and top-quality products—all in one place. Start shopping with ease!"
        ),
        HorizontalPagerContent(
            title = "Discover the Best Deals!",
            img = R.drawable.onboarding2,
            desc = "Enjoy a seamless shopping experience with fast checkout, secure payments, and doorstep delivery."
        ),
        HorizontalPagerContent(
            title = "Get Personalized Offers",
            img = R.drawable.image_slider_four,
            desc = "Receive exclusive deals and recommendations tailored just for you. Never miss out on the best savings!"
        ),
    )
}