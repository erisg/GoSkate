package go.skatebogota.goskate.data.repositories

import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import go.skatebogota.goskate.data.models.PostVO
import go.skatebogota.goskate.data.models.UserVO
import java.util.*

class RepositoryContent() {

    var firebase: FirebaseAuth = FirebaseAuth.getInstance()
    private var storage: FirebaseStorage = FirebaseStorage.getInstance()
    private var storageReference: StorageReference? = storage.reference
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var refDataB: DatabaseReference = FirebaseDatabase.getInstance().reference
    var refDataBaseUserPost: DatabaseReference = FirebaseDatabase.getInstance().reference.child("UsersPost")

    /**
     * Se sube a firebase foto del post
     */

    fun upLoadImagePost(postVO: PostVO): LiveData<String> {
        postVO.idPost = UUID.randomUUID().toString()
        val refStorage = storage.getReference("images/" + UUID.randomUUID().toString())
        val mutableDataResponse = MutableLiveData<String>()
        refStorage.putFile(postVO.imagePost!!.toUri()).addOnSuccessListener {
            refStorage.downloadUrl.addOnSuccessListener {
                val userMap = HashMap<String, Any>()
                userMap["idPost"] = postVO.idPost!!
                userMap["idUser"] = postVO.idUser!!
                userMap["description"] = postVO.description!!
                userMap["spot"] = postVO.spot!!
                userMap["type"] = postVO.type!!
                userMap["imagePost"] = it.toString()

                refDataBaseUserPost.child(postVO.idPost!!).setValue(userMap)
                    .addOnCompleteListener { task ->
                        val message = task.exception?.toString()
                        if (task.isSuccessful) {
                            mutableDataResponse.value = "Successful"
                        } else {
                            mutableDataResponse.value = message!!
                        }
                    }
            }
        }
        return mutableDataResponse
    }


    /**
     * Se trae de firebase la imagen del post
     */

    fun getDataPost(): MutableLiveData<List<PostVO>> {
     //   var photoReference
        val mutableData = MutableLiveData<List<PostVO>>()
        refDataBaseUserPost.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list = dataSnapshot.children.map {
                    it.getValue(PostVO::class.java)!!
                }
                list.let {
                    mutableData.value = it
                }

            }
        })
        return mutableData
    }


    fun getAllUsersName() : MutableLiveData<List<UserVO>> {
        val mutableData = MutableLiveData<List<UserVO>>()
        refDataB
            .child("Users")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val list = dataSnapshot.children.map {
                        it.getValue(UserVO::class.java)!!
                    }
                    list.let {
                        mutableData.value = it
                    }

                }
            })
        return mutableData
    }


}