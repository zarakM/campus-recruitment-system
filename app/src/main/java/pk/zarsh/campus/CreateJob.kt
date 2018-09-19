package pk.zarsh.campus

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.view.*

class CreateJob : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    var comRef = database.getReference("users/company")
    var jobRef = database.getReference("jobs")
    var _company: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_job)

        val create= findViewById<Button>(R.id.create)
        val name =findViewById<EditText>(R.id.name)
        val description =findViewById<EditText>(R.id.description)
        val salary =findViewById<EditText>(R.id.salary)
        val id = auth.currentUser?.uid

        comRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    val info =it.getValue(company_profile::class.java)
                    val string = info?.id
                    if (string== id)
                    {
                        _company=info?.name
                        Log.d("anmee",_company)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })


        create.setOnClickListener {
            val _name = name.text.toString()
            val _description = description.text.toString()
            val _salary = salary.text.toString()
            val push = jobRef.push()
            val key = push.key
            val student = jobs_profile( id,key,_name, _description,_company, _salary)
            push.setValue(student)
            Toast.makeText(this,"succesfully posted job",Toast.LENGTH_LONG).show()
            finish()
        }
    }
}

