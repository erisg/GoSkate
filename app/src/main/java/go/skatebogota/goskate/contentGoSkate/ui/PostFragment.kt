package go.skatebogota.goskate.contentGoSkate.ui

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import go.skatebogota.goskate.R
import go.skatebogota.goskate.contentGoSkate.viewModels.ViewModelContent
import kotlinx.android.synthetic.main.post.*

class PostFragment : Fragment(){

    private val viewModelContent: ViewModelContent by lazy { ViewModelContent.getViewModelContent(this)!! }
    private var filePath: Uri? = null
    private var navController: NavController? = null

    companion object {
        private const val PICK_IMAGE_CODE = 1234
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        btn_galery.setOnClickListener {
            fileChooser() }

        btn_save_post.setOnClickListener {
            uploadFile() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_CODE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            imagePost.setImageURI(filePath)
        }
    }

    private fun uploadFile() {
        if (filePath != null) {
            viewModelContent.upLoadImagePost(filePath)
            val response = viewModelContent.getFirebaseResponseImagePost()
            val progressDialog = ProgressDialog(this.context)
            progressDialog.setTitle(response)
            progressDialog.show()
            progressDialog.dismiss()
            navController!!.navigate(R.id.action_postFragment2_to_homeFragment)
        }
    }

    private fun fileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "SELECCIONA IMAGEN"), PICK_IMAGE_CODE)
    }
}
