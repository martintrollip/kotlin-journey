package com.example.android.minipaint

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat

//Canvas is a 2D drawing surface with methods for drawing
//Canvas can be associated with a view for display
//Paint is for style and colour when drawing geometry
//Custom views should override onDraw and onSizeChanged methods
//onTouchEvents responds to user touch
//Use extraBitmap to chache drawings over time (or cache the path drawables)


/**
 * @author Martin Trollip ***REMOVED***
 * @since 2020/02/24 19:09
 */

private const val STROKE_WIDTH = 12f // has to be float

class MyCanvasView(context: Context) : View(context) {

    //Cache what has been drawn before
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap

    private lateinit var frame: Rect

    private val backgroundColor = ResourcesCompat.getColor(resources, R.color.colorBackground, null)
    private val drawColor = ResourcesCompat.getColor(resources, R.color.colorPaint, null)

    // Set up the paint with which to draw.
    private val paint = Paint().apply {
        color = drawColor
        isAntiAlias = true// Smooths out edges of what is drawn without affecting shape.
        isDither =
            true // Dithering affects how colors with higher-precision than the device are down-sampled.
        style = Paint.Style.STROKE // default: FILL
        strokeJoin = Paint.Join.ROUND // default: MITER
        strokeCap = Paint.Cap.ROUND // default: BUTT
        strokeWidth = STROKE_WIDTH // default: Hairline-width (really thin)
    }

    //Store the path that is being drawn when the user touches the screen
    private var path = Path()

    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop //Interpolate a path between two points.  If only minute movement, dont do redraw


    //Whenever a view change size, including the 1st time
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        //Recycle the old bitmap to prevent a memory leak
        if (::extraBitmap.isInitialized) extraBitmap.recycle()

        //New Bitmap and Canvas is created each time this method is executed
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(backgroundColor)

    }

    override fun onDraw(canvas: Canvas) { //This is a different canvas that the one defined onSizeChanged
        super.onDraw(canvas)
        canvas.drawBitmap(extraBitmap, 0f, 0f, null) //Draw cached bitmap

        // Calculate a rectangular frame around the picture.
        val inset = 40
        frame = Rect(inset, inset, width - inset, height - inset)

        // Draw a frame around the canvas.
        canvas.drawRect(frame, paint)
    }

    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f

    //Whenever a user touch the View
    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }
        return true
    }

    private var currentX = 0f
    private var currentY = 0f

    //When user touches for the 1st time
    private fun touchStart() {
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }

    private fun touchMove() {
        val dx = Math.abs(motionTouchEventX - currentX)
        val dy = Math.abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            // QuadTo() adds a quadratic bezier from the last point,  (smooth line without corners)
            // approaching control point (x1,y1), and ending at (x2,y2).
            path.quadTo(currentX, currentY, (motionTouchEventX + currentX) / 2, (motionTouchEventY + currentY) / 2)
            currentX = motionTouchEventX
            currentY = motionTouchEventY
            // Draw the path in the extra bitmap to cache it.
            extraCanvas.drawPath(path, paint)
        }
        invalidate() //Force redraw of the screen with the path
    }

    //User lifts their finder
    private fun touchUp() {
        path.reset()
        //Nothing drawn so no need for invalidate
    }
}