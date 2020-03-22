package com.lk.biocadproject.api

import com.google.gson.annotations.SerializedName

data class ParameterCharacteristic (@SerializedName("param") val param:String,
                                    @SerializedName("min") val min: Double,
                                    @SerializedName("max") val max: Double,
                                    @SerializedName("author") val author:String)