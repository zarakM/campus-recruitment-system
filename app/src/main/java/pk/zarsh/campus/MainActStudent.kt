package pk.zarsh.campus

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class MainActStudent : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_act_student)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        if (auth.currentUser!=null) {
            val student = findViewById<Button>(R.id.edit_profile)
            val company = findViewById<Button>(R.id.companies)
            val job = findViewById<Button>(R.id.jobs)
            val i_student = Intent(this, EditProfileStudent::class.java)
            val i_company = Intent(this, ListCompany::class.java)
            val i_job = Intent(this, ListJobs::class.java)

            student.setOnClickListener { startActivity(i_student) }
            company.setOnClickListener { startActivity(i_company) }
            job.setOnClickListener { startActivity(i_job) }
        }
        else{
            finish()
        }

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
