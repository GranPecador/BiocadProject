package com.lk.biocadproject.api

import com.google.gson.annotations.SerializedName

data class MinMaxModelApi(@SerializedName("min") val min: Double,
                          @SerializedName("max") val max: Double)