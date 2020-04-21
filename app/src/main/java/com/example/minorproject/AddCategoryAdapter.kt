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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.minorproject.model.AddCategoryModelClass

class AddCategoryAdapter(var context: Context, var arrayList: ArrayList<AddCategoryModelClass>): RecyclerView.Adapter<AddCategoryAdapter.ItemHolder >() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        Log.d("val", "kuch b5")
        val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount(): Int {
        Log.d("arraysize","${arrayList.size}")
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val alphaChar: AddCategoryModelClass = arrayList.get(position)
        var id=alphaChar.id
        Glide.with(context)
                .load(alphaChar.iconsChar)
                .into(holder.icons);
        holder.alphas.text=alphaChar.alphaChar


        holder.alphas.setOnClickListener {
            Toast.makeText(context, "sucessful"+id, Toast.LENGTH_SHORT).show()

            var AddCategoryImage:Fragment=AddCategoryImage()
            var FullScreenImage:Fragment=FullScreenImage()
            val bundle = Bundle()
            bundle.putString("id",id)
            AddCategoryImage.arguments = bundle
            FullScreenImage.arguments=bundle
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.cl,AddCategoryImage).commit()

        }
    }
    class ItemHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var icons:ImageView= ItemView.findViewById<ImageView>(R.id.image)
        var alphas:TextView = ItemView.findViewById<TextView>(R.id.text)


    }

}