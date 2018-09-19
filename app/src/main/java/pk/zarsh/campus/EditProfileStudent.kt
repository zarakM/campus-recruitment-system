package pk.zarsh.campus

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.main_act_admin.*

class EditProfileStudent : AppCompatActivity() {
    val datbase = FirebaseDatabase.getInstance()
    val auth = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile_student)

        val button = findViewById<Button>(R.id.sign_up_button)
        val name =findViewById<EditText>(R.id.name)
        val age =findViewById<EditText>(R.id.age)
        val degree =findViewById<EditText>(R.id.degree)
        val cgpa =findViewById<EditText>(R.id.cgpa)
        val rel_experience =findViewById<EditText>(R.id.experience)

        val ref =datbase.getReference("users/student")

        ref.orderByChild("id").equalTo(auth.toString()).addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach{
                    val info = it.getValue(student_profile::class.java)
                    name.setText(info?.name)
                    age.setText(info?.age)
                    degree.setText(info?.degree)
                    cgpa.setText(info?.cgpa)
                    rel_experience.setText(info?.experience)
                }
            }
        })

        button.setOnClickListener{
            ref.orderByChild("id").equalTo(auth.toString()).addValueEventListener(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {
                    p0.children.forEach{
                        val _name = name.text.toString()
                        val _age = age.text.toString()
                        val _degree = degree.text.toString()
                        val _cgpa=cgpa.text.toString()
                        val _rel_experience= rel_experience.text.toString()
                        val student = student_profile(auth.toString(),_name, _age, _degree, _cgpa, _rel_experience,"student")
                        it.ref.setValue(student)
                    }
                }
            })
            finish()
        }
    }
}
