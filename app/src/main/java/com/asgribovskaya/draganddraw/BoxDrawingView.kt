package com.asgribovskaya.draganddraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class BoxDrawingView(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {

    private var currentBox: Box? = null
    private val boxes = mutableListOf<Box>()
    private val boxPaint = Paint().apply {
        color = resources.getColor(R.color.green, null)
    }
    private val backgroundPaint = Paint().apply {
        color = resources.getColor(R.color.white, null)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPaint(backgroundPaint)
        boxes.forEach {
            canvas.drawRect(it.left, it.top, it.right, it.bottom, boxPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val currentPoint = PointF(event.x, event.y)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                currentBox = Box(currentPoint).also {
                    boxes.add(it)
                }
            }

            MotionEvent.ACTION_MOVE -> {
                updateCurrentBox(currentPoint)
            }

            MotionEvent.ACTION_UP -> {
                updateCurrentBox(currentPoint)
                currentBox = null
            }

            MotionEvent.ACTION_CANCEL -> {
                currentBox = null
            }
        }
        return true
    }

    private fun updateCurrentBox(current: PointF) {
        currentBox?.let {
            it.end = current
            invalidate()
        }
    }
}