package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.withStyledAttributes
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var widthSize = 0
    private var heightSize = 0
    private var LoadingProgress = 0
    private var buttonText = ""

    private val valueAnimator = ValueAnimator.ofInt(0, 360).setDuration(3000)

    var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->

        if (new == ButtonState.Loading) {
            buttonText = "Loading"
            valueAnimator.start()
        }else{
            buttonText = "Download"
            valueAnimator.cancel()

            LoadingProgress = 0
        }
        invalidate()
    }

    private var buttonBackgroundColor = 0
    private var textColor = 0
    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.LoadingButton,
            0, 0).apply {

            try {
                textColor = ContextCompat.getColor(context, R.color.white)
                buttonBackgroundColor = ContextCompat.getColor(context, R.color.colorPrimary)
            } finally {
                recycle()
            }
        }
        buttonState = ButtonState.Completed
        valueAnimator.apply {
            addUpdateListener {
                LoadingProgress = it.animatedValue as Int
                invalidate()
            }
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
        }

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // button background
        paint.color = buttonBackgroundColor
        canvas?.drawRect(0f,0f,widthSize.toFloat(), heightSize.toFloat(), paint)

        // loading button
        paint.color = Color.DKGRAY
        canvas?.drawRect(0f, 0f, widthSize * LoadingProgress/360f, heightSize.toFloat(), paint)

        // text
        paint.color = textColor
        canvas?.drawText(buttonText, widthSize/2.0f, heightSize/2.0f + 40.0f, paint)

        // circle
        paint.color = Color.YELLOW
        canvas?.drawArc(widthSize - 200f,50f,widthSize - 100f,150f,0f, LoadingProgress.toFloat(), true, paint)


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 80.0f
        typeface = Typeface.create( "", Typeface.BOLD)
    }
}