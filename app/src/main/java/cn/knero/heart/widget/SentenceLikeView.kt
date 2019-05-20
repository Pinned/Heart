package cn.knero.heart.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import cn.knero.heart.R


/**
 * @author pinned
 * @since 2019-05-15
 */
class SentenceLikeView : FlowLikeView {

    private val SENTENCE = "我最亲爱的宝宝，师哥爱你哟！！"
    private var index = 0

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun createLikeView(): View {
        val likeView = FrameLayout(context)
        val beatView = BeatHeartView(context)
        beatView.setHeartColor(
            Color.rgb(
                random.nextInt(255),
                random.nextInt(255), random.nextInt(255)
            )
        )
        val beatViewLayoutParams = FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        likeView.addView(beatView, beatViewLayoutParams)
        val showContentView = TextView(context)
        showContentView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f)
        showContentView.setTextColor(Color.WHITE)
        showContentView.text = getShowContent()
        val layoutParams = FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        showContentView.layoutParams = layoutParams
        showContentView.gravity = Gravity.CENTER
        likeView.addView(showContentView, layoutParams)
        return likeView
    }

    private fun getShowContent(): String {
        return if (random.nextInt(10) < 4) {
            index %= SENTENCE.length
            val result = SENTENCE[index].toString()
            index++
            result
        } else ""
    }

    override fun getLikeViewWidth(): Int {
        return context.resources.getDimensionPixelSize(R.dimen.heart_size_width)
    }

    override fun getLikeViewHeight(): Int {
        return context.resources.getDimensionPixelSize(R.dimen.heart_size_height)
    }
}