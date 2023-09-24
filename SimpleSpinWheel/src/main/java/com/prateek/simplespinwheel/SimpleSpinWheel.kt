package com.prateek.simplespinwheel

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

interface OnSpinCompleteListener {
    fun onSpinComplete(selectedItem: String)
}

class SimpleSpinWheel(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var items: Array<String> = arrayOf(
        "Item One", "Item Two", "Item Three",
        "Item Three", "Item Four", "Item Five",
        "Item Six", "Item Seven", "Item Eight", "Item Nine", "Item Ten"
    )

    private var sectionColors : Array<Int> = arrayOf(
        android.R.color.holo_blue_light,
        android.R.color.holo_orange_light,
        android.R.color.holo_green_light,
        android.R.color.holo_red_light,
    )


    private var strokeWidth : Float = 2f
    private var textSize : Float = 30f
    private var strokeColor : Int = android.R.color.black
    private var selectedItemIndex = -1
    private var rotation = 0f
    private var arrowAngle = 20f // more-->upper
    private var clickListener: OnClickListener? = null
    private var completeListener: OnSpinCompleteListener? = null
    private var selectorAbsRotation : Float = 20f


    fun setOnSpinCompleteListener(spinCompleteListener: OnSpinCompleteListener) {
        this.completeListener = spinCompleteListener
    }

    fun setWheelClickListener(onClickListener: OnClickListener) {
        clickListener = onClickListener
    }

    fun setSelectorAbsRotation(rotation: Float){
        this.selectorAbsRotation = rotation
    }


    fun setItems(items: Array<String>) {
        this.items = items
    }

    fun setColors(colors : Array<Int>){
        this.sectionColors = colors
    }

    fun setSeparatorStrokeWidth(strokeWidth: Float){
        this.strokeWidth = strokeWidth
    }

    fun setTextSize(sectionTextSize : Float){
        this.textSize = sectionTextSize
    }

    fun setStrokeColor(strokeColorResource : Int){
        this.strokeColor = strokeColorResource
    }


    private fun calculateSelectedItem(): String {

        val itemCount = items.size

        val anglePerItem = 360.0f / itemCount

        val normalizedRotation = (rotation + 360) % 360

        when(itemCount){
            2 -> arrowAngle = 179f //ver
            3 -> arrowAngle = 110f //ver
            4 -> arrowAngle = 82f //ver
            5 -> arrowAngle = 69f //ver
            6 -> arrowAngle = 55f //ver
            7 -> arrowAngle = 50f //ver
            8 -> arrowAngle = 45f //ver
            9 -> arrowAngle = 40f //vere
            10 -> arrowAngle = 38f //vere
            11 -> arrowAngle = 36f //vere
            12 -> arrowAngle = 35f //vere
            13 -> arrowAngle = 30f //vere
            14 -> arrowAngle = 25f //ver
            15 -> arrowAngle = 20f //ver
        }

        if(itemCount>15){
            arrowAngle = 20f //ver
        }

        val arrowRelativeAngle = (normalizedRotation + arrowAngle) % 360

        val selectedItemIndex = ((itemCount - (arrowRelativeAngle / anglePerItem).toInt()) + itemCount) % itemCount

        return if (selectedItemIndex in 0 until itemCount) {
            items[selectedItemIndex]
        } else {
            "No selection"
        }
    }






    fun spin() {

        val targetRotation = rotation + 500 + (arrowAngle / 2) - (360.0f / items.size) / 2

        val duration = ((targetRotation / 360.0f) * 500).toLong()

        selectedItemIndex = ((rotation % 360) / (360.0f / (items.size))).toInt()

        val animator = ObjectAnimator.ofFloat(this, "rotation", rotation, targetRotation)
        animator.duration = duration
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()

        animator.addUpdateListener { animation ->
            rotation = animation.animatedValue as Float
            invalidate()
        }

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                completeListener?.onSpinComplete(calculateSelectedItem())
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width
        val height = height
        val radius = min(width, height) / 2

        val wheelPaint = Paint()
        wheelPaint.style = Paint.Style.FILL
        wheelPaint.isAntiAlias = true

        val wheelBounds = RectF(
            (width / 2 - radius).toFloat(),
            (height / 2 - radius).toFloat(),
            (width / 2 + radius).toFloat(),
            (height / 2 + radius).toFloat()
        )

        val itemCount = items.size
        val angle = 360.0f / itemCount

        items.forEachIndexed { index, itemText ->
            wheelPaint.color = ContextCompat.getColor(context, sectionColors[index % sectionColors.size])
            canvas.drawArc(wheelBounds, index * angle, angle, true, wheelPaint)
            wheelPaint.color = ContextCompat.getColor(context, android.R.color.white)

            wheelPaint.textSize = textSize

            val textWidth = wheelPaint.measureText(itemText)


            if(itemCount == 2){
                val angleRad = Math.toRadians((index * angle + angle / 2).toDouble())
                val textX = (width / 2 + (radius * 0.7) * cos(angleRad) - textWidth / 2).toFloat()
                val textY = (height / 2 + (radius * 0.7) * sin(angleRad) + textSize / 2).toFloat()
                canvas.drawText(itemText, textX, textY, wheelPaint)

            }else{
                val textRotation = if (angle < 180) {
                    (index * angle + angle / 2)
                } else {
                    (index * angle + angle / 2 + 180)
                }
                val textX = (width / 2 + (radius * 0.7) * cos(Math.toRadians(textRotation.toDouble())) - textWidth / 2).toFloat()
                val textY = (height / 2 + (radius * 0.7) * sin(Math.toRadians(textRotation.toDouble())) + textSize / 2).toFloat()

                canvas.save()
                canvas.rotate(textRotation, textX + textWidth / 2, textY)
                canvas.drawText(itemText, textX, textY, wheelPaint)


                canvas.restore()
            }

        }


        //seperator
        wheelPaint.color = ContextCompat.getColor(context, strokeColor)
        wheelPaint.strokeWidth = strokeWidth

        items.forEachIndexed { index, _ ->
            val startX =
                (width / 2 + (radius - 20) * cos(Math.toRadians((index * angle).toDouble()))).toFloat()
            val startY =
                (height / 2 + (radius - 20) * sin(Math.toRadians((index * angle).toDouble()))).toFloat()
            val endX =
                (width / 2 + radius * cos(Math.toRadians((index * angle).toDouble()))).toFloat()
            val endY =
                (height / 2 + radius * sin(Math.toRadians((index * angle).toDouble()))).toFloat()

            canvas.drawLine(startX, startY, endX, endY, wheelPaint)
        }
    }



    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.AXIS_TOUCH_MINOR -> {
                performClick()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        super.performClick()
        clickListener?.onClick(this)
        return true
    }

}
