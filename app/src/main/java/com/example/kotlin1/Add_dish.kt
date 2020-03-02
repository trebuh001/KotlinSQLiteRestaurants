package com.example.kotlin1

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_dish.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_update_restaurant.*
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.nio.file.Files.delete
import java.nio.file.Files.exists
import android.os.Environment.getExternalStorageDirectory
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth




class Add_dish : AppCompatActivity() {


    companion object
    {
        lateinit var bitmap: Bitmap
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_dish)
    }
    fun Add_To_Menu(v: View?)
    {

        if(etAddDishName.text.isEmpty())
        {
            Toast.makeText(this,"Enter Dish Name",Toast.LENGTH_SHORT).show()
            etAddDishName.requestFocus()
        }
        else
        {
            var shared=getSharedPreferences("A",Context.MODE_PRIVATE)
            val menu_item= Menu()
            menu_item.dishName=etAddDishName.text.toString()
            menu_item.restaurantID=shared.getInt("Edit_Restaurant_ID",0)
            if(etAddDishIngredients.text.isNotEmpty())
            {
                menu_item.dishIngredients=etAddDishIngredients.text.toString()
            }
            if(etAddDishPrice.text.isNotEmpty())
            {
                menu_item.dishPrice=etAddDishPrice.text.toString()
            }




            Menu_Restaurant.dbHandler.addMenu_Items(this,menu_item)


        }

        var intent= Intent(this,Menu_Restaurant::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)


    }

    /*fun Add_From_Gallery(v: View?)
    {
       val intent=Intent()
        intent.type="image/"
        intent.action=Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select Image"),124)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==123)

        if(requestCode==124)
        {
            bitmap = data!!.data as Bitmap


        }
    }*/
}
