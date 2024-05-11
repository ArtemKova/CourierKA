package com.ka.courierka.courier

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.ka.courierka.R
import com.ka.courierka.order.Order

class UsersAdapter: RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private var users = listOf<Order>()
    fun setUsers(items: MutableList<Order>){
        this.users=items
        notifyDataSetChanged()
    }

     var onUserClickListener:OnUserClickListener? = null
//    fun setOnUserClickListener(onUserClickListener:OnUserClickListener){
//        this.onUserClickListener = onUserClickListener
//    }


    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewUserInfo:TextView=itemView.findViewById(R.id.textViewUserInfo)
        var onLineStatus:View=itemView.findViewById(R.id.onLineStatus)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        var view:View = LayoutInflater.from(parent.context).inflate(R.layout.user_itev,parent,false)
        return UserViewHolder(view)

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        var user = users[position]
        var userInfo = String.format("%s %s, %s",user.name, user.phone, user.time)
        holder.textViewUserInfo.text = userInfo
        var bgResId:Int
        if (user.courier==""){
            bgResId = R.drawable.circle_green
        } else {
            bgResId = R.drawable.circle_red
        }
        var background = ContextCompat.getDrawable(holder.itemView.context,bgResId)
        holder.onLineStatus.background = background
        holder.itemView.setOnClickListener{
            onUserClickListener?.onUserClick(user)

        }
    }

    override fun getItemCount(): Int {
       return users.size
    }

//    fun setOnUserClickListener() {
//        this.onUserClickListener = onUserClickListener
//    }

    interface OnUserClickListener{
        fun onUserClick(user:Order)
    }

}