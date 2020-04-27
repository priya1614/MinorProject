package com.example.minorproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigation :Fragment()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState==null)
        {
            var category:Fragment=Category()
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,category).commit()

        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v=inflater.inflate(R.layout.bottom_navigation,container,false)
        var nav=v?.findViewById(R.id.navi) as BottomNavigationView


        nav?.setOnNavigationItemSelectedListener{item->
            when(item.itemId){
                R.id.navigation_category -> {
                    var category:Fragment=Category()
                    (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,category).commit()
                    true
                }
                R.id.navigation_timeline-> {
                    var timeline:Fragment=Timeline()
                    (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,timeline).commit()
                    true
                }R.id.navigation_profile -> {

                var profile:Fragment=Profile()
                (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_container,profile).commit()
                true

            }else -> false
            }
        }
        return v
    }
}

