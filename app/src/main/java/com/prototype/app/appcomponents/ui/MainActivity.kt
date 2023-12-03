package com.prototype.app.appcomponents.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.prototype.app.R

class MainActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT : Long = 3000

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_historial)

    }
}