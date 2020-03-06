package go.skatebogota.goskate.contentGoSkate.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import go.skatebogota.goskate.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.post.*

class PostFragment : Fragment() , View.OnClickListener {


    private var filePath: Uri? = null
    private var storage:FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    companion object {
        private val PICK_IMAGE_CODE = 1234
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

    }

    override fun onClick(v: View?) {
        if(v === btn_galery){
            fileChooser()
        }
        else if(v === btn_save_post){
            uploadFile()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_CODE && resultCode == Activity.RESULT_OK && data != null && data.data != null){
            filePath = data.data

        }
    }

    private fun uploadFile() {

        }

    private fun fileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "SELECCIONA IMAGEN"), PICK_IMAGE_CODE)
    }
}