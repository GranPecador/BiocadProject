package com.lk.biocadproject.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lk.biocadproject.R
import com.neovisionaries.ws.client.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.security.NoSuchAlgorithmException


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

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
        val pressureTextVIew: TextView = root.findViewById(R.id.param_pressure)
        val humidityTextVIew: TextView = root.findViewById(R.id.param_humidity)
        val roomTemperatureTextVIew: TextView = root.findViewById(R.id.param_room_temp)
        val workingAreaTemperatureTextVIew: TextView = root.findViewById(R.id.param_work_area_temp)
        val levelPHTextVIew: TextView = root.findViewById(R.id.param_levelph)
        val weightTextView: TextView = root.findViewById(R.id.param_weight)
        val fluidFlowTextVIew: TextView = root.findViewById(R.id.param_fluid_flow)
        val levelCO2TextVIew: TextView = root.findViewById(R.id.param_levelco2)
        homeViewModel.parameters.observe(this, Observer {
            pressureTextVIew.text = "Давление: ${it.pressure} Па"
            humidityTextVIew.text = "Влажность: ${it.humidity}%"
            roomTemperatureTextVIew.text = "Температура помещения: ${it.roomTemperature}℃"
            workingAreaTemperatureTextVIew.text =
                "Температура рабочей зоны: ${it.workingAreaTemperature}℃"
            levelPHTextVIew.text = "Уровень pH: ${it.levelPH} Ед."
            weightTextView.text = "Масса: ${it.weight} кг"
            fluidFlowTextVIew.text = "Расход жидкости: ${it.fluidFlow} л"
            levelCO2TextVIew.text = "Уровень CO: ${it.levelCO2} PPM"
        })
        getParams()
        return root
    }

    private fun getParams() {
        // Create a WebSocket factory and set 5000 milliseconds as a timeout
// value for socket connection.
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var factory = WebSocketFactory().setConnectionTimeout(5000)

                // Create a WebSocket. The timeout value set above is used.
                var ws: WebSocket = factory.createSocket("ws://192.168.1.109:8080/")
                ws.addListener(object : WebSocketAdapter() {
                    override fun onConnected(
                        websocket: WebSocket?,
                        headers: MutableMap<String, MutableList<String>>?
                    ) {
                        super.onConnected(websocket, headers)
                        Log.e("Websocket", "onConnected on Socket")
                    }

                    override fun onTextMessage(websocket: WebSocket?, text: String?) {
                        super.onTextMessage(websocket, text)
                        Log.e("Websocket", text)
                    }

                    override fun onError(websocket: WebSocket?, cause: WebSocketException?) {
                        super.onError(websocket, cause)
                        Log.e("Websocket", cause?.message)
                    }

                    override fun onDisconnected(
                        websocket: WebSocket?,
                        serverCloseFrame: WebSocketFrame?,
                        clientCloseFrame: WebSocketFrame?,
                        closedByServer: Boolean
                    ) {
                        super.onDisconnected(
                            websocket,
                            serverCloseFrame,
                            clientCloseFrame,
                            closedByServer
                        )
                        Log.i("Websocket", "onDisconnected")
                    }

                    override fun onUnexpectedError(
                        websocket: WebSocket?,
                        cause: WebSocketException?
                    ) {
                        super.onUnexpectedError(websocket, cause)
                        Log.i("Websocket", cause?.message)
                    }
                })
                try { // Connect to the server and perform an opening handshake.
// This method blocks until the opening handshake is finished.
                    ws.connect()
                } catch (e: OpeningHandshakeException) { // A violation against the WebSocket protocol was detected
// during the opening handshake.
                } catch (e: HostnameUnverifiedException) { // The certificate of the peer does not match the expected hostname.
                } catch (e: WebSocketException) { // Failed to establish a WebSocket connection.
                }
            } catch (e: WebSocketException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
        }
    }
}