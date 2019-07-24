package com.eyulingo.ui.main

import android.net.Uri
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.eyulingo.R
import java.net.URI

class MainActivity : AppCompatActivity(), MyInfoFragment.OnFragmentInteractionListener {

    private val fragmentManager = supportFragmentManager

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_search -> {
                this@MainActivity.setTitle(R.string.bottom_nav_menu_item_search)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_store -> {
                this@MainActivity.setTitle(R.string.bottom_nav_menu_item_store)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_mine -> {
                this@MainActivity.setTitle(R.string.bottom_nav_menu_item_mine)

                val myInfoFragment = MyInfoFragment.newInstance("a", "b")

                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.add(R.id.fragment_container, myInfoFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()


                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_cart -> {
                this@MainActivity.setTitle(R.string.bottom_nav_menu_item_cart)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_order -> {
                this@MainActivity.setTitle(R.string.bottom_nav_menu_item_orders)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onFragmentInteraction(uri: Uri) {
        //TO--DO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Toast.makeText(this@MainActivity,"this is："+uri, Toast.LENGTH_SHORT).show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        this@MainActivity.setTitle(R.string.bottom_nav_menu_item_search)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

    }
}
