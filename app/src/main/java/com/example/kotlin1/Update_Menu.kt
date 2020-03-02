package com.example.kotlin1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_update__menu.*
import kotlinx.android.synthetic.main.activity_update_restaurant.*
import java.lang.Exception

class Update_Menu : AppCompatActivity() {


    companion object {
        var dish_ID: String = ""
        var dish_Name: String = ""
        var dish_Ingredients: String=""
        var dish_Price: String=""
        var restaurant_ID: String=""
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update__menu)
        var shared=this.getSharedPreferences("A", Context.MODE_PRIVATE)
        Update_Menu.dish_ID =shared?.getInt("Edit_Dish_ID",0).toString()
        Update_Menu.dish_Name =shared?.getString("Edit_Dish_Name","").toString()
        if(shared.contains("Edit_Dish_Ingredients"))
        {
            Update_Menu.dish_Ingredients =shared?.getString("Edit_Dish_Ingredients","").toString()
            etUpdate_Dish_Ingredients.setText(Update_Menu.dish_Ingredients)
        }
        if(shared.contains("Edit_Dish_Price"))
        {
            Update_Menu.dish_Price =shared?.getString("Edit_Dish_Price","").toString()
            etUpdate_Dish_Price.setText(Update_Menu.dish_Price)
        }
        etUpdate_Dish_Name.setText(Update_Menu.dish_Name)
    }

    fun update_record(v: View?)
    {

        try {
            Menu_Restaurant.dbHandler = DBHandler(this,null,null,6)
            Menu_Restaurant.dbHandler.updateMenu_Item(dishName = etUpdate_Dish_Name.text.toString(),dishIngredients = etUpdate_Dish_Ingredients.text.toString(),dishPrice = etUpdate_Dish_Price.text.toString(),id = dish_ID)
            Toast.makeText(applicationContext,this.getString(R.string.dish_updated), Toast.LENGTH_SHORT).show()
        }catch(e : Exception)
        {
            Toast.makeText(applicationContext,this.getString(R.string.updating_error), Toast.LENGTH_SHORT).show()
        }

        var intent= Intent(this,Menu_Restaurant::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)


    }
}
