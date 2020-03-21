package com.lk.biocadproject.ui.dashboard

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.google.android.material.button.MaterialButton
import com.lk.biocadproject.R

class DashboardFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private var flagPeriod = 0

    private lateinit var startPeriod: TextView
    private lateinit var endPeriod: TextView
    private lateinit var sendPeriodsButton: MaterialButton
    private lateinit var barChart: BarChart

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val adapter: ArrayAdapter<String> = ArrayAdapter(context!!, R.layout.dropdown_menu_popup_item,
            dashboardViewModel.PARAMETRES)
        val dropdownMenu = root.findViewById<AutoCompleteTextView>(R.id.exposed_dropdown)
        dropdownMenu.setAdapter(adapter)
        dropdownMenu.setOnItemClickListener { parent, view, position, id ->
            dashboardViewModel.currentSelectParam = position
        }
        startPeriod = root.findViewById(R.id.start_of_period_edittext)
        endPeriod = root.findViewById(R.id.end_of_period_edittext)
        sendPeriodsButton = root.findViewById(R.id.sent_periods_on_server_button)
        setOnTextWatchers()
        setOnClickList()

        barChart = root.findViewById(R.id.param_barchart)
        dashboardViewModel.dataForGraphic.observe(this, Observer {
            if (it.isNotEmpty()){
                prepareDataForGraphics()
                showGraphics()
            }
        })

        return root
    }

    private fun showGraphics(){
        val barDataSet = BarDataSet(dataPoint, "rssi, дБм")
        //context?.let {barDataSet.color = getColor(it,R.color.colorPrimary50)}
        barDataSet.setDrawValues(true)
        val barData = BarData(barDataSet)
        barChart.data = barData
        setXYAxisBarChart(barChart, barData)
        // Set the marker to the chart
        //mvTemp.setChartView(barChart)
        //barChart.setMarker(mvTemp)
        barChart.invalidate()
    }

    private fun setXYAxisBarChart(barChart: BarChart, barData: BarData) {
        barChart.description.isEnabled=false
        barChart.setOnChartValueSelectedListener(this)
        val xAxis: XAxis = barChart.xAxis
        var yAxis = barChart.axisLeft
        setYAxisMinMaxBarChart(barData, yAxis, xAxis)
        yAxis = barChart.axisRight
        yAxis.isEnabled = false
        xAxis.axisMaximum = size.toFloat()
        xAxis.labelRotationAngle=-60F
        //barChart.xAxis.valueFormatter = MyXAxisFormatter(dateXAxisLabel)
    }

    private fun setYAxisMinMaxBarChart(barData: BarData, yAxis: YAxis, xAxis: XAxis?=null) {
        if (barData.yMin >= 0) {
            yAxis.axisMinimum = 0F
            xAxis?.position = XAxis.XAxisPosition.BOTTOM
        }
        if (barData.yMax <= 0) {
            yAxis.axisMaximum = 0F
            xAxis?.position = XAxis.XAxisPosition.TOP
        }
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
            startPeriod.text = "$dayOfMonth.${monthOfYear+1}.$year"
        else if (flagPeriod ==2)
            endPeriod.text = "$dayOfMonth.${monthOfYear+1}.$year"
        flagPeriod=0
    }

    private fun setOnClickList() {
        sendPeriodsButton.setOnClickListener {
            if (startPeriod.text!="ДД.ММ.ГГГГ" && endPeriod.text!="ДД.ММ.ГГГГ" &&
                        startPeriod.text!="Выберите дату" && endPeriod.text!="Выберите дату" &&
                    dashboardViewModel.currentSelectParam in 0..7) {
                dashboardViewModel.getDataOfPeriod(startPeriod.text.toString(), endPeriod.text.toString())
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