package go.skatebogota.goskate.data.repositories


import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import go.skatebogota.goskate.data.models.UserVO
import go.skatebogota.goskate.util.interfaces.AuthListenerResponseUserInfo
import go.skatebogota.goskate.util.interfaces.AuthListenerResponseUserRegister

class RepositoryUser() {

    var response: String = ""
    private var storage: FirebaseStorage = FirebaseStorage.getInstance()
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var userResponse :String = ""

    /**
     * Se registra USUARIO en retrofit
     */
    fun registerUser(userVO: UserVO) {
        auth.createUserWithEmailAndPassword(userVO.userEmail!!, userVO.password!!).addOnCompleteListener {
            val message  = it.exception?.toString()
            if(it.isSuccessful){
                userVO.userId = auth.currentUser!!.uid
                val userRef: DatabaseReference =
                    FirebaseDatabase.getInstance().reference.child("Users")
                val refStorage = storage.getReference("imagesProfile")
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
                                if (task.isSuccessful) {
                                    userResponse = "Successful"
                                } else {
                                    userResponse = "$message"
                                }
                            }

                    }

                }
                userResponse = "Successful"
            }else{
                userResponse ="$message"
            }
        }
    }


    fun loginUser(userVO: UserVO) {

        auth.signInWithEmailAndPassword(userVO.userEmail!!, userVO.password!!).addOnCompleteListener {result->
            val message  = result.exception?.toString()
            if(result.isSuccessful){
                userResponse = "Successful"
            }else{
                userResponse ="$message"
            }

        }
    }


}