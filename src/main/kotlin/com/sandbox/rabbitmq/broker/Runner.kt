package com.sandbox.rabbitmq.broker

import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component


@Profile("sender")
@Component
class Runner(
    private val template: RabbitTemplate,
    private val fanout: FanoutExchange,
) : CommandLineRunner {
    @Throws(Exception::class)
    override fun run(vararg args: String) {
        val sender = Tut3Sender(template, fanout)
        val message = "Je suis un message"
        println("sending message....")

        sender.send()
        sender.send(message)
    }
}