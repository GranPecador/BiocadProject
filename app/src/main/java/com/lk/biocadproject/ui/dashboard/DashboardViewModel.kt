package com.lk.biocadproject.ui.dashboard

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

    fun getDataOfPeriod(dateStart:String, dateEnd:String){
        CoroutineScope(Dispatchers.IO).launch {
            val minMaxAvarage:MinMaxAverageModelApi =
                RetrofitClient.instance.getMinMaxAvarage(PARAMS_SERVER[currentSelectParam],
                dateStart, dateEnd)
            withContext(Dispatchers.Main){
                dataForBarChart.value?.let{
                    it.clear()
                    it.add(minMaxAvarage.min)
                    it.add(minMaxAvarage.avg)
                    it.add(minMaxAvarage.max)
                }
            }
        }
    }

    fun getAverageOfParameter(){
        CoroutineScope(Dispatchers.IO).launch {
            val averageList =
                RetrofitClient.instance.getAvarage(PARAMS_SERVER[currentSelectParam], "today")
            withContext(Dispatchers.Main){
                dataForLineChart.value?.let{
                    it.clear()
                    it.addAll(averageList.list)
                }
            }
        }
    }

    val dataBarEntry: MutableList<BarEntry> = ArrayList()
    val dataLineEntry:MutableList<Entry> = ArrayList()

    fun updateBarEntry(){
        dataBarEntry.clear()
        var i = 0
        dataForBarChart.value?.forEach {
            dataBarEntry.add(BarEntry(i.toFloat(), it.toFloat()))
            i++
        }
    }

    fun updateLineEntry(){
        dataLineEntry.clear()
        var i = 0
        dataForLineChart.value?.forEach {
            dataLineEntry.add(Entry(i.toFloat(), it.toFloat()))
            i++
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}