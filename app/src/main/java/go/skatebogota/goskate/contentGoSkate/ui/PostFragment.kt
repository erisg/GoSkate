package go.skatebogota.goskate.contentGoSkate.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import go.skatebogota.goskate.R
import kotlinx.android.synthetic.main.post.*

class PostFragment : Fragment() {


    lateinit var alertDialog: AlertDialog
    lateinit var storageReference: StorageReference

    companion object {
        private val PICK_IMAGE_CODE = 1000
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.post, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storageReference = FirebaseStorage.getInstance().getReference("image_upload")


        galery_post.setOnClickListener {

            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "SELECCIONA IMAGEN"),
                PICK_IMAGE_CODE
            )
        }

    }
}