package go.skatebogota.goskate.data.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import go.skatebogota.goskate.data.models.PostVO
import java.util.*

class RepositoryContent () {

    var userResponse : String = ""
    var firebase: FirebaseAuth = FirebaseAuth.getInstance()
    private var storage: FirebaseStorage =  FirebaseStorage.getInstance()
    private var storageReference: StorageReference? = storage.reference
    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Se sube a firebase foto del post
     */

    fun upLoadImagePost(postVO: PostVO){
        val ref :  DatabaseReference = FirebaseDatabase.getInstance().reference.child("UsersPost" )
        if (postVO.postFilePath != null) {
            val userMap = HashMap<String , Any>()
            userMap["uid"] = postVO.postId!!
            userMap["description"] = postVO.description!!
            userMap["spot"] = postVO.spot!!

            ref.child(postVO.postId!!).setValue(userMap).addOnCompleteListener { task ->
                val message  = task.exception?.toString()
                userResponse = if(task.isSuccessful){
                    Log.e("post" , "yes")
                    "Successful"
                }else{

                    Log.e("post" , "$message")
                    "$message"
                }
            }
        }

    }


    /**
     * Se trae de firebase la imagen del post
     */

    fun getImagePost() {

    }

}