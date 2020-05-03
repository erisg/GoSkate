package go.skatebogota.goskate.ui.content

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import go.skatebogota.goskate.R
import go.skatebogota.goskate.data.models.PostVO
import go.skatebogota.goskate.ui.viewmodels.ViewModelContent
import kotlinx.android.synthetic.main.include_image.view.*
import kotlinx.android.synthetic.main.include_video.view.*
import kotlinx.android.synthetic.main.post.*

class PostFragment : Fragment() {

    private lateinit var viewModelContent: ViewModelContent
    private var filePath: Uri? = null
    private var navController: NavController? = null
    private var userImagePost: Uri? = null
    lateinit var description: String
    lateinit var userPlace: String
    lateinit var audioList: ArrayList<PostVO>
    var mediaControl: MediaController? = null
    val VIDEO: Int = 3
    lateinit var typePath: String

    companion object {
        private const val PICK_IMAGE_CODE = 1234
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelContent = ViewModelProviders.of(this).get(ViewModelContent::class.java)
        mediaControl = MediaController(this.context)



        videoImageView.setOnClickListener {
            videoFileChooser()
        }

        galeryImageView.setOnClickListener {
            imageFileChooser()
        }

        btn_save_post.setOnClickListener {
            uploadFile()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_CODE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            typePath = "PHOTO"
            includeImagePost.imageView.setImageURI(filePath)
        } else
            if (requestCode == VIDEO && resultCode == RESULT_OK) {
                includeImagePost.visibility = View.GONE
                includeVideoPost.visibility = View.VISIBLE
                val videoUri: Uri = data?.data!!
                filePath = data.data
                typePath = "VIDEO"
                includeVideoPost.postVideoView.setVideoURI(videoUri)

            }
    }

    private fun uploadFile() {
        userImagePost = filePath
        description = descriptionEditText.text.toString()
        userPlace = placeEditText.text.toString()
        if (userImagePost != null) {
                viewModelContent.upLoadImagePost(userImagePost, description, userPlace, typePath).observeForever {
                    if(it == "Successful"){
                        Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show()
                    }else {
                        navController!!.navigate(R.id.action_postFragment2_to_homeFragment)
                    }
                }

            }
    }

    private fun videoFileChooser() {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "SELECCIONE VIDEO"), VIDEO
        )
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
