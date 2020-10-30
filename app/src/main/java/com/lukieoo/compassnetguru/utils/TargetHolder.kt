package com.lukieoo.compassnetguru.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import com.lukieoo.compassnetguru.R
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class TargetHolder constructor( var mathematicalOperations: MathematicalOperations ,var  context: Context ) {

    private  var myBitmap: Bitmap
    private  var tempCanvas: Canvas
    private  var paint: Paint
    private  var tempBitmap: Bitmap

    init {
        myBitmap = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.img_compass
        )

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
//        tempCanvas.drawCircle(myBitmap.width / 2f, myBitmap.height / 2f, 50f, paint)
    }

    fun setTarget(x1: Double, y1: Double, x2: Double, y2: Double) :BitmapDrawable{

        var angle = mathematicalOperations.azimuth(
            x1,
            y1,
            x2,
            y2

        )

        var x: Int =
            (myBitmap.width / 2f + Math.cos(Math.toRadians(angle - 90)) * (myBitmap.width - 90) / 2).toInt()
        var y: Int =
            (myBitmap.height / 2f + Math.sin(Math.toRadians(angle - 90)) * (myBitmap.height - 90) / 2).toInt()

        tempCanvas.drawCircle(x.toFloat(), y.toFloat(), 150f, paint)

        return BitmapDrawable(context.resources, tempBitmap)

    }
    fun clear( ){
        tempBitmap =
            Bitmap.createBitmap(
                myBitmap.width,
                myBitmap.height,
                Bitmap.Config.ARGB_8888
            )
        tempCanvas = Canvas(tempBitmap)

        tempCanvas.drawBitmap(myBitmap, 0f, 0f, paint)
    }
}