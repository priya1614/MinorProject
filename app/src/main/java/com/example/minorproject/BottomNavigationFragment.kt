package com.example.minorproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationFragment :Fragment()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState==null)
        {
            val category:Fragment=CategoryFragment()
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,category).commit()

        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v=inflater.inflate(R.layout.bottom_navigation,container,false)
        val nav=v?.findViewById(R.id.navi) as BottomNavigationView


        nav.setOnNavigationItemSelectedListener{item->
            when(item.itemId){
                R.id.navigation_category -> {
                    val category:Fragment=CategoryFragment()
                    (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,category).commit()
                    true
                }
                R.id.navigation_timeline-> {
                    val timeline:Fragment=TimelineFragment()
                    (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,timeline).commit()
                    true
                }R.id.navigation_profile -> {

                val profile:Fragment=ProfileFragment()
                (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,profile).commit()
                true

            }else -> false
            }
        }
        return v
    }
}

