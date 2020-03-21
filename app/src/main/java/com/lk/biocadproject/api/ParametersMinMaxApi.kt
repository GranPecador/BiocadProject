package com.lk.biocadproject.api

import com.google.gson.annotations.SerializedName

data class ParametersMinMaxApi(@SerializedName("PRESSURE") val pressure: MinMaxModelApi,
                               @SerializedName("HUMIDITY") val humidity: MinMaxModelApi,
                               @SerializedName("TEMPHOME") val temperature_home: MinMaxModelApi,
                               @SerializedName("TEMPWORK") val temperature_work: MinMaxModelApi,
                               @SerializedName("LEVELPH") val levelpH:MinMaxModelApi,
                               @SerializedName("MASS") val weight: MinMaxModelApi,
                               @SerializedName("WATER") val fluidFlow: MinMaxModelApi,
                               @SerializedName("LEVELCO2") val levelCO2: MinMaxModelApi)
