package com.syarah.core

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

/**
 * Project: Syarah Assignment
 * Created: Jun 18, 2021
 *
 * @author Mohamed Hamdan
 */
fun Uri.save(context: Context): File {
    val inputStream = context.contentResolver.openInputStream(this)!!
    val imageFile = File(context.externalCacheDir, "Image_${System.currentTimeMillis()}.jpg")
    inputStream.use { input ->
        val outputStream = FileOutputStream(imageFile)
        outputStream.use { output ->
            val buffer = ByteArray(4 * 1024)
            while (true) {
                val byteCount = input.read(buffer)
                if (byteCount < 0) break
                output.write(buffer, 0, byteCount)
            }
            output.flush()
        }
    }
    return imageFile
}

fun Bitmap.save(context: Context): File {
    val file = File(context.externalCacheDir, "Image_${System.currentTimeMillis()}.jpg")
    val out = FileOutputStream(file)
    compress(Bitmap.CompressFormat.PNG, 100, out)
    return file
}
