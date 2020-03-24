package go.skatebogota.goskate.data.repositories

import android.app.Application
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class RepositoryContent (application: Application) {

    var response : String = ""

    private var storage: FirebaseStorage =  FirebaseStorage.getInstance()
    private var storageReference: StorageReference? = storage.reference

    /**
     * Se sube a firebase foto del post
     */

    fun upLoadImagePost(filePath: Uri?){
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


    /**
     * Se trae de firebase la imagen del post
     */

    fun getImagePost() =  storageReference!!.child("images/")

}