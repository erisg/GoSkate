package go.skatebogota.goskate.data.repositories


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import go.skatebogota.goskate.data.models.UserVO
import go.skatebogota.goskate.util.interfaces.AuthListenerResponseUserInfo
import go.skatebogota.goskate.util.interfaces.AuthListenerResponseUserRegister
import java.util.*
import kotlin.collections.HashMap

class RepositoryUser() {

    var response: String = ""
    private var storage: FirebaseStorage = FirebaseStorage.getInstance()
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var userResponse: String = ""

    /**
     * Se registra USUARIO en retrofit
     */
    fun registerUser(userVO: UserVO): MutableLiveData<String> {
        val mutableDataResponse = MutableLiveData<String>()
        auth.createUserWithEmailAndPassword(userVO.userEmail!!, userVO.password!!)
            .addOnCompleteListener {
                val message = it.exception?.toString()
                if (it.isSuccessful) {
                    userVO.userId = auth.currentUser!!.uid
                    val userRef: DatabaseReference =
                        FirebaseDatabase.getInstance().reference.child("Users")
                    val refStorage =
                        storage.getReference("imagesProfile/" + UUID.randomUUID().toString())
                    refStorage.putFile(userVO.imageProfile!!).addOnSuccessListener {
                        refStorage.downloadUrl.addOnSuccessListener {
                            val userMap = HashMap<String, Any>()
                            userMap["uid"] = userVO.userId!!
                            userMap["imageProfle"] = userVO.imageProfile.toString()
                            userMap["userName"] = userVO.userName!!
                            userMap["userEmail"] = userVO.userEmail!!
                            userMap["userPassword"] = userVO.password!!
                            userMap["ageUser"] = userVO.birthDate!!
                            userMap["sexUser"] = userVO.sex!!

                            userRef.child(userVO.userId!!).setValue(userMap)
                                .addOnCompleteListener { task ->
                                    val message = task.exception?.toString()
                                    mutableDataResponse.value = if (task.isSuccessful) {
                                        "Successful"
                                    } else {
                                        "$message"
                                    }
                                }
                        }
                    }
                }
            }
        return mutableDataResponse
    }


    fun loginUser(userVO: UserVO): MutableLiveData<String> {
        val mutableDataResponse = MutableLiveData<String>()
        auth.signInWithEmailAndPassword(userVO.userEmail!!, userVO.password!!)
            .addOnCompleteListener { result ->
                val message = result.exception?.toString()
                 if (result.isSuccessful) {
                     mutableDataResponse.value = "Successful"
                } else {
                     mutableDataResponse.value = message
                }

            }
        return mutableDataResponse
    }

}