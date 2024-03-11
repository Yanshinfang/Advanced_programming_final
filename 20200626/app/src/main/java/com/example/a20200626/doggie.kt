package com.example.a20200626

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.*

class doggie : AppCompatActivity() {
    var a:String? = null
    var b:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doggie)
        val bundle = this.intent.extras
        if (bundle != null) {
            val result = bundle.getString("account")
            val result2 = bundle.getString("password")
            var tv9: TextView = findViewById(R.id.tv)
            tv9.setText("歡迎User: $result 回來")

            a = result
            b = result2

            var btn_to_publish =
                findViewById<View>(R.id.publish) as Button
            btn_to_publish.setOnClickListener {
                val intent = Intent()
                intent.setClass(this@doggie, Uploading::class.java)

                val bundle = Bundle()
                bundle.putString("account", a.toString())
                intent.putExtras(bundle)

                startActivity(intent)
            }

            val btn_to_chatroom =
                findViewById<View>(R.id.chatroom) as Button
            btn_to_chatroom.setOnClickListener {
                val intent = Intent()
                intent.setClass(this@doggie, chatmain::class.java)

                val bundle = Bundle()
                bundle.putString("account", a.toString())
                intent.putExtras(bundle)
                startActivity(intent)
            }

        }
        else {
            val btn_to_publish =
                findViewById<View>(R.id.publish) as Button
            btn_to_publish.setOnClickListener {
                val intent = Intent()
                intent.setClass(this@doggie, Uploading::class.java)
                var bundle: Bundle = Bundle()
                bundle.putString("id", a.toString())
                intent.putExtra("account", bundle)
                startActivity(intent)
            }

            val btn_to_chatroom =
                findViewById<View>(R.id.chatroom) as Button
            btn_to_chatroom.setOnClickListener {
                val intent = Intent()
                intent.setClass(this@doggie, chatmain::class.java)
                startActivity(intent)
            }
        }
        val btn_to_hospital =
            findViewById<View>(R.id.hospital) as Button
        btn_to_hospital.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@doggie, hospital::class.java)
            startActivity(intent)
        }

        val btn_to_view =
            findViewById<View>(R.id.view) as Button
        btn_to_view.setOnClickListener {
            val intent = Intent(this@doggie,FindingMenu::class.java)
            var bundle:Bundle = Bundle()
            bundle.putString("id",a)
            intent.putExtra("bundle",bundle)
            startActivity(intent)
        }

    }
}
