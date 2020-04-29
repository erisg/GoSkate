package go.skatebogota.goskate.util.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import go.skatebogota.goskate.R
import go.skatebogota.goskate.data.models.PostVO
import kotlinx.android.synthetic.main.item_image_post.view.*
import java.io.File

class RecyclerPostAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerPostAdapter.ViewHolder>() {

    private var dataList = emptyList<PostVO>()

    fun setListData(data: List<PostVO>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.item_image_post, parent, false)
    )

    override fun getItemCount() = dataList.size


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view.img_post
        var postName : TextView = view.placeEditText
        var descriptionPost: TextView = view.description
        var likeImageView : ImageView = view.likeImageView
        var numberOfLikes: TextView = view.numberLikesTextView

        fun bindView(postVO: PostVO) {
            Glide.with(context)
                .load(File(postVO.imagePost!!))
                .skipMemoryCache(true)//Borrar cache
                .into(imageView)
            postName.text = postVO.spot
            descriptionPost.text = postVO.description
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = dataList[position]
        numberOfLikes(holder.numberOfLikes , post.idPost )
        holder.bindView(post)
        holder.likeImageView.setOnClickListener {
            isLike(post.idPost, holder.likeImageView, post.idUser)
            if (holder.likeImageView.tag != "like") {
                FirebaseDatabase.getInstance().reference
                    .child("Likes")
                    .child(post.idPost!!)
                    .child(post.idUser!!)
                    .setValue(true)
            }else{
                FirebaseDatabase.getInstance().reference
                    .child("Likes")
                    .child(post.idPost!!)
                    .child(post.idUser!!)
                    .removeValue()
            }
        }
    }

    private fun numberOfLikes(numberOfLikes: TextView, idPost: String?) {
        val likesRef =  FirebaseDatabase.getInstance().reference.child("Likes").child(idPost!!)
        likesRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(p0: DataSnapshot) {
              if (p0.exists()){
                  numberOfLikes.text = p0.childrenCount.toString() + "ME GUSTA"
              }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    private fun isLike(idPost: String?, likeImageView: ImageView , idUser: String?) {
        val likesRef =  FirebaseDatabase.getInstance().reference.child("Likes").child(idPost!!)
        likesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.child(idUser!!).exists()){
                    likeImageView.setImageResource(R.drawable.fire_red)
                    likeImageView.tag = "like"
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                likeImageView.setImageResource(R.drawable.fire)
                likeImageView.tag = "like"
            }
        })
    }
}