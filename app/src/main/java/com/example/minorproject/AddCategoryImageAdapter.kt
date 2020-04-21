package com.example.minorproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.minorproject.model.AddCategoryImageModelClass
import com.example.minorproject.model.AddCategoryModelClass

class AddCategoryImageAdapter(var context: Context, var arrayList: ArrayList<AddCategoryImageModelClass>): RecyclerView.Adapter<AddCategoryImageAdapter.ItemHolder >() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ItemHolder {
        Log.d("val", "kuch b5")
        val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.list_item_2, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount(): Int {
        Log.d("arraysize","${arrayList.size}")
        return arrayList.size
    }


    override fun onBindViewHolder(holder: AddCategoryImageAdapter.ItemHolder, position: Int) {
        val alphaChar: AddCategoryImageModelClass = arrayList.get(position)
        var id2=alphaChar.id
        var id1=alphaChar.args

        Glide.with(context)
                .load(alphaChar.iconsChar)
                .into(holder.icons);
        holder.icons.setOnClickListener {
            Toast.makeText(context, "sucessful"+id2, Toast.LENGTH_SHORT).show()

            var FullScreenImage: Fragment =FullScreenImage()
            val bundle = Bundle()
            bundle.putString("id2",id2)
            bundle.putString("id1",id1)

            FullScreenImage.arguments = bundle
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.cl,FullScreenImage).commit()

        }

    }

    class ItemHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var icons: ImageView = ItemView.findViewById<ImageView>(R.id.image2)

    }


}

