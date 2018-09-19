package pk.zarsh.campus

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 * Created by HP on 9/15/18.
 */

class company_adapter(internal var mContext: Context, internal var mData: ArrayList<company_profile?>) : RecyclerView.Adapter<company_adapter.myViewHolder>() {
    val database = FirebaseDatabase.getInstance()
    val ref = database.getReference("users/company")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {

        val v: View
        v = LayoutInflater.from(mContext).inflate(R.layout.content_company, parent, false)
        return myViewHolder(v)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        var company = mData!![position]
        holder.name.setText(company?.name)
        holder.founder.setText(company?.founded)
        holder.employ.setText(company?.employers)
        holder.address.setText(company?.address)
        Glide.with(mContext)
                .load("https://firebasestorage.googleapis.com/v0/b/campus-recruitment-8a151.appspot.com/o/VFiA29fElZQT1T5XRVEiCA6VuNM2?alt=media&token=16cd28ad-a311-45e8-9f3f-b8dfb835dd3e")
                .into(holder.pic)

        holder.itemView.setOnLongClickListener {
            ref.orderByChild("id").equalTo(company?.id.toString()).addValueEventListener(object :ValueEventListener{
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
        val founder : TextView
        val employ : TextView
        val address : TextView
        val pic :ImageView

        init {
            name =itemView.findViewById(R.id.name) as TextView
            founder =itemView.findViewById(R.id.founded) as TextView
            employ =itemView.findViewById(R.id.employ) as TextView
            address =itemView.findViewById(R.id.address) as TextView
            pic =itemView.findViewById(R.id.pic) as ImageView
        }
    }
}