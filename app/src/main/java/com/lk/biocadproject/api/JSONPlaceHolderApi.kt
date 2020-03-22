package com.lk.biocadproject.api

import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface JSONPlaceHolderApi {

    @GET("/criticals")
    suspend fun getCriticals(): ParametersMinMaxApi

    @POST("/critical")
    suspend fun postNewParameter(@Body param: ParameterCharacteristic)

    @GET("/maindata")
    suspend fun getMinMaxAvarage(@Query("paramName") name: String,
                                 @Query("dateStart") start:String,
                                 @Query("dateEnd") end:String,
                                 @Query("timeStart") startTime:String,
                                 @Query("timeEnd") timeEnd: String) : MinMaxAverageModelApi
}
