package com.example.stopwatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    // 1度だけ代入するものはvalを使う
    val handler = Handler()
    // 繰り返し代入するためvarを使う
    var timeValue = 0


    // 配置したView要素にアクセス
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //View要素を変数に代入
        val timeText: TextView = findViewById(R.id.timeText)
        val startButton: Button = findViewById(R.id.start)
        val stopButton: Button = findViewById(R.id.stop)
        val resetButton: Button = findViewById(R.id.reset)

        //1秒ごとに処理を実行
        val runnable = object : Runnable {
            override fun run() {
                timeValue++

                // TextViewを更新
                // ?.letを用いて、nullではない場合のみ更新
                timeToText(timeValue)?.let {
                    // timeToText(timeValue)の値がlet内ではitとして使える
                    timeText.text = it
                }
                handler.postDelayed(this, 1)
            }
        }

        // start
        startButton.setOnClickListener {
            handler.post(runnable)
        }

        // stop
        stopButton.setOnClickListener {
            handler.removeCallbacks(runnable)
        }

        // reset
        resetButton.setOnClickListener {
            handler.removeCallbacks(runnable)
            timeValue = 0
            // timeToTextの引数はデフォルト値が設定されているので、引数省略できる
            timeToText()?.let {
                timeText.text = it
            }
        }
    }

    // 数値を"00:00:00"形式の文字列に変換する関数
    // 引数timeにはデフォルト値0を設定、返却する型はnullableなString?型
    private fun timeToText(time: Int = 0): String? {
        // if式は値を返すため、そのままreturnできる
        return if (time < 0){
            null
        } else if (time == 0) {
            "00:00.00"
        } else {
            //val h = time / 3600
            val m = time % 3600 / 60 / 1000
            val s = time / 100
            val ms = time % 100
            "%1$02d:%2$02d.%3$02d".format(m,s,ms)
        }
    }
}

