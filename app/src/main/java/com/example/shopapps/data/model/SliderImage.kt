package com.example.shopapps.data.model

import com.example.shopapps.R


data class SliderImage(
    val image : Int
)



val dummyImage = listOf(
    SliderImage(R.drawable.image_slider_one),
    SliderImage(R.drawable.image_slider_two),
    SliderImage(R.drawable.image_slider_three),
    SliderImage(R.drawable.image_slider_four),
)