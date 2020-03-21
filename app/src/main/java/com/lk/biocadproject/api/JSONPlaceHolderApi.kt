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

    @GET("/period")
    suspend fun getDataPeriod(@Query("paramName") name:String,
                              @Query("dateStart") start:String,
                              @Query("dateEnd") end:String)
}
