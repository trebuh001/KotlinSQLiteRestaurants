package com.example.kotlin1

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
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

            holder.txtRestaurantAddress.text="Address: ${restaurant.restaurantStreetNumber} ${restaurant.restaurantStreet}\n${restaurant.restaurantCity}"

        if(restaurant.restaurantPhone.isNotEmpty())
        {
            holder.txtRestaurantPhone.text="Phone: \n${restaurant.restaurantPhone}"
        }

        holder.btnDelete.setOnClickListener()
        {
            val restaurantName=restaurant.restaurantName
            var alertDialog=AlertDialog.Builder(mCtx).setTitle("Warning")
                .setMessage("Are You sure to delete: $restaurantName?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                        if(Restaurant_List.dbHandler.deleteRestaurant(restaurant.restaurantID))
                        {
                            restaurants.removeAt(position)
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position,restaurants.size)
                            Toast.makeText(mCtx,"Restaurant: $restaurantName is deleted",Toast.LENGTH_SHORT ).show();
                        }

                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->  })
                .setIcon(R.drawable.ic_warning)
                .show()


        }
        holder.btnUpdate.setOnClickListener()
        {
            val shared=mCtx?.getSharedPreferences("A",Context.MODE_PRIVATE)
            val editor=shared.edit()
            editor.putInt("Edit_Restaurant_ID",restaurant.restaurantID)
            editor.putString("Edit_Restaurant_Name",restaurant.restaurantName)
            if(restaurant.restaurantCity.isNotEmpty())
            {
                editor.putString("Edit_Restaurant_City",restaurant.restaurantCity)
            }
            if(restaurant.restaurantStreet.isNotEmpty())
            {
                editor.putString("Edit_Restaurant_Street",restaurant.restaurantStreet)
            }
            if(restaurant.restaurantStreetNumber.toString().isNotEmpty())
            {
                editor.putString("Edit_Restaurant_StreetNumber",restaurant.restaurantStreetNumber)
            }
            if(restaurant.restaurantPhone.isNotEmpty())
            {
                editor.putString("Edit_Restaurant_Phone",restaurant.restaurantPhone)
            }

            editor.commit()

            Utils.startActivity(mCtx,Update_restaurant::class.java)
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