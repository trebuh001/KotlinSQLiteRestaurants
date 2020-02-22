package com.example.kotlin1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    //val foodList= arrayListOf("Pizza Hut")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Restaurant_List.dbHandler = DBHandler(this,null,null,1)



    }



    fun show_list(v: View?)
    {
        val intent=Intent(this,Restaurant_List::class.java)
        startActivity(intent)

    }
    fun Add_restaurant(v: View?)
    {
        if(etAddNewRestaurant.text.isEmpty())
        {
            Toast.makeText(this,"Enter Restaurant Name",Toast.LENGTH_SHORT).show()
            etAddNewRestaurant.requestFocus()
        }
        else
        {
            val restaurant= Restaurant()
            restaurant.restaurantName=etAddNewRestaurant.text.toString()
            Restaurant_List.dbHandler.addRestaurant(this,restaurant)
            etAddNewRestaurant.text.clear()
            etAddNewRestaurant.requestFocus()

        }
    }
}
