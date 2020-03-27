package go.skatebogota.goskate.util.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import go.skatebogota.goskate.R
import kotlinx.android.synthetic.main.item_post.view.*

class RecyclerPostAdapter(var post:List<String>) : RecyclerView.Adapter<RecyclerPostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
    )
    override fun getItemCount() = post.size

   inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
         val imageView = view.img_user
         val userName = view.placeEditText
         val userEmail = view.description

       fun bind(audio:List<String>) {
       }
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     //
    }
}