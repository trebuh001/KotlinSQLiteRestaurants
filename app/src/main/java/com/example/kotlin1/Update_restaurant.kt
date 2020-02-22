package com.example.kotlin1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_update_restaurant.*
import java.lang.Exception

class Update_restaurant : AppCompatActivity() {

    companion object {
        var restaurant_ID: String = ""
        var restaurant_Name: String = ""
        var restaurant_City: String=""
        var restaurant_Street: String=""
        var restaurant_StreetNumber: String=""
        var restaurant_Phone: String=""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_restaurant)
        var shared=this.getSharedPreferences("A", Context.MODE_PRIVATE)
        restaurant_ID=shared?.getInt("Edit_Restaurant_ID",0).toString()
        restaurant_Name=shared?.getString("Edit_Restaurant_Name","").toString()
        if(shared.contains("Edit_Restaurant_City"))
        {
            restaurant_City=shared?.getString("Edit_Restaurant_City","").toString()
            etUpdate_Restaurant_City.setText(restaurant_City)
        }
        if(shared.contains("Edit_Restaurant_Street"))
        {
            restaurant_Street=shared?.getString("Edit_Restaurant_Street","").toString()
            etUpdate_Restaurant_Street.setText(restaurant_Street)
        }
        if(shared.contains("Edit_Restaurant_StreetNumber"))
        {
            restaurant_StreetNumber=shared?.getString("Edit_Restaurant_StreetNumber","").toString()
            etUpdate_Restaurant_Street_Number.setText(restaurant_StreetNumber)
        }
        if(shared.contains("Edit_Restaurant_Phone"))
        {
            restaurant_Phone=shared?.getString("Edit_Restaurant_Phone","").toString()
            etUpdate_Restaurant_Phone.setText(restaurant_Phone)
        }
        etUpdate_Restaurant_Name.setText(restaurant_Name)

    }
        fun update_record(v: View?)
        {

            try {
                Restaurant_List.dbHandler = DBHandler(this,null,null,1)
                Restaurant_List.dbHandler.updateRestaurant(restaurantName = etUpdate_Restaurant_Name.text.toString(),id = restaurant_ID,
                    restaurantCity = etUpdate_Restaurant_City.text.toString(),restaurantStreet = etUpdate_Restaurant_Street.text.toString(),
                    restaurantStreetNumber = etUpdate_Restaurant_Street_Number.text.toString(),restaurantPhone = etUpdate_Restaurant_Phone.text.toString())
                Toast.makeText(applicationContext,"Restaurant updated",Toast.LENGTH_SHORT).show()
            }catch(e : Exception)
            {
                Toast.makeText(applicationContext,"Updating Error",Toast.LENGTH_SHORT).show()
            }

            var intent= Intent(this,Restaurant_List::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)


        }
}
