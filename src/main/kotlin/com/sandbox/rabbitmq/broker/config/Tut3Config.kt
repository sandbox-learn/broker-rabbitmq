package com.sandbox.rabbitmq.broker.config

import com.sandbox.rabbitmq.broker.Tut3Receiver
import com.sandbox.rabbitmq.broker.Tut3Sender
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile


@Profile("pub-sub")
@Configuration
class Tut3Config {
    @Bean
    fun fanout(): FanoutExchange? {
        return FanoutExchange("tut.fanout")
    }

    @Profile("receiver")
    class ReceiverConfig {
        @Bean
        fun autoDeleteQueue1(): Queue {
            return AnonymousQueue()
        }

        @Bean
        fun autoDeleteQueue2(): Queue {
            return AnonymousQueue()
        }

        @Bean
        fun binding1(
            fanout: FanoutExchange?,
            autoDeleteQueue1: Queue?
        ): Binding {
            return BindingBuilder.bind(autoDeleteQueue1).to(fanout)
        }

        @Bean
        fun binding2(
            fanout: FanoutExchange?,
            autoDeleteQueue2: Queue?
        ): Binding {
            return BindingBuilder.bind(autoDeleteQueue2).to(fanout)
        }

        @Bean
        fun receiver(): Tut3Receiver {
            return Tut3Receiver()
        }
    }

    @Profile("sender")
    @Bean
    fun sender(template: RabbitTemplate, fanout: FanoutExchange): Tut3Sender {
        return Tut3Sender(template, fanout)
    }
}