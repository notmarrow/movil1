package com.prototype.app.appcomponents.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.prototype.app.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



class MainActivity : AppCompatActivity() {
    private lateinit var db: itemDatabase
    lateinit var itemList: List<Item>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_historial)

        val buttonAddItem: Button = findViewById(R.id.buttonAddItem)

        // database startup
        db = Room.databaseBuilder(
            applicationContext,
            itemDatabase::class.java,
            "item-database"
        ).build()

        getAllItems()

        buttonAddItem.setOnClickListener {
            val intent = Intent(this, AddItem::class.java)
            startActivity(intent)
        }
    }

    private fun getAllItems() {
        GlobalScope.launch(Dispatchers.IO) {
            itemList = db.iDao().getAll()

            launch(Dispatchers.Main) {
                val recyclerView: RecyclerView = findViewById(R.id.buttonList)
                val adapter = ButtonAdapter(itemList)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }

    }
}