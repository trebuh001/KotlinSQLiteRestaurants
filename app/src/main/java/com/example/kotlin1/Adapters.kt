package com.example.kotlin1

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.provider.ContactsContract
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.lo_menu_items.view.*
import kotlinx.android.synthetic.main.lo_restaurants.view.*
import java.util.ArrayList

class RestaurantAdapter(mCtx: Context,val restaurants : ArrayList<Restaurant>)
    : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>()
{
    val mCtx= mCtx
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val txtRestaurantName=itemView.txtRestaurantName
        val txtRestaurantAddress=itemView.txtRestaurantAddress
        val txtRestaurantPhone=itemView.txtRestaurantPhone
        val btnUpdate= itemView.btnUpdate
        val btnDelete=itemView.btnDelete
        val btnMenu=itemView.btnMenu
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantAdapter.ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.lo_restaurants,parent,false)
        return ViewHolder(v)

    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    override fun onBindViewHolder(holder: RestaurantAdapter.ViewHolder, position: Int) {
        val restaurant: Restaurant= restaurants[position]
        holder.txtRestaurantName.text=restaurant.restaurantName

            holder.txtRestaurantAddress.text= mCtx.getString(R.string.address)+" "+"${restaurant.restaurantStreetNumber} ${restaurant.restaurantStreet}\n${restaurant.restaurantCity}"

        if(restaurant.restaurantPhone.isNotEmpty())
        {
            holder.txtRestaurantPhone.text=mCtx.getString(R.string.phone)+" "+"\n${restaurant.restaurantPhone}"
        }

        holder.btnDelete.setOnClickListener()
        {
            val restaurantName=restaurant.restaurantName
            var alertDialog=AlertDialog.Builder(mCtx).setTitle(mCtx.getString(R.string.warning))
                .setMessage(mCtx.getString(R.string.sure_delete)+" "+"$restaurantName?")
                .setPositiveButton(mCtx.getString(R.string.yes), DialogInterface.OnClickListener { dialogInterface, i ->
                        if(Restaurant_List.dbHandler.deleteRestaurant(restaurant.restaurantID))
                        {
                            restaurants.removeAt(position)
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position,restaurants.size)
                            Toast.makeText(mCtx,mCtx.getString(R.string.restaurant)+" "+"$restaurantName" +" "+mCtx.getString(R.string.is_deleted_restaurant),Toast.LENGTH_SHORT ).show();
                        }

                })
                .setNegativeButton(mCtx.getString(R.string.no), DialogInterface.OnClickListener { dialogInterface, i ->  })
                .setIcon(R.drawable.ic_warning)
                .show()


        }
        holder.btnUpdate.setOnClickListener()
        {
            val shared=mCtx?.getSharedPreferences("A",Context.MODE_PRIVATE)
            val editor=shared.edit()
            editor.putInt("Edit_Restaurant_ID",restaurant.restaurantID)
            editor.putString("Edit_Restaurant_Name",restaurant.restaurantName)

                editor.putString("Edit_Restaurant_City",restaurant.restaurantCity)


                editor.putString("Edit_Restaurant_Street",restaurant.restaurantStreet)
                editor.putString("Edit_Restaurant_StreetNumber",restaurant.restaurantStreetNumber)
                editor.putString("Edit_Restaurant_Phone",restaurant.restaurantPhone)

            editor.commit()

            Utils.startActivity(mCtx,Update_restaurant::class.java)
        }
        holder.btnMenu.setOnClickListener()
        {
            Utils.startActivity(mCtx,Menu_Restaurant::class.java)
            val shared=mCtx?.getSharedPreferences("A",Context.MODE_PRIVATE)
            val editor=shared.edit()
            editor.putInt("Edit_Restaurant_ID",restaurant.restaurantID)
            editor.putString("Edit_Restaurant_Name",restaurant.restaurantName)
            editor.commit()
        }


    }


}



class MenuAdapter(mCtx: Context,val menu_items : ArrayList<Menu>)
    : RecyclerView.Adapter<MenuAdapter.ViewHolder>()
{
    val mCtx= mCtx
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val txtDishName=itemView.txt_dishName
        val txtDishPrice=itemView.txt_dishPrice
        val txtDishIngredients=itemView.txt_dishIngredients
        val btnDishUpdate=itemView.btnUpdateDish
        val btnDishDelete=itemView.btnDeleteDish


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.lo_menu_items,parent,false)
        return ViewHolder(v)

    }

    override fun getItemCount(): Int {
        return menu_items.size
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: MenuAdapter.ViewHolder, position: Int) {
        val menu_item: Menu= menu_items[position]
        holder.txtDishName.text=menu_item.dishName




        if(menu_item.dishIngredients.isNotEmpty())
        {

            holder.txtDishIngredients.text =mCtx.getString(R.string.ingredients)+" "+menu_item.dishIngredients
        }
        if(menu_item.dishPrice.isNotEmpty())
        {
            holder.txtDishPrice.text=mCtx.getString(R.string.price)+" "+menu_item.dishPrice
        }

        holder.btnDishDelete.setOnClickListener()
        {
            val dishName=menu_item.dishName
            var alertDialog=AlertDialog.Builder(mCtx).setTitle(mCtx.getString(R.string.warning))
                .setMessage(mCtx.getString(R.string.sure_delete)+" "+"$dishName?")
                .setPositiveButton(mCtx.getString(R.string.yes), DialogInterface.OnClickListener { dialogInterface, i ->
                    if(Menu_Restaurant.dbHandler.deleteMenu_Item(menu_item.dishID))
                    {
                        menu_items.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position,menu_items.size)
                        Toast.makeText(mCtx,mCtx.getString(R.string.dish)+" "+"$dishName" +" "+mCtx.getString(R.string.is_deleted_dish),Toast.LENGTH_SHORT ).show();

                    }

                })
                .setNegativeButton(mCtx.getString(R.string.no), DialogInterface.OnClickListener { dialogInterface, i ->  })
                .setIcon(R.drawable.ic_warning)
                .show()


        }
        holder.btnDishUpdate.setOnClickListener()
        {
            val shared=mCtx?.getSharedPreferences("A",Context.MODE_PRIVATE)
            val editor=shared.edit()
            editor.putInt("Edit_Dish_ID",menu_item.dishID)
            editor.putString("Edit_Dish_Name",menu_item.dishName)
                editor.putString("Edit_Dish_Ingredients",menu_item.dishIngredients)
                editor.putString("Edit_Dish_Price",menu_item.dishPrice)



            editor.commit()

            Utils.startActivity(mCtx,Update_Menu::class.java)
        }

    }


}
class Utils {

    companion object {
        fun startActivity(context: Context, clas: Class<*>) {
            val intent = Intent(context, clas)
            context.startActivity(intent)
        }
    }

}