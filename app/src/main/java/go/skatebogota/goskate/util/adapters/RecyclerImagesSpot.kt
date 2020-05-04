package go.skatebogota.goskate.util.adapters

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import go.skatebogota.goskate.R
import kotlinx.android.synthetic.main.item_galery_spot.view.*
import java.io.File

class RecyclerImagesSpot(val context: Context,
                         private val EvidencesPreview: List<String>) : BaseAdapter() {


    override fun getItem(position: Int) = null

    override fun getItemId(position: Int) = 0L

    override fun getCount() = this.EvidencesPreview.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val inflater=(context as Activity).layoutInflater
        val view=inflater.inflate(R.layout.item_galery_spot,parent, false)



        return view


    }
}