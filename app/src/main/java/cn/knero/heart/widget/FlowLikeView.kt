package cn.knero.heart.widget

import android.animation.*
import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import cn.knero.heart.R
import cn.knero.heart.utils.CurveEvaluator
import java.util.*


/**
 * @author pinned
 * @since 2019-05-15
 */
abstract class FlowLikeView : RelativeLayout {

    private var viewWidth = 0
    private var viewHeight = 0
    private lateinit var childView: View

    protected val random: Random by lazy {
        Random()
    }

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


    open fun init(context: Context, attributeSet: AttributeSet?) {
    }

    fun addLikeView() {
        val likeView = createLikeView()
        val layoutParams = LayoutParams(getLikeViewWidth(), getLikeViewHeight())

        addView(likeView, layoutParams)
        likeView.x = getLikeViewLocationX()
        likeView.y = getLikeViewLocationY()
        startAnimation(likeView)
    }

    private fun getLikeViewLocationX() = childView.x + (childView.width - getLikeViewWidth()) / 2
    private fun getLikeViewLocationY() = childView.y + (childView.height - getLikeViewHeight()) / 2

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)
        childView = getChildAt(0)
        childView.id = R.id.flow_like_view_anchor
    }

    private fun startAnimation(target: View) {
        val enterAnimator = generateEnterAnimator(target)
        // 设置路径动画
        val curveAnimator = generateCurveAnimation(target)

        // 设置动画集合, 先执行进入动画,最后再执行运动曲线动画
        val finalAnimatorSet = AnimatorSet()
        finalAnimatorSet.setTarget(target)
        finalAnimatorSet.playSequentially(enterAnimator, curveAnimator)
        finalAnimatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                removeView(target)
            }
        })
        finalAnimatorSet.start()
    }

    private fun generateEnterAnimator(target: View): AnimatorSet {
        val alpha = ObjectAnimator.ofFloat(target, "alpha", 0.2f, 1f)
        val scaleX = ObjectAnimator.ofFloat(target, "scaleX", 0.5f, 1f)
        val scaleY = ObjectAnimator.ofFloat(target, "scaleY", 0.5f, 1f)
        val enterAnimation = AnimatorSet()
        enterAnimation.playTogether(alpha, scaleX, scaleY)
        enterAnimation.duration = 300
        enterAnimation.setTarget(target)
        return enterAnimation
    }

    private fun generateCurveAnimation(target: View): ValueAnimator {
        val evaluator = createCurveEvaluator(target)
        val valueAnimator = ValueAnimator.ofObject(
            evaluator,
            PointF(getLikeViewLocationX(), getLikeViewLocationY()),
            PointF(getLikeViewLocationX() + (if (random.nextBoolean()) 1 else -1) * random.nextInt(100), 0f)
        )
        valueAnimator.duration = 3000L
        valueAnimator.addUpdateListener {
            val point = it.animatedValue as PointF
            target.x = point.x
            target.y = point.y
        }
        valueAnimator.setTarget(target)
        return valueAnimator
    }

    protected open fun createCurveEvaluator(target: View): TypeEvaluator<PointF> {
        return CurveEvaluator(
            generateCTRLPointF(1),
            generateCTRLPointF(2)
        )
    }

    private fun generateCTRLPointF(value: Int): PointF {
        val pointF = PointF()
        val locationX = getLikeViewLocationX()
        if (random.nextBoolean()) {
            val maxSize = (viewWidth - locationX - getLikeViewWidth()).toInt()
            pointF.x = locationX + random.nextInt(maxSize).toFloat()
        } else {
            val maxSize = locationX.toInt()
            pointF.x = locationX - random.nextInt(maxSize).toFloat()
        }
        pointF.y = random.nextInt(viewHeight / value).toFloat()

        return pointF
    }

    abstract fun createLikeView(): View
    abstract fun getLikeViewWidth(): Int
    abstract fun getLikeViewHeight(): Int

}