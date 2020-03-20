package com.lk.biocadproject.ui.dashboard

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.button.MaterialButton
import com.lk.biocadproject.R

class DashboardFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private var flagPeriod = 0

    private lateinit var startPeriod: TextView
    private lateinit var endPeriod: TextView
    private lateinit var sendPeriodsButton: MaterialButton

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
//        val textView: TextView = root.findViewById(R.id.text_dashboard)
//        dashboardViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        startPeriod = root.findViewById(R.id.start_of_period_edittext)
        endPeriod = root.findViewById(R.id.end_of_period_edittext)
        sendPeriodsButton = root.findViewById(R.id.sent_periods_on_server_button)
        setOnTextWatchers()
        setOnClickList()
        return root
    }


    private fun setOnTextWatchers() {

        startPeriod.setOnClickListener{
            flagPeriod = 1
            listener?.onCreateDatePicker(onDateSetListener)
        }
        endPeriod.setOnClickListener{
            flagPeriod = 2
            listener?.onCreateDatePicker(onDateSetListener)
        }
    }

    var onDateSetListener = DatePickerDialog.OnDateSetListener{ view, year, monthOfYear, dayOfMonth ->
        if (flagPeriod == 1)
            startPeriod.text = "$dayOfMonth/${monthOfYear+1}/$year"
        else if (flagPeriod ==2)
            endPeriod.text = "$dayOfMonth/${monthOfYear+1}/$year"
        flagPeriod=0
    }

    private fun setOnClickList() {
        sendPeriodsButton.setOnClickListener {
            if (startPeriod.text!="ДД/ММ/ГГГГ" && endPeriod.text!="ДД/ММ/ГГГГ") {
                //getMessagesOfPeriod(startPeriod.text.toString(), endPeriod.text.toString())
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
        fun onCreateDatePicker(listener:DatePickerDialog.OnDateSetListener)
    }
}