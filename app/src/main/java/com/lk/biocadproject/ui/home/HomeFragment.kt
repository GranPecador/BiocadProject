package com.lk.biocadproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.lk.biocadproject.ParametersModel
import com.lk.biocadproject.R

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
        val levelPHTextVIew:TextView = root.findViewById(R.id.param_levelph)
        val weightTextView: TextView = root.findViewById(R.id.param_weight)
        val fluidFlowTextVIew:TextView = root.findViewById(R.id.param_fluid_flow)
        val levelCO2TextVIew: TextView = root.findViewById(R.id.param_levelco2)
        homeViewModel.parameters.observe(this, Observer {
            pressureTextVIew.text = "Давление: ${it.pressure} Па"
            humidityTextVIew.text = "Влажность: ${it.humidity}%"
            roomTemperatureTextVIew.text = "Температура помещения: ${it.roomTemperature}℃"
            workingAreaTemperatureTextVIew.text = "Температура рабочей зоны: ${it.workingAreaTemperature}℃"
            levelPHTextVIew.text = "Уровень pH: ${it.levelPH} Ед."
            weightTextView.text = "Масса: ${it.weight} кг"
            fluidFlowTextVIew.text = "Расход жидкости: ${it.fluidFlow} л"
            levelCO2TextVIew.text = "Уровень CO: ${it.levelCO2} PPM"
        })
        return root
    }
}