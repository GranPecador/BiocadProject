package com.lk.biocadproject.api

import com.google.gson.annotations.SerializedName

data class DataPairModelApi(@SerializedName("date") val date:String,
                            @SerializedName("value") val value:Double)
