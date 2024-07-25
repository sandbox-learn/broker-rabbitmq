package com.sandbox.rabbitmq.broker

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.util.StopWatch


class Tut3Receiver() {
    @RabbitListener(queues = ["#{autoDeleteQueue1.name}"])
    @Throws(InterruptedException::class)
    fun receive1(`in`: String) {
        receive(`in`, 1)
    }

    @RabbitListener(queues = ["#{autoDeleteQueue2.name}"])
    @Throws(InterruptedException::class)
    fun receive2(`in`: String) {
        receive(`in`, 2)
    }

    @Throws(InterruptedException::class)
    fun receive(`in`: String, receiver: Int) {
        val watch = StopWatch()
        watch.start()
        println("instance $receiver [x] Received '$`in`'")
        doWork(`in`)
        watch.stop()
        println(
            ("instance " + receiver + " [x] Done in "
                    + watch.totalTimeSeconds).toString() + "s"
        )
    }

    @Throws(InterruptedException::class)
    private fun doWork(`in`: String) {
        for (ch: Char in `in`.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(10000)
            }
        }
    }
}