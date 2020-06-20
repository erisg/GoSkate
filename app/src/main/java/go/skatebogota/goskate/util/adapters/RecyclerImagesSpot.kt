package go.skatebogota.goskate.util.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.net.toUri
import go.skatebogota.goskate.R
import kotlinx.android.synthetic.main.item_galery_spot.view.*

class RecyclerImagesSpot(val context: Context,
                         private val listPhotos: List<Uri>) : BaseAdapter() {


    override fun getItem(position: Int) = null

    override fun getItemId(position: Int) = 0L

    override fun getCount() = this.listPhotos.size

    private var view: View? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        this.view = convertView
        if (this.view == null) {
            val inflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            this.view = inflater.inflate(R.layout.item_galery_spot, parent, false)
            this.view!!.spotGalerymageView.setImageURI(listPhotos[position])

        }
        return this.view!!
    }


}