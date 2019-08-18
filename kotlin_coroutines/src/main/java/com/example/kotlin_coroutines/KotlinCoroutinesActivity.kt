package com.example.kotlin_coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_kotlin_coroutines.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class KotlinCoroutinesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_coroutines)

        fetchButton.setOnClickListener {
            waitingForAJob()
        }
    }

    /**
     * https://github.com/Kotlin/kotlinx.coroutines/blob/master/docs/basics.md#waiting-for-a-job
     *
     * Job#joinが合流点的なイメージ？
     */
    private fun waitingForAJob() = runBlocking {
        val job = GlobalScope.launch {
            delay(1000)
            println("World!!")
        } // launchの戻り値はJob
        println("Hello,")
        job.join() // jobが終わるまで待つ
    }

    /**
     * https://github.com/Kotlin/kotlinx.coroutines/blob/master/docs/basics.md#bridging-blocking-and-non-blocking-worlds
     *
     * runBlockingは現在のスレッドをブロックして動く
     * また、runBlockingの戻り値はTなので結果を受け取ることもできる。
     */
    private fun blockingAndNonBlocking2() = runBlocking {
        GlobalScope.launch {
            delay(1000)
            println("World!")
        }
        println("Hello,")
        delay(2000)
        println("Hoge")
    }

    private fun blockingAndNonBlocking() {
        GlobalScope.launch {
            delay(1000)
            println("World!")
        }
        println("Hello,")
        runBlocking {
            delay(2000)
        }
        println("Hoge")
    }

    private fun firstCoroutine() {
        // GlobalScopeのcoroutineはアプリケーションと同じ生存期間をもつ
        GlobalScope.launch {
            // launch a new coroutine in background and continue
            delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
            println("World!") // print after delay
        }
        println("Hello,") // main thread continues while coroutine is delayed
    }
}
