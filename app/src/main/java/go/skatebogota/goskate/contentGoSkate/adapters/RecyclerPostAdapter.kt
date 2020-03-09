package go.skatebogota.goskate.contentGoSkate.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import go.skatebogota.goskate.R
import kotlinx.android.synthetic.main.item_post.view.*

class RecyclerPostAdapter(var users: ArrayList<String>) : RecyclerView.Adapter<RecyclerPostAdapter.UserViewHolder>() {
    fun updateUsers(newUsers: List<String>) {
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = UserViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
    )
    override fun getItemCount() = users.size

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.img_user
        private val userName = view.place
        private val userEmail = view.description
    }

    override fun onBindViewHolder(holder: RecyclerPostAdapter.UserViewHolder, position: Int) {
      //  holder.bind(users[position])
    }
}