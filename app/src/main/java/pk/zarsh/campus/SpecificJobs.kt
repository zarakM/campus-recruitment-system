package pk.zarsh.campus

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SpecificJobs : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.specific_jobs)

        var auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("jobs")
        var list = ArrayList<jobs_profile?>()

        val authid = auth.currentUser?.uid
        //childEve
        ref.orderByChild("id").equalTo(authid.toString()).addChildEventListener(object :ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                list.clear()
            }
        })

        ref.orderByChild("id").equalTo(authid.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    val info =it.getValue(jobs_profile::class.java)
                    Log.d("specific.show","yes")
                    list.add(jobs_profile(info?.id,info?.jobid,info?.name,info?.description,info?.company,info?.salary))
                }
                val viewAdapter = jobs_adapter  (applicationContext,list)
                val viewManager = LinearLayoutManager(applicationContext)
                val recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view)
                recyclerView.setHasFixedSize(true)

                recyclerView.layoutManager = viewManager
                recyclerView.adapter = viewAdapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })
    }
}
