package com.example.a20200626

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import nctu.fintech.appmate.Table
import nctu.fintech.appmate.Tuple
import java.io.IOException
import java.util.*

class chatroom : AppCompatActivity() {
    /*
    var b1: Button? = null
    var mTable: Table? = null
    var et1: EditText?=null
    var et2: EditText?=null
    var tv1: TextView?=null
    var s1:String?=""
    */
    var b1: Button? = null
    var mTable: Table? = null
    var et1: EditText?=null
    var tv1:TextView?=null
    var tv2:TextView?=null
    var s1:String?=""
    var s2:String?=""
    var ee:String?=""
    var er:String?=""
    var tv: TextView? = null
    var bundle :Bundle?=null
    var bundlechat :Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatroom)

        bundle = this.intent.extras
        bundlechat = this.intent.getBundleExtra("bundle")
        var result = bundle?.getString("account")
        er = bundlechat!!.getString("chat")
        ee = bundlechat!!.getString("viewer")
        tv= findViewById(R.id.tv)
        if(result!=null) {
            tv!!.setText("")
            ee = result.toString()
        }
        /*
        mTable = Table( "http://140.113.72.35:8000/api", "class10", "admin", "admin123")
        b1 = findViewById(R.id.B1)
        b1?.setOnClickListener(b1cl)
        et1=findViewById(R.id.ET1)
        et2=findViewById(R.id.ET2)
        tv1=findViewById((R.id.TV1))
        val timer01 = Timer()
        timer01.schedule(task, 0, 3000)*/
        mTable = Table( "http://140.113.72.35:8000/api", "class10", "admin", "admin123")
        b1 = findViewById(R.id.B1)
        b1?.setOnClickListener(b1cl)
        et1=findViewById(R.id.ET1)
        tv1=findViewById((R.id.TV1))
        tv2=findViewById((R.id.TV2))
        val timer01 = Timer()
        timer01.schedule(task, 0, 3000)
    }
    private val b1cl =
        View.OnClickListener {
            val t1 = Thread(r1)
            t1.start()
        }
    private val r1 = Runnable {
        val tuple_add = Tuple()
        val word2send1: String = et1?.getText().toString()
        val word2send2: String? = ee
        val word2send3: String? = er
        tuple_add.put("text1", word2send1)
        tuple_add.put("text2", word2send2)
        tuple_add.put("text3", word2send3)
        tuple_add.put("text4","message")
        et1?.setText("")
        try {
            mTable!!.add(tuple_add)
        } catch (e: IOException) {
            Log.e("Net", "Fail to put")
        }
    }

    private val r2 = Runnable {
        try {
            var tuple_get = mTable!!.get()
            s1  = ""
            s2  = ""
            for (i in 0 until tuple_get.size) {
                if(tuple_get.get(i).get("text4")== "message"){
                if(tuple_get.get(i).get("text2")== ee&&tuple_get.get(i).get("text3")== er) {
                    var tempString: String = tuple_get.get(i).get("text1").toString()
                    if (tempString!=null) {
                            s2 = s2.toString() + tempString + "\n"
                            s1 = s1.toString() + "\n"
                        }
                }else if(tuple_get.get(i).get("text3")== ee&&tuple_get.get(i).get("text2")== er) {
                    var tempString: String = tuple_get.get(i).get("text1").toString()
                    if (tempString != null) {
                            s1 = s1.toString() + tempString + "\n"
                            s2 = s2.toString() + "\n"
                        }
                    }
                }
            }
            val msg = Message()
            msg.what = 1
            mHandler.sendMessage(msg)
        } catch (e: IOException) {
            Log.e("Net", "Fail to get")
        }
    }
    val task: TimerTask = object : TimerTask() {
        override fun run() {
            val t2 = Thread(r2)
            t2.start()
        }
    }
    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            if (msg?.what == 1) {
                if(s1==null)
                {
                    tv1?.setText("對方還沒開始傳訊息喔!")

                }
                if(s2==null)
                {
                    tv2?.setText("您還沒開始傳訊息喔")
                }
                tv1?.setText("Users "+er+" :"+s1)
                tv2?.setText("Users "+ee+" :"+s2)
            }

            super.handleMessage(msg)
        }
    }
}
