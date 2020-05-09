package com.example.minorproject.view


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.minorproject.R
import com.example.minorproject.databinding.ListItemTimelineBinding
import com.example.minorproject.model.TimeLineModelClass

class TimeLineAdapter(var context: Context, var arrayList: ArrayList<TimeLineModelClass>): RecyclerView.Adapter<TimeLineAdapter.ItemHolder >() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ItemHolder {
        val inflater= LayoutInflater.from(parent.context)
        val  binding: ListItemTimelineBinding= DataBindingUtil.inflate(inflater, R.layout.list_item_timeline, parent, false)
        return ItemHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
    override fun onBindViewHolder(holder:ItemHolder, position: Int) {
        holder.binding.timeLineModelClass=arrayList.get(position)
        holder.binding.executePendingBindings()


        val alphaChar: TimeLineModelClass = arrayList.get(position)

        holder.binding.date.text =(alphaChar.date).toString()
        Glide.with(context)
                .load(alphaChar.image).apply(RequestOptions.circleCropTransform())
                .into(holder.binding.image3)

        }
    class ItemHolder(var binding: ListItemTimelineBinding) : RecyclerView.ViewHolder(binding.root) {
    }

}
