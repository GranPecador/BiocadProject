package com.lk.biocadproject.api

import retrofit2.http.GET

interface JSONPlaceHolderApi {

    @GET("/criticals")
    suspend fun getCriticals(): ParametersMinMaxApi
}
