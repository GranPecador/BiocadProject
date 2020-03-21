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
                0F,
                0F,
                0F,
                0F,
                0F,
                0F,
                0F,
                0F
            )
        }
//        this.value?.let {
//            it.pressure = 0F
//            it.humidity = 0F
//            it.roomTemperature = 0F
//            it.workingAreaTemperature = 0F
//            it.levelPH = 0F
//            it.fluidFlow = 0F
//            it.levelCO2 = 0F
//        }

    }
    val parameters: LiveData<ParametersModel> = _parameters


}