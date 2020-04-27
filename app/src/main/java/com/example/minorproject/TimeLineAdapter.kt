package com.example.minorproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.minorproject.databinding.ListItem2Binding
import com.example.minorproject.databinding.ListItem3Binding
import com.example.minorproject.model.AddCategoryImageModelClass
import com.example.minorproject.model.TimeLineModelClass
import java.util.*
import kotlin.Unit.toString
import kotlin.collections.ArrayList

class TimeLineAdapter(var context: Context, var arrayList: ArrayList<TimeLineModelClass>): RecyclerView.Adapter<TimeLineAdapter.ItemHolder >() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ItemHolder {

        //   val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.list_item_2, parent, false)
        // return ItemHolder(itemHolder)
        val inflater= LayoutInflater.from(parent.context)
        var  binding: ListItem3Binding = DataBindingUtil.inflate(inflater, R.layout.list_item_3, parent, false)
        return ItemHolder(binding)
    }

    override fun getItemCount(): Int {
        Log.d("arraysize","${arrayList.size}")
        return arrayList.size
    }
    override fun onBindViewHolder(holder:ItemHolder, position: Int) {
        holder.binding.timeLineModelClass=arrayList.get(position)
        holder.binding.executePendingBindings()


        val alphaChar: TimeLineModelClass = arrayList.get(position)

        holder.binding.date.text =(alphaChar.date!!).toString()
        Glide.with(context)
                .load(alphaChar.image).apply(RequestOptions.circleCropTransform())
                .into(holder.binding.image3);

        }


    // class ItemHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
    //   var icons: ImageView = ItemView.findViewById<ImageView>(R.id.image2)
    class ItemHolder(var binding: ListItem3Binding) : RecyclerView.ViewHolder(binding.root) {
        // var icons:ImageView= ItemView.findViewById<ImageView>(R.id.image)
        // var alphas:TextView = ItemView.findViewById<TextView>(R.id.text)
    }

}
