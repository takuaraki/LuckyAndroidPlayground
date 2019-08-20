package com.example.kotlin_coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_kotlin_coroutines.*
import kotlinx.coroutines.*

class KotlinCoroutinesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_coroutines)

        fetchButton.setOnClickListener {
            scopeBuilder()
        }
    }

    /**
     * https://github.com/Kotlin/kotlinx.coroutines/blob/master/docs/basics.md#scope-builder
     *
     * coroutineScopeでもrunBlockingみたいにcoroutine scopeを作れる
     * coroutineScopeは現在のスレッドをブロックしない（よくわからん）
     *
     * 結果の予想（実行前に予想してみた）
     * > Task from coroutine scope
     * > Task from runBlocking
     * > Task from nested launch
     * > Coroutine scope is over
     *
     * 結果: 予想通りの出力
     */
    private fun scopeBuilder() = runBlocking {
        launch {
            delay(200L)
            println("Task from runBlocking")
        }

        coroutineScope { // これでCoroutineScopeができる。現在のスレッドはブロックしない
            // ネストされたlaunchブロック
            launch {
                delay(500L)
                println("Task from nested launch")
            }

            delay(100L)
            println("Task from coroutine scope") // これはネストされたlaunchブロックよりも先に実行される
        }

        println("Coroutine scope is over") // これはネストされたlaunchブロックが終わるまで実行されない
    }

    /**
     * https://github.com/Kotlin/kotlinx.coroutines/blob/master/docs/basics.md#structured-concurrency
     *
     * concurrency: 並行性。Go言語にConcurrencyというのがあるらしい。関係あるかも。
     * スレッドを切るみたいに特定の操作をコルーチンとして切り出せる？
     * GlobalScope.launchは使わんくていい。
     */
    private fun structuredConcurrency() = runBlocking {
        launch { // runBlockingのスコープ内で（よくわからん）新しくコルーチンを起動する
            delay(1000L)
            // これはちゃんと実行される。
            // runBlockingはこのlaunchでつくったコルーチンが終わるまでは終わらない。
            println("World!")
        }
        println("Hello,")
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
