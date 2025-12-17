package com.hub

import com.asyncapi.kotlinasyncapi.context.service.AsyncApiExtension
import com.asyncapi.kotlinasyncapi.ktor.AsyncApiPlugin
import com.fasterxml.jackson.databind.*

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
