package com.hub


import io.github.damir.denis.tudor.ktor.server.rabbitmq.dsl.basicPublish
import io.github.damir.denis.tudor.ktor.server.rabbitmq.dsl.rabbitmq
import io.github.davidepianca98.MQTTClient
import io.github.davidepianca98.mqtt.MQTTVersion
import io.github.davidepianca98.mqtt.Subscription
import io.github.davidepianca98.mqtt.packets.Qos
import io.github.davidepianca98.mqtt.packets.mqttv5.SubscriptionOptions
import io.ktor.server.application.*


@OptIn(ExperimentalUnsignedTypes::class)
fun Application.configureMqtt() {
    val topicTemperature = "capteurs/temperature"
    val topicHumidity = "capteurs/humidite"
    val topicGaz = "capteurs/gaz"

    val client = MQTTClient(
        MQTTVersion.MQTT5,
        "localhost",
        1883,
        null,


        ) {

        rabbitmq {
            val (exchange, routingKey) = when (it.topicName) {
                topicTemperature -> "temp" to "temperature-temp"
                topicHumidity -> "hum" to "humidity-hum"
                else -> "gaz" to "gaz-gaz"
            }

            basicPublish {
                this.exchange = exchange
                this.routingKey = routingKey
                message { it.payload?.toByteArray()?.decodeToString() }
            }
        }


        println(it.payload?.toByteArray()?.decodeToString())

    }

    client.subscribe(
        listOf(
            Subscription(topicTemperature, SubscriptionOptions(Qos.AT_LEAST_ONCE)),
            Subscription(topicHumidity,    SubscriptionOptions(Qos.AT_LEAST_ONCE)),
            Subscription(topicGaz,         SubscriptionOptions(Qos.AT_LEAST_ONCE))
        )
    )

    client.publish(true, Qos.AT_MOST_ONCE, topicGaz, "hello".encodeToByteArray().toUByteArray())

//    while (true) {
    client.run()
//        Thread.sleep(10)
//    }

}


