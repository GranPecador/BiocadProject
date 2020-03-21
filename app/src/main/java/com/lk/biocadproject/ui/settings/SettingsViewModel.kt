package com.lk.biocadproject.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lk.biocadproject.api.ParametersMinMaxApi
import com.lk.biocadproject.api.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel : ViewModel() {

    val PARAMETRES : Array<String> = arrayOf("Давление", "Влажность", "Температура помещения",
        "Температура рабочей зоны", "Уровень pH", "Масса", "Расход жидкости", "Уровень CO2")

    private val _criticals = MutableLiveData<ParametersMinMaxApi>().apply {
        if (this.value == null){
            updateParameters()
        }
    }
    val criticals : LiveData<ParametersMinMaxApi> = _criticals

    fun updateParameters() {
        var params : ParametersMinMaxApi? = null
        CoroutineScope(Dispatchers.IO).launch {
            params = RetrofitClient.instance.getCriticals()
            withContext(Dispatchers.Main){
                _criticals.value = params
            }
        }
    }
}
