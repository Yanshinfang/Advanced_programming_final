package com.example.a20200626

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import nctu.fintech.appmate.Table
import java.io.IOException

class chatmain : AppCompatActivity() {
    val chatlist = ArrayList<Chatuser>()
    val otherlist = ArrayList<String>()
    var intent1: Intent? = null
    var account: String? = null
    var mTable: Table? = null
    var other: String? = null
    var search: ImageButton? = null
    var send: ImageButton? = null
    var text: EditText? = null
    var chater: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatmain)
        intent1 = this.intent
        account = intent1!!.extras!!.getString("account")
        //Toast.makeText(this,account,Toast.LENGTH_SHORT).show()
        mTable = Table("http://140.113.72.35:8000/api", "class10", "admin", "admin123")
        val layoutManager = LinearLayoutManager(this)
        val dataList = findViewById<RecyclerView>(R.id.hello)
        dataList.layoutManager = layoutManager
        text = findViewById(R.id.editchater)
        chatlist.add(Chatuser("1234",account.toString()))
        search = findViewById(R.id.imageButton4)
        search!!.setOnClickListener {
            (b1cl)
            Toast.makeText(this, "搜尋完成", Toast.LENGTH_SHORT).show()
            dataList.adapter = ChatAdapter(chatlist)
        }
        send = findViewById(R.id.imageButton2)
        send!!.setOnClickListener {
            chater = text!!.getText().toString()
            if (chater != null) {
                var intent: Intent = Intent(this, chatroom::class.java)
                var bundle = Bundle()
                bundle.putString("viewer", account)
                bundle.putString("chat", chater)
                intent.putExtra("bundle", bundle)
                //Toast.makeText(this,chater,Toast.LENGTH_SHORT).show()
                chatlist.add(Chatuser(chater.toString(),account.toString()))
                startActivity(intent)
            } else Toast.makeText(this, "請輸入收信者帳號", Toast.LENGTH_SHORT).show()
        }
    }

    private val b1cl =
        View.OnClickListener {
            val t1 = Thread(r2)
            t1.start()
        }
    private val r2 = Runnable {
        try {
            var tuple_get = mTable!!.get()
            for (i in 0 until tuple_get.size) {
                if (tuple_get.get(i).get("text4") == "message" ) {
                    if(tuple_get.get(i).get("text2") == account){
                        other = tuple_get.get(i).get("text3")
                        val check = otherlist.contains(other!!)
                            if (check == false) {
                                val user = Chatuser(other.toString(), account.toString())
                                otherlist.add(other.toString())
                                chatlist.add(user)
                                Toast.makeText(this, other, Toast.LENGTH_SHORT).show()
                            }

                        }
                    else if (tuple_get.get(i).get("text3") == account){
                        other = tuple_get.get(i).get("text2")
                        val check = otherlist.contains(other!!)
                            if (check == false) {
                                val user = Chatuser(other.toString(), account.toString())
                                otherlist.add(other.toString())
                                chatlist.add(user)
                                Toast.makeText(this, other, Toast.LENGTH_SHORT).show()
                            }
                        }

                }}} catch (e: IOException) {
                    Log.e("Net", "Fail to get")
                }
        }
    }



    class ChatAdapter(private val mData: List<Chatuser>) : RecyclerView.Adapter<ViewHolder1>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder1 {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_cell, parent, false)
            return ViewHolder1(v)
        }

        override fun onBindViewHolder(holder: ViewHolder1, position: Int) {
            holder.user.text = "User" + mData[position].user1

            holder.itemView.setOnClickListener {
                /*Toast.makeText(
                    it.context,
                    "User: " + mData[position].user2 + "is clicked",
                    Toast.LENGTH_SHORT
                ).show()*/
                val context = holder.user.context
                var intent: Intent = Intent(context, chatroom::class.java)
                var bundle: Bundle = Bundle()
                bundle.putString("viewer", mData[position].user2)
                bundle.putString("chat", mData[position].user1)
                intent.putExtra("bundle", bundle)
                context.startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return mData.size
        }
    }

    class ViewHolder1(v: View) : RecyclerView.ViewHolder(v) {
        val user: TextView = v.findViewById(R.id.textView13)
    }

data class Chatuser(var user1:String,var user2:String){

}
