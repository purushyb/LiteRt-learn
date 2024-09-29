package com.example.litert_learn.data.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import kotlin.random.Random

object Utils {
    fun drawableToBitmap(context: Context, drawableId: Int): Bitmap? {
        val drawable: Drawable = context.getDrawable(drawableId) ?: return null
        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else {
            Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            ).also {
                val canvas = android.graphics.Canvas(it)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
            }
        }
    }

    val colors = listOf<Int>(
        Color.RED,
        Color.BLUE,
        Color.GREEN,
        Color.YELLOW,
        Color.MAGENTA,
        Color.CYAN,
        Color.GRAY,
        Color.DKGRAY,
        Color.LTGRAY,
        Color.BLACK
    )

    fun drawRectangleOnBitmap(
        bitmap: Bitmap,
        location: RectF,
        color: Int = getRandomColor(colors),
        strokeWidth: Float = 2.0F,
        title: String,
        titleSize: Float = 12.0F
    ): Bitmap {
        val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)
        val paint = Paint().apply {
            style = Paint.Style.STROKE
            this.color = color
            this.strokeWidth = strokeWidth
        }

        canvas.drawRect(location, paint)

        val titlePaint = Paint().apply {
            this.color = color
            textSize = titleSize
            typeface = Typeface.DEFAULT_BOLD
        }
        val titleX = location.left
        val titleY = (location.top - titleSize).coerceAtLeast(0f) // Position above rectangle
        canvas.drawText(title, titleX, titleY, titlePaint)

        return mutableBitmap
    }

    fun getRandomColor(list: List<Int>): Int {
        val randomIndex = Random.nextInt(list.size)
        return list[randomIndex]
    }

    fun bitmapToYuvBytes(bitmap: Bitmap): ByteArray {
        val width = bitmap.width
        val height = bitmap.height
        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        val yuvBytes = ByteArray(width * height * 3 / 2) // Adjust size as needed
        // ... (Perform YUV conversion here, if necessary) ...
        return yuvBytes
    }

    fun bitmapToArgb(bitmap: Bitmap): IntArray {
        val width = bitmap.width
        val height = bitmap.height
        val argbValues = IntArray(width * height)
        bitmap.getPixels(argbValues, 0, width, 0, 0, width, height)
        return argbValues
    }

    fun loadScaledBitmap(imagePath: String, reqWidth: Int, reqHeight: Int): Bitmap? {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true // Only decode the bounds, not the image
        BitmapFactory.decodeFile(imagePath, options)

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false

        return BitmapFactory.decodeFile(imagePath, options)
    }

    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }
}