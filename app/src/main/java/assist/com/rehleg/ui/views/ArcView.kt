package com.assist.lego.testing.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import assist.com.rehleg.R
import assist.com.rehleg.ui.utils.Utils

/**
 * Created by Sergiu on 27.10.2017.
 */
class ArcView : View {
    private val oval = RectF()
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var bottom: Float = 0f
        set(value) {
            field = Utils.dpToPx(context, value)
        }

    constructor(context: Context) : this(context, null) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        paint.color = ContextCompat.getColor(context, R.color.arcColor)
        paint.strokeWidth = 5f
        paint.style = Paint.Style.STROKE

        shadowPaint.color = ContextCompat.getColor(context, R.color.shadowColor)
        shadowPaint.strokeWidth = 8f
        shadowPaint.style = Paint.Style.STROKE
        shadowPaint.alpha = 30
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        oval.set(-16f, 0f, width.toFloat() + 16, bottom)
        canvas?.drawArc(oval, 0f, 180f, false, paint)

        oval.set(-16f, 16f, width.toFloat() + 16, bottom + 16)
        canvas?.drawArc(oval, 0f, 180f, false, shadowPaint)
    }
}
