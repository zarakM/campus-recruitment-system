package pk.zarsh.campus

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.R.attr.author
import android.widget.TextView
import com.google.firebase.database.ChildEventListener





class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val mDatabase = FirebaseDatabase.getInstance()
        val mDatabaseReference = mDatabase.getReference("users")
        mDatabaseReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, prevChildKey: String?) {
            }
            override fun onChildChanged(dataSnapshot: DataSnapshot, prevChildKey: String?) {}

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}

            override fun onChildMoved(dataSnapshot: DataSnapshot, prevChildKey: String?) {}

            override fun onCancelled(databaseError: DatabaseError) {}
        });

        val text = findViewById<TextView>(R.id.text1)
        text.setOnClickListener {
            val currentUserDb = mDatabaseReference.push()
            currentUserDb.child("type").setValue("student")
        }
    }
}
