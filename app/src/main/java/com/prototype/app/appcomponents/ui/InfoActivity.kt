package com.prototype.app.appcomponents.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.prototype.app.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class InfoActivity : AppCompatActivity() {
    private lateinit var db: itemDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_up_info)

        /*
        *   text tags
        * */
        val idText: TextView = findViewById(R.id.txtZipcode)
        val nameText: TextView = findViewById(R.id.txtContenido)
        val quantityText: TextView = findViewById(R.id.txtContenidoOne)
        val descriptionText: TextView = findViewById(R.id.txtDescription)
        /*
        *   end text tags
        * */


        val itemId = intent.getIntExtra("itemId", 1)
        var quantity : Int = 0
        var initialized : Boolean = false

        db = Room.databaseBuilder(
            applicationContext,
            itemDatabase::class.java,
            "item-database"
        ).fallbackToDestructiveMigration().build()

        GlobalScope.launch(Dispatchers.IO) {
            val item = db.iDao().getItemById(itemId)
            withContext(Dispatchers.Main) {
                if (item != null) {
                    quantity = item.quantity
                    idText.text = item.itemId.toString()
                    nameText.text = item.name
                    quantityText.text = quantity.toString()
                    descriptionText.text = item.description

                    initialized = true
                } else {
                    // Handle when item is null
                }
            }
        }

        // button logic

        val buttonPlus: Button = findViewById(R.id.buttonPlus)
        val buttonMinus: Button = findViewById(R.id.buttonLess)
        val buttonReturn: Button = findViewById(R.id.buttonExit)

        buttonPlus.setOnClickListener{
            if(initialized){
                quantity += 1
                quantityText.text = quantity.toString()
            }
        }

        buttonMinus.setOnClickListener{
            if(initialized && quantity > 0){
                quantity -= 1
                quantityText.text = quantity.toString()
            }
        }

        buttonReturn.setOnClickListener {
            updateItemQuantity(itemId, quantity)
            val intent = Intent(this@InfoActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun updateItemQuantity(itemIdToUpdate: Int, newQuantityValue: Int) {
        try {
            GlobalScope.launch(Dispatchers.IO) {
                db.iDao().updateQuantity(itemIdToUpdate, newQuantityValue)
            }
        } catch (e: Exception) {
            // Handle exceptions if any
        }
    }
}
