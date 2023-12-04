package com.prototype.app.appcomponents.ui

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Entity(tableName="items")
data class Item(
    @PrimaryKey(autoGenerate = true) val itemId: Int = 0,
    val name: String,
    val description: String,
    val quantity: Int
)

@Database(entities = [Item::class], version = 1)
abstract class itemDatabase : RoomDatabase(){
    abstract fun iDao(): itemDAO
}

@Dao
interface itemDAO {
    @Query("SELECT * FROM items")
    fun getAll(): List<Item>

    @Query("SELECT * FROM items WHERE itemId = :id")
    fun getItemById(id: Int): Item

    @Insert
    fun insert(item: Item)

    @Query("UPDATE items SET quantity = :newQuantity WHERE itemId = :itemId")
    fun updateQuantity(itemId: Int, newQuantity: Int)

    @Delete
    fun delete(item: Item)
}