package cn.knero.heart.utils

import android.animation.TypeEvaluator
import android.graphics.PointF


/**
 * @author pinned
 * @since 2019-05-15
 */
class CurveEvaluator(val point1: PointF, val point2: PointF) : TypeEvaluator<PointF> {
    override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
        val leftTime = 1.0f - fraction
        val resultPointF = PointF()

        // 三阶贝塞儿曲线
        resultPointF.x = (Math.pow(leftTime.toDouble(), 3.0).toFloat() * startValue.x
                + 3 * Math.pow(leftTime.toDouble(), 2.0).toFloat() * fraction * point1.x
                + 3 * leftTime * Math.pow(fraction.toDouble(), 2.0).toFloat() * point2.x
                + Math.pow(fraction.toDouble(), 3.0).toFloat() * endValue.x)
        resultPointF.y = (Math.pow(leftTime.toDouble(), 3.0).toFloat() * startValue.y
                + 3 * Math.pow(leftTime.toDouble(), 2.0).toFloat() * fraction * point1.y
                + 3 * leftTime * fraction * fraction * point2.y
                + Math.pow(fraction.toDouble(), 3.0).toFloat() * endValue.y)

        return resultPointF
    }
}