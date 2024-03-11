package com.example.a20200626

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_petinformation.*

class petinformation : AppCompatActivity() {
    private  var fireDB = FirebaseDatabase.getInstance()
    private var dbRef: DatabaseReference = fireDB.getReference("Pets")
    private var storage= FirebaseStorage.getInstance()
    private var storageReference= storage!!.getReference("Pets")
    var pettype:String=""
    var name:String=""
    var sex:String=""
    var age:String=""
    var species:String=""
    var health :String=""
    var address:String=""
    var neuter:String=""
    var ps :String=""
    var userid:String = ""
    var imageuri:String = ""
    var viewerid:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petinformation)
        var intent: Intent = this.getIntent()
        var bundle: Bundle = intent.getBundleExtra("bundle")
        var id = bundle.getString("id")
        viewerid = bundle.getString("viewer").toString()
        Toast.makeText(this@petinformation,viewerid,Toast.LENGTH_SHORT).show()
        var image = bundle.getString("image")
        var setname:TextView= findViewById(R.id.name)
        var setsex:TextView= findViewById(R.id.sex)
        var setspecies:TextView= findViewById(R.id.type)
        var setage:TextView= findViewById(R.id.age)
        var setaddr:TextView= findViewById(R.id.address)
        var setneu:TextView= findViewById(R.id.sex2)
        var sethealth:TextView= findViewById(R.id.health)
        var setps:TextView= findViewById(R.id.ps)
        dbRef.addValueEventListener(object:ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@petinformation,"Failed to load.Try again",Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    for(item in snapshot.children)
                    {
                        var petid =item.key.toString()
                        if(petid==id) {
                            Toast.makeText(this@petinformation,petid,Toast.LENGTH_SHORT).show()
                            pettype = item.child("type").getValue().toString()
                            name = item.child("name").getValue().toString()
                            sex = item.child("sex").getValue().toString()
                            age = item.child("age").getValue().toString()
                            species = item.child("species").getValue().toString()
                            health = item.child("health").getValue().toString()
                            address = item.child("address").getValue().toString()
                            neuter = item.child("neuter").getValue().toString()
                            ps = item.child("ps").getValue().toString()
                            userid= item.child("userid").getValue().toString()
                        }
                    }
                }
                setname.setText("名字:"+name)
                setaddr.setText("地址:"+address)
                setspecies.setText("品種"+species)
                setage.setText("年齡:"+age)
                setsex.setText("性別:"+sex)
                sethealth.setText("健康情況"+health)
                setps.setText("其他備註:"+ps)
                if(neuter=="0")
                    setneu.setText("無結紮")
                else if(neuter=="1")
                    setneu.setText("已結紮")
                else setneu.setText("未填寫")
                Glide.with(this@petinformation).load(image).into(imageView)
            }

        })
        var back : ImageButton = findViewById(R.id.back)
        back.setOnClickListener {

           var intent:Intent = Intent(this,allpet::class.java)
            var bundle:Bundle = Bundle()
            bundle.putString("type",pettype)//這邊要寫一個找原本type的式子，取data的時候存入,顯示沒有的喔
            intent.putExtra("bundle",bundle)
            startActivity(intent)
        }
        var chat:ImageButton = findViewById(R.id.talk)
        chat.setOnClickListener {
            var intent:Intent = Intent(this,chatroom::class.java)//改成聊天室(小)
            var bundle:Bundle = Bundle()
            bundle.putString("chat",userid)//和哪個user 之後會有大聊天室的介面
            bundle.putString("viewer",viewerid)
            intent.putExtra("bundle",bundle)
            startActivity(intent)
        }
    }
}
