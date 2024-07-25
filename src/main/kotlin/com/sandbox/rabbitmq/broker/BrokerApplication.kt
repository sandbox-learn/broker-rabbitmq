package com.sandbox.rabbitmq.broker

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BrokerApplication : CommandLineRunner {
	override fun run(vararg args: String) {
		Thread {
			while (true) {
				try {
					Thread.sleep(1000)
				} catch (e: InterruptedException) {
					Thread.currentThread().interrupt()
					break
				}
			}
		}.start()
	}

}

fun main(args: Array<String>) {
	runApplication<BrokerApplication>(*args)
}
