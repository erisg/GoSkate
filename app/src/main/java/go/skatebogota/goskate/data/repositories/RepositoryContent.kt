package go.skatebogota.goskate.data.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import go.skatebogota.goskate.data.models.PostVO
import go.skatebogota.goskate.ui.content.PostFragment
import java.util.*
import kotlin.collections.ArrayList

class RepositoryContent() {

    var userResponse: String = ""
    var firebase: FirebaseAuth = FirebaseAuth.getInstance()
    private var storage: FirebaseStorage = FirebaseStorage.getInstance()
    private var storageReference: StorageReference? = storage.reference
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var refDataBse: DatabaseReference = FirebaseDatabase.getInstance().reference.child("UsersPost")

    /**
     * Se sube a firebase foto del post
     */

    fun upLoadImagePost(postVO: PostVO) {
        val id = auth.currentUser?.uid
        val refStorage = storage.getReference("images")

        refStorage.putFile(postVO.imagePost!!).addOnSuccessListener {
            refStorage.downloadUrl.addOnSuccessListener {
                val userMap = HashMap<String, Any>()
                userMap["id"] = id!!
                userMap["description"] = postVO.description!!
                userMap["spot"] = postVO.spot!!
                userMap["imagePost"] = it.toString()

                refDataBse.child(id).setValue(userMap).addOnCompleteListener { task ->
                    val message = task.exception?.toString()
                    userResponse = if (task.isSuccessful) {
                        Log.e("post", "yes")
                        "Successful"
                    } else {

                        Log.e("post", "$message")
                        "$message"
                    }
                }
            }
        }

    }


    /**
     * Se trae de firebase la imagen del post
     */

    fun getPostUser(): PostVO {
        refDataBse.child(auth.currentUser!!.uid).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    for (it in p0.children) {
                        val data: ArrayList<PostVO> = ArrayList()
                        data.add(PostVO())
                    }
                }
            }

        })
        return PostVO()
    }




}