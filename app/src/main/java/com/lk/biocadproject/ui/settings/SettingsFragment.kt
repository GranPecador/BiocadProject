package com.lk.biocadproject.ui.settings

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

import com.lk.biocadproject.R
import com.lk.biocadproject.api.MinMaxModelApi

class SettingsFragment : Fragment() {

    private lateinit var minEditText: TextInputEditText
    private lateinit var maxEditText: TextInputEditText

    private lateinit var viewModel: SettingsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        viewModel.updateParameters()

        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        val adapter: ArrayAdapter<String> = ArrayAdapter(context!!, R.layout.dropdown_menu_popup_item, viewModel.PARAMETRES)
        val dropdownMenu = root.findViewById<AutoCompleteTextView>(R.id.exposed_dropdown)
        dropdownMenu.setAdapter(adapter)
        dropdownMenu.setOnItemClickListener { parent, view, position, id ->
            viewModel.currentSelectParam = position
            setMinMax(position)
        }

        minEditText = root.findViewById(R.id.minEditText)
        maxEditText = root.findViewById(R.id.maxEditText)

        val settingsChangeButton = root.findViewById<MaterialButton>(R.id.button_send_new_setting)
        settingsChangeButton.setOnClickListener {
            sendNewParam()
        }

        return root
    }

    private fun setMinMax(position: Int) {
        viewModel.criticals.value?.let {
            when (position){
                0 -> viewModel.minMax = it.pressure
                1 -> viewModel.minMax = it.humidity
                2 -> viewModel.minMax = it.temperature_home
                3 -> viewModel.minMax = it.temperature_work
                4 -> viewModel.minMax = it.levelpH
                5 -> viewModel.minMax = it.weight
                6 -> viewModel.minMax = it.fluidFlow
                7 -> viewModel.minMax = it.levelCO2
            }
        }
        minEditText.hint = "Текущий ${viewModel.minMax.min}"
        maxEditText.hint = "Текущий ${viewModel.minMax.max}"
    }

    private fun sendNewParam(){
        var minNum:Double = viewModel.minMax.min
        if(!minEditText.text.isNullOrEmpty()){
            minNum = minEditText.text.toString().toDouble()
        }
        var maxNum:Double = viewModel.minMax.max
        if (!maxEditText.text.isNullOrEmpty()){
            maxNum = maxEditText.text.toString().toDouble()
        }
        viewModel.changeParameters(minNum, maxNum)
        minEditText.text?.clear()
        maxEditText.text?.clear()
        viewModel.updateParameters()
    }
}
