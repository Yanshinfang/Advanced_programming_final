package com.example.a20200626

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import nctu.fintech.appmate.Table
import nctu.fintech.appmate.Tuple
import java.io.IOException

class register : AppCompatActivity() {
    var b1: Button? = null
    var mTable: Table? = null
    var et1: EditText? = null
    var et2: EditText? = null
    var s1 : String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //var intent: Intent = this.getIntent()

        title = "register"
        var intent: Intent = this.getIntent()
        title = "register"
        mTable = Table( "http://140.113.72.35:8000/api", "class10", "admin", "admin123")
        b1 = findViewById(R.id.signup_button)
        b1?.setOnClickListener(b1cl)
        et1=findViewById(R.id.account)
        et2=findViewById(R.id.password)
    }
    private val b1cl =
        View.OnClickListener {
            val t1 = Thread(r1)
            t1.start()
        }
    private val r1 = Runnable {
        val tuple_add = Tuple()
        val account: String = et1?.getText().toString()
        val password: String = et2?.getText().toString()
        tuple_add.put("text1", account)
        tuple_add.put("text2",password)
        try {
            mTable!!.add(tuple_add)
        } catch (e: IOException) {
            Log.e("Net", "Fail to put")
        }

        b1?.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@register, doggie::class.java)
            val bundle = Bundle()
            bundle.putString("account", account.toString())
            bundle.putString("password", password.toString())
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }
}
