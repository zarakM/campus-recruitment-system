package pk.zarsh.campus

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by HP on 9/15/18.
 */

class student_adapter(internal var mContext: Context, internal var mData: ArrayList<student_profile?>) : RecyclerView.Adapter<student_adapter.myViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {

        val v: View
        v = LayoutInflater.from(mContext).inflate(R.layout.content_student, parent, false)
        return myViewHolder(v)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        var company = mData!![position]
        holder.name.setText(company?.name)
        holder.age.setText(company?.age)
        holder.cgpa.setText(company?.cgpa)
        holder.degree.setText(company?.degree)
        holder.experience.setText(company?.experience)
        Glide.with(mContext)
                .load("https://firebasestorage.googleapis.com/v0/b/campus-recruitment-8a151.appspot.com/o/VFiA29fElZQT1T5XRVEiCA6VuNM2?alt=media&token=16cd28ad-a311-45e8-9f3f-b8dfb835dd3e")
                .into(holder.pic)

        holder.itemView.setOnLongClickListener {
            val db= FirebaseDatabase.getInstance()
            val ref = db.getReference("users/student")
            ref.orderByChild("id").equalTo(company?.id.toString()).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
                override fun onDataChange(p0: DataSnapshot) {

                    p0.children.forEach{
                        val map = HashMap<String,Any>()
                        map.put("delete","yes")
                        it.ref.updateChildren(map)
                    }
                }
            })
            true
        }
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView
        val age : TextView
        val cgpa : TextView
        val degree : TextView
        val experience : TextView
        val pic :ImageView
        init {
            name =itemView.findViewById(R.id.name) as TextView
            age =itemView.findViewById(R.id.age) as TextView
            cgpa =itemView.findViewById(R.id.cgpa) as TextView
            degree =itemView.findViewById(R.id.degree) as TextView
            experience =itemView.findViewById(R.id.experience) as TextView
            pic =itemView.findViewById(R.id.pic) as ImageView
        }
    }
}
