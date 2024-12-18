package com.example.data.data

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [OrderItemDBModel::class], version = 1, exportSchema = false)
abstract class AppDataBase:RoomDatabase() {

    abstract fun orderListDao():OrderListDao

    companion object{
        private var INSTANCE: AppDataBase? = null
        private val LOCK = Any()
        private const val DB_NAME = "order_item.db"

        fun getInstance (context: Context):AppDataBase{
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK){
                INSTANCE?.let {
                    return it
                }
            }
            val db = Room.databaseBuilder(
                context,
                AppDataBase::class.java,
                DB_NAME
            )
//                .allowMainThreadQueries()
                .build()
            INSTANCE=db
            return db
        }
    }
}