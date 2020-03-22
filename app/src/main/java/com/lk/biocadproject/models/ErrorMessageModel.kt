package com.lk.biocadproject.models

import com.google.gson.annotations.SerializedName

data class ErrorMessageModel (@SerializedName("dateTime") val dateTime: String,
                              @SerializedName("paramName") val param: String,
                              @SerializedName("paramValue") val value: String,
                              @SerializedName("message") val message: String)