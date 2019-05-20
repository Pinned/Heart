package cn.knero.heart

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import cn.knero.heart.widget.BeatHeartView
import cn.knero.heart.widget.FlowLikeView

class MainActivity : AppCompatActivity() {
    lateinit var flowLikeView: FlowLikeView
    val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var heartBeatView: BeatHeartView = findViewById(R.id.heart_beat_view)
        heartBeatView.toggle()
        flowLikeView = findViewById(R.id.flowLikeView)
        heartBeatView.setOnClickListener {
            flowLikeView.addLikeView()
        }


        handler.postDelayed(addLikeview2, generageDelay())
    }

    private var addLikeview1: Runnable = Runnable {
        flowLikeView.addLikeView()
        handler.postDelayed(addLikeview2, generageDelay())
    }


    private var addLikeview2: Runnable = Runnable {
        flowLikeView.addLikeView()
        handler.postDelayed(addLikeview1, generageDelay())
    }

    private fun generageDelay(): Long {
        return (Math.random() * 800).toLong()
    }
}
