package cn.knero.heart.utils

import android.graphics.Color


fun manipulateColor(color: Int): Int {
    val a: Int = Color.alpha(color)
    val r: Int = Math.round(Color.red(color) * 0.9f)
    val g: Int = Math.round(Color.green(color) * 0.66f)
    val b: Int = Math.round(Color.blue(color) * 0.66f)
    return Color.argb(
        a,
        Math.min(r, 255),
        Math.min(g, 255),
        Math.min(b, 255)
    )
}

fun darkColor(color: Int, redFactor: Float, greenFactor: Float, blueFactor: Float): Int {
    val alpha = Color.alpha(color)
    val red = Math.round(Color.red(color) * redFactor)
    val green = Math.round(Color.green(color) * greenFactor)
    val blue = Math.round(Color.blue(color) * blueFactor)
    return Color.argb(alpha, Math.min(red, 255), Math.min(green, 255), Math.min(blue, 255))
}