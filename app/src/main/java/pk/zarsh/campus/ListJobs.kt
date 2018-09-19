package pk.zarsh.campus

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListJobs : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_jobs)

        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("jobs")
        var list = ArrayList<jobs_profile?>()


        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    val info =it.getValue(jobs_profile::class.java)

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
