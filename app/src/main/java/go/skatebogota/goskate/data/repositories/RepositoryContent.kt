package go.skatebogota.goskate.data.repositories

import android.os.Build
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import go.skatebogota.goskate.data.models.PostVO
import java.util.*

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

    fun upLoadImagePost(postVO: PostVO): String {
        postVO.idPost = UUID.randomUUID().toString()
        val refStorage = storage.getReference("images/" + UUID.randomUUID().toString())

        refStorage.putFile(postVO.imagePost!!.toUri()).addOnSuccessListener {
            refStorage.downloadUrl.addOnSuccessListener {
                val userMap = HashMap<String, Any>()
                userMap["idPost"] = postVO.idPost!!
                userMap["idUser"] = postVO.idUser!!
                userMap["description"] = postVO.description!!
                userMap["spot"] = postVO.spot!!
                userMap["imagePost"] = it.toString()

                refDataBse.child(postVO.idPost!!).setValue(userMap).addOnCompleteListener { task ->
                    val message = task.exception?.toString()
                    userResponse = if (task.isSuccessful) {
                        "Successful"
                    } else {
                        message!!
                    }
                }
            }
        }
        return userResponse
    }


    /**
     * Se trae de firebase la imagen del post
     */

    fun getDataPost(): MutableLiveData<List<PostVO>> {
        val dataList = mutableListOf<PostVO>()
        val mutableData = MutableLiveData<List<PostVO>>()
        refDataBse.addValueEventListener(object : ValueEventListener {
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



}