package com.example.minorproject.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.minorproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationFragment :Fragment()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState==null)
        {
            val ProfileFragment:Fragment=ProfileFragment()
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,ProfileFragment).commit()

        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v=inflater.inflate(R.layout.f_bottom_navigation,container,false)
        val nav=v?.findViewById(R.id.bottom_navigation) as BottomNavigationView


        nav.setOnNavigationItemSelectedListener{item->
            when(item.itemId){
                R.id.navigation_category -> {
                    val CategoryFragment:Fragment=CategoryFragment()
                    (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,CategoryFragment).commit()
                    true
                }
                R.id.navigation_timeline-> {
                    val TimelineFragment:Fragment=TimelineFragment()
                    (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,TimelineFragment).commit()
                    true
                }R.id.navigation_profile -> {

                val ProfileFragment:Fragment=ProfileFragment()
                (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,ProfileFragment).commit()
                true

            }else -> false
            }
        }
        return v
    }
}

