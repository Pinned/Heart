package cn.knero.heart.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import cn.knero.heart.R


/**
 * @author pinned
 * @since 2019-05-15
 */
class BeatHeartView : HeartView {

    private val DEFAULT_SCALE_FACTOR = 0.2f
    private val DEFAULT_DURATION = 50

    private var scaleFactor = DEFAULT_SCALE_FACTOR
    private var reductionScaleFactor = -scaleFactor
    private var animationDuration = DEFAULT_DURATION

    private var heartBeating = false

    private val scaleUpListener: Animator.AnimatorListener = object : AnimatorListenerAdapter() {

        override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            startAnimation(reductionScaleFactor, animationDuration.toLong(), scaleDownListener)
        }
    }

    private val scaleDownListener: Animator.AnimatorListener = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            super.onAnimationEnd(animation)
            if (heartBeating) {
                val longTime = animationDuration.toLong() * 2
                startAnimation(scaleFactor, longTime, scaleUpListener)
            }
        }
    }


    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun onInit(typedArray: TypedArray) {
        super.onInit(typedArray)
        scaleFactor = typedArray.getFloat(R.styleable.HeartBeatView_scaleFactor, DEFAULT_SCALE_FACTOR)
        reductionScaleFactor = -scaleFactor
        animationDuration = typedArray.getInteger(R.styleable.HeartBeatView_duration, DEFAULT_DURATION)
    }

    override fun getStyleableResId(): IntArray? {
        return R.styleable.HeartBeatView
    }

    fun toggle() {
        if (heartBeating) {
            stop()
        } else {
            start()
        }
    }

    fun start() {
        heartBeating = true
        startAnimation(scaleFactor, animationDuration.toLong(), scaleUpListener)
    }


    fun startAnimation(scaleFactor: Float, animationDuration: Long, listener: Animator.AnimatorListener) {
        with(animate()) {
            scaleXBy(scaleFactor)
            scaleYBy(scaleFactor)
            duration = animationDuration
            setListener(listener)
        }
    }

    fun stop() {
        heartBeating = false
        clearAnimation()
    }

}