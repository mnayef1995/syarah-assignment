package com.syarah.core

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

/**
 * Project: Syarah Assignment
 * Created: Jun 18, 2021
 *
 * @author Mohamed Hamdan
 */
fun View.screenshot(): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    draw(canvas)
    return bitmap
}
