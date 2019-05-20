package cn.knero.heart.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import cn.knero.heart.R
import cn.knero.heart.utils.darkColor


/**
 * @author pinned
 * @since 2019-05-14
 */
open class HeartView : BaseView {
    private val paint = Paint()
    private val DEFAULT_HEART_COLOR = Color.argb(255, 244, 68, 68)

    // 两个颜色
    private var heartColor = DEFAULT_HEART_COLOR
    private var heartDarkColor = darkColor()

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun onInit(typedArray: TypedArray) {
        super.onInit(typedArray)
        heartColor = typedArray.getColor(R.styleable.HeartView_heartColor, DEFAULT_HEART_COLOR)
        setHeartColor(heartColor)
    }

    override fun getStyleableResId(): IntArray? {
        return R.styleable.HeartView
    }

    override fun getOriginalRectF() = RectF(0f, 0f, 200f, 164f)


    fun setHeartColor(color: Int) {
        heartColor = color
        heartDarkColor = darkColor()
    }


    private fun darkColor(): Int {
        return darkColor(heartColor, 0.9f, 0.66f, 0.66f)
    }

    override fun onRealDraw(canvas: Canvas, scale: Float) {
        canvas.save()

        val bezierPath = Path()
        bezierPath.reset()
        bezierPath.moveTo(37.38f * scale, 125.6f * scale)
        bezierPath.cubicTo(52.58f * scale, 140f * scale, 69.38f * scale, 151.4f * scale, 82.38f * scale, 158f * scale)
        bezierPath.cubicTo(90.18f * scale, 162f * scale, 96.38f * scale, 164f * scale, 100.18f * scale, 164f * scale)
        bezierPath.cubicTo(
            109.78f * scale,
            164f * scale,
            138.58f * scale,
            149f * scale,
            162.78f * scale,
            125.8f * scale
        )
        bezierPath.cubicTo(
            178.78f * scale,
            110.4f * scale,
            192.98f * scale,
            91.4f * scale,
            197.98f * scale,
            71f * scale
        )
        bezierPath.cubicTo(
            202.78f * scale,
            51.4f * scale,
            198.38f * scale,
            30.6f * scale,
            183.78f * scale,
            16.2f * scale
        )
        bezierPath.cubicTo(173.78f * scale, 6.2f * scale, 159.98f * scale, 0.2f * scale, 144.78f * scale, 0.2f * scale)
        bezierPath.cubicTo(126.18f * scale, 0.2f * scale, 109.98f * scale, 9.2f * scale, 99.98f * scale, 23f * scale)
        bezierPath.cubicTo(93.18f * scale, 13.6f * scale, 83.38f * scale, 6.4f * scale, 71.98f * scale, 2.6f * scale)
        bezierPath.cubicTo(52.18f * scale, -3.6f * scale, 30.38f * scale, 1.6f * scale, 15.98f * scale, 16f * scale)
        bezierPath.cubicTo(1.38f * scale, 30.4f * scale, -3.02f * scale, 51.2f * scale, 1.98f * scale, 70.8f * scale)
        bezierPath.cubicTo(6.98f * scale, 91f * scale, 21.18f * scale, 110f * scale, 37.38f * scale, 125.6f * scale)
        bezierPath.close()

        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.style = Paint.Style.FILL
        paint.color = heartColor
        canvas.drawPath(bezierPath, paint)

        // Bezier 2
        val bezier2Path = Path()
        bezier2Path.reset()
        bezier2Path.moveTo(199.98f * scale, 54.8f * scale)
        bezier2Path.cubicTo(199.98f * scale, 46.6f * scale, 198.18f * scale, 39f * scale, 194.78f * scale, 32f * scale)
        bezier2Path.cubicTo(
            177.38f * scale,
            89.6f * scale,
            100.38f * scale,
            136.2f * scale,
            81.98f * scale,
            136.2f * scale
        )
        bezier2Path.cubicTo(
            71.18f * scale,
            136.2f * scale,
            39.58f * scale,
            119.8f * scale,
            11.98f * scale,
            94.4f * scale
        )
        bezier2Path.cubicTo(35.78f * scale, 134.2f * scale, 86.58f * scale, 164f * scale, 99.98f * scale, 164f * scale)
        bezier2Path.cubicTo(
            117.78f * scale,
            164f * scale,
            199.98f * scale,
            113f * scale,
            199.98f * scale,
            54.8f * scale
        )
        bezier2Path.close()

        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.style = Paint.Style.FILL
        paint.color = heartDarkColor
        canvas.drawPath(bezier2Path, paint)

        canvas.restore()
    }
}