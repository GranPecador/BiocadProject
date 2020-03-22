package com.lk.biocadproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lk.biocadproject.models.ParametersModel
import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.WebSocketFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocketListener

class HomeViewModel : ViewModel() {



    val socketUrl = "ws://192.168.1.109:8080/"

    private val _text = MutableLiveData<String>().apply {
        value = "Текущие параметры"
    }
    val text: LiveData<String> = _text
    private val _parameters= MutableLiveData<ParametersModel>().apply {
        if (this.value == null){
            this.value = ParametersModel(
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0
            )
        }
//        this.value?.let {
//            it.pressure = 0.0
//            it.humidity = 0.0
//            it.roomTemperature = 0.0
//            it.workingAreaTemperature = 0.0
//            it.levelPH = 0.0
//            it.fluidFlow = 0.0
//            it.levelCO2 = 0.0
//        }

    }
    val parameters: LiveData<ParametersModel> = _parameters

    

}