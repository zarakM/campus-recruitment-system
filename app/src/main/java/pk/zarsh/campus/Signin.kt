package pk.zarsh.campus

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import com.google.firebase.auth.AuthResult
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.FileInputStream


class Signin : AppCompatActivity() {
    val firebaseDatabase = FirebaseDatabase.getInstance()
    val refrence = firebaseDatabase.getReference("users")
    var type:String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin)
        /** for firebase admin baad mai karna hai
         val serviceAccount = FileInputStream("./serviceAccountKey.json")
        val options = FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://campus-recruitment-8a151.firebaseio.com")
                .build();
        FirebaseApp.initializeApp(options)
         */

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val signin = findViewById<Button>(R.id.sign_in_button)
        val signup = findViewById<Button>(R.id.sign_up_button)
        val auth = FirebaseAuth.getInstance()

        signup.setOnClickListener{
            val i = Intent(this,Signup::class.java)
            startActivity(i)
        }

        signin.setOnClickListener{
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                        if (task.isSuccessful) {
                            Log.d("signin", "signInWithEmail:success")

                            val user = auth.getCurrentUser()?.uid
                            updateUI(user)
                        } else {
                            Toast.makeText(this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                            //updateUI(null)
                        }
                    })
        }

    }

    private fun updateUI(user: String?) {
        Log.d("signin user",user)
        val query= refrence.child("company").orderByChild("id").equalTo(user.toString())
                .addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext,p0.message.toString(),Toast.LENGTH_LONG)
            }
            override fun onDataChange(data: DataSnapshot) {
                data.children.forEach{
                    val info =it.getValue(company_profile::class.java)
                    val delete=info?.delete.toString()
                    if (delete == "no"){
                    Toast.makeText(applicationContext,"Login as a"+info?.type.toString(),Toast.LENGTH_LONG).show()
                    val i =Intent(applicationContext,MainActCompany::class.java)
                    startActivity(i)
                    }
                    else{
                        Toast.makeText(applicationContext,"your account has been suspened",Toast.LENGTH_LONG).show()
                    }
                    Log.d("thisss","hello")
                }
            }
        })

        val cquery= refrence.child("student").orderByChild("id").equalTo(user.toString()).
                addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext,p0.message.toString(),Toast.LENGTH_LONG)
            }
            override fun onDataChange(data: DataSnapshot) {
                data.children.forEach{
                    val info =it.getValue(company_profile::class.java)
                    val delete=info?.delete.toString()
                    if (delete == "no"){
                        Toast.makeText(applicationContext,info?.type.toString(),Toast.LENGTH_LONG).show()
                        val i =Intent(applicationContext,MainActStudent::class.java)
                        startActivity(i)
                    }
                    else{
                        Toast.makeText(applicationContext,"your account has been suspened",Toast.LENGTH_LONG).show()
                    }
                    Log.d("thiss","hello")
                }
            }
        })
        val auth= FirebaseAuth.getInstance().currentUser?.email
        Log.d("authemail",auth.toString())
        if(auth.toString()=="admin@gmail.com") {
            val i =Intent(this,MainActAdmin::class.java)
            startActivity(i)
        }
    }
}
