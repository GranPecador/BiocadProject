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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import com.lk.biocadproject.R
import com.lk.biocadproject.api.RetrofitClient
import okhttp3.Dispatcher

class SettingsFragment : Fragment() {


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
            setMinMax(position)
        }

        val settingsChangeButton = root.findViewById<MaterialButton>(R.id.button_send_new_setting)
        settingsChangeButton.setOnClickListener {
        }

        return root
    }

    private fun setMinMax(position: Int) {
        when (position){
            //1 ->
        }
    }
}
