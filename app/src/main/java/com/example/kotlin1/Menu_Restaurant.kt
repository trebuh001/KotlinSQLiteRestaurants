package com.example.kotlin1

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_menu__restaurant.*

class Menu_Restaurant : AppCompatActivity() {

    companion object
    {
        var restaurant_ID: String=""
        var restaurant_Name: String=""
        lateinit var dbHandler: DBHandler
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu__restaurant)
        var shared=this.getSharedPreferences("A", Context.MODE_PRIVATE)
        restaurant_ID =shared?.getInt("Edit_Restaurant_ID",0).toString()
        restaurant_Name =shared?.getString("Edit_Restaurant_Name","").toString()
        txt_menu_restaurant.text="Menu: $restaurant_Name"

        Menu_Restaurant.dbHandler = DBHandler(this,null,null,6)
        viewMenu()
    }
    fun Add_To_Menu(v:View?)
    {
        Utils.startActivity(this,Add_dish::class.java)
    }
    @SuppressLint("WrongConstant")
    private fun viewMenu()
    {
        val menu_items= Menu_Restaurant.dbHandler.getMenu_Items(this)
        val adapter= MenuAdapter(this,menu_items)
        val rv : RecyclerView = findViewById(R.id.rv_menu)
        rv.layoutManager= LinearLayoutManager(this, LinearLayout.VERTICAL,false) as RecyclerView.LayoutManager
        rv.adapter= adapter
    }
    override fun onResume()
    {
        viewMenu()
        super.onResume()
    }
}
