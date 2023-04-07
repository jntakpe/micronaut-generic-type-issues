package com.micronaut.serde

import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import io.micronaut.serde.annotation.Serdeable
import java.util.*

@Client("\${dummy.url}")
interface DummyClient {

    @Get("/api/dummy/wrapped/list")
    fun wrappedList(): ApiResponse<List<Dummy>>


    @Get("/api/dummy/wrapped/nested")
    fun wrappedNested(): ApiResponse<ApiResponse<Dummy>>

    @Get("/api/dummy/wrapped/simple")
    fun wrappedSimple(): ApiResponse<Dummy>

    @Get("/api/dummy/raw")
    fun simpleList(): List<Dummy>
}

@Serdeable
data class Dummy(val name: String)

@Serdeable
data class ApiResponse<T>(val content: T)