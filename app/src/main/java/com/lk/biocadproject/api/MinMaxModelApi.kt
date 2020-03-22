package com.lk.biocadproject.api

import com.google.gson.annotations.SerializedName

data class MinMaxModelApi(@SerializedName("min") var min: Double,
                          @SerializedName("max") var max: Double)