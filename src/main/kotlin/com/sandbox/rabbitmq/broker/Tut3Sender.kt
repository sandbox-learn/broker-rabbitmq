package com.sandbox.rabbitmq.broker

import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.scheduling.annotation.Scheduled
import java.util.concurrent.atomic.AtomicInteger


class Tut3Sender(
    private val template: RabbitTemplate,
    private val fanout: FanoutExchange,
) {


    var dots: AtomicInteger = AtomicInteger(0)

    var count: AtomicInteger = AtomicInteger(0)

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    fun send() {
        val builder = StringBuilder("Hello")
        if (dots.getAndIncrement() == 3) {
            dots.set(1)
        }
        for (i in 0 until dots.get()) {
            builder.append('.')
        }
        builder.append(count.incrementAndGet())
        val message = builder.toString()
        template.convertAndSend(fanout.name, "", message)
        println(" [x] Sent '$message'")
    }

    fun send(message: String) {
        template.convertAndSend(fanout.name, "", message)
        println(" [x] Sent '$message'")
    }
}