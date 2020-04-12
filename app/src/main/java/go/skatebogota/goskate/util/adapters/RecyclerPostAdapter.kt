package go.skatebogota.goskate.util.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import go.skatebogota.goskate.R
import go.skatebogota.goskate.data.models.PostVO
import kotlinx.android.synthetic.main.item_post.view.*
import java.io.File

class RecyclerPostAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerPostAdapter.ViewHolder>() {

    private var dataList = mutableListOf<PostVO>()

    fun setListData(data: MutableList<PostVO>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
    )

    override fun getItemCount() = dataList.size


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView = view.img_user
        var postName = view.placeEditText
        var descriptionPost = view.description

        fun bindView(postVO: PostVO) {
            imageView.setImageURI(postVO.imagePost)
            postName.text = postVO.spot
            descriptionPost.text = postVO.description
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = dataList[position]
        holder.bindView(post)
    }
}