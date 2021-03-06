package com.lk.biocadproject.ui.dashboard

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.button.MaterialButton
import com.lk.biocadproject.R
import com.lk.biocadproject.api.MinMaxAverageModelApi
import com.lk.biocadproject.api.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private var flagPeriod = 0

    private lateinit var startPeriod: TextView
    private lateinit var endPeriod: TextView
    private lateinit var sendPeriodsButton: MaterialButton
    private lateinit var barChart: BarChart
    private lateinit var lineChart: LineChart

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
        val dropdownMenu = root.findViewById<AutoCompleteTextView>(R.id.analytics_exposed_dropdown)
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
        dashboardViewModel.dataForBarChart.observe(this, Observer {
            updateBarEntry()
        })

        lineChart = root.findViewById(R.id.param_linechart)
        dashboardViewModel.dataForLineChart.observe(this, Observer {
            updateLineEntry()
        })

        return root
    }

    private fun showLineChart() {
        val lineDataSet = LineDataSet(dashboardViewModel.dataLineEntry, "")
        //context?.let {lineDataSet.color = getColor(it,R.color.colorPrimary50)}
        val lineData = LineData(lineDataSet)
        lineChart.data = lineData
        lineChart.description.isEnabled=false
        //lineChart.setOnChartValueSelectedListener(this)
        val xAxisTemp: XAxis = lineChart.xAxis
        lineChart.xAxis.axisMaximum =dashboardViewModel.dataLineEntry.size.toFloat()
        var yAxisTemp: YAxis = lineChart.axisLeft
        if (lineData.yMin > 0) {
            yAxisTemp.axisMinimum = 0F
            xAxisTemp.position = XAxis.XAxisPosition.BOTTOM
        }
        if (lineData.yMax < 0) {
            yAxisTemp.axisMaximum = 0F
            xAxisTemp.position = XAxis.XAxisPosition.TOP
        }
        yAxisTemp = lineChart.axisRight
        yAxisTemp.isEnabled = false
        //lineChart.xAxis.valueFormatter = MyXAxisFormatter(dateXAxisLabel)
        xAxisTemp.labelRotationAngle=-75F
        // Set the marker to the chart
        //mvTemp.setChartView(lineChart)
        //lineChart.setMarker(mvTemp)
        lineChart.invalidate()
    }
    
    private fun showBarChart(){
        val barDataSet = BarDataSet(dashboardViewModel.dataBarEntry, "" )
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
        //barChart.setOnChartValueSelectedListener(this)
        val xAxis: XAxis = barChart.xAxis
        var yAxis = barChart.axisLeft
        setYAxisMinMaxBarChart(barData, yAxis, xAxis)
        yAxis = barChart.axisRight
        yAxis.isEnabled = false
        xAxis.axisMaximum = dashboardViewModel.dataBarEntry.size.toFloat()
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
        var monthStr = (monthOfYear+1).toString()
        if (monthOfYear<9) {
            monthStr = "0$monthStr"
        }
        if (flagPeriod == 1)
            startPeriod.text = "$year-${monthStr}-$dayOfMonth"
        else if (flagPeriod ==2)
            endPeriod.text = "$year-${monthStr}-$dayOfMonth"
        flagPeriod=0
    }

    private fun setOnClickList() {
        sendPeriodsButton.setOnClickListener {
            if (startPeriod.text!="ДД.ММ.ГГГГ" && endPeriod.text!="ДД.ММ.ГГГГ" &&
                        startPeriod.text!="Выберите дату" && endPeriod.text!="Выберите дату" &&
                    dashboardViewModel.currentSelectParam in 0..7) {
                getDataOfPeriod(startPeriod.text.toString(), endPeriod.text.toString())
                getAverageOfParameter()
            } else {
                return@setOnClickListener
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

    fun updateBarEntry(){
        dashboardViewModel.dataBarEntry.clear()
        var i = 0
        dashboardViewModel.dataForBarChart.value?.forEach {
            dashboardViewModel.dataBarEntry.add(BarEntry(i.toFloat(), it.toFloat()))
            i++
        }
        showBarChart()
    }

    fun updateLineEntry(){
        dashboardViewModel.dataLineEntry.clear()
        var i = 0
        dashboardViewModel.dataForLineChart.value?.forEach {
            dashboardViewModel.dataLineEntry.add(Entry(i.toFloat(), it.toFloat()))
            i++
        }
        showLineChart()
    }

    fun getDataOfPeriod(dateStart:String, dateEnd:String){
        CoroutineScope(Dispatchers.IO).launch {
            val minMaxAvarage: MinMaxAverageModelApi =
                RetrofitClient.instance.getMinMaxAvarage(dashboardViewModel.PARAMS_SERVER[dashboardViewModel.currentSelectParam],
                    dateStart, dateEnd)
            withContext(Dispatchers.Main){
                dashboardViewModel.dataForBarChart.value?.let{
                    it.clear()
                    it.add(minMaxAvarage.min)
                    it.add(minMaxAvarage.avg)
                    it.add(minMaxAvarage.max)
                    Log.e("dashVM", "${it[0]} ${it[1]} ${it[2]}")
                }
                updateBarEntry()
            }
        }
    }

    fun getAverageOfParameter(){
        CoroutineScope(Dispatchers.IO).launch {
            val averageList =
                RetrofitClient.instance.getAvarage(dashboardViewModel.PARAMS_SERVER[dashboardViewModel.currentSelectParam], "today")
            withContext(Dispatchers.Main){
                dashboardViewModel.dataForLineChart.value?.let{
                    it.clear()
                    it.addAll(averageList.list)
                }
                updateLineEntry()

            }
        }
    }

    class MyXAxisFormatter(val list :List<String>): ValueFormatter(){
        override fun getAxisLabel(value:Float,axis: AxisBase?):String{
            //Log.e("AnaliticsAxis", list[value.toInt()] + value)
            return list[value.toInt()]
        }
    }
}