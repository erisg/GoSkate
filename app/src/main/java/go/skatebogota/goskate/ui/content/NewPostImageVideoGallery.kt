package go.skatebogota.goskate.ui.content

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import go.skatebogota.goskate.R
import go.skatebogota.goskate.ui.viewmodels.ViewModelContent
import go.skatebogota.goskate.util.adapters.RecyclerImagesSpot
import kotlinx.android.synthetic.main.galery_photo_video.*

class NewPostImageVideoGallery : Fragment() {

    private var viewModelContent: ViewModelContent? = null
    private var filePath: Uri? = null
    private var navController: NavController? = null
    val VIDEO: Int = 3
    lateinit var typePath: String
    var uriList = ArrayList<Uri>()
    val galleryMutableList = mutableListOf<Uri>()
    private lateinit var adapter: RecyclerImagesSpot

    companion object {
        private const val PICK_IMAGE_CODE = 1234
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.galery_photo_video, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        //SE ELIGE LA FOTO
        saveFloatingActionButton.setOnClickListener {
            navController!!.navigate(R.id.action_newPostImageVideoGallery_to_newLocation)
        }

        //SE ABRE LA CAMARA PARA TOMAR UNA FOTO
        camButton.setOnClickListener {
        }

        //
        videoButton.setOnClickListener {
            videoFileChooser()
        }
        galleryButton.setOnClickListener {
            imageFileChooser()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_CODE && resultCode == RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            if(data.clipData == null){
                typePath = "PHOTO"
                imagesNewLocation.setImageURI(filePath)
                linearButton.visibility = View.GONE
                saveFloatingActionButton.visibility = View.VISIBLE
                uriList.add(filePath!!)
            } else {
                for (i in 0 until data.clipData!!.itemCount) {
                    linearButton.visibility = View.GONE
                    saveFloatingActionButton.visibility = View.VISIBLE
                    uriList.add(data.clipData!!.getItemAt(i).uri)
                }
            }




        } else if (requestCode == VIDEO && resultCode == RESULT_OK) {

            imagesNewLocation.visibility = View.GONE
            videoNewLocation.visibility = View.VISIBLE
            val videoUri: Uri = data?.data!!
            filePath = data.data
            typePath = "VIDEO"
            videoNewLocation.setVideoURI(videoUri)
            linearButton.visibility = View.GONE
            saveFloatingActionButton.visibility = View.VISIBLE


        }
    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun videoFileChooser() {
        val intent = Intent()
        intent.type = "video/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE , true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "SELECCIONE VIDEO"), VIDEO
        )
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun imageFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE , true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "SELECCIONA IMAGEN"),
            PICK_IMAGE_CODE
        )
    }


}