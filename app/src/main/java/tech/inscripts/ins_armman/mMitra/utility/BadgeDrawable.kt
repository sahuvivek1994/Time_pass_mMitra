package tech.inscripts.ins_armman.mMitra.utility

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log
import tech.inscripts.ins_armman.mMitra.R


class BadgeDrawable(context: Context) : Drawable() {

    private val mTextSize: Float
    private val mBadgePaint: Paint
    private val mTextPaint: Paint
    private val mTxtRect = Rect()

    private var mCount = ""
    private var mWillDraw = false

    init {
        mTextSize = context.resources.getDimension(R.dimen.badge_text_size)

        mBadgePaint = Paint()
        mBadgePaint.color = Color.RED
        mBadgePaint.isAntiAlias = true
        mBadgePaint.style = Paint.Style.FILL

        mTextPaint = Paint()
        mTextPaint.color = Color.WHITE
        mTextPaint.typeface = Typeface.DEFAULT_BOLD
        mTextPaint.textSize = mTextSize
        mTextPaint.isAntiAlias = true
        mTextPaint.textAlign = Paint.Align.CENTER
    }

    override fun draw(canvas: Canvas) {
        if (!mWillDraw) {
            return
        }

        val bounds = bounds
        val width = 45.5.toFloat()
        val height = 45.5.toFloat()


        val radius = (Math.min(width, height) / 2 - 1) / 2
        val centerX = 25.5.toFloat()
        val centerY = 6.5.toFloat()

        // Draw badge circle.
        canvas.drawCircle(centerX, centerY, radius, mBadgePaint)

        // Draw badge textViewCount text inside the circle.
        mTextPaint.getTextBounds(mCount, 0, mCount.length, mTxtRect)
        val textHeight = (mTxtRect.bottom - mTxtRect.top).toFloat()
        val textY = centerY + textHeight / 2f
        canvas.drawText(mCount, 25f, 11.2f, mTextPaint)

        val x = centerX - 0.0003f

        Log.d("FRO", "textHeight : $textHeight centerX : $x centerY : $textY")

    }

    /*
    Sets the textViewCount (i.e notifications) to display.
     */
    fun setCount(count: Int) {
        mCount = Integer.toString(count)

        // Only draw a badge if there are notifications.
        mWillDraw = count > 0
        invalidateSelf()
    }

    override fun setAlpha(alpha: Int) {
    }

    override fun setColorFilter(cf: ColorFilter?) {
    }

    override fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }
}