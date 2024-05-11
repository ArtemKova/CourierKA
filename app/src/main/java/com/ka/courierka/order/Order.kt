package com.ka.courierka.order

class Order  (
    var id:String="",
    var name:String="",
    var phone:String="",
    var adress:String="",
    var recadress:String="",
    var customer_id:String="",
    var time:String="",
    var isPay:String="",
    var courier: String="",
    var city:String="",
    var typeOrder:String="",
    var delivered:Boolean=false)
{
    override fun toString(): String {
        return "Order(id='$id', name='$name', phone = '$phone', adress='$adress', recadress='$recadress',customer_id='$customer_id' time='$time', isOnLine='$isPay', courier='$courier', city='$city', typeOrder='$typeOrder', delivered='$delivered')"
    }
}