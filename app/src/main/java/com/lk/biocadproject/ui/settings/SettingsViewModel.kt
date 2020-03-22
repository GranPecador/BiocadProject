package com.lk.biocadproject.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lk.biocadproject.api.MinMaxModelApi
import com.lk.biocadproject.api.ParameterCharacteristic
import com.lk.biocadproject.api.ParametersMinMaxApi
import com.lk.biocadproject.api.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel : ViewModel() {

    val PARAMETRES : Array<String> = arrayOf("Давление", "Влажность", "Температура помещения",
        "Температура рабочей зоны", "Уровень pH", "Масса", "Расход жидкости", "Уровень CO2")

    val PARAMS_SERVER : Array<String> = arrayOf("PRESSURE", "HUMIDITY", "TEMPHOME", "TEMPWORK",
        "LEVELPH", "MASS", "WATER", "LEVELCO2")

    var currentSelectParam:Int = -1
    var minMax = MinMaxModelApi(0.0, 100.0)

    private val _criticals = MutableLiveData<ParametersMinMaxApi>().apply {
        if (this.value == null){
            updateParameters()
        }
    }
    val criticals : LiveData<ParametersMinMaxApi> = _criticals

    fun updateParameters() {
        CoroutineScope(Dispatchers.IO).launch {
            val params: ParametersMinMaxApi? = RetrofitClient.instance.getCriticals()
            withContext(Dispatchers.Main){
                _criticals.value = params
            }
        }
    }

    fun changeParameters(min: Double, max:Double){
        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.instance.postNewParameter(
                ParameterCharacteristic( PARAMS_SERVER[currentSelectParam], min, max, "mobile"))
        }
    }
}
