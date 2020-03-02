package com.example.kotlin1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Restaurant_List : AppCompatActivity() {


    companion object
    {
        lateinit var dbHandler: DBHandler
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant__list)
        dbHandler = DBHandler(this,null,null,6)
        viewRestaurant()
    }

    @SuppressLint("WrongConstant")
    private fun viewRestaurant()
    {
        val restaurantslist= dbHandler.getRestaurants(this)
        val adapter= RestaurantAdapter(this,restaurantslist)
        val rv : RecyclerView = findViewById(R.id.rv)
         rv.layoutManager= LinearLayoutManager(this, LinearLayout.VERTICAL,false) as RecyclerView.LayoutManager
         rv.adapter= adapter
    }
    override fun onResume()
    {
        viewRestaurant()
        super.onResume()
    }
}
