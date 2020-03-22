package com.lk.biocadproject.ui.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lk.biocadproject.R
import com.lk.biocadproject.models.ErrorMessageModel
import kotlinx.android.synthetic.main.mistake_item.view.*

class MistakesRecyclerAdapter : RecyclerView.Adapter<MistakesRecyclerAdapter.ViewHolder>() {

    private val mValues: MutableList<ErrorMessageModel> = ArrayList()

    fun setData(list : List<ErrorMessageModel>){
        mValues.clear()
        mValues.addAll(list)

        notifyDataSetChanged()
    }

    fun getMessages():List<ErrorMessageModel>{
        return mValues
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.mistake_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.bind(item = item)
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(mView: View):RecyclerView.ViewHolder(mView){
        var dateTime : TextView = mView.dateTime_item
        var parameterName: TextView = mView.parameter_mistake_item
        var valueParameterName : TextView = mView.param_value_item
        var message:TextView = mView.message_item

        fun bind(item: ErrorMessageModel){
            dateTime.text = item.dateTime
            parameterName.text = item.param
            valueParameterName.text = item.value
            message.text = item.message
        }
    }
}