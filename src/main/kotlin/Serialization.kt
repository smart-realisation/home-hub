package com.hub

import com.asyncapi.kotlinasyncapi.context.service.AsyncApiExtension
import com.asyncapi.kotlinasyncapi.ktor.AsyncApiPlugin
import com.fasterxml.jackson.databind.*
import de.kempmobil.ktor.mqtt.MqttClient
import de.kempmobil.ktor.mqtt.buildFilterList
import io.ktor.serialization.gson.*
import io.ktor.serialization.jackson.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.di.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }

        json()

        gson {
        }
    }
    runBlocking {

        val client = MqttClient("localhost", 1883) {
            username = "ro"
            password = "readonly"
        }

        // Print the first 100 published packets
        val receiver = launch {
            client.publishedPackets.take(100).collect { publish ->
                println("New publish packet received: $publish")
            }
        }

        client.connect().onSuccess { connack ->
            if (connack.isSuccess) {
                client.subscribe(buildFilterList { +"#" })
            }
        }.onFailure {
            throw it
        }

        receiver.join()
        client.disconnect()
    }
    routing {
        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }
        get("/json/kotlinx-serialization") {
            call.respond(mapOf("hello" to "world"))
        }
        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}
