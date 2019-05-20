package cn.knero.heart.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * @author pinned
 * @since 2019-05-14
 */
abstract class BaseView : View {
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        init(context, attributeSet)
    }

    private fun init(context: Context, attributeSet: AttributeSet?) {
        val styleableResId = getStyleableResId()
        if (styleableResId != null) {
            val typeArrayed = context.obtainStyledAttributes(attributeSet, styleableResId)
            onInit(typeArrayed)
            typeArrayed.recycle()
        }
    }

    protected open fun getStyleableResId(): IntArray? {
        return null
    }

    protected open fun onInit(typedArray: TypedArray) {}

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val original = getOriginalRectF()
        val result = resize(original, RectF(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat()))
        canvas.save()
        canvas.translate(result.second.left, result.second.top)
        onRealDraw(canvas, result.first)
        canvas.restore()
    }

    private fun resize(original: RectF, target: RectF): Pair<Float, RectF> {
        val xRatio = Math.abs(target.width() / (original.width() + getBorderSize()))
        val yRatio = Math.abs(target.height() / (original.height() + getBorderSize()))
        val scale = Math.min(xRatio, yRatio)
        val newWidth = Math.abs(original.width() * scale)
        val newHeight = Math.abs(original.height() * scale)
        val result = RectF()
        result.left = target.centerX() - newWidth / 2
        result.top = target.centerY() - newHeight / 2
        result.right = target.centerX() + newWidth / 2
        result.bottom = target.centerY() + newHeight / 2
        return Pair(scale, result)
    }

    protected open fun getBorderSize(): Int {
        return 0
    }

    protected abstract fun getOriginalRectF(): RectF
    protected abstract fun onRealDraw(canvas: Canvas, scale: Float)
}