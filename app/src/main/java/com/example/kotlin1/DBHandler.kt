package com.example.kotlin1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class DBHandler(context: Context, name : String?, factory: SQLiteDatabase.CursorFactory?,version : Int):
    SQLiteOpenHelper(context,DATABASE_NAME,factory,DATABASE_VERSION)
{
    companion object
    {
        private val DATABASE_NAME="Base.db"
        private val DATABASE_VERSION=2
        val RESTAURANTS_TABLE_NAME="Restaurants"
        val COLUMN_RESTAURANTID="restaurantid"
        val COLUMN_RESTAURANTNAME="rastaurantname"
        val COLUMN_RESTAURANTCITY="restaurantcity"
        val COLUMN_RESTAURANTSTREET="restaurantstreet"
        val COLUMN_RESTAURANTSTREETNUMBER="restaurantstreetnumber"
        val COLUMN_RESTAURANTPHONE="restaurantphone"
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
            Toast.makeText(mCtx,"Records not found",Toast.LENGTH_SHORT).show();

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
            Toast.makeText(mCtx,"${cursor.count.toString()} Records found",Toast.LENGTH_SHORT).show()
        }
    cursor.close()
        db.close()
        return restaurants

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
            Toast.makeText(mCtx,"Restaurant added",Toast.LENGTH_SHORT).show()

        }catch (e : Exception){
            Toast.makeText(mCtx,e.message,Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun deleteRestaurant(restaurantID : Int):Boolean
    {
         val query ="Delete FROM $RESTAURANTS_TABLE_NAME WHERE $COLUMN_RESTAURANTID=$restaurantID"
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

}
