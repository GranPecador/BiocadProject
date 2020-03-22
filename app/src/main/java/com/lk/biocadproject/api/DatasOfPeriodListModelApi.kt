package com.lk.biocadproject.api

import com.google.gson.annotations.SerializedName

data class DatasOfPeriodListModelApi (@SerializedName("parameters") val dataList: List<Double>)