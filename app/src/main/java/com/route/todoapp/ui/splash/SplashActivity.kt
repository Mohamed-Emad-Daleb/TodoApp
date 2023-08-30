package com.route.todoapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.route.todoapp.HomeActivity
import com.route.todoapp.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //not use this
        // MyDataBase.getInstance(this)
        // use this
        //MyDataBase.getInstance(applicationContext)
        startHomeActivity()

    }

    private fun startHomeActivity() {
        Handler(Looper.myLooper()!!).postDelayed({
            val intent=Intent(this,HomeActivity::class.java)
            startActivity(intent)
        },2000)
    }
}