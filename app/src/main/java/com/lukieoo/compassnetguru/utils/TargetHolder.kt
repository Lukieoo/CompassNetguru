package com.lukieoo.compassnetguru.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import com.lukieoo.compassnetguru.R


class TargetHolder constructor(
    var mathematicalOperations: MathematicalOperations,
    var context: Context
) {

    private var myBitmap: Bitmap
    private var myBitmapArrow: Bitmap
    private var tempCanvas: Canvas
    private var paint: Paint
    private var tempBitmap: Bitmap

    init {
        myBitmap = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.img_compass
        )
        myBitmapArrow = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.img_arrow
        )

        myBitmapArrow = Bitmap.createScaledBitmap(myBitmapArrow, 300, 300, true)

        paint = Paint()
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 100f

        tempBitmap =
            Bitmap.createBitmap(
                myBitmap.width,
                myBitmap.height,
                Bitmap.Config.ARGB_8888
            )

        tempCanvas = Canvas(tempBitmap)

        tempCanvas.drawBitmap(myBitmap, 0f, 0f, paint)
    }

    fun setTarget(x1: Double, y1: Double, x2: Double, y2: Double): BitmapDrawable {

        var angle = mathematicalOperations.azimuth(
            x1,
            y1,
            x2,
            y2

        )

        var x: Int =
            (myBitmap.width / 2f + Math.cos(Math.toRadians(angle - 90)) * (myBitmap.width - 950) / 2).toInt()
        var y: Int =
            (myBitmap.width / 2f + Math.sin(Math.toRadians(angle - 90)) * (myBitmap.width - 950) / 2).toInt()

//        tempCanvas.drawCircle(x.toFloat(), y.toFloat(), 150f, paint)
        myBitmapArrow = rotateBitmap(myBitmapArrow, angle = angle.toFloat())
        tempCanvas.drawBitmap(myBitmapArrow, x.toFloat(), y.toFloat(), paint)

        return BitmapDrawable(context.resources, tempBitmap)

    }

    fun clear() {
        tempBitmap =
            Bitmap.createBitmap(
                myBitmap.width,
                myBitmap.height,
                Bitmap.Config.ARGB_8888
            )
        myBitmapArrow = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.img_arrow
        )

        myBitmapArrow = Bitmap.createScaledBitmap(myBitmapArrow, 200, 200, true)

        tempCanvas = Canvas(tempBitmap)

        tempCanvas.drawBitmap(myBitmap, 0f, 0f, paint)
    }

    private fun rotateBitmap(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source,
            0,
            0,
            source.width,
            source.height,
            matrix,
            true
        )
    }
}