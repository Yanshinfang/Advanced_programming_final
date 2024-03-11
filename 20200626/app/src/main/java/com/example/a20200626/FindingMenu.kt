package com.example.a20200626

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button


class FindingMenu : AppCompatActivity() {
    var userid:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finding_menu)
        var intent1= this.intent.getBundleExtra("bundle")
        userid = intent1.getString("id")

        var dogbutton:Button= findViewById(R.id.dog_button)
        dogbutton.setOnClickListener {
            var intent: Intent = Intent(this@FindingMenu,allpet::class.java)
            var bundle:Bundle = Bundle()
            bundle.putString("type","狗")
            bundle.putString("id",userid)
            intent.putExtra("bundle",bundle)
            startActivity(intent)
        }
        var catbutton:Button= findViewById(R.id.cat_button)
        catbutton.setOnClickListener {
            var intent: Intent = Intent(this@FindingMenu,allpet::class.java)
            var bundle:Bundle = Bundle()
            bundle.putString("type","貓")
            bundle.putString("id",userid)
            intent.putExtra("bundle",bundle)
            startActivity(intent)
        }
        var rabbitbutton:Button= findViewById(R.id.rabbit_button)
        rabbitbutton.setOnClickListener {
            var intent: Intent = Intent(this@FindingMenu,allpet::class.java)
            var bundle:Bundle = Bundle()
            bundle.putString("type","兔")
            bundle.putString("id",userid)
            intent.putExtra("bundle",bundle)
            startActivity(intent)
        }
        var fishbutton:Button= findViewById(R.id.fish_button)
        fishbutton.setOnClickListener {
            var intent: Intent = Intent(this@FindingMenu,allpet::class.java)
            var bundle:Bundle = Bundle()
            bundle.putString("type","魚")
            bundle.putString("id",userid)
            intent.putExtra("bundle",bundle)
            startActivity(intent)
        }
        var mousebutton:Button= findViewById(R.id.mouse_button)
        mousebutton.setOnClickListener {
            var intent: Intent = Intent(this@FindingMenu,allpet::class.java)
            var bundle:Bundle = Bundle()
            bundle.putString("type","鼠")
            bundle.putString("id",userid)
            intent.putExtra("bundle",bundle)
            startActivity(intent)
        }
        var otherbutton:Button= findViewById(R.id.other_button)
        otherbutton.setOnClickListener {
            var intent: Intent = Intent(this@FindingMenu,allpet::class.java)
            var bundle:Bundle = Bundle()
            bundle.putString("type","其他")
            bundle.putString("id",userid)
            intent.putExtra("bundle",bundle)
            startActivity(intent)
        }

    }
}