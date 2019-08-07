//package cn.knero.heart
//
//import android.os.Bundle
//import android.os.Handler
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.os.postDelayed
//
//
///**
// * @author zhaocheng.luo
// * @since 2019-08-07
// */
//class TypeInputActivity : AppCompatActivity() {
//    private val DELAY = 100L
//    val handler = Handler()
//    val buffer = StringBuffer()
//    var printCount = 0
//    var printLine = 0
//    var currentSlogan = ""
//
//    var count = 0
//    var first = true
//
//
//    var mAllInfos = arrayOf("浮世万千", "吾爱有三")
//
//    private val mRunnablePrint = object : Runnable {
//        override fun run() {
//            if (printCount === currentSlogan.length) {
//                printCount = 0
//                handler.removeCallbacks(this)
//                handler.postDelayed(mRunnableNextLine, DELAY)
//            } else {
//                buffer.append(currentSlogan.substring(printCount, printCount + 1))
//                middleInput.text = buffer.toString()
//                printCount += 1
//                handler.postDelayed(this, DELAY)
//            }
//        }
//    }
//
//    private val mRunnableNextLine = object : Runnable {
//        override fun run() {
//            if (count > 3) {
//                first = true
//                count = 0
//                currentSlogan = mAllInfos[printLine]
//                handler.postDelayed(mRunnablePrint, DELAY)
//            } else {
//                if (first) {
//                    first = false
//                    buffer.append("\n")
//                }
//                if (count % 2 == 0) {
//                    buffer.append("|")
//                } else {
//                    buffer.delete(buffer.length - 1, buffer.length)
//                }
//                middleInput.text = buffer.toString()
//                handler.postDelayed(this, DELAY * 4)
//                count++
//            }
//        }
//    }
//
//    lateinit var middleInput: TextView
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_type_input)
//        middleInput = findViewById(R.id.tv_middle)
//        initSlogan()
//    }
//
//    fun initSlogan() {
//        buffer.delete(0, buffer.length)
//        printCount = 0
//        currentSlogan = mAllInfos[0]
//        handler.postDelayed(mRunnablePrint, DELAY)
//    }
//
//
//}