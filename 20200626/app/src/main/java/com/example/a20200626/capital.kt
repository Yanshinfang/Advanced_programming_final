package com.example.a20200626

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class capital : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capital)

        title = "register"
        val btn_to_np =
            findViewById<View>(R.id.npbutton) as Button
        btn_to_np.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@capital, register::class.java)
            startActivity(intent)
        }
    }
}

