package com.example.kotlin1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream


class DBHandler(context: Context, name : String?, factory: SQLiteDatabase.CursorFactory?,version : Int):
    SQLiteOpenHelper(context,DATABASE_NAME,factory,DATABASE_VERSION)
{
    companion object
    {
        private val DATABASE_NAME="Base2.db"
        private val DATABASE_VERSION=6
        val RESTAURANTS_TABLE_NAME="Restaurants"
        val COLUMN_RESTAURANTID="restaurantid"
        val COLUMN_RESTAURANTNAME="rastaurantname"
        val COLUMN_RESTAURANTCITY="restaurantcity"
        val COLUMN_RESTAURANTSTREET="restaurantstreet"
        val COLUMN_RESTAURANTSTREETNUMBER="restaurantstreetnumber"
        val COLUMN_RESTAURANTPHONE="restaurantphone"

        val DISHES_TABLE_NAME="Dishes"
        val COLUMN_DISHID="dishid"
        val COLUMN_DISHNAME="dishname"
        val COLUMN_DISHINGREDIENTS="dishingredients"
        val COLUMN_DISHPRICE="dishprice"
        val COLUMN_DISHIMAGE="dishimage"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_RESTAURANTS_TABLE=("CREATE TABLE $RESTAURANTS_TABLE_NAME " +
                "("+"$COLUMN_RESTAURANTID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "$COLUMN_RESTAURANTNAME TEXT,"+
                "$COLUMN_RESTAURANTCITY TEXT,"+
                "$COLUMN_RESTAURANTSTREET TEXT,"+
                "$COLUMN_RESTAURANTSTREETNUMBER TEXT,"+
                "$COLUMN_RESTAURANTPHONE TEXT)")
        db?.execSQL(CREATE_RESTAURANTS_TABLE)

        val CREATE_DISHES_TABLE=("CREATE TABLE $DISHES_TABLE_NAME " +
                "("+"$COLUMN_DISHID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "$COLUMN_DISHNAME TEXT,"+
                "$COLUMN_DISHINGREDIENTS TEXT,"+
                "$COLUMN_DISHPRICE TEXT,"+
                "$COLUMN_DISHIMAGE BLOB,"+
                "$COLUMN_RESTAURANTID INTEGER)")
        db?.execSQL(CREATE_DISHES_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    fun getRestaurants(mCtx : Context): ArrayList<Restaurant>
    {
        val query="SELECT * FROM $RESTAURANTS_TABLE_NAME"
        val db:SQLiteDatabase=this.readableDatabase
        val cursor:Cursor=db.rawQuery(query,null)
        val restaurants=ArrayList<Restaurant>()
        if(cursor.count==0)
        {
            Toast.makeText(mCtx,mCtx.getString(R.string.restaurant_not_found),Toast.LENGTH_SHORT).show();
        }
        else
        {
            cursor.moveToFirst()
            while(!cursor.isAfterLast())
            {
                val restaurant=Restaurant()
                restaurant.restaurantID=cursor.getInt(cursor.getColumnIndex(COLUMN_RESTAURANTID))
                restaurant.restaurantName=cursor.getString(cursor.getColumnIndex(COLUMN_RESTAURANTNAME))
                if(!cursor.getString(cursor.getColumnIndex(COLUMN_RESTAURANTCITY)).isNullOrEmpty())
                {
                    restaurant.restaurantCity=cursor.getString(cursor.getColumnIndex(COLUMN_RESTAURANTCITY))
                }
                if(!cursor.getString(cursor.getColumnIndex(COLUMN_RESTAURANTSTREET)).isNullOrEmpty())
                {
                    restaurant.restaurantStreet=cursor.getString(cursor.getColumnIndex(COLUMN_RESTAURANTSTREET))
                }
                if(!cursor.getString(cursor.getColumnIndex(COLUMN_RESTAURANTSTREETNUMBER)).isNullOrEmpty())
                {
                    restaurant.restaurantStreetNumber=cursor.getString(cursor.getColumnIndex(COLUMN_RESTAURANTSTREETNUMBER))
                }
                if(!cursor.getString(cursor.getColumnIndex(COLUMN_RESTAURANTPHONE)).isNullOrEmpty())
                {
                    restaurant.restaurantPhone=cursor.getString(cursor.getColumnIndex(COLUMN_RESTAURANTPHONE))
                }
                restaurants.add(restaurant)
                cursor.moveToNext()
            }
            Toast.makeText(mCtx,mCtx.getString(R.string.restaurant_found)+" "+"${cursor.count.toString()}",Toast.LENGTH_SHORT).show()
        }
    cursor.close()
        db.close()
        return restaurants

    }


    fun getMenu_Items(mCtx : Context): ArrayList<Menu>
    {
        val shared=mCtx?.getSharedPreferences("A",Context.MODE_PRIVATE)
        var restaurant_id=shared.getInt("Edit_Restaurant_ID",0)
        val query="SELECT * FROM $DISHES_TABLE_NAME WHERE $COLUMN_RESTAURANTID=$restaurant_id"
        val db:SQLiteDatabase=this.readableDatabase
        val cursor:Cursor=db.rawQuery(query,null)
        val menu_items=ArrayList<Menu>()
        if(cursor.count==0)
        {
            Toast.makeText(mCtx,mCtx.getString(R.string.dish_not_found),Toast.LENGTH_SHORT).show();

        }
        else
        {
            cursor.moveToFirst()
            while(!cursor.isAfterLast())
            {
                val menu_item=Menu()
                menu_item.dishID=cursor.getInt(cursor.getColumnIndex(COLUMN_DISHID))
                menu_item.dishName=cursor.getString(cursor.getColumnIndex(COLUMN_DISHNAME))
                menu_item.restaurantID=cursor.getInt(cursor.getColumnIndex(COLUMN_RESTAURANTID))

                if(!cursor.getString(cursor.getColumnIndex(COLUMN_DISHINGREDIENTS)).isNullOrEmpty())
                {
                    menu_item.dishIngredients=cursor.getString(cursor.getColumnIndex(COLUMN_DISHINGREDIENTS))
                }
                if(!cursor.getString(cursor.getColumnIndex(COLUMN_DISHPRICE)).isNullOrEmpty())
                {
                    menu_item.dishPrice=cursor.getString(cursor.getColumnIndex(COLUMN_DISHPRICE))
                }


                menu_items.add(menu_item)
                cursor.moveToNext()
            }
            Toast.makeText(mCtx,mCtx.getString(R.string.dishes_found)+" "+"${cursor.count.toString()}",Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        db.close()
        return menu_items

    }


    fun addRestaurant(mCtx: Context, restaurant : Restaurant)
    {
        val values=ContentValues();
        values.put(COLUMN_RESTAURANTNAME,restaurant.restaurantName)
        val db = this.writableDatabase
        try
        {
            db.insert(RESTAURANTS_TABLE_NAME,null,values)
            //db.rawQuery("INSERT INTO $RESTAURANTS_TABLE_NAME($COLUMN_RESTAURANTNAME) VALUES('${restaurant.restaurantName}')")
            Toast.makeText(mCtx,mCtx.getString(R.string.restaurant_added),Toast.LENGTH_SHORT).show()

        }catch (e : Exception){
            Toast.makeText(mCtx,e.message,Toast.LENGTH_SHORT).show()
        }
        db.close()
    }
    fun addMenu_Items(mCtx: Context, menu_item : Menu)
    {
        val values=ContentValues();
        values.put(COLUMN_DISHNAME,menu_item.dishName)
        if(menu_item.dishIngredients.isNotEmpty())
        {
            values.put(COLUMN_DISHINGREDIENTS,menu_item.dishIngredients)
        }
        if(menu_item.dishPrice.isNotEmpty())
        {
            values.put(COLUMN_DISHPRICE,menu_item.dishPrice)
        }

        values.put(COLUMN_RESTAURANTID,menu_item.restaurantID)
        val db = this.writableDatabase
        try
        {
            db.insert(DISHES_TABLE_NAME,null,values)
            //db.rawQuery("INSERT INTO $RESTAURANTS_TABLE_NAME($COLUMN_RESTAURANTNAME) VALUES('${restaurant.restaurantName}')")
            Toast.makeText(mCtx,mCtx.getString(R.string.dish_added),Toast.LENGTH_SHORT).show()

        }catch (e : Exception){
            Toast.makeText(mCtx,e.message,Toast.LENGTH_SHORT).show()
        }
        db.close()
    }
    fun deleteRestaurant(restaurantID : Int):Boolean
    {
         val query ="Delete FROM $RESTAURANTS_TABLE_NAME WHERE $COLUMN_RESTAURANTID=$restaurantID"
         val db=this.writableDatabase
         val query2 ="Delete FROM $DISHES_TABLE_NAME WHERE $COLUMN_RESTAURANTID=$restaurantID"
         var result: Boolean=false

        try {
            val cursor=db.execSQL(query)
            val cursor2=db.execSQL(query2)
            result=true

            }catch (e: Exception){
            Log.e(ContentValues.TAG,"Error Deleting")
        }
        db.close()
        return result
    }
    fun deleteMenu_Item(dishID : Int):Boolean
    {
        val query ="Delete FROM $DISHES_TABLE_NAME WHERE $COLUMN_DISHID=$dishID"
        val db=this.writableDatabase
        var result: Boolean=false

        try {
            val cursor=db.execSQL(query)
            result=true

        }catch (e: Exception){
            Log.e(ContentValues.TAG,"Error Deleting")
        }
        db.close()
        return result
    }
    fun updateRestaurant(id : String,restaurantName:String,restaurantStreetNumber: String,restaurantStreet: String,restaurantCity : String,restaurantPhone:String) : Boolean
    {
        val db=this.writableDatabase
        val contentValues=ContentValues()
        var result :Boolean=false
        contentValues.put(COLUMN_RESTAURANTNAME,restaurantName)
        contentValues.put(COLUMN_RESTAURANTCITY,restaurantCity)
        contentValues.put(COLUMN_RESTAURANTSTREET,restaurantStreet)
        contentValues.put(COLUMN_RESTAURANTSTREETNUMBER,restaurantStreetNumber)
        contentValues.put(COLUMN_RESTAURANTPHONE,restaurantPhone)
        try
        {
            db.update(RESTAURANTS_TABLE_NAME,contentValues,"$COLUMN_RESTAURANTID= ?", arrayOf(id))
            result=true


        }catch (e: Exception)
        {
            Log.e(ContentValues.TAG,"Updating error")
            result=false
        }
        return result
    }

    fun updateMenu_Item(id : String,dishName:String,dishIngredients: String,dishPrice: String) : Boolean
    {
        val db=this.writableDatabase
        val contentValues=ContentValues()
        var result :Boolean=false
        contentValues.put(COLUMN_DISHNAME,dishName)
        contentValues.put(COLUMN_DISHINGREDIENTS,dishIngredients)
        contentValues.put(COLUMN_DISHPRICE,dishPrice)
        try
        {
            db.update(DISHES_TABLE_NAME,contentValues,"$COLUMN_DISHID= ?", arrayOf(id))
            result=true


        }catch (e: Exception)
        {
            Log.e(ContentValues.TAG,"Updating error")
            result=false
        }
        return result
    }

}
