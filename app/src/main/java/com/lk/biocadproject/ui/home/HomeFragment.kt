package com.lk.biocadproject.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.lk.biocadproject.R
import com.lk.biocadproject.models.ParametersModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocketListener
import org.json.JSONObject
import java.security.Policy
import java.util.concurrent.TimeUnit


class HomeFragment : Fragment() {

    private lateinit var pressureTextVIew: TextView
    private lateinit var humidityTextVIew: TextView
    private lateinit var roomTemperatureTextVIew: TextView
    private lateinit var workingAreaTemperatureTextVIew:TextView
    private lateinit var levelPHTextVIew: TextView
    private lateinit var weightTextView: TextView
    private lateinit var fluidFlowTextVIew: TextView
    private lateinit var levelCO2TextVIew: TextView

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })
        pressureTextVIew = root.findViewById(R.id.param_pressure)
        humidityTextVIew = root.findViewById(R.id.param_humidity)
        roomTemperatureTextVIew = root.findViewById(R.id.param_room_temp)
        workingAreaTemperatureTextVIew = root.findViewById(R.id.param_work_area_temp)
        levelPHTextVIew = root.findViewById(R.id.param_levelph)
        weightTextView = root.findViewById(R.id.param_weight)
        fluidFlowTextVIew = root.findViewById(R.id.param_fluid_flow)
        levelCO2TextVIew = root.findViewById(R.id.param_levelco2)
        homeViewModel.parameters.observe(this, Observer {
            updeteFiels(it)
        })

        SocketListener(this, homeViewModel).run()
        return root
    }

    fun updeteFiels(parameters:ParametersModel){
        parameters.let {
            pressureTextVIew.text = "${it.pressure} Па"
            humidityTextVIew.text = "${it.humidity}%"
            roomTemperatureTextVIew.text = "${it.roomTemperature}℃"
            workingAreaTemperatureTextVIew.text =
                "${it.workingAreaTemperature}℃"
            levelPHTextVIew.text = "${it.levelPH} Ед."
            weightTextView.text = "${it.weight} кг"
            fluidFlowTextVIew.text = "${it.fluidFlow} л"
            levelCO2TextVIew.text = "${it.levelCO2} PPM"
        }
    }

    class SocketListener(val fragment: HomeFragment, val viewModel: HomeViewModel) : WebSocketListener(), Runnable {

        override fun run() {
            Log.e("TAG", "nen")
            var client = OkHttpClient().newBuilder()
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .build()
            var request = Request.Builder().url("ws://192.168.1.106:8081").build()
            var webSocket = client.newWebSocket(request, this)

            Log.e("TAG", "nen")
        }

        override fun onOpen(webSocket: okhttp3.WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            Log.e("TAG", "onOpen")
        }

        override fun onMessage(webSocket: okhttp3.WebSocket, text: String) {
            super.onMessage(webSocket, text)
            webSocket.send("Hello, it's cheerful")
            Log.e("TAG", text)
            try {
                val obj = JSONObject(text)
                Log.d("My App", obj.toString())
                viewModel.parameters.value?.let {
                    it.pressure = obj.getDouble("PRESSURE")
                    it.humidity = obj.getDouble("HUMIDITY")
                    it.roomTemperature = obj.getDouble("TEMPHOME")
                    it.workingAreaTemperature = obj.getDouble("TEMPWORK")
                    it.levelPH = obj.getDouble("LEVELPH")
                    it.weight = obj.getDouble("MASS")
                    it.fluidFlow = obj.getDouble("WATER")
                    it.levelCO2 = obj.getDouble("LEVELCO2")
                    fragment.updeteFiels(it)
                }
            } catch (tx: Throwable) {
                Log.e(
                    "My App",
                    "Could not parse malformed JSON: \"" + text.toString() + "\""
                )
            }
        }

        override fun onClosed(webSocket: okhttp3.WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            Log.e("onClosed", reason)
        }

        override fun onClosing(webSocket: okhttp3.WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
            Log.e("onClosing", reason)
        }

        override fun onFailure(webSocket: okhttp3.WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            Log.e("onFailure", t.message)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}