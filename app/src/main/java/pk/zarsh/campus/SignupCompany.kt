package pk.zarsh.campus

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.IOException
import kotlin.math.sign


class SignupCompany : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    var ref = database.getReference("users/company")
    val auth = FirebaseAuth.getInstance()
    var bitmap: Bitmap? = null
    lateinit var image:ByteArray
    lateinit var filePath: Uri

    var storage = FirebaseStorage.getInstance()
    var storageRef = storage.getReferenceFromUrl("gs://campus-recruitment-8a151.appspot.com")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_company)

        val signup = findViewById<Button>(R.id.sign_up_button)
        val name = findViewById<EditText>(R.id.name)
        val founded = findViewById<EditText>(R.id.founded)
        val employer = findViewById<EditText>(R.id.employers)
        val address = findViewById<EditText>(R.id.address)

        val upload_pic= findViewById<TextView>(R.id.upload_pic)

        upload_pic.setOnClickListener {
            image_picker()
        }

        signup.setOnClickListener {
            val userId = auth.currentUser?.uid
            val _name = name.text.toString()
            val _founded = founded.text.toString()
            val _employer = employer.text.toString()
            val _address = address.text.toString()
            ref.push().setValue(company_profile(userId.toString(),_name, _founded, _employer, _address,"company"))

            val i = Intent(this,MainActCompany::class.java)
            startActivity(i)
        }
    }

    fun image_picker(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
    }
    fun getByteArray(bmp: Bitmap?): ByteArray {
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
                    Toast.makeText(this,"successfull", Toast.LENGTH_LONG).show()
                }.addOnFailureListener{ex->
                    Toast.makeText(this,ex.message.toString(), Toast.LENGTH_LONG).show()
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
