package com.lk.biocadproject.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lk.biocadproject.api.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Последние сообщения об ошибках"
    }
    val text: LiveData<String> = _text

    val adapter = MistakesRecyclerAdapter()

    fun getMistakes(){
        CoroutineScope(Dispatchers.IO).launch{
            val data= RetrofitClient.instance.getMistakes()
            withContext(Dispatchers.Main){
                adapter.setData(data.errors)
            }
        }
    }
}