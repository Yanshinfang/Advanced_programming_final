package com.example.a20200626
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_uploading.*
import java.io.IOException
import java.util.*


class Uploading : AppCompatActivity(), View.OnClickListener {

    private val PICK_IMAGE_REQUEST=1234
    private  var fireDB = FirebaseDatabase.getInstance()
    private var dbRef: DatabaseReference = fireDB.getReference("Pets")
    private var storage=FirebaseStorage.getInstance()
    private var storageReference= storage!!.getReference("Pets")
    var b1: Button? = null
    var et1: EditText? = null
    var et4: EditText? = null
    var et5: EditText? = null
    var et2: EditText? = null
    var et7: EditText? = null
    var et8: EditText? = null
    var petuid:String? = null
    var neuter:String = ""
    var type:String = ""
    var sex:String = ""
    var intent1:Bundle?=null
    var userid:String =""
    var name:String=""
    var age:String=""
    var species:String =""
    var addr:String =""
    var health:String =""
    var ps:String =""
    var check = 0
    var imageurl:String? = null
    private var filePath: Uri?=null


    override fun onClick(p0: View?) {
        if(p0===btnChoose)
            showFileChooser()
        else if(p0===btnUpload)
            uploadFile()
    }

    override fun onActivityResult(requestCode:Int,resultCode:Int,data:Intent?){
        super.onActivityResult(requestCode,resultCode,data)
        if(requestCode==PICK_IMAGE_REQUEST&&resultCode== Activity.RESULT_OK&&data!=null&&data.data!=null)
        {
            filePath=data.data
            try {
                val bitmap= MediaStore.Images.Media.getBitmap(contentResolver,filePath)
                imageView!!.setImageBitmap(bitmap)
            }catch (e:IOException)
            {
                e.printStackTrace()
            }
        }
    }

    private fun uploadFile() {
        if (filePath!=null)
        {
            val progressDialog=ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()
            petuid = UUID.randomUUID().toString()
            val imageRef=storageReference!!.child("image/"+ petuid)
            var uploadTask :StorageTask<*>
            uploadTask = imageRef.putFile(filePath!!)
            uploadTask.continueWithTask(Continuation <UploadTask.TaskSnapshot, Task<Uri>>{task->
                if(!task.isSuccessful)
                {
                    task.exception?.let{
                        throw it
                    }
                }
                return@Continuation imageRef.downloadUrl
            }).addOnCompleteListener{task ->
                if(task.isSuccessful)
                {
                    val downloadurl = task.result
                    imageurl = downloadurl.toString()
                }
            }
            imageRef.putFile(filePath!!)
                .addOnSuccessListener{
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext,"File Uploaded",Toast.LENGTH_SHORT).show()
                    check = 1
                }
                .addOnFailureListener {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener{taskSnapShot->
                    val progress=100.0*taskSnapShot.bytesTransferred/taskSnapShot.totalByteCount
                    progressDialog.setMessage("Uploaded"+progress.toInt()+"%...")
                }

        }
    }

    private fun showFileChooser() {
        val intent= Intent()
        intent.type="image/*"
        intent.action=Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"SELECT PICTURE"),PICK_IMAGE_REQUEST)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uploading)
        intent1=this.intent.extras
        userid = intent1!!.getString("account").toString()
        //Toast.makeText(this,userid,Toast.LENGTH_SHORT).show()
        val animal = arrayListOf("狗", "貓", "鼠", "魚", "兔","其他")
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, animal)
        spinner1.adapter = adapter1
        spinner1.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                type= animal[pos]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        val gender = arrayListOf("公", "母")
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, gender)
        spinner2.adapter = adapter2
        spinner2.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                sex=gender[pos]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        val knot = arrayListOf("是", "否")
        val adapter3 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, knot)
        spinner3.adapter = adapter3
        spinner3.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {

                if(animal[pos]=="是")
                    neuter ="1"
                else neuter ="0"
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        b1 = findViewById(R.id.button)
        //b1?.setOnClickListener(b1cl)
        et1 = findViewById(R.id.editText)
        et4 = findViewById(R.id.editText4)
        et5 = findViewById(R.id.editText5)
        et2 = findViewById(R.id.editText2)
        et7 = findViewById(R.id.editText7)
        et8 = findViewById(R.id.editText8)


        btnChoose.setOnClickListener(this)
        btnUpload.setOnClickListener(this)

        b1?.setOnClickListener{
            if(check == 0)
                Toast.makeText(this,"請上傳圖片",Toast.LENGTH_SHORT).show()
            else {
                name = et1!!.text.toString()
                age = et5!!.text.toString()
                species = et4!!.text.toString()
                addr = et2!!.text.toString()
                health = et7?.text.toString()
                ps = et8?.text.toString()
                dbRef.child(petuid.toString()).child("name").setValue(name)
                dbRef.child(petuid.toString()).child("type").setValue(type)
                dbRef.child(petuid.toString()).child("sex").setValue(sex)
                dbRef.child(petuid.toString()).child("age").setValue(age)
                dbRef.child(petuid.toString()).child("userid").setValue(userid)
                dbRef.child(petuid.toString()).child("species").setValue(species)
                dbRef.child(petuid.toString()).child("health").setValue(health)
                dbRef.child(petuid.toString()).child("address").setValue(addr)
                dbRef.child(petuid.toString()).child("neuter").setValue(neuter)
                dbRef.child(petuid.toString()).child("ps").setValue(ps)
                dbRef.child(petuid.toString()).child("imageurl").setValue(imageurl)
                Toast.makeText(this@Uploading, "上傳成功", Toast.LENGTH_SHORT).show()
                var intent: Intent = Intent(this@Uploading, doggie::class.java)
                var bundle : Bundle = Bundle()
                bundle.putString("account",userid)
                intent.putExtra("bundle",bundle)
                startActivity(intent)
            }
        }
    }

}