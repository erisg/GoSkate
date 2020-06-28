package go.skatebogota.goskate.util.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import go.skatebogota.goskate.R

class RecyclerGalerySpot (var context: Context , private val uriList: List<Uri>) : RecyclerView.Adapter<RecyclerGalerySpot.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_galery_spot, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setParams(uriList[position])

    }

    override fun getItemCount() = uriList.size


    /**
     * Clase interna encargada de controlar la lógica de cada uno de los items existentes del recycler view
     *
     * @param mView vista que sera cargada sobre el recyclerview
     */

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {

        val imageView: ImageView = mView.findViewById(R.id.spotGalerymageView)
         fun setParams(uri: Uri){
             Glide.with(context)
                 .load(uri)
                 .into(imageView)
         }
    }



}