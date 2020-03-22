package com.lk.biocadproject.api

import com.google.gson.annotations.SerializedName

data class MinMaxAverageModelApi(@SerializedName("avg") val avg: Double,
                                 @SerializedName("max") val max: Double,
                                 @SerializedName("min") val min: Double)