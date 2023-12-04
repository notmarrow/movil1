package com.prototype.app.appcomponents.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.prototype.app.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class AddItem : AppCompatActivity() {
    private lateinit var db: itemDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        var ready: Int = 0
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_up_info_one)

        db = Room.databaseBuilder(
            applicationContext,
            itemDatabase::class.java,
            "item-database"
        ).build()

        /*
        *  item tags
        */
        val nameTag : TextView = findViewById(R.id.plainTextName)
        val quantityTag : TextView = findViewById(R.id.plainTextQuantity)
        val descriptionTag : TextView = findViewById(R.id.plainTextDescription)
        /*
        *   item tags end
        */

        val returnButton : Button = findViewById(R.id.buttonReturn)

        returnButton.setOnClickListener {
            ready = 0
            if(nameTag.text == "Name"){
                Toast.makeText(this, "Nombre Invalido", Toast.LENGTH_SHORT).show()
            }else{
                ready++
            }

            if(quantityTag.text.toString().toDoubleOrNull() == null){
                Toast.makeText(this, "Cantidad Invalida", Toast.LENGTH_SHORT).show()
            }else{
                ready++
            }

            if(descriptionTag.text == "Description"){
                Toast.makeText(this, "Descripcion Invalida", Toast.LENGTH_SHORT).show()
            }else{
                ready++
            }

            if(ready >= 3){
                val newItem = Item(
                    name = nameTag.text.toString(),
                    description = descriptionTag.text.toString(),
                    quantity = quantityTag.text.toString().toInt()
                )

                insertItem(newItem)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        }
    }

    private fun insertItem(item: Item){
        try {
            GlobalScope.launch(Dispatchers.IO) {
                db.iDao().insert(item)
            }
        } catch (e: Exception) {
            Log.e("InsertError", "Error inserting item: ${e.message}")
            e.printStackTrace()
        }
    }
}

