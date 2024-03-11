package com.example.a20200626

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import nctu.fintech.appmate.Table

class login : AppCompatActivity() {
    var b1: Button? = null
    var mTable: Table? = null
    var et1: EditText? = null
    var et2: EditText? = null
    var s1 : String? = ""


    @SuppressLint("HandlerLeak")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var intent: Intent = this.getIntent()
        title = "login"
        mTable = Table( "http://140.113.72.35:8000/api", "class10",
            "admin", "admin123")
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
        val tuple_get = mTable!!.get()
        //for (i in 0 until tuple_get.size) {
        //var temptaccount: String ?= tuple_get.get(i).get("text1")
        //var temptpassword: String ?= tuple_get.get(i).get("text2")

        b1?.setOnClickListener {
            val account: String = et1?.getText().toString()
            val password: String = et2?.getText().toString()

            for (i in 0 until tuple_get.size) {
                var temptaccount: String ?= tuple_get.get(i).get("text1")
                var temptpassword: String ?= tuple_get.get(i).get("text2")

                if (account == temptaccount) {
                    if(password == temptpassword) {
                        val intent = Intent()
                        intent.setClass(this@login, doggie::class.java)

                        val bundle = Bundle()
                        bundle.putString("account", account.toString())
                        bundle.putString("password", password.toString())
                        intent.putExtras(bundle)

                        startActivity(intent)
                    }
                    else if(password != temptpassword){
                        //密碼錯誤
                        //Toast.makeText(this,"wrong password",Toast.LENGTH_LONG).show()
                    }

                }

                //Toast.makeText(this,"success",Toast.LENGTH_LONG).show()
            }
            //toast查無此帳戶
            //Toast.makeText(this,"can't find account",Toast.LENGTH_LONG).show()


        }
    }
}
