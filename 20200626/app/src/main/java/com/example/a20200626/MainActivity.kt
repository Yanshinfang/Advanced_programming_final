package com.example.a20200626

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "寵物認養平台"
        setContentView(R.layout.activity_main)
        val btn_to_login =
            findViewById<View>(R.id.login) as Button
        btn_to_login.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity, login::class.java)
            startActivity(intent)
        }

        val btn_to_register =
            findViewById<View>(R.id.register) as Button
        btn_to_register.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity, capital::class.java)
            startActivity(intent)
        }
    }
}