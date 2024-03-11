package com.example.a20200626

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.view_cell.view.*
import nctu.fintech.appmate.Table
import java.util.*
import kotlin.collections.ArrayList

class allpet : AppCompatActivity() {
    private  var fireDB = FirebaseDatabase.getInstance()
    private var dbRef: DatabaseReference = fireDB.getReference("Pets")
    var type:String? =null
    var viewerid : String? = null
    val listData = ArrayList<Pet>()
    val listName = ArrayList<String>()
    private fun getdata(type:String?){
        dbRef.addValueEventListener(object:ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@allpet,"Failed to load.Try again",Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Toast.makeText(this@allpet,"請稍候，資料搜尋中",Toast.LENGTH_SHORT).show()
                var count = 0
                if(snapshot.exists())
                {
                    for(item in snapshot.children)
                    {
                        var petid =item.key.toString()
                        var pettype = item.child("type").getValue().toString()
                        var name = item.child("name").getValue().toString()
                        var sex = item.child("sex").getValue().toString()
                        var age = item.child("age").getValue().toString()
                        var species = item.child("species").getValue().toString()
                        var health = item.child("health").getValue().toString()
                        var address = item.child("address").getValue().toString()
                        var neuter = item.child("neuter").getValue().toString()
                        var ps = item.child("ps").getValue().toString()
                        var image = item.child("imageurl").getValue().toString()
                        val user = Pet(petid,name,pettype,sex,age,species,health,address,neuter,ps,image,viewerid.toString())
                        if(pettype==type)
                        {
                            listData.add(user)
                            listName.add(user.name)
                            count++
                        }
                    }
                }
                if(count==0)
                    Toast.makeText(this@allpet,"目前沒有你想要的資料喔",Toast.LENGTH_SHORT).show()
                else Toast.makeText(this@allpet,"已搜尋到"+count+"筆資料，請更新",Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allpet)
        var intent: Intent = this.getIntent()
        var bundle: Bundle = intent.getBundleExtra("bundle")
        type = bundle.getString("type")
        viewerid = bundle.getString("id")
        var s1: String? = null
        when (type) {
            "狗" -> s1 = "狗"
            "貓" -> s1 = "貓"
            "兔" -> s1 = "兔"
            "魚" -> s1 = "魚"
            "老鼠" -> s1 = "老鼠"
            else -> s1 = "其他"
        }
        var back: Button = findViewById(R.id.button)
        var search: Button = findViewById(R.id.button2)
        var text: TextView = findViewById(R.id.textView)
        text.setText("目錄>>領養>>分類清單>>" + s1)
        getdata(type)
        back.setOnClickListener {
            var intent: Intent = Intent(this, FindingMenu::class.java)
            startActivity(intent)
        }
        val layoutManager = GridLayoutManager(this, 2)
        val dataList = findViewById<RecyclerView>(R.id.recycleview)
        dataList.layoutManager = layoutManager
        search.setOnClickListener {
            dataList.adapter = DataAdapter(listData)
        }
    }

}

class DataAdapter(private val mData: List<Pet>) : RecyclerView.Adapter<ViewHolder>() {
    private var storage= FirebaseStorage.getInstance()
    private var storageReference= storage!!.getReference("Pets")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_cell, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        holder.dataView.text = mData[position].name
        holder.name.text = "寵物品種: "+mData[position].species
        val context = holder.dataView.context
        Glide.with(context)
            .load(mData[position].image)
            .into(holder.petimage)
        holder.itemView.setOnClickListener {
            Toast.makeText(it.context, mData[position].name+" is clicked", Toast.LENGTH_SHORT).show()
            val context = holder.dataView.context
            var intent:Intent = Intent(context,petinformation::class.java)
            var bundle:Bundle = Bundle()
            bundle.putString("id",mData[position].petid)
            bundle.putString("viewer",mData[position].viewerid)
            //Toast.makeText(it.context, mData[position].viewerid+" is clicked", Toast.LENGTH_SHORT).show()
            bundle.putString("image",mData[position].image)
            intent.putExtra("bundle",bundle)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}
class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val dataView: TextView = v.findViewById(R.id.info_text1)
    val name: TextView = v.findViewById(R.id.info_text2)
    val petimage:ImageView = v.findViewById(R.id.image)
}
data class Pet(var petid:String,var name:String,var type:String,var sex:String,var age:String,var species:String,var health:String,var address:String,var neuter :String,var ps:String,var image:String,var viewerid:String){

}