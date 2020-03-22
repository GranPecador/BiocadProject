package com.lk.biocadproject.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.lk.biocadproject.api.MinMaxAverageModelApi
import com.lk.biocadproject.api.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel : ViewModel() {

    val PARAMETRES : Array<String> = arrayOf("Давление", "Влажность", "Температура помещения",
        "Температура рабочей зоны", "Уровень pH", "Масса", "Расход жидкости", "Уровень CO2")

    val PARAMS_SERVER : Array<String> = arrayOf("PRESSURE", "HUMIDITY", "TEMPHOME", "TEMPWORK",
        "LEVELPH", "MASS", "WATER", "LEVELCO2")

    var currentSelectParam:Int = -1

    private val _dataForBarChart = MutableLiveData<MutableList<Double>>().apply {
        value = ArrayList()
        (value as ArrayList<Double>).add(12.5)
        (value as ArrayList<Double>).add(22.5)
        (value as ArrayList<Double>).add(142.5)
    }
    val dataForBarChart: LiveData<MutableList<Double>> = _dataForBarChart

    private val _dataForLineChart = MutableLiveData<MutableList<Double>>().apply {
        value = ArrayList()
        (value as ArrayList<Double>).add(12.5)
        (value as ArrayList<Double>).add(22.5)
        (value as ArrayList<Double>).add(142.5)
    }
    val dataForLineChart: LiveData<MutableList<Double>> = _dataForLineChart



    val dataBarEntry: MutableList<BarEntry> = ArrayList()
    val dataLineEntry:MutableList<Entry> = ArrayList()


    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}