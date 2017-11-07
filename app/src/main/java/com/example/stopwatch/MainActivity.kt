package com.example.stopwatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    //val dataFormat = SimpleDateFormat("mm:ss.SS")
    //var time = dataFormat.format(System.currentTimeMillis())

    // 1度だけ代入するものはvalを使う
    val handler = Handler()
    // 繰り返し代入するためvarを使う
    var startTimeValue = 0L


    // 配置したView要素にアクセス
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //View要素を変数に代入
        val timeText: TextView = findViewById(R.id.timeText)
        val startButton: Button = findViewById(R.id.start)
        val stopButton: Button = findViewById(R.id.stop)
        val resetButton: Button = findViewById(R.id.reset)

        //1ミリ秒ごとに処理を実行
        val runnable = object : Runnable {
            override fun run() {
                //timeValue++

                // TextViewを更新
                // ?.letを用いて、nullではない場合のみ更新
                timeToText(startTimeValue)?.let {
                    // timeToText(timeValue)の値がlet内ではitとして使える
                    timeText.text = it
                }
                handler.postDelayed(this, 1)
            }
        }

        // start
        startButton.setOnClickListener {
            if(startTimeValue == 0L )
                startTimeValue = System.currentTimeMillis()
            handler.post(runnable)
        }

        // stop
        stopButton.setOnClickListener {
            handler.removeCallbacks(runnable)
        }

        // reset
        resetButton.setOnClickListener {
            handler.removeCallbacks(runnable)
            startTimeValue = 0L
            // timeToTextの引数はデフォルト値が設定されているので、引数省略できる
            timeToText()?.let {
                timeText.text = it
            }
        }
    }

    // 数値を"00:00.00"形式の文字列に変換する関数
    // 引数timeにはデフォルト値0を設定、返却する型はnullableなString?型
    private fun timeToText(time: Long = 0): String? {
        // if式は値を返すため、そのままreturnできる
        return if (time < 0){
            null
        } else if (time == 0L) {
            "00:00.00"
        } else {

            val elapsedTimeDate = System.currentTimeMillis() - time

            var m = elapsedTimeDate / 60 / 1000
            var s = (elapsedTimeDate - m * 60000) / 1000
            var ms = (elapsedTimeDate - m * 60000 - s * 1000) / 10

            "%1$02d:%2$02d.%3$02d".format(m,s,ms)
        }
    }
}

