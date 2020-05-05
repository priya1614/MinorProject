package com.example.minorproject.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.minorproject.R
import com.example.minorproject.databinding.ListItemCategoryBinding
import com.example.minorproject.model.AddCategoryModelClass

class CategoryAdapter(var context: Context, var arrayList: ArrayList<AddCategoryModelClass>): RecyclerView.Adapter<CategoryAdapter.ItemHolder >() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater= LayoutInflater.from(parent.context)
      val  binding:ListItemCategoryBinding= DataBindingUtil.inflate(inflater, R.layout.list_item_category, parent, false)
        return ItemHolder(binding)
    }
    override fun getItemCount(): Int{
        return arrayList.size
    }
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.binding.addCategoryModelClass=arrayList.get(position)
        holder.binding.executePendingBindings()

       val alphaChar: AddCategoryModelClass = arrayList.get(position)
           val id = alphaChar.id
           Glide.with(context)
                  .load(alphaChar.iconsChar)
                   .into(holder.binding.image)

           holder.binding.image.setOnClickListener {
               val bundle = Bundle()
              val categoryDetail:Fragment = CategoryDetailFragment()
               bundle.putString("id", id)
               categoryDetail.arguments = bundle
                (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container, categoryDetail).addToBackStack("frag2").commit()
        }
    }
    class ItemHolder(var binding: ListItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

    }

}