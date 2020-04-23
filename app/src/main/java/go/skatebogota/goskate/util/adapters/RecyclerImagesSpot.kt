package go.skatebogota.goskate.util.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import go.skatebogota.goskate.R
import kotlinx.android.synthetic.main.item_galery_spot.view.*

class RecyclerImagesSpot(private val context: Context) :
    RecyclerView.Adapter<RecyclerImagesSpot.ViewHolder>() {

    private var dataList = mutableListOf<Uri>()

    fun setListData(data: MutableList<Uri>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.item_galery_spot, parent, false)
    )

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView = view.spotGalerymageView

        fun bindView(uri: Uri) {
            imageView.setImageURI(uri)
        }
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = dataList[position]
        holder.bindView(post)
    }
}