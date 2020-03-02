package com.example.kotlin1

import android.graphics.Bitmap

class Restaurant
{
    var restaurantID : Int=0
    var restaurantName:String=""
    var restaurantCity:String=""
    var restaurantStreet:String=""
    var restaurantStreetNumber:String=""
    var restaurantPhone:String=""
}
class Menu
{
    var dishID: Int=0
    var dishName:String=""
    var dishPrice:String=""
    var dishIngredients:String=""

    var restaurantID: Int=0
}