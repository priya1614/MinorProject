package com.example.minorproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.minorproject.databinding.ListItemBinding
import com.example.minorproject.model.AddCategoryModelClass

class AddCategoryAdapter(var context: Context, var arrayList: ArrayList<AddCategoryModelClass>): RecyclerView.Adapter<AddCategoryAdapter.ItemHolder >() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater= LayoutInflater.from(parent.context)
      var  binding:ListItemBinding= DataBindingUtil.inflate(inflater, R.layout.list_item, parent, false)
        return ItemHolder(binding)
    }
    override fun getItemCount(): Int{
        return arrayList.size
    }
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.binding.addCategoryModelClass=arrayList.get(position)
        holder.binding.executePendingBindings()

       val alphaChar: AddCategoryModelClass = arrayList.get(position)
           var id = alphaChar.id
           Glide.with(context)
                  .load(alphaChar.iconsChar)
                   .into(holder.binding.image);
       //     holder.alphas.text = alphaChar.alphaChar
           holder.binding.image.setOnClickListener {
               val bundle = Bundle()
              val categoryDetail:Fragment = CategoryDetail()
               bundle.putString("id", id)
               categoryDetail.arguments = bundle
                (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container, categoryDetail).addToBackStack("frag2").commit()
        }
    }
    class ItemHolder(var binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
       // var icons:ImageView= ItemView.findViewById<ImageView>(R.id.image)
       // var alphas:TextView = ItemView.findViewById<TextView>(R.id.text)
    }

}