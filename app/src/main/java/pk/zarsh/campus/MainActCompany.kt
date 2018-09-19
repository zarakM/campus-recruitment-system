package pk.zarsh.campus

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class MainActCompany : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_act_company)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        if (auth.currentUser!=null) {
            val student = findViewById<Button>(R.id.view_students)
            val view_post = findViewById<Button>(R.id.view_post)
            val create_post = findViewById<Button>(R.id.create_post)
            val students = Intent(this, ListStudent::class.java)
            val createpost = Intent(this, CreateJob::class.java)
            val viewpost = Intent(this, SpecificJobs::class.java)

            student.setOnClickListener { startActivity(students) }
            view_post.setOnClickListener { startActivity(viewpost) }
            create_post.setOnClickListener { startActivity(createpost) }
        }
        else{finish()}
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_favorite -> {
            FirebaseAuth.getInstance().signOut()
            finish()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}
