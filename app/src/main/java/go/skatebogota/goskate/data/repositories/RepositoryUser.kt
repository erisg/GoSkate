package go.skatebogota.goskate.data.repositories


import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import go.skatebogota.goskate.data.models.UserVO
import go.skatebogota.goskate.util.interfaces.AuthListenerResponseUserInfo
import go.skatebogota.goskate.util.interfaces.AuthListenerResponseUserRegister

class RepositoryUser() {

    var response: String = ""
    var authListener : AuthListenerResponseUserRegister? = null
    private var authListenerInfoUser : AuthListenerResponseUserInfo? = null
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var userResponse :String = ""

    /**
     * Se registra USUARIO en retrofit
     */
    fun registerUser(userVO: UserVO) {
        auth.createUserWithEmailAndPassword(userVO.userEmail!!, userVO.password!!).addOnCompleteListener {
            val message  = it.exception?.toString()
            if(it.isSuccessful){
                userResponse = "Successful"
            }else{
                userResponse ="$message"
            }
        }
    }

    /**
     * Se guarda la informacion del perfil del usuario
     */
    fun saveInfoUser(userVO: UserVO) {
        userVO.userId = auth.currentUser!!.uid
        var userRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")

        val userMap = HashMap<String , Any>()
        userMap["uid"] = userVO.userId!!
        userMap["userName"] = userVO.userName!!
        userMap["userEmail"] = userVO.userEmail!!
        userMap["userPassword"] = userVO.password!!
        userMap["ageUser"] = userVO.birthDate!!

        userRef.child(userVO.userId!!).setValue(userMap).addOnCompleteListener{task ->
            val message  = task.exception?.toString()
            if(task.isSuccessful){
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