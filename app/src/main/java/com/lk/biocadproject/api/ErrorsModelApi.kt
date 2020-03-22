package com.lk.biocadproject.api

import com.google.gson.annotations.SerializedName
import com.lk.biocadproject.models.ErrorMessageModel

data class ErrorsModelApi(@SerializedName("errors") val errors:List<ErrorMessageModel>)