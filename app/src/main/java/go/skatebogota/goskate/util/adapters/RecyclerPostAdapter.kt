package go.skatebogota.goskate.util.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import go.skatebogota.goskate.R
import kotlinx.android.synthetic.main.item_post.view.*

class RecyclerPostAdapter(var post: ArrayList<String>) : RecyclerView.Adapter<RecyclerPostAdapter.ImagePostViewHolder>() {
    fun updateUsers(newUsers: List<String>) {
        post.clear()
        post.addAll(newUsers)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = ImagePostViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
    )
    override fun getItemCount() = post.size

    class ImagePostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.img_user
        private val userName = view.place
        private val userEmail = view.description
    }

    override fun onBindViewHolder(holder: RecyclerPostAdapter.ImagePostViewHolder, position: Int) {
      //  holder.bind(users[position])
    }
}