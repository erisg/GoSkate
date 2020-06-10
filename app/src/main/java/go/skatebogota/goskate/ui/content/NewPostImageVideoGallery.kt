package go.skatebogota.goskate.ui.content

import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
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
    lateinit var typePath: String
    var uriList = ArrayList<Uri>()
    var picture: Uri? = null
    private lateinit var adapter: RecyclerImagesSpot
    private val RECORD_REQUEST_CODE = 101

    companion object {
        val CAMERA = 1
        val VIDEO: Int = 3
        private const val PICK_IMAGE_CODE = 1234
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.galery_photo_video, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        //SE ELIGE LA FOTO
        saveFloatingActionButton.setOnClickListener {

            navController!!.navigate(R.id.action_newPostImageVideoGallery_to_newLocation)
        }

        //SE ABRE LA CAMARA PARA TOMAR UNA FOTO

        camButton.setOnClickListener {
            imageFileChooser()
        }

        //SE ABRE LA GALERIA PARA SELECCIONAR VIDEOS

        videoButton.setOnClickListener {
            videoFileChooser()
        }

        //SE ABRE LA GALERIA PARA SELECCIONAR FOTOS
        galleryButton.setOnClickListener {
            imageFileChooser()
        }
    }


    private fun requestPermissionGallery() {
        val galleryPermission = ActivityCompat.checkSelfPermission(this.requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if (galleryPermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val permissions = arrayOf(android.Manifest.permission.READ_PHONE_STATE)
                requestPermissions(permissions, RECORD_REQUEST_CODE) }
        }else{
            imageFileChooser()
        }
    }

    private fun requestPermissionCamera() {
        val galleryPermission = ActivityCompat.checkSelfPermission(this.requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if (galleryPermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val permissions = arrayOf(android.Manifest.permission.READ_PHONE_STATE)
                requestPermissions(permissions, RECORD_REQUEST_CODE) }
        }else{
            imageFileChooser()
        }
    }

    private fun requestPermissionVideoGallery() {
        val galleryPermission = ActivityCompat.checkSelfPermission(this.requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if (galleryPermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val permissions = arrayOf(android.Manifest.permission.READ_PHONE_STATE)
                requestPermissions(permissions, RECORD_REQUEST_CODE) }
        }else{
            videoFileChooser()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when {

            requestCode == PICK_IMAGE_CODE && resultCode == RESULT_OK && data != null && data.data != null -> {
                filePath = data.data
                if (data.clipData == null) {
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
            }

            requestCode == VIDEO && resultCode == RESULT_OK -> {
                imagesNewLocation.visibility = View.GONE
                videoNewLocation.visibility = View.VISIBLE
                val videoUri: Uri = data?.data!!
                filePath = data.data
                typePath = "VIDEO"
                videoNewLocation.setVideoURI(videoUri)
                linearButton.visibility = View.GONE
                saveFloatingActionButton.visibility = View.VISIBLE
            }
            requestCode == CAMERA && resultCode == RESULT_OK -> {
                imagesNewLocation.visibility = View.VISIBLE
                videoNewLocation.visibility = View.GONE
                linearButton.visibility = View.GONE
                saveFloatingActionButton.visibility = View.VISIBLE
                val pictureUri: Uri = data?.data!!
                filePath = data.data
                typePath = "PHOTO"
                imagesNewLocation.setImageURI(pictureUri)
            }
        }
    }

    private fun camFileChooser() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(
            Intent.createChooser(intent, "CAPTURA UNA FOTO"), CAMERA
        )
    }


    private fun videoFileChooser() {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "SELECCIONE VIDEO"), VIDEO)
    }

    private fun imageFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "SELECCIONA IMAGEN"),
            PICK_IMAGE_CODE
        )
    }


}