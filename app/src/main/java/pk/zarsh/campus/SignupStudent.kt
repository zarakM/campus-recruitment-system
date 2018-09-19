package pk.zarsh.campus

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.time.Instant
import android.R.attr.data
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import android.R.attr.data
import com.google.firebase.storage.UploadTask
import com.google.android.gms.tasks.OnSuccessListener
import android.support.annotation.NonNull
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth


class SignupStudent : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    var ref = database.getReference("users/student")
    val auth = FirebaseAuth.getInstance()

    var bitmap: Bitmap? = null
    lateinit var image:ByteArray
    lateinit var filePath: Uri
    var storage = FirebaseStorage.getInstance()
    var storageRef = storage.getReferenceFromUrl("gs://campus-recruitment-8a151.appspot.com")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_student)

        val button = findViewById<Button>(R.id.sign_up_button)
        val name =findViewById<EditText>(R.id.name)
        val age =findViewById<EditText>(R.id.age)
        val degree =findViewById<EditText>(R.id.degree)
        val cgpa =findViewById<EditText>(R.id.cgpa)
        val rel_experience =findViewById<EditText>(R.id.experience)
        val upload_pic= findViewById<TextView>(R.id.upload_pic)

        upload_pic.setOnClickListener {
            image_picker()
        }
        button.setOnLongClickListener{
            Log.d("urlpic",storageRef.child("user").downloadUrl.result.toString())
            true
        }

        button.setOnClickListener {
            val userid= auth.currentUser?.uid
            val _name = name.text.toString()
            val _age = age.text.toString()
            val _degree = degree.text.toString()
            val _cgpa=cgpa.text.toString()
            val _rel_experience= rel_experience.text.toString()

            val student = student_profile(userid.toString(),_name, _age, _degree, _cgpa, _rel_experience,"student")
            ref.push().setValue(student)

            val i =Intent(this,MainActStudent::class.java)
            startActivity(i)
        }
    }

    fun image_picker(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
    }
    fun getByteArray(bmp:Bitmap?): ByteArray {
        val baos = ByteArrayOutputStream()
        bmp?.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val imageBytes = baos.toByteArray()
        return imageBytes
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.data != null){
            filePath = data.data

            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filePath)
                bitmap = Bitmap.createScaledBitmap(bitmap, 1000, 500, false)
                image=getByteArray(bitmap)

                val imageRef = storageRef.child(auth.uid.toString())

                imageRef.putFile(filePath).addOnSuccessListener {
                    Toast.makeText(this,"successfull",Toast.LENGTH_LONG).show()
                }.addOnFailureListener{ex->
                    Toast.makeText(this,ex.message.toString(),Toast.LENGTH_LONG).show()
                    Log.d("urisss",ex.message.toString())
                }

            }catch (e: FileNotFoundException){
                Log.d("errororr",e.message)
            }  catch (e: IOException){
                Log.d("erroro",e.message)
            }
        }
    }
}
