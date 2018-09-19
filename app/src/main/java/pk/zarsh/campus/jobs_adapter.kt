package pk.zarsh.campus

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by HP on 9/15/18.
 */

class jobs_adapter(internal var mContext: Context, internal var mData: ArrayList<jobs_profile?>) : RecyclerView.Adapter<jobs_adapter.myViewHolder>() {
    val database = FirebaseDatabase.getInstance()
    val student_refr= database.getReference("users/student")
    val company_refr= database.getReference("users/company")
    val jobs_refr= database.getReference("jobs")
    val auth = FirebaseAuth.getInstance().currentUser?.uid
    var key:String? = null
    lateinit var i:Intent

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val v: View
        v = LayoutInflater.from(mContext).inflate(R.layout.content_job, parent, false)
        return myViewHolder(v)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        var company = mData!![position]
        holder.name.setText(company?.name)
        holder.description.setText(company?.description)
        holder.salary.setText(company?.salary)
        holder.company.setText(company?.company)
        val job_id =company?.jobid
        Glide.with(mContext)
                .load("https://firebasestorage.googleapis.com/v0/b/campus-recruitment-8a151.appspot.com/o/VFiA29fElZQT1T5XRVEiCA6VuNM2?alt=media&token=16cd28ad-a311-45e8-9f3f-b8dfb835dd3e")
                .into(holder.pic)

        holder.itemView.setOnClickListener {
            student_refr.orderByChild("id").equalTo(auth.toString()).addValueEventListener(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    Log.d("errro",p0.message)
                }
                override fun onDataChange(p0: DataSnapshot) {
                    val child=p0.children.forEach {
                        val info = it.getValue(student_profile::class.java)
                        val type= info?.type
                        if (type==null){}
                        else {
                            holder.b_apply.visibility=View.VISIBLE
                        }
                    }
                }
            })

            company_refr.orderByChild("id").equalTo(auth.toString()).addValueEventListener(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    Log.d("errro",p0.message)
                }
                override fun onDataChange(p0: DataSnapshot) {
                    i = Intent(mContext,AppliedStudents::class.java)
                    i.putExtra("key",company?.jobid.toString())
                    Log.d("keyaas",company?.jobid.toString())
                    mContext.startActivity(i)
                }
            })
        }

        holder.b_apply.setOnClickListener {
            holder.b_apply.setBackgroundColor(Color.GRAY)
            holder.b_apply.text="Applied"
            val refa= database.getReference("jobs")
            refa.orderByChild("jobid").equalTo(job_id.toString()).addValueEventListener(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    TODO("time nahi") //To change body of created functions use File | Settings | File Templates.
                }
                override fun onDataChange(p0: DataSnapshot) {
                    p0.children.forEach{
                        val info = it.getValue(jobs_profile::class.java)
                        key = info?.jobid
                        Log.d("key",key)
                    }
                    val applid_student= database.getReference("applied_student")
                    val apply = applied_student(auth,key)
                    applid_student.push().setValue(apply)
                }
                })
        }

        holder.itemView.setOnLongClickListener {
            jobs_refr.orderByChild("jobid").equalTo(job_id).addValueEventListener(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
                override fun onDataChange(p0: DataSnapshot) {
                    for (appleSnapshot in p0.getChildren()) {
                        appleSnapshot.getRef().removeValue()
                    }
                }
            })
            mData.removeAt(position)
            true
        }
        }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView
        val description : TextView
        val salary : TextView
        val company : TextView
        val pic :ImageView
        val b_apply:Button
        init {
            name =itemView.findViewById(R.id.name) as TextView
            description =itemView.findViewById(R.id.description) as TextView
            salary =itemView.findViewById(R.id.salary) as TextView
            company =itemView.findViewById(R.id.company) as TextView
            b_apply =itemView.findViewById(R.id.apply) as Button
            pic =itemView.findViewById(R.id.pic) as ImageView
        }
    }
}