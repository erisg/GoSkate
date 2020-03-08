package go.skatebogota.goskate.contentGoSkate.repository

import android.app.Application
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class RepositoryContent (application: Application) {

    var response : String = ""

    private var storage: FirebaseStorage? = null
    private var storageReference: StorageReference? = null


    fun upLoadImagePost(filePath: Uri?){
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        if (filePath != null) {

            val imageRef = storageReference!!.child("images/" + UUID.randomUUID().toString())
            imageRef.putFile(filePath!!)
                .addOnSuccessListener {
                    response = "PUBLICACION REALIZADA"
                }
                .addOnFailureListener {
                    response = "ALGO FALLO , POR FAVOR VUELVE  A INTENTARLO"
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = 100.0 * taskSnapshot.bytesTransferred/taskSnapshot.totalByteCount
                    response ="IMAGEN "+progress.toInt() + "% ..."
                }
        }

    }
}