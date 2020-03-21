package com.lk.biocadproject.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lk.biocadproject.api.DataPairModelApi
import com.lk.biocadproject.api.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    val PARAMETRES : Array<String> = arrayOf("Давление", "Влажность", "Температура помещения",
        "Температура рабочей зоны", "Уровень pH", "Масса", "Расход жидкости", "Уровень CO2")

    val PARAMS_SERVER : Array<String> = arrayOf("PRESSURE", "HUMIDITY", "TEMPHOME", "TEMPWORK",
        "LEVELPH", "MASS", "WATER", "LEVELCO2")

    var currentSelectParam:Int = -1

    private val _dataForGraphic = MutableLiveData<MutableList<DataPairModelApi>>().apply {
        value = ArrayList()
        (value as ArrayList<DataPairModelApi>).add(DataPairModelApi("12.02.2020", 12.5))
        (value as ArrayList<DataPairModelApi>).add(DataPairModelApi("13.02.2020", 22.5))
        (value as ArrayList<DataPairModelApi>).add(DataPairModelApi("14.02.2020", 142.5))
    }
    val dataForGraphic: LiveData<MutableList<DataPairModelApi>> = _dataForGraphic

    fun getDataOfPeriod(dateStart:String, dateEnd:String){
        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.instance.getDataPeriod(PARAMS_SERVER[currentSelectParam], dateStart, dateEnd)
        }
    }



    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}